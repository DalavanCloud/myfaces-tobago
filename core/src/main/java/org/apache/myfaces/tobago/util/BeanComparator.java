package org.apache.myfaces.tobago.util;

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

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Comparator;

/**
 * Created: Mon Apr 15 15:56:44 2002
 * @author Volker Weber
 */

public class BeanComparator extends ComparatorBase {

  private static final Log LOG = LogFactory.getLog(BeanComparator.class);

  private String property;


  public BeanComparator(String property) {
    this.property = property;
  }

  public BeanComparator(String property, boolean reverse) {
    super(reverse);
    this.property = property;
  }

  public BeanComparator(String property, Comparator comparator) {
    super(comparator);
    this.property = property;
  }

  public BeanComparator(String property, Comparator comparator, boolean reverse) {
    super(reverse, comparator);
    this.property = property;
  }

  /**
   *
   * @param param1 <description>
   * @return <description>
   */
  public boolean equals(Object param1) {
    if (this == param1) {
      return true;
    }
    if (param1 instanceof BeanComparator) {
      return ((BeanComparator) param1).getProperty().equals(property)
          && super.equals(param1);
    }
    return false;
  }

  public int hashCode() {
    int result;
    result = (property != null ? property.hashCode() : 0);
    result = 29 * result + super.hashCode();
    return result;
  }

  // implementation of java.util.Comparator interface

  /**
   *
   * @param param1 <description>
   * @param param2 <description>
   * @return <description>
   */
  public int compare(Object param1, Object param2){
    Object obj1;
    Object obj2;
    try {
      obj1 = PropertyUtils.getProperty(param1, property);
      obj2 = PropertyUtils.getProperty(param2, property);

    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      return 0;
    }

    return internalCompare(obj1, obj2);
  }

  public String getProperty() {
    return this.property;
  }
}
