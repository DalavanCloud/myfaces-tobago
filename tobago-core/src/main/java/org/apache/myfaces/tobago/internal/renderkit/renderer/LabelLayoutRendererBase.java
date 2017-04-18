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

package org.apache.myfaces.tobago.internal.renderkit.renderer;

import org.apache.myfaces.tobago.component.Attributes;
import org.apache.myfaces.tobago.component.LabelLayout;
import org.apache.myfaces.tobago.component.SupportsAccessKey;
import org.apache.myfaces.tobago.component.SupportsLabelLayout;
import org.apache.myfaces.tobago.internal.util.StringUtils;
import org.apache.myfaces.tobago.renderkit.LabelWithAccessKey;
import org.apache.myfaces.tobago.renderkit.css.BootstrapClass;
import org.apache.myfaces.tobago.renderkit.css.CssItem;
import org.apache.myfaces.tobago.renderkit.css.TobagoClass;
import org.apache.myfaces.tobago.renderkit.html.DataAttributes;
import org.apache.myfaces.tobago.renderkit.html.HtmlAttributes;
import org.apache.myfaces.tobago.renderkit.html.HtmlElements;
import org.apache.myfaces.tobago.internal.util.HtmlRendererUtils;
import org.apache.myfaces.tobago.util.ComponentUtils;
import org.apache.myfaces.tobago.webapp.TobagoResponseWriter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import java.io.IOException;

/**
 * Manages the rendering of the <b>label</b> and the <b>field</b> together with different possibilities for
 * the position of the label (defined by {@link org.apache.myfaces.tobago.component.Attributes#labelLayout}
 */
public abstract class LabelLayoutRendererBase extends DecodingInputRendererBase {

  @Override
  public void encodeBegin(final FacesContext facesContext, final UIComponent component) throws IOException {

    encodeBeginSurroundingLabel(facesContext, component);

    switch (getType(component)) {
      case segmentLeft:
        if (LabelLayout.getSegment(facesContext) == LabelLayout.segmentRight) {
          encodeBeginMessageField(facesContext, component);
        }
        break;
      case segmentRight:
        if (LabelLayout.getSegment(facesContext) == LabelLayout.segmentLeft) {
          encodeBeginMessageField(facesContext, component);
        }
        break;
      default:
        encodeBeginMessageField(facesContext, component);
        break;
    }
  }

  @Override
  public void encodeEnd(final FacesContext facesContext, final UIComponent component) throws IOException {

    switch (getType(component)) {
      case segmentLeft:
        if (LabelLayout.getSegment(facesContext) == LabelLayout.segmentRight) {
          encodeEndMessageField(facesContext, component);
        }
        break;
      case segmentRight:
        if (LabelLayout.getSegment(facesContext) == LabelLayout.segmentLeft) {
          encodeEndMessageField(facesContext, component);
        }
        break;
      default:
        encodeEndMessageField(facesContext, component);
        break;
    }

    encodeEndSurroundingLabel(facesContext, component);
  }

  protected abstract void encodeBeginMessageField(FacesContext facesContext, UIComponent component) throws IOException;

  protected abstract void encodeEndMessageField(FacesContext facesContext, UIComponent component) throws IOException;

