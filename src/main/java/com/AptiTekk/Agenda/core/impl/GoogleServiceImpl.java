/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.AptiTekk.Agenda.core.impl;

import static com.AptiTekk.Agenda.core.GoogleService.CALLBACK_PATH;
import com.AptiTekk.Agenda.core.Properties;
import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import java.io.IOException;
import java.util.Collections;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import com.AptiTekk.Agenda.core.GoogleService;
import com.AptiTekk.Agenda.core.utilities.AgendaLogger;
import com.google.api.client.util.store.DataStoreFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import java.io.File;

/**
 *
 * @author kevint
 */
@Stateless
public class GoogleServiceImpl implements GoogleService {

    @Inject
    Properties properties;

    HttpTransport httpTransport;
    JsonFactory jsonFactory;

    String clientID;
    String clientSecret;

    @PostConstruct
    public void init() {
        httpTransport = new NetHttpTransport();
        jsonFactory = new JacksonFactory();

        clientID = properties.get(CLIENT_ID_PROPERTY.getKey());
        clientSecret = properties.get(CLIENT_SECRET_PROPERTY.getKey());
    }

    @Override
    public Credential authorize(String userId) throws IOException {
        return initializeCalendarFlow().loadCredential(userId);
    }

    @Override
    public Calendar getCalendarService() throws IOException {
        Credential cred = authorize(CALENDAR_USER_ID);
        if (cred == null) { //reauthorize
            AgendaLogger.logMessage("Google Calendar Credential was null");
            return null;
        } else {
            return new com.google.api.services.calendar.Calendar.Builder(
                    httpTransport, jsonFactory, cred)
                    .setApplicationName(APP_NAME)
                    .build();
        }
    }

    @Override
    public AuthorizationCodeFlow initializeCalendarFlow() throws IOException {
        return new GoogleAuthorizationCodeFlow.Builder(
                new NetHttpTransport(), JacksonFactory.getDefaultInstance(),
                clientID, clientSecret,
                Collections.singleton(CalendarScopes.CALENDAR))
                .setDataStoreFactory(getDataStoreFactory())
                .setAccessType("offline")
                .setApprovalPrompt("force").build();
    }

    @Override
    public String getCalendarRedirectUri(HttpServletRequest req) throws ServletException, IOException {
        GenericUrl url = new GenericUrl(req.getRequestURL().toString());
        url.setRawPath(req.getContextPath() + CALLBACK_PATH);
        return url.build();
    }

    @Override
    public String getCalendarUserId(HttpServletRequest req) throws ServletException, IOException {
        return CALENDAR_USER_ID;
    }

    @Override
    public DataStoreFactory getDataStoreFactory() throws IOException {
//        return new JdoDataStoreFactory(persistenceManagerFactory);
        return new FileDataStoreFactory(new File(System.getProperty("jboss.server.data.dir"), "Agenda_GoogleCredentials"));
    }
}
