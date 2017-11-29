/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.myfaces.tobago.internal.config;

import org.apache.myfaces.test.base.junit4.AbstractJsfTestCase;
import org.apache.myfaces.test.mock.MockFacesContext;
import org.apache.myfaces.test.mock.MockHttpServletRequest;
import org.apache.myfaces.tobago.component.UIButton;
import org.apache.myfaces.tobago.component.UIIn;
import org.apache.myfaces.tobago.component.UILink;
import org.apache.myfaces.tobago.component.UIOut;
import org.apache.myfaces.tobago.component.UIPanel;
import org.apache.myfaces.tobago.component.UIPopup;
import org.apache.myfaces.tobago.component.UIStyle;
import org.apache.myfaces.tobago.config.TobagoConfig;
import org.apache.myfaces.tobago.context.ThemeImpl;
import org.apache.myfaces.tobago.context.TobagoContext;
import org.apache.myfaces.tobago.internal.mock.faces.MockTheme;
import org.apache.myfaces.tobago.internal.util.MimeTypeUtils;
import org.apache.myfaces.tobago.internal.webapp.HtmlResponseWriter;
import org.junit.After;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Collections;
import java.util.Locale;

/**
 * <p>Abstract JUnit test case base class, which sets up the JavaServer Faces
 * mock object environment for a particular simulated request.
 * </p>
 * <p>
 * This is a port of the class AbstractJsfTestCase from myfaces-test12 to JUnit 4.
 * It also contains Tobago specifics.
 * </p>
 */

public abstract class AbstractTobagoTestBase extends AbstractJsfTestCase {

  private static final Logger LOG = LoggerFactory.getLogger(AbstractTobagoTestBase.class);

  private StringWriter stringWriter;
  private int last = 0;

  /**
   * <p>Set up instance variables required by Tobago test cases.</p>
   */
  @Override
  @Before
  public void setUp() throws Exception {

    super.setUp();

    stringWriter = new StringWriter();
    getFacesContext().setResponseWriter(new HtmlResponseWriter(stringWriter, "", "UTF-8"));

    // Tobago specific extensions

    final TobagoConfigImpl tobagoConfig = TobagoConfigMergingUnitTest.loadAndMerge("tobago-config-for-unit-tests.xml");
    final ThemeImpl theme = new MockTheme("default", "Default Mock Theme", Collections.emptyList());
    final ThemeImpl one = new MockTheme("one", "Mock Theme One", Collections.singletonList(theme));
    tobagoConfig.addAvailableTheme(theme);
    tobagoConfig.addAvailableTheme(one);
    tobagoConfig.resolveThemes();
    tobagoConfig.initDefaultValidatorInfo();
    servletContext.setAttribute(TobagoConfig.TOBAGO_CONFIG, tobagoConfig);
    try {
      MimeTypeUtils.init(servletContext);
    } catch (final IllegalStateException e) {
      // ignoring double call
    }

    final TobagoContext tobagoContext = new TobagoContext();
    tobagoContext.setTheme(one);
    facesContext.getViewRoot().setLocale(Locale.ENGLISH);
    request.setAttribute(TobagoContext.BEAN_NAME, tobagoContext);

    // XXX is there a better way? Get it from Tobagos generated faces-config.xml?
    application.addComponent(UIIn.COMPONENT_TYPE, UIIn.class.getName());
    application.addComponent(UIOut.COMPONENT_TYPE, UIOut.class.getName());
    application.addComponent(UIPanel.COMPONENT_TYPE, UIPanel.class.getName());
    application.addComponent(
        javax.faces.component.UICommand.COMPONENT_TYPE, javax.faces.component.UICommand.class.getName());
    application.addComponent(UILink.COMPONENT_TYPE, UILink.class.getName());
    application.addComponent(UIButton.COMPONENT_TYPE, UIButton.class.getName());
    application.addComponent(UIPopup.COMPONENT_TYPE, UIPopup.class.getName());
    application.addComponent(UIStyle.COMPONENT_TYPE, UIStyle.class.getName());

    tobagoConfig.lock();
  }

  @Override
  @After
  public void tearDown() throws Exception {
    super.tearDown();
  }

  public MockFacesContext getFacesContext() {
    return facesContext;
  }

  public MockHttpServletRequest getRequest() {
    return request;
  }

  public String getLastWritten() throws IOException {
    getFacesContext().getResponseWriter().flush(); // is this needed
    final String full = stringWriter.toString();
    final String result = full.substring(last);
    last = full.length();
    return result;
  }
}
