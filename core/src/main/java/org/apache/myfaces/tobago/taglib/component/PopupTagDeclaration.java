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
import org.apache.myfaces.tobago.taglib.decl.HasDimension;
import org.apache.myfaces.tobago.taglib.decl.HasId;
import org.apache.myfaces.tobago.taglib.decl.IsRendered;

/*
 * Date: 02.04.2006
 * Time: 16:11:02
 */
/**
 * Renders a popup panel.
 */
@Tag(name = "popup")
@UIComponentTag(
    uiComponent = "org.apache.myfaces.tobago.component.UIPopup",
    uiComponentBaseClass = "org.apache.myfaces.tobago.component.AbstractUIPopup",
    interfaces = "org.apache.myfaces.tobago.ajax.api.AjaxComponent",
    rendererType = "Popup")
public interface PopupTagDeclaration extends HasId, IsRendered, HasDimension {

  @TagAttribute
  @UIComponentTagAttribute(type = "java.lang.Integer")
  void setLeft(String left);

  @TagAttribute
  @UIComponentTagAttribute(type = "java.lang.Integer")
  void setTop(String top);

  @TagAttribute
  @UIComponentTagAttribute(type = {"java.lang.Boolean"}, defaultValue = "true")
  void setModal(String modal);

}
