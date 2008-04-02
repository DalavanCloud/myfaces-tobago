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
import org.apache.myfaces.tobago.apt.annotation.UIComponentTag;
import org.apache.myfaces.tobago.taglib.decl.HasBooleanValue;
import org.apache.myfaces.tobago.taglib.decl.HasCommandType;
import org.apache.myfaces.tobago.taglib.decl.HasIdBindingAndRendered;
import org.apache.myfaces.tobago.taglib.decl.HasImage;
import org.apache.myfaces.tobago.taglib.decl.HasLabelAndAccessKey;
import org.apache.myfaces.tobago.taglib.decl.HasTip;
import org.apache.myfaces.tobago.taglib.decl.IsDisabled;
import org.apache.myfaces.tobago.taglib.decl.AbstractCommandTagDeclaration;

/*
 * Date: 31.03.2006
 * Time: 22:35:28
 */

/**
 * Renders a selectable command button within a toolbar.
 */
@Tag(name = "toolBarCheck", tagExtraInfoClassName = "org.apache.myfaces.tobago.taglib.component.CommandTagExtraInfo")
@UIComponentTag(
    uiComponent = "org.apache.myfaces.tobago.component.UIToolBarCheck",
    uiComponentBaseClass = "org.apache.myfaces.tobago.component.UISelectBooleanCommand",
    rendererType = "MenuCommand",
    allowedChildComponenents = "NONE")
public interface ToolBarSelectBooleanTagDeclaration extends AbstractCommandTagDeclaration,
    HasIdBindingAndRendered, HasLabelAndAccessKey,
    HasCommandType, HasImage, IsDisabled, HasBooleanValue,
    HasTip {
}
