package com.tw ter.un f ed_user_act ons.adapter

 mport com.tw ter.f nagle.stats.NullStatsRece ver
 mport com.tw ter.f nagle.stats.StatsRece ver

tra  AbstractAdapter[ NPUT, OUTK, OUTV] extends Ser al zable {

  /**
   * T  bas c  nput -> seq[output] adapter wh ch concrete adapters should extend from
   * @param  nput a s ngle  NPUT
   * @return A l st of (OUTK, OUTV) tuple. T  OUTK  s t  output key ma nly for publ sh ng to Kafka (or Pubsub).
   *          f ot r process ng, e.g. offl ne batch process ng, doesn't requ re t  output key t n   can drop  
   *         l ke s ce.adaptOneToKeyedMany.map(_._2)
   */
  def adaptOneToKeyedMany(
     nput:  NPUT,
    statsRece ver: StatsRece ver = NullStatsRece ver
  ): Seq[(OUTK, OUTV)]
}
