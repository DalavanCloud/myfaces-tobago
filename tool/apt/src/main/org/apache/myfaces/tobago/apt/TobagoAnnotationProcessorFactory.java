/*
 * Copyright 2002-2005 atanion GmbH.
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
package org.apache.myfaces.tobago.apt;

import com.sun.mirror.apt.AnnotationProcessorFactory;
import com.sun.mirror.apt.AnnotationProcessor;
import com.sun.mirror.apt.AnnotationProcessorEnvironment;
import com.sun.mirror.declaration.AnnotationTypeDeclaration;

import java.util.Collection;
import java.util.Collections;
import java.util.Arrays;
import java.util.Set;

/**
 * Copyright (c) 2003 Atanion GmbH, Germany. All rights reserved.
 * Created: Apr 27, 2005 4:27:27 PM
 * User: bommel
 * $Id: TobagoAnnotationProcessorFactory.java,v 1.1 2005/05/11 15:20:34 bommel Exp $
 */
public class TobagoAnnotationProcessorFactory implements AnnotationProcessorFactory  {

  private TobagoAnnotationProcessor annotationProcessor = null;

  private static final Collection<String> supportedAnnotations
      = Collections.unmodifiableCollection(Arrays.asList("org.apache.myfaces.tobago.apt.annotation.Tag",
          "org.apache.myfaces.tobago.apt.annotation.TagAttribute", "org.apache.myfaces.tobago.apt.annotation.Taglib",
          "org.apache.myfaces.tobago.apt.annotation.UIComponentTagAttribute" ));

  private static final Collection<String> supportedOptions = Collections.emptyList();

  public Collection<String> supportedAnnotationTypes() {
    return supportedAnnotations;
  }

  public Collection<String> supportedOptions() {
    return supportedOptions;
  }

  public AnnotationProcessor getProcessorFor(Set<AnnotationTypeDeclaration> atds,
      AnnotationProcessorEnvironment env) {
    if (annotationProcessor == null) {
      annotationProcessor = new TobagoAnnotationProcessor(atds, env);
    }
    return annotationProcessor;
  }


}
