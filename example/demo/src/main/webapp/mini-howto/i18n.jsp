<%--
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
--%>
<%@ taglib uri="http://myfaces.apache.org/tobago/component" prefix="tc" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib tagdir="/WEB-INF/tags/layout" prefix="layout" %>

<layout:mini-howto>
  <jsp:body>
    <tc:panel>
      <f:facet name="layout">
        <tc:gridLayout rows="150px;110px;50px;1*;" />
      </f:facet>

      <tc:out escape="false" value="#{miniHowtoBundle.i18nText1}" />

      <tc:box label="#{miniHowtoBundle.codeExample}" >
        <tc:out value="#{miniHowtoBundle.i18nCodeExample1}" />
      </tc:box>

      <tc:out escape="false" value="#{miniHowtoBundle.i18nText2}" />

      <tc:box label="#{miniHowtoBundle.codeExample}" >
        <tc:out escape="true" value="#{miniHowtoBundle.i18nCodeExample2}" />
      </tc:box>

    </tc:panel>
  </jsp:body>
</layout:mini-howto>
