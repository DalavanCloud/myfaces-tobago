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

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import static org.apache.myfaces.tobago.TobagoConstants.ATTR_DISABLED;
import static org.apache.myfaces.tobago.TobagoConstants.ATTR_STYLE;
import static org.apache.myfaces.tobago.TobagoConstants.ATTR_TIP;
import static org.apache.myfaces.tobago.TobagoConstants.FACET_LABEL;
import org.apache.myfaces.tobago.component.ComponentUtil;
import org.apache.myfaces.tobago.context.ClientProperties;
import org.apache.myfaces.tobago.renderkit.InputRendererBase;
import org.apache.myfaces.tobago.renderkit.html.HtmlAttributes;
import org.apache.myfaces.tobago.renderkit.html.HtmlConstants;
import org.apache.myfaces.tobago.util.LayoutUtil;
import org.apache.myfaces.tobago.webapp.TobagoMultipartFormdataRequest;
import org.apache.myfaces.tobago.webapp.TobagoResponseWriterImpl;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;

public class FileRenderer extends InputRendererBase {

  private static final Log LOG = LogFactory.getLog(FileRenderer.class);

  public boolean getRendersChildren() {
    return true;
  }

  public int getComponentExtraWidth(
      FacesContext facesContext, UIComponent component) {
    int space = 0;

    if (component.getFacet(FACET_LABEL) != null) {
      int labelWidht = LayoutUtil.getLabelWidth(component);
      space += labelWidht != 0
          ? labelWidht : getLabelWidth(facesContext, component);
    }

    return space;
  }

  public void decode(FacesContext facesContext, UIComponent component) {
    if (ComponentUtil.isOutputOnly(component)) {
      return;
    }

    UIInput input = (UIInput) component;

    TobagoMultipartFormdataRequest request = null;
    Object requestObject = facesContext.getExternalContext().getRequest();
    if (requestObject instanceof TobagoMultipartFormdataRequest) {
      request = (TobagoMultipartFormdataRequest) requestObject;
    } else if (requestObject instanceof HttpServletRequestWrapper) {
      ServletRequest wrappedRequest
          = ((HttpServletRequestWrapper) requestObject).getRequest();
      if (wrappedRequest instanceof TobagoMultipartFormdataRequest) {
        request = (TobagoMultipartFormdataRequest) wrappedRequest;
      }
    }
    // TODO PortletRequest ??
    if (request == null) {
      // should not be possible, because of the check in UIPage
      LOG.error("Can't process multipart/form-data without TobagoRequest. "
          + "Please check the web.xml and define a TobagoMultipartFormdataFilter. "
          + "See documentation for <tc:file>");
    } else {

      FileItem item = request.getFileItem(input.getClientId(facesContext));

      if (LOG.isDebugEnabled()) {
        LOG.debug("Uploaded file name : \"" + item.getName()
            + "\"  size = " + item.getSize());
      }
      input.setSubmittedValue(item);
      //TODO remove this
      input.setValid(true);
    }
  }

  public void encodeEnd(
      FacesContext facesContext,
      UIComponent uiComponent) throws IOException {

    UIInput component = (UIInput) uiComponent;
    String clientId = component.getClientId(facesContext);

    TobagoResponseWriterImpl writer
        = (TobagoResponseWriterImpl) facesContext.getResponseWriter();

    writer.startElement(HtmlConstants.INPUT, component);
    writer.writeAttribute(HtmlAttributes.TYPE, "file", null);
    writer.writeClassAttribute();
    if (!ClientProperties.getInstance(facesContext).getUserAgent().isMozilla()) {
      writer.writeAttribute(HtmlAttributes.STYLE, null, ATTR_STYLE);
    }
    writer.writeNameAttribute(clientId);
    writer.writeIdAttribute(clientId);
    writer.writeAttribute(HtmlAttributes.READONLY,
        ComponentUtil.getBooleanAttribute(component, ATTR_DISABLED));
    writer.writeAttribute(HtmlAttributes.TITLE, null, ATTR_TIP);
    writer.endElement(HtmlConstants.INPUT);

  }
}

