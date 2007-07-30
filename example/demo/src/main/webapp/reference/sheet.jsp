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
<%@ page import="org.apache.myfaces.tobago.example.reference.SimpleBean" %>

<%
  SimpleBean[] simpleList = (SimpleBean[]) session.getAttribute("simpleList");
  System.out.println("1" + session.getAttribute("simpleList"));
  if (simpleList == null) {
    simpleList = new SimpleBean[]{
        new SimpleBean(1, "One"),
        new SimpleBean(2, "Two"),
        new SimpleBean(3, "Three"),
        new SimpleBean(4, "Four"),
        new SimpleBean(5000000, "Five Million")};
  }
  session.setAttribute("simpleList", simpleList);
  System.out.println("2" + session.getAttribute("simpleList"));
%>

<%@ taglib uri="http://myfaces.apache.org/tobago/component" prefix="tc" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib tagdir="/WEB-INF/tags/layout" prefix="layout" %>

<layout:overview>
    <jsp:body>
      <tc:box label="Sheet">
        <f:facet name="layout">
          <tc:gridLayout rows="fixed;*;fixed;fixed;fixed"/>
        </f:facet>

        <tc:messages/>

        <tc:sheet value="#{simpleList}" columns="fixed;*" var="bean">
          <tc:columnSelector />
          <tc:column label="Number">
            <tc:in value="#{bean.value}" required="true"/>
          </tc:column>
        </tc:sheet>

        <tc:separator >
          <f:facet name="label">
            <tc:label value="Layout: Sheet with 'fixed' height"/>
          </f:facet>
        </tc:separator>

        <tc:sheet value="#{simpleList}" columns="*;*;*" var="bean" rows="5">
          <tc:column label="Cipher">
            <tc:out value="#{bean.number}"/>
          </tc:column>
          <tc:column label="Name">
            <tc:out value="#{bean.value}"/>
          </tc:column>
          <tc:column label="Date">
            <tc:in value="#{bean.date}">
              <f:convertDateTime dateStyle="medium" type="date"/>
            </tc:in>
          </tc:column>
        </tc:sheet>

        <tc:panel>
          <f:facet name="layout">
            <tc:gridLayout columns="*;fixed"   />
          </f:facet>
          <tc:cell/>
          <tc:button action="submit" label="Submit" />
        </tc:panel>

      </tc:box>

    </jsp:body>
</layout:overview>
