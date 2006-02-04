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

/*
 * Created on: 15.02.2002, 16:19:49
 * $Id$
 */

import static org.apache.myfaces.tobago.TobagoConstants.ATTR_REQUIRED;
import static org.apache.myfaces.tobago.TobagoConstants.ATTR_VALUE;
import org.apache.myfaces.tobago.component.ComponentUtil;

import javax.faces.component.UIComponent;

public abstract class BeanTag extends TobagoTag implements BeanTagDeclaration {

  private String converter;
  private String value;
  private String required;

  @Override
  protected void setProperties(UIComponent component) {
    super.setProperties(component);
    ComponentUtil.setConverter(component, converter);
    ComponentUtil.setBooleanProperty(component, ATTR_REQUIRED, required);
    ComponentUtil.setStringProperty(component, ATTR_VALUE, value);
  }

  @Override
  public void release() {
    super.release();
    this.converter = null;
    this.value = null;
    this.required = null;
  }

  public String getConverter() {
    return converter;
  }

  public void setConverter(String converter) {
    this.converter = converter;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getRequired() {
    return required;
  }

  public void setRequired(String required) {
    this.required = required;
  }
}
