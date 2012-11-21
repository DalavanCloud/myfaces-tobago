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

package org.apache.myfaces.tobago.internal.taglib.extension;

import org.apache.myfaces.tobago.apt.annotation.DynamicExpression;
import org.apache.myfaces.tobago.apt.annotation.ExtensionTag;
import org.apache.myfaces.tobago.apt.annotation.Tag;
import org.apache.myfaces.tobago.apt.annotation.TagAttribute;
import org.apache.myfaces.tobago.apt.annotation.UIComponentTagAttribute;
import org.apache.myfaces.tobago.internal.taglib.InTag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.jsp.JspException;

/**
 * Renders a text input field with a label.
 * <br />
 * Short syntax of:
 * <p/>
 * <pre>
 * &lt;tc:panel>
 *   &lt;f:facet name="layout">
 *     &lt;tc:gridLayout columns="auto;*"/>
 *   &lt;/f:facet>
 *   &lt;tc:label value="#{label}" for="@auto"/>
 *   &lt;tc:in value="#{value}">
 *     ...
 *   &lt;/tc:in>
 * &lt;/tc:panel>
 * </pre>
 */

@Tag(name = "in")
@ExtensionTag(baseClassName = "org.apache.myfaces.tobago.internal.taglib.InTag")
public class InExtensionTag extends TobagoExtensionBodyTagSupport {

  private javax.el.ValueExpression binding;
  private javax.el.ValueExpression converter;
  private javax.el.MethodExpression validator;
  private javax.el.ValueExpression disabled;
  private javax.el.ValueExpression focus;
  private javax.el.ValueExpression label;
  private javax.el.ValueExpression password;
  private javax.el.ValueExpression readonly;
  private javax.el.ValueExpression rendered;
  private javax.el.ValueExpression required;
  private javax.el.ValueExpression tip;
  private javax.el.ValueExpression value;
  private javax.el.MethodExpression valueChangeListener;
  private javax.el.ValueExpression onchange;
  private javax.el.MethodExpression suggestMethod;
  private javax.el.ValueExpression suggestMinChars;
  private javax.el.ValueExpression suggestDelay;
  private javax.el.ValueExpression markup;
  private javax.el.ValueExpression labelWidth;
  private javax.el.ValueExpression tabIndex;
  private javax.el.ValueExpression validatorMessage;
  private javax.el.ValueExpression converterMessage;
  private javax.el.ValueExpression requiredMessage;
  private String fieldId;

  private LabelExtensionTag labelTag;
  private InTag inTag;

  @Override
  public int doStartTag() throws JspException {

    labelTag = new LabelExtensionTag();
    labelTag.setPageContext(pageContext);
    if (id != null) {
      labelTag.setId(id);
    }
    if (label != null) {
      labelTag.setValue(label);
    }
    if (tip != null) {
      labelTag.setTip(tip);
    }
    if (rendered != null) {
      labelTag.setRendered(rendered);
    }
    if (labelWidth != null) {
      labelTag.setColumns(createStringValueExpression(labelWidth.getExpressionString() + ";*"));
    }
    if (markup != null) {
      labelTag.setMarkup(markup);
    }
    labelTag.setParent(getParent());
    labelTag.setJspId(jspId + PREFIX + idSuffix++);
    labelTag.doStartTag();

    inTag = new InTag();
    inTag.setPageContext(pageContext);
    if (value != null) {
      inTag.setValue(value);
    }
    if (valueChangeListener != null) {
      inTag.setValueChangeListener(valueChangeListener);
    }
    if (label != null) {
      inTag.setLabel(label);
    }
    if (binding != null) {
      inTag.setBinding(binding);
    }
    if (converter != null) {
      inTag.setConverter(converter);
    }
    if (validator != null) {
      inTag.setValidator(validator);
    }
    if (onchange != null) {
      inTag.setOnchange(onchange);
    }
    if (suggestMethod != null) {
      inTag.setSuggestMethod(suggestMethod);
    }
    if (suggestMinChars != null) {
      inTag.setSuggestMinChars(suggestMinChars);
    }
    if (suggestDelay != null) {
      inTag.setSuggestDelay(suggestDelay);
    }
    if (disabled != null) {
      inTag.setDisabled(disabled);
    }
    if (focus != null) {
      inTag.setFocus(focus);
    }
    if (fieldId != null) {
      inTag.setId(fieldId);
    }
    if (password != null) {
      inTag.setPassword(password);
    }
    if (readonly != null) {
      inTag.setReadonly(readonly);
    }
    if (required != null) {
      inTag.setRequired(required);
    }
    if (markup != null) {
      inTag.setMarkup(markup);
    }
    if (tabIndex != null) {
      inTag.setTabIndex(tabIndex);
    }
    if (validatorMessage != null) {
      inTag.setValidatorMessage(validatorMessage);
    }
    if (converterMessage != null) {
      inTag.setConverterMessage(converterMessage);
    }
    if (requiredMessage != null) {
      inTag.setRequiredMessage(requiredMessage);
    }
    inTag.setParent(labelTag);
    inTag.setJspId(jspId + PREFIX + idSuffix++);
    inTag.doStartTag();

    return super.doStartTag();
  }

