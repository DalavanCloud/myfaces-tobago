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
package org.apache.myfaces.tobago.example.demo.clientConfig;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

/**
 * User: weber
 * Date: Jan 26, 2005
 * Time: 11:28:20 AM
 */
public class ClientConfigPhaseListener implements PhaseListener {

  public static String[] BEAN_NAMES
      = {"clientConfigController", "clientConfigController2"};

  public void afterPhase(PhaseEvent event) {
  }

  public void beforePhase(PhaseEvent event) {
    FacesContext facesContext = FacesContext.getCurrentInstance();
    for (int i = 0; i < BEAN_NAMES.length; i++) {
      String beanName = BEAN_NAMES[i];
      ClientConfigController controller = ClientConfigController
          .getCurrentInstance(facesContext, beanName);

      if (controller != null) {
        controller.loadFromClientProperties();
      }
    }
  }

  public PhaseId getPhaseId() {
    return PhaseId.RENDER_RESPONSE;
  }
}
