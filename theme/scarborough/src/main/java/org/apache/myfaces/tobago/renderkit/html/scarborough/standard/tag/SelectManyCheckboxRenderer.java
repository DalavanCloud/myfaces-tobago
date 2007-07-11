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
import static org.apache.myfaces.tobago.TobagoConstants.ATTR_INLINE;
import org.apache.myfaces.tobago.component.ComponentUtil;
import org.apache.myfaces.tobago.renderkit.RenderUtil;
import org.apache.myfaces.tobago.renderkit.SelectManyRendererBase;
import org.apache.myfaces.tobago.renderkit.html.HtmlAttributes;
import org.apache.myfaces.tobago.renderkit.html.HtmlConstants;
import org.apache.myfaces.tobago.renderkit.html.HtmlRendererUtil;
import org.apache.myfaces.tobago.renderkit.html.StyleClasses;
import org.apache.myfaces.tobago.webapp.TobagoResponseWriter;

import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectMany;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SelectManyCheckboxRenderer extends SelectManyRendererBase {

  private static final Log LOG = LogFactory.getLog(SelectManyCheckboxRenderer.class);

  public void encodeEnd(FacesContext facesContext,
      UIComponent uiComponent) throws IOException {

    UISelectMany component = (UISelectMany) uiComponent;

    List<SelectItem> items = ComponentUtil.getItemsToRender(component);

    TobagoResponseWriter writer = HtmlRendererUtil.getTobagoResponseWriter(facesContext);

    Object[] values = component.getSelectedValues();
    if (LOG.isDebugEnabled()) {
      LOG.debug("values = '" + Arrays.toString(values) + "'");
    }
    String id = component.getClientId(facesContext);

    boolean inline = ComponentUtil.getBooleanAttribute(component, ATTR_INLINE);
    String title = HtmlRendererUtil.getTitleFromTipAndMessages(facesContext, component);
    if (!inline) {
      writer.startElement(HtmlConstants.TABLE, component);
      // TODO writer.writeComponentClass();
      writer.writeAttribute(HtmlAttributes.BORDER, 0);
      writer.writeAttribute(HtmlAttributes.CELLSPACING, 0);
      writer.writeAttribute(HtmlAttributes.CELLPADDING, 0);
      writer.writeAttribute(HtmlAttributes.SUMMARY, "", false);
      writer.writeStyleAttribute();
      if (title != null) {
        writer.writeAttribute(HtmlAttributes.TITLE, title, true);
      }
    }
    List<String> clientIds = new ArrayList<String>();
    for (SelectItem item : items) {

      if (!inline) {
        writer.startElement(HtmlConstants.TR, null);
        writer.startElement(HtmlConstants.TD, null);
      }
      String itemId = id
          + NamingContainer.SEPARATOR_CHAR + NamingContainer.SEPARATOR_CHAR
          + item.getValue().toString();
      clientIds.add(itemId);
      writer.startElement(HtmlConstants.INPUT, component);
      writer.writeAttribute(HtmlAttributes.TYPE, "checkbox", false);

      writer.writeClassAttribute();
      writer.writeAttribute(HtmlAttributes.CHECKED, RenderUtil.contains(values, item.getValue()));
      writer.writeNameAttribute(id);
      writer.writeIdAttribute(itemId);
      String formattedValue = RenderUtil.getFormattedValue(facesContext, component, item.getValue());
      writer.writeAttribute(HtmlAttributes.VALUE, formattedValue, true);
      writer.writeAttribute(HtmlAttributes.DISABLED, item.isDisabled());
      writer.endElement(HtmlConstants.INPUT);

      if (LOG.isDebugEnabled()) {
        LOG.debug("item.getLabel() = " + item.getLabel());
      }
      if (item.getLabel() != null) {

        if (!inline) {
          writer.endElement(HtmlConstants.TD);
          writer.startElement(HtmlConstants.TD, null);
          writer.writeStyleAttribute("width: 100%;"); // todo: make more nice with a layout-manager!
        }
        // FIXME: use created UIOutput Label
        // FIXME: see outcommented part
        writer.startElement(HtmlConstants.LABEL, null);
        // todo: use label component with a "light" markup
        StyleClasses styleClasses = new StyleClasses();
        styleClasses.addAspectClass("label", StyleClasses.Aspect.DEFAULT);
        if (item.isDisabled()) {
          styleClasses.addAspectClass("label", StyleClasses.Aspect.DISABLED);
        }
        writer.writeClassAttribute(styleClasses);
        writer.writeAttribute(HtmlAttributes.FOR, itemId, false);
        writer.writeText(item.getLabel());
        writer.endElement(HtmlConstants.LABEL);
//        Application application = tobagoContext.getApplication();
//        UIOutput label = (UIOutput)
//            application.createComponent(TobagoConstants.COMPONENT_TYPE_OUTPUT);
//        label.getAttributes().put(TobagoConstants.ATTR_FOR, itemId);
//        label.setValue( item.getLabel() );
//        label.setRendererType("Label");
//        label.setRendered(true);
//
//        RenderUtil.encode(label);
      }
      if (!inline) {
        writer.endElement(HtmlConstants.TD);
        writer.endElement(HtmlConstants.TR);
      }
    }
    if (!inline) {
      writer.endElement(HtmlConstants.TABLE);
    }
    checkForCommandFacet(component, clientIds, facesContext, writer);
  }

  public int getFixedHeight(FacesContext facesContext, UIComponent component) {
    int heightPerRow = super.getFixedHeight(facesContext, component);
    if (ComponentUtil.getBooleanAttribute(component, ATTR_INLINE)) {
      return heightPerRow;
    } else {
      List<SelectItem> items = ComponentUtil.getItemsToRender((UISelectMany) component);
      return items.size() * heightPerRow;
    }
  }
}
