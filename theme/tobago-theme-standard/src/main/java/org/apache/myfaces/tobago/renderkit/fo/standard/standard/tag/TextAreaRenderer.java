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
package org.apache.myfaces.tobago.renderkit.fo.standard.standard.tag;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.tobago.component.ComponentUtil;
import org.apache.myfaces.tobago.renderkit.InputRendererBase;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import java.io.IOException;

/**
 * Created: Dec 1, 2004 7:43:42 PM
 * User: bommel
 * $Id$
 */
public class TextAreaRenderer extends InputRendererBase {
  private static final Log LOG = LogFactory.getLog(TextAreaRenderer.class);

  public void encodeEnd(FacesContext facesContext, UIComponent component)
      throws IOException {
    if (LOG.isDebugEnabled()) {
      LOG.debug("*** end      " + component);
    }
    try {
      encodeEndTobago(facesContext, component);
    } catch (IOException e) {
      throw e;
    } catch (RuntimeException e) {
      LOG.error("catched " + e + " :" + e.getMessage(), e);
      throw e;
    } catch (Throwable e) {
      LOG.error("catched Throwable :", e);
      throw new RuntimeException(e);
    }
    if (LOG.isDebugEnabled()) {
      LOG.debug("*   end      " + component);
    }
  }

  public boolean getRendersChildren() {
    return false;
  }
  public void encodeEndTobago(FacesContext facesContext,
        UIComponent component) throws IOException {
      String text = ComponentUtil.currentValue(component);
      if (text == null) {
        text = "";
      }
      Layout layout = Layout.getLayout(component.getParent());
      //layout.addMargin(200, 0, 0, 0);

      ResponseWriter writer = facesContext.getResponseWriter();
     if (!Layout.isInLayout(component)) {
        FoUtils.startBlockContainer(writer, component);
        FoUtils.layoutBlockContainer(writer, FoUtils.DEFAULT_HEIGHT,layout.getWidth(), layout.getX(), layout.getY() );
      }
      FoUtils.writeTextBlockAlignLeft(writer, component, "TextArea");
      if (!Layout.isInLayout(component)) {
        FoUtils.endBlockContainer(writer);
      }
      if (!Layout.isInLayout(component)) {
        layout.addMargin(200, 0, 0, 0);
      }


    }


}
