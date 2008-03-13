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
import static org.apache.myfaces.tobago.TobagoConstants.ATTR_DISABLED;
import static org.apache.myfaces.tobago.TobagoConstants.ATTR_READONLY;
import static org.apache.myfaces.tobago.TobagoConstants.ATTR_ROWS;
import org.apache.myfaces.tobago.component.ComponentUtil;
import org.apache.myfaces.tobago.component.UIInputBase;
import org.apache.myfaces.tobago.component.UITextarea;
import org.apache.myfaces.tobago.renderkit.HtmlUtils;
import org.apache.myfaces.tobago.renderkit.InputRendererBase;
import org.apache.myfaces.tobago.renderkit.html.HtmlAttributes;
import org.apache.myfaces.tobago.renderkit.html.HtmlConstants;
import org.apache.myfaces.tobago.renderkit.html.HtmlRendererUtil;
import org.apache.myfaces.tobago.webapp.TobagoResponseWriter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import java.io.IOException;

public class TextAreaRenderer extends InputRendererBase {

  private static final Log LOG = LogFactory.getLog(TextAreaRenderer.class);

  @Override
  public void encodeEnd(FacesContext facesContext, UIComponent component) throws IOException {
    if (!(component instanceof UITextarea)) {
      LOG.error("Wrong type: Need " + UITextarea.class.getName() + ", but was " + component.getClass().getName());
      return;
    }

    UIInputBase input = (UIInputBase) component;
    String title = HtmlRendererUtil.getTitleFromTipAndMessages(facesContext, component);

    String clientId = input.getClientId(facesContext);
    String onchange = HtmlUtils.generateOnchange(input, facesContext);

    TobagoResponseWriter writer = HtmlRendererUtil.getTobagoResponseWriter(facesContext);
    writer.startElement(HtmlConstants.TEXTAREA, input);
    writer.writeNameAttribute(clientId);
    writer.writeIdAttribute(clientId);
    writer.writeAttribute(HtmlAttributes.ROWS, null, ATTR_ROWS);
    if (title != null) {
      writer.writeAttribute(HtmlAttributes.TITLE, title, true);
    }
    writer.writeAttribute(HtmlAttributes.READONLY,
        ComponentUtil.getBooleanAttribute(input, ATTR_READONLY));
    writer.writeAttribute(HtmlAttributes.DISABLED,
        ComponentUtil.getBooleanAttribute(input, ATTR_DISABLED));
    Integer tabIndex = input.getTabIndex();
    if (tabIndex != null) {
      writer.writeAttribute(HtmlAttributes.TABINDEX, tabIndex);
    }
    HtmlRendererUtil.renderDojoDndItem(component, writer, true);
    writer.writeStyleAttribute();
    writer.writeClassAttribute();
    if (onchange != null) {
      writer.writeAttribute(HtmlAttributes.ONCHANGE, onchange, null);
    }
    String currentValue = ComponentUtil.currentValue(input);
    if (currentValue != null) {
      // this is because browsers eat the first CR+LF of <textarea>
      if (currentValue.startsWith("\r\n")) {
        currentValue = "\r\n" + currentValue;
      } else if (currentValue.startsWith("\n")) {
        currentValue = "\n" + currentValue;
      } else if (currentValue.startsWith("\r")) {
        currentValue = "\r" + currentValue;
      }
      writer.writeText(currentValue, null);
    }
    writer.endElement(HtmlConstants.TEXTAREA);

    checkForCommandFacet(input, facesContext, writer);

    // focus
    HtmlRendererUtil.renderFocusId(facesContext, component);
  }
}

