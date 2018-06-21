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

QUnit.test("Open 'Simple Popup' and press 'Cancel'.", function(assert) {
  assert.expect(3);
  var done = assert.async(2);
  var step = 1;

  var $popup = jQueryFrame("#page\\:mainForm\\:form1\\:popup input");
  var $openButton = jQueryFrame("#page\\:mainForm\\:form1\\:openPopup");
  var $cancelButton = jQueryFrame("#page\\:mainForm\\:form1\\:popup\\:cancel1");

  assert.equal($popup.attr("value"), "true");
  $openButton.click();

  jQuery("#page\\:testframe").on("load", function() {
    if (step == 1) {
      $popup = jQueryFrame("#page\\:mainForm\\:form1\\:popup input");
      assert.equal($popup.attr("value"), "false");

      $cancelButton = jQueryFrame("#page\\:mainForm\\:form1\\:popup\\:cancel1");
      $cancelButton.click();

      step++;
      done();
    } else if (step == 2) {
      $popup = jQueryFrame("#page\\:mainForm\\:form1\\:popup input");
      assert.equal($popup.attr("value"), "true");

      done();
    }
  });
});

QUnit.test("Open 'Simple Popup', press 'Submit' while field is empty. Press 'Cancel'.", function(assert) {
  assert.expect(5);
  var done = assert.async(3);
  var step = 1;

  var $popup = jQueryFrame("#page\\:mainForm\\:form1\\:popup input");
  var $openButton = jQueryFrame("#page\\:mainForm\\:form1\\:openPopup");
  var $output = jQueryFrame("#page\\:mainForm\\:form1\\:output span");
  var outputValue = $output.text();
  var $inputField = jQueryFrame("#page\\:mainForm\\:form1\\:popup\\:in1\\:\\:field");
  var $submitButton = jQueryFrame("#page\\:mainForm\\:form1\\:popup\\:submit1");
  var $cancelButton = jQueryFrame("#page\\:mainForm\\:form1\\:popup\\:cancel1");

  assert.equal($popup.attr("value"), "true");
  $openButton.click();

  jQuery("#page\\:testframe").on("load", function() {
    if (step == 1) {
      $popup = jQueryFrame("#page\\:mainForm\\:form1\\:popup input");
      $inputField = jQueryFrame("#page\\:mainForm\\:form1\\:popup\\:in1\\:\\:field");
      $submitButton = jQueryFrame("#page\\:mainForm\\:form1\\:popup\\:submit1");

      assert.equal($popup.attr("value"), "false");
      $inputField.val("");
      $submitButton.click();

      step++;
      done();
    } else if (step == 2) {
      $popup = jQueryFrame("#page\\:mainForm\\:form1\\:popup input");
      $cancelButton = jQueryFrame("#page\\:mainForm\\:form1\\:popup\\:cancel1");

      assert.equal($popup.attr("value"), "false");
      $cancelButton.click();

      step++;
      done();
    } else if (step == 3) {
      $popup = jQueryFrame("#page\\:mainForm\\:form1\\:popup input");
      $output = jQueryFrame("#page\\:mainForm\\:form1\\:output span");

      assert.equal($popup.attr("value"), "true");
      assert.equal($output.text(), outputValue);

      done();
    }
  });
});

