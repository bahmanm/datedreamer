package com.bahmanm.datedreamer


/**
 * All of Date Dreamer configuration options.
 *
 * @author Bahman Movaqar <Bahman@BahmanM.com>
 */
class Config {

  enum OutputMode {
    FILE, UI, BOTH
  }

  static final Config instance = new Config()
  
  OutputMode outputMode
  String filePath
  int nPoints
  int leap

  private void setOutputMode(OutputMode outputMode) {
    this.outputMode = outputMode
  }
  
  private void setFilePath(String filePath) {
    this.filePath = filePath
  }

  private void setNPoints(int nPoints) {
    this.nPoints = nPoints
  }

  private void setLeap(int leap) {
    this.leap = leap
  }
  
  private Config() {}
  
}
