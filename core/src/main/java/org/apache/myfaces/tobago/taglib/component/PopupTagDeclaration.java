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

import org.apache.myfaces.tobago.apt.annotation.Tag;
import org.apache.myfaces.tobago.apt.annotation.TagAttribute;
import org.apache.myfaces.tobago.apt.annotation.UIComponentTag;
import org.apache.myfaces.tobago.apt.annotation.UIComponentTagAttribute;
import org.apache.myfaces.tobago.component.RendererTypes;
import org.apache.myfaces.tobago.taglib.decl.HasId;
import org.apache.myfaces.tobago.taglib.decl.IsGridLayoutComponentWithDeprecatedDimension;
import org.apache.myfaces.tobago.taglib.decl.IsRendered;

/**
 * Renders a popup panel.
 */
@Tag(name = "popup")
@UIComponentTag(
    uiComponent = "org.apache.myfaces.tobago.component.UIPopup",
    uiComponentBaseClass = "org.apache.myfaces.tobago.component.AbstractUIPopup",
    interfaces = "org.apache.myfaces.tobago.ajax.api.AjaxComponent",
    rendererType = RendererTypes.POPUP)
public interface PopupTagDeclaration extends HasId, IsRendered, IsGridLayoutComponentWithDeprecatedDimension {

  @TagAttribute
  @UIComponentTagAttribute(type = {"java.lang.Boolean"}, defaultValue = "true")
  void setModal(String modal);

}
