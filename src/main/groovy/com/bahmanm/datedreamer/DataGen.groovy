package com.bahmanm.datedreamer


import static java.lang.Math.*
import org.apache.commons.math3.complex.Complex

/**
 * Generates the data points 
 * @author Bahman Movaqar <Bahman@BahmanM.com>
 */
class DataGen {

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
  
  final static Complex CJ = new Complex(0.0, 1.0)
  final static int N = 20_000
  final static int LEAP = 3

  int yy, mm, dd
  private DataGen(int y, int m, int d) {
    yy = y ** 0.63
    mm = (d+m) ** 1.1
    dd = (d+y) ** 1.16
  }

  private Complex f(double n) {
    double tmp = ((n / mm) + ((n ** 2) / yy) + ((n ** 3) / dd)) / 4.1
    CJ
      .multiply(2 * PI * tmp)
      .exp()
  }

  private Result doGenerate() {
    Result result = new Result(N)
    Complex sum = Complex.ZERO
    double maxx, maxy, minx, miny
    for (int i=LEAP; i<N+LEAP; i++) {
      sum = sum.add(f(i))
      result.xs[i-LEAP] = sum.real
      result.ys[i-LEAP] = sum.imaginary
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

  static Result generate(int y, int m, int d) {
    new DataGen(y, m, d).doGenerate()
  }
  
}