  @Override
  public int doEndTag() throws JspException {
    inTag.doEndTag();
    labelTag.doEndTag();
    return super.doEndTag();
  }

  private static final Logger LOG = LoggerFactory.getLogger(InExtensionTag.class);

  @Override
  public void release() {
    super.release();
    binding = null;
    converter = null;
    validator = null;
    disabled = null;
    labelWidth = null;
    focus = null;
    label = null;
    password = null;
    readonly = null;
    rendered = null;
    required = null;
    tip = null;
    value = null;
    valueChangeListener = null;
    onchange = null;
    suggestMethod = null;
    suggestMinChars = null;
    suggestDelay = null;
    markup = null;
    tabIndex = null;
    inTag = null;
    labelTag = null;
    validatorMessage = null;
    converterMessage = null;
    requiredMessage = null;
    fieldId = null;
  }

  /**
   * Indicate markup of this component.
   * Possible value is 'none'. But this can be overridden in the theme.
   */
  @TagAttribute
  @UIComponentTagAttribute(defaultValue = "none", type = "java.lang.String[]")
  public void setMarkup(javax.el.ValueExpression markup) {
    this.markup = markup;
  }

  /**
   * The current value of this component.
   */
  @TagAttribute
  @UIComponentTagAttribute(type = "java.lang.Object")
  public void setValue(javax.el.ValueExpression value) {
    this.value = value;
  }

  /**
   * MethodBinding representing a value change listener method
   * that will be notified when a new value has been set for this input component.
   * The expression must evaluate to a public method that takes a ValueChangeEvent
   * parameter, with a return type of void.
   *
   * @param valueChangeListener
   */
  @TagAttribute
  @UIComponentTagAttribute(
      type = {},
      expression = DynamicExpression.METHOD_BINDING_REQUIRED,
      methodSignature = "javax.faces.event.ValueChangeEvent")
  public void setValueChangeListener(javax.el.MethodExpression valueChangeListener) {
    this.valueChangeListener = valueChangeListener;
  }

  /**
   * Text value to display as label.
   * If text contains an underscore the next character is used as accesskey.
   */
  @TagAttribute
  @UIComponentTagAttribute()
  public void setLabel(javax.el.ValueExpression label) {
    this.label = label;
  }

  /**
   * Flag indicating this component should receive the focus.
   */
  @TagAttribute
  @UIComponentTagAttribute(type = "boolean", defaultValue = "false")
  public void setFocus(javax.el.ValueExpression focus) {
    this.focus = focus;
  }

  /**
   * The value binding expression linking this
   * component to a property in a backing bean.
   */
  @TagAttribute
  @UIComponentTagAttribute(type = "javax.faces.component.UIComponent")
  public void setBinding(javax.el.ValueExpression binding) {
    this.binding = binding;
  }

  /**
   * Flag indicating whether or not this component should be rendered
   * (during Render Response Phase), or processed on any subsequent form submit.
   */
  @TagAttribute
  @UIComponentTagAttribute(type = "boolean", defaultValue = "true")
  public void setRendered(javax.el.ValueExpression rendered) {
    this.rendered = rendered;
  }

  /**
   * An expression that specifies the Converter for this component.
   * If the value binding expression is a String,
   * the String is used as an ID to look up a Converter.
   * If the value binding expression is a Converter,
   * uses that instance as the converter.
   * The value can either be a static value (ID case only)
   * or an EL expression.
   */
  @TagAttribute
  @UIComponentTagAttribute(type = "javax.faces.convert.Converter",
      expression = DynamicExpression.VALUE_BINDING)
  public void setConverter(javax.el.ValueExpression converter) {
    this.converter = converter;
  }

  /**
   * Clientside script function to add to this component's onchange handler.
   */
  @TagAttribute
  @UIComponentTagAttribute()
  public void setOnchange(javax.el.ValueExpression onchange) {
    this.onchange = onchange;
  }

  /**
   * MethodBinding which generates a list of suggested input values based on
   * the currently entered text, which could be retrieved via getSubmittedValue() on the UIInput.
   * The expression has to evaluate to a public method which has a javax.faces.component.UIInput parameter
   * and returns a List&lt;String>(deprecated), a List&lt;org.apache.myfaces.tobago.model.AutoSuggestItem>
   * or a org.apache.myfaces.tobago.model.AutoSuggestItems.
   */
  @TagAttribute
  @UIComponentTagAttribute(type = {},
      expression = DynamicExpression.METHOD_BINDING_REQUIRED,
      methodSignature = "java.lang.String")
  public void setSuggestMethod(javax.el.MethodExpression suggestMethod) {
    this.suggestMethod = suggestMethod;
  }

