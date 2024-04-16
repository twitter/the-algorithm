package com.tw ter.follow_recom ndat ons.common.cl ents.d sm ss_store

 mport com.tw ter.follow_recom ndat ons.common.constants.Gu ceNa dConstants
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.onboard ng.relevance.store.thr ftscala.WhoToFollowD sm ssEventDeta ls
 mport com.tw ter.st ch.St ch
 mport com.tw ter.strato.catalog.Scan.Sl ce
 mport com.tw ter.strato.cl ent.Scanner
 mport com.tw ter.ut l.logg ng.Logg ng
 mport javax. nject. nject
 mport javax. nject.Na d
 mport javax. nject.S ngleton

/**
 * t  store gets t  l st of d sm ssed cand dates s nce a certa n t  
 * pr mar ly used for f lter ng out accounts that a user has expl c ly d sm ssed
 *
 *   fa l open on t  outs, but loudly on ot r errors
 */
@S ngleton
class D sm ssStore @ nject() (
  @Na d(Gu ceNa dConstants.D SM SS_STORE_SCANNER)
  scanner: Scanner[(Long, Sl ce[
      (Long, Long)
    ]), Un , (Long, (Long, Long)), WhoToFollowD sm ssEventDeta ls],
  stats: StatsRece ver)
    extends Logg ng {

  pr vate val MaxCand datesToReturn = 100

  // gets a l st of d sm ssed cand dates.  f numCand datesToFetchOpt on  s none,   w ll fetch t  default number of cand dates
  def get(
    user d: Long,
    negStartT  Ms: Long,
    maxCand datesToFetchOpt on: Opt on[ nt]
  ): St ch[Seq[Long]] = {

    val maxCand datesToFetch = maxCand datesToFetchOpt on.getOrElse(MaxCand datesToReturn)

    scanner
      .scan(
        (
          user d,
          Sl ce(
            from = None,
            to = So ((negStartT  Ms, Long.MaxValue)),
            l m  = So (maxCand datesToFetch)
          )
        )
      )
      .map {
        case s: Seq[((Long, (Long, Long)), WhoToFollowD sm ssEventDeta ls)]  f s.nonEmpty =>
          s.map {
            case ((_: Long, (_: Long, cand date d: Long)), _: WhoToFollowD sm ssEventDeta ls) =>
              cand date d
          }
        case _ => N l
      }
  }
}
