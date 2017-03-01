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

import org.apache.myfaces.tobago.component.UIOut;
import org.apache.myfaces.tobago.config.TobagoConfig;
import org.apache.myfaces.tobago.renderkit.css.BootstrapClass;
import org.apache.myfaces.tobago.renderkit.css.Classes;
import org.apache.myfaces.tobago.renderkit.html.HtmlAttributes;
import org.apache.myfaces.tobago.renderkit.html.HtmlElements;
import org.apache.myfaces.tobago.internal.util.HtmlRendererUtils;
import org.apache.myfaces.tobago.internal.util.RenderUtils;
import org.apache.myfaces.tobago.sanitizer.SanitizeMode;
import org.apache.myfaces.tobago.sanitizer.Sanitizer;
import org.apache.myfaces.tobago.webapp.TobagoResponseWriter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.StringTokenizer;

public class OutRenderer extends LabelLayoutRendererBase {

  @Override
  public void encodeBeginMessageField(final FacesContext facesContext, final UIComponent component) throws IOException {

    final UIOut out = (UIOut) component;

    String text = RenderUtils.currentValue(out);
    if (text == null) {
      text = "";
    }

    final TobagoResponseWriter writer = getResponseWriter(facesContext);

    final boolean escape = out.isEscape();
    final boolean createSpan = out.isCreateSpan();

    if (createSpan) {
      writer.startElement(HtmlElements.SPAN);
      HtmlRendererUtils.writeDataAttributes(facesContext, writer, out);
      writer.writeStyleAttribute(out.getStyle());
      writer.writeClassAttribute(Classes.create(out), BootstrapClass.FORM_CONTROL_STATIC, out.getCustomClass());
      final String title = HtmlRendererUtils.getTitleFromTipAndMessages(facesContext, out);
      if (title != null) {
        writer.writeAttribute(HtmlAttributes.TITLE, title, true);
      }
    }
    if (escape) {
      final StringTokenizer tokenizer = new StringTokenizer(text, "\r\n");
      while (tokenizer.hasMoreTokens()) {
        final String token = tokenizer.nextToken();
        writer.writeText(token);
        if (tokenizer.hasMoreTokens()) {
          writer.startElement(HtmlElements.BR);
          writer.endElement(HtmlElements.BR);
        }
      }
    } else { // escape="false"
      writer.writeText("", null); // to ensure the closing > of the <span> start tag.
      if (SanitizeMode.auto == out.getSanitize()) {
        final Sanitizer sanitizer = TobagoConfig.getInstance(facesContext).getSanitizer();
        text = sanitizer.sanitize(text);
      }
      writer.write(text);
    }
  }

  @Override
  public void encodeEndMessageField(final FacesContext facesContext, final UIComponent component) throws IOException {

    final UIOut out = (UIOut) component;
    final TobagoResponseWriter writer = getResponseWriter(facesContext);
    final boolean createSpan = out.isCreateSpan();

    if (createSpan) {
      writer.endElement(HtmlElements.SPAN);
    }
  }
}
