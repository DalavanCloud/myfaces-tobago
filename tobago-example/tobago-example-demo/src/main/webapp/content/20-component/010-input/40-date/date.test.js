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

function getToday($dateField) {
  var tobagoToday = $dateField.data("tobago-today");
  var todayArray = tobagoToday.split("-");
  return todayArray[2] + "." + todayArray[1] + "." + todayArray[0];
}

QUnit.test("date with label", function (assert) {
  assert.expect(5);

  var $label = jQueryFrame("#page\\:mainForm\\:dNormal > label");
  var $dateField = jQueryFrame("#page\\:mainForm\\:dNormal\\:\\:field");
  var $dateButton = jQueryFrame("#page\\:mainForm\\:dNormal button");
  var today = getToday($dateField);

  assert.equal($label.text(), "Date");
  assert.equal($dateField.val(), today);

  $dateField.val("32.05.2016");
  $dateButton.click();

  assert.equal($dateField.val(), today);

  var $dayToday = jQueryFrame(".day.today");
  assert.equal($dayToday.hasClass("past"), false);
  assert.equal($dayToday.prevAll(".past").length, $dayToday.prevAll().length);

  $dateButton.click(); // IE11: close datetimepicker for next test
});

QUnit.test("submit", function(assert) {
  assert.expect(6);
  var done = assert.async();

  var $dateField = jQueryFrame("#page\\:mainForm\\:formSubmit\\:input\\:\\:field");
  var $dateButton = jQueryFrame("#page\\:mainForm\\:formSubmit\\:input button");
  var $outField = jQueryFrame("#page\\:mainForm\\:formSubmit\\:output span");
  var $submitButton = jQueryFrame("#page\\:mainForm\\:formSubmit\\:button");
  var $days = jQueryFrame(".bootstrap-datetimepicker-widget .day");

  assert.equal($dateField.val(), "22.05.2016");
  assert.equal($outField.text(), "22.05.2016");

  $dateButton.click();
  assert.ok(jQueryFrame(".bootstrap-datetimepicker-widget").get(0),
      ".bootstrap-datetimepicker-widget should be available");

  $days = jQueryFrame($days.selector);
  var day22 = 0;
  for (i = 0; i < $days.length; i++) {
    $days = jQueryFrame($days.selector);
    if ($days.eq(i).text() === "22") {
      day22 = i;
      break;
    }
  }
  assert.ok(jQueryFrame(".bootstrap-datetimepicker-widget .day").get(i + 10));
  jQueryFrame(".bootstrap-datetimepicker-widget .day").get(i + 10).click(); // Choose '01.06.2016'.

  assert.equal($dateField.val(), "01.06.2016");
  $submitButton.click();

  jQuery("#page\\:testframe").load(function () {
    $outField = jQueryFrame("#page\\:mainForm\\:formSubmit\\:output span");
    assert.equal($outField.text(), "01.06.2016");
    done();
  });
});

QUnit.test("ajax", function (assert) {
  assert.expect(5);
  var done = assert.async();

  var $dateField = jQueryFrame("#page\\:mainForm\\:ajaxinput\\:\\:field");
  var $dateButton = jQueryFrame("#page\\:mainForm\\:ajaxinput button");
  var $outField = jQueryFrame("#page\\:mainForm\\:outputfield span");
  var today = getToday($dateField);

  assert.equal($dateField.val(), "");
  assert.equal($outField.text(), "");

  $dateButton.click();

  assert.ok(jQueryFrame(".bootstrap-datetimepicker-widget").get(0));
  assert.equal($dateField.val(), today);

  waitForAjax(function () {
    $outField = jQueryFrame($outField.selector);
    return $outField.text() === today;
  }, function () {
    $outField = jQueryFrame($outField.selector);
    assert.equal($outField.text(), today);
    done();
  });
});
