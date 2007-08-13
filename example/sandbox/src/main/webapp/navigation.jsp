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

<f:view>

  <tc:loadBundle basename="demo" var="bundle"/>

  <tc:page label="Screenshot" id="page"
           width="200px" height="800px">
    <f:facet name="layout">
      <tc:gridLayout margin="5px" rows="fixed;fixed;fixed;fixed;fixed;fixed;*"/>
    </f:facet>

    <tc:link link="separator.jsp" label="separator.jsp" target="View"/>

    <tc:link link="tree-normal.jsp" label="tree-normal.jsp" target="View"/>

    <tc:link link="tree-select.jsp" label="tree-select.jsp" target="View"/>

    <tc:link link="tree-menu.jsp" label="tree-menu.jsp" target="View"/>

    <tc:link link="tree-editor.jsp" label="tree-editor.jsp" target="View"/>

    <tc:link link="tree-ajax.jsp" label="tree-ajax.jsp" target="View"/>

    <tc:link link="inputSlider.jsp" label="inputSlider.jsp" target="View"/>

    <tc:cell/>
  </tc:page>
</f:view>
