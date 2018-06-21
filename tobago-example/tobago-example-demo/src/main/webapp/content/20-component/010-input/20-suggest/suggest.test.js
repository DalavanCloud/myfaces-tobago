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

QUnit.test("Basics: 'C'", function(assert) {
  var inputString = "C";
  var expectedLength = 10;

  assert.expect(expectedLength + 1);
  var done = assert.async();

  var $in = jQueryFrame("#page\\:mainForm\\:inBasic\\:\\:field");
  var $suggestions = getSuggestionsInBasic();

  $in.val(inputString).trigger('input');

  waitForAjax(function() {
    $suggestions = getSuggestionsInBasic();
    return $suggestions.length === expectedLength;
  }, function() {
    $suggestions = getSuggestionsInBasic();

    assert.equal($suggestions.length, expectedLength);
    for (var i = 0; i < expectedLength; i++) {
      assert.ok($suggestions.eq(i).find("strong").text().toUpperCase().indexOf(inputString.toUpperCase()) >= 0);
    }

    done();
  });
});


QUnit.test("Basics: 'Ch'", function(assert) {
  var inputString = "Ch";
  var expectedLength = 10;

  assert.expect(expectedLength + 1);
  var done = assert.async();

  var $in = jQueryFrame("#page\\:mainForm\\:inBasic\\:\\:field");
  var $suggestions = getSuggestionsInBasic();

  $in.val(inputString).trigger('input');

  waitForAjax(function() {
    $suggestions = getSuggestionsInBasic();
    return $suggestions.length === expectedLength;
  }, function() {
    $suggestions = getSuggestionsInBasic();

    assert.equal($suggestions.length, expectedLength);
    for (var i = 0; i < expectedLength; i++) {
      assert.ok($suggestions.eq(i).find("strong").text().toUpperCase().indexOf(inputString.toUpperCase()) >= 0);
    }

    done();
  });
});

QUnit.test("Basics: 'Chi'", function(assert) {
  var inputString = "Chi";
  var expectedLength = 10;

  assert.expect(expectedLength + 1);
  var done = assert.async();

  var $in = jQueryFrame("#page\\:mainForm\\:inBasic\\:\\:field");
  var $suggestions = getSuggestionsInBasic();

  $in.val(inputString).trigger('input');

  waitForAjax(function() {
    $suggestions = getSuggestionsInBasic();
    return $suggestions.length === expectedLength;
  }, function() {
    $suggestions = getSuggestionsInBasic();

    assert.equal($suggestions.length, expectedLength);
    for (var i = 0; i < expectedLength; i++) {
      assert.ok($suggestions.eq(i).find("strong").text().toUpperCase().indexOf(inputString.toUpperCase()) >= 0);
    }

    done();
  });
});

QUnit.test("Basics: 'Chil'", function(assert) {
  var inputString = "Chil";
  var expectedLength = 10;

  assert.expect(expectedLength + 1);
  var done = assert.async();

  var $in = jQueryFrame("#page\\:mainForm\\:inBasic\\:\\:field");
  var $suggestions = getSuggestionsInBasic();

  $in.val(inputString).trigger('input');

  waitForAjax(function() {
    $suggestions = getSuggestionsInBasic();
    return $suggestions.length === expectedLength;
  }, function() {
    $suggestions = getSuggestionsInBasic();

    assert.equal($suggestions.length, expectedLength);
    for (var i = 0; i < expectedLength; i++) {
      assert.ok($suggestions.eq(i).find("strong").text().toUpperCase().indexOf(inputString.toUpperCase()) >= 0);
    }

    done();
  });
});

QUnit.test("Basics: 'Chile'", function(assert) {
  var inputString = "Chile";
  var expectedLength = 7;

  assert.expect(expectedLength + 1);
  var done = assert.async();

  var $in = jQueryFrame("#page\\:mainForm\\:inBasic\\:\\:field");
  var $suggestions = getSuggestionsInBasic();

  $in.val(inputString).trigger('input');

  waitForAjax(function() {
    $suggestions = getSuggestionsInBasic();
    return $suggestions.length === expectedLength;
  }, function() {
    $suggestions = getSuggestionsInBasic();

    assert.equal($suggestions.length, expectedLength);
    for (var i = 0; i < expectedLength; i++) {
      assert.ok($suggestions.eq(i).find("strong").text().toUpperCase().indexOf(inputString.toUpperCase()) >= 0);
    }

    done();
  });
});

