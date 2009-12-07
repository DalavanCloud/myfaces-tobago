package org.apache.myfaces.tobago.renderkit.html.util;

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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.tobago.component.Attributes;
import org.apache.myfaces.tobago.component.Facets;
import org.apache.myfaces.tobago.component.UICommandBase;
import org.apache.myfaces.tobago.component.UIPopup;
import org.apache.myfaces.tobago.context.ClientProperties;
import org.apache.myfaces.tobago.context.ResourceManagerUtil;
import org.apache.myfaces.tobago.event.PopupFacetActionListener;
import org.apache.myfaces.tobago.util.ComponentUtils;

import javax.faces.application.Application;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIComponent;
import javax.faces.component.UIParameter;
import javax.faces.component.ValueHolder;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;

/*
 * Date: 19.03.2007 17:54:59
 */
public class CommandRendererHelper {

  private static final Log LOG = LogFactory.getLog(CommandRendererHelper.class);

  private String onclick;
  private boolean disabled;
  private String href;
  private String target;

  public CommandRendererHelper(FacesContext facesContext, UICommandBase component) {
    initOnclick(facesContext, component, null);
  }

  public CommandRendererHelper(FacesContext facesContext, UICommandBase component, Tag tag) {
    initOnclick(facesContext, component, tag);
  }

  private void initOnclick(FacesContext facesContext, UICommandBase command, Tag tag) {

    disabled = ComponentUtils.getBooleanAttribute(command, Attributes.DISABLED);
    href = getEmptyHref(facesContext);

    if (disabled) {
      onclick = "";
      href = "";
    } else {

      UIPopup popup = (UIPopup) command.getFacet(Facets.POPUP);
      if (popup != null) {
        if (!ComponentUtils.containsPopupActionListener(command)) {
          command.addActionListener(new PopupFacetActionListener());
        }
      }

      String clientId = command.getClientId(facesContext);
      boolean defaultCommand = ComponentUtils.getBooleanAttribute(command, Attributes.DEFAULT_COMMAND);
      boolean transition = ComponentUtils.getBooleanAttribute(command, Attributes.TRANSITION);

      if (command.getLink() != null || command.getResource() != null) {
        String url = generateUrl(facesContext, command);
        if (tag == Tag.ANCHOR) {
          onclick = null;
          href = url;
          target = command.getTarget();
        } else {
          // TODO target
          onclick = "Tobago.navigateToUrl('" + url + "');";
        }
      } else if (command.getAttributes().get(Attributes.ONCLICK) != null) {
        onclick = prepareOnClick(facesContext, command);
      } else if (command instanceof UICommandBase
          && ((UICommandBase) command).getRenderedPartially().length > 0) {

        String[] componentIds = ((UICommandBase) command).getRenderedPartially();

          // TODO find a better way
          boolean popupAction = ComponentUtils.containsPopupActionListener(command);
          if (popupAction) {
            if (componentIds.length != 1) {
              LOG.warn("more than one parially rendered component is not supported for popup! using first one: "
              + Arrays.toString(componentIds));
            }
            onclick = "Tobago.openPopupWithAction(this, '"
                + HtmlRendererUtils.getComponentId(facesContext, command, componentIds[0]) + "', '" + clientId + "')";
          } else {
            onclick = "Tobago.reloadComponent(this, '"
                + HtmlRendererUtils.getComponentIds(facesContext, command, componentIds) + "','" + clientId + "', {});";
          }

      } else if (defaultCommand) {
        ComponentUtils.findPage(facesContext, command).setDefaultActionId(clientId);
        onclick = null;
      } else {
        String target = ComponentUtils.getStringAttribute(command, Attributes.TARGET);
        if (target == null) {
          onclick = "Tobago.submitAction(this, '" + clientId + "', " + transition + ");";
        } else {
          onclick = "Tobago.submitAction(this, '" + clientId + "', " + transition + ", '" + target + "');";
        }
      }

      if (command.getAttributes().get(Attributes.POPUP_CLOSE) != null
          && ComponentUtils.isInPopup(command)) {
        String value = (String) command.getAttributes().get(Attributes.POPUP_CLOSE);
        if (value.equals("immediate")) {
          onclick = "Tobago.closePopup(this);";
        } else if (value.equals("afterSubmit")
            && command instanceof org.apache.myfaces.tobago.component.UICommand
            && ((org.apache.myfaces.tobago.component.UICommand) command).getRenderedPartially().length > 0) {
          onclick += "Tobago.closePopup(this);";
        }

      }
      onclick = appendConfirmationScript(onclick, command);
    }
  }

