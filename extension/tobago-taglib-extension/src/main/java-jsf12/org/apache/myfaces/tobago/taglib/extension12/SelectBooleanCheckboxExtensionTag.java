package org.apache.myfaces.tobago.taglib.extension12;

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

import org.apache.myfaces.tobago.apt.annotation.DynamicExpression;
import org.apache.myfaces.tobago.apt.annotation.ExtensionTag;
import org.apache.myfaces.tobago.apt.annotation.Tag;
import org.apache.myfaces.tobago.apt.annotation.TagAttribute;
import org.apache.myfaces.tobago.apt.annotation.UIComponentTagAttribute;
import org.apache.myfaces.tobago.internal.taglib.SelectBooleanCheckboxTag;

import javax.servlet.jsp.JspException;

/*
 * Date: Oct 7, 2006
 * Time: 9:13:21 AM
 */
/**
 * Renders a checkbox.
 */
@Tag(name = "selectBooleanCheckbox")
@ExtensionTag(baseClassName = "org.apache.myfaces.tobago.internal.taglib.SelectBooleanCheckboxTag")
public class SelectBooleanCheckboxExtensionTag extends TobagoExtensionBodyTagSupport {

  private javax.el.ValueExpression value;
  private javax.el.MethodExpression valueChangeListener;
  private javax.el.ValueExpression disabled;
  private javax.el.ValueExpression readonly;
  private javax.el.ValueExpression onchange;
  private javax.el.ValueExpression label;
  private javax.el.ValueExpression rendered;
  private javax.el.ValueExpression binding;
  private javax.el.ValueExpression tip;
  private javax.el.ValueExpression converter;
  private javax.el.MethodExpression validator;
  private javax.el.ValueExpression labelWidth;
  private javax.el.ValueExpression markup;
  private javax.el.ValueExpression tabIndex;
  private javax.el.ValueExpression required;
  private javax.el.ValueExpression focus;
  private javax.el.ValueExpression validatorMessage;
  private javax.el.ValueExpression converterMessage;
  private javax.el.ValueExpression requiredMessage;

  private LabelExtensionTag labelTag;
  private SelectBooleanCheckboxTag selectBooleanCheckboxTag;

  @Override
  public int doStartTag() throws JspException {

    labelTag = new LabelExtensionTag();
    labelTag.setPageContext(pageContext);
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
    labelTag.doStartTag();

    selectBooleanCheckboxTag = new SelectBooleanCheckboxTag();
    selectBooleanCheckboxTag.setPageContext(pageContext);
    if (value != null) {
      selectBooleanCheckboxTag.setValue(value);
    }
    if (valueChangeListener != null) {
      selectBooleanCheckboxTag.setValueChangeListener(valueChangeListener);
    }
    if (binding != null) {
      selectBooleanCheckboxTag.setBinding(binding);
    }
    if (onchange != null) {
      selectBooleanCheckboxTag.setOnchange(onchange);
    }
    if (validator != null) {
      selectBooleanCheckboxTag.setValidator(validator);
    }
    if (converter != null) {
      selectBooleanCheckboxTag.setConverter(converter);
    }
    if (disabled != null) {
      selectBooleanCheckboxTag.setDisabled(disabled);
    }

    if (id != null) {
      selectBooleanCheckboxTag.setId(id);
    }

    if (readonly != null) {
      selectBooleanCheckboxTag.setReadonly(readonly);
    }

    if (focus != null) {
      selectBooleanCheckboxTag.setFocus(focus);
    }

    if (required != null) {
      selectBooleanCheckboxTag.setRequired(required);
    }
    // TODO item Label
    //if (itemLabel != null) {
    //  selectOneRadioTag.setLabel(itemLabel);
    //}

    if (markup != null) {
      selectBooleanCheckboxTag.setMarkup(markup);
    }
    if (tabIndex != null) {
      selectBooleanCheckboxTag.setTabIndex(tabIndex);
    }
    if (validatorMessage != null) {
      selectBooleanCheckboxTag.setValidatorMessage(validatorMessage);
    }
    if (converterMessage != null) {
      selectBooleanCheckboxTag.setConverterMessage(converterMessage);
    }
    if (requiredMessage != null) {
      selectBooleanCheckboxTag.setRequiredMessage(requiredMessage);
    }
    selectBooleanCheckboxTag.setParent(labelTag);
    selectBooleanCheckboxTag.doStartTag();

    return super.doStartTag();
  }

