package org.apache.myfaces.tobago.renderkit;

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
import org.apache.myfaces.tobago.component.ComponentUtil;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

public abstract class CommandRendererBase extends LayoutableRendererBase {

  private static final Log LOG = LogFactory.getLog(CommandRendererBase.class);

  public void decode(FacesContext facesContext, UIComponent component) {

    if (ComponentUtil.isOutputOnly(component)) {
      return;
    }
    String actionId = ComponentUtil.findPage(facesContext, component).getActionId();
    String clientId = component.getClientId(facesContext);
    if (LOG.isDebugEnabled()) {
      LOG.debug("actionId = '" + actionId + "'");
      LOG.debug("clientId = '" + clientId + "'");
    }
    if (actionId != null && actionId.equals(clientId)) {

      if (LOG.isDebugEnabled()) {
        LOG.debug("queueEvent = '" + actionId + "'");
      }
      component.queueEvent(new ActionEvent(component));
    }
  }

}
