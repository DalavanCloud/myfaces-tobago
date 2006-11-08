package org.apache.myfaces.tobago.taglib.component;

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

import org.apache.myfaces.tobago.apt.annotation.BodyContentDescription;
import org.apache.myfaces.tobago.apt.annotation.Tag;
import org.apache.myfaces.tobago.apt.annotation.UIComponentTag;
import org.apache.myfaces.tobago.taglib.decl.HasBinding;
import org.apache.myfaces.tobago.taglib.decl.HasId;
import org.apache.myfaces.tobago.taglib.decl.HasRenderRange;
import org.apache.myfaces.tobago.taglib.decl.IsDisabled;
import org.apache.myfaces.tobago.taglib.decl.IsInline;
import org.apache.myfaces.tobago.taglib.decl.IsRendered;
import org.apache.myfaces.tobago.taglib.decl.HasConverter;
import org.apache.myfaces.tobago.taglib.decl.IsReadonly;

/*
 * Created: Aug 5, 2005 5:54:37 PM
 * User: bommel
 * $Id: $
 */

/**
 * Render a group of checkboxes.
 */
@Tag(name = "selectManyCheckbox")
@BodyContentDescription(anyTagOf= "(<f:selectItems>|<f:selectItem>|<tc:selectItem>)+ <f:facet>* ")
@UIComponentTag(
    uiComponent = "javax.faces.component.UISelectMany",
    rendererType = "SelectManyCheckbox", isComponentAlreadyDefined = true)
public interface SelectManyCheckboxTagDeclaration extends
    SelectManyTagDeclaration, IsDisabled, HasId,
    IsInline, HasRenderRange, IsRendered, HasBinding, IsReadonly, HasConverter {

}
