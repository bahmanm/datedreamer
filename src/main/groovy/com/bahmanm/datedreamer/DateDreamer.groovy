package com.bahmanm.datedreamer


import javax.swing.JFrame
import java.awt.Color
import javax.imageio.ImageIO
import java.awt.image.BufferedImage
import java.awt.Graphics2D
import org.math.plot.*
import static java.lang.Math.abs
import com.bahmanm.datedreamer.config.Config

/**
 * Convert calendar dates to alien looking plots using partial sums.
 *
 * @author Bahman Movaqar <Bahman@BahmanM.com>
 */
class DateDreamer {

  int y, m, d
  DataGen.Result result
  Config config

  DateDreamer(int y, int m, int d, Config config) {
    this.y = y; this.m = m; this.d = d; this.config = config
  }

  int nPoints() {
    if (config.framesConfig.enabled)
      return config.framesConfig.frames.inject(0) { acc, f ->
	acc + (f.nPoints * f.nRepeat)
      }
    else return config.nPoints
  }

  private void writePlots() {
    Plotter plotter = new Plotter(result, config.width)
    if (config.framesConfig.enabled == true) {
      int sliceStart = 0
      int sliceEnd = 0
      def frames = config
	.framesConfig
	.frames
	.inject([]) { acc, f ->
	  f.nRepeat.times { 
	    sliceEnd += f.nPoints
	    acc << [sliceStart, sliceEnd-1]
	  }
	  acc
      }
      frames.eachWithIndex { f, i ->
	print("Writing plot frame $i...")
	plotter.writePlotToFile(
	  f[0], f[1],
	  config.framesConfig.directory +
	    File.separator +
	    config.framesConfig.prefix + "${i}.png"
	)
	println('done.')
      }
    } else {
      print('Writing the plot to file...')
      plotter.writePlotToFile(0, result.xs.length-1, config.filePath)
      println('done.')
    }
  }

  private void showPlot() {
    Plotter plotter = new Plotter(result, config.width)  
    plotter.showPlot(0, result.xs.length-1)
  }
  
  private void doGenerate() {
    print('Generating plot data. This may take a few seconds...')
    result = DataGen.generate(y, m, d, nPoints(), config.leap)
    println('done')
    if (config.outputMode in [Config.OutputMode.FILE, Config.OutputMode.BOTH]) {

      writePlots()
      println('done.')
    }
    if (config.outputMode in [Config.OutputMode.UI, Config.OutputMode.BOTH]) {
      print('Setting up the plot viewer...')
      showPlot()
      println('')
    }
  }

  static void generate(Config config) {
    def date = new Date()
    new DateDreamer(date.year-100, date.month+1, date.date, config)
      .doGenerate()
  }

}
