package com.bahmanm.datedreamer


import org.yaml.snakeyaml.Yaml

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

  private Config() {}

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

  //////////////////////////////////////////////////////////////////////////////
  static Config fromYamlText(String text) {
    def yaml = new Yaml().load(text)
    def config = new Config()
    config.with {
      outputMode = Config.OutputMode.valueOf(
	yaml.outputMode.toUpperCase()
      )
      filePath = yaml.filePath as String
      nPoints = yaml.nPoints as int
      leap = yaml.leap as int      
    }
    config
  }
  
  static Config fromYaml(String path) {
    fromYamlText(new File(path).text)
  }

  static String validate(Config config) {
    if (config.outputMode in [Config.OutputMode.FILE, Config.OutputMode.BOTH])
      if (config.filePath == null) '`filePath` cannot be empty'
    if (config.nPoints < 10) '`nPoints` cannot be less than 10'
    if (config.leap < 0) '`leap` cannot be less than 0'
    null
  }
  
}
