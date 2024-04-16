package com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work. ron

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.ml.ap .DataRecord
 mport com.tw ter.ml.ap .Feature
 mport com.tw ter.summ ngb rd._
 mport com.tw ter.summ ngb rd.storm.Storm
 mport com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work.AggregateS ce
 mport java.lang.{Long => JLong}

/**
 * Use t  tra  to  mple nt onl ne summ ngb rd producer that subscr bes to
 * spouts and generates a data record.
 */
tra  StormAggregateS ce extends AggregateS ce {
  def na : Str ng

  def t  stampFeature: Feature[JLong]

  /**
   * Constructs t  storm Producer w h t   mple nted topology at runt  .
   */
  def bu ld(
    statsRece ver: StatsRece ver,
    jobConf g: RealT  AggregatesJobConf g
  ): Producer[Storm, DataRecord]
}
