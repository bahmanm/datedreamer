package com.bahmanm.datedreamer


import javax.swing.JFrame
import java.awt.Color
import javax.imageio.ImageIO
import java.awt.image.BufferedImage
import java.awt.Graphics2D
import org.math.plot.*
import static java.lang.Math.abs

/**
 * Convert calendar dates to alien looking plots using partial sums.
 *
 * @author Bahman Movaqar <Bahman@BahmanM.com>
 */
class DateDreamer {

  final static String TITLE = 'Date Dreamer'
  int y, m, d
  DataGen.Result result

  DateDreamer(int y, int m, int d) {
    this.y = y; this.m = m; this.d = d
  }

  private Plot2DPanel prepPlot() {
    double[] mins = [result.minx-3.0, result.miny-3.0]
    double[] maxs = [result.maxx+3.0, result.maxy+3.0]
    Plot2DPanel plot = new Plot2DPanel(
      mins, maxs,
      ['LIN', 'LIN'] as String[],
      ['LIN', 'LIN'] as String[]
    )
    plot.with {
      addLinePlot(TITLE, new Color(108, 0, 143), result.xs, result.ys)
      setFixedBounds(result.mins, result.maxs)
      removeLegend()
    }
    plot
  }

  private void writePlotToFile(Plot2DPanel plot, String filePath) {
    def dims = plotDims.collect { it * 4 }
    def img = new BufferedImage(dims[0], dims[1], BufferedImage.TYPE_INT_RGB)
    plot.with {
      setSize(dims[0], dims[1])
      doLayout()
      paint(img.createGraphics())
    }
    try {
      ImageIO.write(img, 'png', new File(filePath));
    } catch (Exception e) {
      println("\nERROR: Failed to write plot to file --${e.message}.")
    }
  }

  private void showPlot(Plot2DPanel plot) {
    new JFrame(TITLE).with {
      setSize(plotDims[0], plotDims[1])
      contentPane = plot
      visible = true
      defaultCloseOperation = EXIT_ON_CLOSE
    }
  }

  private int[] getPlotDims() {
    int width = (result.maxx - result.minx) * 10
    int height = ((result.maxy - result.miny) /
		  (result.maxx - result.minx)) * width + 100
    [abs(width), abs(height)]
  }

  private void doGenerate(Config conf) {
    print('Generating plot data. This may take a few seconds...')
    result = DataGen.generate(y, m, d)
    println('done')
    def plot = prepPlot()
    if (conf.mode in [Config.OutputMode.FILE, Config.OutputMode.BOTH]) {
      print('Writing the plot to file...')
      writePlotToFile(plot, conf.filePath)
      println('done.')
    }
    if (conf.mode in [Config.OutputMode.UI, Config.OutputMode.BOTH]) {
      print('Setting up the plot viewer...')
      showPlot(plot)
      println()
    }
  }

  static void generate(Config conf) {
    def date = new Date()
    new DateDreamer(date.year-100, date.month+1, date.date)
      .doGenerate(conf)
  }

}

class Config {

  static enum OutputMode {
    FILE, UI, BOTH
  }

  OutputMode mode
  String filePath
  
}
