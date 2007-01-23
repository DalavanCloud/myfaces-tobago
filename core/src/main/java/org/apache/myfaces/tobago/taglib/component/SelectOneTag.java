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

/*
 * Created on: 19.02.2002, 17:01:56
 * $Id$
 */

import org.apache.myfaces.tobago.component.UISelectOne;
import org.apache.myfaces.tobago.component.ComponentUtil;

import javax.faces.component.UIComponent;

public class SelectOneTag extends InputTag implements SelectOneTagDeclaration {

  private String markup;

  public String getComponentType() {
    return UISelectOne.COMPONENT_TYPE;
  }

  protected void setProperties(UIComponent component) {
    ComponentUtil.setMarkup(component, markup);
    super.setProperties(component);
  }

  public void setMarkup(String markup) {
    this.markup = markup;
  }

  public void release() {
    super.release();
    markup = null;
  }
}
