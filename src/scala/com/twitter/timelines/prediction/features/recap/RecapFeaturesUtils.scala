package com.tw ter.t  l nes.pred ct on.features.recap

object RecapFeaturesUt ls {
  // T  needs to be updated  f an engage nt model  s added or removed from pred ct on serv ce.
  val scoreFeature dsMap: Map[Str ng, Long] = Map(
    RecapFeatures. S_FAVOR TED.getFeatureNa  -> RecapFeatures.PRED CTED_ S_FAVOR TED.getFeature d,
    RecapFeatures. S_REPL ED.getFeatureNa  -> RecapFeatures.PRED CTED_ S_REPL ED.getFeature d,
    RecapFeatures. S_RETWEETED.getFeatureNa  -> RecapFeatures.PRED CTED_ S_RETWEETED.getFeature d,
    RecapFeatures. S_GOOD_CL CKED_CONVO_DESC_V1.getFeatureNa  -> RecapFeatures.PRED CTED_ S_GOOD_CL CKED_V1.getFeature d,
    RecapFeatures. S_GOOD_CL CKED_CONVO_DESC_V2.getFeatureNa  -> RecapFeatures.PRED CTED_ S_GOOD_CL CKED_V2.getFeature d,
//    RecapFeatures. S_NEGAT VE_FEEDBACK_V2.getFeatureNa  -> RecapFeatures.PRED CTED_ S_NEGAT VE_FEEDBACK_V2.getFeature d,
    RecapFeatures. S_PROF LE_CL CKED_AND_PROF LE_ENGAGED.getFeatureNa  -> RecapFeatures.PRED CTED_ S_PROF LE_CL CKED_AND_PROF LE_ENGAGED.getFeature d,
    RecapFeatures. S_REPL ED_REPLY_ENGAGED_BY_AUTHOR.getFeatureNa  -> RecapFeatures.PRED CTED_ S_REPL ED_REPLY_ENGAGED_BY_AUTHOR.getFeature d
  )

  // T  needs to be updated  f an engage nt model  s added or removed from pred ct on serv ce.
  val labelFeature dToScoreFeature dsMap: Map[Long, Long] = Map(
    RecapFeatures. S_FAVOR TED.getFeature d -> RecapFeatures.PRED CTED_ S_FAVOR TED.getFeature d,
    RecapFeatures. S_REPL ED.getFeature d -> RecapFeatures.PRED CTED_ S_REPL ED.getFeature d,
    RecapFeatures. S_RETWEETED.getFeature d -> RecapFeatures.PRED CTED_ S_RETWEETED.getFeature d,
    RecapFeatures. S_GOOD_CL CKED_CONVO_DESC_V1.getFeature d -> RecapFeatures.PRED CTED_ S_GOOD_CL CKED_V1.getFeature d,
    RecapFeatures. S_GOOD_CL CKED_CONVO_DESC_V2.getFeature d -> RecapFeatures.PRED CTED_ S_GOOD_CL CKED_V2.getFeature d,
    //    RecapFeatures. S_NEGAT VE_FEEDBACK_V2.getFeatureNa  -> RecapFeatures.PRED CTED_ S_NEGAT VE_FEEDBACK_V2.getFeature d,
    RecapFeatures. S_PROF LE_CL CKED_AND_PROF LE_ENGAGED.getFeature d -> RecapFeatures.PRED CTED_ S_PROF LE_CL CKED_AND_PROF LE_ENGAGED.getFeature d,
    RecapFeatures. S_REPL ED_REPLY_ENGAGED_BY_AUTHOR.getFeature d -> RecapFeatures.PRED CTED_ S_REPL ED_REPLY_ENGAGED_BY_AUTHOR.getFeature d
  )

  val labelFeatureNa s: Seq[Str ng] = scoreFeature dsMap.keys.toSeq
}
