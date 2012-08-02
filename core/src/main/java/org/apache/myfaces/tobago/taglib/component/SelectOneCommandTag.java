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

package org.apache.myfaces.tobago.taglib.component;

import static org.apache.myfaces.tobago.TobagoConstants.ATTR_VALUE;
import static org.apache.myfaces.tobago.TobagoConstants.RENDERER_TYPE_MENUCOMMAND;
import org.apache.myfaces.tobago.component.ComponentUtil;
import org.apache.myfaces.tobago.component.UISelectOneCommand;

import javax.faces.component.UIComponent;

/*
 * User: weber
 * Date: Apr 26, 2005
 * Time: 3:03:44 PM
 */
public class SelectOneCommandTag extends AbstractCommandTag {

  private String value;

  public String getComponentType() {
    return UISelectOneCommand.COMPONENT_TYPE;
  }

  public void release() {
    super.release();
    value = null;

  }

  protected void setProperties(UIComponent component) {
    super.setProperties(component);
    component.setRendererType(RENDERER_TYPE_MENUCOMMAND);
    ComponentUtil.setStringProperty(component, ATTR_VALUE, value);
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }
}
