# double-reader-writer
Benchmark testing number reading/writing in Java. Relates to https://github.com/FasterXML/jackson-core/issues/577

So far, [FastDoubleParser](https://github.com/FasterXML/jackson-core/pull/747) looks useful if the parser results can be verified to be correct. The results for [RyuWriter](https://github.com/FasterXML/jackson-core/pull/749) seem more mixed. RyuWriter seems to have a major weak point dealing with whole numbers (int/long) but is faster than JDK for writing random doubles. The [Schubfach](https://github.com/pjfanning/double-reader-writer/issues/1) writer has similar results to RyuWriter but is much better with whole numbers.

Also, seems worthwhile to use newer JDKs regardless of whether that is with JDK read/writing or with the custom implementations.


## Azul Java 17.0.2 Old Macbook


```
Benchmark                               Mode  Cnt       Score      Error  Units
ReaderBenchmark.fastDoubleLongReader   thrpt    5   22858.144 ±  261.718  ops/s
ReaderBenchmark.fastDoubleReader       thrpt    5   24819.875 ±  368.288  ops/s
ReaderBenchmark.jdkDoubleReader        thrpt    5    3854.082 ±   67.203  ops/s
ReaderBenchmark.jdkLongReader          thrpt    5   22811.293 ±  351.590  ops/s
WriterBenchmark.jdkDoubleIntWriter     thrpt    5   26394.304 ± 1779.867  ops/s
WriterBenchmark.jdkDoubleWriter        thrpt    5    3439.019 ±   69.317  ops/s
WriterBenchmark.jdkLongWriter          thrpt    5  109371.088 ± 6345.820  ops/s
WriterBenchmark.ryuDoubleWriter        thrpt    5   10890.341 ±  116.903  ops/s
WriterBenchmark.ryuIntWriter           thrpt    5    7395.981 ±   53.028  ops/s
WriterBenchmark.schubfachDoubleWriter  thrpt    5   12675.343 ±  122.862  ops/s
WriterBenchmark.schubfachIntWriter     thrpt    5   22488.307 ±   78.873  ops/s
```

## Azul Java 8.0.302 Old Macbook

```
Benchmark                               Mode  Cnt      Score       Error  Units
ReaderBenchmark.fastDoubleLongReader   thrpt    5  17401.416 ±   852.803  ops/s
ReaderBenchmark.fastDoubleReader       thrpt    5  20016.967 ±   549.814  ops/s
ReaderBenchmark.jdkDoubleReader        thrpt    5   3381.695 ±    15.922  ops/s
ReaderBenchmark.jdkLongReader          thrpt    5  15467.008 ±   263.848  ops/s
WriterBenchmark.jdkDoubleIntWriter     thrpt    5  27664.566 ±   233.965  ops/s
WriterBenchmark.jdkDoubleWriter        thrpt    5   3406.844 ±   559.611  ops/s
WriterBenchmark.jdkLongWriter          thrpt    5  39736.881 ± 23287.619  ops/s
WriterBenchmark.ryuDoubleWriter        thrpt    5   9247.258 ±  5943.127  ops/s
WriterBenchmark.ryuIntWriter           thrpt    5   7401.490 ±   641.347  ops/s
WriterBenchmark.schubfachDoubleWriter  thrpt    5   9474.260 ±  4323.468  ops/s
WriterBenchmark.schubfachIntWriter     thrpt    5  15566.757 ± 14525.793  ops/s
```
