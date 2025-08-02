package com.twitter.follow_recommendations.products.sidebar

import com.twitter.follow_recommendations.common.base.BaseRecommendationFlow
import com.twitter.follow_recommendations.common.base.IdentityTransform
import com.twitter.follow_recommendations.common.base.Transform
import com.twitter.follow_recommendations.flows.ads.PromotedAccountsFlow
import com.twitter.follow_recommendations.flows.ads.PromotedAccountsFlowRequest
import com.twitter.follow_recommendations.blenders.PromotedAccountsBlender
import com.twitter.follow_recommendations.common.models.DisplayLocation
import com.twitter.follow_recommendations.common.models.Recommendation
import com.twitter.follow_recommendations.flows.post_nutwitter_ml.PostNutwitterMlFlow
import com.twitter.follow_recommendations.flows.post_nutwitter_ml.PostNutwitterMlRequestBuilder
import com.twitter.follow_recommendations.products.common.Product
import com.twitter.follow_recommendations.products.common.ProductRequest
import com.twitter.follow_recommendations.products.sidebar.configapi.SidebarParams
import com.twitter.stitch.Stitch
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class SidebarProduct @Inject() (
  postNutwitterMlFlow: PostNutwitterMlFlow,
  postNutwitterMlRequestBuilder: PostNutwitterMlRequestBuilder,
  promotedAccountsFlow: PromotedAccountsFlow,
  promotedAccountsBlender: PromotedAccountsBlender)
    etwittertends Product {
  override val name: String = "Sidebar"

  override val identifier: String = "sidebar"

  override val displayLocation: DisplayLocation = DisplayLocation.Sidebar

  override def selectWorkflows(
    request: ProductRequest
  ): Stitch[Seq[BaseRecommendationFlow[ProductRequest, _ <: Recommendation]]] = {
    postNutwitterMlRequestBuilder.build(request).map { postNutwitterMlRequest =>
      Seq(
        postNutwitterMlFlow.mapKey({ _: ProductRequest => postNutwitterMlRequest }),
        promotedAccountsFlow.mapKey(mkPromotedAccountsRequest)
      )
    }
  }

  override val blender: Transform[ProductRequest, Recommendation] = {
    promotedAccountsBlender.mapTarget[ProductRequest](getMatwitterResults)
  }

  private[sidebar] def mkPromotedAccountsRequest(
    req: ProductRequest
  ): PromotedAccountsFlowRequest = {
    PromotedAccountsFlowRequest(
      req.recommendationRequest.clientContetwittert,
      req.params,
      req.recommendationRequest.displayLocation,
      None,
      req.recommendationRequest.etwittercludedIds.getOrElse(Nil)
    )
  }

  private[sidebar] def getMatwitterResults(req: ProductRequest): Int = {
    req.recommendationRequest.matwitterResults.getOrElse(
      req.params(SidebarParams.DefaultMatwitterResults)
    )
  }

  override def resultsTransformer(
    request: ProductRequest
  ): Stitch[Transform[ProductRequest, Recommendation]] =
    Stitch.value(new IdentityTransform[ProductRequest, Recommendation])

  override def enabled(request: ProductRequest): Stitch[Boolean] =
    Stitch.value(request.params(SidebarParams.EnableProduct))
}
