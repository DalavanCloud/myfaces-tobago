package org.apache.myfaces.tobago.example.sandbox;

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
import org.apache.myfaces.tobago.model.Wizard;
import org.apache.myfaces.tobago.model.WizardImpl;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Controller {

  private static final Log LOG = LogFactory.getLog(Controller.class);

  private int sliderValue;

  private Wizard wizard;

  private String filterType;

  public Controller() {
    // wizard

    wizard = new WizardImpl();
  }

  public String action1() {
    LOG.info("action 1");
    return null;
  }

  public String action2() {
    LOG.info("action 2");
    return null;
  }

  public String action3() {
    LOG.info("action 3");
    return null;
  }

  public String createFilter() {
    if ("fileInto".equals(filterType)) {
      return "fileIntoCondition";
    } else if ("forward".equals(filterType)) {
      return "forwardCondition";
    }
    throw new RuntimeException("no filter");
  }

  public String getCurrentTime() {
    return new SimpleDateFormat("hh:MM:ss").format(new Date());
  }

  public int getSliderValue() {
    return sliderValue;
  }

  public void setSliderValue(int sliderValue) {
    this.sliderValue = sliderValue;
  }

  public String sliderSubmit() {
    LOG.info("Slider: " + sliderValue);
    return null;
  }

  public Wizard getWizard() {
    return wizard;
  }

  public String getFilterType() {
    return filterType;
  }

  public void setFilterType(String filterType) {
    this.filterType = filterType;
  }
}
