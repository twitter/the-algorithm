package com.tw ter.follow_recom ndat ons.common.cl ents. mpress on_store

 mport com.tw ter.follow_recom ndat ons.common.models.D splayLocat on
 mport com.tw ter.follow_recom ndat ons.common.models.Wtf mpress on
 mport com.tw ter.follow_recom ndat ons.thr ftscala.{D splayLocat on => TD splayLocat on}
 mport com.tw ter.st ch.St ch
 mport com.tw ter.strato.catalog.Scan.Sl ce
 mport com.tw ter.strato.cl ent.Scanner
 mport com.tw ter.ut l.T  
 mport com.tw ter.ut l.logg ng.Logg ng
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Wtf mpress onStore @ nject() (
  scanner: Scanner[
    ((Long, TD splayLocat on), Sl ce[Long]),
    Un ,
    ((Long, TD splayLocat on), Long),
    (Long,  nt)
  ]) extends Logg ng {
  def get(user d: Long, dl: D splayLocat on): St ch[Seq[Wtf mpress on]] = {
    val thr ftDl = dl.toThr ft
    scanner.scan(((user d, thr ftDl), Sl ce.all[Long])).map {  mpress onsPerDl =>
      val wtf mpress ons =
        for {
          (((_, _), cand date d), (latestTs, counts)) <-  mpress onsPerDl
        } y eld Wtf mpress on(
          cand date d = cand date d,
          d splayLocat on = dl,
          latestT   = T  .fromM ll seconds(latestTs),
          counts = counts
        )
      wtf mpress ons
    } rescue {
      // fa l open so that t  request can st ll go through
      case ex: Throwable =>
        logger.warn(s"$dl Wtf mpress onsStore warn: " + ex.get ssage)
        St ch.N l
    }
  }
}
