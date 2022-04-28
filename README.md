# double-reader-writer
Benchmark testing number reading/writing in Java. Relates to https://github.com/FasterXML/jackson-core/issues/577

So far, [FastDoubleParser](https://github.com/FasterXML/jackson-core/pull/747) looks useful if the parser results can be verified to be correct. The results for [RyuWriter](https://github.com/FasterXML/jackson-core/pull/749) seem more mixed. RyuWriter seems to have a major weak point dealing with whole numbers (int/long) but is faster than JDK for writing random doubles. The [Schubfach](https://github.com/pjfanning/double-reader-writer/issues/1) writer has similar results to RyuWriter but is much better with whole numbers.

Also, seems worthwhile to use newer JDKs regardless of whether that is with JDK read/writing or with the custom implementations.


## Zulu Java 17.0.2 Old Macbook

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

## Zulu Java 8.0.332 Old Macbook

```
Benchmark                               Mode  Cnt      Score      Error  Units
ReaderBenchmark.fastDoubleLongReader   thrpt    5  14313.322 ±  799.185  ops/s
ReaderBenchmark.fastDoubleReader       thrpt    5  13457.506 ± 5202.269  ops/s
ReaderBenchmark.fastFloatReader        thrpt    5  19794.857 ± 3111.309  ops/s
ReaderBenchmark.jdkDoubleReader        thrpt    5   1704.180 ± 2562.849  ops/s
ReaderBenchmark.jdkFloatReader         thrpt    5   3835.529 ± 6550.398  ops/s
ReaderBenchmark.jdkLongReader          thrpt    5  13130.709 ± 3968.561  ops/s
WriterBenchmark.jdkDoubleIntWriter     thrpt    5  25296.327 ± 3358.200  ops/s
WriterBenchmark.jdkDoubleWriter        thrpt    5   3260.515 ±  190.571  ops/s
WriterBenchmark.jdkLongWriter          thrpt    5  42692.502 ± 5502.388  ops/s
WriterBenchmark.ryuDoubleWriter        thrpt    5  10026.740 ± 8484.169  ops/s
WriterBenchmark.ryuIntWriter           thrpt    5   6385.586 ±  819.930  ops/s
WriterBenchmark.schubfachDoubleWriter  thrpt    5   9359.569 ±  814.004  ops/s
WriterBenchmark.schubfachIntWriter     thrpt    5  15515.109 ± 1588.893  ops/s
```
