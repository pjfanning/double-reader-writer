# double-reader-writer
Benchmark testing number reading/writing in Java. Relates to https://github.com/FasterXML/jackson-core/issues/577

So far, [FastDoubleParser](https://github.com/FasterXML/jackson-core/pull/747) looks useful if the parser results can be verified to be correct. The results for [RyuWriter](https://github.com/FasterXML/jackson-core/pull/749) seem more mixed. RyuWriter seems to have a major weak point dealing with whole numbers (int/long) but is faster than JDK for writing random doubles. The [Schubfach](https://github.com/pjfanning/double-reader-writer/issues/1) writer has similar results to RyuWriter but is much better with whole numbers.

Also, seems worthwhile to use newer JDKs regardless of whether that is with JDK read/writing or with the custom implementations.


## Zulu Java 17.0.3 Old Macbook

```
Benchmark                               Mode  Cnt       Score       Error  Units
ReaderBenchmark.fastDoubleLongReader   thrpt    5   21539.632 ±  3775.796  ops/s
ReaderBenchmark.fastDoubleReader       thrpt    5   23205.794 ±  5158.088  ops/s
ReaderBenchmark.fastFloatReader        thrpt    5   26081.041 ±  6600.205  ops/s
ReaderBenchmark.jdkDoubleReader        thrpt    5    2685.878 ±   901.350  ops/s
ReaderBenchmark.jdkFloatReader         thrpt    5    8087.257 ±  4846.451  ops/s
ReaderBenchmark.jdkLongReader          thrpt    5   23447.193 ±   320.989  ops/s
WriterBenchmark.jdkDoubleIntWriter     thrpt    5   26920.490 ±   652.261  ops/s
WriterBenchmark.jdkDoubleWriter        thrpt    5    3676.989 ±    47.458  ops/s
WriterBenchmark.jdkLongWriter          thrpt    5  113002.108 ±  1435.419  ops/s
WriterBenchmark.ryuDoubleWriter        thrpt    5   11283.349 ±   162.625  ops/s
WriterBenchmark.ryuIntWriter           thrpt    5    7250.325 ±  1008.664  ops/s
WriterBenchmark.schubfachDoubleWriter  thrpt    5    9271.144 ± 10743.030  ops/s
WriterBenchmark.schubfachIntWriter     thrpt    5   20106.119 ±  2386.457  ops/s
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
