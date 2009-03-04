package org.apache.myfaces.tobago.example.facelets;

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
import org.apache.myfaces.tobago.event.TabChangeEvent;
import org.apache.myfaces.tobago.event.TabChangeListener;

public class TabController {

  private static final Log LOG = LogFactory.getLog(TabController.class);

  private TabChangeListener listener;

  public void processTabChange(TabChangeEvent event) {
    LOG.info("Controller: Change index from " + event.getOldTabIndex() + " to " + event.getNewTabIndex());
  }

  public TabChangeListener getListener() {
    LOG.info("get listener");
    return listener;
  }

  public void setListener(TabChangeListener listener) {
    LOG.info("set listener");
    this.listener = listener;
  }
}
