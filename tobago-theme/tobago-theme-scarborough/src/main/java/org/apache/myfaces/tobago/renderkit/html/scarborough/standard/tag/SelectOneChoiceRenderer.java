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

package org.apache.myfaces.tobago.renderkit.html.scarborough.standard.tag;

import org.apache.myfaces.tobago.component.UISelectOneChoice;
import org.apache.myfaces.tobago.renderkit.HtmlUtils;
import org.apache.myfaces.tobago.renderkit.SelectOneRendererBase;
import org.apache.myfaces.tobago.renderkit.css.Classes;
import org.apache.myfaces.tobago.renderkit.css.Style;
import org.apache.myfaces.tobago.renderkit.html.HtmlAttributes;
import org.apache.myfaces.tobago.renderkit.html.HtmlElements;
import org.apache.myfaces.tobago.renderkit.html.util.HtmlRendererUtils;
import org.apache.myfaces.tobago.renderkit.util.RenderUtils;
import org.apache.myfaces.tobago.util.VariableResolverUtils;
import org.apache.myfaces.tobago.webapp.TobagoResponseWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import java.io.IOException;
import java.util.List;

public class SelectOneChoiceRenderer extends SelectOneRendererBase {

  private static final Logger LOG = LoggerFactory.getLogger(SelectOneChoiceRenderer.class);

  public boolean getRendersChildren() {
    return true;
  }

  public void encodeEnd(FacesContext facesContext, UIComponent component) throws IOException {
    if (!(component instanceof UISelectOneChoice)) {
      LOG.error("Wrong type: Need " + UISelectOneChoice.class.getName()
          + ", but was " + component.getClass().getName());
      return;
    }

    UISelectOneChoice select = (UISelectOneChoice) component;
    TobagoResponseWriter writer = HtmlRendererUtils.getTobagoResponseWriter(facesContext);

    String id = select.getClientId(facesContext);
    List<SelectItem> items = RenderUtils.getSelectItems(select);
    String title = HtmlRendererUtils.getTitleFromTipAndMessages(facesContext, select);
    boolean disabled = items.size() == 0 || select.isDisabled() || select.isReadonly();

    writer.startElement(HtmlElements.SELECT, select);
    writer.writeNameAttribute(id);
    writer.writeIdAttribute(id);
    HtmlRendererUtils.writeDataAttributes(facesContext, writer, select);
    writer.writeAttribute(HtmlAttributes.DISABLED, disabled);
    Integer tabIndex = select.getTabIndex();
    if (tabIndex != null) {
      writer.writeAttribute(HtmlAttributes.TABINDEX, tabIndex);
    }
    Style style = new Style(facesContext, select);
    writer.writeStyleAttribute(style);
    writer.writeClassAttribute(Classes.create(select));
    if (title != null) {
      writer.writeAttribute(HtmlAttributes.TITLE, title, true);
    }
    String onchange = HtmlUtils.generateOnchange(select, facesContext);
    if (onchange != null) {
      writer.writeAttribute(HtmlAttributes.ONCHANGE, onchange, true);
    }
    
    // TOBAGO-789
    if (VariableResolverUtils.resolveClientProperties(facesContext).getUserAgent().isMsie6()) {
      writer.writeAttribute(HtmlAttributes.ONFOCUSIN, "Tobago.fixSelectionOnFocusIn()", false);
      writer.writeAttribute(HtmlAttributes.ONFOCUS, "Tobago.fixSelectionOnFocus()", false);
    }
    Object[] values = {select.getValue()};

    HtmlRendererUtils.renderSelectItems(select, items, values, writer, facesContext);

    writer.endElement(HtmlElements.SELECT);
    super.encodeEnd(facesContext, select);
    HtmlRendererUtils.renderFocusId(facesContext, select);
    HtmlRendererUtils.checkForCommandFacet(select, facesContext, writer);
  }
}
