package org.apache.myfaces.tobago.renderkit.wml.standard.standard.tag;

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
 * : $
 */

import org.apache.commons.collections.keyvalue.DefaultKeyValue;
import static org.apache.myfaces.tobago.TobagoConstants.FACET_LABEL;
import org.apache.myfaces.tobago.util.ComponentUtil;
import org.apache.myfaces.tobago.component.AbstractUIPage;
import org.apache.myfaces.tobago.renderkit.LayoutableRendererBase;
import org.apache.myfaces.tobago.renderkit.util.RenderUtil;
import org.apache.myfaces.tobago.renderkit.html.util.HtmlRendererUtil;
import org.apache.myfaces.tobago.webapp.TobagoResponseWriter;

import javax.faces.component.UIComponent;
import javax.faces.component.UISelectOne;
import javax.faces.component.ValueHolder;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import java.io.IOException;
import java.util.List;

public class SelectOneChoiceRenderer extends LayoutableRendererBase {

  public void encodeEnd(FacesContext facesContext, UIComponent component)
      throws IOException {

    UISelectOne selectOne = (UISelectOne) component;
    AbstractUIPage page = ComponentUtil.findPage(facesContext, selectOne);

    TobagoResponseWriter writer = HtmlRendererUtil.getTobagoResponseWriter(facesContext);

    String clientId = selectOne.getClientId(facesContext);

    if (page != null) {
      page.getPostfields().add(new DefaultKeyValue(clientId, clientId));
    }

    ValueHolder label
        = (ValueHolder) selectOne.getFacet(FACET_LABEL);
    if (label != null) {
      writer.writeText(label.toString());
    }
    List<SelectItem> items = RenderUtil.getSelectItems(selectOne);
    String value = RenderUtil.currentValue(selectOne);

    writer.startElement("select", selectOne);
    writer.writeNameAttribute(clientId);
    writer.writeIdAttribute(clientId);
    writer.writeAttribute("value", value, true);
    writer.writeAttribute("multiple", Boolean.FALSE.toString(), false);

    for (SelectItem item : items) {
      writer.startElement("option", selectOne);
      String formattedValue
          = RenderUtil.getFormattedValue(facesContext, component, item.getValue());
      writer.writeAttribute("value", formattedValue, true);
      writer.writeText(item.getLabel());
      writer.endElement("option");
    }
    writer.endElement("select");
  }

}
