package com.fasterxml.jackson.core.io;

import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.regex.Pattern;

@BenchmarkMode(Mode.Throughput)
@Warmup(iterations = 5)
@Fork(1)
@Threads(1)
public abstract class BenchmarkLauncher {

    @Test
    public void launchBenchmarks() throws RunnerException {
        Options options = new OptionsBuilder()
                .include(Pattern.quote(getClass().getName()))
                .measurementIterations(15)
                .shouldFailOnError(true)
                .result("target/" + getClass().getSimpleName() + ".csv")
                .resultFormat(ResultFormatType.CSV)
                .build();
        new Runner(options).run();
    }

}
