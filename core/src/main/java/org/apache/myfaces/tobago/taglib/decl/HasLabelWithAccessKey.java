package org.apache.myfaces.tobago.taglib.decl;

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

import org.apache.myfaces.tobago.apt.annotation.TagAttribute;
import org.apache.myfaces.tobago.apt.annotation.UIComponentTagAttribute;

/**
 * $Id$
 */
@Deprecated
public interface HasLabelWithAccessKey {

  /**
   * Deprecated! Use 'label' instead.
   * Text value to display as label.
   * If text contains an underscore the next character overwrites 'accesskey'.
   * @deprecated
   */
  @TagAttribute @UIComponentTagAttribute()
  @Deprecated
  void setLabelWithAccessKey(String key);


  /**
   * Deprecated! Has not longer any function.
   * @deprecated
   */
//  @TagAttribute @UIComponentTagAttribute(type = String.class)
  @TagAttribute @UIComponentTagAttribute(type = { "java.lang.String", "java.lang.Character" })
  @Deprecated
  void setAccessKey(String key);
}
