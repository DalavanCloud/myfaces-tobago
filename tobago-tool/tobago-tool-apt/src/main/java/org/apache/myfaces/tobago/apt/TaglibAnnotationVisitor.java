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

import com.sun.mirror.apt.AnnotationProcessorEnvironment;
import com.sun.mirror.apt.Filer;
import com.sun.mirror.declaration.ClassDeclaration;
import com.sun.mirror.declaration.Declaration;
import com.sun.mirror.declaration.InterfaceDeclaration;
import com.sun.mirror.declaration.MethodDeclaration;
import com.sun.mirror.declaration.PackageDeclaration;
import com.sun.mirror.type.InterfaceType;
import org.apache.commons.io.IOUtils;
import org.apache.myfaces.tobago.apt.annotation.BodyContent;
import org.apache.myfaces.tobago.apt.annotation.BodyContentDescription;
import org.apache.myfaces.tobago.apt.annotation.ExtensionTag;
import org.apache.myfaces.tobago.apt.annotation.Facet;
import org.apache.myfaces.tobago.apt.annotation.Preliminary;
import org.apache.myfaces.tobago.apt.annotation.Tag;
import org.apache.myfaces.tobago.apt.annotation.TagAttribute;
import org.apache.myfaces.tobago.apt.annotation.Taglib;
import org.apache.myfaces.tobago.apt.annotation.UIComponentTag;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

/*
 * Created: Mar 22, 2005 8:18:35 PM
 * User: bommel
 */
public class TaglibAnnotationVisitor extends AbstractAnnotationVisitor {

  private Set<String> tagSet = new HashSet<String>();
  private Set<String> attributeSet = new HashSet<String>();
  private String currentTag;

  public TaglibAnnotationVisitor(AnnotationProcessorEnvironment env) {
    super(env);
  }

  public void process() throws Exception {
    for (PackageDeclaration packageDeclaration : getCollectedPackageDeclarations()) {
      Taglib taglibAnnotation = packageDeclaration.getAnnotation(Taglib.class);
      Document document = createTaglib(taglibAnnotation, packageDeclaration);
      writeTaglib(packageDeclaration, taglibAnnotation, document);
    }
  }

  private Document createTaglib(Taglib taglibAnnotation, PackageDeclaration packageDeclaration) throws
      ParserConfigurationException {
    resetDuplicateList();
    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    dbf.setValidating(false);
    javax.xml.parsers.DocumentBuilder parser = dbf.newDocumentBuilder();

    Document document = parser.newDocument();

    Element taglib = document.createElement("taglib");
    addLeafTextElement(taglibAnnotation.tlibVersion(), "tlib-version", taglib, document);
    addLeafTextElement(taglibAnnotation.jspVersion(), "jsp-version", taglib, document);
    addLeafTextElement(taglibAnnotation.shortName(), "short-name", taglib, document);
    addLeafTextElement(taglibAnnotation.uri(), "uri", taglib, document);
    String displayName = taglibAnnotation.displayName();
    if (displayName==null||displayName.length()==0) {
      displayName = taglibAnnotation.shortName();
    }
    addLeafTextElement(displayName, "display-name", taglib, document);
    String description = packageDeclaration.getDocComment();
    if (description != null) {
      addLeafCDATAElement(description, "description", taglib, document);
    }
    for (String listenerClass : taglibAnnotation.listener()) {
      Element listener = document.createElement("listener");
      // TODO check listenerClass implements ServletContextListener !!
      addLeafTextElement(listenerClass, "listener-class", listener, document);
      taglib.appendChild(listener);
    }

    for (ClassDeclaration decl : getCollectedClassDeclarations()) {
      if (decl.getPackage().equals(packageDeclaration)) {
        appendTag(decl, taglib, document);
      }
    }
    for (InterfaceDeclaration decl : getCollectedInterfaceDeclarations()) {
      if (decl.getPackage().equals(packageDeclaration)) {
        appendTag(decl, taglib, document);
      }
    }
    document.appendChild(taglib);
    return document;
  }

