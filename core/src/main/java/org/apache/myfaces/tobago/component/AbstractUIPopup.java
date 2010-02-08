package org.apache.myfaces.tobago.component;

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

import org.apache.myfaces.tobago.ajax.api.AjaxComponent;
import org.apache.myfaces.tobago.compat.FacesUtils;
import org.apache.myfaces.tobago.compat.InvokeOnComponent;
import org.apache.myfaces.tobago.internal.layout.LayoutUtils;
import org.apache.myfaces.tobago.layout.LayoutComponent;
import org.apache.myfaces.tobago.layout.LayoutContainer;
import org.apache.myfaces.tobago.layout.LayoutManager;
import org.apache.myfaces.tobago.layout.Measure;
import org.apache.myfaces.tobago.util.ComponentUtils;

import javax.faces.FacesException;
import javax.faces.component.ContextCallback;
import javax.faces.component.NamingContainer;
import javax.faces.component.UICommand;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public abstract class AbstractUIPopup extends UIPanelBase 
    implements OnComponentPopulated, NamingContainer, AjaxComponent, InvokeOnComponent, 
    DeprecatedDimension, LayoutContainer {

  private boolean activated;

  public void onComponentPopulated(FacesContext facesContext) {
    if (getLayoutManager() == null) {
      setLayoutManager(CreateComponentUtils.createAndInitLayout(
          facesContext, ComponentTypes.GRID_LAYOUT, RendererTypes.GRID_LAYOUT));
    }
  }
  
  public List<LayoutComponent> getComponents() {
    return LayoutUtils.findLayoutChildren(this);
  }

  public void setActivated(boolean activated) {
    this.activated = activated;
  }

  public void processDecodes(FacesContext facesContext) {
    if (isSubmitted()) {
      for (Iterator it = getFacetsAndChildren(); it.hasNext();) {
        UIComponent childOrFacet = (UIComponent) it.next();
        childOrFacet.processDecodes(facesContext);
      }
      try {
        decode(facesContext);
      } catch (RuntimeException e) {
        facesContext.renderResponse();
        throw e;
      }
    }
  }

  public boolean isRendered() {
    if (FacesUtils.hasValueBindingOrValueExpression(this, "rendered")) {
      return (Boolean)
          FacesUtils.getValueFromValueBindingOrValueExpression(FacesContext.getCurrentInstance(), this, "rendered");
    } else {
      return isActivated() || isRedisplay();
    }
  }

  private boolean isSubmitted() {
    String action = ComponentUtils.findPage(getFacesContext(), this).getActionId();
    return action != null && action.startsWith(getClientId(getFacesContext()) + SEPARATOR_CHAR);
  }

  private boolean isRedisplay() {
    if (isSubmitted()) {
      AbstractUIPage page = ComponentUtils.findPage(getFacesContext(), this);
      String action = page.getActionId();
      if (action != null) {
        UIComponent command = page.findComponent(SEPARATOR_CHAR + action);
        if (command != null && command instanceof UICommand) {
          return !(command.getAttributes().get(Attributes.POPUP_CLOSE) != null);
        }
      }
    }
    return false;
  }

  private boolean isActivated() {
    return activated;
  }


  public void processValidators(FacesContext context) {
    if (isSubmitted()) {
      for (Iterator it = getFacetsAndChildren(); it.hasNext();) {
        UIComponent childOrFacet = (UIComponent) it.next();
        childOrFacet.processValidators(context);
      }
      //TODO: check if validation has faild and reset rendered if needed
      if (context.getRenderResponse()) {
        setActivated(true);
      }
    }
  }

  public void processUpdates(FacesContext context) {
    if (isSubmitted()) {
      for (Iterator it = getFacetsAndChildren(); it.hasNext();) {
        UIComponent childOrFacet = (UIComponent) it.next();
        childOrFacet.processUpdates(context);
      }
    }
  }

  public Object saveState(FacesContext context) {
    Object[] saveState = new Object[2];
    saveState[0] = super.saveState(context);
    saveState[1] = activated;
    return saveState;
  }

  public void restoreState(FacesContext context, Object savedState) {
    Object[] values = (Object[]) savedState;
    super.restoreState(context, values[0]);
    activated = (Boolean) values[1];
  }

  public void encodeEnd(FacesContext context) throws IOException {
    super.encodeEnd(context);
    activated = false;
  }

  public void encodeAjax(FacesContext facesContext) throws IOException {
    super.encodeAjax(facesContext);
    activated = false;
  }

  public boolean invokeOnComponent(FacesContext context, String clientId, ContextCallback callback)
      throws FacesException {
    return FacesUtils.invokeOnComponent(context, this, clientId, callback);
  }

  public LayoutManager getLayoutManager() {
    return (LayoutManager) getFacet(Facets.LAYOUT);
  }
  
  public void setLayoutManager(LayoutManager layoutManager) {
    getFacets().put(Facets.LAYOUT, (UILayout) layoutManager);
  }
  
  public abstract Measure getWidth();

  public abstract void setWidth(Measure width);

  public abstract Measure getHeight();

  public abstract void setHeight(Measure height);

  public abstract Measure getTop();

  public abstract void setTop(Measure top);

  public abstract Measure getLeft();

  public abstract void setLeft(Measure left);
}
