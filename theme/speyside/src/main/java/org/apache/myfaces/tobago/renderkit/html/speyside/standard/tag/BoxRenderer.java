package org.apache.myfaces.tobago.renderkit.html.speyside.standard.tag;

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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.tobago.ajax.api.AjaxRenderer;
import org.apache.myfaces.tobago.ajax.api.AjaxUtils;
import org.apache.myfaces.tobago.component.Attributes;
import org.apache.myfaces.tobago.component.Facets;
import org.apache.myfaces.tobago.component.UIBox;
import org.apache.myfaces.tobago.component.UIToolBar;
import org.apache.myfaces.tobago.layout.Measure;
import org.apache.myfaces.tobago.renderkit.BoxRendererBase;
import org.apache.myfaces.tobago.renderkit.css.Style;
import org.apache.myfaces.tobago.renderkit.html.HtmlConstants;
import org.apache.myfaces.tobago.renderkit.html.StyleClasses;
import org.apache.myfaces.tobago.renderkit.html.util.HtmlRendererUtils;
import org.apache.myfaces.tobago.renderkit.util.RenderUtil;
import org.apache.myfaces.tobago.webapp.TobagoResponseWriter;

import javax.faces.component.UIComponent;
import javax.faces.component.UIPanel;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.Map;

import static org.apache.myfaces.tobago.TobagoConstants.SUBCOMPONENT_SEP;

public class BoxRenderer extends BoxRendererBase implements AjaxRenderer {

  private static final Log LOG = LogFactory.getLog(BoxRenderer.class);
  public static final String CONTENT_INNER = SUBCOMPONENT_SEP + "content-inner";
  public static final String HEADER = SUBCOMPONENT_SEP + "header";

  @Override
  public void prepareRender(FacesContext facesContext, UIComponent component) throws IOException {
    super.prepareRender(facesContext, component);
    HtmlRendererUtils.renderDojoDndSource(facesContext, component);
  }

  /*
  
with shadow
  
<div class="tobago-box-default" style="width: 100px; height: 100px">
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

<div class="tobago-box-default" style="width: 100px; height: 100px">
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
    writer.startElement(HtmlConstants.DIV, box);
    HtmlRendererUtils.renderDojoDndItem(box, writer, true);
    writer.writeClassAttribute();
    writer.writeIdAttribute(clientId);
    writer.writeStyleAttribute(new Style(facesContext, box));
    writer.writeJavascript("Tobago.addAjaxComponent(\"" + clientId + "\");");
    encodeBox(facesContext, writer, box);
  }

  private void encodeBox(FacesContext facesContext, TobagoResponseWriter writer, UIBox box) throws IOException {

    // todo: shadow = 0px means, that shadow is disabled, but it may be better, if we can set a boolean in the config.
    Measure measure = getResourceManager().getThemeMeasure(facesContext, box, "shadow");
    boolean hasShadow = measure.greaterThan(Measure.ZERO);

    if (hasShadow) {
      // shadow begin
      writer.startElement(HtmlConstants.DIV, box);

      StyleClasses classes = new StyleClasses();
      classes.addClass("box", "shadow");
      writer.writeClassAttribute(classes);

      Style shadow = new Style();
      shadow.setWidth(box.getWidth().subtract(1));
      shadow.setHeight(box.getHeight().subtract(1));
      writer.writeStyleAttribute(shadow);

      // border begin
      writer.startElement(HtmlConstants.DIV, box);

      classes = new StyleClasses();
      classes.addClass("box", "border");
      writer.writeClassAttribute(classes);

      Style border = new Style();
      border.setWidth(box.getWidth().subtract(3));
      border.setHeight(box.getHeight().subtract(3));
      writer.writeStyleAttribute(border);
    }

    UIComponent label = box.getFacet(Facets.LABEL);
    writer.startElement(HtmlConstants.DIV, null);
    writer.writeClassAttribute("tobago-box-header");
    String labelString = (String) box.getAttributes().get(Attributes.LABEL);
    if (label != null) {
      RenderUtil.encode(facesContext, label);
    } else if (labelString != null) {
      writer.writeText(labelString);
    }
    writer.endElement(HtmlConstants.DIV);

    UIPanel toolbar = (UIPanel) box.getFacet(Facets.TOOL_BAR);
    if (toolbar != null) {
      renderToolbar(facesContext, writer, toolbar);
    }
    
    if (hasShadow) {
      // border end
      writer.endElement(HtmlConstants.DIV);
      // shadow end
      writer.endElement(HtmlConstants.DIV);
    }
  }

  @Override
  public void encodeEnd(FacesContext facesContext, UIComponent component) throws IOException {
    TobagoResponseWriter writer = HtmlRendererUtils.getTobagoResponseWriter(facesContext);
    writer.endElement(HtmlConstants.DIV);
  }

  protected void renderToolbar(
      FacesContext facesContext, TobagoResponseWriter writer, UIPanel toolbar) throws IOException {
    final Map attributes = toolbar.getAttributes();
    String className = "tobago-box-header-toolbar-div";
    if (UIToolBar.LABEL_OFF.equals(attributes.get(Attributes.LABEL_POSITION))) {
      className += " tobago-box-header-toolbar-label_off";
    }
    writer.startElement(HtmlConstants.DIV, null);
    writer.writeClassAttribute(className);
    toolbar.setRendererType("BoxToolBar");
    RenderUtil.encode(facesContext, toolbar);
    writer.endElement(HtmlConstants.DIV);
  }

  public void encodeAjax(FacesContext facesContext, UIComponent component) throws IOException {
    AjaxUtils.checkParamValidity(facesContext, component, UIPanel.class);
    TobagoResponseWriter writer = HtmlRendererUtils.getTobagoResponseWriter(facesContext);

    encodeBox(facesContext, writer, (UIBox) component);
    component.encodeChildren(facesContext);
    facesContext.responseComplete();
  }
}
