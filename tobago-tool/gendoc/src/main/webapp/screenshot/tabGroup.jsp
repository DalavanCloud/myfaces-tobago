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
<%@ taglib tagdir="/WEB-INF/tags/layout" prefix="layout" %>

<layout:screenshot>
  <jsp:body>
    <f:subview id="button">
      <tc:panel>
        <f:facet name="layout">
          <tc:gridLayout />
        </f:facet>
        <tc:tabGroup switchType="reloadPage" immediate="true">
            <tc:tab label="tab1">
              <tc:panel >
                <f:facet name="layout">
                  <tc:gridLayout rows="1*;fixed;fixed;1*" /></f:facet>
                <tc:cell />
                <tx:in label="label1" required="true" />
                <tx:in label="label2" />
                <tc:cell />
              </tc:panel>
            </tc:tab>
            <tc:tab label="tab2" >
              <tc:panel >
                <f:facet name="layout">
                  <tc:gridLayout rows="1*;fixed;fixed;1*" />
                </f:facet>
                <tc:cell />
                <tx:in label="label1" />
                <tx:in label="label2" />
                <tc:cell />
              </tc:panel>
            </tc:tab>

          </tc:tabGroup>
      </tc:panel>
    </f:subview>
  </jsp:body>
</layout:screenshot>