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

import junit.framework.TestCase;

import java.io.IOException;
import java.io.StringWriter;

public class TobagoResponseWriterUnitTest extends TestCase {

  private StringWriter stringWriter;
  private TobagoResponseWriterImpl writer;

  protected void setUp() throws Exception {
    super.setUp();
    stringWriter = new StringWriter();
    writer = new TobagoResponseWriterImpl(stringWriter, "", "UTF-8");
  }

  public void testDocument() throws IOException {
    writer.startDocument();
    writer.endDocument();
    assertEquals("no content needed", "", stringWriter.toString());
  }

  public void testEmptyTag() throws IOException {
    writer.startElement("input", null);
    writer.endElement("input");
    assertEquals("empty tag", "<input\n>", stringWriter.toString());
  }

  public void testNormalTag() throws IOException {
    writer.startElement("select", null);
    writer.endElement("select");
    assertEquals("normal tag", "<select\n></select>", stringWriter.toString());
  }

  public void testAttribute() throws IOException {
    writer.startElement("select", null);
    writer.writeAttribute("value", "0", null);
    writer.endElement("select");
    assertEquals("attr tag", "<select value=\"0\"\n></select>", stringWriter.toString());
  }

  public void testAttributeQuoting() throws IOException {
    writer.startElement("select", null);
    writer.writeAttribute("value", "-<->-ü-€-", null);
    writer.endElement("select");
    assertEquals("attr tag", "<select value=\"-&lt;-&gt;-ü-€-\"\n></select>", stringWriter.toString());
  }

  public void testTextQuoting() throws IOException {
    writer.startElement("textarea", null);
    writer.writeText("-<->-ü-€-", null);
    writer.endElement("textarea");
    assertEquals("attr tag", "<textarea\n>-&lt;-&gt;-ü-€-</textarea>", stringWriter.toString());
  }

  public void testStringWriter() throws IOException { 
    stringWriter.write("-ü-€-");
    assertEquals("-ü-€-", stringWriter.toString());
  }

  public void testManyChars() throws IOException {
    writer.startElement("select", null);
    StringBuffer buffer = new StringBuffer();
    for (char c = 0x20; c < 0x1ff; c++) {
      buffer.append(c);
    }
    writer.writeAttribute("value", buffer, null);
    writer.writeText(buffer, null);
    writer.endElement("select");

    String result = buffer.toString(); // all the same but this 4 items
    result = result.replace("&", "&amp;");
    result = result.replace("\"", "&quot;");
    result = result.replace("<", "&lt;");
    result = result.replace(">", "&gt;");
    assertEquals("all chars", "<select value=\"" + result + "\"\n>" + result + "</select>", stringWriter.toString());
  }

  public void testNonUtf8() throws IOException {
    TobagoResponseWriterImpl writer1 = new TobagoResponseWriterImpl(stringWriter, "", "ISO-8859-1");
    writer1.startElement("input", null);
    writer1.writeAttribute("value", "Gutschein über 100 €.", null);
    writer1.writeAttribute("readonly", true);
    writer1.endElement("input");
    writer1.close();
    assertEquals("<input value=\"Gutschein &uuml;ber 100 &euro;.\" readonly=\"readonly\"\n>", stringWriter.toString());
  }

  public void testCharArray() throws IOException {
    TobagoResponseWriterImpl writer = new TobagoResponseWriterImpl(stringWriter, "text/xml", "ISO-8859-1");
    writer.writeText("123".toCharArray(), 0, 3);
    assertEquals("123", stringWriter.toString());
  }
}
