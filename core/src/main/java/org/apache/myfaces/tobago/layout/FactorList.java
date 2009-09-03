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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.tobago.layout.math.MathUtils;

import java.util.ArrayList;
import java.util.List;

public class FactorList extends ArrayList<Integer> {

  private static final Log LOG = LogFactory.getLog(FactorList.class);

  public List<Measure> partition(Measure size) {

    List<Measure> result = new ArrayList<Measure>(size());

    int sum = 0;
    for (Integer integer : this) {
      sum += integer;
    }

    double[] doubles = new double[size()];
    int i = 0;
    for (Integer integer : this) {
      doubles[i++] = integer * size.getPixel() / (double) sum;
    }

    MathUtils.adjustRemainders(doubles, 0.0);

    for (double value : doubles) {
      result.add(new PixelMeasure((int) value));
    }

    return result;
  }
}
