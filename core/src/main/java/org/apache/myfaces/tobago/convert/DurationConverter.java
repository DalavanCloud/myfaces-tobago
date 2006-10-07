package org.apache.myfaces.tobago.convert;

/*
 * Copyright 2002-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
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
import static org.apache.myfaces.tobago.TobagoConstants.ATTR_UNIT;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

@org.apache.myfaces.tobago.apt.annotation.Converter(id=DurationConverter.CONVERTER_ID)
public class DurationConverter implements Converter {

  private static final Log LOG = LogFactory.getLog(DurationConverter.class);

  public static final String CONVERTER_ID = "org.apache.myfaces.tobago.Duration";

  private static final String NANO = "nano";
  private static final String MILLI = "milli";
  private static final String SECOND = "second";
  private static final String MINUTE = "minute";
  private static final String HOUR = "hour";
  private static final String DAY = "day";
  private static final String YEAR = "year";

  public String getAsString(
      FacesContext facesContext, UIComponent component, Object object)
      throws ConverterException {
    if (object == null || object instanceof String) {
      return (String) object;
    }
    double aDouble = ((Number) object).doubleValue();
    boolean negative = false;
    if (aDouble < 0) {
      negative = true;
      aDouble = -aDouble;
    }
    double factor = getUnitFactor(component);
    aDouble = aDouble * factor;

    NumberFormat format = new DecimalFormat("00");
    long value = new Double(aDouble).longValue();
    int seconds = (int) (value % 60);
    value = value / 60;
    int minutes = (int) (value % 60);
    value = value / 60;
    String string;
    if (value > 0) {
      string = (negative ? "-" : "") + value + ":"
          + format.format(minutes) + ":"
          + format.format(seconds);
    } else {
      string = (negative ? "-" : "") + minutes + ":"
          + format.format(seconds);
    }
    if (LOG.isDebugEnabled()) {
      LOG.debug("string = '" + string + "'");
    }
    return string;
  }

  public Object getAsObject(
      FacesContext facesContext, UIComponent component, String string)
      throws ConverterException {
    boolean negative = string.indexOf('-') > -1;
    StringTokenizer tokenizer = new StringTokenizer(string, " :-");
    List elements = new ArrayList();
    while (tokenizer.hasMoreElements()) {
      elements.add(tokenizer.nextElement());
    }
    int hours = 0;
    int minutes;
    int seconds;
    switch (elements.size()) {
      case 3:
        hours = Integer.parseInt((String) elements.get(0));
        minutes = Integer.parseInt((String) elements.get(1));
        seconds = Integer.parseInt((String) elements.get(2));
        break;
      case 2:
        minutes = Integer.parseInt((String) elements.get(0));
        seconds = Integer.parseInt((String) elements.get(1));
        break;
      default:
        throw new ConverterException("Cannot parse string='" + string + "'");
    }
    double factor = getUnitFactor(component);
    long value = (long) (((hours * 60L + minutes) * 60L + seconds) / factor);
    if (negative) {
      return new Long(-value);
    } else {
      return new Long(value);
    }
  }

  private static double getUnitFactor(UIComponent component) {
    String unit = null;
    if (component != null) {
      unit = (String) component.getAttributes().get(ATTR_UNIT);
    }
    double factor;
    if (unit == null) {
      factor = 0.001;
    } else if (NANO.equals(unit)) {
      factor = 0.000000001;
    } else if (MILLI.equals(unit)) {
      factor = 0.001;
    } else if (SECOND.equals(unit)) {
      factor = 1.0;
    } else if (MINUTE.equals(unit)) {
      factor = 60.0;
    } else if (HOUR.equals(unit)) {
      factor = 3600.0;
    } else if (DAY.equals(unit)) {
      factor = 86400.0;
    } else if (YEAR.equals(unit)) {
      factor = 31556736.0;
    } else {
      LOG.warn("Unsupported unit: '" + unit + "'");
      factor = 0.001;
    }
    return factor;
  }

}
