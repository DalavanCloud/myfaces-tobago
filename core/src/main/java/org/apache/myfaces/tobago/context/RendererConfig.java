package org.apache.myfaces.tobago.context;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import java.io.Serializable;
import java.util.Locale;

/*
 * Created by IntelliJ IDEA.
 * User: bommel
 * Date: Sep 25, 2006
 * Time: 10:54:19 AM
 */
public class RendererConfig implements Serializable {

  private static final long serialVersionUID = 1L;

  private String name;
  private MarkupConfig markupConfig;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name.substring(0, 1).toLowerCase(Locale.ENGLISH) + name.substring(1);
  }

  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    final RendererConfig that = (RendererConfig) o;

    return name.equals(that.name);

  }

  public boolean contains(String markup) {
    return markupConfig.contains(markup);
  }

  public int hashCode() {
    return name.hashCode();
  }

  public MarkupConfig getMarkupConfig() {
    return markupConfig;
  }

  public void setMarkupConfig(MarkupConfig markupConfig) {
    this.markupConfig = markupConfig;
  }

  public String toString() {
    return "RendererConfig: " + getName() + " " + markupConfig;
  }

}
