package com.bahmanm.datedreamer.config


import org.junit.Test

class ConfigTest {

  @Test
  void test_fromYamlText() {
    def text = '''
nPoints: 10000
leap: 3
filePath: /home/bahman/t.png
outputMode: file
width: 500
color: 0x112233
frames:
  enabled: yes
  directory: .
  prefix: 'i-'
  showInterim: yes
  interimColor: 0x332211
  data:
    - nPoints: 100
      nRepeat: 10
    - nPoints: 500
      nRepeat: 5
    '''.trim()
    def config = Config.fromYamlText(text)
    assert config != null
    assert config.outputMode == Config.OutputMode.FILE
    assert config.nPoints == 10000
    assert config.leap == 3
    assert config.filePath == '/home/bahman/t.png'
    assert config.width == 500
    assert config.color == 1122867
    assert config.framesConfig != null
    assert config.framesConfig.enabled == true
    assert config.framesConfig.directory == '.'
    assert config.framesConfig.prefix == 'i-'
    assert config.framesConfig.interimEnabled == true
    assert config.framesConfig.interimColor == 3351057
    assert config.framesConfig.frames.size() == 2
    assert config.framesConfig.frames[0].nPoints == 100
    assert config.framesConfig.frames[0].nRepeat == 10
    assert config.framesConfig.frames[1].nPoints == 500
    assert config.framesConfig.frames[1].nRepeat == 5
  }

}

