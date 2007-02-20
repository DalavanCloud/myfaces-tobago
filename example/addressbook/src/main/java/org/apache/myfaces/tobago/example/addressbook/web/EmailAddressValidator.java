package org.apache.myfaces.tobago.example.addressbook.web;


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

import org.apache.myfaces.tobago.example.addressbook.EmailAddress;

import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.faces.context.FacesContext;
import javax.faces.component.UIComponent;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class EmailAddressValidator implements Validator {

  private static final String EMAIL_ATOM
      = "[^()<>@,;:\\\\.\\[\\]\\\"]";
  private static final String LOCAL_PART_SPEC
      = EMAIL_ATOM + "+(\\." + EMAIL_ATOM + "+)*";
  private static final String DOMAIN_SPEC
      = EMAIL_ATOM + "+(\\." + EMAIL_ATOM + "+)+";

  private static final Pattern LOCAL_PART_PATTERN
      = Pattern.compile(LOCAL_PART_SPEC);
  private static final Pattern DOMAIN_PATTERN
      = Pattern.compile(DOMAIN_SPEC);

  public void validate(
      FacesContext facesContext, UIComponent uiComponent, Object value)
      throws ValidatorException {
    EmailAddress emailAddress = (EmailAddress) value;

    Matcher matcher = LOCAL_PART_PATTERN.matcher(emailAddress.getLocalPart());
    if (!matcher.matches()) {
      throw new ValidatorException(MessageUtils.createErrorMessage(
          "validatorEmailLocalPart", facesContext));
    }

    matcher = DOMAIN_PATTERN.matcher(emailAddress.getDomain());
    if (!matcher.matches()) {
      throw new ValidatorException(MessageUtils.createErrorMessage(
          "validatorEmailDomain", facesContext));
    }
  }

}
