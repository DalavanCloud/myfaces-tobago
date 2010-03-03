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
import org.apache.myfaces.tobago.apt.annotation.DynamicExpression;
import org.apache.myfaces.tobago.apt.annotation.Facet;
import org.apache.myfaces.tobago.apt.annotation.Tag;
import org.apache.myfaces.tobago.apt.annotation.TagAttribute;
import org.apache.myfaces.tobago.apt.annotation.UIComponentTag;
import org.apache.myfaces.tobago.apt.annotation.UIComponentTagAttribute;
import org.apache.myfaces.tobago.component.Facets;
import org.apache.myfaces.tobago.component.RendererTypes;
import org.apache.myfaces.tobago.taglib.decl.AbstractCommandTagDeclaration;
import org.apache.myfaces.tobago.taglib.decl.HasIdBindingAndRendered;
import org.apache.myfaces.tobago.taglib.decl.HasImage;
import org.apache.myfaces.tobago.taglib.decl.HasLabel;
import org.apache.myfaces.tobago.taglib.decl.HasMarkup;
import org.apache.myfaces.tobago.taglib.decl.HasTip;
import org.apache.myfaces.tobago.taglib.decl.HasValue;
import org.apache.myfaces.tobago.taglib.decl.IsDisabled;

/**
 * Creates a tree node. This component represents a single node inside a tree structure.
 * For interating over tree node use tc:treeData
 */
@SuppressWarnings("ALL")
@Tag(name = "treeNode")
@BodyContentDescription(anyTagOf = "<tc:treeNode>* <tc:treeData>*")
@UIComponentTag(
    uiComponent = "org.apache.myfaces.tobago.component.UITreeNode",
    uiComponentBaseClass = "org.apache.myfaces.tobago.component.AbstractUITreeNode",
    rendererType = RendererTypes.TREE_NODE,
    allowedChildComponenents = {
        "org.apache.myfaces.tobago.TreeNode",
        "org.apache.myfaces.tobago.TreeData"
    },
    facets = {
        @Facet(name = Facets.ADDENDUM, description = "Displays an additional component to a node.")})
public interface TreeNodeTagDeclaration
    extends HasIdBindingAndRendered, HasLabel, HasValue, HasMarkup, HasTip, HasImage, IsDisabled,
    AbstractCommandTagDeclaration {

  /**
   * Flag indicating if the subnodes are to be displayed.
   */
  @TagAttribute()
  @UIComponentTagAttribute(type = "java.lang.Boolean")
  void setExpanded(String expanded);

  /**
   * Flag indicating if the node is marked, and should be displayed in a special way.
   */
  @TagAttribute()
  @UIComponentTagAttribute(type = "java.lang.Boolean", defaultValue = "Boolean.FALSE")
  void setMarked(String marked);

  /**
   * Method binding representing a expansionListener method that ....
   */
  @TagAttribute
  @UIComponentTagAttribute(type = {},
      expression = DynamicExpression.METHOD_BINDING_REQUIRED,
      methodSignature = "org.apache.myfaces.tobago.event.TreeExpansionEvent")
  void setTreeExpansionListener(String treeExpansionListener);
}
