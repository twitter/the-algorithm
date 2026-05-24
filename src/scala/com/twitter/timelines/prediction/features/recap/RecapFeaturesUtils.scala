package com.twitter.timelines.prediction.features.recap

object RecapFeaturesUtils {
  // This needs to be updated if an engagement model is added or removed from prediction service.
  val scoreFeatureIdsMap: Map[String, Long] = Map(
    RecapFeatures.IS_FAVORITED.getFeatureName -> RecapFeatures.PREDICTED_IS_FAVORITED.getFeatureId,
    RecapFeatures.IS_REPLIED.getFeatureName -> RecapFeatures.PREDICTED_IS_REPLIED.getFeatureId,
    RecapFeatures.IS_RETWEETED.getFeatureName -> RecapFeatures.PREDICTED_IS_RETWEETED.getFeatureId,
    RecapFeatures.IS_GOOD_CLICKED_CONVO_DESC_V1.getFeatureName -> RecapFeatures.PREDICTED_IS_GOOD_CLICKED_V1.getFeatureId,
    RecapFeatures.IS_GOOD_CLICKED_CONVO_DESC_V2.getFeatureName -> RecapFeatures.PREDICTED_IS_GOOD_CLICKED_V2.getFeatureId,
//    RecapFeatures.IS_NEGATIVE_FEEDBACK_V2.getFeatureName -> RecapFeatures.PREDICTED_IS_NEGATIVE_FEEDBACK_V2.getFeatureId,
    RecapFeatures.IS_PROFILE_CLICKED_AND_PROFILE_ENGAGED.getFeatureName -> RecapFeatures.PREDICTED_IS_PROFILE_CLICKED_AND_PROFILE_ENGAGED.getFeatureId,
    RecapFeatures.IS_REPLIED_REPLY_ENGAGED_BY_AUTHOR.getFeatureName -> RecapFeatures.PREDICTED_IS_REPLIED_REPLY_ENGAGED_BY_AUTHOR.getFeatureId
  )

  // This needs to be updated if an engagement model is added or removed from prediction service.
  val labelFeatureIdToScoreFeatureIdsMap: Map[Long, Long] = Map(
    RecapFeatures.IS_FAVORITED.getFeatureId -> RecapFeatures.PREDICTED_IS_FAVORITED.getFeatureId,
    RecapFeatures.IS_REPLIED.getFeatureId -> RecapFeatures.PREDICTED_IS_REPLIED.getFeatureId,
    RecapFeatures.IS_RETWEETED.getFeatureId -> RecapFeatures.PREDICTED_IS_RETWEETED.getFeatureId,
    RecapFeatures.IS_GOOD_CLICKED_CONVO_DESC_V1.getFeatureId -> RecapFeatures.PREDICTED_IS_GOOD_CLICKED_V1.getFeatureId,
    RecapFeatures.IS_GOOD_CLICKED_CONVO_DESC_V2.getFeatureId -> RecapFeatures.PREDICTED_IS_GOOD_CLICKED_V2.getFeatureId,
    //    RecapFeatures.IS_NEGATIVE_FEEDBACK_V2.getFeatureName -> RecapFeatures.PREDICTED_IS_NEGATIVE_FEEDBACK_V2.getFeatureId,
    RecapFeatures.IS_PROFILE_CLICKED_AND_PROFILE_ENGAGED.getFeatureId -> RecapFeatures.PREDICTED_IS_PROFILE_CLICKED_AND_PROFILE_ENGAGED.getFeatureId,
    RecapFeatures.IS_REPLIED_REPLY_ENGAGED_BY_AUTHOR.getFeatureId -> RecapFeatures.PREDICTED_IS_REPLIED_REPLY_ENGAGED_BY_AUTHOR.getFeatureId
  )

  val labelFeatureNames: Seq[String] = scoreFeatureIdsMap.keys.toSeq
}
