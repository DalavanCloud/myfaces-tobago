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
import org.apache.myfaces.tobago.apt.annotation.TagAttribute;
import org.apache.myfaces.tobago.apt.annotation.UIComponentTag;
import org.apache.myfaces.tobago.apt.annotation.UIComponentTagAttribute;
import org.apache.myfaces.tobago.apt.annotation.Facet;
import org.apache.myfaces.tobago.taglib.decl.HasBinding;
import org.apache.myfaces.tobago.taglib.decl.HasDimension;
import org.apache.myfaces.tobago.taglib.decl.HasId;
import org.apache.myfaces.tobago.taglib.decl.HasLabel;
import org.apache.myfaces.tobago.taglib.decl.HasState;

/*
 * Created by IntelliJ IDEA.
 * User: bommel
 * Date: 30.03.2006
 * Time: 21:57:22
 */
/**
 * TODO description of page tag
 */
@Tag(name = "page")
@UIComponentTag(
    uiComponent = "org.apache.myfaces.tobago.component.UIPage",
    rendererType = "Page",
    facets =
        { @Facet(name = "action", description ="Contains an instance of UICommand (tc:command) for an auto-action"),
        @Facet(name = "menuBar", description = "Menubar"),
        @Facet(name="layout", description = "Contains an instance of UILayout")})

public interface PageTagDeclaration extends TobagoBodyTagDeclaration, HasLabel, HasId, HasDimension, HasBinding,
    HasState {
  /**
   * Possible values for doctype are:
   * <dl>
   * <dt>strict</dt><dd>HTML 4.01 Strict DTD</dd>
   * <dt>loose</dt><dd>HTML 4.01 Transitional DTD</dd>
   * <dt>frameset</dt><dd>HTML 4.01 Frameset DTD</dd>
   * </dl>
   * All other values are ignored and no DOCTYPE is set.
   * The default value is 'loose'.
   */
  @TagAttribute
  @UIComponentTagAttribute(defaultValue = "loose")
  void setDoctype(String doctype);

  /**
   * Contains the id of the component wich should have the focus after
   * loading the page.
   * Set to emtpy string for disabling setting of focus.
   * Default (null) enables the "auto focus" feature.
   */
  @TagAttribute
  @UIComponentTagAttribute()
  void setFocusId(String focusId);

  /**
   * Absolute URL to an image or image name to lookup in tobago resource path
   * representing the application. In HTML it is used as a favicon.
   */
  @TagAttribute
  @UIComponentTagAttribute()
  void setApplicationIcon(String icon);

}
