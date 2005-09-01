/*
 * Copyright 2002-2005 atanion GmbH.
 * 
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 * 
 *        http://www.apache.org/licenses/LICENSE-2.0
 * 
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
/*
 * Created 07.02.2003 16:00:00.
 * $Id$
 */
package org.apache.myfaces.tobago.renderkit.html.scarborough.standard.tag;

import org.apache.myfaces.tobago.component.ComponentUtil;
import org.apache.myfaces.tobago.component.UIPage;
import org.apache.myfaces.tobago.context.ResourceManagerUtil;
import org.apache.myfaces.tobago.renderkit.RendererBase;
import org.apache.myfaces.tobago.webapp.TobagoResponseWriter;

import javax.faces.component.UICommand;
import javax.faces.component.UIComponent;
import javax.faces.component.UIGraphic;
import javax.faces.context.FacesContext;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ImageRenderer extends RendererBase {
  
// ------------------------------------------------------------------ constants

  private static final Log LOG = LogFactory.getLog(ImageRenderer.class);

// ----------------------------------------------------------------- interfaces


// ---------------------------- interface TobagoRenderer

  public void encodeEndTobago(FacesContext facesContext,
      UIComponent component) throws IOException {
    UIGraphic graphic = (UIGraphic) component;
    final String value = graphic.getUrl();
    String src = value;
    if (src != null) {
      final String ucSrc = src.toUpperCase();
      if (ucSrc.startsWith("HTTP:") || ucSrc.startsWith("FTP:")
          || ucSrc.startsWith("/")) {
        // absolute Path to image : nothing to do
      } else {
        src = null;
        if (isDisabled(graphic)) {
          src = ResourceManagerUtil.getImageWithPath(
              facesContext, createSrc(value, "Disabled"), true);
        }
        if (src == null) {
          src = ResourceManagerUtil.getImageWithPath(facesContext, value);
        }
        addImageSources(facesContext, graphic);
      }
    }

    String border = (String) graphic.getAttributes().get(ATTR_BORDER);
    if (border == null) {
      border = "0";
    }
    String alt = (String) graphic.getAttributes().get(ATTR_ALT);
    if (alt == null) {
      alt = "";
    }
    String tip = (String) graphic.getAttributes().get(ATTR_TIP);


    TobagoResponseWriter writer = (TobagoResponseWriter) facesContext.getResponseWriter();

    writer.startElement("img", graphic);
    final String clientId = graphic.getClientId(facesContext);
    writer.writeIdAttribute(clientId);
    if (ComponentUtil.isHoverEnabled(graphic) && !isDisabled(graphic)) {
      writer.writeAttribute("onmouseover",
          "tobagoImageMouseover('" + clientId + "')", null);
      writer.writeAttribute("onmouseout",
          "tobagoImageMouseout('" + clientId + "')", null);
    }
    if (src != null) {
      writer.writeAttribute("src", src, null);
    }
    writer.writeAttribute("alt", alt, null);
    if (tip != null) {
      writer.writeAttribute("title", tip, null);
    }
    writer.writeAttribute("border", border, null);
    writer.writeAttribute("height", null, ATTR_HEIGHT);
    writer.writeAttribute("style", null, ATTR_STYLE);
    writer.writeComponentClass();
    writer.endElement("img");
  }

// ----------------------------------------------------------- business methods

  public static void addImageSources(
      FacesContext facesContext, UIGraphic graphic) {
    addImageSources(facesContext, ComponentUtil.findPage(graphic),
        graphic.getUrl(), graphic.getClientId(facesContext));
  }
  public static void addImageSources(
      FacesContext facesContext, UIPage page, String src, String id) {
    StringBuffer buffer = new StringBuffer();
    buffer.append("addImageSources('");
    buffer.append(id);
    buffer.append("','");
    buffer.append(ResourceManagerUtil.getImageWithPath(
          facesContext, src, false));
    buffer.append("','");
    buffer.append(ResourceManagerUtil.getImageWithPath(
          facesContext, createSrc(src, "Disabled"), true));
    buffer.append("','");
    buffer.append(ResourceManagerUtil.getImageWithPath(
          facesContext, createSrc(src, "Hover"), true));
    buffer.append("');");
    page.getOnloadScripts().add(buffer.toString());
  }

  public static String createSrc(String src, String ext) {
    int dot = src.lastIndexOf('.');
    if (dot == -1) {
      LOG.warn("Image src without extension: '" + src + "'");
      return src;
    } else {
      return src.substring(0, dot) + ext + src.substring(dot);
    }
  }

  private boolean isDisabled(UIGraphic graphic) {
    boolean disabled = ComponentUtil.getBooleanAttribute(graphic,
        ATTR_DISABLED);
    if (!disabled && graphic.getParent() instanceof UICommand) {
      disabled =
          ComponentUtil.getBooleanAttribute(graphic.getParent(), ATTR_DISABLED);
    }
    return disabled;
  }
}