  protected void encodeBeginSurroundingLabel(final FacesContext facesContext, final UIComponent component)
      throws IOException {

    final TobagoResponseWriter writer = getResponseWriter(facesContext);
    String clientId = component.getClientId(facesContext);

    // possible values:
    // - none
    // - flexLeft (default)
    // - flexRight
    // - top
    // - segmentLeft (todo)
    // - segmentRight (todo)
    // - flowLeft (todo)
    // - flowRight (todo)
    final LabelLayout labelLayout = getType(component);
    final CssItem divClass;
    switch (labelLayout) {
      case flexLeft:
      case flexRight:
        divClass = TobagoClass.FLEX_LAYOUT;
        break;
      case segmentLeft:
        if (LabelLayout.getSegment(facesContext) == LabelLayout.segmentRight) {
          clientId += ComponentUtils.SUB_SEPARATOR + "label";
        }
        divClass = null;
        break;
      case segmentRight:
        if (LabelLayout.getSegment(facesContext) == LabelLayout.segmentLeft) {
          clientId += ComponentUtils.SUB_SEPARATOR + "label";
        }
        divClass = null;
        break;
      case none:
      case top:
      case flowLeft:
      case flowRight:
      case skip:
      default: // none, top, segmentLeft, segmentRight, flowLeft, flowRight
        divClass = null;
    }

//    if (labelLayout != LabelLayout.none) {
    writer.startElement(HtmlElements.DIV);
    writer.writeIdAttribute(clientId);
//    }
//    writer.writeClassAttribute(divClass, BootstrapClass.maximumSeverity(component));
    // todo: check if BootstrapClass.FORM_GROUP is needed, I've removed it, because of it's margin-bottom: 15px;
    // todo: so we lost too much space
    // todo: without it, e. g. an input field in the header will not be layouted correctly
//    CssItem extra = ComponentUtils.findAncestor(component, AbstractUILinks.class) != null
// ? BootstrapClass.FORM_GROUP : null;
    // TODO: optimize findAncestor() -> set a marker in AbstractUILinks?

//    writer.writeClassAttribute(divClass, extra, BootstrapClass.maximumSeverity(component));

    if (!(LabelLayout.skip == labelLayout)) {
      writer.writeClassAttribute(
          divClass,
          TobagoClass.LABEL__CONTAINER,
          BootstrapClass.FORM_GROUP,
          BootstrapClass.maximumSeverity(component),
          ComponentUtils.getBooleanAttribute(component, Attributes.required) ? TobagoClass.REQUIRED : null);
    }

    switch (labelLayout) {
      case flexLeft:
        writer.writeAttribute(DataAttributes.LAYOUT, "{\"columns\":[\"auto\",1]}", true);
        break;
      case flexRight:
        writer.writeAttribute(DataAttributes.LAYOUT, "{\"columns\":[1,\"auto\"]}", true);
        break;
      default:
        // nothing to do
    }

    switch (labelLayout) {
      case none:
      case skip:
      case flexRight:
      case flowRight:
        break;
      case segmentLeft:
        if (LabelLayout.getSegment(facesContext) == LabelLayout.segmentLeft) {
          encodeLabel(component, writer, labelLayout);
        }
        break;
      case segmentRight:
        if (LabelLayout.getSegment(facesContext) == LabelLayout.segmentRight) {
          encodeLabel(component, writer, labelLayout);
        }
        break;
      default:
        encodeLabel(component, writer, labelLayout);
    }
  }

  protected void encodeEndSurroundingLabel(final FacesContext facesContext, final UIComponent component)
      throws IOException {

    final TobagoResponseWriter writer = getResponseWriter(facesContext);
    final LabelLayout labelLayout = getType(component);

    switch (labelLayout) {
      case flexRight:
      case flowRight:
        encodeLabel(component, writer, labelLayout);
        break;
      default:
        // nothing to do
    }

//    if (labelLayout != LabelLayout.none) {
    writer.endElement(HtmlElements.DIV);
//    }
  }

  protected void encodeLabel(UIComponent component, TobagoResponseWriter writer, LabelLayout labelLayout)
      throws IOException {
    // TBD: maybe use an interface for getLabel()
    final String label = ComponentUtils.getStringAttribute(component, Attributes.label);
    if (StringUtils.isNotBlank(label)) {
      writer.startElement(HtmlElements.LABEL);
      writer.writeAttribute(HtmlAttributes.FOR, component.getClientId(), false);
      writer.writeClassAttribute(TobagoClass.LABEL, BootstrapClass.COL_FORM_LABEL);
      if (component instanceof SupportsAccessKey) {
        LabelWithAccessKey labelWithAccessKey = new LabelWithAccessKey((SupportsAccessKey) component);
        HtmlRendererUtils.writeLabelWithAccessKey(writer, labelWithAccessKey);
      } else {
        writer.writeText(label);
      }
      writer.endElement(HtmlElements.LABEL);
    }
  }

  private LabelLayout getType(UIComponent component) {
    return component instanceof SupportsLabelLayout
        ? ((SupportsLabelLayout) component).getLabelLayout()
        : LabelLayout.skip;
  }

}
