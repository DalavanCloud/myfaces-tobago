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

import org.apache.myfaces.tobago.component.SupportFieldId;
import org.apache.myfaces.tobago.context.Markup;
import org.apache.myfaces.tobago.internal.component.AbstractUILabel;
import org.apache.myfaces.tobago.internal.util.AccessKeyLogger;
import org.apache.myfaces.tobago.internal.util.HtmlRendererUtils;
import org.apache.myfaces.tobago.internal.util.JsonUtils;
import org.apache.myfaces.tobago.renderkit.LabelWithAccessKey;
import org.apache.myfaces.tobago.renderkit.RendererBase;
import org.apache.myfaces.tobago.renderkit.css.BootstrapClass;
import org.apache.myfaces.tobago.renderkit.css.TobagoClass;
import org.apache.myfaces.tobago.renderkit.html.DataAttributes;
import org.apache.myfaces.tobago.renderkit.html.HtmlAttributes;
import org.apache.myfaces.tobago.renderkit.html.HtmlElements;
import org.apache.myfaces.tobago.util.ComponentUtils;
import org.apache.myfaces.tobago.webapp.TobagoResponseWriter;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.ComponentSystemEventListener;
import javax.faces.event.ListenerFor;
import javax.faces.event.PostAddToViewEvent;
import java.io.IOException;

@ListenerFor(systemEventClass = PostAddToViewEvent.class)
public class LabelRenderer extends RendererBase implements ComponentSystemEventListener {

  @Override
  public void processEvent(final ComponentSystemEvent event) {
    ComponentUtils.evaluateAutoFor(event.getComponent(), UIInput.class);
  }

  @Override
  public void encodeEnd(final FacesContext facesContext, final UIComponent component) throws IOException {

    final AbstractUILabel label = (AbstractUILabel) component;
    final TobagoResponseWriter writer = getResponseWriter(facesContext);
    final UIComponent corresponding = ComponentUtils.findFor(label);
    final String forId;
    if (corresponding instanceof SupportFieldId) {
      forId = ((SupportFieldId) corresponding).getFieldId(facesContext);
    } else {
      forId = corresponding != null ? corresponding.getClientId(facesContext) : null;
    }
    final String clientId = label.getClientId(facesContext);
    final Markup markup = label.getMarkup();
    final boolean required;
    if (corresponding instanceof UIInput) {
      required = ((UIInput) corresponding).isRequired();
    } else {
      required = false;
    }

    writer.startElement(HtmlElements.LABEL);
    HtmlRendererUtils.writeDataAttributes(facesContext, writer, label);
    writer.writeClassAttribute(
        TobagoClass.LABEL,
        TobagoClass.LABEL.createMarkup(markup),
        required ? TobagoClass.REQUIRED : null,
        BootstrapClass.COL_FORM_LABEL,
        label.getCustomClass());
    writer.writeIdAttribute(clientId);
    writer.writeAttribute(DataAttributes.MARKUP, JsonUtils.encode(markup), false);
    if (forId != null) {
      writer.writeAttribute(HtmlAttributes.FOR, forId, false);
    }
    final String title = HtmlRendererUtils.getTitleFromTipAndMessages(facesContext, label);
    if (title != null) {
      writer.writeAttribute(HtmlAttributes.TITLE, title, true);
    }

    encodeTextContent(facesContext, writer, label);

    writer.endElement(HtmlElements.LABEL);
  }

  /**
   * Encodes the text inside of the label.
   * Can be overwritten in other themes.
   */
  protected void encodeTextContent(
      final FacesContext facesContext, final TobagoResponseWriter writer, final AbstractUILabel component)
      throws IOException {

    final LabelWithAccessKey label = new LabelWithAccessKey(component);

    if (label.getAccessKey() != null) {
      writer.writeAttribute(HtmlAttributes.ACCESSKEY, Character.toString(label.getAccessKey()), false);
      AccessKeyLogger.addAccessKey(facesContext, label.getAccessKey(), component.getClientId());
    }
    HtmlRendererUtils.writeLabelWithAccessKey(writer, label);
  }
}
