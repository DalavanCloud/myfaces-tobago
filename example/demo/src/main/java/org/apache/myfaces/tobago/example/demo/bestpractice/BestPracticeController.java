package org.apache.myfaces.tobago.example.demo.bestpractice;

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

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.IOException;


public class BestPracticeController {

  private static final Log LOG = LogFactory.getLog(BestPracticeController.class);

  public String throwException() {
    throw new RuntimeException("This exception is forced by the user.");
  }

  public String viewPdfInBrowser() {
    return viewPdf(false);
  }

  public String viewPdfOutsideOfBrowser() {
    return viewPdf(true);
  }

  private String viewPdf(boolean outside) {

    FacesContext facesContext = FacesContext.getCurrentInstance();

    InputStream inputStream = null;
    try {
      inputStream = facesContext.getExternalContext().getResourceAsStream("best-practice/sample.pdf");
      if (inputStream == null) {
        inputStream = facesContext.getExternalContext().getResourceAsStream("/best-practice/sample.pdf");
      }
      HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
      response.setContentType("application/pdf");
      if (outside) {
        response.setHeader("Content-Disposition", "attachment; filename=sample.pdf");
      }
      IOUtils.copy(inputStream, response.getOutputStream());
    } catch (IOException e) {
      LOG.warn("Cannot deliver pdf", e);
      return "error"; // response via faces
    } finally {
      IOUtils.closeQuietly(inputStream);
    }
    facesContext.responseComplete();
    return null;
  }
}
