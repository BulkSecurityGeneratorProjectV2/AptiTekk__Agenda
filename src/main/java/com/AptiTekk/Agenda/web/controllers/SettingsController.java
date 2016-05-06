package com.AptiTekk.Agenda.web.controllers;

import java.util.Arrays;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class SettingsController {

  public enum SettingsPage {
    PROPERTIES("Properties", "properties.xhtml"), USERS("Users", "users.xhtml"), GROUPS("Groups",
        "groups.xhtml"), RESERVABLES("Reservables", "reservables.xhtml");

    private String name;
    private String fileName;

    private SettingsPage(String name, String fileName) {
      this.name = name;
      this.fileName = fileName;
    }

    public String getName() {
      return name;
    }

    public String getFileName() {
      return fileName;
    }
  }

  private List<SettingsPage> pages = Arrays.asList(SettingsPage.values());
  private SettingsPage currentPage = SettingsPage.values()[0];

  public List<SettingsPage> getPages() {
    return pages;
  }

  public SettingsPage getCurrentPage() {
    return currentPage;
  }

  public void setCurrentPage(SettingsPage settingsPage) {
    this.currentPage = settingsPage;
  }

}
