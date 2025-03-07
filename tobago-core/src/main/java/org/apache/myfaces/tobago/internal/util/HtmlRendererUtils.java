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

package org.apache.myfaces.tobago.internal.util;

import org.apache.myfaces.tobago.component.Attributes;
import org.apache.myfaces.tobago.component.RendererTypes;
import org.apache.myfaces.tobago.component.Tags;
import org.apache.myfaces.tobago.component.Visual;
import org.apache.myfaces.tobago.context.Markup;
import org.apache.myfaces.tobago.context.TobagoContext;
import org.apache.myfaces.tobago.internal.component.AbstractUIStyle;
import org.apache.myfaces.tobago.internal.webapp.TobagoResponseWriterWrapper;
import org.apache.myfaces.tobago.renderkit.LabelWithAccessKey;
import org.apache.myfaces.tobago.renderkit.css.Icons;
import org.apache.myfaces.tobago.renderkit.css.TobagoClass;
import org.apache.myfaces.tobago.renderkit.html.DataAttributes;
import org.apache.myfaces.tobago.renderkit.html.HtmlAttributes;
import org.apache.myfaces.tobago.renderkit.html.HtmlElements;
import org.apache.myfaces.tobago.util.ComponentUtils;
import org.apache.myfaces.tobago.webapp.TobagoResponseWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;

public final class HtmlRendererUtils {

  private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  private static final String FOCUS_KEY = HtmlRendererUtils.class.getName() + ".FocusId";

  private HtmlRendererUtils() {
    // to prevent instantiation
  }

  public static void renderFocus(
      final String clientId, final boolean focus, final boolean error, final FacesContext facesContext,
      final TobagoResponseWriter writer) throws IOException {
    final Map<String, Object> requestMap = facesContext.getExternalContext().getRequestMap();
    if (!requestMap.containsKey(FOCUS_KEY)
        && (clientId.equals(TobagoContext.getInstance(facesContext).getFocusId()) || focus || error)) {
      requestMap.put(FOCUS_KEY, Boolean.TRUE);
      writer.writeAttribute(HtmlAttributes.AUTOFOCUS, true);
    }
  }

  public static String getRendererName(final FacesContext facesContext, final UIComponent component) {
    final String rendererType = component.getRendererType();
    return rendererType.substring(0, 1).toLowerCase(Locale.ENGLISH) + rendererType.substring(1);
  }

  public static void writeLabelWithAccessKey(final TobagoResponseWriter writer, final LabelWithAccessKey label)
      throws IOException {
    final int pos = label.getPos();
    final String text = label.getLabel();
    if (text != null) {
      if (pos == -1) {
        writer.writeText(text);
      } else {
        writer.writeText(text.substring(0, pos));
        writer.startElement(HtmlElements.U);
        writer.writeText(Character.toString(text.charAt(pos)));
        writer.endElement(HtmlElements.U);
        writer.writeText(text.substring(pos + 1));
      }
    }
  }

  /**
   * @deprecated 4.0.0.
   */
  @Deprecated
  public static void encodeIconWithLabel(
      final TobagoResponseWriter writer, final String image, final String label) throws IOException {
    if (image != null && image.startsWith("fa-")) { // XXX hack: should be integrated in the resource manager
      writer.writeIcon(null, Icons.custom(image)); // todo: should not be static
    }
    if (label != null) {
      writer.startElement(HtmlElements.SPAN);
      writer.writeText(label);
      writer.endElement(HtmlElements.SPAN);
    }
  }

  public static void encodeIconOrImage(final TobagoResponseWriter writer, final String image) throws IOException {
    if (image != null) {
      if (image.startsWith("fa-")) {
        writer.startElement(HtmlElements.I);
        writer.writeClassAttribute(Icons.FA, Icons.custom(image));
        writer.endElement(HtmlElements.I);
      } else {
        writer.startElement(HtmlElements.IMG);
        writer.writeAttribute(HtmlAttributes.SRC, image, true);
        writer.writeAttribute(HtmlAttributes.ALT, "", false);
        writer.endElement(HtmlElements.IMG);
      }
    }
  }

  /**
   * @deprecated 4.0.0.
   */
  @Deprecated
  public static void encodeIconWithLabel(
      final TobagoResponseWriter writer, final FacesContext facesContext, final String image,
      final LabelWithAccessKey label, final boolean disabled)
      throws IOException {
    if (image != null) {
      if (image.startsWith("fa-")) {
        writer.writeIcon(null, Icons.custom(image)); // todo: should not be static
      } else {
        writer.startElement(HtmlElements.IMG);
        writer.writeAttribute(HtmlAttributes.SRC, image, true);
        writer.writeAttribute(HtmlAttributes.ALT, "", false);
        writer.endElement(HtmlElements.IMG);
      }
    }

    if (label.getLabel() != null) {
      writer.startElement(HtmlElements.SPAN);
      HtmlRendererUtils.writeLabelWithAccessKey(writer, label);
      writer.endElement(HtmlElements.SPAN);
    }
  }

  /**
   * @deprecated since 3.0.0, use {@link org.apache.myfaces.tobago.renderkit.RendererBase#getResponseWriter}
   */
  @Deprecated
  public static TobagoResponseWriter getTobagoResponseWriter(final FacesContext facesContext) {

    final ResponseWriter writer = facesContext.getResponseWriter();
    if (writer instanceof TobagoResponseWriter) {
      return (TobagoResponseWriter) writer;
    } else {
      return new TobagoResponseWriterWrapper(writer);
    }
  }

  public static String getTitleFromTipAndMessages(final FacesContext facesContext, final UIComponent component) {
    final String messages = ComponentUtils.getFacesMessageAsString(facesContext, component);
    return HtmlRendererUtils.addTip(messages, ComponentUtils.getAttribute(component, Attributes.tip));
  }

