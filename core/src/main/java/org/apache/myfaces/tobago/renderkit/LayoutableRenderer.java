package org.apache.myfaces.tobago.renderkit;

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

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import java.io.IOException;


public interface LayoutableRenderer {
  void decode(FacesContext context, UIComponent component);

  void encodeBegin(FacesContext context, UIComponent component) throws IOException;

  void encodeChildren(FacesContext context, UIComponent component) throws IOException;

  void encodeEnd(FacesContext context, UIComponent component) throws IOException;

  String convertClientId(FacesContext context, String clientId);

  boolean getRendersChildren();

  Object getConvertedValue(FacesContext context, UIComponent component, Object submittedValue)
      throws ConverterException;

  String getRendererName(String rendererType);

  void prepareRender(FacesContext facesContext, UIComponent component) throws IOException;
}
