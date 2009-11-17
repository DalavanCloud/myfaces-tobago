package org.apache.myfaces.tobago.taglib.component;

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

import org.apache.myfaces.tobago.apt.annotation.BodyContentDescription;
import org.apache.myfaces.tobago.apt.annotation.DynamicExpression;
import org.apache.myfaces.tobago.apt.annotation.Tag;
import org.apache.myfaces.tobago.apt.annotation.TagAttribute;
import org.apache.myfaces.tobago.apt.annotation.UIComponentTag;
import org.apache.myfaces.tobago.apt.annotation.UIComponentTagAttribute;
import org.apache.myfaces.tobago.component.AbstractUITabGroup;
import org.apache.myfaces.tobago.component.RendererTypes;
import org.apache.myfaces.tobago.taglib.decl.HasAction;
import org.apache.myfaces.tobago.taglib.decl.HasActionListener;
import org.apache.myfaces.tobago.taglib.decl.HasIdBindingAndRendered;
import org.apache.myfaces.tobago.taglib.decl.HasMarkup;
import org.apache.myfaces.tobago.taglib.decl.IsGridLayoutComponent;
import org.apache.myfaces.tobago.taglib.decl.IsGridLayoutContainer;
import org.apache.myfaces.tobago.taglib.decl.IsImmediateCommand;

/**
 * Renders a tabpanel.
 */
@Tag(name = "tabGroup")
@BodyContentDescription(anyTagOf = "(<tc:tab>* ")
@UIComponentTag(
    uiComponent = "org.apache.myfaces.tobago.component.UITabGroup",
    uiComponentBaseClass = "org.apache.myfaces.tobago.component.AbstractUITabGroup",
    rendererType = RendererTypes.TAB_GROUP,
    interfaces = { "org.apache.myfaces.tobago.ajax.api.AjaxComponent", "javax.faces.component.ActionSource"},
//    componentFamily = "org.apache.myfaces.tobago.TabGroup",
    allowedChildComponenents = "org.apache.myfaces.tobago.Tab")

public interface TabGroupTagDeclaration
    extends HasIdBindingAndRendered, IsImmediateCommand, HasAction, HasActionListener, HasMarkup,
    IsGridLayoutComponent, IsGridLayoutContainer {
  /**
   * Deprecated! Use 'switchType' instead.
   * Flag indicating that tab switching is done by server request.
   * @deprecated
   */
  @TagAttribute
  @UIComponentTagAttribute(type = "java.lang.Boolean", defaultValue = "Boolean.FALSE")
  @Deprecated
  void setServerside(String serverside);

  /**
   * Indicating how tab switching should be done.
   * <p/>
   * Possible values are:
   * <dl>
   *   <dt>client</dt>
   *   <dd>Tab switching is done on client, no server Request.</dd>
   *   <dt>reloadPage</dt>
   *   <dd>Tab switching is done by server request. Full page is reloaded.</dd>
   *   <dt>reloadTab</dt>
   *   <dd>Tab switching is done by server request. Only the Tab is reloaded.</dd>
   * </dl>
   * @param switchType Sets the switching type.
   */
  @TagAttribute
  @UIComponentTagAttribute(type = "java.lang.String",
      allowedValues = {AbstractUITabGroup.SWITCH_TYPE_CLIENT, AbstractUITabGroup.SWITCH_TYPE_RELOAD_PAGE,
              AbstractUITabGroup.SWITCH_TYPE_RELOAD_TAB},
      defaultValue = AbstractUITabGroup.SWITCH_TYPE_CLIENT)
  void setSwitchType(String switchType);

  /**
   *
   * <strong>ValueBindingExpression</strong> pointing to a Integer to save the
   * component's selected Tab.
   *
   */
  @TagAttribute @UIComponentTagAttribute(type = "java.lang.Integer", defaultValue = "0")
  void setSelectedIndex(String selectedIndex);

  @TagAttribute @UIComponentTagAttribute(type = "java.lang.Integer", defaultValue = "0")
  void setRenderedIndex(String renderedIndex);

  /**
   *
   * <strong>ValueBindingExpression</strong> pointing to a Integer to save the
   * component's selected Tab.
   */
  @TagAttribute
  @UIComponentTagAttribute(type = "java.lang.Integer")
  @Deprecated()
  void setState(String state);

  @TagAttribute  
  @UIComponentTagAttribute(
      type = {},
      expression = DynamicExpression.METHOD_BINDING_REQUIRED,
      methodSignature = "org.apache.myfaces.tobago.event.TabChangeEvent")
  void setTabChangeListener(String listener);
}
