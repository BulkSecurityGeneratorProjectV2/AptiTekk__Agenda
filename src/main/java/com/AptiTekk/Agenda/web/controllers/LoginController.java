package com.AptiTekk.Agenda.web.controllers;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import com.AptiTekk.Agenda.core.UserService;
import com.AptiTekk.Agenda.core.entity.User;
import com.AptiTekk.Agenda.core.utilities.AgendaLogger;
import com.AptiTekk.Agenda.core.utilities.BanHelper;
import com.AptiTekk.Agenda.core.utilities.FacesSessionHelper;
import com.AptiTekk.Agenda.web.AuthenticationFilter;

/**
 * The LoginController is the backing bean for login forms. It controls
 * authentication of users and banning guests who enter invalid information too
 * many times.
 */
@ManagedBean
@ViewScoped
public class LoginController {

    private final static String BAN_NAME = "Login";

    @Inject
    private UserService userService;

    @PostConstruct
    public void init() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        username = externalContext.getRequestHeaderMap().get("REMOTE_USER");
    }

    private String username;
    private String password;

    /**
     * Whether or not the user is currently banned (for attempting to login too
     * many times)
     */
    private boolean isBanned = BanHelper.isUserBanned(BAN_NAME);

    /**
     * Attempts to log the user in with the credentials they have input.
     *
     * @return The outcome page.
     */
    public String login() {
        FacesContext context = FacesContext.getCurrentInstance();
        AgendaLogger.logVerbose("Logging In - User: " + username);

        User authenticatedUser = userService.correctCredentials(username, password);

        username = null;
        password = null;

        if (authenticatedUser == null) // Invalid Credentials
        {
            context.addMessage("loginForm", new FacesMessage("Login Failed: Incorrect Credentials."));
            BanHelper.recordFailedAttempt(BAN_NAME);
            if (BanHelper.getNumberFailedAttempts(BAN_NAME) >= 3) {
                BanHelper.banUser(BAN_NAME, 6);
                setBanned(true);
            }
            return null;
        } else {
            AgendaLogger.logVerbose("Login Successful");
            FacesSessionHelper.setSessionVariable(UserService.SESSION_VAR_USERNAME,
                    authenticatedUser.getUsername());
            BanHelper.unBanUser(BAN_NAME);
            BanHelper.clearFailedAttempts(BAN_NAME);
            String originalUrl
                    = FacesSessionHelper.getSessionVariableAsString(AuthenticationFilter.SESSION_ORIGINAL_URL);
            if (originalUrl != null) {
                FacesSessionHelper.removeSessionVariable(AuthenticationFilter.SESSION_ORIGINAL_URL);
                try {
                    FacesContext.getCurrentInstance().getExternalContext().redirect(originalUrl);
                    return null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "index";
    }

    public String logout() {
        AgendaLogger.logVerbose("Logging Out");

        FacesSessionHelper.removeSessionVariable(UserService.SESSION_VAR_USERNAME);
        return "index";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getBanned() {
        return isBanned;
    }

    public void setBanned(boolean isBanned) {
        this.isBanned = isBanned;
    }

    public boolean getError() {
        return !FacesContext.getCurrentInstance().getMessageList("loginForm").isEmpty();
    }

}
