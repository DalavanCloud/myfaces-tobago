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

import org.apache.myfaces.tobago.apt.annotation.BodyContent;
import org.apache.myfaces.tobago.apt.annotation.DynamicExpression;
import org.apache.myfaces.tobago.apt.annotation.Tag;
import org.apache.myfaces.tobago.apt.annotation.TagAttribute;
import org.apache.myfaces.tobago.apt.annotation.UIComponentTag;
import org.apache.myfaces.tobago.apt.annotation.UIComponentTagAttribute;
import org.apache.myfaces.tobago.component.RendererType;
import org.apache.myfaces.tobago.taglib.decl.HasIdBindingAndRendered;

/*
 * Created: Aug 6, 2005 3:24:33 PM
 * User: bommel
 * $Id$
 */
/**
 * Renders a calendar.
 */
@Tag(name = "calendar", bodyContent = BodyContent.EMPTY)
@UIComponentTag(
    uiComponent = "org.apache.myfaces.tobago.component.UICalendar",
    uiComponentBaseClass = "javax.faces.component.UIOutput",
    rendererType = RendererType.CALENDAR)
public interface CalendarTagDeclaration extends HasIdBindingAndRendered {

  /**
   * The current value of this component.
   */
  @TagAttribute
  @UIComponentTagAttribute(type = {"java.util.Calendar", "java.util.Date"}, 
      expression = DynamicExpression.VALUE_BINDING_REQUIRED)
  void setValue(String value);
}
