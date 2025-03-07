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

import org.apache.myfaces.tobago.context.Markup;
import org.apache.myfaces.tobago.internal.component.AbstractUIFlowLayout;
import org.apache.myfaces.tobago.internal.util.JsonUtils;
import org.apache.myfaces.tobago.layout.TextAlign;
import org.apache.myfaces.tobago.renderkit.RendererBase;
import org.apache.myfaces.tobago.renderkit.css.BootstrapClass;
import org.apache.myfaces.tobago.renderkit.css.TobagoClass;
import org.apache.myfaces.tobago.renderkit.html.DataAttributes;
import org.apache.myfaces.tobago.renderkit.html.HtmlElements;
import org.apache.myfaces.tobago.webapp.TobagoResponseWriter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import java.io.IOException;

public class FlowLayoutRenderer extends RendererBase {

  @Override
  public void encodeBegin(final FacesContext facesContext, final UIComponent component) throws IOException {

    final TobagoResponseWriter writer = getResponseWriter(facesContext);
    final AbstractUIFlowLayout flowLayout = (AbstractUIFlowLayout) component;
    final Markup markup = flowLayout.getMarkup();

    writer.startElement(HtmlElements.DIV);
    writer.writeIdAttribute(flowLayout.getClientId());
    writer.writeAttribute(DataAttributes.MARKUP, JsonUtils.encode(markup), false);
    final TextAlign textAlign = flowLayout.getTextAlign();
    writer.writeClassAttribute(
        TobagoClass.FLOW_LAYOUT,
        TobagoClass.FLOW_LAYOUT.createMarkup(markup),
        flowLayout.getCustomClass(),
        textAlign != null ? BootstrapClass.textAlign(textAlign) : null);
  }

  @Override
  public void encodeEnd(final FacesContext facesContext, final UIComponent component) throws IOException {
    final TobagoResponseWriter writer = getResponseWriter(facesContext);
    writer.endElement(HtmlElements.DIV);
  }
}
