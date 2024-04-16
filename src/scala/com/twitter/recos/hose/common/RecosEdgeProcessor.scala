package com.tw ter.recos.hose.common

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.recos. nternal.thr ftscala.RecosHose ssage
 mport com.tw ter.ut l.Future
 mport org.apac .kafka.cl ents.consu r.Consu rRecord

/**
 * T  class processes RecosHose ssage and  nserts t   ssage as an edge  nto a recos graph.
 */
case class RecosEdgeProcessor(
  edgeCollector: EdgeCollector
)(
   mpl c  statsRece ver: StatsRece ver) {

  pr vate val scopedStats = statsRece ver.scope("RecosEdgeProcessor")

  pr vate val processEventsCounter = scopedStats.counter("process_events")
  pr vate val nullPo nterEventCounter = scopedStats.counter("null_po nter_num")
  pr vate val errorCounter = scopedStats.counter("process_errors")

  def process(record: Consu rRecord[Str ng, RecosHose ssage]): Future[Un ] = {
    processEventsCounter. ncr()
    val  ssage = record.value()
    try {
      // t   ssage  s nullable
       f ( ssage != null) {
        edgeCollector.addEdge( ssage)
      } else {
        nullPo nterEventCounter. ncr()
      }
      Future.Un 
    } catch {
      case e: Throwable =>
        errorCounter. ncr()
        e.pr ntStackTrace()
        Future.Un 
    }
  }

}
