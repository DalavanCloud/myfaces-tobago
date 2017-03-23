/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.myfaces.tobago.renderkit.css;

import org.junit.Assert;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * Test if every in Java declared CSS class has really an entry in a CSS file.
 */
public class BootstrapClassUnitTest {

  /**
   * This test checks, if every item of the {@link BootstrapClass} occurs in the bootstrap.css.
   */
  @Test
  public void testCompareBootstrapCss() throws FileNotFoundException {

    final List<CssItem> missing =
    CssClassUtils.compareCss(
        "src/main/resources/META-INF/resources/tobago/standard/tobago-bootstrap/_version/css/bootstrap.css",
        BootstrapClass.values());

    Assert.assertTrue("These classes are missing in bootstrap.css: " + missing, missing.isEmpty());
  }

}
