package org.apache.myfaces.tobago.apt.annotation;

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

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

/**
 * Created: Aug 5, 2005 3:11:18 PM
 * User: bommel
 * $Id: $
 */

@Retention(value = RetentionPolicy.SOURCE)
@Target(value = ElementType.TYPE)

public @interface UIComponentTag {

  String uiComponent();

  String rendererType() default "";

  String displayName() default "";

  Facet[] facets() default {};

  boolean isComponentAlreadyDefined() default false;

}
