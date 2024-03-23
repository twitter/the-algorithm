package com.ExTwitter.follow_recommendations.models

import com.ExTwitter.follow_recommendations.{thriftscala => t}
import com.ExTwitter.follow_recommendations.logging.{thriftscala => offline}
import com.ExTwitter.follow_recommendations.common.models.Recommendation
import com.ExTwitter.product_mixer.core.model.marshalling.HasMarshalling

case class RecommendationResponse(recommendations: Seq[Recommendation]) extends HasMarshalling {
  lazy val toThrift: t.RecommendationResponse =
    t.RecommendationResponse(recommendations.map(_.toThrift))

  lazy val toOfflineThrift: offline.OfflineRecommendationResponse =
    offline.OfflineRecommendationResponse(recommendations.map(_.toOfflineThrift))
}
