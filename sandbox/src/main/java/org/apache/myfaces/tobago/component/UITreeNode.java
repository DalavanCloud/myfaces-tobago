package org.apache.myfaces.tobago.component;

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

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import static org.apache.myfaces.tobago.TobagoConstants.ATTR_DISABLED;
import static org.apache.myfaces.tobago.TobagoConstants.ATTR_DISABLED_REFERENCE;
import static org.apache.myfaces.tobago.TobagoConstants.ATTR_ID_REFERENCE;
import static org.apache.myfaces.tobago.TobagoConstants.ATTR_NAME;
import static org.apache.myfaces.tobago.TobagoConstants.ATTR_NAME_REFERENCE;
import static org.apache.myfaces.tobago.TobagoConstants.RENDERER_TYPE_TREE_NODE;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import java.util.Map;

public class UITreeNode extends UIInput {

  private static final Log LOG = LogFactory.getLog(UITreeNode.class);

  public static final String COMPONENT_TYPE = "org.apache.myfaces.tobago.TreeNode";

  private static final String SUB_REFERENCE_KEY = "subReferenceKey";

  protected UITreeNode(UIComponent parent, int index) {
    super();
    if (parent instanceof UITreeNode) {
      String parentSubReference = ((UITreeNode) parent).getSubReference();
      if (parentSubReference == null) {
        getAttributes().put(SUB_REFERENCE_KEY, "childAt[" + index + "]");
      } else {
        getAttributes().put(SUB_REFERENCE_KEY, parentSubReference + ".childAt[" + index + "]");
      }
    }
    setRendererType(RENDERER_TYPE_TREE_NODE);
    parent.getChildren().add(this);
    initId();
    initName();
    initDisabled();
  }

  public UITreeNode() {
  }

// ///////////////////////////////////////////// code

  public boolean getRendersChildren() {
    return true;
  }

  public String getSubReference() {
    return (String) getAttributes().get(SUB_REFERENCE_KEY);
  }

  public DefaultMutableTreeNode getTreeNode() {
    return (DefaultMutableTreeNode) getValue();
  }

  public Object getValue() {
    TreeNode value = null;
    UITreeNodes root = findTreeNodesRoot();
    if (root == null) {
      return null;
    }
    String subReference = getSubReference();
    if (LOG.isDebugEnabled()) {
      LOG.debug("root         = '" + root + "'");
      LOG.debug("subReference = '" + subReference + "'");
    }
    TreeNode rootNode = (TreeNode) root.getValue();

    if (LOG.isDebugEnabled()) {
      LOG.debug("rootNode = '" + rootNode + "'");
    }
    if (rootNode != null) {
      try {
        if (subReference == null) {
          value = rootNode;
        } else {
          value = (TreeNode) PropertyUtils.getProperty(rootNode, subReference);
        }
        if (LOG.isDebugEnabled()) {
          LOG.debug("treeNode     = '" + value + "'");
        }
      } catch (Throwable e) {
        LOG.error("subReference = '" + subReference + "'", e);
      }
    }
    return value;
  }

  protected void createTreeNodes() {

    TreeNode node = (TreeNode) getValue();
    if (node != null) {
      int childCount = node.getChildCount();
      for (int i = 0; i < childCount; i++) {
        UITreeNode component = new UITreeNode(this, i);
        component.createTreeNodes();
      }
    }
  }

  private void initName() {
    TreeNode treeNode = (TreeNode) getValue();
    if (treeNode != null) {
      Object name = getReference(treeNode, ATTR_NAME_REFERENCE);
      if (name == null) {
        name = toString();
      }
      getAttributes().put(ATTR_NAME, name.toString());
    }
  }

  private void initDisabled() {
    TreeNode treeNode = (TreeNode) getValue();
    if (treeNode != null) {
      Object disabled = getReference(treeNode, ATTR_DISABLED_REFERENCE);
      if (!(disabled instanceof Boolean)) {
        if (disabled instanceof String) {
          disabled = Boolean.valueOf((String) disabled);
        } else {
          disabled = false;
        }
      }
      getAttributes().put(ATTR_DISABLED, disabled);
    }
  }

  private void initId() {
    TreeNode treeNode = (TreeNode) getValue();
    if (treeNode != null) {
      Object id = getReference(treeNode, ATTR_ID_REFERENCE);
      if (!(id instanceof String)) {
        id = "node" + Integer.toString(System.identityHashCode(treeNode));
      }
      setId((String) id);
    }
  }

  @SuppressWarnings("unchecked")
  private Object getReference(TreeNode treeNode, String key) {
    Object value = null;
    UITreeNodes root = findTreeNodesRoot();
    String reference = (String) root.getAttributes().get(key);
    if (reference != null) {
      try {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Map requestMap = facesContext.getExternalContext().getRequestMap();
        String ref = "#{tobagoTreeNode." + reference + "}";
        ValueBinding vb = facesContext.getApplication().createValueBinding(ref);
        requestMap.put("tobagoTreeNode", treeNode);
        value = vb.getValue(facesContext);
        requestMap.remove("tobagoTreeNode");
      } catch (Exception e) {
        LOG.warn(
            "Can't find " + key + " over ref='" + reference
                + "' treeNode='" + treeNode + "! " + treeNode.getClass().getName(), e);
      }
    }
    return value;
  }

  public UITree findTreeRoot() {
    UIComponent ancestor = getParent();
    while (ancestor != null
        && (ancestor instanceof UITreeNode || ancestor instanceof UITreeNodes)) {
      ancestor = ancestor.getParent();
    }
    if (ancestor instanceof UITree) {
      return (UITree) ancestor;
    }
    return null;
  }

  public UITreeNodes findTreeNodesRoot() {
    UIComponent ancestor = getParent();
    while (ancestor != null && ancestor instanceof UITreeNode) {
      ancestor = ancestor.getParent();
    }
    if (ancestor instanceof UITreeNodes) {
      return (UITreeNodes) ancestor;
    }
    return null;
  }

  public void updateModel(FacesContext facesContext) {
    // nothig to update for treeNode's
  }

  protected Object checkValue(Object currentValue) {

    if (currentValue == null) {
      LOG.error("currentValue is null");
      currentValue = emergencyValue();
    }

    if (!(currentValue instanceof MutableTreeNode)) {
      LOG.error("currentValue is not valid: '" + currentValue + "'");
      LOG.error(
          "currentValue is not of type '"
          + MutableTreeNode.class.getName() + "': '"
          + currentValue.getClass().getName() + "'");
      currentValue = emergencyValue();
    }

    return currentValue;
  }

  protected Object emergencyValue() {
    return new DefaultMutableTreeNode("Default");
  }

}

