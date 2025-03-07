/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.myfaces.tobago.internal.renderkit.renderer;

import org.apache.myfaces.tobago.context.Markup;
import org.apache.myfaces.tobago.internal.component.AbstractUIData;
import org.apache.myfaces.tobago.internal.component.AbstractUITree;
import org.apache.myfaces.tobago.internal.component.AbstractUITreeListbox;
import org.apache.myfaces.tobago.internal.component.AbstractUITreeMenu;
import org.apache.myfaces.tobago.internal.component.AbstractUITreeNode;
import org.apache.myfaces.tobago.internal.component.AbstractUITreeNodeBase;
import org.apache.myfaces.tobago.internal.component.AbstractUITreeSelect;
import org.apache.myfaces.tobago.internal.util.HtmlRendererUtils;
import org.apache.myfaces.tobago.internal.util.JsonUtils;
import org.apache.myfaces.tobago.model.Selectable;
import org.apache.myfaces.tobago.renderkit.RendererBase;
import org.apache.myfaces.tobago.renderkit.css.BootstrapClass;
import org.apache.myfaces.tobago.renderkit.css.TobagoClass;
import org.apache.myfaces.tobago.renderkit.html.DataAttributes;
import org.apache.myfaces.tobago.renderkit.html.HtmlAttributes;
import org.apache.myfaces.tobago.renderkit.html.HtmlElements;
import org.apache.myfaces.tobago.util.ComponentUtils;
import org.apache.myfaces.tobago.webapp.TobagoResponseWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.component.UIComponent;
import javax.faces.component.UINamingContainer;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.Map;

public class TreeNodeRenderer extends RendererBase {

  private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  @Override
  public void decode(final FacesContext facesContext, final UIComponent component) {

    final AbstractUITreeNode node = (AbstractUITreeNode) component;

    super.decode(facesContext, node);

    if (ComponentUtils.isOutputOnly(node)) {
      return;
    }

    final AbstractUIData data = ComponentUtils.findAncestor(node, AbstractUIData.class);
    if (data instanceof AbstractUITreeListbox) {
      final String clientId = data.getClientId(facesContext);
      final String nodeStateId = node.nodeStateId(facesContext);
      final Map<String, String> requestParameterMap = facesContext.getExternalContext().getRequestParameterMap();
      final String id = node.getClientId(facesContext);
      final boolean folder = node.isFolder();

      // expand state
      if (folder) {
        final boolean expanded = Boolean.parseBoolean(requestParameterMap.get(id + "-expanded"));
/* XXX check
      if (node.isExpanded() != expanded) {
        new TreeExpansionEvent(node, node.isExpanded(), expanded).queue();
      }
*/
      }

      // select
      if (data.getSelectable() != Selectable.none) { // selection
        final String selected = requestParameterMap.get(clientId + AbstractUITree.SELECT_STATE);
        final String searchString = ";" + node.getClientId(facesContext) + ";";
        final AbstractUITreeSelect treeSelect = ComponentUtils.findDescendant(node, AbstractUITreeSelect.class);
        if (treeSelect != null) {
          treeSelect.setSubmittedValue(selected.contains(searchString));
        }
      }

      // marked
      final String marked =
          requestParameterMap.get(clientId + ComponentUtils.SUB_SEPARATOR + AbstractUITree.SUFFIX_MARKED);
      if (marked != null) {
        final String searchString = clientId + UINamingContainer.getSeparatorChar(facesContext) + nodeStateId;
        final boolean markedValue = marked.equals(searchString);
/* XXX check
      if (node.isMarked() != markedValue) {
        new TreeMarkedEvent(node, node.isMarked(), markedValue).queue();
      }
*/
      } else {
        LOG.warn("This log message is help clarifying the occurrence of this else case.");
      }
    }
  }

