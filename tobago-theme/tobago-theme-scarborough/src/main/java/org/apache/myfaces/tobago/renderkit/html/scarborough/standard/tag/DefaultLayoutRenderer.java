/*
 * Copyright 2002-2005 The Apache Software Foundation.
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
package org.apache.myfaces.tobago.renderkit.html.scarborough.standard.tag;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.tobago.renderkit.LayoutRenderer;
import org.apache.myfaces.tobago.renderkit.RenderUtil;
import org.apache.myfaces.tobago.renderkit.html.HtmlRendererUtil;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.Iterator;

/**
 * User: weber
 * Date: Feb 22, 2005
 * Time: 3:05:58 PM
 */
public class DefaultLayoutRenderer extends LayoutRenderer {

  private static final Log LOG = LogFactory.getLog(DefaultLayoutRenderer.class);

  public void layoutWidth(FacesContext facesContext, UIComponent component) {
    HtmlRendererUtil.layoutWidth(facesContext, component);
  }

  public void layoutHeight(FacesContext facesContext, UIComponent component) {
    HtmlRendererUtil.layoutHeight(facesContext, component);
  }

  public void prepareRender(FacesContext facesContext, UIComponent component) {
    HtmlRendererUtil.prepareRender(facesContext, component);
  }

  public void encodeChildrenOfComponent(FacesContext facesContext, UIComponent component)
      throws IOException {
      for (Iterator i = component.getChildren().iterator(); i.hasNext();) {
        UIComponent child = (UIComponent) i.next();
        RenderUtil.encode(facesContext, child);
      }
  }
}
