package com.AptiTekk.Agenda.core;

import com.AptiTekk.Agenda.core.entity.AppProperty;
import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.store.DataStoreFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import java.io.File;
import java.io.IOException;
import javax.ejb.Local;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Local
public interface GoogleService {
    
    public static final String APP_NAME = "Agenda";
    
    public static final String CALENDAR_USER_ID = "CALENDAR";
    
    public static final String INITAL_PATH = "/CalendarServlet";
    public static final String CALLBACK_PATH = "/CalendarCallbackServlet";
    
    public static final AppProperty ACCESS_TOKEN_PROPERTY = new AppProperty("agenda.google.calendar.accessToken","");
    public static final AppProperty CALENDAR_ID_PROPERTY = new AppProperty("agenda.google.calendar.calendarID", "primary");
    
    public static final AppProperty CLIENT_ID_PROPERTY = new AppProperty("agenda.google.clientID", "457451349060-uo39jdu88fbm4ib0r7l6jrba3k3uflem.apps.googleusercontent.com");
    public static final AppProperty CLIENT_SECRET_PROPERTY = new AppProperty("agenda.google.clientSecret", "YNNu0ompxxjoZRZVRvK3GPhY");

    public Credential authorize(String userId) throws IOException;

    public Calendar getCalendarService() throws IOException;
    
    AuthorizationCodeFlow initializeCalendarFlow() throws IOException;
    
    String getCalendarRedirectUri(HttpServletRequest req) throws ServletException, IOException;
    
    String getCalendarUserId(HttpServletRequest req) throws ServletException, IOException;
    
    public DataStoreFactory getDataStoreFactory() throws IOException;
    
//    return new FileDataStoreFactory(new File(System.getProperty("jboss.server.data.dir"), "Agenda_GoogleCredentials"));

}
