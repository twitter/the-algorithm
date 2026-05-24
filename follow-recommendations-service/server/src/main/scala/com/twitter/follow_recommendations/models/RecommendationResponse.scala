package com.twitter.follow_recommendations.models

import com.twitter.follow_recommendations.{thriftscala => t}
import com.twitter.follow_recommendations.logging.{thriftscala => offline}
import com.twitter.follow_recommendations.common.models.Recommendation
import com.twitter.product_mixer.core.model.marshalling.HasMarshalling

case class RecommendationResponse(recommendations: Seq[Recommendation]) extends HasMarshalling {
  lazy val toThrift: t.RecommendationResponse =
    t.RecommendationResponse(recommendations.map(_.toThrift))

  lazy val toOfflineThrift: offline.OfflineRecommendationResponse =
    offline.OfflineRecommendationResponse(recommendations.map(_.toOfflineThrift))
}
