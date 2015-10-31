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

import com.gs.collections.impl.factory.Sets;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class ContainsCollisionBenchmark {

  @State(Scope.Benchmark)
  public static class SetState {
    Set<String> gsSet;
    Set<String> jcfSet;
    String first;
    String last;


    @Setup
    public void setUp() {
      List<String> collisionList = Arrays.asList(Collisions.COLLISIONS2);
      this.gsSet = Sets.mutable.with(Collisions.COLLISIONS2);
      this.jcfSet = new HashSet<>(collisionList);
      this.first = Collisions.COLLISIONS2[0];
      this.last = Collisions.COLLISIONS2[Collisions.COLLISIONS2.length - 1];
    }
  }

  @Benchmark
  public boolean containsFirstJcf(SetState state) {
    return state.jcfSet.contains(state.first);
  }

  @Benchmark
  public boolean containsLastJcf(SetState state) {
    return state.jcfSet.contains(state.last);
  }

  @Benchmark
  public boolean containsFirstGs(SetState state) {
    return state.gsSet.contains(state.first);
  }

  @Benchmark
  public boolean containsLastGs(SetState state) {
    return state.gsSet.contains(state.last);
  }

}
