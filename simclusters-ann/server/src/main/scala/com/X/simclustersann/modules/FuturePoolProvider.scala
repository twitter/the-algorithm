package com.X.simclustersann.modules

import com.google.inject.Provides
import com.X.inject.XModule
import com.X.inject.annotations.Flag
import com.X.simclustersann.common.FlagNames.NumberOfThreads
import com.X.util.ExecutorServiceFuturePool
import java.util.concurrent.Executors
import javax.inject.Singleton
object FuturePoolProvider extends XModule {
  flag[Int](
    name = NumberOfThreads,
    default = 20,
    help = "The number of threads in the future pool."
  )

  @Singleton
  @Provides
  def providesFuturePool(
    @Flag(NumberOfThreads) numberOfThreads: Int
  ): ExecutorServiceFuturePool = {
    val threadPool = Executors.newFixedThreadPool(numberOfThreads)
    new ExecutorServiceFuturePool(threadPool) {
      override def toString: String = s"warmup-future-pool-$executor)"
    }
  }
}
