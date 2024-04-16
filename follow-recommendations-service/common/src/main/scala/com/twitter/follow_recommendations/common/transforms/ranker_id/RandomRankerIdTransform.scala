package com.tw ter.follow_recom ndat ons.common.transforms.ranker_ d

 mport com.google. nject. nject
 mport com.google. nject.S ngleton
 mport com.tw ter.follow_recom ndat ons.common.base.GatedTransform
 mport com.tw ter.follow_recom ndat ons.common.models.Cand dateUser
 mport com.tw ter.follow_recom ndat ons.common.models.Score
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t  l nes.conf gap .HasParams

/**
 * T  class appends each cand date's ranker ds w h t  RandomRanker d.
 * T   s pr mar ly for determ n ng  f a cand date was generated v a random shuffl ng.
 */
@S ngleton
class RandomRanker dTransform @ nject() () extends GatedTransform[HasParams, Cand dateUser] {

  overr de def transform(
    target: HasParams,
    cand dates: Seq[Cand dateUser]
  ): St ch[Seq[Cand dateUser]] = {
    St ch.value(cand dates.map(_.addScore(Score.RandomScore)))
  }
}
