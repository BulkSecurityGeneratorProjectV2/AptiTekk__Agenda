package com.AptiTekk.Agenda.core;

import java.io.File;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Local;

@Local
public interface UpdateService {
  
  public static final File UPDATES_DIR = new File(System.getProperty("jboss.server.data.dir"), "Agenda_Updates");
  
  static final long SLEEP_TIME = 10000L;
  
  public enum State {
    STOPPED, STARTED, RUNNING, DOWNLOADING, STORING, SHUTDOWN
  };

  @PostConstruct
  void init();
  
  void pause();
  
  void start();
  
  @PreDestroy
  void terminate();
  
  public State getUpdaterState();
  
  public void setUpdaterState(State state);
  
}
