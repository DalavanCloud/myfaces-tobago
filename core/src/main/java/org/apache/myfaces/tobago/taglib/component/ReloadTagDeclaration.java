package org.apache.myfaces.tobago.taglib.component;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
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

import org.apache.myfaces.tobago.apt.annotation.Tag;
import org.apache.myfaces.tobago.apt.annotation.TagAttribute;
import org.apache.myfaces.tobago.apt.annotation.UIComponentTag;
import org.apache.myfaces.tobago.apt.annotation.UIComponentTagAttribute;
import org.apache.myfaces.tobago.taglib.decl.HasIdBindingAndRendered;

/*
 * Created by IntelliJ IDEA.
 * User: bommel
 * Date: 28.05.2006
 * Time: 22:31:38
 * To change this template use File | Settings | File Templates.
 */

/**
 * Update the parent component
 */

@Tag(name = "reload")
@UIComponentTag(
    uiComponent = "org.apache.myfaces.tobago.component.UIReload")
public interface ReloadTagDeclaration extends TobagoTagDeclaration, HasIdBindingAndRendered {


  /**
   * Time in milliseconds after which the parent component is automaticaly reloaded.
   */
  @TagAttribute
  @UIComponentTagAttribute(type = "java.lang.Integer", defaultValue = "5000")
  void setFrequency(String frequency);

  /**
   * Is update required.
   */
  @TagAttribute
  @UIComponentTagAttribute(type = "java.lang.Boolean", defaultValue = "true")
  void setUpdate(String update);
}
