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

/*
 * Created 07.02.2003 16:00:00.
 * $Id$
 */

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.tobago.component.Attributes;
import org.apache.myfaces.tobago.component.UIButton;
import org.apache.myfaces.tobago.component.UICommand;
import org.apache.myfaces.tobago.layout.Measure;
import org.apache.myfaces.tobago.layout.PixelMeasure;
import org.apache.myfaces.tobago.renderkit.CommandRendererBase;
import org.apache.myfaces.tobago.renderkit.LabelWithAccessKey;
import org.apache.myfaces.tobago.renderkit.html.HtmlAttributes;
import org.apache.myfaces.tobago.renderkit.html.HtmlConstants;
import org.apache.myfaces.tobago.renderkit.html.HtmlStyleMap;
import org.apache.myfaces.tobago.renderkit.html.util.CommandRendererHelper;
import org.apache.myfaces.tobago.renderkit.html.util.HtmlRendererUtils;
import org.apache.myfaces.tobago.renderkit.util.RenderUtil;
import org.apache.myfaces.tobago.util.AccessKeyMap;
import org.apache.myfaces.tobago.util.ComponentUtils;
import org.apache.myfaces.tobago.webapp.TobagoResponseWriter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import java.io.IOException;

public class ButtonRenderer extends CommandRendererBase {

  private static final Log LOG = LogFactory.getLog(ButtonRenderer.class);

  public void encodeEnd(FacesContext facesContext, UIComponent component) throws IOException {

    UIButton button = (UIButton) component;
    String clientId = button.getClientId(facesContext);

    CommandRendererHelper helper = new CommandRendererHelper(facesContext, button, CommandRendererHelper.Tag.BUTTON);

    TobagoResponseWriter writer = HtmlRendererUtils.getTobagoResponseWriter(facesContext);

    LabelWithAccessKey label = new LabelWithAccessKey(button);

    writer.startElement(HtmlConstants.BUTTON, button);
    writer.writeAttribute(HtmlAttributes.TYPE, createButtonType(button), false);
    writer.writeNameAttribute(clientId);
    writer.writeIdAttribute(clientId);
    HtmlRendererUtils.renderTip(button, writer);
    writer.writeAttribute(HtmlAttributes.DISABLED, helper.isDisabled());
    Integer tabIndex = button.getTabIndex();
    if (tabIndex != null) {
      writer.writeAttribute(HtmlAttributes.TABINDEX, tabIndex);
    }
    if (helper.getOnclick() != null) {
      writer.writeAttribute(HtmlAttributes.ONCLICK, helper.getOnclick(), true);
    }
    HtmlStyleMap style = new HtmlStyleMap(facesContext, button);
    writer.writeStyleAttribute(style);
    HtmlRendererUtils.renderDojoDndItem(component, writer, true);
    writer.writeClassAttribute();
    writer.flush(); // force closing the start tag

    String imageName = (String) button.getAttributes().get(Attributes.IMAGE);
    if (imageName != null) {
      String image;
      if (imageName.startsWith("HTTP:") || imageName.startsWith("FTP:")
          || imageName.startsWith("/")) {
        image = imageName;
        // absolute Path to image : nothing to do
      } else {
        image = getImageWithPath(facesContext, imageName, helper.isDisabled());
      }
      writer.startElement(HtmlConstants.IMG, null);
      writer.writeAttribute(HtmlAttributes.SRC, image, true);
      HtmlRendererUtils.renderImageTip(component, writer);
      writer.endElement(HtmlConstants.IMG);
    }

    if (label.getText() != null) {
      if (imageName != null) {
        writer.writeText(" "); // separator: e.g. &nbsp;
      }
      HtmlRendererUtils.writeLabelWithAccessKey(writer, label);
    }

    writer.endElement(HtmlConstants.BUTTON);
    if (label.getAccessKey() != null) {
      if (LOG.isInfoEnabled()
          && !AccessKeyMap.addAccessKey(facesContext, label.getAccessKey())) {
        LOG.info("duplicated accessKey : " + label.getAccessKey());
      }
      HtmlRendererUtils.addClickAcceleratorKey(
          facesContext, button.getClientId(facesContext), label.getAccessKey());
    }

    if (ComponentUtils.getBooleanAttribute(component, Attributes.DEFAULT_COMMAND)) {
      boolean transition = ComponentUtils.getBooleanAttribute(button, Attributes.TRANSITION);
      HtmlRendererUtils.setDefaultTransition(facesContext, transition);

      HtmlRendererUtils.writeScriptLoader(facesContext, null, new String[]{
          "Tobago.setDefaultAction('" + button.getClientId(facesContext) + "')"});      
    }
  }

  private String createButtonType(UIComponent component) {
    boolean defaultCommand = ComponentUtils.getBooleanAttribute(component, Attributes.DEFAULT_COMMAND);
    return defaultCommand ? "submit" : "button";
  }

  @Override
  public Measure getPreferredWidth(FacesContext facesContext, UIComponent component) {

    UIButton button = (UIButton) component;
    int width = 0;
    boolean image = button.getImage() != null;
    if (image) {
      width = getConfiguredValue(facesContext, button, "imageWidth");
    }
    LabelWithAccessKey label = new LabelWithAccessKey(button);

    if (label.getText() != null) {
      width += RenderUtil.calculateStringWidth(facesContext, button, label.getText());
    }
    int padding = getConfiguredValue(facesContext, button, "paddingWidth");
    width += 2 * padding;
    if (image && label.getText() != null) {
      width += padding;
    }

    return new PixelMeasure(width);
  }
}
