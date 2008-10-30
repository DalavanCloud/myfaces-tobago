package org.apache.myfaces.tobago.facelets.extension;

/*
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
 */

import com.sun.facelets.FaceletContext;
import com.sun.facelets.el.ELAdaptor;
import com.sun.facelets.tag.MetaRuleset;
import com.sun.facelets.tag.TagAttribute;
import com.sun.facelets.tag.jsf.ComponentConfig;
import com.sun.facelets.tag.jsf.ComponentHandler;
import com.sun.facelets.tag.jsf.ComponentSupport;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.tobago.component.Attributes;
import org.apache.myfaces.tobago.component.Facets;
import org.apache.myfaces.tobago.component.InputSuggest;
import org.apache.myfaces.tobago.component.RendererTypes;
import org.apache.myfaces.tobago.component.SupportsMarkup;
import org.apache.myfaces.tobago.component.UIGridLayout;
import org.apache.myfaces.tobago.component.UILabel;
import org.apache.myfaces.tobago.component.UIPanel;
import org.apache.myfaces.tobago.facelets.SuggestMethodRule;
import org.apache.myfaces.tobago.facelets.SupportsMarkupRule;
import org.apache.myfaces.tobago.util.ComponentUtil;
import org.apache.myfaces.tobago.util.LayoutUtil;

import javax.el.ELException;
import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import java.io.IOException;
import java.util.List;

/*
 * Date: Jul 31, 2007
 * Time: 6:14:34 PM
 */
public abstract class TobagoLabelExtensionHandler extends ComponentHandler {
  private static final Log LOG = LogFactory.getLog(TobagoLabelExtensionHandler.class);
  private static final String DEFAULT_COLUMNS = "fixed;*";
  private TagAttribute labelWidthAttribute;
  private TagAttribute tipAttribute;
  private TagAttribute labelAttribute;
  private TagAttribute markupAttribute;

  public TobagoLabelExtensionHandler(ComponentConfig config) {
    super(config);
    labelWidthAttribute = getAttribute("labelWidth");
    tipAttribute = getAttribute(Attributes.TIP);
    labelAttribute = getAttribute(Attributes.LABEL);
    markupAttribute = getAttribute(Attributes.MARKUP);
  }

  protected abstract String getSubComponentType();

  protected abstract String getSubRendererType();

  protected String getRows() {
    return "fixed";
  }

  protected String getColumns(String first) {
    return first + ";*";
  }

  protected void applyNextHandler(FaceletContext ctx, UIComponent panel)
      throws IOException, FacesException, ELException {
    if (ComponentSupport.isNew(panel)) {
      // ensure that input has no parent (isNew)
      UIComponent input  = (UIComponent) panel.getChildren().remove(1);
      nextHandler.apply(ctx, input);
      UIComponent date = null;
      if (panel.getChildCount() > 1) {
        date = (UIComponent) panel.getChildren().get(1);
      }
      panel.getChildren().add(input);
      if (date != null) {
        panel.getChildren().add(date);
      }
    } else {
      UIComponent input  = (UIComponent) panel.getChildren().get(1);
      nextHandler.apply(ctx, input);
    }
  }

  protected void onComponentCreated(FaceletContext faceletContext, UIComponent panel, UIComponent parent) {

    Application application = faceletContext.getFacesContext().getApplication();
    UIViewRoot root = ComponentSupport.getViewRoot(faceletContext, parent);

    addGridLayout(faceletContext, panel, root);

    addLabel(faceletContext, panel, root);
    String uid = root.createUniqueId();
    if (checkForAlreadyCreated(panel, uid)) {
      return;
    }

    UIComponent input = application.createComponent(getSubComponentType());
    input.setRendererType(getSubRendererType());
    input.setId(uid);

    setAttributes(faceletContext, input);

    panel.getChildren().add(input);
  }