QUnit.test("Basics: 'Chile '", function(assert) {
  var inputString = "Chile ";
  var expectedLength = 7;

  assert.expect(expectedLength + 1);
  var done = assert.async();

  var $in = jQueryFrame("#page\\:mainForm\\:inBasic\\:\\:field");
  var $suggestions = getSuggestionsInBasic();

  $in.val(inputString).trigger('input');

  waitForAjax(function() {
    $suggestions = getSuggestionsInBasic();
    return $suggestions.length === expectedLength;
  }, function() {
    $suggestions = getSuggestionsInBasic();

    assert.equal($suggestions.length, expectedLength);
    for (var i = 0; i < expectedLength; i++) {
      assert.ok($suggestions.eq(i).find("strong").text().toUpperCase().indexOf(inputString.toUpperCase()) >= 0);
    }

    done();
  });
});

QUnit.test("Basics: 'Chile ('", function(assert) {
  var inputString = "Chile (";
  var expectedLength = 7;

  assert.expect(expectedLength + 1);
  var done = assert.async();

  var $in = jQueryFrame("#page\\:mainForm\\:inBasic\\:\\:field");
  var $suggestions = getSuggestionsInBasic();

  $in.val(inputString).trigger('input');

  waitForAjax(function() {
    $suggestions = getSuggestionsInBasic();
    return $suggestions.length === expectedLength;
  }, function() {
    $suggestions = getSuggestionsInBasic();

    assert.equal($suggestions.length, expectedLength);
    for (var i = 0; i < expectedLength; i++) {
      assert.ok($suggestions.eq(i).find("strong").text().toUpperCase().indexOf(inputString.toUpperCase()) >= 0);
    }

    done();
  });
});

QUnit.test("Basics: 'Chile (e'", function(assert) {
  var inputString = "Chile (e";
  var expectedLength = 3;

  assert.expect(expectedLength + 1);
  var done = assert.async();

  var $in = jQueryFrame("#page\\:mainForm\\:inBasic\\:\\:field");
  var $suggestions = getSuggestionsInBasic();

  $in.val(inputString).trigger('input');

  waitForAjax(function() {
    $suggestions = getSuggestionsInBasic();
    return $suggestions.length === expectedLength;
  }, function() {
    $suggestions = getSuggestionsInBasic();

    assert.equal($suggestions.length, expectedLength);
    for (var i = 0; i < expectedLength; i++) {
      assert.ok($suggestions.eq(i).find("strong").text().toUpperCase().indexOf(inputString.toUpperCase()) >= 0);
    }

    done();
  });
});

QUnit.test("Basics: 'Chile (es'", function(assert) {
  var inputString = "Chile (es";
  var expectedLength = 3;

  assert.expect(expectedLength + 1);
  var done = assert.async();

  var $in = jQueryFrame("#page\\:mainForm\\:inBasic\\:\\:field");
  var $suggestions = getSuggestionsInBasic();

  $in.val(inputString).trigger('input');

  waitForAjax(function() {
    $suggestions = getSuggestionsInBasic();
    return $suggestions.length === expectedLength;
  }, function() {
    $suggestions = getSuggestionsInBasic();

    assert.equal($suggestions.length, expectedLength);
    for (var i = 0; i < expectedLength; i++) {
      assert.ok($suggestions.eq(i).find("strong").text().toUpperCase().indexOf(inputString.toUpperCase()) >= 0);
    }

    done();
  });
});

QUnit.test("Basics: 'Chile (esp'", function(assert) {
  var inputString = "Chile (esp";
  var expectedLength = 3;

  assert.expect(expectedLength + 1);
  var done = assert.async();

  var $in = jQueryFrame("#page\\:mainForm\\:inBasic\\:\\:field");
  var $suggestions = getSuggestionsInBasic();

  $in.val(inputString).trigger('input');

  waitForAjax(function() {
    $suggestions = getSuggestionsInBasic();
    return $suggestions.length === expectedLength;
  }, function() {
    $suggestions = getSuggestionsInBasic();

    assert.equal($suggestions.length, expectedLength);
    for (var i = 0; i < expectedLength; i++) {
      assert.ok($suggestions.eq(i).find("strong").text().toUpperCase().indexOf(inputString.toUpperCase()) >= 0);
    }

    done();
  });
});

