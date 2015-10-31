package com.github.marschall.hashcollisions;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import com.gs.collections.impl.factory.Sets;

//@BenchmarkMode(Mode.AverageTime)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class AddCollisionBenchmark {

  public static void main(String[] args) throws RunnerException {
    Options options = new OptionsBuilder()
      .include(".*AddCollisionBenchmark.*")
      .warmupIterations(10)
      .measurementIterations(10)
      .resultFormat(ResultFormatType.TEXT)
//      .jvmArgsPrepend("-XX:+UnlockCommercialFeatures", "-XX:+FlightRecorder")
      .build();

    new Runner(options).run();
//    System.out.println(options.getResult().get());
  }

  @State(Scope.Benchmark)
  public static class SetState {
    List<String> collisionList;

    @Setup
    public void setUp() {
      this.collisionList = Arrays.asList(Collisions.COLLISIONS);
    }

  }


  @Benchmark
  public Set<Object> addJcf() {
    Set<Object> set = new HashSet<>();
    for (String string : Collisions.COLLISIONS) {
      set.add(string);
    }
    return set;
  }

  @Benchmark
  public Set<Object> addAllJcf(SetState state) {
    Set<Object> set = new HashSet<>();
    set.addAll(state.collisionList);
    return set;
  }

  @Benchmark
  public Set<Object> addGs() {
    Set<Object> set = Sets.mutable.empty();
    for (String string : Collisions.COLLISIONS) {
      set.add(string);
    }
    return set;
  }

  @Benchmark
  public Set<Object> addAllGs(SetState state) {
    Set<Object> set = Sets.mutable.empty();
    set.addAll(state.collisionList);
    return set;
  }

}
