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
  Plotter plotter

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

  private List<List<Integer>> frameBoundaries() {
    int sliceStart = 0
    int sliceEnd = 0
    config
      .framesConfig
      .frames
      .inject([]) { acc, f ->
        f.nRepeat.times {
	  sliceStart = sliceEnd
	  sliceEnd += f.nPoints
	  acc << [sliceStart, sliceEnd-1]
        }
        acc
      }
  }

  private void writeFrame(int n, List<Integer> boundaries) {
    print("Writing plot frame $n...")
    if (config.framesConfig.interimEnabled) {
      plotter.writePlotToFile(
	boundaries[0], boundaries[1],
	config.framesConfig.directory +
	  File.separator +
	  config.framesConfig.prefix + "${n}-0.png",
	config.framesConfig.interimColor
      ) 
    }
    plotter.writePlotToFile(
      0, boundaries[1],
      config.framesConfig.directory +
	File.separator +
	config.framesConfig.prefix + "${n}-1.png",
      config.color
    )
    println('done.')    
  }
  
  private void writePlot() {
    plotter = new Plotter(result, config.width)
    if (config.framesConfig.enabled == true) {
      def frames = frameBoundaries()
      frames.eachWithIndex { f, i ->
	writeFrame(i, f)
      }
    } else {
      print('Writing the plot to file...')
      plotter.writePlotToFile(
	0, result.xs.length-1, config.filePath, config.color
      )
      println('done.')
    }
  }

  private void showPlot() {
    plotter = new Plotter(result, config.width)  
    plotter.showPlot(0, result.xs.length-1, config.color)
  }
  
  private void doGenerate() {
    print('Generating plot data. This may take a few seconds...')
    result = DataGen.generate(y, m, d, nPoints(), config.leap)
    println('done')
    if (config.outputMode in [Config.OutputMode.FILE, Config.OutputMode.BOTH]) {
      writePlot()
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
