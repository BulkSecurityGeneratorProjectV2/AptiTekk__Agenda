package com.AptiTekk.Agenda.core;

import javax.ejb.Local;

@Local
public interface StartupService {

  public void checkForRootGroup();
  
  public void checkForAdminUser();
  
  public void checkForReservableTypes();
  
  public void persistServiceDefaultProperties();
}
