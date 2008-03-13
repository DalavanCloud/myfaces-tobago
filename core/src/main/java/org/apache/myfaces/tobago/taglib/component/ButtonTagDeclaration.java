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

import org.apache.myfaces.tobago.apt.annotation.BodyContentDescription;
import org.apache.myfaces.tobago.apt.annotation.Facet;
import org.apache.myfaces.tobago.apt.annotation.Tag;
import org.apache.myfaces.tobago.apt.annotation.UIComponentTag;
import org.apache.myfaces.tobago.taglib.decl.HasCommandType;
import org.apache.myfaces.tobago.taglib.decl.HasDeprecatedWidth;
import org.apache.myfaces.tobago.taglib.decl.HasIdBindingAndRendered;
import org.apache.myfaces.tobago.taglib.decl.HasImage;
import org.apache.myfaces.tobago.taglib.decl.HasLabelAndAccessKey;
import org.apache.myfaces.tobago.taglib.decl.HasMarkup;
import org.apache.myfaces.tobago.taglib.decl.HasTabIndex;
import org.apache.myfaces.tobago.taglib.decl.HasTarget;
import org.apache.myfaces.tobago.taglib.decl.HasTip;
import org.apache.myfaces.tobago.taglib.decl.IsDefaultCommand;
import org.apache.myfaces.tobago.taglib.decl.IsDisabled;
import org.apache.myfaces.tobago.taglib.decl.IsInline;

/*
 * Date: 10.02.2006
 * Time: 22:00:03
 */

/**
 * Renders a button element.
 */
@Tag(name = "button", tagExtraInfoClassName = "org.apache.myfaces.tobago.taglib.component.CommandTagExtraInfo")
@BodyContentDescription(anyTagOf = "facestag")
@UIComponentTag(
    uiComponent = "org.apache.myfaces.tobago.component.UIButtonCommand",
    uiComponentBaseClass = "org.apache.myfaces.tobago.component.UICommand",
    rendererType = "Button",
    allowedChildComponenents = "NONE",
    facets = {
        @Facet(name="confirmation", description = "Contains a UIOutput instance with the confirmation message.",
                allowedChildComponenents = "org.apache.myfaces.tobago.Output"),
        @Facet(name="popup", description = "Contains a UIPopup instance.",
                allowedChildComponenents = "org.apache.myfaces.tobago.Popup")})
public interface ButtonTagDeclaration extends AbstractCommandTagDeclaration,
    HasIdBindingAndRendered, HasLabelAndAccessKey, HasImage,
    IsDisabled, HasCommandType, IsDefaultCommand, HasDeprecatedWidth, HasTip,
    IsInline, HasTarget, HasMarkup, HasTabIndex {
}
