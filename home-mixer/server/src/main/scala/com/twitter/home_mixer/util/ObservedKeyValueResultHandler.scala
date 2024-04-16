package com.tw ter.ho _m xer.ut l

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.servo.keyvalue.KeyValueResult
 mport com.tw ter.ut l.Return
 mport com.tw ter.ut l.Throw
 mport com.tw ter.ut l.Try

tra  ObservedKeyValueResultHandler {
  val statsRece ver: StatsRece ver
  val statScope: Str ng

  pr vate lazy val scopedStatsRece ver = statsRece ver.scope(statScope)
  pr vate lazy val keyTotalCounter = scopedStatsRece ver.counter("key/total")
  pr vate lazy val keyFoundCounter = scopedStatsRece ver.counter("key/found")
  pr vate lazy val keyLossCounter = scopedStatsRece ver.counter("key/loss")
  pr vate lazy val keyFa lureCounter = scopedStatsRece ver.counter("key/fa lure")

  def observedGet[K, V](
    key: Opt on[K],
    keyValueResult: KeyValueResult[K, V],
  ): Try[Opt on[V]] = {
     f (key.nonEmpty) {
      keyTotalCounter. ncr()
      keyValueResult(key.get) match {
        case Return(So (value)) =>
          keyFoundCounter. ncr()
          Return(So (value))
        case Return(None) =>
          keyLossCounter. ncr()
          Return(None)
        case Throw(except on) =>
          keyFa lureCounter. ncr()
          Throw(except on)
        case _ =>
          // never reac s  re
          Return(None)
      }
    } else {
      Throw(M ss ngKeyExcept on)
    }
  }
}
