/*
 * Copyright (c) 2001 Atanion GmbH, Germany
 * All rights reserved.
 * $Id$
 */
package com.atanion.tobago.taglib.component;

import com.atanion.tobago.el.ConstantMethodBinding;
import com.atanion.tobago.component.ComponentUtil;

import javax.faces.component.UICommand;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.MethodBinding;
import javax.faces.application.Application;

public abstract class CommandTag extends TobagoTag {
// ----------------------------------------------------------------- attributes

  private String disabled;

  private String action;
  private String actionListener;

  private String type;

  private String immediate;

// ----------------------------------------------------------- business methods

  public String getComponentType() {
    return UICommand.COMPONENT_TYPE;
  }

  protected void setProperties(UIComponent component) {
    UICommand command = (UICommand) component;
    super.setProperties(component);

   ComponentUtil.setBooleanProperty(component, ATTR_DISABLED, disabled, getIterationHelper());
   ComponentUtil.setStringProperty(component, ATTR_TYPE, type, getIterationHelper());
//   ComponentUtil.setBooleanProperty(component, ATTR_DEFAULT_COMMAND, defaultCommand, getIterationHelper());
   ComponentUtil.setBooleanProperty(component, ATTR_IMMEDIATE, immediate, getIterationHelper());



    String commandType;
    final FacesContext facesContext = FacesContext.getCurrentInstance();
    final Application application = facesContext.getApplication();
    if (type != null && isValueReference(type)) {
         commandType = (String)
             application.createValueBinding(type).getValue(facesContext);
    }
    else {
      commandType = type;
    }
    if (commandType != null &&
        (commandType.equals(COMMAND_TYPE_NAVIGATE)
        || commandType.equals(COMMAND_TYPE_RESET)
        || commandType.equals(COMMAND_TYPE_SCRIPT))) {
     ComponentUtil.setStringProperty(component, ATTR_ACTION_STRING, action, getIterationHelper());
    }
    else {
      if (action != null) {
        if (isValueReference(action)) {
          MethodBinding binding = application.createMethodBinding(action, null);
          ((UICommand)component).setAction(binding);
        } else {
          ((UICommand)component).setAction(new ConstantMethodBinding(action));
        }
      }
    }



    if (actionListener != null) {
      if (isValueReference(actionListener)) {
        Class arguments[] = {javax.faces.event.ActionEvent.class};
        MethodBinding binding
            = application.createMethodBinding(actionListener, arguments);
        command.setActionListener(binding);
      } else {
        throw new IllegalArgumentException(
            "Must be a valueReference (actionListener): " + actionListener);
      }
    }
  }

  public void release() {
    super.release();
    action= null;
    actionListener= null;
    type = null;
    disabled = null;
    immediate = null;
  }

// ------------------------------------------------------------ getter + setter

  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
  }

  public String getActionListener() {
    return actionListener;
  }

  public void setActionListener(String actionListener) {
    this.actionListener = actionListener;
  }


  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getImmediate() {
    return immediate;
  }

  public void setImmediate(String immediate) {
    this.immediate = immediate;
  }

  public void setDisabled(String disabled) {
    this.disabled = disabled;
  }
}

