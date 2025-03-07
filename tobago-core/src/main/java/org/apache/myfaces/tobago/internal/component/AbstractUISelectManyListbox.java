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

package org.apache.myfaces.tobago.internal.component;

import org.apache.myfaces.tobago.component.LabelLayout;
import org.apache.myfaces.tobago.component.SupportFieldId;
import org.apache.myfaces.tobago.util.ComponentUtils;

import javax.faces.context.FacesContext;

/**
 * {@link org.apache.myfaces.tobago.internal.taglib.component.SelectManyListboxTagDeclaration}
 */
public abstract class AbstractUISelectManyListbox extends AbstractUISelectManyBase implements SupportFieldId {

  @Override
  public String getFieldId(final FacesContext facesContext) {
    if (getLabelLayout() == LabelLayout.skip) {
      return getClientId(facesContext);
    } else {
      return getClientId(facesContext) + ComponentUtils.SUB_SEPARATOR + "field";
    }
  }

  public abstract Integer getSize();
}