QUnit.test("Open 'Simple Popup', press 'Submit' while field has content. Press 'Cancel'.", function(assert) {
  assert.expect(5);
  var done = assert.async(3);
  var step = 1;

  var $popup = jQueryFrame("#page\\:mainForm\\:form1\\:popup input");
  var $openButton = jQueryFrame("#page\\:mainForm\\:form1\\:openPopup");
  var $output = jQueryFrame("#page\\:mainForm\\:form1\\:output span");
  var $inputField = jQueryFrame("#page\\:mainForm\\:form1\\:popup\\:in1\\:\\:field");
  var $submitButton = jQueryFrame("#page\\:mainForm\\:form1\\:popup\\:submit1");
  var $cancelButton = jQueryFrame("#page\\:mainForm\\:form1\\:popup\\:cancel1");

  assert.equal($popup.attr("value"), "true");
  $openButton.click();

  jQuery("#page\\:testframe").on("load", function() {
    if (step == 1) {
      $popup = jQueryFrame("#page\\:mainForm\\:form1\\:popup input");
      $inputField = jQueryFrame("#page\\:mainForm\\:form1\\:popup\\:in1\\:\\:field");
      $submitButton = jQueryFrame("#page\\:mainForm\\:form1\\:popup\\:submit1");

      assert.equal($popup.attr("value"), "false");
      $inputField.val("Hello Popup!");
      $submitButton.click();

      step++;
      done();
    } else if (step == 2) {
      $popup = jQueryFrame("#page\\:mainForm\\:form1\\:popup input");
      $cancelButton = jQueryFrame("#page\\:mainForm\\:form1\\:popup\\:cancel1");

      assert.equal($popup.attr("value"), "false");
      $cancelButton.click();

      step++;
      done();
    } else if (step == 3) {
      $popup = jQueryFrame("#page\\:mainForm\\:form1\\:popup input");
      $output = jQueryFrame("#page\\:mainForm\\:form1\\:output span");

      assert.equal($popup.attr("value"), "true");
      assert.equal($output.text(), "Hello Popup!");

      done();
    }
  });
});

QUnit.test("Open 'Simple Popup', press 'Submit & Close' while field is empty. Press 'Cancel'.", function(assert) {
  assert.expect(5);
  var done = assert.async(3);
  var step = 1;

  var $popup = jQueryFrame("#page\\:mainForm\\:form1\\:popup input");
  var $openButton = jQueryFrame("#page\\:mainForm\\:form1\\:openPopup");
  var $output = jQueryFrame("#page\\:mainForm\\:form1\\:output span");
  var outputValue = $output.text();
  var $inputField = jQueryFrame("#page\\:mainForm\\:form1\\:popup\\:in1\\:\\:field");
  var $submitCloseButton = jQueryFrame("#page\\:mainForm\\:form1\\:popup\\:submitClose1");
  var $cancelButton = jQueryFrame("#page\\:mainForm\\:form1\\:popup\\:cancel1");

  assert.equal($popup.attr("value"), "true");
  $openButton.click();

  jQuery("#page\\:testframe").on("load", function() {
    if (step == 1) {
      $popup = jQueryFrame("#page\\:mainForm\\:form1\\:popup input");
      $inputField = jQueryFrame("#page\\:mainForm\\:form1\\:popup\\:in1\\:\\:field");
      $submitCloseButton = jQueryFrame("#page\\:mainForm\\:form1\\:popup\\:submitClose1");

      assert.equal($popup.attr("value"), "false");
      $inputField.val("");
      $submitCloseButton.click();

      step++;
      done();
    } else if (step == 2) {
      $popup = jQueryFrame("#page\\:mainForm\\:form1\\:popup input");
      $cancelButton = jQueryFrame("#page\\:mainForm\\:form1\\:popup\\:cancel1");

      assert.equal($popup.attr("value"), "false");
      $cancelButton.click();

      step++;
      done();
    } else if (step == 3) {
      $popup = jQueryFrame("#page\\:mainForm\\:form1\\:popup input");
      $output = jQueryFrame("#page\\:mainForm\\:form1\\:output span");

      assert.equal($popup.attr("value"), "true");
      assert.equal($output.text(), outputValue);

      done();
    }
  });
});

