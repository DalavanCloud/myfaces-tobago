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

import org.apache.myfaces.tobago.component.Attributes;
import org.apache.myfaces.tobago.internal.component.AbstractUIGridLayout;
import org.apache.myfaces.tobago.internal.layout.Cell;
import org.apache.myfaces.tobago.internal.layout.Grid;
import org.apache.myfaces.tobago.internal.layout.OriginCell;
import org.apache.myfaces.tobago.internal.util.JsonUtils;
import org.apache.myfaces.tobago.internal.util.RenderUtils;
import org.apache.myfaces.tobago.layout.LayoutToken;
import org.apache.myfaces.tobago.layout.LayoutTokens;
import org.apache.myfaces.tobago.layout.Measure;
import org.apache.myfaces.tobago.renderkit.RendererBase;
import org.apache.myfaces.tobago.renderkit.css.TobagoClass;
import org.apache.myfaces.tobago.renderkit.html.DataAttributes;
import org.apache.myfaces.tobago.renderkit.html.HtmlAttributes;
import org.apache.myfaces.tobago.renderkit.html.HtmlElements;
import org.apache.myfaces.tobago.renderkit.html.HtmlRoleValues;
import org.apache.myfaces.tobago.util.ComponentUtils;
import org.apache.myfaces.tobago.webapp.TobagoResponseWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import java.io.IOException;

public class GridLayoutRenderer extends RendererBase {

  private static final Logger LOG = LoggerFactory.getLogger(GridLayoutRenderer.class);

  // todo: may use: http://www.w3.org/TR/css3-grid-layout/ (currently only IE 10 and higher)

  @Override
  public void encodeBegin(final FacesContext facesContext, final UIComponent component) throws IOException {
    final AbstractUIGridLayout gridLayout = (AbstractUIGridLayout) component;
    final TobagoResponseWriter writer = getResponseWriter(facesContext);

    writer.startElement(HtmlElements.TABLE);
    writer.writeAttribute(HtmlAttributes.ROLE, HtmlRoleValues.PRESENTATION.toString(), false);
    writer.writeClassAttribute(
        TobagoClass.GRID_LAYOUT,
        null,
        TobagoClass.GRID_LAYOUT.createMarkup(ComponentUtils.updateMarkup(gridLayout, gridLayout.getMarkup())));

    final StringBuilder builder = new StringBuilder();

    builder.append("{\"columns\":");
    JsonUtils.encode(gridLayout.getGrid().getColumns(), builder);
    builder.append(",");

    builder.append("\"rows\":");
    JsonUtils.encode(gridLayout.getGrid().getRows(), builder);
    builder.append("}");

    writer.writeAttribute(DataAttributes.LAYOUT, builder.toString(), true);

    writer.startElement(HtmlElements.COLGROUP);
    final LayoutTokens columns = gridLayout.getGrid().getColumns();
    for (LayoutToken column : columns.getTokens()) {
        writer.startElement(HtmlElements.COL);
        writer.endElement(HtmlElements.COL);
    }
    writer.endElement(HtmlElements.COLGROUP);
    writer.startElement(HtmlElements.TBODY);
  }

  @Override
  public boolean getRendersChildren() {
    return true;
  }

  @Override
  public void encodeChildren(final FacesContext facesContext, final UIComponent component) throws IOException {

    final AbstractUIGridLayout gridLayout = (AbstractUIGridLayout) component;
    final TobagoResponseWriter writer = getResponseWriter(facesContext);
    final Grid grid = gridLayout.getGrid();
    final int columns = grid.getColumnCount();
    final int rows = grid.getRowCount();

    for (int i = 0; i < rows; i++) {
      writer.startElement(HtmlElements.TR);
      for (int j = 0; j < columns; j++) {
        final Cell cell = grid.getCell(j, i);
        if (cell instanceof OriginCell) {
          writer.startElement(HtmlElements.TD);
          final int columnSpan = cell.getColumnSpan();
          if (columnSpan > 1) {
            writer.writeAttribute(HtmlAttributes.COLSPAN, columnSpan);
          }
          final int rowSpan = cell.getRowSpan();
          if (rowSpan > 1) {
            writer.writeAttribute(HtmlAttributes.ROWSPAN, rowSpan);
          }

          final UIComponent element = cell.getComponent();
          StringBuilder builder = new StringBuilder();
          builder.append("{");
          final Measure width = Measure.valueOf(ComponentUtils.getAttribute(element, Attributes.width));
          if (width != null) {
            builder.append("\"width\":");
            builder.append(width.serialize());
            builder.append(",");
          }
          final Measure height = Measure.valueOf(ComponentUtils.getAttribute(element, Attributes.height));
          if (height != null) {
            builder.append("\"height\":");
            builder.append(height.serialize());
            builder.append(",");
          }
          if (builder.length() > 1) {
            builder.deleteCharAt(builder.length() - 1);
          }
          // todo: add more attributes
          builder.append("}");

          if (builder.length() > 2) { // empty is not need to render
            ComponentUtils.putDataAttribute(element, "tobago-layout", builder.toString());
          }
          RenderUtils.encode(facesContext, element);

          writer.endElement(HtmlElements.TD);
        }
      }
      writer.endElement(HtmlElements.TR);
    }
  }

  @Override
  public void encodeEnd(final FacesContext facesContext, final UIComponent component) throws IOException {
    final TobagoResponseWriter writer = getResponseWriter(facesContext);

    writer.endElement(HtmlElements.TBODY);
    writer.endElement(HtmlElements.TABLE);
  }
}
