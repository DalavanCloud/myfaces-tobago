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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.tobago.renderkit.html.HtmlConstants;
import org.apache.myfaces.tobago.renderkit.html.HtmlAttributes;

import javax.faces.render.ResponseStateManager;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.application.StateManager;
import java.util.Map;
import java.io.IOException;

/*
 * Created by IntelliJ IDEA.
 * User: bommel
 * Date: 21.05.2006
 * Time: 10:59:19
 */
public class TobagoResponseStateManager extends ResponseStateManager {
  private static final Log LOG = LogFactory.getLog(TobagoResponseStateManager.class);

  private static final String TREE_PARAM = "jsf_tree";
  private static final String STATE_PARAM = "jsf_state";
  private static final String VIEWID_PARAM = "jsf_viewid";

  public Object getTreeStructureToRestore(FacesContext facescontext, String viewId) {
    Map requestMap = facescontext.getExternalContext().getRequestParameterMap();
    Object requestViewId = requestMap.get(VIEWID_PARAM);
    if (requestViewId == null || !requestViewId.equals(viewId)) {
      //no saved state or state of different viewId
      return null;
    }

    return requestMap.get(TREE_PARAM);

  }

  public Object getComponentStateToRestore(FacesContext facesContext) {
    Map requestMap = facesContext.getExternalContext().getRequestParameterMap();
    return requestMap.get(STATE_PARAM);
  }

  public void writeState(FacesContext facescontext,
      StateManager.SerializedView serializedview) throws IOException {
    ResponseWriter responseWriter = facescontext.getResponseWriter();
    Object treeStruct = serializedview.getStructure();
    Object compStates = serializedview.getState();

    if (treeStruct != null) {
      if (treeStruct instanceof String) {
        responseWriter.startElement(HtmlConstants.INPUT, null);
        responseWriter.writeAttribute(HtmlAttributes.TYPE, "hidden", null);
        responseWriter.writeAttribute(HtmlAttributes.NAME, TREE_PARAM, null);
        responseWriter.writeAttribute(HtmlAttributes.ID, TREE_PARAM, null);
        responseWriter.writeAttribute(HtmlAttributes.VALUE, treeStruct, null);
        responseWriter.endElement(HtmlConstants.INPUT);
      }
    } else {
      LOG.error("No tree structure to be saved in client response!");
    }

    if (compStates != null) {
      if (compStates instanceof String) {
        responseWriter.startElement(HtmlConstants.INPUT, null);
        responseWriter.writeAttribute(HtmlAttributes.TYPE, "hidden", null);
        responseWriter.writeAttribute(HtmlAttributes.NAME, STATE_PARAM, null);
        responseWriter.writeAttribute(HtmlAttributes.ID, STATE_PARAM, null);
        responseWriter.writeAttribute(HtmlAttributes.VALUE, compStates, null);
        responseWriter.endElement(HtmlConstants.INPUT);
      }
    } else {
      LOG.error("No component states to be saved in client response!");
    }

    responseWriter.startElement(HtmlConstants.INPUT, null);
    responseWriter.writeAttribute(HtmlAttributes.TYPE, "hidden", null);
    responseWriter.writeAttribute(HtmlAttributes.NAME, VIEWID_PARAM, null);
    responseWriter.writeAttribute(HtmlAttributes.ID, VIEWID_PARAM, null);
    responseWriter.writeAttribute(HtmlAttributes.VALUE, facescontext.getViewRoot().getViewId(), null);
    responseWriter.endElement(HtmlConstants.INPUT);
  }

}
