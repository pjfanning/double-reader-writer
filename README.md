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
ReaderBenchmark.fastDoubleFloatReader  thrpt    5  23234.799 ±  7646.048  ops/s
ReaderBenchmark.fastDoubleLongReader   thrpt    5  13285.999 ±  6325.269  ops/s
ReaderBenchmark.fastDoubleReader       thrpt    5  11968.453 ± 17433.848  ops/s
ReaderBenchmark.jdkDoubleReader        thrpt    5   2585.516 ±  1037.001  ops/s
ReaderBenchmark.jdkFloatReader         thrpt    5   5965.517 ±  1139.410  ops/s
ReaderBenchmark.jdkLongReader          thrpt    5   9516.470 ±  9143.042  ops/s
WriterBenchmark.jdkDoubleIntWriter     thrpt    5  10986.540 ± 20767.640  ops/s
WriterBenchmark.jdkDoubleWriter        thrpt    5   1360.805 ±  2993.175  ops/s
WriterBenchmark.jdkLongWriter          thrpt    5  26533.555 ± 36950.402  ops/s
WriterBenchmark.ryuDoubleWriter        thrpt    5   9058.091 ±  3430.143  ops/s
WriterBenchmark.ryuIntWriter           thrpt    5   4543.158 ±  4702.344  ops/s
WriterBenchmark.schubfachDoubleWriter  thrpt    5   8562.764 ±  2854.817  ops/s
WriterBenchmark.schubfachIntWriter     thrpt    5  15234.208 ±  4152.243  ops/s
```
