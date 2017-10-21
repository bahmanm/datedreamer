package com.bahmanm.datedreamer


import static java.lang.Math.*
import org.apache.commons.math3.complex.Complex
import javax.swing.JFrame
import java.awt.Color
import javax.imageio.ImageIO
import java.awt.image.BufferedImage
import java.awt.Graphics2D
import org.math.plot.*

/**
 * Convert calendar dates to alien looking plots using partial sums.
 * I got the idea from John D. Cook.
 *
 * @author Bahman Movaqar <Bahman@BahmanM.com>
 */
class DateDreamer {

  final static Complex CJ = new Complex(0.0, 1.0)
  final static int N = 20_000
  final static int LEAP = 3
  final static String TITLE = 'Date Dreamer'

  int yy, mm, dd
  double[] xs = new double[N]
  double[] ys = new double[N]
  double maxx, maxy, minx, miny

  private DateDreamer(int y, int m, int d) {
    yy = y ** 0.63
    mm = (d+m) ** 1.1
    dd = (d+y) ** 1.16
  }

  private Complex compOne(double n) {
    double tmp = ((n / mm) + ((n ** 2) / yy) + ((n ** 3) / dd)) / 4.1
    CJ
      .multiply(2 * PI * tmp)
      .exp()
  }

  private void compAll() {
    Complex sum = Complex.ZERO
    for (int i=LEAP; i<N+LEAP; i++) {
      sum = sum.add(compOne(i))
      xs[i-LEAP] = sum.real; ys[i-LEAP] = sum.imaginary
      maxx = max(sum.real, maxx); maxy = max(sum.imaginary, maxy)
      minx = min(sum.real, minx); miny = min(sum.imaginary, miny)
    }
  }

  private Plot2DPanel prepPlot() {
    double[] mins = [minx-3.0, miny-3.0]
    double[] maxs = [maxx+3.0, maxy+3.0]
    Plot2DPanel plot = new Plot2DPanel(
      mins, maxs,
      ['LIN', 'LIN'] as String[],
      ['LIN', 'LIN'] as String[]
    )
    plot.with {
      addLinePlot(TITLE, new Color(108, 0, 143), xs, ys)
      setFixedBounds(mins, maxs)
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
    int width = (maxx - minx) * 10
    int height = ((maxy - miny) / (maxx - minx)) * width + 100
    [abs(width), abs(height)]
  }

  private void doGenerate(Config conf) {
    print('Generating plot data. This may take a few seconds...')
    compAll()
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
    }
  }

  static void generate() {
    def date = new Date()
    new DateDreamer(date.year-100, date.month+1, date.date)
      .doGenerate(
        new Config(mode: Config.OutputMode.FILE, filePath: '/home/bahman/Temp/t.png')
      )
  }

}

class Config {

  static enum OutputMode {
    FILE, UI, BOTH
  }

  OutputMode mode
  String filePath
  
}