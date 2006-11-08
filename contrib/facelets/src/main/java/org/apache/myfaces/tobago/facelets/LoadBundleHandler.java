package org.apache.myfaces.tobago.facelets;

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

import com.sun.facelets.tag.TagHandler;
import com.sun.facelets.tag.TagAttribute;
import com.sun.facelets.tag.TagConfig;

import com.sun.facelets.FaceletContext;

import org.apache.myfaces.tobago.util.BundleMapWrapper;

import javax.faces.component.UIComponent;
import javax.faces.FacesException;
import javax.faces.context.FacesContext;
import javax.el.ELException;
import java.io.IOException;

/*
 * Created by IntelliJ IDEA.
 * User: bommel
 * Date: 20.04.2006
 * Time: 19:35:29
 */
public class LoadBundleHandler extends TagHandler {
  private final TagAttribute basename;

  private final TagAttribute var;

  public LoadBundleHandler(TagConfig config) {
    super(config);
    this.basename = this.getRequiredAttribute("basename");
    this.var = this.getRequiredAttribute("var");
  }

  public void apply(FaceletContext faceletContext, UIComponent parent)
      throws IOException, FacesException, ELException {
    String name = basename.getValue(faceletContext);
    BundleMapWrapper map = new BundleMapWrapper(name);
    FacesContext facesContext = faceletContext.getFacesContext();
    // TODO find a better way
    facesContext.getExternalContext().
        getSessionMap().put(var.getValue(faceletContext), map);
        //getRequestMap().put(var.getValue(faceletContext), map);
  }
}