  @Override
  public void encodeBegin(final FacesContext facesContext, final UIComponent component) throws IOException {

    final AbstractUITreeNodeBase node = (AbstractUITreeNodeBase) component;
    final AbstractUIData data = ComponentUtils.findAncestor(node, AbstractUIData.class);

    final boolean dataRendersRowContainer = data.isRendersRowContainer();
    final String clientId = node.getClientId(facesContext);
    final String parentId = data.getRowParentClientId();
    final boolean visible = data.isRowVisible();
    final boolean folder = node.isFolder();
    Markup markup = Markup.NULL;
    if (data instanceof AbstractUITree && data.getSelectedState().isSelected(node.getPath())) {
      markup = markup.add(Markup.SELECTED);
    }
    if (folder) {
      markup = markup.add(Markup.FOLDER);
      if (data.getExpandedState().isExpanded(node.getPath())) {
        markup = markup.add(Markup.EXPANDED);
      }
    }

    final TobagoResponseWriter writer = getResponseWriter(facesContext);

    if (data instanceof AbstractUITreeListbox) {
      writer.startElement(HtmlElements.OPTION);
      // todo: define where to store the selection of a tree, node.getValue() seems not to be a god place.
      //        writer.writeAttribute(HtmlAttributes.value, node.getValue().toString(), true); // XXX converter?
      writer.writeAttribute(HtmlAttributes.VALUE, clientId, true);
      writer.writeIdAttribute(clientId);
      writer.writeAttribute(DataAttributes.MARKUP, JsonUtils.encode(markup), false);
      writer.writeAttribute(HtmlAttributes.SELECTED, folder);
    } else {
      writer.startElement(HtmlElements.DIV);

      // div id
      writer.writeIdAttribute(clientId);
      writer.writeAttribute(DataAttributes.MARKUP, JsonUtils.encode(markup), false);

      // In the case of a sheet, we need not hiding the node, because the whole TR will be hidden.
      final boolean hidden = !dataRendersRowContainer && !visible;

      writer.writeClassAttribute(
          TobagoClass.TREE_NODE,
          TobagoClass.TREE_NODE.createMarkup(markup),
          hidden ? BootstrapClass.D_NONE : null,
          node.getCustomClass());
      HtmlRendererUtils.writeDataAttributes(facesContext, writer, node);
      if (parentId != null) {
        writer.writeAttribute(DataAttributes.TREE_PARENT, parentId, false);
      }
      writer.writeAttribute(DataAttributes.LEVEL, data.isShowRoot() ? node.getLevel() : node.getLevel() - 1);
    }
  }

  @Override
  public void encodeEnd(final FacesContext facesContext, final UIComponent component) throws IOException {
    final AbstractUITreeNodeBase node = (AbstractUITreeNodeBase) component;
    final AbstractUIData data = ComponentUtils.findAncestor(node, AbstractUIData.class);
    final int level = node.getLevel();
    final boolean folder = node.isFolder();
    final boolean expanded = folder && data.getExpandedState().isExpanded(node.getPath()) || level == 0;
    final boolean isMenu = data instanceof AbstractUITreeMenu;

    final TobagoResponseWriter writer = getResponseWriter(facesContext);

    if (data instanceof AbstractUITreeListbox) {
      if (folder) {
        writer.writeText(" \u2192"); // this is an right arrow →
      }
      writer.endElement(HtmlElements.OPTION);
    } else {
      if (isMenu && folder) {
        encodeIcon(facesContext, writer, expanded, node);
      }
      writer.endElement(HtmlElements.DIV);
    }
  }

  private void encodeIcon(
      final FacesContext facesContext, final TobagoResponseWriter writer, final boolean expanded,
      final AbstractUITreeNodeBase node)
      throws IOException {
//    LOG.warn("todo: font-awesome icons");
/* XXX
    final String srcOpen = ResourceManagerUtils.getImage(facesContext, "image/treeMenuOpen");
    final String srcClose = ResourceManagerUtils.getImage(facesContext, "image/treeMenuClose");
    final String src = expanded ? srcOpen : srcClose;
    writer.startElement(HtmlElements.IMG);
    writer.writeClassAttribute(Classes.create(node, "toggle"));
    writer.writeAttribute(HtmlAttributes.SRC, src, false);
    writer.writeAttribute(DataAttributes.OPEN, srcOpen, false);
    writer.writeAttribute(DataAttributes.SRC_CLOSED, srcClose, false);
    writer.writeAttribute(HtmlAttributes.ALT, "", false);
    writer.endElement(HtmlElements.IMG);
*/
  }
}
