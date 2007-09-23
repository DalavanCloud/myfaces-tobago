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
<%@ taglib uri="http://myfaces.apache.org/tobago/extension" prefix="tx" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>

<tc:panel>
  <f:facet name="layout">
    <tc:gridLayout rows="2*;3*;fixed" margin="10"/>
  </f:facet>

  <tc:box label="Appenders">
    <tc:sheet columns="*;*;*;2*" var="appender" value="#{logging.appenders}">
      <tc:column label="Class">
        <tc:out value="#{appender.className}"/>
      </tc:column>
      <tc:column label="Name">
        <tc:out value="#{appender.name}"/>
      </tc:column>
      <tc:column label="File">
        <tc:link inline="true" action="#{logging.downloadLogFile}" tip="Download Log File"
                 target="_blank" rendered="#{! empty appender.file}">
          <f:param value="#{appender}" binding="#{logging.currentAppender}"/>
          <tc:out value="#{appender.file}"/>
        </tc:link>
      </tc:column>
      <tc:column label="Layout">
        <tc:out value="#{appender.layout}"/>
      </tc:column>
    </tc:sheet>
  </tc:box>

  <tc:box label="Categories">
    <f:facet name="layout">
      <tc:gridLayout rows="*;fixed"/>
    </f:facet>
    <tc:sheet columns="5*;25px;*" var="category"
              value="#{logging.categories}" showDirectLinks="left" showPageRange="right"
              first="#{logging.catergoriesFirst}" rows="#{logging.catergoriesRows}">
      <tc:column label="Name" sortable="true">
        <tc:link inline="true" action="#{logging.selectCategory}" immediate="true">
          <f:param value="#{category}" binding="#{logging.currentCategory}"/>
          <tc:out value="#{category.name}"/>
          <tc:attribute name="renderedPartially" value=":page:addCategory"/>
        </tc:link>
      </tc:column>
      <tc:column label="Inh" tip="Inherited" sortable="true">
        <tc:selectBooleanCheckbox readonly="true" value="#{category.inherited}"/>
      </tc:column>
      <tc:column label="Level" sortable="true">
        <tc:selectOneChoice value="#{category.level}">
          <tc:selectItems value="#{logging.levels}"/>
        </tc:selectOneChoice>
      </tc:column>
    </tc:sheet>
    <tc:panel>
      <f:facet name="layout">
        <tc:gridLayout columns="*;fixed"/>
      </f:facet>
      <tc:cell/>
      <tc:button label="Update" action="#{logging.updateCategories}"/>
    </tc:panel>
  </tc:box>

  <tc:box label="Add Category" id="addCategory">
    <f:facet name="layout">
      <tc:gridLayout rows="fixed"/>
    </f:facet>
    <tc:panel>
      <f:facet name="layout">
        <tc:gridLayout columns="*;*;fixed"/>
      </f:facet>
      <tx:in label="Category" value="#{logging.category}"
             binding="#{logging.categoryControl}"/>
      <tx:selectOneChoice label="Level" value="#{logging.level}">
        <tc:selectItems value="#{logging.levels}"/>
      </tx:selectOneChoice>
      <tc:button label="Set Level" action="#{logging.addCategory}"/>
    </tc:panel>
  </tc:box>

</tc:panel>
