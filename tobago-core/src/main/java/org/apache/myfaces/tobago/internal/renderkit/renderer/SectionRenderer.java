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

import org.apache.myfaces.tobago.component.Facets;
import org.apache.myfaces.tobago.internal.component.AbstractUISection;
import org.apache.myfaces.tobago.internal.util.HtmlRendererUtils;
import org.apache.myfaces.tobago.internal.util.RenderUtils;
import org.apache.myfaces.tobago.model.CollapseMode;
import org.apache.myfaces.tobago.renderkit.css.CssItem;
import org.apache.myfaces.tobago.renderkit.css.TobagoClass;
import org.apache.myfaces.tobago.renderkit.html.HtmlElements;
import org.apache.myfaces.tobago.util.ComponentUtils;
import org.apache.myfaces.tobago.webapp.TobagoResponseWriter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SectionRenderer extends PanelRendererBase {

  @Override
  public void encodeBegin(final FacesContext facesContext, final UIComponent component) throws IOException {

    final AbstractUISection section = (AbstractUISection) component;
    final TobagoResponseWriter writer = getResponseWriter(facesContext);
    final String clientId = section.getClientId(facesContext);
    final boolean collapsed = section.isCollapsed();

    writer.startElement(HtmlElements.DIV);
    writer.writeIdAttribute(clientId);

    // TODO: optimize class attribute writing
    final List<CssItem> classAttributes = new ArrayList<CssItem>();
    classAttributes.add(TobagoClass.SECTION);
    if (section.getMarkup() != null) {
      classAttributes.addAll(Arrays.asList(TobagoClass.SECTION.createMarkup(section.getMarkup())));
    }
    classAttributes.add(collapsed ? TobagoClass.COLLAPSED : null);
    classAttributes.add(section.getCustomClass());
    writer.writeClassAttribute(null, null, classAttributes.toArray(new CssItem[classAttributes.size()]));
    HtmlRendererUtils.writeDataAttributes(facesContext, writer, section);

    String label = section.getLabelToRender();
    final HtmlElements tag;
    switch (section.getLevel()) {
      case 1:
        tag = HtmlElements.H1;
        break;
      case 2:
        tag = HtmlElements.H2;
        break;
      case 3:
        tag = HtmlElements.H3;
        break;
      case 4:
        tag = HtmlElements.H4;
        break;
      case 5:
        tag = HtmlElements.H5;
        break;
      default:
        tag = HtmlElements.H6;
    }

    if (section.getCollapsedMode() != CollapseMode.none) {
      encodeHidden(writer, clientId, collapsed);
    }

    writer.startElement(HtmlElements.DIV);
    writer.writeClassAttribute(TobagoClass.SECTION__HEADER);
    writer.startElement(tag);
    final String image = section.getImage();
    HtmlRendererUtils.encodeIconWithLabel(writer, image, label);
    writer.endElement(tag);

    final UIComponent bar = ComponentUtils.getFacet(section, Facets.bar);
    if (bar != null) {
      RenderUtils.encode(facesContext, bar);
    }

    writer.endElement(HtmlElements.DIV);

    writer.startElement(HtmlElements.DIV);
    writer.writeClassAttribute(TobagoClass.SECTION__CONTENT);
  }

  @Override
  public void encodeEnd(final FacesContext facesContext, final UIComponent component) throws IOException {

    final TobagoResponseWriter writer = getResponseWriter(facesContext);
    writer.endElement(HtmlElements.DIV);
    writer.endElement(HtmlElements.DIV);
  }
}
