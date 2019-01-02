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

package org.apache.myfaces.tobago.internal.renderkit;

import org.apache.myfaces.tobago.component.Attributes;
import org.apache.myfaces.tobago.component.UIForm;
import org.apache.myfaces.tobago.internal.component.AbstractUICommand;
import org.apache.myfaces.tobago.internal.renderkit.renderer.TobagoClientBehaviorRenderer;
import org.apache.myfaces.tobago.internal.util.StringUtils;
import org.apache.myfaces.tobago.util.ComponentUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import java.lang.invoke.MethodHandles;

/**
 * @since 2.0.0
 */
public class Command {

  private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  /**
   * The action is only needed if the action is not the HTML element itself.
   */
  private String action;
  private Boolean transition;
  private String target;
  private String execute;
  private String render;
  private String focus;
  private String confirmation;
  private Integer delay;
  private Collapse collapse;
  private Boolean omit;

  public Command() {
  }

  public Command(
      final String action, final Boolean transition, final String target, final String execute,
      final String render, final String focus, final String confirmation, final Integer delay,
      final Collapse collapse, final Boolean omit) {
    this.action = action;
    this.transition = transition;
    this.target = target;
    setExecute(execute);
    setRender(render);
    this.focus = focus;
    this.confirmation = confirmation;
    this.delay = delay;
    this.collapse = collapse;
    this.omit = omit;
  }

  public Command(final FacesContext facesContext, final AbstractUICommand command) {
    this(
        null,
        command.isTransition(),
        command.getTarget(),
        null,
        null,
        null,
        ComponentUtils.getConfirmation(command),
        null,
        TobagoClientBehaviorRenderer.createCollapsible(facesContext, command),
        command.isOmit());
  }

  public Command(final FacesContext facesContext, final UIComponent facetComponent, final String focusId) {
    final UIComponent component;
    if (facetComponent instanceof UIForm && facetComponent.getChildCount() == 1) {
      LOG.warn("Please don't use a form, but a command with immediate=true instead.");
      component = facetComponent.getChildren().get(0);
    } else {
      component = facetComponent;
    }
    this.action = component.getClientId(facesContext);
    // transition == true is the default
    if (!ComponentUtils.getBooleanAttribute(component, Attributes.transition)) {
      this.transition = Boolean.FALSE;
    }
    final String targetAttribute = ComponentUtils.getStringAttribute(component, Attributes.target);
    if (targetAttribute != null) {
      this.target = targetAttribute;
    }
    if (focusId != null) {
      this.focus = focusId;
    }

    final int delayAttribute = ComponentUtils.getIntAttribute(component, Attributes.delay);
    if (delayAttribute > 0) {
      this.delay = delayAttribute;
    }
    // omit == false is the default
    if (ComponentUtils.getBooleanAttribute(component, Attributes.omit)) {
      this.omit = Boolean.TRUE;
    }
  }

  public String getAction() {
    return action;
  }

  public void setAction(final String action) {
    this.action = action;
  }

  public Boolean getTransition() {
    return transition;
  }

  public void setTransition(final Boolean transition) {
    this.transition = transition;
  }

  public String getTarget() {
    return target;
  }

  public void setTarget(final String target) {
    this.target = target;
  }

  public String getExecute() {
    return execute;
  }

  public void setExecute(final String execute) {
    if (StringUtils.isNotBlank(execute)) {
      this.execute = execute;
    }
  }

  public String getRender() {
    return render;
  }

  public void setRender(final String render) {
    if (StringUtils.isNotBlank(render)) {
      this.render = render;
    }
  }

  public String getFocus() {
    return focus;
  }

  public void setFocus(final String focus) {
    this.focus = focus;
  }

  public String getConfirmation() {
    return confirmation;
  }

  public void setConfirmation(final String confirmation) {
    this.confirmation = confirmation;
  }

  public Integer getDelay() {
    return delay;
  }

  public void setDelay(final Integer delay) {
    this.delay = delay;
  }

  public Collapse getCollapse() {
    return collapse;
  }

  public void setCollapse(final Collapse collapse) {
    this.collapse = collapse;
  }

  public Boolean getOmit() {
    return omit;
  }

  public void setOmit(final Boolean omit) {
    this.omit = omit;
  }

  public void merge(final Command c) {

    //XXX TBD: check if this is okay.
    // we need at least this for "execute" and "render" in the moment.

    if (action == null) {
      action = c.action;
    }
    if (transition == null) {
      transition = c.transition;
    }
    if (target == null) {
      target = c.target;
    }
    if (execute != null) {
      if (c.execute != null) {
        execute += " " + c.execute;
      }
    } else {
      execute = c.execute;
    }
    if (render != null) {
      if (c.render != null) {
        render += " " + c.render;
      }
    } else {
      render = c.render;
    }
    if (focus == null) {
      focus = c.focus;
    }
    if (confirmation == null) {
      confirmation = c.confirmation;
    }
    if (delay == null) {
      delay = c.delay;
    }
    if (collapse == null) {
      collapse = c.collapse;
    }
    if (omit == null) {
      omit = c.omit;
    }
  }
}
