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
package org.apache.myfaces.tobago.apt;

import com.sun.mirror.apt.AnnotationProcessor;
import com.sun.mirror.apt.AnnotationProcessorEnvironment;
import com.sun.mirror.apt.AnnotationProcessorFactory;
import com.sun.mirror.declaration.AnnotationTypeDeclaration;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

/**
 * Created: Mar 5, 2005 12:39:32 PM
 * User: bommel
 * $Id: TaglibAnnotationProcessorFactory.java,v 1.3 2005/04/20 18:39:06 bommel Exp $
 */
public class TaglibAnnotationProcessorFactory implements AnnotationProcessorFactory {

  private TaglibAnnotationProcessor annotationProcessor = null;

  private static final Collection<String> supportedAnnotations
      = Collections.unmodifiableCollection(Arrays.asList("org.apache.myfaces.tobago.apt.annotation.Tag",
          "org.apache.myfaces.tobago.apt.annotation.TagAttribute", "org.apache.myfaces.tobago.apt.annotation.Taglib" ));

  private static final Collection<String> supportedOptions =
      Collections.unmodifiableCollection(Arrays.asList("package", "file"));

  public Collection<String> supportedAnnotationTypes() {
    return supportedAnnotations;
  }

  public Collection<String> supportedOptions() {
    return supportedOptions;
  }

  public AnnotationProcessor getProcessorFor(Set<AnnotationTypeDeclaration> atds,
      AnnotationProcessorEnvironment env) {
    if (annotationProcessor == null) {
      annotationProcessor = new TaglibAnnotationProcessor(atds, env);
    }
    return annotationProcessor;
  }

}
