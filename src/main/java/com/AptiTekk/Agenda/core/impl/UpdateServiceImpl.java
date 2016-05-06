package com.AptiTekk.Agenda.core.impl;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;

import com.AptiTekk.Agenda.core.UpdateService;
import com.aptitekk.aptiapi.AptiAPI;
import com.aptitekk.aptiapi.AptiAPIListener;
import com.aptitekk.aptiapi.AptiAPIUpdateDetails;
import com.aptitekk.aptiapi.AptiAPIUpdateHandler;
import com.aptitekk.aptiapi.AptiAPIVersioningDetails;

@Startup
@Singleton
@ApplicationScoped
public class UpdateServiceImpl implements UpdateService, AptiAPIListener, AptiAPIUpdateHandler {

  private State state = State.STOPPED;

  private AptiAPI aptiAPI;

  @Override
  @PostConstruct
  public void init() {
    aptiAPI = new AptiAPI(new Versioning(), UPDATES_DIR);
    aptiAPI.addAPIListener(this);
    aptiAPI.setUpdateHandler(this);
    setUpdaterState(State.STARTED);
    System.out.println("Updater Started!");
    aptiAPI.checkForUpdates();
  }

  @Override
  @PreDestroy
  public void terminate() {
    // kill aptiapi
    setUpdaterState(State.SHUTDOWN);
  }

  @Override
  public void pause() {
    // pause aptiapi
    setUpdaterState(State.STOPPED);
  }

  @Override
  public void start() {
    init();
  }

  public State getUpdaterState() {
    return state;
  }

  public void setUpdaterState(State state) {
    this.state = state;
  }

  private class Versioning implements AptiAPIVersioningDetails {

    @Override
    public int getAptiAPIProjectID() {
      return 3;
    }

    @Override
    public int getAptiAPIVersionID() {
      return 0;
    }

    @Override
    public String getProgramName() {
      return "Agenda";
    }

    @Override
    public String getProgramNameWithVersion() {
      return "Agenda V" + getVersionString();
    }

    @Override
    public String getVersionString() {
      return "1.0";
    }

  }

  @Override
  public void aptiApiError(String message) {
    System.out.println("AptiAPI [ERROR]: "+message);
  }

  @Override
  public void aptiApiInfo(String message) {
    System.out.println("AptiAPI [INFO]: "+message);
  }

  @Override
  public void shutdown() {
    
  }

  @Override
  public void onNoNewUpdates() {
    
  }

  @Override
  public boolean onNewAutoUpdate(AptiAPIUpdateDetails updateDetails) {
    return false;
  }

  @Override
  public void onNewUpdate(AptiAPIUpdateDetails updateDetails) {
    
  }

}
