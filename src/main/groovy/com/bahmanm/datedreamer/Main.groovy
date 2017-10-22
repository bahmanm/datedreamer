package com.bahmanm.datedreamer


/**
 * DateDreamer launcher.
 * 
 * @author Bahman Movaqar <Bahman@BahmanM.com>
 */
class Main {

  static void main(String[] args) {
    def confFile = args.length > 0 ? args[0] : 'datedreamer.yml'
    def config = Config.fromYaml(confFile)
    def validation = Config.validate(config)
    if (validation == null)
      DateDreamer.generate(config)
    else
      println("Invalid configuration option: [$validation]")
  }

}
