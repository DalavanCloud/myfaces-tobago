/*
 * Copyright (c) 2002 Atanion GmbH, Germany. All rights reserved.
 * Created: 06.08.2002 13:42:01
 * $Id$
 */
package com.atanion.tobago.context;

import java.util.Iterator;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;

public class UserAgent {

  public static final String DEFAULT_NAME = "standard";

  public static final UserAgent DEFAULT = new UserAgent(null, null);


  public static final UserAgent MSIE = new UserAgent("msie", null);

  public static final UserAgent MSIE_5_0 = new UserAgent("msie", "5_0");

  public static final UserAgent MSIE_5_5 = new UserAgent("msie", "5_5");

  public static final UserAgent MSIE_6_0 = new UserAgent("msie", "6_0");

  public static final UserAgent MSIE_5_0_MAC = new UserAgent("msie", "5_0_mac");

  public static final UserAgent MSIE_6_0_MAC = new UserAgent("msie", "6_0_mac");


  public static final UserAgent OPERA = new UserAgent("opera", null);

  public static final UserAgent OPERA_5_0 = new UserAgent("opera", "5_0");

  public static final UserAgent OPERA_6_0 = new UserAgent("opera", "6_0");

  public static final UserAgent OPERA_7_11 = new UserAgent("opera", "7_11");


  public static final UserAgent MOZILLA = new UserAgent("mozilla", null);

  public static final UserAgent MOZILLA_4_7 = new UserAgent("mozilla", "4_7");

  public static final UserAgent MOZILLA_5_0 = new UserAgent("mozilla", "5_0");

  public static final UserAgent MOZILLA_5_0_R1_6 = new UserAgent("mozilla", "5_0_r1_6");


  public static UserAgent getInstance(String header){
    if (header == null) {
      return DEFAULT;
    }

    header = header.toLowerCase().replace('/', ' ');
    if (header.indexOf("opera") > -1){
      if (header.indexOf("opera 5.0") > -1){
        return OPERA_5_0;
      } else if (header.indexOf("opera 6.0") > -1){
        return OPERA_6_0;
      } else if (header.indexOf("opera 7.11") > -1){
        return OPERA_7_11;
      } else {
        return OPERA;
      }
    } else if (header.indexOf("msie") > -1) {
      if (header.indexOf("msie 5.0") > -1) {
        if (header.indexOf("mac") > -1) {
          return MSIE_5_0_MAC;
        }else {
          return MSIE_5_0;
        }
      } else if (header.indexOf("msie 5.5") > -1) {
        return MSIE_5_5;
      } else if (header.indexOf("msie 6.0") > -1) {
         if (header.indexOf("mac") > -1) {
          return MSIE_6_0_MAC;
        }else {
          return MSIE_6_0;
        }
      } else {
        return MSIE;
      }
    } else if (header.indexOf("mozilla") > -1) {
      if (header.indexOf("mozilla 4.7") > -1) {
        return MOZILLA_4_7;
      } else if (header.indexOf("mozilla 5.0") > -1) {
        if (header.indexOf("rv:1.6") > -1) {
          return MOZILLA_5_0_R1_6;
        }
        else {
          return MOZILLA_5_0;
        }
      } else {
        return MOZILLA;
      }
    }

    return DEFAULT;
  }
  public static UserAgent getInstanceForId(String id){
     if (id == null) {
       return DEFAULT;
     }

     if (id.indexOf("opera") == 0){
       if (id.equals("opera_5_0")){
         return OPERA_5_0;
       } else if (id.equals("opera_6_0")){
         return OPERA_6_0;
       } else if (id.equals("opera_7_11")){
         return OPERA_7_11;
       } else {
         return OPERA;
       }
     } else if (id.indexOf("msie") == 0) {
       if (id.equals("msie_5_0")) {
         return MSIE_5_0;
       } else if (id.equals("msie_5_0_mac")) {
         return MSIE_5_0_MAC;
       } else if (id.equals("msie_5_5")) {
         return MSIE_5_5;
       } else if (id.equals("msie_6_0")) {
         return MSIE_6_0;
       } else if (id.equals("msie_6_0_mac")) {
         return MSIE_6_0_MAC;
       } else {
         return MSIE;
       }
     } else if (id.indexOf("mozilla") == 0) {
       if (id.equals("mozilla_4_7")) {
         return MOZILLA_4_7;
       } else if (id.equals("mozilla_5_0")) {
         return MOZILLA_5_0;
       } else if (id.equals("mozilla_5_0_r1_6")) {
         return MOZILLA_5_0_R1_6;
       } else {
         return MOZILLA;
       }
     }

     return DEFAULT;
   }



  public Iterator iterator(){
    return iterator(false);
  }

  public Iterator iterator(boolean reverseOrder) {
    List list = new ArrayList(3);
    if (version != null) {
      list.add(name + '_' + version);
    }
    if (name != null) {
      list.add(name);
    }
    list.add(DEFAULT_NAME);
    if (reverseOrder){
      Collections.reverse(list);
    }
    return list.iterator();
  }



  private String name;

  private String version;

  private UserAgent(String name, String version){
    this.name    = name;
    this.version = version;
  }

  public boolean isMsie() {
    return MSIE.name.equals(name);
  }

  /** @deprecated don't use toString() functionallity! */
  public String toString() {
    return version != null
        ? name + '_' +  version
        : name;
  }
}

