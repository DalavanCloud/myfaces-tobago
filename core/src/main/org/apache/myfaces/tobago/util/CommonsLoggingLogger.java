/*
 * Copyright 2002-2005 atanion GmbH.
 * 
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 * 
 *        http://www.apache.org/licenses/LICENSE-2.0
 * 
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
/*
 * All rights reserved. Created 24.11.2004 21:56:42.
 * $Id: CommonsLoggingLogger.java,v 1.1 2005/06/20 07:41:21 lofwyr Exp $
 */
package org.apache.myfaces.tobago.util;

import org.apache.avalon.framework.logger.Logger;
import org.apache.commons.logging.Log;

public class CommonsLoggingLogger implements Logger {

  private Log log;

  public CommonsLoggingLogger(Log log) {
    this.log = log;
  }

  public void debug(String reference) {
    log.debug(reference);
  }

  public void debug(String reference, Throwable throwable) {
    log.debug(reference, throwable);
  }

  public boolean isDebugEnabled() {
    return log.isDebugEnabled();
  }

  public void info(String reference) {
    log.info(reference);
  }

  public void info(String reference, Throwable throwable) {
    log.info(reference, throwable);
  }

  public boolean isInfoEnabled() {
    return log.isInfoEnabled();
  }

  public void warn(String reference) {
    log.warn(reference);
  }

  public void warn(String reference, Throwable throwable) {
    log.warn(reference, throwable);
  }

  public boolean isWarnEnabled() {
    return log.isWarnEnabled();
  }

  public void error(String reference) {
    log.error(reference);
  }

  public void error(String reference, Throwable throwable) {
    log.error(reference, throwable);
  }

  public boolean isErrorEnabled() {
    return log.isErrorEnabled();
  }

  public void fatalError(String reference) {
    log.fatal(reference);
  }

  public void fatalError(String reference, Throwable throwable) {
    log.fatal(reference, throwable);
  }

  public boolean isFatalErrorEnabled() {
    return log.isFatalEnabled();
  }

  public Logger getChildLogger(String reference) {
    System.err.println("reference" + reference);
    return null;
  }
}
