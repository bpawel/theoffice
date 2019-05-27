package com.theoffice.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class AuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

  public void commence(final HttpServletRequest request,
                       final HttpServletResponse response,
                       final AuthenticationException authException) throws IOException, ServletException {

    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    PrintWriter writer = response.getWriter();
    writer.println("HTTP Status 401 : " + "Unauthorised Request");
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    setRealmName("MY_TEST_REALM");
    super.afterPropertiesSet();
  }
}
