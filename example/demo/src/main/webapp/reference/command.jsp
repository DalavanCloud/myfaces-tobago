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
<%@ taglib uri="http://myfaces.apache.org/tobago/extension" prefix="tx" %>

<layout:overview>
  <jsp:body>
    <tc:box label="Command Controls">
      <f:facet name="layout">
        <tc:gridLayout columns="100px;100px;100px;100px;*" rows="fixed;fixed;fixed;fixed;fixed;fixed;*"/>
      </f:facet>

      <%-- standard --%>

      <tc:cell spanY="2">
        <tc:label value="Standard"/>
      </tc:cell>

      <%-- code-sniplet-start id="button" --%>
      <tc:button label="Action" action="reference/command"/>
      <%-- code-sniplet-end id="button" --%>
      <tc:button label="On Click" onclick="alert('Hallo Tobago!');"/>
      <tc:button label="Link" link="http://www.apache.org/"/>
      <tc:cell/>

      <%-- code-sniplet-start id="link" --%>
      <tc:link label="Action" action="reference/command"/>
      <%-- code-sniplet-end id="link" --%>
      <tc:link label="On Click" onclick="alert('Hallo Tobago!');"/>
      <tc:link label="Link" link="http://www.apache.org/"/>
      <tc:cell/>

      <%-- confirmation --%>

      <tc:cell spanY="2">
        <tc:label value="Confirmation" tip="The user will be asked, if the button/link should be executed."/>
      </tc:cell>

      <tc:button label="Action" action="reference/command">
        <f:facet name="confirmation">
          <tc:out value="Are you sure?"/>
        </f:facet>
      </tc:button>
      <tc:button label="On Click" onclick="alert('Hallo Tobago!');">
        <f:facet name="confirmation">
          <tc:out value="Are you sure?"/>
        </f:facet>
      </tc:button>
      <tc:button label="Link" link="http://www.apache.org/">
        <f:facet name="confirmation">
          <tc:out value="Are you sure?"/>
        </f:facet>
      </tc:button>
      <tc:cell/>

      <tc:link label="Action" action="reference/command">
        <f:facet name="confirmation">
          <tc:out value="Are you sure?"/>
        </f:facet>
      </tc:link>
      <tc:link label="On Click" onclick="alert('Hallo Tobago!');">
        <f:facet name="confirmation">
          <tc:out value="Are you sure?"/>
        </f:facet>
      </tc:link>
      <tc:link label="Link" link="http://www.apache.org/">
        <f:facet name="confirmation">
          <tc:out value="Are you sure?"/>
        </f:facet>
      </tc:link>
      <tc:cell/>

      <%-- target --%>

      <tc:cell spanY="2">
        <tc:label value="Target"/>
      </tc:cell>

      <tc:button label="Action" action="reference/command" target="Command Target"/>
      <tc:button label="N/A" disabled="true"/>
      <tc:button label="N/A yet" disabled="true"/>
      <tc:cell/>

      <tc:link label="Action" action="reference/command" target="Command Target"/>
      <tc:link label="N/A"  disabled="true"/>
      <tc:link label="Link" link="http://www.apache.org/" target="Command Target"/>
      <tc:cell/>

      <%-- empty --%>

      <tc:cell/>
      <tc:cell/>
      <tc:cell/>
      <tc:cell/>
      <tc:cell/>

    </tc:box>
  </jsp:body>
</layout:overview>
