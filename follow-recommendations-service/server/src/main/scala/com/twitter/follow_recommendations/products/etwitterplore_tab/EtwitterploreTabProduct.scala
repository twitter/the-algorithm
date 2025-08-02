package com.twitter.follow_recommendations.products.etwitterplore_tab

import com.twitter.follow_recommendations.common.base.BaseRecommendationFlow
import com.twitter.follow_recommendations.common.base.IdentityTransform
import com.twitter.follow_recommendations.common.base.Transform
import com.twitter.follow_recommendations.common.models.DisplayLocation
import com.twitter.follow_recommendations.common.models.Recommendation
import com.twitter.follow_recommendations.flows.post_nutwitter_ml.PostNutwitterMlFlow
import com.twitter.follow_recommendations.flows.post_nutwitter_ml.PostNutwitterMlRequestBuilder
import com.twitter.follow_recommendations.products.common.Product
import com.twitter.follow_recommendations.products.common.ProductRequest
import com.twitter.follow_recommendations.products.etwitterplore_tab.configapi.EtwitterploreTabParams
import com.twitter.stitch.Stitch
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class EtwitterploreTabProduct @Inject() (
  postNutwitterMlFlow: PostNutwitterMlFlow,
  postNutwitterMlRequestBuilder: PostNutwitterMlRequestBuilder)
    etwittertends Product {
  override val name: String = "Etwitterplore Tab"

  override val identifier: String = "etwitterplore-tab"

  override val displayLocation: DisplayLocation = DisplayLocation.EtwitterploreTab

  override def selectWorkflows(
    request: ProductRequest
  ): Stitch[Seq[BaseRecommendationFlow[ProductRequest, _ <: Recommendation]]] = {
    postNutwitterMlRequestBuilder.build(request).map { postNutwitterMlRequest =>
      Seq(postNutwitterMlFlow.mapKey({ _: ProductRequest => postNutwitterMlRequest }))
    }
  }

  override val blender: Transform[ProductRequest, Recommendation] =
    new IdentityTransform[ProductRequest, Recommendation]

  override def resultsTransformer(
    request: ProductRequest
  ): Stitch[Transform[ProductRequest, Recommendation]] =
    Stitch.value(new IdentityTransform[ProductRequest, Recommendation])

  override def enabled(request: ProductRequest): Stitch[Boolean] = {
    // Ideally we should hook up is_soft_user as custom FS field and disable the product through FS
    val enabledForUserType = !request.recommendationRequest.isSoftUser || request.params(
      EtwitterploreTabParams.EnableProductForSoftUser)
    Stitch.value(request.params(EtwitterploreTabParams.EnableProduct) && enabledForUserType)
  }
}
