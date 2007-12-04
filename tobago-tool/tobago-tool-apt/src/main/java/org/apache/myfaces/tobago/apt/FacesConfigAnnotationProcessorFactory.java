package org.apache.myfaces.tobago.apt;

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

import com.sun.mirror.apt.AnnotationProcessor;
import com.sun.mirror.apt.AnnotationProcessorEnvironment;
import com.sun.mirror.apt.AnnotationProcessorFactory;
import com.sun.mirror.declaration.AnnotationTypeDeclaration;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

/*
 * Date: Sep 25, 2006
 * Time: 9:29:33 PM
 */
public class FacesConfigAnnotationProcessorFactory implements AnnotationProcessorFactory  {

  private static final Collection<String> SUPPORTED_ANNOTATIONS
      = Collections.unmodifiableCollection(Arrays.asList("org.apache.myfaces.tobago.apt.annotation.Tag",
          "org.apache.myfaces.tobago.apt.annotation.TagAttribute", "org.apache.myfaces.tobago.apt.annotation.Taglib",
          "org.apache.myfaces.tobago.apt.annotation.UIComponentTagAttribute",
          "org.apache.myfaces.tobago.apt.annotation.Converter", 
          "org.apache.myfaces.tobago.apt.annotation.Validator"));

  private static final Collection<String> SUPPORTED_OPTIONS =
      Collections.unmodifiableCollection(Arrays.asList(FacesConfigAnnotationVisitor.SOURCE_FACES_CONFIG_KEY,
          FacesConfigAnnotationVisitor.TARGET_FACES_CONFIG_KEY));

  private FacesConfigAnnotationProcessor annotationProcessor = null;

  public Collection<String> supportedAnnotationTypes() {
    return SUPPORTED_ANNOTATIONS;
  }

  public Collection<String> supportedOptions() {
    return SUPPORTED_OPTIONS;
  }

  public AnnotationProcessor getProcessorFor(Set<AnnotationTypeDeclaration> atds,
      AnnotationProcessorEnvironment env) {
    if (annotationProcessor == null) {
      annotationProcessor = new FacesConfigAnnotationProcessor(atds, env);
    }
    return annotationProcessor;
  }
}
