package org.apache.myfaces.tobago.taglib.extension;

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

import org.apache.myfaces.tobago.taglib.decl.HasId;
import org.apache.myfaces.tobago.taglib.decl.HasValue;
import org.apache.myfaces.tobago.taglib.decl.HasValueChangeListener;
import org.apache.myfaces.tobago.taglib.decl.IsDisabled;
import org.apache.myfaces.tobago.taglib.decl.IsInline;
import org.apache.myfaces.tobago.taglib.decl.HasLabel;
import org.apache.myfaces.tobago.taglib.decl.HasLabelWidth;
import org.apache.myfaces.tobago.taglib.decl.IsRendered;
import org.apache.myfaces.tobago.taglib.decl.HasBinding;
import org.apache.myfaces.tobago.taglib.decl.HasTip;
import org.apache.myfaces.tobago.taglib.decl.HasConverter;
import org.apache.myfaces.tobago.taglib.decl.HasValidator;
import org.apache.myfaces.tobago.taglib.decl.HasOnchange;
import org.apache.myfaces.tobago.taglib.decl.IsReadonly;
import org.apache.myfaces.tobago.taglib.decl.HasMarkup;
import org.apache.myfaces.tobago.taglib.decl.IsFocus;
import org.apache.myfaces.tobago.taglib.decl.IsRequired;
import org.apache.myfaces.tobago.taglib.decl.HasTabIndex;
import org.apache.myfaces.tobago.taglib.decl.HasRenderRange;
import org.apache.myfaces.tobago.taglib.decl.HasValidatorMessage;
import org.apache.myfaces.tobago.taglib.decl.HasRequiredMessage;
import org.apache.myfaces.tobago.taglib.decl.HasConverterMessage;
import org.apache.myfaces.tobago.internal.taglib.SelectManyCheckboxTag;
import org.apache.myfaces.tobago.apt.annotation.Tag;
import org.apache.myfaces.tobago.apt.annotation.ExtensionTag;

import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.JspException;

/**
 * Render a group of checkboxes.
 */
