package com.AptiTekk.Agenda.core;

import javax.ejb.Local;

import com.AptiTekk.Agenda.core.entity.UserGroup;

@Local
public interface UserGroupService extends EntityService<UserGroup> {
  
  public final static String ROOT_GROUP_NAME = "root";
  
  /**
   * Forces a reload of the tree, starting at the root element and propagating downward.
   */
  public void loadTree();
  
  /**
   * Finds Group Entity by its name
   * 
   * @param groupName
   * 
   * @return Group where table.name = groupName
   */
  public UserGroup findByName(String groupName);

}
