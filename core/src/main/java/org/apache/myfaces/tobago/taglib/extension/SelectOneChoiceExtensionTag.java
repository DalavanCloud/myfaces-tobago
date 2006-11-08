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

import org.apache.myfaces.tobago.apt.annotation.Tag;
import org.apache.myfaces.tobago.taglib.component.SelectOneChoiceTag;
import org.apache.myfaces.tobago.taglib.decl.HasBinding;
import org.apache.myfaces.tobago.taglib.decl.HasConverter;
import org.apache.myfaces.tobago.taglib.decl.HasId;
import org.apache.myfaces.tobago.taglib.decl.HasLabel;
import org.apache.myfaces.tobago.taglib.decl.HasOnchange;
import org.apache.myfaces.tobago.taglib.decl.HasTip;
import org.apache.myfaces.tobago.taglib.decl.HasValidator;
import org.apache.myfaces.tobago.taglib.decl.HasValue;
import org.apache.myfaces.tobago.taglib.decl.IsDisabled;
import org.apache.myfaces.tobago.taglib.decl.IsInline;
import org.apache.myfaces.tobago.taglib.decl.IsReadonly;
import org.apache.myfaces.tobago.taglib.decl.IsRendered;
import org.apache.myfaces.tobago.taglib.decl.HasValueChangeListener;
import org.apache.myfaces.tobago.taglib.decl.IsRequired;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * Render a single selection dropdown list with a label.
 */

@Tag(name = "selectOneChoice")
public class SelectOneChoiceExtensionTag
    extends BodyTagSupport
    implements HasId, HasValue, HasValueChangeListener, IsDisabled,
    IsReadonly, HasOnchange, IsInline, HasLabel, IsRequired,
    IsRendered, HasBinding, HasTip , HasValidator, HasConverter {

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
  private String validator;
  private String converter;

  private LabelExtensionTag labelTag;
  private SelectOneChoiceTag selectOneChoiceTag;

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
    labelTag.setParent(getParent());
    labelTag.doStartTag();

    selectOneChoiceTag = new SelectOneChoiceTag();
    selectOneChoiceTag.setPageContext(pageContext);
    if (value != null) {
      selectOneChoiceTag.setValue(value);
    }
    if (valueChangeListener != null) {
      selectOneChoiceTag.setValueChangeListener(valueChangeListener);
    }
    if (validator != null) {
      selectOneChoiceTag.setValidator(validator);
    }
    if (converter != null) {
      selectOneChoiceTag.setConverter(converter);
    }
    if (binding != null) {
      selectOneChoiceTag.setBinding(binding);
    }
    if (onchange != null) {
      selectOneChoiceTag.setOnchange(onchange);
    }
    if (disabled != null) {
      selectOneChoiceTag.setDisabled(disabled);
    }

    if (inline != null) {
      selectOneChoiceTag.setFocus(inline);
    }
    if (id != null) {
      selectOneChoiceTag.setId(id);
    }
    if (readonly != null) {
      selectOneChoiceTag.setReadonly(readonly);
    }
    if (required != null) {
      selectOneChoiceTag.setRequired(required);
    }
    selectOneChoiceTag.setParent(labelTag);
    selectOneChoiceTag.doStartTag();

    return super.doStartTag();
  }

  @Override
  public int doEndTag() throws JspException {
    selectOneChoiceTag.doEndTag();
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
    converter = null;
    validator = null;
    readonly = null;
    rendered = null;
    required = null;
    tip = null;
    value = null;
    valueChangeListener = null;
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

  public void setValidator(String validator) {
    this.validator = validator;
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

  public void setConverter(String converter) {
    this.converter = converter;
  }

  public void setInline(String inline) {
    this.inline = inline;
  }

  public void setLabel(String label) {
    this.label = label;
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

}
