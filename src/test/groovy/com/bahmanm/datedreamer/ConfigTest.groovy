package com.bahmanm.datedreamer


import org.junit.Test

class ConfigTest {

  @Test
  void test_fromYamlText() {
    def text = '''
nPoints: 10000
leap: 3
filePath: /home/bahman/t.png
outputMode: file
    '''.trim()
    def config = Config.fromYamlText(text)
    assert config != null
    assert config.outputMode == Config.OutputMode.FILE
    assert config.nPoints == 10000
    assert config.leap == 3
    assert config.filePath == '/home/bahman/t.png'
  }

}

