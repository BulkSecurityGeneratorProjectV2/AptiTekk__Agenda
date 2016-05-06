package com.AptiTekk.Agenda.core;

import javax.ejb.Local;

import com.AptiTekk.Agenda.core.entity.ReservableType;

@Local
public interface ReservableTypeService extends EntityService<ReservableType> {
  
  /**
   * Finds ReservableType by its name
   * 
   * @param name
   * 
   * @return ReservableType where table.name = name
   */
  ReservableType findByName(String name);
  
}
