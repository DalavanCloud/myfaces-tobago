package org.apache.myfaces.tobago.example.test;

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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Filter {

  private static final Log LOG = LogFactory.getLog(Filter.class);

  public static final List<String> ALLOWED = Arrays.asList(".*\\.xhtml", ".*\\.jsp", ".*\\.jspx", ".*\\.html");

  public static final Set<String> FORBIDDEN = new HashSet<String>(Arrays.asList(
      "/META-INF.*",
      "/WEB-INF.*",
      "/index.html",
      "/navi.xhtml",
      "/org/apache/myfaces/tobago/renderkit/html/standard/blank.html",
      "/org/apache/myfaces/tobago/renderkit/html/standard/standard/script/dojo/.*",
      "/tc/button/plain.html",
      "/tc/button/plain_de.html"));

  public static boolean isValid(String name) {

    // 1st all has to start with a '/' slash

    if (!name.startsWith("/")) {
      return false;
    }

    // 2nd the positive check

    boolean matches = false;
    for (String allowed : ALLOWED) {
      if (name.matches(allowed)) {
        matches = true;
      }
    }
    if (!matches) {
      return false;
    }

    // 3rd the negative check

    for (String forbidden : FORBIDDEN) {
      if (name.matches(forbidden)) {
        return false;
      }
    }

    return true;
  }

}
