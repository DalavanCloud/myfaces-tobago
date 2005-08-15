/*
 * Copyright 2002-2005 atanion GmbH.
 * 
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 * 
 *        http://www.apache.org/licenses/LICENSE-2.0
 * 
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
/*
 * Created 25.08.2004 10:54:04.
 * $Id: MockServletContext.java,v 1.1.1.1 2004/08/27 13:02:11 lofwyr Exp $
 */
package org.apache.myfaces.tobago.mock.servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.Servlet;
import java.util.Enumeration;
import java.util.Set;
import java.net.URL;
import java.net.MalformedURLException;
import java.io.InputStream;

public class MockServletContext implements ServletContext {

  public Set getResourcePaths(String reference) {
    return null;
  }

  public Object getAttribute(String s) {
    return null;
  }

  public Enumeration getAttributeNames() {
    return null;
  }

  public ServletContext getContext(String s) {
    return null;
  }

  public String getInitParameter(String s) {
    return null;
  }

  public Enumeration getInitParameterNames() {
    return null;
  }

  public int getMajorVersion() {
    return 0;
  }

  public String getMimeType(String s) {
    return null;
  }

  public int getMinorVersion() {
    return 0;
  }

  public RequestDispatcher getNamedDispatcher(String s) {
    return null;
  }

  public String getRealPath(String s) {
    return null;
  }

  public RequestDispatcher getRequestDispatcher(String s) {
    return null;
  }

  public URL getResource(String s) throws MalformedURLException {
    return null;
  }

  public InputStream getResourceAsStream(String s) {
    return null;
  }

  public Set getResourcePaths() {
    return null;
  }

  public String getServerInfo() {
    return null;
  }

  public Servlet getServlet(String s) throws ServletException {
    return null;
  }

  public String getServletContextName() {
    return null;
  }

  public Enumeration getServletNames() {
    return null;
  }

  public Enumeration getServlets() {
    return null;
  }

  public void log(Exception e, String s) {
  }

  public void log(String s) {
  }

  public void log(String s, Throwable throwable) {
  }

  public void removeAttribute(String s) {
  }

  public void setAttribute(String s, Object o) {
  }
}
