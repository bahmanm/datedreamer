package com.bahmanm.datedreamer.config


/**
 *  
 * @author Bahman Movaqar <Bahman@BahmanM.com>
 */
class FramesConfig {
  
  static class Frame {
    int nPoints
    int nRepeat

    static String validate(Frame frame) {
      if (frame.nPoints < 1) return '`nPoints` should be > 0.'
      if (frame.nRepeat < 1) return '`nRepeat` should be > 0.'
      return null
    }
  }
  
  boolean enabled
  String directory
  String prefix
  List<Frame> frames

  static fromMap(Map map) {
    def frames = new FramesConfig()
    frames.enabled = map.enabled as boolean
    frames.directory = map.directory as String
    frames.prefix = map.prefix as String
    frames.frames = map.data?.collect { d ->
      new Frame(nPoints: d.nPoints, nRepeat: d.nRepeat)
    }
    frames
  }

  static String validate(FramesConfig frames) {
    if (frames.directory == null) return '`directory` cannot be empty.'
    if (frames.prefix == null) return '`prefix` cannot be empty.'
    return frames.frames.find { f -> Frame.validate(f) != null }
  }
  
}
