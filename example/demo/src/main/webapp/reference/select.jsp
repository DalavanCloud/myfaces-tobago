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

<layout:overview>
  <jsp:body>
    <tc:box>
      <f:facet name="layout">
        <tc:gridLayout
            rows="fixed;fixed;fixed;fixed;fixed;fixed;fixed;fixed;fixed;45px;45px;fixed;fixed;fixed;200px;200px;fixed;*"/>
      </f:facet>

      <tc:messages/>

      <tc:selectOneRadio>
        <tc:selectItem itemValue="sun" itemLabel="Sun" />
        <tc:selectItem itemValue="moon" itemLabel="Moon" />
        <tc:selectItem itemValue="stars" itemLabel="Stars" itemDisabled="true" />
      </tc:selectOneRadio>

      <tc:selectManyCheckbox>
        <tc:selectItem itemValue="sun" itemLabel="Sun" />
        <tc:selectItem itemValue="moon" itemLabel="Moon" />
        <tc:selectItem itemValue="stars" itemLabel="Stars" itemDisabled="true" />
      </tc:selectManyCheckbox>

      <%-- code-sniplet-start id="selectBooleanCheckbox" --%>
      <tc:selectBooleanCheckbox label="_Letter"/>
      <%-- code-sniplet-end id="selectBooleanCheckbox" --%>
      <tc:selectBooleanCheckbox label="_Phone"/>
      <tc:selectBooleanCheckbox label="_eMail"/>
      <tc:selectBooleanCheckbox label="_Fax">
        <f:facet name="click">
          <tc:command/>
        </f:facet>
      </tc:selectBooleanCheckbox>

      <tc:selectBooleanCheckbox label="Value is Boolean" value="#{selectBooleanCheckboxController.normalBoolean}"/>

      <tc:selectBooleanCheckbox label="Converter for Boolean" value="#{selectBooleanCheckboxController.onOffString}">
        <f:converter converterId="org.apache.myfaces.tobago.example.reference.OnOffConverter"/>
      </tc:selectBooleanCheckbox>

      <%-- code-sniplet-start id="selectManyListbox" --%>
      <tx:selectManyListbox inline="true" id="LabeledInlineMultiSelect"
                            label="Contact via: ">
        <f:selectItem itemValue="Phone" itemLabel="Phone"/>
        <f:selectItem itemValue="eMail" itemLabel="eMail"/>
        <f:selectItem itemValue="Mobile" itemLabel="Mobile"/>
        <f:selectItem itemValue="Fax" itemLabel="Faxscimile"/>
      </tx:selectManyListbox>
      <tx:selectManyListbox inline="true"
                            label="Contact via: ">
        <f:selectItem itemValue="Phone" itemLabel="Phone"/>
        <f:selectItem itemValue="eMail" itemLabel="eMail"/>
        <f:selectItem itemValue="Mobile" itemLabel="Mobile"/>
        <f:selectItem itemValue="Fax" itemLabel="Faxscimile"/>
        <f:facet name="click">
          <tc:command/>
        </f:facet>
      </tx:selectManyListbox>
      <%-- code-sniplet-end id="selectManyListbox" --%>

      <%-- code-sniplet-start id="selectOneChoice" --%>
      <tx:selectOneChoice inline="true" value="selectOneChoice0"
                          label="Contact me1: ">
        <tc:selectItem itemLabel="Letter" itemValue="letter"/>
        <tc:selectItem itemLabel="Phone" itemValue="phone"/>
        <tc:selectItem itemLabel="eMail" itemValue="eMail"/>
        <tc:selectItem itemLabel="Fax" itemValue="fax"/>
      </tx:selectOneChoice>

      <%-- code-sniplet-end id="selectOneChoice" --%>

      <%-- surround this with a tc:form if you get validatation or required messages from other fields.
        With tc:form you enable partial validation and update model.
         <tc:form>
      --%>
      <tx:selectOneChoice value="#{reference.vehicle}"
                          label="Vehicle: " valueChangeListener="#{reference.valueChanged}">
        <f:selectItems value="#{reference.selectItems}"/>
        <f:facet name="change">
          <tc:command action="#{reference.action}"/>
        </f:facet>
      </tx:selectOneChoice>
      <tx:selectOneChoice value="#{reference.manufacturer}"
                          label="Manufacturer: " valueChangeListener="#{reference.valueChanged}">
        <f:selectItems value="#{reference.manufacturerSelectItems}"/>
      </tx:selectOneChoice>

      <%-- code-sniplet-start id="selectOneListbox" --%>
      <tx:selectOneListbox id="LabeledInlineSingleSelect"
                           label="Contact via: " height="90px">
        <f:selectItem itemValue="Phone" itemLabel="Phone"/>
        <f:selectItem itemValue="eMail" itemLabel="eMail"/>
        <f:selectItem itemValue="Mobile" itemLabel="Mobile"/>
        <f:selectItem itemValue="Fax" itemLabel="Faxscimile"/>
      </tx:selectOneListbox>
      <tx:selectOneListbox id="LabeledInlineSingleSelect1"
                           label="Contact via: " height="90px">
        <f:selectItem itemValue="Phone" itemLabel="Phone"/>
        <f:selectItem itemValue="eMail" itemLabel="eMail"/>
        <f:selectItem itemValue="Mobile" itemLabel="Mobile"/>
        <f:selectItem itemValue="Fax" itemLabel="Faxscimile"/>
        <f:facet name="click">
          <tc:command/>
        </f:facet>
      </tx:selectOneListbox>
      <%-- code-sniplet-end id="selectOneListbox" --%>

      <tc:button label="submit" action="submit"/>

      <tc:cell/>

    </tc:box>

  </jsp:body>
</layout:overview>