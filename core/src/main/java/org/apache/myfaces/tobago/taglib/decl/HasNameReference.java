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
public interface HasNameReference {
  /**
   * Bean property reference to fetch the label for the treeNode's.<br>
   * Example:<br>
   *   a idReference="userObject.name" try's to invoke
   *   <code>&lt;UITreeNode>.getUserObject().getName()<code> to fetch the label.
   *
   */
  @TagAttribute @UIComponentTagAttribute()
  void setNameReference(String name);
}