  @Override
  public int doEndTag() throws JspException {
    selectBooleanCheckboxTag.doEndTag();
    labelTag.doEndTag();
    return super.doEndTag();
  }

  @Override
  public void release() {
    super.release();
    binding = null;
    onchange = null;
    disabled = null;
    label = null;
    labelWidth = null;
    readonly = null;
    rendered = null;
    converter = null;
    validator = null;
    tip = null;
    value = null;
    valueChangeListener = null;
    markup = null;
    tabIndex = null;
    focus = null;
    required = null;
    selectBooleanCheckboxTag = null;
    labelTag = null;
    validatorMessage = null;
    converterMessage = null;
    requiredMessage = null;
  }

  /**
   * The current value of this component.
   */
  @TagAttribute
  @UIComponentTagAttribute(type = "java.lang.Boolean")
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
   * Flag indicating that this element is disabled.
   */
  @TagAttribute()
  @UIComponentTagAttribute(type = "java.lang.Boolean", defaultValue = "Boolean.FALSE")
  public void setDisabled(javax.el.ValueExpression disabled) {
    this.disabled = disabled;
  }

  /**
   * Flag indicating that this component will prohibit changes by the user.
   */
  @TagAttribute
  @UIComponentTagAttribute(type = "java.lang.Boolean")
  public void setReadonly(javax.el.ValueExpression readonly) {
    this.readonly = readonly;
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
   * Text value to display as label.
   * If text contains an underscore the next character is used as accesskey.
   */
  @TagAttribute
  @UIComponentTagAttribute()
  public void setLabel(javax.el.ValueExpression label) {
    this.label = label;
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
      methodSignature = { "javax.faces.context.FacesContext", "javax.faces.component.UIComponent", "java.lang.Object" })
  public void setValidator(javax.el.MethodExpression validator) {
    this.validator = validator;
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
   * Flag indicating whether or not this component should be rendered
   * (during Render Response Phase), or processed on any subsequent form submit.
   */
  @TagAttribute
  @UIComponentTagAttribute(type = "java.lang.Boolean", defaultValue = "Boolean.TRUE")
  public void setRendered(javax.el.ValueExpression rendered) {
    this.rendered = rendered;
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
   * Text value to display as tooltip.
   */
  @TagAttribute
  @UIComponentTagAttribute()
  public void setTip(javax.el.ValueExpression tip) {
    this.tip = tip;
  }

  /**
   * The width for the label component. Default: 'fixed'.
   * This value is used in the gridLayouts columns attribute.
   * See gridLayout tag for valid values.
   */
  @TagAttribute
  @UIComponentTagAttribute()
  public void setLabelWidth(javax.el.ValueExpression labelWidth) {
    this.labelWidth = labelWidth;
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

  @TagAttribute
  @UIComponentTagAttribute(type = "java.lang.Integer")
  public void setTabIndex(javax.el.ValueExpression tabIndex) {
    this.tabIndex = tabIndex;
  }

  /**
   * Flag indicating this component should recieve the focus.
   */
  @TagAttribute
  @UIComponentTagAttribute(type = "java.lang.Boolean")
  public void setFocus(javax.el.ValueExpression focus) {
    this.focus = focus;
  }

  /**
   * Flag indicating that a value is required.
   * If the value is an empty string a
   * ValidationError occurs and a Error Message is rendered.
   */
  @TagAttribute
  @UIComponentTagAttribute(type = "java.lang.Boolean")
  public void setRequired(javax.el.ValueExpression required) {
    this.required = required;
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

}
