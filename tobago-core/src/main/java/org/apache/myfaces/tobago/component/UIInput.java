package org.apache.myfaces.tobago.component;

/*
 * Copyright 2002-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
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
import org.apache.myfaces.tobago.ajax.api.AjaxComponent;
import org.apache.myfaces.tobago.ajax.api.AjaxPhaseListener;
import org.apache.myfaces.tobago.ajax.api.AjaxUtils;

import javax.faces.context.FacesContext;
import javax.faces.el.MethodBinding;
import java.io.IOException;

public class UIInput extends javax.faces.component.UIInput implements AjaxComponent {

  private static final Log LOG = LogFactory.getLog(UIInput.class);
  public static final String COMPONENT_TYPE = "org.apache.myfaces.tobago.Input";

  private javax.faces.el.MethodBinding suggestMethod;

  public void restoreState(FacesContext context, Object state) {
    Object[] values = (Object[]) state;
    super.restoreState(context, values[0]);
    suggestMethod = (MethodBinding) restoreAttachedState(context, values[1]);
  }

  public Object saveState(FacesContext context) {
    Object[] values  = new Object[2];
    values[0] = super.saveState(context);
    values[1] = saveAttachedState(context, suggestMethod);
    return values;
  }
  
  public MethodBinding getSuggestMethod() {
    return suggestMethod;
  }

  public void setSuggestMethod(MethodBinding suggestMethod) {
    this.suggestMethod = suggestMethod;
  }


  // TODO can this removed?
  public void updateModel(FacesContext facesContext) {
    if (ComponentUtil.mayUpdateModel(this)) {
      super.updateModel(facesContext);
    }
  }

  public void encodeBegin(FacesContext facesContext) throws IOException {
    // TODO change this should be renamed to DimensionUtils.prepare!!!
    UILayout.getLayout(this).layoutBegin(facesContext, this);    
    super.encodeBegin(facesContext);
  }

  public void encodeAjax(FacesContext facesContext) throws IOException {
    AjaxUtils.encodeAjaxComponent(facesContext, this);
  }

  public void processAjax(FacesContext facesContext) throws IOException {
    final String ajaxId = (String) facesContext.getExternalContext().
        getRequestParameterMap().get(AjaxPhaseListener.AJAX_COMPONENT_ID);
    if (ajaxId.equals(getClientId(facesContext))) {
      encodeAjax(facesContext);
    } else {
      AjaxUtils.processAjaxOnChildren(facesContext, this);
    }
  }
}
