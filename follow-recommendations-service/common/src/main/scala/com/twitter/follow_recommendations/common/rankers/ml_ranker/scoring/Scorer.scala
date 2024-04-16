package com.tw ter.follow_recom ndat ons.common.rankers.ml_ranker.scor ng

 mport com.tw ter.follow_recom ndat ons.common.models.Cand dateUser
 mport com.tw ter.follow_recom ndat ons.common.models.HasD splayLocat on
 mport com.tw ter.follow_recom ndat ons.common.models.HasDebugOpt ons
 mport com.tw ter.follow_recom ndat ons.common.models.Score
 mport com.tw ter.follow_recom ndat ons.common.models.ScoreType
 mport com.tw ter.follow_recom ndat ons.common.rankers.common.Ranker d
 mport com.tw ter.ml.ap .DataRecord
 mport com.tw ter.product_m xer.core.model.marshall ng.request.HasCl entContext
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t  l nes.conf gap .HasParams

tra  Scorer {

  // un que  d of t  scorer
  def  d: Ranker d.Value

  // type of t  output scores
  def scoreType: Opt on[ScoreType] = None

  // Scor ng w n an ML model  s used.
  def score(records: Seq[DataRecord]): St ch[Seq[Score]]

  /**
   * Scor ng w n a non-ML  thod  s appl ed. E.g: Boost ng, random zed reorder ng, etc.
   * T   thod assu s that cand dates' scores are already retr eved from  avy-ranker models and
   * are ava lable for use.
   */
  def score(
    target: HasCl entContext w h HasParams w h HasD splayLocat on w h HasDebugOpt ons,
    cand dates: Seq[Cand dateUser]
  ): Seq[Opt on[Score]]
}