  /**
   * Minimum number of chars to type before the list will be requested.
   *
   * @since 1.5.9 and 1.6.0
   */
  @TagAttribute
  @UIComponentTagAttribute(type = "java.lang.Integer", defaultValue = "1")
  public void setSuggestMinChars(javax.el.ValueExpression suggestMinChars) {
    this.suggestMinChars = suggestMinChars;
  }

  /**
   * Time in milli seconds before the list will be requested.
   *
   * @since 1.5.9 and 1.6.0
   */
  @TagAttribute
  @UIComponentTagAttribute(type = "java.lang.Integer", defaultValue = "300")
  public void setSuggestDelay(javax.el.ValueExpression suggestDelay) {
    this.suggestDelay = suggestDelay;
  }

  /**
   * A method binding EL expression,
   * accepting FacesContext, UIComponent,
   * and Object parameters, and returning void, that validates
   * the component's local value.
   */
  @TagAttribute
  @UIComponentTagAttribute(type = {},
      expression = DynamicExpression.METHOD_BINDING,
      methodSignature = {"javax.faces.context.FacesContext", "javax.faces.component.UIComponent", "java.lang.Object"})
  public void setValidator(javax.el.MethodExpression validator) {
    this.validator = validator;
  }

  /**
   * Flag indicating whether or not this component should be rendered as
   * password field , so you will not see the typed charakters.
   */
  @TagAttribute
  @UIComponentTagAttribute(type = "boolean", defaultValue = "false")
  public void setPassword(javax.el.ValueExpression password) {
    this.password = password;
  }

  /**
   * Flag indicating that this component will prohibit changes by the user.
   */
  @TagAttribute
  @UIComponentTagAttribute(type = "boolean", defaultValue = "false")
  public void setReadonly(javax.el.ValueExpression readonly) {
    this.readonly = readonly;
  }

  /**
   * Flag indicating that this element is disabled.
   */
  @TagAttribute()
  @UIComponentTagAttribute(type = "boolean", defaultValue = "false")
  public void setDisabled(javax.el.ValueExpression disabled) {
    this.disabled = disabled;
  }

  /**
   * Flag indicating that a value is required.
   * If the value is an empty string a
   * ValidationError occurs and a Error Message is rendered.
   */
  @TagAttribute
  @UIComponentTagAttribute(type = "boolean", defaultValue = "false")
  public void setRequired(javax.el.ValueExpression required) {
    this.required = required;
  }

  /**
   * Text value to display as tooltip.
   */
  @TagAttribute
  @UIComponentTagAttribute()
  public void setTip(javax.el.ValueExpression tip) {
    this.tip = tip;
  }

  /**
   * The width for the label component. Default: 'auto'.
   * This value is used in the gridLayouts columns attribute.
   * See gridLayout tag for valid values.
   */
  @TagAttribute
  @UIComponentTagAttribute()
  public void setLabelWidth(javax.el.ValueExpression labelWidth) {
    this.labelWidth = labelWidth;
  }

  @TagAttribute
  @UIComponentTagAttribute(type = "java.lang.Integer")
  public void setTabIndex(javax.el.ValueExpression tabIndex) {
    this.tabIndex = tabIndex;
  }

  /**
   * An expression that specifies the validator message
   */
  @TagAttribute
  @UIComponentTagAttribute()
  public void setValidatorMessage(javax.el.ValueExpression validatorMessage) {
    this.validatorMessage = validatorMessage;
  }

  /**
   * An expression that specifies the converter message
   */
  @TagAttribute
  @UIComponentTagAttribute()
  public void setConverterMessage(javax.el.ValueExpression converterMessage) {
    this.converterMessage = converterMessage;
  }

  /**
   * An expression that specifies the required message
   */
  @TagAttribute
  @UIComponentTagAttribute()
  public void setRequiredMessage(javax.el.ValueExpression requiredMessage) {
    this.requiredMessage = requiredMessage;
  }

  /**
   * The component identifier for the input field component inside of the container.
   * This value must be unique within the closest parent component that is a naming container.
   */
  @TagAttribute(rtexprvalue = true)
  @UIComponentTagAttribute
  public void setFieldId(String fieldId) {
    this.fieldId = fieldId;
  }

  /**
   * The component identifier for this component.
   * This value must be unique within the closest parent component that is a naming container.
   * For tx components the id will be set to the container (e. g. the panel).
   * To set the id of the input field, you have to use the attribute "fieldId".
   */
  @TagAttribute(rtexprvalue = true)
  @UIComponentTagAttribute
  public void setId(String id) {
    super.setId(id);
  }
}
