package org.apache.myfaces.tobago.example.addressbook;

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

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Basic;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

/*
 * Created by IntelliJ IDEA.
 * User: bommel
 * Date: Feb 19, 2007
 * Time: 7:45:33 PM
 */
@Entity
public class Picture {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String contentType;
  @Lob
  @Basic(fetch = FetchType.EAGER)
  private byte [] content;

  public Picture() {
  }

  public Picture(String contentType, byte[] content) {
    this.contentType = contentType;
    this.content = content;
  }

  public Integer getId() {
    return id;
  }

  public String getContentType() {
    return contentType;
  }

  public byte[] getContent() {
    return content;
  }

}
