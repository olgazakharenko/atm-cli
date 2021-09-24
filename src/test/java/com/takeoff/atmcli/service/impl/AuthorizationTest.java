package com.takeoff.atmcli.service.impl;

//import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;


import com.takeoff.atmcli.service.AccountService;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthorizationTest {

  @Autowired
  Authorization authService;
  @Autowired
  AccountService service;

  @Test
  public void successfulAuthorization() {
    assertNotNull(authService.authorise("12", "12"));
    assertEquals("12", service.getCurrent().getUserId());
  }

  @Test
  public void failedAuthWithEmptyUserId() {
    assertNull(authService.authorise("", "12"));
  }

  @Test
  public void failedAuthWithEmptyPin() {
    assertNull(authService.authorise("12", ""));
  }

  @Test
  public void failedAuthWithEmptyCreds() {
    assertNull(authService.authorise("", ""));
  }

  @Test
  public void failedAuthWithncorrectCreds() {
    assertNull(authService.authorise("13", "13"));
  }
}

