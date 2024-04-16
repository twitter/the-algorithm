package com.tw ter.follow_recom ndat ons.common.rankers.common

/**
 * To manage t  extent of adhoc score mod f cat ons,   set a hard l m  that from each of t 
 * types below *ONLY ONE* adhoc scorer can be appl ed to cand dates' scores. More deta ls about t 
 * usage  s ava lable  n [[AdhocRanker]]
 */

object AdhocScoreMod f cat onType extends Enu rat on {
  type AdhocScoreMod f cat onType = Value

  // T  type of scorer  ncreases t  score of a subset of cand dates through var ous pol c es.
  val Boost ngScorer: AdhocScoreMod f cat onType = Value("boost ng")

  // T  type of scorer shuffles cand dates randomly accord ng to so  d str but on.
  val   ghtedRandomSampl ngScorer: AdhocScoreMod f cat onType = Value("  ghted_random_sampl ng")

  // T   s added solely for test ng purposes and should not be used  n product on.
  val  nval dAdhocScorer: AdhocScoreMod f cat onType = Value(" nval d_adhoc_scorer")
}
