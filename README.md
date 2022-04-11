# double-reader-writer
benchmark testing number reading/writing in Java

So far, [FastDoubleParser](https://github.com/FasterXML/jackson-core/pull/747) looks useful if the parser results can be verified to be correct. The results for [RyuWriter](https://github.com/FasterXML/jackson-core/pull/749) seem more mixed. RyuWriter seems to have a major weak point dealing with whole numbers (int/long) but is faster than JDK for writing random doubles.


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
