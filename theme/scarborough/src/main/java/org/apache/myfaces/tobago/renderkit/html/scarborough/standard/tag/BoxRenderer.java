package org.apache.myfaces.tobago.renderkit.html.scarborough.standard.tag;

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

import org.apache.myfaces.tobago.component.Facets;
import org.apache.myfaces.tobago.component.UIBox;
import org.apache.myfaces.tobago.layout.Measure;
import org.apache.myfaces.tobago.layout.PixelMeasure;
import org.apache.myfaces.tobago.renderkit.BoxRendererBase;
import org.apache.myfaces.tobago.renderkit.css.Style;
import org.apache.myfaces.tobago.renderkit.html.HtmlConstants;
import org.apache.myfaces.tobago.renderkit.html.util.HtmlRendererUtils;
import org.apache.myfaces.tobago.renderkit.util.RenderUtil;
import org.apache.myfaces.tobago.util.VariableResolverUtil;
import org.apache.myfaces.tobago.webapp.TobagoResponseWriter;

import javax.faces.component.UIComponent;
import javax.faces.component.UIPanel;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import java.io.IOException;

public class BoxRenderer extends BoxRendererBase {

  public void encodeBegin(FacesContext facesContext, UIComponent component) throws IOException {

    UIBox box = (UIBox) component;
    TobagoResponseWriter writer = HtmlRendererUtils.getTobagoResponseWriter(facesContext);

    UIComponent label = box.getFacet(Facets.LABEL);
    String labelString = box.getLabel();
    UIPanel toolbar = (UIPanel) box.getFacet(Facets.TOOL_BAR);
    Style style = new Style(facesContext, box);
    if (toolbar != null) {
      Measure padding = getResourceManager().getThemeMeasure(facesContext, box, "paddingTopWhenToolbar");
      style.setPaddingTop(padding);
      style.setPaddingBottom(PixelMeasure.ZERO);
    }

    writer.startElement(HtmlConstants.FIELDSET, box);
    writer.writeClassAttribute();
    writer.writeStyleAttribute(style);

    if (label != null || labelString != null) {
      writer.startElement(HtmlConstants.LEGEND, box);
      writer.writeClassAttribute();

      if (label != null) {
        RenderUtil.encode(facesContext, label);
      } else {
        writer.writeText(labelString);
      }
      writer.endElement(HtmlConstants.LEGEND);
    }

    Style innerStyle = new Style();
    if (toolbar != null) {
      writer.startElement(HtmlConstants.DIV, null);
      writer.writeClassAttribute("tobago-box-toolbar-div");
      writer.startElement(HtmlConstants.DIV, null);
      writer.writeClassAttribute("tobago-box-toolbar-span");
      toolbar.setRendererType("BoxToolBar");
      RenderUtil.encode(facesContext, toolbar);
      writer.endElement(HtmlConstants.DIV);
      writer.endElement(HtmlConstants.DIV);
      if (VariableResolverUtil.resolveClientProperties(facesContext).getUserAgent().isMsie()) {
        innerStyle.setTop(new PixelMeasure(-10));
      }
    }
    writer.startElement(HtmlConstants.DIV, box);
    writer.writeClassAttribute();
    writer.writeStyleAttribute(innerStyle);
  }

  public void encodeEnd(FacesContext facesContext,
                        UIComponent component) throws IOException {

    ResponseWriter writer = facesContext.getResponseWriter();
    writer.endElement(HtmlConstants.DIV);
    writer.endElement(HtmlConstants.FIELDSET);
  }

  public boolean getRendersChildren() {
    return true;
  }
}