QUnit.test("Basics: 'Chile (espa'", function(assert) {
  var inputString = "Chile (espa";
  var expectedLength = 3;

  assert.expect(expectedLength + 1);
  var done = assert.async();

  var $in = jQueryFrame("#page\\:mainForm\\:inBasic\\:\\:field");
  var $suggestions = getSuggestionsInBasic();

  $in.val(inputString).trigger('input');

  waitForAjax(function() {
    $suggestions = getSuggestionsInBasic();
    return $suggestions.length === expectedLength;
  }, function() {
    $suggestions = getSuggestionsInBasic();

    assert.equal($suggestions.length, expectedLength);
    for (var i = 0; i < expectedLength; i++) {
      assert.ok($suggestions.eq(i).find("strong").text().toUpperCase().indexOf(inputString.toUpperCase()) >= 0);
    }

    done();
  });
});

QUnit.test("Basics: 'Chile (espan'", function(assert) {
  var inputString = "Chile (espan";
  var expectedLength = 2;

  assert.expect(expectedLength + 1);
  var done = assert.async();

  var $in = jQueryFrame("#page\\:mainForm\\:inBasic\\:\\:field");
  var $suggestions = getSuggestionsInBasic();

  $in.val(inputString).trigger('input');

  waitForAjax(function() {
    $suggestions = getSuggestionsInBasic();
    return $suggestions.length === expectedLength;
  }, function() {
    $suggestions = getSuggestionsInBasic();

    assert.equal($suggestions.length, expectedLength);
    for (var i = 0; i < expectedLength; i++) {
      assert.ok($suggestions.eq(i).find("strong").text().toUpperCase().indexOf(inputString.toUpperCase()) >= 0);
    }

    done();
  });
});

QUnit.test("Basics: 'Chile (espanj'", function(assert) {
  var inputString = "Chile (espanj";
  var expectedLength = 1;

  assert.expect(expectedLength + 1);
  var done = assert.async();

  var $in = jQueryFrame("#page\\:mainForm\\:inBasic\\:\\:field");
  var $suggestions = getSuggestionsInBasic();

  $in.val(inputString).trigger('input');

  waitForAjax(function() {
    $suggestions = getSuggestionsInBasic();
    return $suggestions.length === expectedLength;
  }, function() {
    $suggestions = getSuggestionsInBasic();

    assert.equal($suggestions.length, expectedLength);
    for (var i = 0; i < expectedLength; i++) {
      assert.ok($suggestions.eq(i).find("strong").text().toUpperCase().indexOf(inputString.toUpperCase()) >= 0);
    }

    done();
  });
});

QUnit.test("Basics: 'Chile (espanja'", function(assert) {
  var inputString = "Chile (espanja";
  var expectedLength = 1;

  assert.expect(expectedLength + 1);
  var done = assert.async();

  var $in = jQueryFrame("#page\\:mainForm\\:inBasic\\:\\:field");
  var $suggestions = getSuggestionsInBasic();

  $in.val(inputString).trigger('input');

  waitForAjax(function() {
    $suggestions = getSuggestionsInBasic();
    return $suggestions.length === expectedLength;
  }, function() {
    $suggestions = getSuggestionsInBasic();

    assert.equal($suggestions.length, expectedLength);
    for (var i = 0; i < expectedLength; i++) {
      assert.ok($suggestions.eq(i).find("strong").text().toUpperCase().indexOf(inputString.toUpperCase()) >= 0);
    }

    done();
  });
});

QUnit.test("Basics: 'Chile (espanja)'", function(assert) {
  var inputString = "Chile (espanja)";
  var expectedLength = 1;

  assert.expect(expectedLength + 1);
  var done = assert.async();

  var $in = jQueryFrame("#page\\:mainForm\\:inBasic\\:\\:field");
  var $suggestions = getSuggestionsInBasic();

  $in.val(inputString).trigger('input');

  waitForAjax(function() {
    $suggestions = getSuggestionsInBasic();
    return $suggestions.length === expectedLength;
  }, function() {
    $suggestions = getSuggestionsInBasic();

    assert.equal($suggestions.length, expectedLength);
    for (var i = 0; i < expectedLength; i++) {
      assert.ok($suggestions.eq(i).find("strong").text().toUpperCase().indexOf(inputString.toUpperCase()) >= 0);
    }

    done();
  });
});

