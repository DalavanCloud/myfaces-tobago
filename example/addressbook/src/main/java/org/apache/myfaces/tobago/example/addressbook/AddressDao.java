package org.apache.myfaces.tobago.example.addressbook;

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

/*
 * Created 29.11.2004 17:36:20.
 * $Id: Controller.java,v 1.2 2005/08/10 11:57:55 lofwyr Exp $
 */

import java.util.List;

public interface AddressDao {

  Address updateAddress(Address address) throws AddressDaoException;

  List<Address> findAddresses() throws AddressDaoException;

  void removeAddress(Address address) throws AddressDaoException;

  Address getAddress(Integer id);
}
