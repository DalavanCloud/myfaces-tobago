package org.apache.myfaces.tobago.layout.grid;

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

import org.apache.myfaces.tobago.layout.LayoutComponent;

public class RealCell implements Cell {

  private LayoutComponent component;
  private int columnSpan;
  private int rowSpan;

  public RealCell(LayoutComponent component) {
    this.component = component;
  }

  public LayoutComponent getComponent() {
    return component;
  }

  public RealCell getRealCell() {
    return this;
  }

  public int getColumnSpan() {
    return columnSpan;
  }

  public void setColumnSpan(int columnSpan) {
    this.columnSpan = columnSpan;
  }

  public int getRowSpan() {
    return rowSpan;
  }

  public void setRowSpan(int rowSpan) {
    this.rowSpan = rowSpan;
  }
}
