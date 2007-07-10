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
<f:subview id="footer" >

  <tc:panel>
    <f:facet name="layout">
      <tc:gridLayout columns="fixed;fixed;fixed;*" rows="15px;fixed"/>
    </f:facet>

    <tc:cell spanX="4">
      <tc:separator/>
    </tc:cell>

    <tc:button immediate="true"
      image="image/prev.gif"
      action="#{navigation.gotoPrevious}"
      disabled="#{navigation.first}"
      label="#{overviewBundle.footer_previous}"/>

    <tc:button immediate="true"
      image="image/next.gif"
      action="#{navigation.gotoNext}"
      disabled="#{navigation.last}"
      label="#{overviewBundle.footer_next}"/>

    <tc:button action="#{navigation.viewSource}"
        immediate="true" label="#{overviewBundle.footer_viewSource}"
        target="Source Viewer" transition="false"/>

    <tc:out value="#{overviewBundle.notTranslated}"/>

  </tc:panel>
</f:subview>
