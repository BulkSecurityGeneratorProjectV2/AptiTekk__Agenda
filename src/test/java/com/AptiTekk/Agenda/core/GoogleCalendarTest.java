package com.AptiTekk.Agenda.core;

import static org.junit.Assert.fail;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Test;

import com.AptiTekk.Agenda.core.testingUtil.TestUtils;

public class GoogleCalendarTest {
  
  @Deployment
  public static Archive<?> createDeployment() {
    return TestUtils.createDeployment();
  }

  @Test
  public void test() {
    //fail("Not yet implemented");
  }

}