  private void addLabel(FaceletContext faceletContext, UIComponent panel, UIViewRoot root) {
    String uid = root.createUniqueId();
    if (checkForAlreadyCreated(panel, uid)) {
      return;
    }
    Application application = faceletContext.getFacesContext().getApplication();
    UILabel label = (UILabel) application.createComponent(UILabel.COMPONENT_TYPE);
    label.setRendererType(RendererTypes.LABEL);
    label.setId(uid);
    label.getAttributes().put(Attributes.FOR, "@auto");
    if (tipAttribute != null) {
      if (tipAttribute.isLiteral()) {
        label.setTip(tipAttribute.getValue(faceletContext));
      } else {
        ValueExpression expression = tipAttribute.getValueExpression(faceletContext, String.class);
        ELAdaptor.setExpression(label, Attributes.TIP, expression);
      }
    }
    if (labelAttribute != null) {
      if (labelAttribute.isLiteral()) {
        label.setValue(labelAttribute.getValue(faceletContext));
      } else {
        ValueExpression expression = labelAttribute.getValueExpression(faceletContext, String.class);
        ELAdaptor.setExpression(label, Attributes.VALUE, expression);
      }
    }
    if (markupAttribute != null) {
      if (markupAttribute.isLiteral()) {
        ComponentUtil.setMarkup(label, markupAttribute.getValue());
      } else {
        ValueExpression expression = markupAttribute.getValueExpression(faceletContext, Object.class);
        ELAdaptor.setExpression(label, Attributes.MARKUP, expression);
      }
    }
    panel.getChildren().add(label);
  }

  private boolean checkForAlreadyCreated(UIComponent panel, String uid) {
    if (panel.getChildCount() > 0) {
      List list = panel.getChildren();
      for (int i = 0; i < list.size(); i++) {
        UIComponent child = (UIComponent) list.get(i);
        if (uid.equals(child.getId())) {
          return true;
        }
      }
    }
    return false;
  }

  private void addGridLayout(FaceletContext faceletContext, UIComponent panel, UIViewRoot root) {
    Application application = faceletContext.getFacesContext().getApplication();
    UIGridLayout gridLayout = (UIGridLayout) application.createComponent(UIGridLayout.COMPONENT_TYPE);
    gridLayout.setRendererType(RendererTypes.GRID_LAYOUT);
    if (labelWidthAttribute != null) {
      String columns = getColumns(labelWidthAttribute.getValue(faceletContext));
      if (!LayoutUtil.checkTokens(columns)) {
        LOG.warn("Illegal value for columns = \"" + columns + "\" replacing with default: \"" + DEFAULT_COLUMNS + "\"");
        columns = DEFAULT_COLUMNS;
      }
      gridLayout.setColumns(columns);
    } else {
      gridLayout.setColumns(getColumns("fixed"));
    }
    gridLayout.setRows(getRows());

    gridLayout.setId(root.createUniqueId());
    panel.getFacets().put(Facets.LAYOUT, gridLayout);
  }

  protected MetaRuleset createMetaRuleset(Class aClass) {
    MetaRuleset metaRuleset = super.createMetaRuleset(aClass);
    if (UIPanel.class.isAssignableFrom(aClass)) {
      TagAttribute [] attrs = tag.getAttributes().getAll();
      for (int i = 0; i < attrs.length; i++) {
        TagAttribute attr = attrs[i];
        if (!attr.getLocalName().equals("rendered")) {
          metaRuleset.ignore(attr.getLocalName());
        }
      }
      return metaRuleset;
    } else {
      metaRuleset.ignore(Attributes.LABEL);
      metaRuleset.ignore(Attributes.TIP);
      metaRuleset.ignore("labelWidth");
    }
    if (SupportsMarkup.class.isAssignableFrom(aClass)) {
      metaRuleset.addRule(SupportsMarkupRule.INSTANCE);
    }
    if (InputSuggest.class.isAssignableFrom(aClass)) {
      metaRuleset.addRule(SuggestMethodRule.INSTANCE);
    }
    return metaRuleset;
  }
}
