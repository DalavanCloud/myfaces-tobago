package org.apache.myfaces.tobago.layout;

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

import org.apache.myfaces.tobago.layout.math.EquationManager;

/*
Thinking about an alternative algorithm to the SystemOfEquations ...

- get UIPage
  - call compute-sizes
    - get the LayoutManager
      - go to the PX elements
        - call compute-sizes (recursively)
      - go to the Auto elements
        - call compute-sizes (recursively)
        - compute the max size of the elements and set it to the row/column
      - calculate remainder = given size - all px sizes - all auto sizes
      - go to the * elements
        - partition remainder to this elements (3*;2*)
        - call compute-sizes (recursively)
  - call set-positions
      - compute and set positions of columns/rows
      - call set-positions for all elements (recursively)
 */
public class LayoutContext {

  private EquationManager horizontal;
  private EquationManager vertical;
  private LayoutContainer container;

  public LayoutContext(LayoutContainer container) {
    this.horizontal = new EquationManager();
    this.vertical = new EquationManager();
    this.container = container;
  }

  public EquationManager getHorizontal() {
    return horizontal;
  }

  public EquationManager getVertical() {
    return vertical;
  }

  public void layout() {

    horizontal.addComponentRoot();
    vertical.addComponentRoot();

    if (container.getWidth() != null) {
      horizontal.setFixedLength(0, container.getWidth(), container);
    }
    if (container.getHeight() != null) {
      vertical.setFixedLength(0, container.getHeight(), container);
    }

    container.getLayoutManager().collect(this, container, 0, 0);

    horizontal.solve();
    vertical.solve();

    container.getLayoutManager().distribute(this, container);
  }
}
