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

import org.apache.myfaces.tobago.component.UIData;

import javax.faces.event.ActionEvent;


public class PageActionEvent extends ActionEvent {

  private static final long serialVersionUID = 3364193750247386220L;

  private PageAction action;
  private int value;

  public PageActionEvent(UIData component, PageAction action) {
    super(component);
    this.action = action;
  }

  /**
   * Returns the action type ({@link PageAction}).
   */
  public PageAction getAction() {
    return action;
  }

  public void setValue(int value) {
    this.value = value;
  }

  /**
   * Returns the value for action types {@link PageAction#TO_ROW}
   * and {@link PageAction#TO_PAGE}.
   */
  public int getValue() {
    return value;
  }

  /**
   * Convenience method to get the UIData Component.
   */
  public UIData getSheet() {
    return (UIData) getComponent();
  }
}
