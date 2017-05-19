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

import org.apache.myfaces.tobago.component.UISelectOneChoice;
import org.apache.myfaces.tobago.internal.util.HtmlRendererUtils;
import org.apache.myfaces.tobago.internal.util.JsonUtils;
import org.apache.myfaces.tobago.internal.util.RenderUtils;
import org.apache.myfaces.tobago.internal.util.SelectItemUtils;
import org.apache.myfaces.tobago.renderkit.css.BootstrapClass;
import org.apache.myfaces.tobago.renderkit.css.CssItem;
import org.apache.myfaces.tobago.renderkit.css.TobagoClass;
import org.apache.myfaces.tobago.renderkit.html.HtmlAttributes;
import org.apache.myfaces.tobago.renderkit.html.HtmlElements;
import org.apache.myfaces.tobago.util.ComponentUtils;
import org.apache.myfaces.tobago.webapp.TobagoResponseWriter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SelectOneChoiceRenderer extends SelectOneRendererBase {

  @Override
  public boolean getRendersChildren() {
    return true;
  }

  @Override
  protected void encodeBeginField(FacesContext facesContext, UIComponent component) throws IOException {
    final UISelectOneChoice select = (UISelectOneChoice) component;
    final TobagoResponseWriter writer = getResponseWriter(facesContext);

    final String clientId = select.getClientId(facesContext);
    final String fieldId = select.getFieldId(facesContext);
    final Iterable<SelectItem> items = SelectItemUtils.getItemIterator(facesContext, select);
    final String title = HtmlRendererUtils.getTitleFromTipAndMessages(facesContext, select);
    final boolean disabled = !items.iterator().hasNext() || select.isDisabled() || select.isReadonly();

    writer.startElement(HtmlElements.SELECT);
    writer.writeIdAttribute(fieldId);
    writer.writeNameAttribute(clientId);
    HtmlRendererUtils.writeDataAttributes(facesContext, writer, select);
    writer.writeAttribute(HtmlAttributes.DISABLED, disabled);
    writer.writeAttribute(HtmlAttributes.TABINDEX, select.getTabIndex());
    writer.writeStyleAttribute(select.getStyle());

    // TODO: optimize class attribute writing
    final List<CssItem> classAttributes = new ArrayList<CssItem>();
    classAttributes.add(TobagoClass.SELECT_ONE_CHOICE);
    classAttributes.addAll(Arrays.asList(
        TobagoClass.SELECT_ONE_CHOICE.createMarkup(ComponentUtils.updateMarkup(select, select.getMarkup()))));
    classAttributes.add(BootstrapClass.FORM_CONTROL);
    classAttributes.add(select.getCustomClass());
    writer.writeClassAttribute(null, null, classAttributes.toArray(new CssItem[classAttributes.size()]));
    if (title != null) {
      writer.writeAttribute(HtmlAttributes.TITLE, title, true);
    }
    writer.writeCommandMapAttribute(JsonUtils.encode(RenderUtils.getBehaviorCommands(facesContext, select)));
    HtmlRendererUtils.renderFocus(clientId, select.isFocus(), ComponentUtils.isError(select), facesContext, writer);

    HtmlRendererUtils.renderSelectItems(select, items, select.getValue(), (String) select.getSubmittedValue(), writer,
        facesContext);

  }

  @Override
  protected void encodeEndField(FacesContext facesContext, UIComponent component) throws IOException {
    final TobagoResponseWriter writer = getResponseWriter(facesContext);
    writer.endElement(HtmlElements.SELECT);
  }
}
