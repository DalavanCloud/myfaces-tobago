package org.apache.myfaces.tobago.context;

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

import org.apache.commons.collections.list.SetUniqueList;
import org.apache.commons.collections.set.ListOrderedSet;
import org.apache.myfaces.tobago.component.AbstractUIPopup;

import javax.faces.context.FacesContext;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;


public class TobagoFacesContext extends FacesContextWrapper {

  public static final String DEFAULT_STYLE = "style/style.css";

  private SetUniqueList scriptFiles;

  private Set<String> scriptBlocks;

  private Set<String> styleFiles;

  private Set<String> styleBlocks;

  private Set<String> onloadScripts;

  private Set<String> onunloadScripts;

  private Set<String> onexitScripts;

  private Set<String> onsubmitScripts;

  private Set<AbstractUIPopup> popups;

  private String enctype;

  private boolean ajax;


  public TobagoFacesContext(FacesContext context) {
    super(context);
    scriptFiles = SetUniqueList.decorate(new ArrayList());
    scriptBlocks = new ListOrderedSet();
    styleFiles = new ListOrderedSet();
    styleFiles.add(DEFAULT_STYLE);
    styleBlocks = new ListOrderedSet();
    onloadScripts = new ListOrderedSet();
    onunloadScripts = new ListOrderedSet();
    onexitScripts = new ListOrderedSet();
    onsubmitScripts = new ListOrderedSet();
    popups = new ListOrderedSet();
  }

  public boolean isAjax() {
    return ajax;
  }

  public void setAjax(boolean ajax) {
    this.ajax = ajax;
  }

  public String getEnctype() {
    return enctype;
  }

  public void setEnctype(String enctype) {
    this.enctype = enctype;
  }

  @SuppressWarnings("unchecked")
  public List<String> getScriptFiles() {
    return scriptFiles;
  }

  public Set<String> getScriptBlocks() {
    return scriptBlocks;
  }

  public Set<String> getStyleFiles() {
    return styleFiles;
  }

  public Set<String> getStyleBlocks() {
    return styleBlocks;
  }

  public Set<String> getOnloadScripts() {
    return onloadScripts;
  }

  public Set<String> getOnunloadScripts() {
    return onunloadScripts;
  }

  public Set<String> getOnexitScripts() {
    return onexitScripts;
  }

  public Set<String> getOnsubmitScripts() {
    return onsubmitScripts;
  }

  public Set<AbstractUIPopup> getPopups() {
    return popups;
  }

  private void clearScriptsAndPopups() {
    // clear script Set's
    getOnloadScripts().clear();
    getOnunloadScripts().clear();
    getOnexitScripts().clear();
    getScriptBlocks().clear();
    getPopups().clear();
  }

}
