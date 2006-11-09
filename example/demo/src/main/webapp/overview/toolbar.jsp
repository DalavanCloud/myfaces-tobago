<%--
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
--%>
<%@ taglib uri="http://myfaces.apache.org/tobago/component" prefix="tc" %>
<%@ taglib uri="http://myfaces.apache.org/tobago/extension" prefix="tx" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib tagdir="/WEB-INF/tags/layout" prefix="layout" %>

<layout:overview>
  <jsp:body>
    <tc:panel id="pageToolbar" >
      <f:facet name="layout">
        <tc:gridLayout rows="65px;fixed;1px;70px;fixed;50px"  id="pageToolbarLayout"/>
      </f:facet>

      <tc:out escape="false" value="#{overviewBundle.toolbar_text1}" />

      <tc:box label="#{overviewBundle.toolbar_sampleTitle}" id="boxToolbar" >

        <f:facet name="layout">
          <tc:gridLayout rows="fixed;fixed;fixed" columns="2*;1*;1*" id="boxToolbarLayout"/>
        </f:facet>

        <f:facet name="toolBar">
        <tc:toolBar>
          <tc:toolBarCommand id="button" action="overview/toolbar"
              actionListener="#{overviewController.click}"
              label="#{overviewBundle.toolbar_buttonAction}" />

        <tc:toolBarCommand id="imageButton" action="#{overviewController.ping}"
            actionListener="#{overviewController.click}"
            label="#{overviewBundle.toolbar_imageButtonAction}"
            image="image/toolbar_example_button.gif" />

        <tc:toolBarCommand id="popupButton"
            actionListener="#{overviewController.click}"
            label="#{overviewBundle.toolbar_popupButtonAction}">

          <f:facet name="popup">
            <tc:popup width="200" height="100"
                          rendered="#{overviewController.showPopup}" id="popup" >

              <f:facet name="layout">
                <tc:gridLayout rows="35px;1*;fixed" />
              </f:facet>

              <tc:out value="#{overviewBundle.toolbar_popupText}"/>

              <tc:cell/>
              <tc:button id="popupCloseButton"
                             actionListener="#{overviewController.click}"
                             label="#{overviewBundle.toolbar_closePopupAction}" />

            </tc:popup>
          </f:facet>
        </tc:toolBarCommand>

        <tc:toolBarCommand id="DropDown"
                           label="#{overviewBundle.toolbar_dropDownAction}" >
          <f:facet name="menupopup">
            <tc:menu>
              <tc:menuItem onclick="alert('test 1')"  label="Alert 1"/>
              <tc:menuItem onclick="alert('test 2')" label="Alert 2"/>
              <tc:menuItem action="#{overviewController.ping}" id="dropdown3" actionListener="#{overviewController.click}" label="Action 3"/>
            </tc:menu>
          </f:facet>
        </tc:toolBarCommand>

        </tc:toolBar>
        </f:facet>

        <tc:cell spanX="3">
        <tc:toolBar iconSize="#{overviewController.toolbarIconSize}"
            labelPosition="#{overviewController.toolbarTextPosition}" >
          <tc:toolBarCommand id="button2" action="overview/toolbar"
              actionListener="#{overviewController.click}"
              label="#{overviewBundle.toolbar_buttonAction}" />

        <tc:toolBarCommand id="imageButton2" action="overview/toolbar"
            actionListener="#{overviewController.click}"
            label="#{overviewBundle.toolbar_imageButtonAction}"
            image="image/toolbar_example_button.gif" />



        <tc:toolBarCommand id="popupButton2" action="overview/toolbar"
            actionListener="#{overviewController.click}"
            label="#{overviewBundle.toolbar_popupButtonAction}">

          <f:facet name="popup">
            <tc:popup width="200" height="100"
                          rendered="#{overviewController.showPopup}" id="popup2" >

              <f:facet name="layout">
                <tc:gridLayout rows="35px;1*;fixed" />
              </f:facet>

              <tc:out value="#{overviewBundle.toolbar_popupText}"/>

              <tc:cell/>
              <tc:button id="popupCloseButton2"
                             actionListener="#{overviewController.click}"
                             label="#{overviewBundle.toolbar_closePopupAction}" />

            </tc:popup>
          </f:facet>
        </tc:toolBarCommand>

        <tc:toolBarCommand id="DropDown2" action="overview/toolbar"
          actionListener="#{overviewController.click}" label="#{overviewBundle.toolbar_dropDownAction}" >
          <f:facet name="menupopup">
            <tc:menu>
              <tc:menuItem onclick="alert('test 1')"  label="Alert 1"/>
              <tc:menuItem onclick="alert('test 2')"  label="Alert 2"/>
              <tc:menuItem id="dropdown3_2" actionListener="#{overviewController.click}" label="Action 3"/>
            </tc:menu>
          </f:facet>
        </tc:toolBarCommand>

        </tc:toolBar>
        </tc:cell>

        <tx:in value="#{overviewController.lastAction}" readonly="true"
            label="#{overviewBundle.basic_lastActionLabel}" />

        <tc:selectOneChoice value="#{overviewController.toolbarTextPosition}" >
          <f:selectItems value="#{overviewController.toolbarTextItems}"/>
        </tc:selectOneChoice>
        <tc:selectOneChoice value="#{overviewController.toolbarIconSize}" >
          <f:selectItems value="#{overviewController.toolbarIconItems}"/>
        </tc:selectOneChoice>

        <tc:cell/>
        <tc:cell spanX="2">
          <tc:button label="updateView"  />
        </tc:cell>

      </tc:box>

      <tc:cell/>

      <tc:out escape="false" value="#{overviewBundle.toolbar_text2}" />

      <tc:box label="#{overviewBundle.toolbar_sampleTitle2}" id="boxMenu">
        <tc:menuBar >

          <tc:menu label="Menu 1" >

            <tc:menuItem onclick="alert('action 1')"  label="alert 1" />

            <tc:menuItem action="/" type="navigate" label="#{overviewBundle.toolbar_linkClickme}"
                image="image/config.gif" >
              <f:facet name="confirmation"><tc:out value="Do you really want leave this demo?" /></f:facet>
            </tc:menuItem>

            <tc:menuItem link="http://www.atanion.com" disabled="true" label="disabled" />

            <tx:menuCheckbox action="#{demo.clickButton}"  label="#{overviewBundle.toolbar_linkClickme}" value="#{demo.boolTest}" />
          </tc:menu>
          <tc:menu label="Menu 2">

            <tc:menuItem onclick="alert('action 1')"  label="alert 1" />

            <tc:menu label="Menu 3">

              <tc:menuItem onclick="alert('action 1')"  label="alert 1" image="image/date.gif" />

              <tc:menuItem link="/" label="#{overviewBundle.toolbar_linkClickme}" image="image/config.gif" >
                <f:facet name="confirmation"><tc:out value="Do you really want leave this demo?" /></f:facet>
              </tc:menuItem>

              <tc:menuItem link="http://www.atanion.com" disabled="true" label="#{overviewBundle.toolbar_linkClickme}" image="image/remove.gif" />

            <tc:menu label="#{overviewBundle.toolbar_selectSingleselect}">

              <tx:menuRadio value="#{overviewController.radioValue}" converter="salutationId" >
                <f:selectItems value="#{overviewController.items}" />
              </tx:menuRadio>

            </tc:menu>

              <tc:menuItem action="#{demo.clickButton}"  label="#{overviewBundle.toolbar_linkClickme}" />
            </tc:menu>

            <tc:menuSeparator/>

            <tc:menuItem label="#{overviewBundle.toolbar_linkClickme}" >
              <f:facet name="items">
                <tc:selectBooleanCheckbox value="#{demo.bool[0]}" />
              </f:facet>
            </tc:menuItem>
            <tx:menuCheckbox label="#{overviewBundle.toolbar_linkClickme}" value="#{demo.bool[1]}" />
            <tx:menuCheckbox label="#{overviewBundle.toolbar_linkClickme}" value="#{demo.bool[2]}" />

          </tc:menu>

        </tc:menuBar>
      </tc:box>
      <tc:cell/>
    </tc:panel>
  </jsp:body>
</layout:overview>
