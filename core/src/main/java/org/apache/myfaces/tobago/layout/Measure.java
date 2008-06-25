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
import org.apache.commons.lang.StringUtils;

/*
 * Date: 23.01.2008 20:12:30
 */
public abstract class Measure {

  // todo: refactor and consolidate with LayoutToken

  public static Measure parse(String value) {
    if (StringUtils.isEmpty(value)) {
      return new PixelMeasure(0); // fixme: may return a "default measure", or is Pixel the default?
    }
    if (value.toLowerCase().matches("\\d+px")) {// XXX no regexp here: user LayoutTokens.parse !!!
      return new PixelMeasure(Integer.parseInt(value.substring(0, value.length() - 2)));
    }
    throw new IllegalArgumentException("Can't parse to any measure: '" + value + "'");
  }

  public abstract Measure add(Measure m);

  public abstract Measure substractNotNegative(Measure m);

  public abstract int getPixel();
}
