package com.AptiTekk.Agenda.core;

import javax.ejb.Local;

import com.AptiTekk.Agenda.core.entity.Reservable;
import com.AptiTekk.Agenda.core.entity.ReservableType;
import java.util.List;

@Local
public interface ReservableService extends EntityService<Reservable> {
  
  /**
   * Finds Room Entity by its name
   * 
   * @param roomName
   * 
   * @return Room where table.name = roomName
   */
  Reservable findByName(String roomName);
  
  List<Reservable> getAllByType(ReservableType type);

}
