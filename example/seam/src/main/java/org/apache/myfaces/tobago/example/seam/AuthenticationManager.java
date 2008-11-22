package org.apache.myfaces.tobago.example.seam;

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

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.bpm.Actor;
import org.jboss.seam.security.Identity;


@Name("authenticationManager")
public class AuthenticationManager {

  @In
  Identity identity;

  @In
  Actor actor;

  public boolean authenticate() {

    actor.setId(identity.getUsername());
    identity.addRole("authenticated");
    actor.getGroupActorIds().add("actor");
    actor.getGroupActorIds().add("developer");
    return true;
  }
}
