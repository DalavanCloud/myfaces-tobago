/*
 * Copyright 2002-2005 The Apache Software Foundation.
 * 
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 * 
 *        http://www.apache.org/licenses/LICENSE-2.0
 * 
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.apache.myfaces.tobago.apt.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created: Apr 20, 2005 6:55:06 PM
 * User: bommel
 * $Id: Taglib.java,v 1.1 2005/04/20 18:39:09 bommel Exp $
 */
@Retention(value = RetentionPolicy.SOURCE)
@Target(value = ElementType.PACKAGE)

public @interface Taglib {

  String tlibVersion() default "1.0";
  String jspVersion() default "1.2";
  String shortName();
  String uri();
  String fileName() default "";
  String displayName() default "";
  // TODO change to Class !!
  String [] listener() default {};

}
