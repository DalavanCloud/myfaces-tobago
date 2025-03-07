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

package org.apache.myfaces.tobago.internal.component;

import org.apache.myfaces.tobago.util.FacesVersion;

import javax.faces.component.UIComponentBase;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.ListenerFor;
import javax.faces.event.PostAddToViewEvent;
import javax.faces.event.PreRenderViewEvent;

/**
 * {@link org.apache.myfaces.tobago.internal.taglib.component.ScriptTagDeclaration}
 *
 * @since 3.0.0
 */
@ListenerFor(systemEventClass = PostAddToViewEvent.class)
public abstract class AbstractUIScript extends UIComponentBase {

  @Override
  public void processEvent(final ComponentSystemEvent event) {

    super.processEvent(event);

    if (event instanceof PreRenderViewEvent) {
      addComponentResource();
    } else if (event instanceof PostAddToViewEvent) {
      if (FacesVersion.supports21() || !FacesVersion.isMyfaces()) {
        // MyFaces core is removing the component resources in head if the view will be recreated before rendering.
        // The view will be recreated because of expressions. For example  expressins in the ui:include src attribute
        // The PostAddToViewEvent will not be broadcasted in this case again.
        // A subscription to the PreRenderViewEvent avoids this problem
        // NOTE: PreRenderViewEvent can not used in myfaces prior 2.0.3 using PostAddToView for all myfaces 2.0 versions
        getFacesContext().getViewRoot().subscribeToEvent(PreRenderViewEvent.class, this);
      } else {
        addComponentResource();
      }
    }
  }

  private void addComponentResource() {
    final FacesContext facesContext = getFacesContext();
    final UIViewRoot root = facesContext.getViewRoot();
    root.addComponentResource(facesContext, this);
  }

  public abstract String getFile();
  public abstract void setFile(String file);

}