  private String getEmptyHref(FacesContext facesContext) {
    ClientProperties clientProperties = ClientProperties.getInstance(facesContext);
    return clientProperties.getUserAgent().isMsie() ? "#" : "javascript:;";
  }

  private String prepareOnClick(FacesContext facesContext, UIComponent component) {
    String onclick;
    onclick = (String) component.getAttributes().get(Attributes.ONCLICK);
    if (onclick.contains("@autoId")) {
      onclick = onclick.replace("@autoId", component.getClientId(facesContext));
    }
    return onclick;
  }

  private String appendConfirmationScript(String onclick, UIComponent component) {
    ValueHolder confirmation = (ValueHolder) component.getFacet(Facets.CONFIRMATION);
    if (confirmation != null) {
      StringBuilder script = new StringBuilder("return confirm('");
      script.append(confirmation.getValue());
      script.append("')");
      if (onclick != null) {
        script.append(" && ");
        script.append(onclick);
      }
      onclick = script.toString();
    }
    return onclick;
  }

  private String generateUrl(FacesContext facesContext, UICommandBase component) {
    String url;
    Application application = facesContext.getApplication();
    ViewHandler viewHandler = application.getViewHandler();
    ExternalContext externalContext = facesContext.getExternalContext();

    if (component.getResource() != null) {
      boolean jsfResource = component.getJsfResource();
      url = ResourceManagerUtil.getPageWithoutContextPath(facesContext, component.getResource());
      if (url != null) {
        if (jsfResource) {
          url = viewHandler.getActionURL(facesContext, url);
          url = externalContext.encodeActionURL(url);
        } else {
          url = viewHandler.getResourceURL(facesContext, url);
          url = externalContext.encodeResourceURL(url);
        }
      } else {
        url = "";
      }
    } else if (component.getLink() != null) {

      String link = component.getLink();
      if (link.startsWith("/")) { // internal absolute link
        url = viewHandler.getActionURL(facesContext, link);
        url = externalContext.encodeActionURL(url);
      } else if (link.contains(":")) { // external link
        url = link;
      } else { // internal relative link
        url = externalContext.encodeResourceURL(link);
      }

      StringBuilder builder = new StringBuilder(url);
      boolean firstParameter = !url.contains("?");
      for (UIComponent child : (List<UIComponent>) component.getChildren()) {
        if (child instanceof UIParameter) {
          UIParameter parameter = (UIParameter) child;
          if (firstParameter) {
            builder.append("?");
            firstParameter = false;
          } else {
            builder.append("&");
          }
          builder.append(parameter.getName());
          builder.append("=");
          Object value = parameter.getValue();
          // TODO encoding
          builder.append(value != null ? URLDecoder.decode(value.toString()) : null);
        }
      }
      url = builder.toString();
    } else {
      throw new AssertionError("Needed " + Attributes.LINK + " or " + Attributes.RESOURCE);
    }

    return url;
  }

  public String getOnclick() {
    return onclick;
  }

  public String getOnclickDoubleQuoted() {
    return onclick.replaceAll("'", "\"");
  }

  public boolean isDisabled() {
    return disabled;
  }

  public String getHref() {
    return href;
  }

  public String getTarget() {
    return target;
  }

  public static enum Tag {
    ANCHOR, BUTTON
  }
}
