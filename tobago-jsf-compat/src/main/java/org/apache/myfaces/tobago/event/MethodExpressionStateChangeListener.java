package org.apache.myfaces.tobago.event;

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

import javax.el.MethodExpression;
import javax.el.ELContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.context.FacesContext;
import javax.faces.component.StateHolder;


public class MethodExpressionStateChangeListener implements SheetStateChangeListener, StateHolder {

  private MethodExpression methodExpression;

  private boolean isTransient = false;

  public MethodExpressionStateChangeListener() {
  }

  public MethodExpressionStateChangeListener(MethodExpression methodExpression) {
    this.methodExpression = methodExpression;
  }

  public void processSheetStateChange(SheetStateChangeEvent actionEvent) throws AbortProcessingException {
    try {
      Object[] params = new Object[]{actionEvent};
      methodExpression.invoke(elContext(), params);
    } catch (Exception e) {
      throw new AbortProcessingException(e);
    }
  }

  private ELContext elContext() {
    return FacesContext.getCurrentInstance().getELContext();
  }

  public void restoreState(FacesContext context, Object state) {
    methodExpression = (MethodExpression) state;
  }

  public Object saveState(FacesContext context) {
    return methodExpression;
  }

  public void setTransient(boolean newTransientValue) {
    isTransient = newTransientValue;
  }

  public boolean isTransient() {
    return isTransient;
  }

}
