package org.apache.myfaces.tobago.webapp;

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
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.tobago.component.Attributes;
import org.apache.myfaces.tobago.renderkit.css.Style;
import org.apache.myfaces.tobago.renderkit.html.HtmlAttributes;
import org.apache.myfaces.tobago.renderkit.html.HtmlConstants;
import org.apache.myfaces.tobago.renderkit.html.StyleClasses;
import org.apache.myfaces.tobago.util.HtmlWriterUtils;
import org.apache.myfaces.tobago.util.XmlUtils;

import javax.faces.component.UIComponent;
import javax.faces.context.ResponseWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class TobagoResponseWriterImpl extends TobagoResponseWriter {

  private static final Log LOG = LogFactory.getLog(TobagoResponseWriterImpl.class);

  protected static final Set<String> EMPTY_TAG = new HashSet<String>(Arrays.asList(
      HtmlConstants.BR,
      HtmlConstants.AREA,
      HtmlConstants.LINK,
      HtmlConstants.IMG,
      HtmlConstants.PARAM,
      HtmlConstants.HR,
      HtmlConstants.INPUT,
      HtmlConstants.COL,
      HtmlConstants.BASE,
      HtmlConstants.META));

  private Writer writer;

  private UIComponent component;

  private boolean startStillOpen;

  private String contentType;

  private String characterEncoding;

  private Stack<String> stack;

  /**
   * use XML instead HMTL
   */
  private boolean xml;

  private HtmlWriterUtils helper;

  public TobagoResponseWriterImpl(
      Writer writer, String contentType, String characterEncoding, boolean xml) {
//    LOG.info("new TobagoResponseWriterImpl!");
//    final StackTraceElement[] stackTrace = new Exception().getStackTrace();
//    for (int i = 1; i < stackTrace.length && i < 5; i++) {
//      LOG.info("  " + stackTrace[i].toString());
//    }
    this.writer = writer;
    this.component = null;
    this.stack = new Stack<String>();
    this.contentType = contentType;
    this.characterEncoding = characterEncoding != null ? characterEncoding : "UTF-8";
    this.xml = xml;
    this.helper = new HtmlWriterUtils(writer, characterEncoding);
  }

  protected final UIComponent getComponent() {
    return component;
  }

  protected final void setComponent(UIComponent component) {
    this.component = component;
  }

  protected final boolean isStartStillOpen() {
    return startStillOpen;
  }

  protected final void setStartStillOpen(boolean startStillOpen) {
    this.startStillOpen = startStillOpen;
  }

  protected final Stack<String> getStack() {
    return stack;
  }

  public final boolean isXml() {
    return xml;
  }

  public final HtmlWriterUtils getHelper() {
    return helper;
  }


  private String findValue(final Object value, final String property) {
    if (value != null) {
      return value instanceof String ? (String) value : value.toString();
    } else if (property != null) {
      if (component != null) {
        final Object object = component.getAttributes().get(property);
        if (object != null) {
          return object instanceof String ? (String) object : object.toString();
        } else {
          return null;
        }
      } else {
        final String trace = getCallingClassStackTraceElementString();
        LOG.error("Don't know what to do! "
            + "Property defined, but no component to get a value. "
            + trace.substring(trace.indexOf('(')));
        LOG.error("value = 'null'");
        LOG.error("property = '" + property + "'");
        return null;
      }
    } else {
      final String trace = getCallingClassStackTraceElementString();
      LOG.error("Don't know what to do! "
          + "No value and no property defined. "
          + trace.substring(trace.indexOf('(')));
      LOG.error("value = 'null'");
      LOG.error("property = 'null'");
      return null;
    }
  }

  protected final Writer getWriter() {
    return writer;
  }

  public void write(final char[] cbuf, final int off, final int len)
      throws IOException {
    if (new String(cbuf).equals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")) {
      LOG.error("Filtering XML header: " + new String(cbuf) + " FIXME"); // FIXME
    } else {
      writer.write(cbuf, off, len);
    }
  }

  @Override
  public void write(String string) throws IOException {
    writeInternal(writer, string);
  }

  protected final void writeInternal(Writer writer, String string) throws IOException {
    closeOpenTag();
    writer.write(string);
  }

  @Override
  public void write(int i) throws IOException {
    closeOpenTag();
    writer.write(i);
  }

  @Override
  public void write(char[] chars) throws IOException {
    closeOpenTag();
    writer.write(chars);
  }

  @Override
  public void write(String string, int i, int i1) throws IOException {
    closeOpenTag();
    writer.write(string, i, i1);
  }

  public void close() throws IOException {
    closeOpenTag();
    writer.close();
  }

  public void flush() throws IOException {
    /*
    From the api:
    Flush any ouput buffered by the output method to the underlying Writer or OutputStream.
    This method will not flush the underlying Writer or OutputStream;
    it simply clears any values buffered by this ResponseWriter.
     */
    closeOpenTag();
  }

  public void writeText(final Object text, final String property)
      throws IOException {
    closeOpenTag();
    final String value = findValue(text, property);
    if (xml) {
      write(XmlUtils.escape(value));
    } else {
      helper.writeText(value);
    }
  }

  protected void closeOpenTag() throws IOException {
    if (startStillOpen) {
      writer.write("\n>");
      startStillOpen = false;
    }
  }

  public void writeText(final char[] text, final int offset, final int length)
      throws IOException {
    closeOpenTag();
    if (xml) {
      writer.write(XmlUtils.escape(text, offset, length, true));
    } else {
      helper.writeText(text, offset, length);
    }
  }

  public void startDocument() throws IOException {
    // nothing to do
  }

  public void endDocument() throws IOException {
    // nothing to do
  }

  public String getContentType() {
    return contentType;
  }

  public String getCharacterEncoding() {
    return characterEncoding;
  }

  public void startElement(final String name, final UIComponent currentComponent)
      throws IOException {
    startElementInternal(writer, name, currentComponent);
  }

  protected void startElementInternal(Writer writer, String name, UIComponent currentComponent)
      throws IOException {
    this.component = currentComponent;
    stack.push(name);
//    closeOpenTag();
    if (startStillOpen) {
      writer.write("\n>");
    }
    writer.write("<");
    writer.write(name);
    startStillOpen = true;
  }

  public void endElement(final String name) throws IOException {
    endElementInternal(writer, name);
  }

  protected void endElementInternal(Writer writer, String name) throws IOException {
    if (LOG.isDebugEnabled()) {
      LOG.debug("end Element: " + name);
    }

    String top = "";
    try {
      top = stack.pop();
    } catch (EmptyStackException e) {
      LOG.error("Failed to close element \"" + name + "\"!");
      throw e;
    }
    if (!top.equals(name)) {
      final String trace = getCallingClassStackTraceElementString();
      LOG.error("Element end with name='" + name + "' doesn't "
          + "match with top element on the stack='" + top + "' "
          + trace.substring(trace.indexOf('(')));
    }

    if (EMPTY_TAG.contains(name)) {
      if (xml) {
        writer.write("\n/>");
      } else {
        writer.write("\n>");
      }
    } else {
      if (startStillOpen) {
        writer.write("\n>");
      }
      writer.write("</");
      writer.write(name);
//      writer.write("\n>"); // FIXME: this makes problems with Tidy
      writer.write(">");
    }
    startStillOpen = false;
  }

  public void writeComment(final Object obj) throws IOException {
    closeOpenTag();
    String comment = obj.toString();
    write("<!--");
    if (comment.indexOf("--") < 0) {
      write(comment);
    } else {
      String trace = getCallingClassStackTraceElementString();
      LOG.warn(
          "Comment must not contain the sequence '--', comment = '"
              + comment + "' " + trace.substring(trace.indexOf('(')));
      write(StringUtils.replace(comment, "--", "++"));
    }
    write("-->");
  }

  public ResponseWriter cloneWithWriter(final Writer originalWriter) {
    return new TobagoResponseWriterImpl(
        originalWriter, getContentType(), getCharacterEncoding(), isXml());
  }

  public void writeAttribute(final String name, final Object value, final String property)
      throws IOException {

    final String attribute = findValue(value, property);
    writeAttribute(name, attribute, true);
  }

  protected final String getCallingClassStackTraceElementString() {
    final StackTraceElement[] stackTrace = new Exception().getStackTrace();
    int i = 1;
    while (stackTrace[i].getClassName().equals(this.getClass().getName())) {
      i++;
    }
    return stackTrace[i].toString();
  }

  public void writeURIAttribute(final String s, final Object obj, final String s1)
      throws IOException {
    LOG.error("Not implemented yet!");
  }

// interface TobagoResponseWriter //////////////////////////////////////////////////////////////////////////////////

  public void writeAttribute(final String name, final String value, final boolean escape)
      throws IOException {
    writeAttributeInternal(writer, name, value, escape);
  }

  protected void writeAttributeInternal(Writer writer, String name, String value, boolean escape)
      throws IOException {
    if (!startStillOpen) {
      String trace = getCallingClassStackTraceElementString();
      String error = "Cannot write attribute when start-tag not open. "
          + "name = '" + name + "' "
          + "value = '" + value + "' "
          + trace.substring(trace.indexOf('('));
      LOG.error(error);
      throw new IllegalStateException(error);
    }

    if (value != null) {
      writer.write(' ');
      writer.write(name);
      writer.write("=\"");
      if (xml) {
        writer.write(XmlUtils.escape(value));
      } else {
        if (escape) {
          helper.writeAttributeValue(value);
        } else {
          writer.write(value);
        }
      }
      writer.write('\"');
    }
  }

  public void writeClassAttribute() throws IOException {
    StyleClasses clazz = (StyleClasses) component.getAttributes().get(Attributes.STYLE_CLASS);
    if (clazz != null) {
      writeAttribute(HtmlAttributes.CLASS, clazz.toString(), false);
    }
  }

  public static Style ensureHtmlStyleMap(UIComponent component, Style styles) {
    if (styles == null) {
      styles = new Style();
      ((Map<String, Object>) component.getAttributes()).put(Attributes.STYLE, styles);
    }
    return styles;
  }

}
