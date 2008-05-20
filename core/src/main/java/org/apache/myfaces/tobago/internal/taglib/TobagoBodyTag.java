package org.apache.myfaces.tobago.internal.taglib;

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

/*
 * Created on: 15.02.2002, 16:19:49
 * $Id$
 */

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTag;

public abstract class TobagoBodyTag extends TobagoTag implements BodyTag {

  private static final Log LOG = LogFactory.getLog(TobagoBodyTag.class);

  private BodyContent bodyContent;

  public int doAfterBody() throws JspException {
    return getDoAfterBodyValue();
  }

  public void doInitBody() throws JspException {
  }

  protected String getBodyContentStr() {
    String content = bodyContent.getString();
    bodyContent.clearBody();
    return content;
  }

  protected boolean isBodyContentEmpty() {
    if (bodyContent != null) {
      String content = bodyContent.getString();
      String tmp = content.replace('\n', ' ');
      if (tmp.trim().length() > 0) { // if there are only whitespaces: drop bodyContent
        return false;
      }
    }
    return true;
  }

  protected int getDoStartValue() throws JspException {
    return BodyTag.EVAL_BODY_BUFFERED;
  }

  protected int getDoAfterBodyValue() throws JspException {
    return BodyTag.SKIP_BODY;
  }

  public void release() {
    super.release();
    bodyContent = null;
  }

  public void setBodyContent(BodyContent bodyContent) {
    this.bodyContent = bodyContent;
  }
}

