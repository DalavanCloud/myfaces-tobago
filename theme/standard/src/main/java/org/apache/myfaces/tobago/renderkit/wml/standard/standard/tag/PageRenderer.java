/*
 * Copyright 2002-2005 atanion GmbH.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
/*
 * Created 07.02.2003 16:00:00.
 * : $
 */
package org.apache.myfaces.tobago.renderkit.wml.standard.standard.tag;

import org.apache.myfaces.tobago.component.UIPage;
import org.apache.myfaces.tobago.renderkit.PageRendererBase;
import org.apache.myfaces.tobago.renderkit.RenderUtil;
import org.apache.myfaces.tobago.webapp.TobagoResponseWriter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import java.io.IOException;
import java.io.StringWriter;

public class PageRenderer extends PageRendererBase {

  private static final String DOCTYPE =
      "<?xml version='1.0'?>\n" +
      "<!DOCTYPE wml PUBLIC '-//WAPFORUM//DTD WML 1.1//EN'\n" +
      " 'http://www.wapforum.org/DTD/wml_1.1.xml'>";

  public void encodeEndTobago(FacesContext facesContext, UIComponent component)
      throws IOException {

    UIPage page = (UIPage) component;

    TobagoResponseWriter writer
        = (TobagoResponseWriter) facesContext.getResponseWriter();

    // replace responseWriter and render page content
    StringWriter content = new StringWriter();
    ResponseWriter contentWriter = new TobagoResponseWriter(
        content, writer.getContentType(), writer.getCharacterEncoding());
    facesContext.setResponseWriter(contentWriter);

    RenderUtil.encodeChildren(facesContext, page);

    // reset responseWriter and render page
    facesContext.setResponseWriter(writer);

    writer.write(DOCTYPE);
    writer.write('\n');

    writer.startElement("wml", page);
    writer.startElement("card", page);

    writer.write(content.toString());

    writer.endElement("card");
    writer.endElement("wml");
  }
}

