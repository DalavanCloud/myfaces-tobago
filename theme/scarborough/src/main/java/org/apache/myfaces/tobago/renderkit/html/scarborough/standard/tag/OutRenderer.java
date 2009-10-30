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

import org.apache.myfaces.tobago.component.UIOut;
import org.apache.myfaces.tobago.renderkit.LayoutableRendererBase;
import org.apache.myfaces.tobago.renderkit.html.HtmlConstants;
import org.apache.myfaces.tobago.renderkit.html.util.HtmlRendererUtils;
import org.apache.myfaces.tobago.renderkit.util.RenderUtil;
import org.apache.myfaces.tobago.webapp.TobagoResponseWriter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.StringTokenizer;

public class OutRenderer extends LayoutableRendererBase {

  public void encodeEnd(FacesContext facesContext, UIComponent component) throws IOException {

    UIOut out = (UIOut) component;

    String text = RenderUtil.currentValue(out);
    if (text == null) {
      text = "";
    }

    TobagoResponseWriter writer = HtmlRendererUtils.getTobagoResponseWriter(facesContext);

    boolean escape = out.getEscape();
    boolean createSpan = out.getCreateSpan();

    if (createSpan) {
      String id = out.getClientId(facesContext);
      writer.startElement(HtmlConstants.DIV, out);
      writer.writeIdAttribute(id);
      writer.writeStyleAttribute();
      HtmlRendererUtils.renderDojoDndItem(out, writer, true);
      writer.writeClassAttribute();
      HtmlRendererUtils.renderTip(out, writer);
    }
    if (escape) {
      StringTokenizer tokenizer = new StringTokenizer(text, "\n\r");
      while (tokenizer.hasMoreTokens()) {
        String token = tokenizer.nextToken();
        writer.writeText(token);
        if (tokenizer.hasMoreTokens()) {
          writer.startElement(HtmlConstants.BR, null);
          writer.endElement(HtmlConstants.BR);
        }
      }
    } else {
      writer.writeText("", null);
      writer.write(text);
    }
    if (createSpan) {
      writer.endElement(HtmlConstants.DIV);
    }
  }
}
