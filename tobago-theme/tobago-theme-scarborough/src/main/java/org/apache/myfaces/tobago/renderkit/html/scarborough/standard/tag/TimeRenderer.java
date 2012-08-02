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

package org.apache.myfaces.tobago.renderkit.html.scarborough.standard.tag;

/*
 * Created 07.02.2003 16:00:00.
 * $Id$
 */

import org.apache.myfaces.tobago.component.Attributes;
import org.apache.myfaces.tobago.component.UITime;
import org.apache.myfaces.tobago.context.Markup;
import org.apache.myfaces.tobago.context.ResourceManagerUtils;
import org.apache.myfaces.tobago.internal.util.DateFormatUtils;
import org.apache.myfaces.tobago.renderkit.InputRendererBase;
import org.apache.myfaces.tobago.renderkit.css.Classes;
import org.apache.myfaces.tobago.renderkit.css.Style;
import org.apache.myfaces.tobago.renderkit.html.HtmlAttributes;
import org.apache.myfaces.tobago.renderkit.html.HtmlElements;
import org.apache.myfaces.tobago.renderkit.html.HtmlInputTypes;
import org.apache.myfaces.tobago.renderkit.html.util.HtmlRendererUtils;
import org.apache.myfaces.tobago.util.ComponentUtils;
import org.apache.myfaces.tobago.webapp.TobagoResponseWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.DateTimeConverter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeRenderer extends InputRendererBase {

  private static final Logger LOG = LoggerFactory.getLogger(TimeRenderer.class);


  public void encodeEnd(FacesContext facesContext, UIComponent component) throws IOException {
    if (!(component instanceof UITime)) {
      LOG.error("Wrong type: Need " + UITime.class.getName() + ", but was " + component.getClass().getName());
      return;
    }

    UITime time = (UITime) component;

    String title = HtmlRendererUtils.getTitleFromTipAndMessages(facesContext, time);
    String currentValue = getCurrentValue(facesContext, time);
    if (LOG.isDebugEnabled()) {
      LOG.debug("currentValue = '" + currentValue + "'");
    }

    String converterPattern = "HH:mm";
    if (time.getConverter() != null) {
      Converter converter = time.getConverter();
      if (converter instanceof DateTimeConverter) {
        String pattern = DateFormatUtils.findPattern((DateTimeConverter) converter);
        if (pattern != null && pattern.indexOf('s') > -1) {
          converterPattern += ":ss";
        }
      }
    }

    boolean hasSeconds = converterPattern.indexOf('s') > -1;

    Object value = time.getValue();
    Date date;
    if (value instanceof Date) {
      date = (Date) value;
    } else if (value instanceof Calendar) {
      date = ((Calendar) value).getTime();
    } else {
      date = new Date();
    }

    String hour = new SimpleDateFormat("HH").format(date);
    String minute = new SimpleDateFormat("mm").format(date);
    String second = new SimpleDateFormat("ss").format(date);

    String id = time.getClientId(facesContext);
    final String idPrefix = id + ComponentUtils.SUB_SEPARATOR;
    TobagoResponseWriter writer = HtmlRendererUtils.getTobagoResponseWriter(facesContext);
    writer.startElement(HtmlElements.DIV, time);
    writer.writeClassAttribute(Classes.create(time));
    HtmlRendererUtils.writeDataAttributes(facesContext, writer, time);
    Style style = new Style(facesContext, time);
    writer.writeStyleAttribute(style);

    writer.startElement(HtmlElements.DIV, time);
    writer.writeAttribute(HtmlAttributes.ID, idPrefix + "borderDiv", false);
    Markup markup = time.getCurrentMarkup();
    if (hasSeconds) {
      markup = markup.add(Markup.SECONDS);
    }
    writer.writeClassAttribute(Classes.create(time, "borderDiv", markup));

    writeInput(writer, time, idPrefix + "hour", hour, true, title);
    writeInputSeparator(writer, time, ":");
    writeInput(writer, time, idPrefix + "minute", minute, false, title);
    if (hasSeconds) {
      writeInputSeparator(writer, time, ":");
      writeInput(writer, time, idPrefix + "second", second, false, title);
    }

    writer.endElement(HtmlElements.DIV);

    String imageId = idPrefix + "inc";
    String imageSrc = "image/timeIncrement.gif";
    writer.startElement(HtmlElements.IMG, null);
    writer.writeIdAttribute(imageId);
    writer.writeClassAttribute(Classes.create(time, "incImage", markup));
    writer.writeAttribute(HtmlAttributes.SRC, ResourceManagerUtils.getImageWithPath(facesContext, imageSrc), true);
    writer.writeAttribute(HtmlAttributes.ALT, "", false); // TODO: tip

    if (!(ComponentUtils.getBooleanAttribute(time, Attributes.DISABLED)
        || ComponentUtils.getBooleanAttribute(time, Attributes.READONLY))) {
      writer.writeAttribute(HtmlAttributes.ONCLICK, "tbgIncTime(this)", false);
    }
    writer.endElement(HtmlElements.IMG);

    imageId = idPrefix + "dec";
    imageSrc = "image/timeDecrement.gif";
    writer.startElement(HtmlElements.IMG, null);
    writer.writeIdAttribute(imageId);
    writer.writeClassAttribute(Classes.create(time, "decImage", markup));
    writer.writeAttribute(HtmlAttributes.SRC, ResourceManagerUtils.getImageWithPath(facesContext, imageSrc), true);
    writer.writeAttribute(HtmlAttributes.ALT, "", false); // TODO: tip
    if (!(ComponentUtils.getBooleanAttribute(time, Attributes.DISABLED)
        || ComponentUtils.getBooleanAttribute(time, Attributes.READONLY))) {
      writer.writeAttribute(HtmlAttributes.ONCLICK, "tbgDecTime(this)", false);
    }
    writer.endElement(HtmlElements.IMG);

    writer.startElement(HtmlElements.INPUT, time);
    writer.writeAttribute(HtmlAttributes.TYPE, HtmlInputTypes.HIDDEN, false);
    writer.writeIdAttribute(id + ":converterPattern");
    writer.writeAttribute(HtmlAttributes.VALUE, converterPattern, true);
    writer.endElement(HtmlElements.INPUT);

    writer.startElement(HtmlElements.INPUT, time);
    writer.writeAttribute(HtmlAttributes.TYPE, HtmlInputTypes.HIDDEN, false);
    writer.writeIdAttribute(id);
    writer.writeNameAttribute(id);
    writer.writeAttribute(HtmlAttributes.VALUE, hour + ":" + minute + ":" + second, false);
    writer.endElement(HtmlElements.INPUT);

    String dateTextBoxId = (String) time.getAttributes().get(Attributes.DATE_INPUT_ID);

    if (dateTextBoxId != null) {
      String[] cmds = {"tbgInitTimeParse('" + id + "', '" + dateTextBoxId + "');"};
      HtmlRendererUtils.writeScriptLoader(facesContext, null, cmds);
    }

    writer.endElement(HtmlElements.DIV);
  }

  private void writeInputSeparator(TobagoResponseWriter writer, UITime time, String sep) throws IOException {
    writer.startElement(HtmlElements.SPAN, null);
    writer.writeClassAttribute(Classes.create(time, "sep"));
    writer.writeText(sep);
    writer.endElement(HtmlElements.SPAN);
  }

  private void writeInput(TobagoResponseWriter writer, UITime input, String id, String hour, boolean hourMode,
      String title) throws IOException {
    Integer tabIndex = input.getTabIndex();
    writer.startElement(HtmlElements.INPUT, null);
    writer.writeAttribute(HtmlAttributes.TYPE, HtmlInputTypes.TEXT, false);
    writer.writeIdAttribute(id);
    if (tabIndex != null) {
      writer.writeAttribute(HtmlAttributes.TABINDEX, tabIndex);
    }
    writer.writeAttribute(HtmlAttributes.TITLE, title, true);

    writer.writeClassAttribute(Classes.create(input, "input"));
    writer.writeAttribute(HtmlAttributes.READONLY, ComponentUtils.getBooleanAttribute(input, Attributes.READONLY));
    writer.writeAttribute(HtmlAttributes.DISABLED, ComponentUtils.getBooleanAttribute(input, Attributes.DISABLED));
    if (!(ComponentUtils.getBooleanAttribute(input, Attributes.DISABLED)
        || ComponentUtils.getBooleanAttribute(input, Attributes.READONLY))) {
      writer.writeAttribute(HtmlAttributes.ONFOCUS, "tbgTimerInputFocus(this, " + hourMode + ")", false);
      writer.writeAttribute(HtmlAttributes.ONBLUR, "tbgTimerInputBlur(this)", false);
      writer.writeAttribute(HtmlAttributes.ONKEYUP, "tbgTimerKeyUp(this, event)", false);
    }
    writer.writeAttribute(HtmlAttributes.VALUE, hour, true);
    writer.endElement(HtmlElements.INPUT);
  }
}
