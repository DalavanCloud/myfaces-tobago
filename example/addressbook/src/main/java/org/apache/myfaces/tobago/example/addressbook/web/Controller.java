package org.apache.myfaces.tobago.example.addressbook.web;

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

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.tobago.component.UIColumn;
import org.apache.myfaces.tobago.config.TobagoConfig;
import org.apache.myfaces.tobago.context.ClientProperties;
import org.apache.myfaces.tobago.context.Theme;
import org.apache.myfaces.tobago.event.SortActionEvent;
import org.apache.myfaces.tobago.example.addressbook.Address;
import org.apache.myfaces.tobago.example.addressbook.AddressDao;
import org.apache.myfaces.tobago.example.addressbook.AddressDaoException;
import org.apache.myfaces.tobago.example.addressbook.Picture;
import org.apache.myfaces.tobago.model.SheetState;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class Controller {

  private static final Log LOG = LogFactory.getLog(Controller.class);

  private static final String OUTCOME_LIST = "list";
  private static final String OUTCOME_EDITOR = "editor";

  private List<Address> currentAddressList;
  private Address currentAddress;
  private SheetState selectedAddresses;

  private String searchCriterion;

  private Locale language;

  private List<SelectItem> languages = new ArrayList<SelectItem>();

  private Countries countries;

  private Theme theme;

  private List<SelectItem> themeItems = new ArrayList<SelectItem>();

  private boolean simple;

  private boolean renderPopup;
  private boolean renderFirstName = true;
  private boolean renderLastName = true;
  private boolean renderDayOfBirth = false;

  private AddressDao addressDao;

  private FileItem uploadedFile;
  private boolean renderFileUploadPopup;

  public Controller() {
    FacesContext facesContext = FacesContext.getCurrentInstance();
    Application application = facesContext.getApplication();
    language = application.getDefaultLocale();
    facesContext.getExternalContext().getSession(true);
    initLanguages();

    TobagoConfig tobagoConfig = TobagoConfig.getInstance(facesContext);
    List<Theme> themes = new ArrayList<Theme>(tobagoConfig.getSupportedThemes());
    themes.add(0, tobagoConfig.getDefaultTheme());
    themeItems = new ArrayList<SelectItem>();
    for (Theme theme : themes) {
      themeItems.add(new SelectItem(theme, StringUtils.capitalize(theme.getDisplayName())));
    }

    ClientProperties client = ClientProperties.getInstance(facesContext);
    theme = client.getTheme();
  }

  public void setAddressDao(AddressDao addressDao) throws AddressDaoException {
    this.addressDao = addressDao;
    currentAddressList = addressDao.findAddresses();
  }

  public void sheetSorter(ActionEvent event) throws AddressDaoException {
    if (event instanceof SortActionEvent) {
      SortActionEvent sortEvent = (SortActionEvent) event;
      UIColumn column = (UIColumn) sortEvent.getColumn();

      SheetState sheetState = sortEvent.getSheet().getSheetState(FacesContext.getCurrentInstance());
      currentAddressList = addressDao.findAddresses(column.getId(), sheetState.isAscending());
    }
  }

  public String search() throws AddressDaoException {
    return OUTCOME_LIST;
  }

  public String createAddress() {
    LOG.debug("action: createAddress");
    currentAddress = new Address();
    return OUTCOME_EDITOR;
  }

  public String addDummyAddresses() throws IOException, AddressDaoException {
    for (int i=0; i<100; ++i) {
      currentAddress = RandomAddressGenerator.generateAddress();
      store();
    }
    return OUTCOME_LIST;
  }

  public String editAddress() {
    LOG.debug("action: editAddress");
    List<Integer> selection = selectedAddresses.getSelectedRows();
    if (selection.size() != 1) {
      FacesMessage error = new FacesMessage("Please select exactly one address.");
      FacesContext.getCurrentInstance().addMessage(null, error);
      return null;
    }
    currentAddress = currentAddressList.get(selection.get(0));
    return OUTCOME_EDITOR;
  }

  public String deleteAddresses() throws AddressDaoException {
    List<Integer> selection = selectedAddresses.getSelectedRows();
    if (selection.size() < 1) {
      FacesMessage error = new FacesMessage("Please select at least one address.");
      FacesContext.getCurrentInstance().addMessage(null, error);
      return null;
    }
    Collections.sort(selection); // why?
    for (int i = selection.size() - 1; i >= 0; i--) {
      Address address = currentAddressList.get(selection.get(i));
      addressDao.removeAddress(address);
    }
    selectedAddresses.resetSelected();
    currentAddressList = addressDao.findAddresses();
    return OUTCOME_LIST;
  }

  public String store() throws AddressDaoException {
    LOG.debug("action: storeAddress");
    currentAddress = addressDao.updateAddress(currentAddress);
    selectedAddresses.resetSelected();
    currentAddressList = addressDao.findAddresses();
    return OUTCOME_LIST;
  }

  public String cancel() throws AddressDaoException {
    currentAddressList = addressDao.findAddresses();
    return OUTCOME_LIST;
  }

  public String languageChangedList() {
    initLanguages();
    return OUTCOME_LIST;
  }

  public String themeChanged() {
    FacesContext facesContext = FacesContext.getCurrentInstance();
    ClientProperties client = ClientProperties.getInstance(facesContext);
    client.setTheme(theme);
    return null;
  }

  public void validatePhoneNumber(
      FacesContext context, UIComponent component, Object value) {
    String phoneNumber = (String) value;
    if (phoneNumber == null || phoneNumber.length() == 0) {
      return;
    }
    if (!phoneNumber.matches("\\+?[0-9 ]*(\\([0-9 ]*\\))?[0-9 ]*")) {
      throw new ValidatorException(MessageUtils.createErrorMessage(
          "validator_phone", context));
    }
  }

  public List getCurrentAddressList() {
    return currentAddressList;
  }

  public Address getCurrentAddress() {
    return currentAddress;
  }

  public SheetState getSelectedAddresses() {
    return selectedAddresses;
  }

  public void setSelectedAddresses(SheetState selectedAddresses) {
    this.selectedAddresses = selectedAddresses;
  }

  public boolean isRenderFileUploadPopup() {
    return renderFileUploadPopup;
  }

  public void setRenderFileUploadPopup(boolean renderFileUploadPopup) {
    LOG.debug(">>> " + renderFileUploadPopup);
    this.renderFileUploadPopup = renderFileUploadPopup;
  }

  public String cancelFileUploadPopup() {
    setRenderFileUploadPopup(false);
    return null;
  }

  public void setRenderPopup(boolean renderPopup) {
    this.renderPopup = renderPopup;
  }

  public boolean isRenderPopup() {
    return renderPopup;
  }

  public String selectColumns() {
    setRenderPopup(true);
    return OUTCOME_LIST;
  }

  public String okFileUpload() {
    setRenderFileUploadPopup(false);
    Picture picture = new Picture(uploadedFile.getContentType(), uploadedFile.get());
    currentAddress.setPicture(picture);
    return null;
  }

  public String cancelFileUpload() {
    setRenderFileUploadPopup(false);
    return null;
  }

  public String cancelPopup() {
    setRenderPopup(false);
    return OUTCOME_LIST;
  }

  public String logout() {
    FacesContext facesContext = FacesContext.getCurrentInstance();
    HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
    if (session != null) {
      session.invalidate();
    }
    return "logout";
  }

  public Locale getLanguage() {
    return language;
  }

  public String getDisplayLanguage() {
    return language.getDisplayName(language);
  }

  public void setLanguage(Locale language) {
    this.language = language;
  }

  public List<SelectItem> getLanguages() {
    return languages;
  }

  public void setLanguages(List<SelectItem> languages) {
    this.languages = languages;
  }

  public Countries getCountries() {
    return countries;
  }

  public void setCountries(Countries countries) {
    this.countries = countries;
    countries.init(language);
  }

  public List<SelectItem> getThemeItems() {
    return themeItems;
  }

  public Theme getTheme() {
    return theme;
  }

  @Deprecated
  public String getThemeName() {
    return WordUtils.capitalize(theme.getDisplayName());
  }

  public void setTheme(Theme theme) {
    this.theme = theme;
  }


  public boolean isSimple() {
    return simple;
  }

  public void setSimple(boolean simple) {
    this.simple = simple;
  }

  public boolean isRenderFirstName() {
    return renderFirstName;
  }

  public void setRenderFirstName(boolean renderFirstName) {
    this.renderFirstName = renderFirstName;
  }

  public boolean isRenderLastName() {
    return renderLastName;
  }

  public void setRenderLastName(boolean renderLastName) {
    this.renderLastName = renderLastName;
  }

  public boolean isRenderDayOfBirth() {
    return renderDayOfBirth;
  }

  public void setRenderDayOfBirth(boolean renderDayOfBirth) {
    this.renderDayOfBirth = renderDayOfBirth;
  }

  public FileItem getUploadedFile() {
    return uploadedFile;
  }

  public void setUploadedFile(FileItem uploadedFile) {
    this.uploadedFile = uploadedFile;
  }

  public String popupFileUpload() {
    setRenderFileUploadPopup(true);
    return null;
  }

  public String languageChanged() {
    countries.init(language);
    initLanguages();
/*
    // reinit date converter
    FacesContext facesContext = FacesContext.getCurrentInstance();
    UIViewRoot viewRoot = facesContext.getViewRoot();
    EditableValueHolder component = (EditableValueHolder)
        viewRoot.findComponent(":page:dayOfBirth");
    if (component != null) {
      DateTimeConverter converter = (DateTimeConverter) component.getConverter();
      converter.setPattern(MessageUtils.getLocalizedString(facesContext, "editor_date_pattern"));
    }
*/
    return null;
  }

  private void initLanguages() {
    languages.clear();
    FacesContext facesContext = FacesContext.getCurrentInstance();
    Application application = facesContext.getApplication();
    Iterator supportedLocales = application.getSupportedLocales();
    while (supportedLocales.hasNext()) {
      Locale locale = (Locale) supportedLocales.next();
      SelectItem item = new SelectItem(locale, locale.getDisplayName(language));
      languages.add(item);
    }
    Collections.sort(languages, new SelectItemComparator());
  }

}
