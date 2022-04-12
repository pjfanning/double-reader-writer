# double-reader-writer
Benchmark testing number reading/writing in Java. Relates to https://github.com/FasterXML/jackson-core/issues/577

So far, [FastDoubleParser](https://github.com/FasterXML/jackson-core/pull/747) looks useful if the parser results can be verified to be correct. The results for [RyuWriter](https://github.com/FasterXML/jackson-core/pull/749) seem more mixed. RyuWriter seems to have a major weak point dealing with whole numbers (int/long) but is faster than JDK for writing random doubles. The [Schubfach](https://github.com/pjfanning/double-reader-writer/issues/1) writer has similar results to RyuWriter but is much better with whole numbers.

Also, seems worthwhile to use newer JDKs regardless of whether that is with JDK read/writing or with the custom implementations.


## Azul Java 17.0.2 Old Macbook


```
Benchmark                            Mode  Cnt       Score      Error  Units
ReaderBenchmark.fastDoubleReader    thrpt    5   23582.740 ±  191.994  ops/s
ReaderBenchmark.jdkDoubleReader     thrpt    5    3900.926 ±  393.212  ops/s
WriterBenchmark.jdkDoubleIntWriter  thrpt    5   24154.571 ± 5798.525  ops/s
WriterBenchmark.jdkDoubleWriter     thrpt    5    3426.179 ±  149.114  ops/s
WriterBenchmark.jdkLongWriter       thrpt    5  108114.384 ± 3921.598  ops/s
WriterBenchmark.ryuDoubleWriter     thrpt    5   11079.164 ±  253.736  ops/s
WriterBenchmark.ryuIntWriter        thrpt    5    7430.936 ±   64.940  ops/s
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
