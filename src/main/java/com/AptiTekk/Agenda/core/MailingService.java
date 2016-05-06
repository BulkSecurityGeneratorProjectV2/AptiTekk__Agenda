package com.AptiTekk.Agenda.core;

import javax.ejb.Local;

import com.AptiTekk.Agenda.core.utilities.notification.EmailNotification;

/**
 * Interface for managing emailing services
 * 
 * @author kevint
 *
 */
@Local
public interface MailingService {
  
  public boolean send(EmailNotification email);

}
