package com.AptiTekk.Agenda.web.controllers;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import com.AptiTekk.Agenda.core.UserService;
import com.AptiTekk.Agenda.core.entity.User;
import com.AptiTekk.Agenda.core.utilities.FacesSessionHelper;
import com.AptiTekk.Agenda.core.utilities.Sha256Helper;

@ManagedBean
@ViewScoped
public class AccountController {

    @Inject
    private UserService userService;

    private User user;

    private String editableFirstName;
    private String editableLastName;
    private String editableEmail;
    private String editablePhoneNumber;
    private String editableLocation;
    private String newPassword;
    private String confirmPassword;

    private boolean editSuccess;

    @PostConstruct
    public void init() {
        String username
                = FacesSessionHelper.getSessionVariableAsString(UserService.SESSION_VAR_USERNAME);
        if (username != null) {
            this.setUser(userService.findByName(username));
        }
        
        resetSettings();
    }

    public void resetSettings() {
        if (this.user != null) {
            setEditableFirstName(user.getFirstName());
            setEditableLastName(user.getLastName());
            setEditableEmail(user.getEmail());
            setEditablePhoneNumber(user.getPhoneNumber());
            setEditableLocation(user.getLocation());

            setNewPassword("");
            setConfirmPassword("");
        }
    }

    public void updateSettings() {
        editSuccess = false;
        if (!getEditableFirstName().isEmpty() && !getEditableFirstName().matches("[A-Za-z'-]+")) {
            FacesContext.getCurrentInstance().addMessage("accountSettingsForm",
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, null, "First Name may only contain A-Z, a-z, apostrophe, and dash."));
        }

        if (!getEditableLastName().isEmpty() && !getEditableLastName().matches("[A-Za-z'-]+")) {
            FacesContext.getCurrentInstance().addMessage("accountSettingsForm",
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, null, "Last Name may only contain A-Z, a-z, apostrophe, and dash."));
        }

        if (!getEditableEmail().isEmpty() && !getEditableEmail().matches("[A-Za-z\\.\\+0-9@]+")) {
            FacesContext.getCurrentInstance().addMessage("accountSettingsForm",
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, null, "Invalid Email Format."));
        }

        if (!getEditablePhoneNumber().isEmpty()
                && !getEditablePhoneNumber().matches("[0-9\\- \\(\\)]+")) {
            FacesContext.getCurrentInstance().addMessage("accountSettingsForm",
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, null, "Invalid Phone Number Format."));
        }

        if (!getNewPassword().isEmpty()) {
            if (!getConfirmPassword().equals(getNewPassword())) {
                FacesContext.getCurrentInstance().addMessage("accountSettingsForm",
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, null, "Passwords do not match."));
                setNewPassword("");
                setConfirmPassword("");
            }
        }

        if (FacesContext.getCurrentInstance().getMessageList("accountSettingsForm").isEmpty()) {
            user.setFirstName(editableFirstName);
            user.setLastName(editableLastName);
            user.setEmail(editableEmail);
            user.setPhoneNumber(getEditablePhoneNumber());
            user.setLocation(editableLocation);

            FacesContext.getCurrentInstance().addMessage("accountSettingsForm",
                    new FacesMessage(FacesMessage.SEVERITY_INFO, null, "Account Settings Updated."));

            if (!getNewPassword().isEmpty()) {
                user.setPassword(Sha256Helper.rawToSha(getNewPassword()));
                FacesContext.getCurrentInstance().addMessage("accountSettingsForm",
                        new FacesMessage(FacesMessage.SEVERITY_INFO, null, "Password Changed Successfully."));
            }

            user = userService.merge(user);

            editSuccess = true;
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        resetSettings();
    }

    public String getEditableFirstName() {
        return editableFirstName;
    }

    public void setEditableFirstName(String editableFirstName) {
        this.editableFirstName = editableFirstName;
    }

    public String getEditableLastName() {
        return editableLastName;
    }

    public void setEditableLastName(String editableLastName) {
        this.editableLastName = editableLastName;
    }

    public String getEditableEmail() {
        return editableEmail;
    }

    public void setEditableEmail(String editableEmail) {
        this.editableEmail = editableEmail;
    }

    public String getEditablePhoneNumber() {
        return editablePhoneNumber;
    }

    public void setEditablePhoneNumber(String editablePhoneNumber) {
        this.editablePhoneNumber = editablePhoneNumber;
    }

    public String getEditableLocation() {
        return editableLocation;
    }

    public void setEditableLocation(String editableLocation) {
        this.editableLocation = editableLocation;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public boolean isEditSuccess() {
        return editSuccess;
    }

}