@Tag(name = "selectManyCheckbox")
@ExtensionTag(baseClassName = "org.apache.myfaces.tobago.internal.taglib.SelectManyCheckboxTag")
public class SelectManyCheckboxExtensionTag extends BodyTagSupport
    implements HasId, HasValue, HasValueChangeListener, IsDisabled, IsInline, //HasDeprecatedHeight,
    HasLabel, HasLabelWidth, IsRendered, HasBinding, HasTip, HasConverter, HasValidator, HasOnchange,
    HasValidatorMessage, HasRequiredMessage, HasConverterMessage,
    IsReadonly, HasMarkup, IsFocus, IsRequired, HasTabIndex, HasRenderRange {

  private String required;
  private String value;
  private String valueChangeListener;
  private String disabled;
  private String readonly;
  private String onchange;
  private String inline;
  private String label;
  private String rendered;
  private String binding;
  private String tip;
  //private String height;
  private String converter;
  private String validator;
  private String labelWidth;
  private String markup;
  private String tabIndex;
  private String focus;
  private String renderRange;
  private String validatorMessage;
  private String converterMessage;
  private String requiredMessage;

  private LabelExtensionTag labelTag;
  private SelectManyCheckboxTag selectManyCheckboxTag;

  @Override
  public int doStartTag() throws JspException {

    labelTag = new LabelExtensionTag();
    labelTag.setPageContext(pageContext);
    labelTag.setRows("*");
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
      labelTag.setColumns(labelWidth + ";*");
    }
    if (markup != null) {
      labelTag.setMarkup(markup);
    }
    labelTag.setParent(getParent());
    labelTag.doStartTag();

    selectManyCheckboxTag = new SelectManyCheckboxTag();
    selectManyCheckboxTag.setPageContext(pageContext);
    if (value != null) {
      selectManyCheckboxTag.setValue(value);
    }
    if (valueChangeListener != null) {
      selectManyCheckboxTag.setValueChangeListener(valueChangeListener);
    }
    if (binding != null) {
      selectManyCheckboxTag.setBinding(binding);
    }
    if (onchange != null) {
      selectManyCheckboxTag.setOnchange(onchange);
    }
    if (validator != null) {
      selectManyCheckboxTag.setValidator(validator);
    }
    if (converter != null) {
      selectManyCheckboxTag.setConverter(converter);
    }
    if (disabled != null) {
      selectManyCheckboxTag.setDisabled(disabled);
    }
    if (inline != null) {
      selectManyCheckboxTag.setInline(inline);
    }
    if (focus != null) {
      selectManyCheckboxTag.setFocus(focus);
    }
    if (id != null) {
      selectManyCheckboxTag.setId(id);
    }
    /*if (height != null) {
      selectManyCheckboxTag.setHeight(height);
    }*/
    if (readonly != null) {
      selectManyCheckboxTag.setReadonly(readonly);
    }
    if (required != null) {
      selectManyCheckboxTag.setRequired(required);
    }
    if (markup != null) {
      selectManyCheckboxTag.setMarkup(markup);
    }
    if (renderRange != null) {
      selectManyCheckboxTag.setRenderRange(renderRange);
    }
    if (tabIndex != null) {
      selectManyCheckboxTag.setTabIndex(tabIndex);
    }
    if (validatorMessage != null) {
      selectManyCheckboxTag.setValidatorMessage(validatorMessage);
    }
    if (converterMessage != null) {
      selectManyCheckboxTag.setConverterMessage(converterMessage);
    }
    if (requiredMessage != null) {
      selectManyCheckboxTag.setRequiredMessage(requiredMessage);
    }

    selectManyCheckboxTag.setParent(labelTag);
    selectManyCheckboxTag.doStartTag();

    return super.doStartTag();
  }

  @Override
  public int doEndTag() throws JspException {
    selectManyCheckboxTag.doEndTag();
    labelTag.doEndTag();
    return super.doEndTag();
  }

  @Override
  public void release() {
    super.release();
    binding = null;
    onchange = null;
    disabled = null;
    inline = null;
    label = null;
    labelWidth = null;
    //height = null;
    readonly = null;
    rendered = null;
    converter = null;
    validator = null;
    required = null;
    tip = null;
    value = null;
    valueChangeListener = null;
    markup = null;
    tabIndex = null;
    selectManyCheckboxTag = null;
    labelTag = null;
    focus = null;
    renderRange = null;
    validatorMessage = null;
    converterMessage = null;
    requiredMessage = null;
  }

  public void setRequired(String required) {
    this.required = required;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public void setValueChangeListener(String valueChangeListener) {
    this.valueChangeListener = valueChangeListener;
  }

  public void setDisabled(String disabled) {
    this.disabled = disabled;
  }

  public void setReadonly(String readonly) {
    this.readonly = readonly;
  }

  public void setOnchange(String onchange) {
    this.onchange = onchange;
  }

  public void setInline(String inline) {
    this.inline = inline;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  /*public void setHeight(String height) {
    this.height = height;
  } */

  public void setValidator(String validator) {
    this.validator = validator;
  }

  public void setConverter(String converter) {
    this.converter = converter;
  }

  public void setRendered(String rendered) {
    this.rendered = rendered;
  }

  public void setBinding(String binding) {
    this.binding = binding;
  }

  public void setTip(String tip) {
    this.tip = tip;
  }

  public void setLabelWidth(String labelWidth) {
    this.labelWidth = labelWidth;
  }

  public void setMarkup(String markup) {
    this.markup = markup;
  }

  public void setTabIndex(String tabIndex) {
    this.tabIndex = tabIndex;
  }

  public void setFocus(String focus) {
    this.focus = focus;
  }

  public void setRenderRange(String renderRange) {
    this.renderRange = renderRange;
  }

  public void setValidatorMessage(String validatorMessage) {
    this.validatorMessage = validatorMessage;
  }

  public void setConverterMessage(String converterMessage) {
    this.converterMessage = converterMessage;
  }

  public void setRequiredMessage(String requiredMessage) {
    this.requiredMessage = requiredMessage;
  }


}

