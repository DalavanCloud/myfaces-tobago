package org.apache.myfaces.tobago.renderkit.fo.standard.standard.tag;

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
import org.apache.myfaces.tobago.component.ComponentUtil;
import org.apache.myfaces.tobago.renderkit.InputRendererBase;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import java.io.IOException;

/**
 * Created: Dec 1, 2004 7:43:42 PM
 * User: bommel
 * $Id:TextAreaRenderer.java 472227 2006-11-07 21:05:00 +0100 (Tue, 07 Nov 2006) bommel $
 */
public class TextAreaRenderer extends InputRendererBase {
  private static final Log LOG = LogFactory.getLog(TextAreaRenderer.class);


  public boolean getRendersChildren() {
    return false;
  }
  public void encodeEnd(FacesContext facesContext,
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
        FoUtils.layoutBlockContainer(writer, FoUtils.DEFAULT_HEIGHT, layout.getWidth(), layout.getX(), layout.getY());
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
