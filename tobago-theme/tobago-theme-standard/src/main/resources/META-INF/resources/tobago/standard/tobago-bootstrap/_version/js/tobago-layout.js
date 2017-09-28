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

Tobago.Layout = {};

Tobago.Layout.Orientation = {
  HORIZONTAL: true,
  VERTICAL: false
};

function init(table) {

  var elements = table.children("tbody").children("tr").children("td").children();
  elements.each(function () {
    var element = jQuery(this);
    var data = element.data("tobago-layout");
    if (data !== undefined) {
      var width = data.width;
      if (width == null && element.prop("tagName") == "img") {
        width = element.outerWidth();
      }
      if (width != null) {
        element.css("width", width + "px");
      }

      var height = data.height;
      if (height == null && element.prop("tagName") == "img") {
        height = element.outerHeight();
      }
      if (height != null) {
        element.css("height", height + "px");
      }
    }
  });
}

function layoutGrid(table, orientation) {
  var cells;
  var banks;
  var tokens;
  var css;
  var desired;

  var tobagoLayout = table.data("tobago-layout");
  if (! tobagoLayout) {
    return;
  }

  if (orientation == Tobago.Layout.Orientation.HORIZONTAL) {
//    cells = table.find("tr:first>td");
    banks = table.children("colgroup").children("col");
    tokens = tobagoLayout.columns;
    css = "width";
    desired = table.outerWidth();
//    desired = table.parent().data("tobago-style").width.replace("px", ""); // todo: data("tobago-layout") wohl doch nicht so gut...? der wert wurde ja schon berechnet...
  } else { // Tobago.Layout.Orientation.VERTICAL
//    cells = table.find("tr");
    banks = table.children("tbody").children("tr");
    tokens = tobagoLayout.rows;
    css = "height";
    desired = table.outerHeight();
//    desired = table.parent().data("tobago-style").height.replace("px", ""); // todo: data("tobago-layout")
  }

  if (tokens) {
    table.css(css, "0px");
    var i;
    var cell;
    var sumRelative = 0;
    var sumUsed = 0;
    for (i = 0; i < tokens.length; i++) {
//      cell = cells.eq(i);
      cell = banks.eq(i);
      switch (typeof tokens[i]) {
        case "number":
          // a relative value
          sumRelative += tokens[i];
          break;
        case "string":
          // a string, currently only "auto" is supported
          if ("auto" == tokens[i]) {
            // nothing to do
            sumUsed += orientation == Tobago.Layout.Orientation.HORIZONTAL ? cell.outerWidth() : cell.outerHeight();
          } else {
            console.warn("currently only 'auto' is supported, but found: '" + tokens[i] + "'");  // @DEV_ONLY
          }
          break;
        case "object":
          var m = tokens[i].measure;
          if (m) {
            setLength(table, banks, i, css, m);
            if (/^\d+px$/.test(m)) {
              sumUsed += m.substr(0, m.length - 2);
            }
          } else {
            console.warn("can't find  measure in object: '" + tokens[i] + "'");  // @DEV_ONLY
          }
          break;
        default:
          console.warn("unsupported type of: '" + tokens[i] + "'");  // @DEV_ONLY
          break;
      }
    }

    table.css(css, "");
    var rest = desired - sumUsed;
    if (rest > 0 && sumRelative > 0) {
      for (i = 0; i < tokens.length; i++) {
        if (typeof tokens[i] == "number") {
          setLength(table, banks, i, css, rest * tokens[i] / sumRelative + "px");
        }
      }
    }
  }
}

function setLength(table, banks, i, css, length) {
  banks.eq(i).css(css, length);
  /*
   if (css == "width") {
   var trs = table.children("tbody").children("tr");
   trs.each(function () {
   jQuery(this).children("td").eq(i).children().css(css, length);
   });
   } else {
   var tr = table.children("tbody").children("tr").eq(i);
   tr.children("td").children().css(css, length);
   }
   */
}

Tobago.Layout.init = function (elements) {

  var gridLayouts = Tobago.Utils.selectWithJQuery(elements, ".tobago-gridLayout");

  gridLayouts.each(function () {
    var table = jQuery(this);
    init(table);
  });

  gridLayouts.each(function () {
    var table = jQuery(this);
    layoutGrid(table, Tobago.Layout.Orientation.HORIZONTAL);
    layoutGrid(table, Tobago.Layout.Orientation.VERTICAL);
  });

  //////////////////////////////////////////////

  // fixing fixed header/footer: content should not scroll behind the footer
  // XXX Is there a CSS solution?
  // TODO: this might be reevaluated after a "resize"

  var header = Tobago.Utils.selectWithJQuery(elements, ".fixed-top");
  header.each(function () {
    var content = header.next();
    content.css({
      marginTop: (parseInt(content.css("margin-top").replace("px", "")) + header.outerHeight(true)) + "px"
    });
  });

  var footer = Tobago.Utils.selectWithJQuery(elements, ".fixed-bottom");
  footer.each(function () {
    var content = footer.prev();
    content.css({
      marginBottom: (parseInt(content.css("margin-bottom").replace("px", "")) + footer.outerHeight(true)) + "px"
    });
  });

};

Tobago.registerListener(Tobago.Layout.init, Tobago.Phase.DOCUMENT_READY);
Tobago.registerListener(Tobago.Layout.init, Tobago.Phase.AFTER_UPDATE);
