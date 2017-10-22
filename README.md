Table Of Contents
=================
   * [Introduction](#introduction)
   * [Download](#download)
   * [What It Does](#what-it-does)
      * [Samples](#samples)
   * [How To Run](#how-to-run)
      * [Configuration Options](#configuration-options)
   * [How It Works](#how-it-works)
   * [Credits](#credits)


# Introduction #
Date Dreamer: where calendar dates meet fantasy!
Transforming calendar dates into surprisingly intriguing (and sometimes weird) plots.

# Download #
* [The latest release](https://github.com/bahmanm/datedreamer/releases/latest)
* [Full list of all releases](https://github.com/bahmanm/datedreamer/releases/)

# What It Does #
*Date Dreamer* can operate in 4 modes:
1. UI-only: the plot is displayed in a graphical window.
2. File-only: the plot is written to a PNG file.
3. File-only with interim frames: the plot is rendered partially in frames, demonstrating the process.
4. UI and File: where you can enjoy the graphical display and at the same time have the plot(s) saved as images.

See "Configuration Options" for information on how to configure different modes.

## Samples ##
Here are 3 sample plots generated by *datedreamer*.

![Date Dreamer - Sample Plot #3](https://i.imgur.com/iHKdX14.png)
![Date Dreamer - Sample Plot #2](https://i.imgur.com/CPCdShy.png)
![Date Dreamer - Sample Plot #1](https://i.imgur.com/HoUh12Z.png)

# How To Run #
The only thing you need before running *Date Dreamer* is Java 1.7 and above. If you have it, then just download a release and follow the instructions in HOWTO_RUN file.

*Note: Starting from release 0.6.0, the command line options have been replaced by a YAML configuration file which is self-descriptive.*

## Configuration Options ##

```yaml
##
## Configuration options for Date Dreamer
##

# How many data points to generate?
# Though a bigger value means better precision, it comes at the price of speed
# and the size of the image. Usually 20000 is a good balance.
nPoints: 20000

# Where to output the plot?
# Can be 'file', 'ui' or 'both'.
outputMode: ui

# Where to write the image file?
# In case `outputMode` is set to `file` or `both`, this is the path to the file.
filePath: datedreamer.png

# What is the width of the image in pixels?
# Scales the width and height of the original image so that the width is
# `width`.
# For instance, if the original image is 300x100 and `width` is set to 800,
# the output dimensions will be 800x267.
width: 900

# What is the line color of the plot?
# In hex RGB format. Note `0x` at the beginning.
color: 0x6c008f

# Instructions for Date Dreamer to generate frames as the "dreaming" happens!
# If enabled, after generating certain number of points Date Dreamer will write
# the resulting (partial) plot to a file.
frames:
  # Is framed rendering enabled?
  # If set to `no`, none of the other options will have any effect whatsoever.
  # If set to `yes`, `filePath` and `nPoints` will have no effect.
  enabled: yes

  # In which directory to place the frames?
  # The directory should already exist and be accessible to Date Dreamer.
  directory: .
  
  # What is the prefix of the partial image files?
  # Date Dreamer will write image files with a simple naming convention:
  # `prefix+i`. For instance: `frame-0.png`, `frame-1.png`, ...
  prefix: 'frame-'

  # Should Date Dreamer show interim frames?
  # If you enable interim frames, then each frame will be represented as 2
  # frames: 1 with the portion rendered using `interimColor` and the other
  # one using `color`.
  showInterim: no

  # What is the line color of interim frames?
  # Only takes effect if `showInterim` is enabled.
  # Note `0x` at the beginning.
  interimColor: 0xff075f

  # Data to generate the frames. Each set contains the number of points in each
  # frame and the number of frames.
  data:
    - nPoints: 100
      nRepeat: 15
    - nPoints: 500
      nRepeat: 13
    - nPoints: 1000
      nRepeat: 13
    
# What is the initial seed to f(i)?
# Probably you don't need to change this. That said, take a look at the README 
# for a better understanding.
leap: 3

```

# How It Works #
Internally, the current date (or in future releases, the date you pass to it) is used to build a function. This function is then called N times, generating N data points. The list of data points is then converted to the list of partial sums of data points. And finally rendered to an image.

In math terms, given

![Definition of Y, M and D](https://i.imgur.com/P4KlG5d.gif)

![Definition of C1 and C2](https://i.imgur.com/GiOxOwm.gif)

and

![Definition of f(i)](https://i.imgur.com/vtZoTQe.gif)

the points are generated using

![Definition of g(n)](https://i.imgur.com/gXWf8L3.gif)

and then rendered as a line plot.

# Credits #
I got the idea from [John D. Cook's website](https://www.johndcook.com/expsum/details.html).