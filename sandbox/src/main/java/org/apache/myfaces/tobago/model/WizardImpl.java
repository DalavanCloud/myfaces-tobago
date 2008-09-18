package org.apache.myfaces.tobago.model;

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

import javax.faces.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class WizardImpl implements Wizard {

  private static final Log LOG = LogFactory.getLog(WizardImpl.class);

  private int index;

  private List<WizardStep> course;

  public WizardImpl() {
    reset();
  }

  public void next(ActionEvent event) {
    LOG.debug("next: " + event);

    index++;
  }

  public void gotoStep(ActionEvent event) {
    index = Integer.parseInt((String) (event.getComponent().getAttributes().get("step")));

    LOG.debug("gotoStep: " + index);
  }

  public String previous() {
    String outcome = getPreviousStep().getOutcome();
    if (index > 0) {
      index--;
    } else {
      LOG.error("Previous not available!");
    }

    LOG.debug("gotoStep: " + index);
    return outcome;
  }

  public final boolean isPreviousAvailable() {
    return getIndex() > 0;
  }

  public final void leave(ActionEvent event) {
    if (LOG.isDebugEnabled()) {
      LOG.debug("leave");
    }

    reset();
  }

  public final void cancel(ActionEvent event) {
    if (LOG.isDebugEnabled()) {
      LOG.debug("cancel");
    }
    reset();
  }

  public final int getIndex() {
    return index;
  }

  /**
   * Helper method to reset attributes
   */
  protected void reset() {
    index = 0;
    course = new ArrayList<WizardStep>();
  }

  public List<WizardStep> getCourse() {
    return course;
  }

  public void register() {

    if (index == course.size()) { // this is a new page
      course.add(new WizardStep(index));
    } else if (index < course.size()) {
      course.set(index, new WizardStep(index));
    } else {
      throw new IllegalStateException("Index too large for course: index="
          + index + " course.size()=" + course.size());
    }
    if (LOG.isInfoEnabled()) {
      LOG.info("course: " + course);
    }
  }

  public WizardStep getPreviousStep() {
    if (index > 0) {
      return course.get(index - 1);
    } else {
      return null;
    }
  }
  public WizardStep getCurrentStep() {
      return course.get(index);
  }

  public void removeForwardSteps() {
    // todo
    LOG.error("Not implemented yet");
  }
}
