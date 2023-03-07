package com.bahmanm.datedreamer


import static org.apache.commons.math3.util.FastMath.*
import org.apache.commons.math3.complex.Complex
import groovy.transform.CompileStatic

/**
 * Generates the data points
 * @author Bahman Movaqar <Bahman@BahmanM.com>
 */
@CompileStatic
class DataGen {

  final static Complex CJ = new Complex(0.0d, 1.0d)

  /**
   * Holds the result of the data generation.
   *
   * @author Bahman Movaqar <Bahman@BahmanM.com>
   */
  static class Result {
    double[] xs
    double[] ys
    double[] mins
    double[] maxs

    double getMinx() { mins[0] }
    void setMinx(double d) { mins[0] = d }

    double getMiny() { mins[1] }
    void setMiny(double d) { mins[1] = d }

    double getMaxx() { maxs[0] }
    void setMaxx(double d) { maxs[0] = d }

    double getMaxy() { maxs[1] }
    void setMaxy(double d) { maxs[1] = d }

    Result(int n) {
      xs = new double[n]
      ys = new double[n]
      mins = new double[2]
      maxs = new double[2]
    }
  }

  int y, m, d
  double c1, c2
  int nPoints, leap

  private DataGen(int y, int m, int d, int nPoints, int leap) {
    this.y = pow(y, 0.63d) as int
    this.m = pow(d+m, 1.1d) as int
    this.d = pow(d+y, 1.16d) as int
    this.c1 = (this.y + this.m) * PI
    this.c2 = 6.1d
    this.nPoints = nPoints
    this.leap = leap
  }

  private Complex f(double n) {
    double tmp = ((n / m) + (pow(n, 2) / y) + (pow(n, 3) / d)) / c2
    CJ
      .multiply(c1 * tmp)
      .exp()
  }

  private Result doGenerate() {
    Result result = new Result(nPoints)
    Complex sum = Complex.ZERO
    double maxx, maxy, minx, miny
    for (int i=leap; i<nPoints+leap; i++) {
      sum = sum.add(f(i))
      result.xs[i-leap] = sum.real
      result.ys[i-leap] = sum.imaginary
      maxx = max(sum.real, maxx)
      minx = min(sum.real, minx)
      maxy = max(sum.imaginary, maxy)
      miny = min(sum.imaginary, miny)
    }
    result.maxx = maxx
    result.minx = minx
    result.maxy = maxy
    result.miny = miny
    result
  }

  static Result generate(int y, int m, int d, int nPoints, int leap) {
    new DataGen(y, m, d, nPoints, leap).doGenerate()
  }

}
