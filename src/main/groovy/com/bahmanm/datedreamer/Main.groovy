package com.bahmanm.datedreamer


/**
 * DateDreamer launcher.
 * 
 * @author Bahman Movaqar <Bahman@BahmanM.com>
 */
class Main {

  static Map parseArgs(CliBuilder cli, String[] args) {
    def options = cli.parse(args)
    if (options)
      [file: options.file ?: null,
       noUi: options.noUi ?: false,
       nPoints: (options.n ?: 10_000) as int,
       leap: (options.initialLeap ?: 3) as int,
       help: options.help]
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
    cli.n(
      args: 1,
      longOpt: 'nPoints',
      'how many data points to use in the plot --an integer'
    )
    cli.l(
      args: 1,
      longOpt: 'initialLeap',
      'the integer to start the plot with, e.g. with n=100 and l=50, the plot starts ' +
	'at 50 and ends at 150 (as the input).'
    )
    cli.h(
      args: 0,
      longOpt: 'help',
      'prints this message'
    )
    cli
  }

  static Config optionsToConfig(Map opts) {
    def conf = new Config()
    if (opts.file != null && opts.noUi == false)
      conf.outputMode = Config.OutputMode.BOTH
    else if (opts.file == null)
      conf.outputMode = Config.OutputMode.UI
    else
      conf.mode = Config.OutputMode.FILE
    conf.filePath = opts.file
    conf.nPoints = opts.nPoints
    conf.leap = opts.leap
    conf
  }

  static boolean validOptions(Map opts) {
    if (opts.help == true)
      return false
    else if (opts.file == null && opts.noUi == true)
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
