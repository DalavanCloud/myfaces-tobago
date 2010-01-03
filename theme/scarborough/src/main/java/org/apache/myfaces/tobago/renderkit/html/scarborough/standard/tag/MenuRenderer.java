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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.tobago.component.RendererTypes;
import org.apache.myfaces.tobago.component.UIMenu;
import org.apache.myfaces.tobago.renderkit.LabelWithAccessKey;
import org.apache.myfaces.tobago.renderkit.LayoutComponentRendererBase;
import org.apache.myfaces.tobago.renderkit.css.Style;
import org.apache.myfaces.tobago.renderkit.html.HtmlAttributes;
import org.apache.myfaces.tobago.renderkit.html.HtmlConstants;
import org.apache.myfaces.tobago.renderkit.html.util.HtmlRendererUtils;
import org.apache.myfaces.tobago.util.AccessKeyMap;
import org.apache.myfaces.tobago.webapp.TobagoResponseWriter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.List;

public class MenuRenderer extends LayoutComponentRendererBase {

  private static final Log LOG = LogFactory.getLog(MenuRenderer.class);

  private static final String MENU_ACCELERATOR_KEYS = "menuAcceleratorKeys";

  @Override
  public void encodeBegin(FacesContext facesContext, UIComponent component) throws IOException {
    
    UIMenu menu = (UIMenu) component;
    TobagoResponseWriter writer = HtmlRendererUtils.getTobagoResponseWriter(facesContext);

    boolean disabled = menu.isDisabled();
    boolean firstLevel = RendererTypes.MENU_BAR.equals(menu.getParent().getRendererType());
    boolean isParentMenu = menu.getChildCount() > 0; // todo: may be not correct

    writer.startElement(HtmlConstants.LI, menu);
    String clazz = firstLevel ? "tobago-menu-top" : "tobago-menu-parent"; 
    writer.writeClassAttribute(clazz);
    if (menu.getImage() != null) {
      Style style = new Style();
      style.setBackgroundImage("url(" + menu.getImage() + ")");
      writer.writeStyleAttribute(style);
    }
    writer.startElement(HtmlConstants.A, menu);
    writer.writeAttribute(HtmlAttributes.HREF, "#", false);
    writer.writeIdAttribute(menu.getClientId(facesContext));

    LabelWithAccessKey label = new LabelWithAccessKey(menu);
    if (label.getText() != null) {
      if (label.getAccessKey() != null) {
        if (LOG.isInfoEnabled()
            && !AccessKeyMap.addAccessKey(facesContext, label.getAccessKey())) {
          LOG.info("duplicated accessKey : " + label.getAccessKey());
        }
        if (!disabled) {
          addAcceleratorKey(facesContext, menu, label.getAccessKey());
        }
      }
      HtmlRendererUtils.writeLabelWithAccessKey(writer, label);
    }
    writer.endElement(HtmlConstants.A);
    if (isParentMenu) {
      writer.startElement(HtmlConstants.OL, menu);
    }
  }

  @Override
  public void encodeEnd(FacesContext facesContext, UIComponent component) throws IOException {

    UIMenu menu = (UIMenu) component;
    TobagoResponseWriter writer = HtmlRendererUtils.getTobagoResponseWriter(facesContext);

    boolean isParentMenu = menu.getChildCount() > 0; // todo: may be not correct
    if (isParentMenu) {
      writer.endElement(HtmlConstants.OL);
    }
    writer.endElement(HtmlConstants.LI);
  }
  
  private void addAcceleratorKey(
      FacesContext facesContext, UIComponent component, Character accessKey) {
    String clientId = component.getClientId(facesContext);
    while (component != null && !component.getAttributes().containsKey(MENU_ACCELERATOR_KEYS)) {
      component = component.getParent();
    }
    if (component != null) {
      List<String> keys
          = (List<String>) component.getAttributes().get(MENU_ACCELERATOR_KEYS);
      String jsStatement = HtmlRendererUtils.createOnclickAcceleratorKeyJsStatement(
          clientId, accessKey, null);
      keys.add(jsStatement);
    } else {
      LOG.warn("Can't find menu root component!");
    }
  }
  
}
