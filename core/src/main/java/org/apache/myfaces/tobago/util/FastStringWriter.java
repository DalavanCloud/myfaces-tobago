package org.apache.myfaces.tobago.util;

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

import java.io.Writer;
import java.io.IOException;

public class FastStringWriter extends Writer {

  private StringBuilder stringBuilder;

  public FastStringWriter() {
    this(1024);
  }

  public FastStringWriter(int initialSize) {
    stringBuilder = new StringBuilder(initialSize);
  }

  public void write(int c) {
    stringBuilder.append((char) c);
  }

  public void write(char[] cbuf, int off, int len) {
    stringBuilder.append(cbuf, off, len);
  }

  public void write(String str) {
    stringBuilder.append(str);
  }

  public void write(String str, int off, int len) {
    stringBuilder.append(str.substring(off, off + len));
  }

  public String toString() {
    return stringBuilder.toString();
  }

  public void flush() {
    // do nothing
  }

  public void close() throws IOException {
    // do nothing
  }

  public StringBuilder getBuilder() {
    return stringBuilder;
  }
}