QUnit.test("Open 'Simple Popup', press 'Submit & Close' while field has content.", function(assert) {
  assert.expect(4);
  var done = assert.async(2);
  var step = 1;

  var $popup = jQueryFrame("#page\\:mainForm\\:form1\\:popup input");
  var $openButton = jQueryFrame("#page\\:mainForm\\:form1\\:openPopup");
  var $output = jQueryFrame("#page\\:mainForm\\:form1\\:output span");
  var $inputField = jQueryFrame("#page\\:mainForm\\:form1\\:popup\\:in1\\:\\:field");
  var $submitCloseButton = jQueryFrame("#page\\:mainForm\\:form1\\:popup\\:submitClose1");
  var $cancelButton = jQueryFrame("#page\\:mainForm\\:form1\\:popup\\:cancel1");

  assert.equal($popup.attr("value"), "true");
  $openButton.click();

  jQuery("#page\\:testframe").on("load", function() {
    if (step == 1) {
      $popup = jQueryFrame("#page\\:mainForm\\:form1\\:popup input");
      $inputField = jQueryFrame("#page\\:mainForm\\:form1\\:popup\\:in1\\:\\:field");
      $submitCloseButton = jQueryFrame("#page\\:mainForm\\:form1\\:popup\\:submitClose1");

      assert.equal($popup.attr("value"), "false");
      $inputField.val("press submit and close test");
      $submitCloseButton.click();

      step++;
      done();
    } else if (step == 2) {
      $popup = jQueryFrame("#page\\:mainForm\\:form1\\:popup input");
      $output = jQueryFrame("#page\\:mainForm\\:form1\\:output span");

      assert.equal($popup.attr("value"), "true");
      assert.equal($output.text(), "press submit and close test");

      done();
    }
  });
});

QUnit.test("Open 'Client Popup' and press 'Cancel'.", function(assert) {
  assert.expect(3);
  var step = 1;

  var $popup = jQueryFrame("#page\\:mainForm\\:form2\\:clientPopup input");
  var $openButton = jQueryFrame("#page\\:mainForm\\:form2\\:open");
  var $cancelButton = jQueryFrame("#page\\:mainForm\\:form2\\:clientPopup\\:cancel2");

  assert.equal($popup.attr("value"), "true");
  $openButton.click();
  assert.equal($popup.attr("value"), "false");
  $cancelButton.click();
  assert.equal($popup.attr("value"), "true");
});

QUnit.test("Open 'Client Popup', press 'Submit' while field is empty. Press 'Cancel'.", function(assert) {
  assert.expect(5);
  var done = assert.async(1);

  var $popup = jQueryFrame("#page\\:mainForm\\:form2\\:clientPopup input");
  var $openButton = jQueryFrame("#page\\:mainForm\\:form2\\:open");
  var $output = jQueryFrame("#page\\:mainForm\\:form2\\:output span");
  var outputValue = $output.text();
  var $messages = jQueryFrame("#page\\:mainForm\\:form2\\:clientPopup\\:messages");
  var $inputField = jQueryFrame("#page\\:mainForm\\:form2\\:clientPopup\\:in2\\:\\:field");
  var $submitButton = jQueryFrame("#page\\:mainForm\\:form2\\:clientPopup\\:submit2");
  var $cancelButton = jQueryFrame("#page\\:mainForm\\:form2\\:clientPopup\\:cancel2");

  assert.equal($popup.attr("value"), "true");
  $openButton.click();
  assert.equal($popup.attr("value"), "false");
  $inputField.val("");
  $submitButton.click();

  waitForAjax(function() {
    $messages = jQueryFrame("#page\\:mainForm\\:form2\\:clientPopup\\:messages");
    return $messages.length == 1;
  }, function() {
    $output = jQueryFrame("#page\\:mainForm\\:form2\\:output span");
    $messages = jQueryFrame("#page\\:mainForm\\:form2\\:clientPopup\\:messages");
    $cancelButton = jQueryFrame("#page\\:mainForm\\:form2\\:clientPopup\\:cancel2");

    assert.equal($messages.length, 1);
    $cancelButton.click();
    assert.equal($popup.attr("value"), "true");
    assert.equal($output.text(), outputValue);

    done();
  });
});

