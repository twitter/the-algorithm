package com.tw ter.recos njector.cl ents

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.logg ng.Logger
 mport com.tw ter.soc algraph.thr ftscala._
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.ut l.Future

class Soc alGraph(
  soc alGraph dStore: ReadableStore[ dsRequest,  dsResult]
)(
   mpl c  statsRece ver: StatsRece ver) {
   mport Soc alGraph._
  pr vate val log = Logger()

  pr vate val follo dByNotMutedByStats = statsRece ver.scope("follo dByNotMutedBy")

  pr vate def fetch dsFromSoc alGraph(
    user d: Long,
     ds: Seq[Long],
    relat onsh pTypes: Map[Relat onsh pType, Boolean],
    lookupContext: Opt on[LookupContext] =  nclude nact veUn onLookupContext,
    stats: StatsRece ver
  ): Future[Seq[Long]] = {
     f ( ds. sEmpty) {
      stats.counter("fetch dsEmpty"). ncr()
      Future.N l
    } else {
      val relat onsh ps = relat onsh pTypes.map {
        case (relat onsh pType, hasRelat onsh p) =>
          SrcRelat onsh p(
            s ce = user d,
            relat onsh pType = relat onsh pType,
            hasRelat onsh p = hasRelat onsh p,
            targets = So ( ds)
          )
      }.toSeq
      val  dsRequest =  dsRequest(
        relat onsh ps = relat onsh ps,
        pageRequest = SelectAllPageRequest,
        context = lookupContext
      )
      soc alGraph dStore
        .get( dsRequest)
        .map { _.map(_. ds).getOrElse(N l) }
        .rescue {
          case e =>
            stats.scope("fetch dsFa lure").counter(e.getClass.getS mpleNa ). ncr()
            log.error(s"Fa led w h  ssage ${e.toStr ng}")
            Future.N l
        }
    }
  }

  // wh ch of t  users  n cand dates follow user d and have not muted user d
  def follo dByNotMutedBy(user d: Long, cand dates: Seq[Long]): Future[Seq[Long]] = {
    fetch dsFromSoc alGraph(
      user d,
      cand dates,
      Follo dByNotMutedRelat onsh ps,
       nclude nact veLookupContext,
      follo dByNotMutedByStats
    )
  }

}

object Soc alGraph {
  val SelectAllPageRequest = So (PageRequest(selectAll = So (true)))

  val  nclude nact veLookupContext = So (LookupContext( nclude nact ve = true))
  val  nclude nact veUn onLookupContext = So (
    LookupContext( nclude nact ve = true, performUn on = So (true))
  )

  val Follo dByNotMutedRelat onsh ps: Map[Relat onsh pType, Boolean] = Map(
    Relat onsh pType.Follo dBy -> true,
    Relat onsh pType.MutedBy -> false
  )
}