  protected void writeTaglib(PackageDeclaration packageDeclaration, Taglib taglibAnnotation, Document document) throws
      IOException, TransformerException {
    Writer writer = null;
    try {
      getEnv().getMessager().printNotice("Create DOM");
      writer = getEnv().getFiler().createTextFile(Filer.Location.SOURCE_TREE,
          packageDeclaration.getQualifiedName(),
          new File(taglibAnnotation.fileName()), null);
      TransformerFactory transFactory = TransformerFactory.newInstance();
      transFactory.setAttribute("indent-number", 2);
      Transformer transformer = transFactory.newTransformer();
      transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC,
          "-//Sun Microsystems, Inc.//DTD JSP Tag Library 1.2//EN");
      transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM,
          "http://java.sun.com/dtd/web-jsptaglibrary_1_2.dtd");
      transformer.setOutputProperty(OutputKeys.INDENT, "yes");
      transformer.transform(new DOMSource(document),
          new StreamResult(writer));
      getEnv().getMessager().printNotice("Write to file " + packageDeclaration.getQualifiedName()
          + " " + taglibAnnotation.fileName());
    } finally {
      IOUtils.closeQuietly(writer);
    }
  }

  protected void appendTag(ClassDeclaration decl, Element parent, Document document) {
    Tag annotationTag = decl.getAnnotation(Tag.class);
    checkDuplicates(annotationTag.name());
    resetAttributeDuplicateList();
    String className = decl.getQualifiedName();
    Element tag = createTag(decl, annotationTag, className, document, false);
    addAttributes(decl, tag, document);
    parent.appendChild(tag);
    if (annotationTag.deprecatedName() != null&&annotationTag.deprecatedName().length() > 0) {
      Element deprecatedTag = createTag(decl, annotationTag, className, document, true);
      addAttributes(decl, deprecatedTag, document);
      parent.appendChild(deprecatedTag);
    }

  }

  protected void appendTag(InterfaceDeclaration decl, Element parent, Document document) {
    Tag annotationTag = decl.getAnnotation(Tag.class);
    if (annotationTag != null) {
      checkDuplicates(annotationTag.name());
      resetAttributeDuplicateList();
      // TODO configure replacement

      String className =
          decl.getQualifiedName().substring(0, decl.getQualifiedName().length()-"Declaration".length());
      //decl.getQualifiedName().replaceAll("Declaration", "");
      String msg = "Replacing: " + decl.getQualifiedName()
          + " -> " + className;
      getEnv().getMessager().printNotice(msg);
      Element tag = createTag(decl, annotationTag, className, document, false);
      addAttributes(decl, tag, document);
      parent.appendChild(tag);
      if (annotationTag.deprecatedName() != null&&annotationTag.deprecatedName().length() > 0) {
        Element deprecatedTag = createTag(decl, annotationTag, className, document, true);
        addAttributes(decl, deprecatedTag, document);
        parent.appendChild(deprecatedTag);
      }
    }
  }

  protected Element createTag(Declaration decl, Tag annotationTag, String className, Document document,
      boolean deprecated) {
    Element tagElement = document.createElement("tag");
    if (deprecated) {
      addLeafTextElement(annotationTag.deprecatedName(), "name", tagElement, document);
    } else {
      addLeafTextElement(annotationTag.name(), "name", tagElement, document);
    }
    addLeafTextElement(className, "tag-class", tagElement, document);
    String tagExtraInfo = annotationTag.tagExtraInfoClassName();
    if (tagExtraInfo != null&& tagExtraInfo.length() > 0) {
      // TODO check tagExtraInfo extends TagExtraInfo         
      addLeafTextElement(tagExtraInfo, "tei-class", tagElement, document);
    }
    BodyContent bodyContent = annotationTag.bodyContent();
    BodyContentDescription contentDescription = decl.getAnnotation(BodyContentDescription.class);
    // TODO more error checking
    if (contentDescription != null) {
      if (bodyContent.equals(BodyContent.JSP)
          && contentDescription.contentType().length() > 0) {
        throw new IllegalArgumentException("contentType " + contentDescription.contentType()
            + " for bodyContent JSP not allowed!");
      } else if (bodyContent.equals(BodyContent.TAGDEPENDENT)
          && contentDescription.contentType().length() == 0) {
        throw new IllegalArgumentException("contentType should set for tagdependent bodyContent");
      }
    }
    addLeafTextElement(bodyContent.toString(), "body-content", tagElement, document);
    addDescription(decl, tagElement, document, deprecated);
    return tagElement;
  }

  private void checkAttributeDuplicates(String attributeName) {
    if (attributeSet.contains(attributeName)) {
      throw new IllegalArgumentException("Attribute " + attributeName + " in tag " +  currentTag + " already defined!");
    } else {
      attributeSet.add(attributeName);
    }
  }

  private void checkDuplicates(String tagName) {
    currentTag = tagName;
    if (tagSet.contains(tagName)) {
      throw new IllegalArgumentException("tag with name " + tagName + " already defined!");
    } else {
      tagSet.add(tagName);
    }
  }

  protected void addDescription(Declaration decl, Element element, Document document) {
    addDescription(decl, element, document, false);
  }


  protected void addDescription(Declaration decl, Element element, Document document, boolean deprecated) {
    StringBuilder description = new StringBuilder();

    Deprecated deprecatedAnnotation = decl.getAnnotation(Deprecated.class);
    if (deprecatedAnnotation != null) {
      description.append("<p>**** @deprecated. Will be removed in a future version **** </p>");
    }
    if (deprecated) {
      Tag annotationTag = decl.getAnnotation(Tag.class);
      description.append("<p>**** @deprecated. Will be removed in a future version. Use ");
      description.append(annotationTag.name());
      description.append(" instead. **** </p>");
    }

    Preliminary preliminary = decl.getAnnotation(Preliminary.class);
    if (preliminary != null) {
      description.append("<p>**** Preliminary. Maybe subject to changed in a future version");
      if (preliminary.value().length() > 0) {
        description.append(": ");
        description.append(preliminary.value());
      }
      description.append(" **** </p>");
    }
    String comment = decl.getDocComment();
    if (comment != null) {
      // remove @param section
      int index = comment.indexOf(" @");
      if (index != -1) {
        comment = comment.substring(0, index);
      }
      comment = comment.trim();
      if (comment.length() > 0) {
        //description.append("<p>");
        description.append(comment);
        //description.append("</p>");
      }

    }
    UIComponentTag componentTag = decl.getAnnotation(UIComponentTag.class);
    if (componentTag != null) {
      description.append(createDescription(componentTag));
    }
    ExtensionTag extensionTag = decl.getAnnotation(ExtensionTag.class);
    if (extensionTag != null) {
      String baseName = extensionTag.baseClassName();
      description.append("<p><b>Extended tag: </b>");
      description.append(baseName);
      description.append("</p>");

      InterfaceDeclaration declaration = getInterfaceDeclaration(baseName + "Declaration");
      if (declaration != null) {
        UIComponentTag baseComponentTag = declaration.getAnnotation(UIComponentTag.class);
        if (baseComponentTag != null) {
          description.append(createDescription(baseComponentTag));
        }
      }
    }
    if (description.length() > 0) {
      addLeafCDATAElement(description.toString(), "description", element, document);
    }
  }

  private InterfaceDeclaration getInterfaceDeclaration(String name) {
    for (InterfaceDeclaration declaration : getCollectedInterfaceDeclarations()) {
      if (name.equals(declaration.getQualifiedName())) {
        return declaration;
      }
    }
    return null;
  }

  private String createDescription(UIComponentTag componentTag) {
    StringBuilder description = new StringBuilder();
    description.append("<p><b>UIComponentClass: </b>");
    description.append(componentTag.uiComponent());
    description.append("</p>");
    description.append("<p><b>RendererType: </b>");
    description.append(componentTag.rendererType());
    description.append("</p>");
    Facet[] facets = componentTag.facets();
    if (facets.length > 0) {
      description.append("<p><b>Supported facets:</b></p>");
      description.append("<dl>");
      for (Facet facet: facets) {
        description.append("<dt><b>");
        description.append(facet.name());
        description.append("</b></dt>");
        description.append("<dd>");
        description.append(facet.description());
        description.append("</dd>");
      }
      description.append("</dl>");
    }
    return description.toString();
  }

  protected void addAttributes(Collection<InterfaceType> interfaces, Element tagElement, Document document) {
    for (InterfaceType type : interfaces) {
      addAttributes(type.getDeclaration(), tagElement, document);
    }
  }

  protected void addAttributes(InterfaceDeclaration type, Element tagElement, Document document) {
    addAttributes(type.getSuperinterfaces(), tagElement, document);
    for (MethodDeclaration decl : getCollectedMethodDeclarations()) {
      if (decl.getDeclaringType().equals(type)) {
        addAttribute(decl, tagElement, document);
      }
    }
  }

  protected void addAttributes(ClassDeclaration d, Element tagElement, Document document) {

    for (MethodDeclaration decl : getCollectedMethodDeclarations()) {
      if (d.getQualifiedName().
          equals(decl.getDeclaringType().getQualifiedName())) {
        addAttribute(decl, tagElement, document);
      }
    }
    addAttributes(d.getSuperinterfaces(), tagElement, document);
    if (d.getSuperclass() != null) {
      addAttributes(d.getSuperclass().getDeclaration(), tagElement, document);
    }
  }
  private void resetDuplicateList() {
    tagSet = new HashSet<String>();
  }
  private void resetAttributeDuplicateList() {
    attributeSet = new HashSet<String>();
  }

  protected void addAttribute(MethodDeclaration d, Element tagElement,
      Document document) {
    TagAttribute tagAttribute = d.getAnnotation(TagAttribute.class);
    if (tagAttribute != null) {
      String simpleName = d.getSimpleName();
      if (simpleName.startsWith("set")) {
        Element attribute = document.createElement("attribute");
        String attributeStr = simpleName.substring(3, 4).toLowerCase(Locale.ENGLISH) + simpleName.substring(4);
        checkAttributeDuplicates(attributeStr);
        addLeafTextElement(attributeStr, "name", attribute, document);

        addLeafTextElement(Boolean.toString(tagAttribute.required()), "required", attribute, document);
        addLeafTextElement(Boolean.toString(tagAttribute.rtexprvalue()), "rtexprvalue", attribute, document);
        addDescription(d, attribute, document, false);
        tagElement.appendChild(attribute);
      } else {
        throw new IllegalArgumentException("Only setter allowed found: " + simpleName);
      }
    }
  }

}