QUnit.test("Open 'Client Popup', press 'Submit' while field has content. Press 'Cancel'.", function(assert) {
  assert.expect(5);
  var done = assert.async(1);

  var $popup = jQueryFrame("#page\\:mainForm\\:form2\\:clientPopup input");
  var $openButton = jQueryFrame("#page\\:mainForm\\:form2\\:open");
  var $output = jQueryFrame("#page\\:mainForm\\:form2\\:out span");
  var $messages = jQueryFrame("#page\\:mainForm\\:form2\\:clientPopup\\:messages");
  var $inputField = jQueryFrame("#page\\:mainForm\\:form2\\:clientPopup\\:in2\\:\\:field");
  var $submitButton = jQueryFrame("#page\\:mainForm\\:form2\\:clientPopup\\:submit2");
  var $cancelButton = jQueryFrame("#page\\:mainForm\\:form2\\:clientPopup\\:cancel2");

  assert.equal($popup.attr("value"), "true");
  $openButton.click();
  assert.equal($popup.attr("value"), "false");
  $inputField.val("test client popup - submit button");
  $submitButton.click();

  waitForAjax(function() {
    $messages = jQueryFrame("#page\\:mainForm\\:form2\\:clientPopup\\:messages");
    return $messages.length == 0;
  }, function() {
    $output = jQueryFrame("#page\\:mainForm\\:form2\\:out span");
    $messages = jQueryFrame("#page\\:mainForm\\:form2\\:clientPopup\\:messages");
    $cancelButton = jQueryFrame("#page\\:mainForm\\:form2\\:clientPopup\\:cancel2");

    assert.equal($messages.length, 0);
    $cancelButton.click();
    assert.equal($popup.attr("value"), "true");
    assert.equal($output.text(), "test client popup - submit button");

    done();
  });
});

QUnit.test("Open 'Client Popup', press 'Submit & Close' while field is empty.", function(assert) {
  assert.expect(4);
  var done = assert.async(1);

  var $popup = jQueryFrame("#page\\:mainForm\\:form2\\:clientPopup input");
  var $openButton = jQueryFrame("#page\\:mainForm\\:form2\\:open");
  var $output = jQueryFrame("#page\\:mainForm\\:form2\\:output span");
  var outputValue = $output.text();
  var $inputField = jQueryFrame("#page\\:mainForm\\:form2\\:clientPopup\\:in2\\:\\:field");
  var $submitCloseButton = jQueryFrame("#page\\:mainForm\\:form2\\:clientPopup\\:submitClose2");

  assert.equal($popup.attr("value"), "true");
  $openButton.click();
  assert.equal($popup.attr("value"), "false");
  $inputField.val("");
  $submitCloseButton.click();

  jQuery("#page\\:testframe").on("load", function() {
    $popup = jQueryFrame("#page\\:mainForm\\:form2\\:clientPopup input");
    $output = jQueryFrame("#page\\:mainForm\\:form2\\:output span");

    assert.equal($popup.attr("value"), "true");
    assert.equal($output.text(), outputValue);

    done();
  });
});

QUnit.test("Open 'Simple Popup', press 'Submit & Close' while field has content.", function(assert) {
  assert.expect(4);
  var done = assert.async(1);

  var $popup = jQueryFrame("#page\\:mainForm\\:form2\\:clientPopup input");
  var $openButton = jQueryFrame("#page\\:mainForm\\:form2\\:open");
  var $output = jQueryFrame("#page\\:mainForm\\:form2\\:out span");
  var $inputField = jQueryFrame("#page\\:mainForm\\:form2\\:clientPopup\\:in2\\:\\:field");
  var $submitCloseButton = jQueryFrame("#page\\:mainForm\\:form2\\:clientPopup\\:submitClose2");

  assert.equal($popup.attr("value"), "true");
  $openButton.click();
  assert.equal($popup.attr("value"), "false");
  $inputField.val("test client popup - submit and close button");
  $submitCloseButton.click();

  jQuery("#page\\:testframe").on("load", function() {
    $popup = jQueryFrame("#page\\:mainForm\\:form2\\:clientPopup input");
    $output = jQueryFrame("#page\\:mainForm\\:form2\\:out span");

    assert.equal($popup.attr("value"), "true");
    assert.equal($output.text(), "test client popup - submit and close button");

    done();
  });
});
