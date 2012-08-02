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

package org.apache.myfaces.tobago.renderkit.html.speyside.standard.tag;

import org.apache.myfaces.tobago.component.Attributes;
import org.apache.myfaces.tobago.component.Facets;
import org.apache.myfaces.tobago.component.RendererTypes;
import org.apache.myfaces.tobago.component.UIBox;
import org.apache.myfaces.tobago.component.UIMenuBar;
import org.apache.myfaces.tobago.layout.Measure;
import org.apache.myfaces.tobago.renderkit.BoxRendererBase;
import org.apache.myfaces.tobago.renderkit.css.Classes;
import org.apache.myfaces.tobago.renderkit.css.Style;
import org.apache.myfaces.tobago.renderkit.html.HtmlElements;
import org.apache.myfaces.tobago.renderkit.html.util.HtmlRendererUtils;
import org.apache.myfaces.tobago.renderkit.util.RenderUtils;
import org.apache.myfaces.tobago.webapp.TobagoResponseWriter;

import javax.faces.component.UIComponent;
import javax.faces.component.UIPanel;
import javax.faces.context.FacesContext;
import java.io.IOException;

public class BoxRenderer extends BoxRendererBase {

  @Override
  public void prepareRender(FacesContext facesContext, UIComponent component) throws IOException {
    super.prepareRender(facesContext, component);
    HtmlRendererUtils.renderDojoDndSource(facesContext, component);
  }

  /*
  
with shadow
  
<div class="tobago-box" style="width: 100px; height: 100px">
  <div class="tobago-box-shadow" style="width: 99px; height: 99px">
    <div class="tobago-box-border" style="width: 97px; height: 97px">
      <div class="tobago-box-header">Label</div>
    </div>
  </div>

  <div style="position: absolute; top: 26px; left: 6px; width: 87px; height: 67px; background-color: blue;">
    Content
  </div>
</div>

without shadow

<div class="tobago-box" style="width: 100px; height: 100px">
  <span class="tobago-box-header">Label</span>

  <div style="position: absolute; top: 26px; left: 6px; width: 87px; height: 67px; background-color: blue;">
    Content
  </div>
</div>

   */
  @Override
  public void encodeBegin(FacesContext facesContext, UIComponent component) throws IOException {

    TobagoResponseWriter writer = HtmlRendererUtils.getTobagoResponseWriter(facesContext);
    UIBox box = (UIBox) component;

    String clientId = box.getClientId(facesContext);
    writer.startElement(HtmlElements.DIV, box);
    HtmlRendererUtils.renderDojoDndItem(box, writer, true);
    writer.writeClassAttribute(Classes.create(box));
    writer.writeIdAttribute(clientId);
    HtmlRendererUtils.writeDataAttributes(facesContext, writer, box);
    writer.writeStyleAttribute(new Style(facesContext, box));
    encodeBox(facesContext, writer, box);
  }

  private void encodeBox(FacesContext facesContext, TobagoResponseWriter writer, UIBox box) throws IOException {

    // todo: shadow = 0px means, that shadow is disabled, but it may be better, if we can set a boolean in the config.
    // todo: this is possible after fixing 
    Measure measure = getResourceManager().getThemeMeasure(facesContext, box, "shadow");
    boolean hasShadow = measure.greaterThan(Measure.ZERO);

    if (hasShadow) {
      // shadow begin
      writer.startElement(HtmlElements.DIV, box);
      writer.writeClassAttribute(Classes.create(box, "shadow"));

      Style shadow = new Style();
      shadow.setWidth(box.getCurrentWidth().subtract(1));
      shadow.setHeight(box.getCurrentHeight().subtract(1));
      writer.writeStyleAttribute(shadow);

      // border begin
      writer.startElement(HtmlElements.DIV, box);
      writer.writeClassAttribute(Classes.create(box, "border"));

      Style border = new Style();
      border.setWidth(box.getCurrentWidth().subtract(3));
      border.setHeight(box.getCurrentHeight().subtract(3));
      writer.writeStyleAttribute(border);
    }

    UIComponent label = box.getFacet(Facets.LABEL);
    writer.startElement(HtmlElements.DIV, null);
    writer.writeClassAttribute(Classes.create(box, "header"));
    String labelString = (String) box.getAttributes().get(Attributes.LABEL);
    if (label != null) {
      RenderUtils.encode(facesContext, label);
    } else if (labelString != null) {
      writer.writeText(labelString);
    }
    writer.endElement(HtmlElements.DIV);

    final UIMenuBar menuBar = getMenuBarFacet(box);
    if (menuBar != null) {
      RenderUtils.encode(facesContext, menuBar);
    }

    UIPanel toolbar = (UIPanel) box.getFacet(Facets.TOOL_BAR);
    if (toolbar != null) {
      renderToolbar(facesContext, writer, box, toolbar);
    }
    
    if (hasShadow) {
      // border end
      writer.endElement(HtmlElements.DIV);
      // shadow end
      writer.endElement(HtmlElements.DIV);
    }

    writer.startElement(HtmlElements.DIV, null);
    writer.writeClassAttribute(Classes.create(box, "content")); // needed to be scrollable inside of the box
    final Style style = new Style(facesContext, box);
    final Measure borderLeft = box.getBorderLeft();
    final Measure borderRight = box.getBorderRight();
    final Measure borderTop = box.getBorderTop();
    final Measure borderBottom = box.getBorderBottom();
    style.setWidth(style.getWidth().subtract(borderLeft).subtract(borderRight));
    style.setHeight(style.getHeight().subtract(borderTop).subtract(borderBottom));
    style.setLeft(borderLeft);
    style.setTop(borderTop);
    writer.writeStyleAttribute(style);
  }

  @Override
  public void encodeEnd(FacesContext facesContext, UIComponent component) throws IOException {
    TobagoResponseWriter writer = HtmlRendererUtils.getTobagoResponseWriter(facesContext);
    writer.endElement(HtmlElements.DIV);
    writer.endElement(HtmlElements.DIV);
  }

  protected void renderToolbar(
      FacesContext facesContext, TobagoResponseWriter writer, UIBox box, UIPanel toolbar) throws IOException {
    writer.startElement(HtmlElements.DIV, null);
    writer.writeClassAttribute(Classes.create(box, "headerToolBar"));
    toolbar.setRendererType(RendererTypes.BOX_TOOL_BAR);
    RenderUtils.encode(facesContext, toolbar);
    writer.endElement(HtmlElements.DIV);
  }
}
