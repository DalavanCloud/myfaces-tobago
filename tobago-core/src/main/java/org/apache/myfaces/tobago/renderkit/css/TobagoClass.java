/*
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
 */

package org.apache.myfaces.tobago.renderkit.css;

import org.apache.myfaces.tobago.layout.AlignItems;
import org.apache.myfaces.tobago.layout.JustifyContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Declaration of the Tobago CSS classes.
 *
 * @since 3.0.0
 */
public enum TobagoClass implements CssItem {

  // general classes

  ALIGN_ITEMS__BASELINE("tobago-alignItems-baseline"),
  ALIGN_ITEMS__CENTER("tobago-alignItems-center"),
  ALIGN_ITEMS__FLEX_END("tobago-alignItems-flexEnd"),
  ALIGN_ITEMS__FLEX_START("tobago-alignItems-flexStart"),
  ALIGN_ITEMS__STRETCH("tobago-alignItems-stretch"),

  JUSTIFY_CONTENT__CENTER("tobago-justifyContent-center"),
  JUSTIFY_CONTENT__FLEX_START("tobago-justifyContent-flexStart"),
  JUSTIFY_CONTENT__FLEX_END("tobago-justifyContent-flexEnd"),
  JUSTIFY_CONTENT__SPACE_BETWEEN("tobago-justifyContent-spaceBetween"),
  JUSTIFY_CONTENT__SPACE_AROUND("tobago-justifyContent-spaceAround"),

  DROPDOWN__SUBMENU("tobago-dropdown-submenu"),
  /** @deprecated Since 3.0.1. Please use {@link TobagoClass#DROPDOWN__SUBMENU} */
  @Deprecated
  DROPDOWN_SUBMENU(DROPDOWN__SUBMENU.getName()),
  TABLE_LAYOUT__FIXED("tobago-tableLayout-fixed"),

  HAS__INFO("tobago-has-info"),
  /** @deprecated Since 3.0.1. Please use {@link TobagoClass#HAS__INFO} */
  @Deprecated
  HAS_INFO(HAS__INFO.getName()),
  REQUIRED("tobago-required"),
  SPREAD("tobago-spread"),

  // component based classes

  BOX__HEADER("tobago-box-header"),
  COLLAPSED("tobago-collapsed"),
  FLEX_LAYOUT("tobago-flexLayout"),
  FIGURE("tobago-figure"),
  FORM("tobago-form"),
  INPUT__GROUP__OUTER("tobago-input-group-outer"),
  /** @deprecated Since 3.0.1. Please use {@link TobagoClass#INPUT__GROUP__OUTER} */
  @Deprecated
  INPUT_GROUP_OUTER(INPUT__GROUP__OUTER.getName()),
  LABEL("tobago-label"),
  LABEL__CONTAINER("tobago-label-container"),
  MESSAGES("tobago-messages"),
  MESSAGES__BUTTON("tobago-messages-button"),
  MESSAGES__CONTAINER("tobago-messages-container"),
  PAGE__MENU_STORE("tobago-page-menuStore"),
  PANEL("tobago-panel"),
  POPUP("tobago-popup"),
  SECTION__HEADER("tobago-section-header"),
  SECTION__CONTENT("tobago-section-content"),
  SELECT_MANY_CHECKBOX__INLINE("tobago-selectManyCheckbox-inline"),
  SELECT_ONE_RADIO__INLINE("tobago-selectOneRadio-inline"),
  SHEET__HEADER("tobago-sheet-header"),
  SHEET__BODY_TABLE("tobago-sheet-bodyTable"),
  SHEET__COLUMN_SELECTOR("tobago-sheet-columnSelector"),
  SHEET__HEADER_TABLE("tobago-sheet-headerTable"),
  SHEET__PAGING_INPUT("tobago-sheet-pagingInput"),
  SHEET__PAGING_OUTPUT("tobago-sheet-pagingOutput"),
  SHEET__CELL__MARKUP__RIGHT("tobago-sheet-cell-markup-right"),
  SHEET__CELL__MARKUP__CENTER("tobago-sheet-cell-markup-center"),
  SHEET__CELL__MARKUP__JUSTIFY("tobago-sheet-cell-markup-justify"),
  SUGGEST("tobago-suggest"),
  TREE_NODE__TOGGLE("tobago-treeNode-toggle");

  private static final Logger LOG = LoggerFactory.getLogger(TobagoClass.class);

  private final String name;

  TobagoClass(final String name) {
    this.name = name;
  }

  @Override
  public String getName() {
    return name;
  }

  public static TobagoClass valueOf(AlignItems alignItems) {
    if (alignItems == null) {
      return null;
    } else {
      switch (alignItems) {
        case baseline:
          return ALIGN_ITEMS__BASELINE;
        case center:
          return ALIGN_ITEMS__CENTER;
        case flexEnd:
          return ALIGN_ITEMS__FLEX_END;
        case flexStart:
          return ALIGN_ITEMS__FLEX_START;
        case stretch:
          return ALIGN_ITEMS__STRETCH;
        default:
          LOG.warn("Undefined alignItems: '{}'.", alignItems);
          return null;
      }
    }
  }

  public static TobagoClass valueOf(JustifyContent justifyContent) {
    if (justifyContent == null) {
      return null;
    } else {
      switch (justifyContent) {
        case center:
          return JUSTIFY_CONTENT__CENTER;
        case flexEnd:
          return JUSTIFY_CONTENT__FLEX_END;
        case flexStart:
          return JUSTIFY_CONTENT__FLEX_START;
        case spaceBetween:
          return JUSTIFY_CONTENT__SPACE_BETWEEN;
        case spaceAround:
          return JUSTIFY_CONTENT__SPACE_AROUND;
        default:
          LOG.warn("Undefined justifyContent: '{}'.", justifyContent);
          return null;
      }
    }
  }

}
