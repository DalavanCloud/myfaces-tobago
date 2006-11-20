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

import javax.faces.context.FacesContext;
import java.util.HashSet;
import java.util.Map;
import java.util.Locale;

public class AccessKeyMap {

  private static final char[] KEYS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
      'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o',
      'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

  private static final String REQUEST_MAP_KEY = "accessKeysRequestMapKey";

  private HashSet set;
  private String dublicated = "";


  public static AccessKeyMap getInstance(FacesContext facesContext) {
    final Map requestMap = facesContext.getExternalContext().getRequestMap();
    AccessKeyMap keyMap = (AccessKeyMap) requestMap.get(REQUEST_MAP_KEY);
    if (keyMap == null) {
      keyMap = new AccessKeyMap();
      requestMap.put(REQUEST_MAP_KEY, keyMap);
    }
    return keyMap;
  }

  private AccessKeyMap() {
    set = new HashSet();
  }

  private HashSet getSet() {
    return set;
  }

  private String getDublicated() {
    return dublicated;
  }

  private void addDublicated(char key) {
    dublicated = dublicated + key;
  }

  public static boolean addAccessKey(FacesContext facesContext, Character key) {
    key = new Character(key.toString().toLowerCase(Locale.ENGLISH).charAt(0));
    final AccessKeyMap instance = getInstance(facesContext);
    if (instance.getSet().contains(key)) {
      instance.addDublicated(key.charValue());
      return false;
    } else {
      instance.getSet().add(key);
      return true;
    }
  }


  public static String getDublicatedKeys(FacesContext facesContext) {
    return getInstance(facesContext).getDublicated();
  }

  public static String getUnusedKeys(FacesContext facesContext) {
    HashSet set = getInstance(facesContext).getSet();
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < KEYS.length; i++) {
      if (!set.contains(new Character(KEYS[i]))) {
        sb.append(KEYS[i]);
      }
    }
    return sb.toString();
  }
}
