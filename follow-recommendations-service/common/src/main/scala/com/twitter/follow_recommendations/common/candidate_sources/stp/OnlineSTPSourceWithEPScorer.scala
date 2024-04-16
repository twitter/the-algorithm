package com.tw ter.follow_recom ndat ons.common.cand date_s ces.stp

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.stp.Onl neSTPS ceParams.SetPred ct onDeta ls
 mport com.tw ter.follow_recom ndat ons.common.models.AccountProof
 mport com.tw ter.follow_recom ndat ons.common.models.Cand dateUser
 mport com.tw ter.follow_recom ndat ons.common.models.FollowProof
 mport com.tw ter.follow_recom ndat ons.common.models.HasRecentFollo dUser ds
 mport com.tw ter.follow_recom ndat ons.common.models.Reason
 mport com.tw ter.onboard ng.relevance.features.strongt e.{
  StrongT eFeatures => StrongT eFeaturesWrapper
}
 mport com.tw ter.product_m xer.core.model.marshall ng.request.HasCl entContext
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t  l nes.conf gap .HasParams
 mport com.tw ter.ut l.logg ng.Logg ng
 mport com.tw ter.wtf.scald ng.jobs.strong_t e_pred ct on.STPRecord
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Onl neSTPS ceW hEPScorer @ nject() (
  epStpScorer: EpStpScorer,
  stpGraphBu lder: STPGraphBu lder,
  baseStatRece ver: StatsRece ver)
    extends BaseOnl neSTPS ce(stpGraphBu lder, baseStatRece ver)
    w h Logg ng {
  pr vate val epScorerUsedCounter = statsRece ver.counter("ep_scorer_used")

  overr de def getCand dates(
    records: Seq[STPRecord],
    request: HasCl entContext w h HasParams w h HasRecentFollo dUser ds,
  ): St ch[Seq[Cand dateUser]] = {
    epScorerUsedCounter. ncr()

    val poss bleCand dates: Seq[St ch[Opt on[Cand dateUser]]] = records.map { tra n ngRecord =>
      val scoredResponse =
        epStpScorer.getScoredResponse(tra n ngRecord.record, request.params(SetPred ct onDeta ls))
      scoredResponse.map(_.map { response: ScoredResponse =>
        logger.debug(response)
        Cand dateUser(
           d = tra n ngRecord.dest nat on d,
          score = So (response.score),
          reason = So (
            Reason(
              So (
                AccountProof(followProof =
                  So (FollowProof(tra n ngRecord.soc alProof, tra n ngRecord.soc alProof.s ze)))
              )))
        ).w hCand dateS ceAndFeatures(
           dent f er,
          Seq(StrongT eFeaturesWrapper(tra n ngRecord.features)))
      })
    }

    St ch.collect(poss bleCand dates).map { _.flatten.sortBy(-_.score.getOrElse(0.0)) }
  }
}
