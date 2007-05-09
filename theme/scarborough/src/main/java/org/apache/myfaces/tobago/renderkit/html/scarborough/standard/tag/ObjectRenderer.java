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

import static org.apache.myfaces.tobago.TobagoConstants.ATTR_STYLE;
import static org.apache.myfaces.tobago.TobagoConstants.ATTR_TARGET;
import org.apache.myfaces.tobago.context.ResourceManagerUtil;
import org.apache.myfaces.tobago.renderkit.LayoutableRendererBase;
import org.apache.myfaces.tobago.renderkit.html.HtmlAttributes;
import org.apache.myfaces.tobago.renderkit.html.HtmlConstants;
import org.apache.myfaces.tobago.webapp.TobagoResponseWriter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import java.io.IOException;

public class ObjectRenderer extends LayoutableRendererBase {
  public void encodeEnd(FacesContext facesContext, UIComponent component)
      throws IOException {
    TobagoResponseWriter writer = (TobagoResponseWriter) facesContext.getResponseWriter();
    writer.startElement(HtmlConstants.IFRAME, component);
    writer.writeAttribute(HtmlAttributes.SRC, null, ATTR_TARGET);
    writer.writeClassAttribute();
    writer.writeAttribute(HtmlAttributes.STYLE, null, ATTR_STYLE);

    String noframes = ResourceManagerUtil.getPropertyNotNull(
        facesContext, "tobago", "browser.noframe.message.prefix");
    writer.writeText(noframes + " ", null);
    writer.startElement(HtmlConstants.A, component);
    writer.writeAttribute(HtmlAttributes.HREF, null, ATTR_TARGET);
    writer.writeText(null, ATTR_TARGET);
    writer.endElement(HtmlConstants.A);
    noframes = ResourceManagerUtil.getPropertyNotNull(
        facesContext, "tobago", "browser.noframe.message.postfix");
    writer.writeText(" " + noframes, null);

    writer.endElement(HtmlConstants.IFRAME);
  }
}
