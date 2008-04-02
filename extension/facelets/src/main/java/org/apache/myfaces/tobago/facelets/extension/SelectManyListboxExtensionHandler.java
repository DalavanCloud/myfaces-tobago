package org.apache.myfaces.tobago.facelets.extension;

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

import com.sun.facelets.tag.jsf.ComponentConfig;
import org.apache.myfaces.tobago.TobagoConstants;
import org.apache.myfaces.tobago.component.UISelectManyListbox;

/*
 * Date: Aug 9, 2007
 * Time: 8:37:36 PM
 */
public class SelectManyListboxExtensionHandler extends TobagoLabelExtensionHandler {

  public SelectManyListboxExtensionHandler(ComponentConfig config) {
    super(config);
  }

  protected String getSubComponentType() {
    return UISelectManyListbox.COMPONENT_TYPE;
  }

  protected String getSubRendererType() {
    return TobagoConstants.RENDERER_TYPE_SELECT_MANY_LISTBOX;
  }

  protected String getRows() {
    return "1*";
  }
}
