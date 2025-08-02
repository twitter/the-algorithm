package com.twitter.simclustersann.modules

import com.google.inject.Provides
import com.twitter.inject.TwitterModule
import com.twitter.inject.annotations.Flag
import com.twitter.simclustersann.common.FlagNames.NumberOfThreads
import com.twitter.util.EtwitterecutorServiceFuturePool
import java.util.concurrent.Etwitterecutors
import javatwitter.inject.Singleton
object FuturePoolProvider etwittertends TwitterModule {
  flag[Int](
    name = NumberOfThreads,
    default = 20,
    help = "The number of threads in the future pool."
  )

  @Singleton
  @Provides
  def providesFuturePool(
    @Flag(NumberOfThreads) numberOfThreads: Int
  ): EtwitterecutorServiceFuturePool = {
    val threadPool = Etwitterecutors.newFitwitteredThreadPool(numberOfThreads)
    new EtwitterecutorServiceFuturePool(threadPool) {
      override def toString: String = s"warmup-future-pool-$etwitterecutor)"
    }
  }
}
