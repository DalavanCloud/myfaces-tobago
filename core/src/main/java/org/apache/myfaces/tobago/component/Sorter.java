package org.apache.myfaces.tobago.component;

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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.tobago.TobagoConstants;
import static org.apache.myfaces.tobago.TobagoConstants.ATTR_SORTABLE;
import org.apache.myfaces.tobago.event.SortActionEvent;
import org.apache.myfaces.tobago.model.SheetState;
import org.apache.myfaces.tobago.util.BeanComparator;
import org.apache.myfaces.tobago.util.ValueBindingComparator;

import javax.faces.component.UIColumn;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UIOutput;
import javax.faces.component.UISelectBoolean;
import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.MethodBinding;
import javax.faces.el.MethodNotFoundException;
import javax.faces.el.ValueBinding;
import javax.faces.model.DataModel;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * User: weber
 * Date: Mar 7, 2005
 * Time: 4:01:27 PM
 */
public class  Sorter extends MethodBinding {

  private static final Log LOG = LogFactory.getLog(Sorter.class);

  private Comparator comparator;

  public Object invoke(FacesContext facesContext, Object[] aobj)
      throws EvaluationException {
    if (aobj[0] instanceof SortActionEvent) {
      SortActionEvent sortEvent = (SortActionEvent) aobj[0];
      if (LOG.isDebugEnabled()) {
        LOG.debug("sorterId = " + sortEvent.getComponent().getId());
      }
      UIColumn column = sortEvent.getColumn();
      UIData data = sortEvent.getSheet();

      Object value = data.getValue();
      if (value instanceof DataModel) {
        value = ((DataModel) value).getWrappedData();
      }
      SheetState sheetState = data.getSheetState(facesContext);

      Comparator actualComparator = null;

      if (value instanceof List || value instanceof Object[]) {
        String sortProperty;

        try {

          UIComponent child = getFirstSortableChild(column.getChildren());
          if (child != null) {
            ValueBinding valueBinding = child.getValueBinding("value");
            String var = data.getVar();

            if (valueBinding != null) {
              if (isSimpleProperty(valueBinding.getExpressionString())) {
                String expressionString = valueBinding.getExpressionString();
                if (expressionString.startsWith("#{")
                    && expressionString.endsWith("}")) {
                  expressionString =
                      expressionString.substring(2,
                          expressionString.length() - 1);
                }
                sortProperty = expressionString.substring(var.length() + 1);

                actualComparator = new BeanComparator(
                    sortProperty, comparator, !sheetState.isAscending());

                if (LOG.isDebugEnabled()) {
                  LOG.debug("Sort property is " + sortProperty);
                }
              } else {
                actualComparator = new ValueBindingComparator(facesContext, var,
                    valueBinding, !sheetState.isAscending(), comparator);
              }
            }

          } else {
            LOG.error("No sortable component found!");
            removeSortableAttribute(column);
            return null;
          }
        } catch (Exception e) {
          LOG.error("Error while extracting sortMethod :" + e.getMessage(), e);
          if (column != null) {
            removeSortableAttribute(column);
          }
          return null;
        }

          // TODO: locale / comparator parameter?
          // don't compare numbers with Collator.getInstance() comparator
//        Comparator comparator = Collator.getInstance();
//          comparator = new RowComparator(ascending, method);

          if (value instanceof List) {
            Collections.sort((List) value, actualComparator);
          } else { // value is instanceof Object[]
            Arrays.sort((Object[]) value, actualComparator);
          }

      } else {  // DataModel?, ResultSet, Result or Object
        LOG.warn("Sorting not supported for type "
                   + (value != null ? value.getClass().toString() : "null"));
      }
    }
    return null;
  }

  private boolean isSimpleProperty(String expressionString) {
    return expressionString.matches("^#\\{(\\w+(\\.\\w)*)\\}$");
  }

  private void removeSortableAttribute(UIColumn uiColumn) {
    LOG.warn("removing attribute sortable from column " + uiColumn.getId());
    uiColumn.getAttributes().remove(ATTR_SORTABLE);
  }

  private UIComponent getFirstSortableChild(List children) {
    UIComponent child = null;

    for (Iterator iter = children.iterator(); iter.hasNext();) {
      child = (UIComponent) iter.next();
      if (child instanceof UICommand
        || child instanceof javax.faces.component.UIPanel) {
        child = getFirstSortableChild(child.getChildren());
      }
      if (child instanceof UISelectMany
        || child instanceof UISelectOne
        || child instanceof UISelectBoolean) {
        continue;
      } else if (child instanceof UIInput
          && TobagoConstants.RENDERER_TYPE_HIDDEN.equals(child.getRendererType())) {
        continue;
      } else if (child instanceof UIOutput) {
        break;
      }
    }
    return child;
  }

  public Class getType(FacesContext facescontext)
      throws MethodNotFoundException {
    return String.class;
  }

  public Comparator getComparator() {
    return comparator;
  }

  public void setComparator(Comparator comparator) {
    this.comparator = comparator;
  }
}

