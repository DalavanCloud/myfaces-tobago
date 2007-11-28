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
import org.apache.commons.lang.StringUtils;
import static org.apache.myfaces.tobago.TobagoConstants.ATTR_LABEL;
import static org.apache.myfaces.tobago.TobagoConstants.ATTR_VALUE;
import static org.apache.myfaces.tobago.TobagoConstants.RENDERER_TYPE_LABEL;

import javax.faces.component.UIComponent;
import java.util.Locale;

public final class LabelWithAccessKey {

  private static final Log LOG = LogFactory.getLog(LabelWithAccessKey.class);

  private String text;
  private Character accessKey;
  private int pos = -1;
  public static final char INDICATOR = '_';
  public static final String ESCAPED_INDICATOR = "__";

  public LabelWithAccessKey(UIComponent component) {
    Object value;
    if (RENDERER_TYPE_LABEL.equals(component.getRendererType())) {
      value = component.getAttributes().get(ATTR_VALUE);
    } else {
      value = component.getAttributes().get(ATTR_LABEL);
    }
    text = (value == null) ? null : String.valueOf(value);
    setup(text);
  }

  private void findIndicator(String label, int index, int escapedIndicatorCount) {
    index = label.indexOf(INDICATOR, index);
    if (index == -1) {
      text = label;
    } else if (index == label.length() - 1) {
      LOG.warn(INDICATOR + " in label is last char, this is not allowed"
          + "label='" + label + "'.");
      text = label.substring(0, label.length() - 1);
      pos = -1;
    } else if (label.charAt(index+1) == INDICATOR) {
      escapedIndicatorCount++;
      findIndicator(label, index+2, escapedIndicatorCount);
    } else {
      text = label.substring(0, index)
            + label.substring(index + 1);
      accessKey = text.charAt(index);
      pos = index - escapedIndicatorCount;
    }
  }

  public void setup(String label) {
    if (label != null) {
      findIndicator(label, 0, 0);
      text = StringUtils.replace(text, ESCAPED_INDICATOR, String.valueOf(INDICATOR));
    } else {
      if (accessKey != null && text != null) {
        pos = text.toLowerCase(Locale.ENGLISH).indexOf(
            Character.toLowerCase(accessKey.charValue()));
      }
    }
  }

  public void reset() {
    text = null;
    accessKey = null;
    pos = -1;
  }

  public String getText() {
    return text;
  }

  public Character getAccessKey() {
    return accessKey;
  }

  public int getPos() {
    return pos;
  }

  public void setText(String text) {
    this.text = text;
  }

  public void setAccessKey(Character accessKey) {
    this.accessKey = accessKey;
  }

}
