package com.bahmanm.datedreamer


import javax.swing.JFrame
import java.awt.Color
import javax.imageio.ImageIO
import java.awt.image.BufferedImage
import java.awt.Graphics2D
import org.math.plot.*
import static java.lang.Math.*
import groovy.transform.CompileStatic

/**
 * Rendering and/or saving plots to files.
 *
 * @author Bahman Movaqar <Bahman@BahmanM.com>
 */
@CompileStatic
class Plotter {

  static final String TITLE = 'Date Dreamer'
  DataGen.Result data
  int width, height
  
  Plotter(DataGen.Result data, int width) {
    this.data = data
    this.width = width
    this.height = getPlotHeight(data, width)
  }

  void writePlotToFile(int sliceStart, int sliceEnd, String filePath) {
    def plot = prepPlot(sliceStart, sliceEnd)
    def img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
    plot.with {
      setSize(width, height)
      doLayout()
      paint(img.createGraphics())
    }
    try {
      ImageIO.write(img, 'png', new File(filePath))
    } catch (Exception e) {
      println("\nERROR: Failed to write plot to file --${e.message}.")
    }
  }

  void showPlot(int sliceStart, int sliceEnd) {
    def plot = prepPlot(sliceStart, sliceEnd)
    new JFrame(TITLE).with {
      setSize(width, height)
      contentPane = plot
      visible = true
      defaultCloseOperation = EXIT_ON_CLOSE
    }
  }

  private Plot2DPanel prepPlot(int sliceStart, int sliceEnd) {
    double[] mins = [data.minx-3.0, data.miny-3.0] as double[]
    double[] maxs = [data.maxx+3.0, data.maxy+3.0] as double[]
    Plot2DPanel plot = new Plot2DPanel(
      mins, maxs,
      ['LIN', 'LIN'] as String[],
      ['LIN', 'LIN'] as String[]
    )
    plot.with {
      addLinePlot(
	TITLE, new Color(108, 0, 143),
	data.xs[sliceStart..sliceEnd] as double[],
	data.ys[sliceStart..sliceEnd] as double[]
      )
      setFixedBounds(data.mins, data.maxs)
      removeLegend()
    }
    plot
  }

  static private int getPlotHeight(DataGen.Result data, int desiredWidth) {
    double actualWidth = (data.maxx - data.minx) * 10
    double height = (data.maxy - data.miny) * 10
    (abs(height) * (desiredWidth / actualWidth)).intValue() + 1
  }
  
}
