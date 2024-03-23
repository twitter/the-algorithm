package com.ExTwitter.follow_recommendations.products.home_timeline_tweet_recs

import com.ExTwitter.follow_recommendations.common.base.BaseRecommendationFlow
import com.ExTwitter.follow_recommendations.common.base.IdentityTransform
import com.ExTwitter.follow_recommendations.common.base.Transform
import com.ExTwitter.follow_recommendations.common.models.DisplayLocation
import com.ExTwitter.follow_recommendations.common.models.Recommendation
import com.ExTwitter.follow_recommendations.flows.content_recommender_flow.ContentRecommenderFlow
import com.ExTwitter.follow_recommendations.flows.content_recommender_flow.ContentRecommenderRequestBuilder
import com.ExTwitter.follow_recommendations.products.common.Product
import com.ExTwitter.follow_recommendations.products.common.ProductRequest
import com.ExTwitter.follow_recommendations.products.home_timeline_tweet_recs.configapi.HomeTimelineTweetRecsParams._
import com.ExTwitter.stitch.Stitch
import javax.inject.Inject
import javax.inject.Singleton

/*
 * This "DisplayLocation" is used to generate user recommendations using the ContentRecommenderFlow. These recommendations are later used downstream
 * to generate recommended tweets on Home Timeline.
 */
@Singleton
class HomeTimelineTweetRecsProduct @Inject() (
  contentRecommenderFlow: ContentRecommenderFlow,
  contentRecommenderRequestBuilder: ContentRecommenderRequestBuilder)
    extends Product {
  override val name: String = "Home Timeline Tweet Recs"

  override val identifier: String = "home-timeline-tweet-recs"

  override val displayLocation: DisplayLocation = DisplayLocation.HomeTimelineTweetRecs

  override def selectWorkflows(
    request: ProductRequest
  ): Stitch[Seq[BaseRecommendationFlow[ProductRequest, _ <: Recommendation]]] = {
    contentRecommenderRequestBuilder.build(request).map { contentRecommenderRequest =>
      Seq(contentRecommenderFlow.mapKey({ request: ProductRequest => contentRecommenderRequest }))
    }
  }

  override val blender: Transform[ProductRequest, Recommendation] =
    new IdentityTransform[ProductRequest, Recommendation]

  override def resultsTransformer(
    request: ProductRequest
  ): Stitch[Transform[ProductRequest, Recommendation]] =
    Stitch.value(new IdentityTransform[ProductRequest, Recommendation])

  override def enabled(request: ProductRequest): Stitch[Boolean] =
    Stitch.value(request.params(EnableProduct))
}
