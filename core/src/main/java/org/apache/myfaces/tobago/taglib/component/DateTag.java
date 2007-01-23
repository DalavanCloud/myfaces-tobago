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
 * Created on: 15.02.2002, 17:01:56
 * $Id$
 */

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import static org.apache.myfaces.tobago.TobagoConstants.RENDERER_TYPE_DATE;
import org.apache.myfaces.tobago.component.UIDateInput;
import org.apache.myfaces.tobago.component.ComponentUtil;

import javax.faces.component.UIComponent;

public class DateTag extends InputTag implements DateTagDeclaration {

  private static final Log LOG = LogFactory.getLog(DateTag.class);

  private String markup;

  public String getComponentType() {
    return UIDateInput.COMPONENT_TYPE;
  }

  public String getRendererType() {
    return RENDERER_TYPE_DATE;
  }

  protected void setProperties(UIComponent component) {
    if (getLabel() != null) {
      LOG.warn("the label attribute is deprecated in tc:date, please use tx:date instead.");
    }
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

