package com.tw ter.s mclustersann.modules

 mport com.google.common.ut l.concurrent.RateL m er
 mport com.google. nject.Prov des
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter. nject.annotat ons.Flag
 mport com.tw ter.s mclustersann.common.FlagNa s.RateL m erQPS
 mport javax. nject.S ngleton

object RateL m erModule extends Tw terModule {
  flag[ nt](
    na  = RateL m erQPS,
    default = 1000,
     lp = "T  QPS allo d by t  rate l m er."
  )

  @S ngleton
  @Prov des
  def prov desRateL m er(
    @Flag(RateL m erQPS) rateL m erQps:  nt
  ): RateL m er =
    RateL m er.create(rateL m erQps)
}
