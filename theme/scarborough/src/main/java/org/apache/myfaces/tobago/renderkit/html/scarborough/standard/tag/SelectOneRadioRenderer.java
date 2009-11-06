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
import org.apache.myfaces.tobago.component.UISelectOneRadio;
import org.apache.myfaces.tobago.context.TobagoFacesContext;
import org.apache.myfaces.tobago.layout.Measure;
import org.apache.myfaces.tobago.renderkit.SelectOneRendererBase;
import org.apache.myfaces.tobago.renderkit.css.Style;
import org.apache.myfaces.tobago.renderkit.html.HtmlAttributes;
import org.apache.myfaces.tobago.renderkit.html.HtmlConstants;
import org.apache.myfaces.tobago.renderkit.html.HtmlInputTypes;
import org.apache.myfaces.tobago.renderkit.html.util.HtmlRendererUtils;
import org.apache.myfaces.tobago.renderkit.util.RenderUtil;
import org.apache.myfaces.tobago.webapp.TobagoResponseWriter;

import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectOne;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SelectOneRadioRenderer extends SelectOneRendererBase {

  private static final Log LOG = LogFactory.getLog(SelectOneRadioRenderer.class);

  public void prepareRender(FacesContext facesContext, UIComponent component) throws IOException {
    super.prepareRender(facesContext, component);
    if (facesContext instanceof TobagoFacesContext) {
      ((TobagoFacesContext) facesContext).getOnloadScripts().add("Tobago.selectOneRadioInit('"
          + component.getClientId(facesContext) + "')");
    }
  }

  public void encodeEnd(FacesContext facesContext, UIComponent component) throws IOException {
    if (!(component instanceof UISelectOneRadio)) {
      LOG.error("Wrong type: Need " + UISelectOneRadio.class.getName()
          + ", but was " + component.getClass().getName());
      return;
    }

    UISelectOneRadio select = (UISelectOneRadio) component;
    TobagoResponseWriter writer = HtmlRendererUtils.getTobagoResponseWriter(facesContext);

    String id = select.getClientId(facesContext);
    List<SelectItem> items = RenderUtil.getItemsToRender(select);
    boolean inline = select.isInline();
    String title = HtmlRendererUtils.getTitleFromTipAndMessages(facesContext, select);
    boolean disabled = select.isDisabled();
    boolean readonly = select.isReadonly();
    Style style = new Style(facesContext, select);
    boolean required = select.isRequired();
    // fixme: use CSS, not the Style Attribute for "display"
    style.setDisplay(null);

    writer.startElement(HtmlConstants.OL, select);
    writer.writeStyleAttribute(style);
    writer.writeClassAttribute();
    if (title != null) {
      writer.writeAttribute(HtmlAttributes.TITLE, title, true);
    }

    Object value = select.getValue();
    List<String> clientIds = new ArrayList<String>();
    for (SelectItem item : items) {
      String itemId = id + NamingContainer.SEPARATOR_CHAR + NamingContainer.SEPARATOR_CHAR + item.getValue().toString();
      clientIds.add(itemId);
      writer.startElement(HtmlConstants.LI, select);
      writer.startElement(HtmlConstants.INPUT, select);
      writer.writeAttribute(HtmlAttributes.TYPE, HtmlInputTypes.RADIO, false);
      boolean checked = item.getValue().equals(value);
      writer.writeAttribute(HtmlAttributes.CHECKED, checked);
      writer.writeNameAttribute(id);
      writer.writeIdAttribute(itemId);
      String formattedValue = RenderUtil.getFormattedValue(facesContext, select, item.getValue());
      writer.writeAttribute(HtmlAttributes.VALUE, formattedValue, true);
      writer.writeAttribute(HtmlAttributes.DISABLED, item.isDisabled() || disabled);
      writer.writeAttribute(HtmlAttributes.READONLY, readonly);
      if (!required || readonly) {
        writer.writeAttribute(HtmlAttributes.ONCLICK,
            "Tobago.selectOneRadioClick(this, '" + id + "'," + required + " , " + readonly + ")", false);
      }
      Integer tabIndex = select.getTabIndex();
      if (tabIndex != null) {
        writer.writeAttribute(HtmlAttributes.TABINDEX, tabIndex);
      }
      writer.endElement(HtmlConstants.INPUT);

      String label = item.getLabel();
      if (label != null) {
        writer.startElement(HtmlConstants.LABEL, select);
        writer.writeAttribute(HtmlAttributes.FOR, itemId, false);
        writer.writeText(label);
        writer.endElement(HtmlConstants.LABEL);
      }

      writer.endElement(HtmlConstants.LI);
    }
    writer.endElement(HtmlConstants.OL);

    HtmlRendererUtils.renderFocusId(facesContext, select);
    HtmlRendererUtils.checkForCommandFacet(select, clientIds, facesContext, writer);
  }

  @Override
  public Measure getHeight(FacesContext facesContext, UIComponent component) {
    UISelectOneRadio select = (UISelectOneRadio) component;
    Measure heightOfOne = super.getHeight(facesContext, component);
    if (select.isInline()) {
      return heightOfOne;
    } else {
      List<SelectItem> items = RenderUtil.getItemsToRender((UISelectOne) component);
      return heightOfOne.multiply(items.size());
    }
  }
}
