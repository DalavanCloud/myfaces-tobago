package org.apache.myfaces.tobago.example.facelets;

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

import javax.faces.model.SelectItem;
import java.util.List;
import java.util.ArrayList;

public class MessageBackingBean {
  private String type;

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public SelectItem [] getSelectionItems() {
    SelectItem [] items  = new SelectItem[2];
    items[0] = new SelectItem("One", "One");
    items[1] = new SelectItem("Two", "Two");
    return items;
  }

  public List<User> getList() {
    List<User> list = new ArrayList<User>();
    list.add(new User("Anton", "Antonius"));
    list.add(new User("Bill", "Bilson"));
    return list;
  }
}
