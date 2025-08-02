package com.twitter.search.earlybird;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.EtwitterecutorService;
import java.util.concurrent.Etwitterecutors;
import java.util.concurrent.RejectedEtwitterecutionEtwitterception;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolEtwitterecutor;
import java.util.concurrent.TimeUnit;

import scala.Function0;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import com.twitter.search.common.concurrent.ThreadPoolEtwitterecutorStats;
import com.twitter.search.common.metrics.SearchRateCounter;
import com.twitter.search.earlybird.common.config.EarlybirdProperty;
import com.twitter.util.EtwitterecutorServiceFuturePool;
import com.twitter.util.Future;
import com.twitter.util.FuturePool;

/**
 * A future pool that delegates all calls to an underlying futurePool, which can be recreated.
 */
public class EarlybirdFuturePoolManager implements FuturePool {
  private volatile EtwitterecutorServiceFuturePool pool = null;

  private final String threadName;
  private final ThreadPoolEtwitterecutorStats threadPoolEtwitterecutorStats;

  public EarlybirdFuturePoolManager(String threadName) {
    this.threadName = threadName;
    this.threadPoolEtwitterecutorStats = new ThreadPoolEtwitterecutorStats(threadName);
  }

  final synchronized void createUnderlyingFuturePool(int threadCount) {
    Preconditions.checkState(pool == null, "Cannot create a new pool before stopping the old one");

    EtwitterecutorService etwitterecutorService =
        createEtwitterecutorService(threadCount, getMatwitterQueueSize());
    if (etwitterecutorService instanceof ThreadPoolEtwitterecutor) {
      threadPoolEtwitterecutorStats.setUnderlyingEtwitterecutorForStats((ThreadPoolEtwitterecutor) etwitterecutorService);
    }

    pool = new EtwitterecutorServiceFuturePool(etwitterecutorService);
  }

  final synchronized void stopUnderlyingFuturePool(long timeout, TimeUnit timeunit)
      throws InterruptedEtwitterception {
    Preconditions.checkNotNull(pool);
    pool.etwitterecutor().shutdown();
    pool.etwitterecutor().awaitTermination(timeout, timeunit);
    pool = null;
  }

  boolean isPoolReady() {
    return pool != null;
  }

  @Override
  public final <T> Future<T> apply(Function0<T> f) {
    return Preconditions.checkNotNull(pool).apply(f);
  }

  @VisibleForTesting
  protected EtwitterecutorService createEtwitterecutorService(int threadCount, int matwitterQueueSize) {
    if (matwitterQueueSize <= 0) {
      return Etwitterecutors.newFitwitteredThreadPool(threadCount, createThreadFactory(threadName));
    }

    SearchRateCounter rejectedTaskCounter =
        SearchRateCounter.etwitterport(threadName + "_rejected_task_count");
    return new ThreadPoolEtwitterecutor(
        threadCount, threadCount, 0, TimeUnit.MILLISECONDS,
        new ArrayBlockingQueue<>(matwitterQueueSize),
        createThreadFactory(threadName),
        (runnable, etwitterecutor) -> {
          rejectedTaskCounter.increment();
          throw new RejectedEtwitterecutionEtwitterception(threadName + " queue is full");
        });
  }

  @VisibleForTesting
  protected int getMatwitterQueueSize() {
    return EarlybirdProperty.MAtwitter_QUEUE_SIZE.get(0);
  }

  @VisibleForTesting
  static ThreadFactory createThreadFactory(String threadName) {
    return new ThreadFactoryBuilder()
        .setNameFormat(threadName + "-%d")
        .setDaemon(true)
        .build();
  }

  @Override
  public int poolSize() {
    return Preconditions.checkNotNull(pool).poolSize();
  }

  @Override
  public int numActiveTasks() {
    return Preconditions.checkNotNull(pool).numActiveTasks();
  }

  @Override
  public long numCompletedTasks() {
    return Preconditions.checkNotNull(pool).numCompletedTasks();
  }


}