  public static String addTip(final String title, final Object tip) {
    String result = title;
    if (tip != null) {
      if (result != null && result.length() > 0) {
        result += " :: ";
      } else {
        result = "";
      }
      result += tip;
    }
    return result;
  }

  /**
   * @deprecated Since Tobago 2.0.7
   */
  @Deprecated
  public static void renderSelectItems(final UIInput component, final TobagoClass optionClass,
      final Iterable<SelectItem> items, final Object[] values, final TobagoResponseWriter writer,
      final FacesContext facesContext) throws IOException {
    renderSelectItems(component, optionClass, items, values, null, null, writer, facesContext);
  }

  public static void renderSelectItems(final UIInput component, final TobagoClass optionClass,
      final Iterable<SelectItem> items, final Object[] values, final String[] submittedValues,
      final TobagoResponseWriter writer, final FacesContext facesContext) throws IOException {
    renderSelectItems(component, optionClass, items, values, submittedValues, null, writer, facesContext);
  }

  public static void renderSelectItems(final UIInput component, final TobagoClass optionClass,
      final Iterable<SelectItem> items, final Object value, final String submittedValue,
      final TobagoResponseWriter writer, final FacesContext facesContext) throws IOException {
    renderSelectItems(component, optionClass, items, value != null ? new Object[]{value} : null,
        submittedValue != null ? new String[]{submittedValue} : null, null, writer, facesContext);
  }

  public static void renderSelectItems(final UIInput component, final TobagoClass optionClass,
      final Iterable<SelectItem> items, final Object[] values, final String[] submittedValues,
      final Boolean onlySelected, final TobagoResponseWriter writer, final FacesContext facesContext)
      throws IOException {

    if (LOG.isDebugEnabled()) {
      LOG.debug("component id = '{}'", component.getId());
      LOG.debug("values = '{}'", Arrays.toString(values));
      LOG.debug("submittedValues = '{}'", Arrays.toString(submittedValues));
    }
    for (final SelectItem item : items) {
      if (item instanceof SelectItemGroup) {
        writer.startElement(HtmlElements.OPTGROUP);
        writer.writeAttribute(HtmlAttributes.LABEL, item.getLabel(), true);
        if (item.isDisabled()) {
          writer.writeAttribute(HtmlAttributes.DISABLED, true);
        }
        final SelectItem[] selectItems = ((SelectItemGroup) item).getSelectItems();
        renderSelectItems(component, optionClass, Arrays.asList(selectItems), values, submittedValues,
            onlySelected, writer, facesContext);
        writer.endElement(HtmlElements.OPTGROUP);
      } else {

        Object itemValue = item.getValue();
        // when using selectItem tag with a literal value: use the converted value
        if (itemValue instanceof String && values != null && values.length > 0 && !(values[0] instanceof String)) {
          itemValue = ComponentUtils.getConvertedValue(facesContext, component, (String) itemValue);
        }
        final String formattedValue = ComponentUtils.getFormattedValue(facesContext, component, itemValue);
        final boolean contains;
        if (submittedValues == null) {
          contains = ArrayUtils.contains(values, itemValue);
        } else {
          contains = ArrayUtils.contains(submittedValues, formattedValue);
        }
        if (onlySelected != null) {
          if (onlySelected) {
            if (!contains) {
              continue;
            }
          } else {
            if (contains) {
              continue;
            }
          }
        }
        writer.startElement(HtmlElements.OPTION);
        writer.writeAttribute(HtmlAttributes.VALUE, formattedValue, true);
        if (item instanceof org.apache.myfaces.tobago.model.SelectItem) {
          final String image = ((org.apache.myfaces.tobago.model.SelectItem) item).getImage();
          if (image != null) {
            final AbstractUIStyle style = (AbstractUIStyle) facesContext.getApplication()
                .createComponent(facesContext, Tags.style.componentType(), RendererTypes.Style.name());
            style.setTransient(true);
            style.setBackgroundImage(image);
            style.setSelector(
                StyleRenderUtils.encodeIdSelector(component.getClientId(facesContext))
                    + " option[value=" + formattedValue + "]");
            // XXX This works not in common browsers...
            component.getChildren().add(style);
          }
        }
        Markup markup = item instanceof Visual ? ((Visual) item).getMarkup() : Markup.NULL;
        if (onlySelected == null && contains) {
          writer.writeAttribute(HtmlAttributes.SELECTED, true);
          markup = Markup.SELECTED.add(markup);
        }
        if (item.isDisabled()) {
          writer.writeAttribute(HtmlAttributes.DISABLED, true);
          markup = Markup.DISABLED.add(markup);
        }
        writer.writeClassAttribute(optionClass, optionClass.createMarkup(markup));

        writer.writeText(item.getLabel());
        writer.endElement(HtmlElements.OPTION);
      }
    }
  }

  public static void writeDataAttributes(
      final FacesContext context, final TobagoResponseWriter writer, final UIComponent component)
      throws IOException {

    final Map<Object, Object> dataAttributes = ComponentUtils.getDataAttributes(component);
    if (dataAttributes == null) {
      return;
    }

    final ELContext elContext = context.getELContext();

    for (final Map.Entry<Object, Object> entry : dataAttributes.entrySet()) {
      final Object mapKey = entry.getKey();
      final String name = mapKey instanceof ValueExpression
          ? ((ValueExpression) mapKey).getValue(elContext).toString() : mapKey.toString();
      final Object mapValue = entry.getValue();
      final String value = mapValue instanceof ValueExpression
          ? ((ValueExpression) mapValue).getValue(elContext).toString() : mapValue.toString();
      writer.writeAttribute(DataAttributes.dynamic(name), value, true);
    }
  }
}
