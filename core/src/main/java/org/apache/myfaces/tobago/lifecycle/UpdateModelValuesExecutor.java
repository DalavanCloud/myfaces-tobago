package org.apache.myfaces.tobago.lifecycle;

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

import static javax.faces.event.PhaseId.UPDATE_MODEL_VALUES;

import org.apache.myfaces.tobago.ajax.api.AjaxUtils;
import org.apache.myfaces.tobago.component.UIViewRoot;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import java.util.List;


/**
 * Implements the lifecycle as described in Spec. 1.0 PFD Chapter 2
 * Update model values phase (JSF Spec 2.2.4)
 */
class UpdateModelValuesExecutor implements PhaseExecutor {
  public boolean execute(FacesContext facesContext) {
    List<UIComponent> ajaxComponents = AjaxUtils.getAjaxComponents(facesContext);
    if (ajaxComponents != null) {
      for (UIComponent ajaxComponent : ajaxComponents) {
        ajaxComponent.processUpdates(facesContext);
      }
      UIViewRoot viewRoot = ((UIViewRoot) facesContext.getViewRoot());
      viewRoot.broadcastEventsForPhase(facesContext, UPDATE_MODEL_VALUES);
    } else {
      facesContext.getViewRoot().processUpdates(facesContext);
    }
    return false;
  }

  public PhaseId getPhase() {
    return UPDATE_MODEL_VALUES;
  }
}
