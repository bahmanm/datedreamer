package com.bahmanm.datedreamer


class Main {

  static Map parseArgs(CliBuilder cli, String[] args) {
    def options = cli.parse(args)
    if (options)
      [file: options.file ?: null, noUi: options.noUi]
    else
      null
  }

  static CliBuilder setupCli() {
    def cli = new CliBuilder(
      usage: 'datedreamer [options]',
      header: 'Options'
    )
    cli.f(
      args: 1,
      longOpt: 'file',
      'path to store the plot (e.g. /home/foo/bar.png)'
    )
    cli._(
      longOpt: 'noUi',
      "don't show a plot viewer, e.g. in case you just need to save the plot to file. " +
      "If used, you must pass the `--file` argument."
    )
    cli
  }

  static Config optionsToConfig(Map opts) {
    def conf = new Config()
    if (opts.file != null && opts.noUi == false)
      conf.mode = Config.OutputMode.BOTH
    else if (opts.file == null)
      conf.mode = Config.OutputMode.UI
    else
      conf.mode = Config.OutputMode.FILE
    conf.filePath = opts.file
    conf
  }

  static boolean validOptions(Map opts) {
    if (opts.file == null && opts.noUi == true)
      false
    else
      true
  }

  static void main(String[] args) {
    def cli = setupCli()
    def opts = parseArgs(cli, args)
    if (validOptions(opts))
      DateDreamer.generate(optionsToConfig(opts))
    else
      println(cli.usage())
  }

}