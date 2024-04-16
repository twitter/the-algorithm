package com.tw ter.s mclustersann.modules

 mport com.google. nject.Prov des
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter. nject.annotat ons.Flag
 mport com.tw ter.s mclustersann.common.FlagNa s.NumberOfThreads
 mport com.tw ter.ut l.ExecutorServ ceFuturePool
 mport java.ut l.concurrent.Executors
 mport javax. nject.S ngleton
object FuturePoolProv der extends Tw terModule {
  flag[ nt](
    na  = NumberOfThreads,
    default = 20,
     lp = "T  number of threads  n t  future pool."
  )

  @S ngleton
  @Prov des
  def prov desFuturePool(
    @Flag(NumberOfThreads) numberOfThreads:  nt
  ): ExecutorServ ceFuturePool = {
    val threadPool = Executors.newF xedThreadPool(numberOfThreads)
    new ExecutorServ ceFuturePool(threadPool) {
      overr de def toStr ng: Str ng = s"warmup-future-pool-$executor)"
    }
  }
}
