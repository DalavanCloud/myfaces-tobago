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

import javax.faces.component.UIComponent;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import java.io.IOException;

/**
 * Created: Dec 1, 2004 7:49:55 PM
 * User: bommel
 * $Id$
 */
public class LabelRenderer extends FoRendererBase {

  public void encodeEndTobago(
      FacesContext facesContext, UIComponent component) throws IOException {

    UIOutput output = (UIOutput) component;


    ResponseWriter writer = facesContext.getResponseWriter();
    FoUtils.writeTextBlockAlignLeft(writer, component, "Label");
    if (output.getValue() != null) {
      //writer.writeText("Label", null);
    }
    //writer.endElement("fo:block");


  }

}
