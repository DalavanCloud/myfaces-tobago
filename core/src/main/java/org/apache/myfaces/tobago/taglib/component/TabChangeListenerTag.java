package org.apache.myfaces.tobago.taglib.component;

/*
 * Copyright 2002-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.apache.myfaces.tobago.apt.annotation.BodyContent;
import org.apache.myfaces.tobago.apt.annotation.Tag;
import org.apache.myfaces.tobago.apt.annotation.TagAttribute;
import org.apache.myfaces.tobago.component.ComponentUtil;
import org.apache.myfaces.tobago.event.TabChangeListener;
import org.apache.myfaces.tobago.event.TabChangeSource;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.webapp.UIComponentTag;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Register an TabChangedListener instance on the UIComponent
 * associated with the closest parent UIComponent custom action.
 */
@Tag(name = "tabChangeListener", bodyContent = BodyContent.EMPTY)
public class TabChangeListenerTag extends TagSupport {


  /**
   * <p>The fully qualified class name of the {@link TabChangeListener}
   * instance to be created.</p>
   */
  private String type;
  private String binding;
  /**
   * Fully qualified Java class name of a TabChangeListener to be
   * created and registered.
   */
  @TagAttribute(required = true)
  public void setType(String type) {
    this.type = type;
  }

  /**
   * The value binding expression to a TabChangeListener.
   */
  @TagAttribute
  public void setBinding(String binding) {
    this.binding = binding;
  }



  /**
   * <p>Create a new instance of the specified {@link TabChangeListener}
   * class, and register it with the {@link javax.faces.component.UIComponent} instance associated
   * with our most immediately surrounding {@link javax.faces.webapp.UIComponentTag} instance, if
   * the {@link javax.faces.component.UIComponent} instance was created by this execution of the
   * containing JSP page.</p>
   *
   * @throws JspException if a JSP error occurs
   */
  public int doStartTag() throws JspException {

    // Locate our parent UIComponentTag
    UIComponentTag tag =
        UIComponentTag.getParentUIComponentTag(pageContext);
    if (tag == null) {
      // TODO Message resource i18n
      throw new JspException("Not nested in faces tag");
    }

    if (!tag.getCreated()) {
      return (SKIP_BODY);
    }

    UIComponent component = tag.getComponentInstance();
    if (component == null) {
      // TODO Message resource i18n
      throw new JspException("Component Instance is null");
    }
    if (!(component instanceof TabChangeSource)) {
      // TODO Message resource i18n
      throw new JspException("Component "+ component.getClass().getName() + " is not instanceof TabChangeSource");
    }
    TabChangeSource changeSource = (TabChangeSource) component;

    TabChangeListener handler = null;

    if (binding != null && UIComponentTag.isValueReference(binding)) {
      ValueBinding valueBinding = ComponentUtil.createValueBinding(binding);
      if (valueBinding != null) {
        Object obj = valueBinding.getValue(FacesContext.getCurrentInstance());
        if (obj != null && obj instanceof TabChangeListener) {
          handler = (TabChangeListener) obj;
        }
      }
    }

    if (handler == null && type != null) {
      String className;
      // evaluate any VB expression that we were passed
      if (UIComponentTag.isValueReference(type)) {
        ValueBinding typeValueBinding = ComponentUtil.createValueBinding(type);
        className = (String) typeValueBinding.getValue(FacesContext.getCurrentInstance());
      } else {
        className = type;
      }
      handler = createStateChangeListener(className);
      if (handler != null && binding != null) {
        ComponentUtil.setValueForValueBinding(binding, handler);
      }
    }
    if (handler != null) {
      changeSource.addTabChangeListener(handler);
    }
    // TODO else LOG.warn?
    return (SKIP_BODY);
  }


  /**
   * <p>Release references to any acquired resources.
   */
  public void release() {
    this.type = null;
    this.binding = null;
  }

  /**
   * <p>Create and return a new {@link TabChangeListener} to be registered
   * on our surrounding {@link javax.faces.component.UIComponent}.</p>
   *
   * @throws javax.servlet.jsp.JspException if a new instance cannot be created
   */
  protected TabChangeListener createStateChangeListener(String className) throws JspException {
    try {
      Class clazz = getClass().getClassLoader().loadClass(className);
      return ((TabChangeListener) clazz.newInstance());
    } catch (Exception e) {
      throw new JspException(e);
    }
  }
}
