package com.tw ter.follow_recom ndat ons.common.rankers.ml_ranker.scor ng

 mport com.tw ter.follow_recom ndat ons.common.rankers.common.AdhocScoreMod f cat onType.AdhocScoreMod f cat onType
 mport com.tw ter.follow_recom ndat ons.common.models.Score
 mport com.tw ter.ml.ap .DataRecord
 mport com.tw ter.st ch.St ch

tra  AdhocScorer extends Scorer {

  /**
   * NOTE: For  nstances of [[AdhocScorer]] t  funct on SHOULD NOT be used.
   * Please use:
   *   [[score(target: HasCl entContext w h HasParams, cand dates: Seq[Cand dateUser])]]
   *  nstead.
   */
  @Deprecated
  overr de def score(records: Seq[DataRecord]): St ch[Seq[Score]] =
    throw new UnsupportedOperat onExcept on(
      "For  nstances of AdhocScorer t  operat on  s not def ned. Please use " +
        "`def score(target: HasCl entContext w h HasParams, cand dates: Seq[Cand dateUser])` " +
        " nstead.")

  /**
   * T   lps us manage t  extend of adhoc mod f cat on on cand dates' score. T re  s a hard
   * l m  of apply ng ONLY ONE scorer of each type to a score.
   */
  val scoreMod f cat onType: AdhocScoreMod f cat onType
}
