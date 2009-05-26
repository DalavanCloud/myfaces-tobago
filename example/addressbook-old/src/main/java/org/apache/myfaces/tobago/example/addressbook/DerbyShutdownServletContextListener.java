package org.apache.myfaces.tobago.example.addressbook;

/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.ServletContextListener;
import javax.servlet.ServletContextEvent;
import java.sql.DriverManager;

/*
 * User: bommel
 * Date: Mar 17, 2007
 * Time: 9:18:20 AM
 */
public class DerbyShutdownServletContextListener implements ServletContextListener {

  private static final Log LOG = LogFactory.getLog(DerbyShutdownServletContextListener.class);

  public void contextInitialized(ServletContextEvent servletContextEvent) {

  }

  public void contextDestroyed(ServletContextEvent servletContextEvent) {
    try {
      DriverManager.getConnection("jdbc:derby:target/addressDB;shutdown=true");
    } catch (Exception e) {
      LOG.error("", e); 
    }
  }
}
