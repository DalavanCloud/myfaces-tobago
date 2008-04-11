package org.apache.myfaces.tobago.renderkit.html.example.standard.tag;

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

import org.apache.myfaces.tobago.component.UIInput;
import org.apache.myfaces.tobago.renderkit.html.util.HtmlRendererUtil;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Simple example for dynamic markups.
 * <p>
 * The markup 'changeaware' tracks changes
 * of an input field and applies the style class 'example-changed' if the
 * current value differs from the original one.
 * </p><p>
 * The markup 'blink' highlights the input field with the markup related
 * style class 'tobago-in-markup-blink'. This class is removed after a
 * certain amount time, which is specified in the Blinker JavaScript object
 * in tobago.js.
 * </p>
 *
 */
public class InRenderer extends org.apache.myfaces.tobago.renderkit.html.scarborough.standard.tag.InRenderer{

  @Override
  public void encodeEnd(FacesContext facesContext, UIComponent component) throws IOException {
    super.encodeEnd(facesContext, component);

    UIInput input = (UIInput) component;
    List<String> markup = Arrays.asList(input.getMarkup());
    if (markup.contains("changeaware")) {
      String id = input.getClientId(facesContext);
      final String[] cmds = {
          "new Example.ChangeAware('" + id + "');"
      };
      HtmlRendererUtil.writeScriptLoader(facesContext, null, cmds);
    }
    if (markup.contains("blink")) {
      String id = input.getClientId(facesContext);
      final String[] cmds = {
          "new Example.Blinker('" + id + "');"
      };
      HtmlRendererUtil.writeScriptLoader(facesContext, null, cmds);
    }
  }

}
