package com.tw ter.recos.hose.common

 mport com.tw ter.f nagle.stats.{Stat, StatsRece ver}
 mport com.tw ter.recos. nternal.thr ftscala.RecosHose ssage
 mport java.ut l.concurrent.Semaphore

tra  EdgeCollector {
  def addEdge( ssage: RecosHose ssage): Un 
}

/**
 * T  class consu s  ncom ng edges and  nserts t m  nto a buffer of a spec f ed bufferS ze.
 * Once t  buffer  s full of edges,    s wr ten to a concurrently l nked queue w re t  s ze  s bounded by queuel m .
 */
case class BufferedEdgeCollector(
  bufferS ze:  nt,
  queue: java.ut l.Queue[Array[RecosHose ssage]],
  queuel m : Semaphore,
  statsRece ver: StatsRece ver)
    extends EdgeCollector {

  pr vate var buffer = new Array[RecosHose ssage](bufferS ze)
  pr vate var  ndex = 0
  pr vate val queueAddCounter = statsRece ver.counter("queueAdd")

  overr de def addEdge( ssage: RecosHose ssage): Un  = {
    buffer( ndex) =  ssage
     ndex =  ndex + 1
     f ( ndex >= bufferS ze) {
      val oldBuffer = buffer
      buffer = new Array[RecosHose ssage](bufferS ze)
       ndex = 0

      Stat.t  (statsRece ver.stat("wa Enqueue")) {
        queuel m .acqu reUn nterrupt bly()
      }

      queue.add(oldBuffer)
      queueAddCounter. ncr()
    }
  }
}
