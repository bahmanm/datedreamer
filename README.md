# Introduction #
Date Dreamer: where calendar dates meet fantasy!
Transforming calendar dates into surprisingly intriguing (and sometimes weird) plots.

## Samples ##
Here are 3 sample plots generated by *datedreamer*.

![Date Dreamer - Sample Plot #3](https://i.imgur.com/iHKdX14.png)
![Date Dreamer - Sample Plot #2](https://i.imgur.com/CPCdShy.png)
![Date Dreamer - Sample Plot #1](https://i.imgur.com/HoUh12Z.png)

# How To Run #
You need to have Java 1.7+ installed. If you do, then just download [one of the releases](https://github.com/bahmanm/datedreamer/releases) and `$ java -jar datedreamer-X.Y.Z.jar` and behold! *NOTE: Replece `X.Y.Z` with the version number.*

If you need to customise the behaviour, run it with `-h` or `--help` option to see a list of available options. For example:

```
$ java -jar datedreamer-0.5.0.jar -h
usage: datedreamer [options]
Options
 -f,--file <arg>          path to store the plot (e.g. /home/foo/bar.png)
 -h,--help                prints this message
 -l,--initialLeap <arg>   the integer to start the plot with, e.g. with
                          n=100 and l=50, the plot starts at 50 and ends
                          at 150 (as the input).
 -n,--nPoints <arg>       how many data points to use in the plot --an
                          integer
    --noUi                don't show a plot viewer, e.g. in case you just
                          need to save the plot to file. If used, you must
                          pass the `--file` argument.

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


# Download #
Just grab the `datedreamer-VERSION.jar` file from any of the following --it's an uber-jar with all dependencies included.

* [The latest release](https://github.com/bahmanm/datedreamer/releases/latest)
* [Full list of all releases](https://github.com/bahmanm/datedreamer/releases/)

# Credits #
I got the idea from [John D. Cook's website](https://www.johndcook.com/expsum/details.html).