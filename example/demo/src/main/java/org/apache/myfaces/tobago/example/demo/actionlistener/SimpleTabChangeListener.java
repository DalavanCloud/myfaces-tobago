package org.apache.myfaces.tobago.example.demo.actionlistener;

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

/**
 * User: weber
 * Date: 13.12.2004
 * Time: 16:55:44
 */
public class SimpleTabChangeListener implements TabChangeListener {

  private static final Log LOG = LogFactory.getLog(SimpleTabChangeListener.class);

  public void processTabChange(TabChangeEvent tabChangeEvent) {
    LOG.error("TabState has Changed: from tabIndex " + tabChangeEvent.getOldState()
        + " to tabIndex " + tabChangeEvent.getNewState());

  }
}
