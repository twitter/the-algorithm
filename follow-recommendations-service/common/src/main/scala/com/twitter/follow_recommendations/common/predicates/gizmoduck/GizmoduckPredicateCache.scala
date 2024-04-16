package com.tw ter.follow_recom ndat ons.common.pred cates.g zmoduck

 mport java.ut l.concurrent.T  Un 

 mport com.google.common.base.T cker
 mport com.google.common.cac .Cac Bu lder
 mport com.google.common.cac .Cac 
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.ut l.T  
 mport com.tw ter.ut l.Durat on

/**
 *  n- mory cac  used for cach ng G zmoduckPred cate query calls  n
 * com.tw ter.follow_recom ndat ons.common.pred cates.g zmoduck.G zmoduckPred cate.
 * 
 * References t  cac   mple ntat on  n com.tw ter.esc rb rd.ut l.st chcac ,
 * but w hout t  underly ng St ch call.
 */
object G zmoduckPred cateCac  {

  pr vate[G zmoduckPred cateCac ] class T  T cker extends T cker {
    overr de def read(): Long = T  .now. nNanoseconds
  }

  def apply[K, V](
    maxCac S ze:  nt,
    ttl: Durat on,
    statsRece ver: StatsRece ver
  ): Cac [K, V] = {

    val cac : Cac [K, V] =
      Cac Bu lder
        .newBu lder()
        .max mumS ze(maxCac S ze)
        .as nstanceOf[Cac Bu lder[K, V]]
        .exp reAfterWr e(ttl. nSeconds, T  Un .SECONDS)
        .recordStats()
        .t cker(new T  T cker())
        .bu ld()

    //  tr cs for track ng cac  usage
    statsRece ver.prov deGauge("cac _s ze") { cac .s ze.toFloat }
    statsRece ver.prov deGauge("cac _h s") { cac .stats.h Count.toFloat }
    statsRece ver.prov deGauge("cac _m sses") { cac .stats.m ssCount.toFloat }
    statsRece ver.prov deGauge("cac _h _rate") { cac .stats.h Rate.toFloat }
    statsRece ver.prov deGauge("cac _ev ct ons") { cac .stats.ev ct onCount.toFloat }

    cac 
  }
}