QUnit.test("Basics: Add 'Belgiu' and click first entry.", function(assert) {
  assert.expect(12);
  var done = assert.async();

  assert.equal(1, 1);
  var inSelector = "#page\\:mainForm\\:inBasic\\:\\:field";
  var $in = jQueryFrame(inSelector);
  assert.equal(1, 1);
  var $suggestions = getSuggestionsInBasic();
  assert.equal(1, 1);

  $in.val("Belgiu").trigger('input');
  assert.equal($in.val(), "Belgiu");

  waitForAjax(function() {
    $suggestions = getSuggestionsInBasic();
    return $suggestions.length === 2;
  }, function() {
    assert.equal(1, 1);
    $in = jQueryFrame(inSelector);
    assert.equal(1, 1);
    $suggestions = getSuggestionsInBasic();
    assert.equal(1, 1);

    assert.equal($suggestions.length, 2);
    assert.equal($suggestions.eq(0).find("strong").text(), "Belgiu");
    assert.equal($suggestions.eq(1).find("strong").text(), "Belgiu");

    // click first entry
    assert.equal($in.val(), "Belgiu");
    $suggestions.eq(0).click();
    assert.equal($in.val(), "Belgium (Dutch)");

    done();
  });
});

QUnit.test("Advanced: 'H'", function(assert) {
  assert.expect(1);
  var done = assert.async();

  var $in = jQueryFrame("#page\\:mainForm\\:inAdvanced\\:\\:field");
  var $suggestions = getSuggestionsInAdvanced();
  var suggestionDelay = 2000;

  $in.val("H").trigger('input');

  setTimeout(function() {
    waitForAjax(function() {
      $suggestions = getSuggestionsInAdvanced();
      return $suggestions.length === 0;
    }, function() {
      $suggestions = getSuggestionsInAdvanced();
      assert.equal($suggestions.length, 0);
      done();
    });
  }, suggestionDelay);
});

QUnit.test("Advanced: 'Ho'", function(assert) {
  assert.expect(4);
  var done = assert.async(2);

  var inSelector = "#page\\:mainForm\\:inAdvanced\\:\\:field";
  var $in = jQueryFrame(inSelector);
  var $suggestions = getSuggestionsInAdvanced();
  var suggestionDelay = 2000;

  $in.val("Ho").trigger('input');

  setTimeout(function() {
    $suggestions = getSuggestionsInAdvanced();

    // Nothing happen, because the delay is greater than the default delay.
    assert.equal($suggestions.length, 0);

    done();
  }, 200); // default suggestion delay

  setTimeout(function() {
    waitForAjax(function() {
      $in = jQueryFrame(inSelector);
      $suggestions = getSuggestionsInAdvanced();

      return $suggestions.length === 2
          && $suggestions.eq(0).find("strong").text() === "ho"
          && $suggestions.eq(1).find("strong").text() === "ho";
    }, function() {
      $in = jQueryFrame(inSelector);
      $suggestions = getSuggestionsInAdvanced();

      assert.equal($suggestions.length, 2);
      assert.equal($suggestions.eq(0).find("strong").text(), "ho");
      assert.equal($suggestions.eq(1).find("strong").text(), "ho");

      done();
    });
  }, suggestionDelay);
});

QUnit.test("Client sided: 'Korean'", function(assert) {
  assert.expect(5);

  var $in = jQueryFrame("#page\\:mainForm\\:inClient\\:\\:field");
  var $suggestions = getSuggestionsInClient();

  $in.val("Korean").trigger('input');

  $suggestions = getSuggestionsInClient();
  assert.equal($suggestions.length, 4);
  assert.equal($suggestions.eq(0).find("strong").text(), "Korean");
  assert.equal($suggestions.eq(1).find("strong").text(), "Korean");
  assert.equal($suggestions.eq(2).find("strong").text(), "Korean");
  assert.equal($suggestions.eq(3).find("strong").text(), "Korean");
});

function getSuggestionsInBasic() {
  return getSuggestions("#page\\:mainForm\\:inBasic")
}

function getSuggestionsInAdvanced() {
  return getSuggestions("#page\\:mainForm\\:inAdvanced")
}

function getSuggestionsInClient() {
  return getSuggestions("#page\\:mainForm\\:inClient");
}

function getSuggestions(id) {
  return jQueryFrame(Tobago.Utils.escapeClientId(
          jQueryFrame(id + " .tobago-suggest").attr("id") + "::popup") + " .tt-suggestion");
}
