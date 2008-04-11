package org.apache.myfaces.tobago.layout;

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

public class LayoutTokensUnitTest extends TestCase {

  public void testIsPixelToken() {
    assertTrue(LayoutTokens.isPixelToken("120px"));
  }

  public void testIsPercentToken() {
    assertTrue(LayoutTokens.isPercentToken("50%"));
  }

  public void testIsRelativeToken() {
    assertTrue(LayoutTokens.isRelativeToken("3*"));
  }

  public void testIsNumberAndSuffix() {
    assertTrue(LayoutTokens.isNumberAndSuffix("34cm", "cm"));
    assertFalse(LayoutTokens.isNumberAndSuffix("acm", "cm"));
    assertFalse(LayoutTokens.isNumberAndSuffix("cm", "cm"));
  }

  public void testParseToken() {
    assertEquals(RelativeLayoutToken.DEFAULT_INSTANCE, LayoutTokens.parseToken("*"));
    assertEquals(new RelativeLayoutToken(3), LayoutTokens.parseToken("3*"));
    assertEquals(new PercentLayoutToken(33), LayoutTokens.parseToken("33%"));
    assertEquals(new PixelLayoutToken(120), LayoutTokens.parseToken("120px"));
  }
}
