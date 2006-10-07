package org.apache.myfaces.tobago.taglib.component;

/*
 * Copyright 2002-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
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
import org.apache.myfaces.tobago.apt.annotation.Tag;
import org.apache.myfaces.tobago.apt.annotation.TagAttribute;
import org.apache.myfaces.tobago.apt.annotation.UIComponentTag;
import org.apache.myfaces.tobago.apt.annotation.UIComponentTagAttribute;

/*
 * Created by IntelliJ IDEA.
 * User: bommel
 * Date: 02.04.2006
 * Time: 15:58:16
 */
@Tag(name = "object", bodyContent = BodyContent.EMPTY)
@UIComponentTag(
    uiComponent = "javax.faces.component.UIOutput",
    rendererType = "Object", isComponentAlreadyDefined = true)
public interface ObjectTagDeclaration extends TobagoTagDeclaration {
  /**
   * URI to object source
   */
  @TagAttribute()
  @UIComponentTagAttribute()
  void setSrc(String src);
}
