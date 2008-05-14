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
import org.apache.myfaces.tobago.apt.annotation.Tag;
import org.apache.myfaces.tobago.apt.annotation.TagAttribute;
import org.apache.myfaces.tobago.apt.annotation.UIComponentTag;
import org.apache.myfaces.tobago.apt.annotation.UIComponentTagAttribute;
import org.apache.myfaces.tobago.apt.annotation.BodyContent;
import org.apache.myfaces.tobago.taglib.decl.HasIdBindingAndRendered;

/**
 * Add a style tag.
 * Collected bodyContent is rendered as content into a style tag.
 */
@Tag(name = "style", bodyContent = BodyContent.TAGDEPENDENT)
@BodyContentDescription(contentType = "css")
@UIComponentTag(
    uiComponent = "org.apache.myfaces.tobago.component.UIStyle",
    componentType = "org.apache.myfaces.tobago.Style",
    componentFamily = "org.apache.myfaces.tobago.Style",
    rendererType = "Style",
    isTransparentForLayout = true,
    allowedChildComponenents = "NONE")
public interface StyleTagDeclaration extends HasIdBindingAndRendered {

  /**
   * Name of the stylesheet file to add to page.
   */
  @TagAttribute()
  @UIComponentTagAttribute()
  void setFile(String file);

  /**
   * stylesheet to add to page.
   */
  @TagAttribute(bodyContent = true)
  @UIComponentTagAttribute()
  void setStyle(String style);

}
