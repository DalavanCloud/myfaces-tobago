package org.apache.myfaces.tobago.example.nonfacesrequest;

/*
 * Copyright 2002-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
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

import java.util.Map;
import java.util.HashMap;
import java.util.Random;

public class FishPond {

  private static final Log LOG = LogFactory.getLog(FishPond.class);

  private Map<String, String> fishes;

  private String selectedFish;

  public FishPond() {
    fishes = new HashMap<String, String>();
    fishes.put("0", "Scholle");
    fishes.put("1", "Hai");
    fishes.put("2", "Luce");
    fishes.put("3", "Halibut");
    fishes.put("4", "Tamboril");
  }

  public String random() {
    Random random = new Random(System.currentTimeMillis());

    selectedFish = fishes.get("" + random.nextInt(fishes.size()));

    LOG.info("select via random: '" + selectedFish + "'");

    return "view";
  }

  public String select(String id) {
    selectedFish = fishes.get(id);

    LOG.info("select via id: '" + selectedFish + "'");

    return "view";
  }

  public String getSelectedFish() {
    return selectedFish;
  }


}
