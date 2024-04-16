package com.tw ter.follow_recom ndat ons.common.cand date_s ces.s ms

 mport com.tw ter.esc rb rd.ut l.st chcac .St chCac 
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.follow_recom ndat ons.common.models.Cand dateUser
 mport com.tw ter.follow_recom ndat ons.common.models.HasS m larToContext
 mport com.tw ter. rm .cand date.thr ftscala.Cand dates
 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.Cand dateS ce
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateS ce dent f er
 mport com.tw ter.st ch.St ch
 mport com.tw ter.strato.cl ent.Fetc r
 mport com.tw ter.t  l nes.conf gap .HasParams
 mport com.tw ter.ut l.Durat on

 mport java.lang.{Long => JLong}

class Cac BasedS msStore(
   d: Cand dateS ce dent f er,
  fetc r: Fetc r[Long, Un , Cand dates],
  maxCac S ze:  nt,
  cac Ttl: Durat on,
  statsRece ver: StatsRece ver)
    extends Cand dateS ce[HasParams w h HasS m larToContext, Cand dateUser] {

  overr de val  dent f er: Cand dateS ce dent f er =  d
  pr vate def getUsersFromS msS ce(user d: JLong): St ch[Opt on[Cand dates]] = {
    fetc r
      .fetch(user d)
      .map(_.v)
  }

  pr vate val s msCac  = St chCac [JLong, Opt on[Cand dates]](
    maxCac S ze = maxCac S ze,
    ttl = cac Ttl,
    statsRece ver = statsRece ver,
    underly ngCall = getUsersFromS msS ce
  )

  overr de def apply(request: HasParams w h HasS m larToContext): St ch[Seq[Cand dateUser]] = {
    St ch
      .traverse(request.s m larToUser ds) { user d =>
        s msCac .readThrough(user d).map { cand datesOpt =>
          cand datesOpt
            .map { cand dates =>
              StratoBasedS msCand dateS ce.map(user d, cand dates)
            }.getOrElse(N l)
        }
      }.map(_.flatten.d st nct.map(_.w hCand dateS ce( dent f er)))
  }
}
