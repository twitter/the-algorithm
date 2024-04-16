package com.tw ter.follow_recom ndat ons.common.cand date_s ces.stp
 mport com.tw ter.f nagle.stats.StatsRece ver
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
 mport com.tw ter.wtf.scald ng.jobs.strong_t e_pred ct on.STPRecord
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Onl neSTPS ceW hDeepb rdV2Scorer @ nject() (
  dbv2StpScorer: Dbv2StpScorer,
  stpGraphBu lder: STPGraphBu lder,
  baseStatRece ver: StatsRece ver)
    extends BaseOnl neSTPS ce(stpGraphBu lder, baseStatRece ver) {

  pr vate val dbv2ScorerUsedCounter = statsRece ver.counter("dbv2_scorer_used")
  pr vate val dbv2ScorerFa lureCounter = statsRece ver.counter("dbv2_scorer_fa lure")
  pr vate val dbv2ScorerSuccessCounter = statsRece ver.counter("dbv2_scorer_success")

  overr de def getCand dates(
    records: Seq[STPRecord],
    request: HasCl entContext w h HasParams w h HasRecentFollo dUser ds,
  ): St ch[Seq[Cand dateUser]] = {
    val poss bleCand dates: Seq[St ch[Opt on[Cand dateUser]]] = records.map { tra n ngRecord =>
      dbv2ScorerUsedCounter. ncr()
      val score = dbv2StpScorer.getScoredResponse(tra n ngRecord)
      score.map {
        case None =>
          dbv2ScorerFa lureCounter. ncr()
          None
        case So (scoreVal) =>
          dbv2ScorerSuccessCounter. ncr()
          So (
            Cand dateUser(
               d = tra n ngRecord.dest nat on d,
              score = So (Onl neSTPS ceW hDeepb rdV2Scorer.log Subtract on(scoreVal)),
              reason = So (
                Reason(So (
                  AccountProof(followProof =
                    So (FollowProof(tra n ngRecord.soc alProof, tra n ngRecord.soc alProof.s ze)))
                )))
            ).w hCand dateS ceAndFeatures(
               dent f er,
              Seq(StrongT eFeaturesWrapper(tra n ngRecord.features)))
          )
      }
    }
    St ch.collect(poss bleCand dates).map { _.flatten.sortBy(-_.score.getOrElse(0.0)) }
  }
}

object Onl neSTPS ceW hDeepb rdV2Scorer {
  // T  follow ng two var ables are t   ans for t  d str but on of scores com ng from t  legacy
  // and DBv2 Onl neSTP models.   need t  to cal brate t  DBv2 scores and al gn t  two  ans.
  // BQ L nk: https://console.cloud.google.com/b gquery?sq=213005704923:e06ac27e4db74385a77a4b538c531f82
  pr vate val legacy anScore = 0.0478208871192468
  pr vate val dbv2 anScore = 0.238666097210261

  //  n below are t  necessary funct ons to cal brate t  scores such that t   ans are al gned.
  pr vate val EPS: Double = 1e-8
  pr vate val e: Double = math.exp(1)
  pr vate def s gmo d(x: Double): Double = math.pow(e, x) / (math.pow(e, x) + 1)
  //   add an EPS to t  denom nator to avo d d v s on by 0.
  pr vate def log (x: Double): Double = math.log(x / (1 - x + EPS))
  def log Subtract on(x: Double): Double = s gmo d(
    log (x) - (log (dbv2 anScore) - log (legacy anScore)))
}
