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

import static org.apache.myfaces.tobago.TobagoConstants.ATTR_FREQUENCY;
import static org.apache.myfaces.tobago.TobagoConstants.ATTR_IMMEDIATE;
import static org.apache.myfaces.tobago.TobagoConstants.ATTR_UPDATE;
import org.apache.myfaces.tobago.component.ComponentUtil;
import org.apache.myfaces.tobago.component.UIReload;

import javax.faces.component.UIComponent;

/*
 * Date: 28.05.2006
 * Time: 22:23:07
 */
public class ReloadTag extends TobagoTag
    implements ReloadTagDeclaration {

  private String frequency;
  private String update;
  private String immediate;

  public String getComponentType() {
    return UIReload.COMPONENT_TYPE;
  }

  public String getRendererType() {
    return null;
  }

  public void setFrequency(String frequency) {
    this.frequency = frequency;
  }

  public void setUpdate(String update) {
    this.update = update;
  }

  public String getImmediate() {
    return immediate;
  }

  public void setImmediate(String immediate) {
    this.immediate = immediate;
  }

  public void release() {
    super.release();
    frequency = null;
    update = null;
    immediate = null;
  }

  protected void setProperties(UIComponent component) {
    super.setProperties(component);
    ComponentUtil.setIntegerProperty(component, ATTR_FREQUENCY, frequency);
    ComponentUtil.setBooleanProperty(component, ATTR_UPDATE, update);
    ComponentUtil.setBooleanProperty(component, ATTR_IMMEDIATE, immediate);
  }
}
