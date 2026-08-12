# double-reader-writer
Benchmark testing number reading/writing in Java. Relates to https://github.com/FasterXML/jackson-core/issues/577

So far, [FastDoubleParser](https://github.com/FasterXML/jackson-core/pull/747) looks useful if the parser results can be verified to be correct. The results for [RyuWriter](https://github.com/FasterXML/jackson-core/pull/749) seem more mixed. RyuWriter seems to have a major weak point dealing with whole numbers (int/long) but is faster than JDK for writing random doubles. The [Schubfach](https://github.com/pjfanning/double-reader-writer/issues/1) writer has similar results to RyuWriter but is much better with whole numbers. Both Ryu and Schubfach may output string values that has small value differences from the equivalent JDK toString methods. Ryu and Schubfach have very similar results to each other.

Also, seems worthwhile to use newer JDKs regardless of whether that is with JDK read/writing or with the custom implementations.


## Temurin Java 17.0.19 Old Macbook

```
Benchmark                               Mode  Cnt       Score       Error  Units
Benchmark                               Mode  Cnt   Score   Error   Units
WriterBenchmark.jdkDoubleWriter        thrpt    3   3.360 ± 0.212  ops/ms
WriterBenchmark.ryuDoubleWriter        thrpt    3  10.690 ± 1.252  ops/ms
WriterBenchmark.schubfachDoubleWriter  thrpt    3  14.107 ± 7.175  ops/ms
WriterBenchmark.xjbDoubleWriter        thrpt    3  13.198 ± 7.947  ops/ms
```

## Temurin Java 25.0.3 Old Macbook

```
Benchmark                               Mode  Cnt      Score      Error  Units
Benchmark                               Mode  Cnt   Score   Error   Units
WriterBenchmark.jdkDoubleWriter        thrpt    3  14.323 ± 3.257  ops/ms
WriterBenchmark.ryuDoubleWriter        thrpt    3   9.351 ± 5.853  ops/ms
WriterBenchmark.schubfachDoubleWriter  thrpt    3  13.915 ± 0.990  ops/ms
WriterBenchmark.xjbDoubleWriter        thrpt    3  14.432 ± 1.301  ops/ms
```
