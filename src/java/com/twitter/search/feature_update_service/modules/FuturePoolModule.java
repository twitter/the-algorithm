package com.twitter.search.feature_update_service.modules;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolEtwitterecutor;
import java.util.concurrent.TimeUnit;

import com.google.common.annotations.VisibleForTesting;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import com.twitter.inject.TwitterModule;
import com.twitter.search.common.metrics.SearchCustomGauge;
import com.twitter.search.feature_update_service.stats.FeatureUpdateStats;
import com.twitter.util.EtwitterecutorServiceFuturePool;
import com.twitter.util.InterruptibleEtwitterecutorServiceFuturePool;

public class FuturePoolModule etwittertends TwitterModule {
  /**
   * Provide future pool backed by etwitterecutor service, with bounded thread pool and bounded backing
   * queue.
   */
  @Provides
  @Singleton
  public EtwitterecutorServiceFuturePool futurePool() {
    // These limits are based on service capacity estimates and testing on staging,
    // attempting to give the pool as many resources as possible without overloading anything.
    // 100-200 threads is manageable, and the 2000 queue size is based on a conservative upper
    // limit that tasks in the queue take 1 MB each, meaning queue matwitteres out at 2 GB, which should
    // be okay given 4 GB RAM with 3 GB reserved heap.
    return createFuturePool(100, 200, 2000);
  }

  /**
   * Create a future pool backed by etwitterecutor service, with bounded thread pool and bounded backing
   * queue. ONLY VISIBILE FOR TESTING; don't invoke outside this class.
   */
  @VisibleForTesting
  public static EtwitterecutorServiceFuturePool createFuturePool(
      int corePoolSize, int matwitterimumPoolSize, int queueCapacity) {
    final LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(queueCapacity);

    EtwitterecutorServiceFuturePool futurePool = new InterruptibleEtwitterecutorServiceFuturePool(
        new ThreadPoolEtwitterecutor(
            corePoolSize,
            matwitterimumPoolSize,
            60L,
            TimeUnit.SECONDS,
            queue));

    SearchCustomGauge.etwitterport(FeatureUpdateStats.PREFItwitter + "thread_pool_size",
        futurePool::poolSize);
    SearchCustomGauge.etwitterport(FeatureUpdateStats.PREFItwitter + "work_queue_size",
        queue::size);

    return futurePool;
  }
}
