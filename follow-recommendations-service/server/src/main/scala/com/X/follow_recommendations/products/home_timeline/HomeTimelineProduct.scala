package com.X.follow_recommendations.products.home_timeline

import com.X.follow_recommendations.assembler.models.ActionConfig
import com.X.follow_recommendations.assembler.models.FollowedByUsersProof
import com.X.follow_recommendations.assembler.models.FooterConfig
import com.X.follow_recommendations.assembler.models.GeoContextProof
import com.X.follow_recommendations.assembler.models.HeaderConfig
import com.X.follow_recommendations.assembler.models.Layout
import com.X.follow_recommendations.assembler.models.TitleConfig
import com.X.follow_recommendations.assembler.models.UserListLayout
import com.X.follow_recommendations.assembler.models.UserListOptions
import com.X.follow_recommendations.common.base.BaseRecommendationFlow
import com.X.follow_recommendations.common.base.IdentityTransform
import com.X.follow_recommendations.common.base.Transform
import com.X.follow_recommendations.flows.ads.PromotedAccountsFlow
import com.X.follow_recommendations.flows.ads.PromotedAccountsFlowRequest
import com.X.follow_recommendations.blenders.PromotedAccountsBlender
import com.X.follow_recommendations.common.models.DisplayLocation
import com.X.follow_recommendations.common.models.Recommendation
import com.X.follow_recommendations.flows.post_nux_ml.PostNuxMlFlow
import com.X.follow_recommendations.flows.post_nux_ml.PostNuxMlRequestBuilder
import com.X.follow_recommendations.products.common.Product
import com.X.follow_recommendations.products.common.ProductRequest
import com.X.follow_recommendations.products.home_timeline.configapi.HomeTimelineParams._
import com.X.inject.Injector
import com.X.product_mixer.core.model.marshalling.request
import com.X.product_mixer.core.product.guice.ProductScope
import com.X.stitch.Stitch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeTimelineProduct @Inject() (
  postNuxMlFlow: PostNuxMlFlow,
  postNuxMlRequestBuilder: PostNuxMlRequestBuilder,
  promotedAccountsFlow: PromotedAccountsFlow,
  promotedAccountsBlender: PromotedAccountsBlender,
  productScope: ProductScope,
  injector: Injector,
) extends Product {

  override val name: String = "Home Timeline"

  override val identifier: String = "home-timeline"

  override val displayLocation: DisplayLocation = DisplayLocation.HomeTimeline

  override def selectWorkflows(
    request: ProductRequest
  ): Stitch[Seq[BaseRecommendationFlow[ProductRequest, _ <: Recommendation]]] = {
    postNuxMlRequestBuilder.build(request).map { postNuxMlRequest =>
      Seq(
        postNuxMlFlow.mapKey({ request: ProductRequest => postNuxMlRequest }),
        promotedAccountsFlow.mapKey(mkPromotedAccountsRequest))
    }
  }

  override val blender: Transform[ProductRequest, Recommendation] = {
    promotedAccountsBlender.mapTarget[ProductRequest](getMaxResults)
  }

  private val identityTransform = new IdentityTransform[ProductRequest, Recommendation]

  override def resultsTransformer(
    request: ProductRequest
  ): Stitch[Transform[ProductRequest, Recommendation]] = Stitch.value(identityTransform)

  override def enabled(request: ProductRequest): Stitch[Boolean] =
    Stitch.value(request.params(EnableProduct))

  override def layout: Option[Layout] = {
    productMixerProduct.map { product =>
      val homeTimelineStrings = productScope.let(product) {
        injector.instance[HomeTimelineStrings]
      }
      UserListLayout(
        header = Some(HeaderConfig(TitleConfig(homeTimelineStrings.whoToFollowModuleTitle))),
        userListOptions = UserListOptions(userBioEnabled = true, userBioTruncated = true, None),
        socialProofs = Some(
          Seq(
            FollowedByUsersProof(
              homeTimelineStrings.whoToFollowFollowedByManyUserSingleString,
              homeTimelineStrings.whoToFollowFollowedByManyUserDoubleString,
              homeTimelineStrings.whoToFollowFollowedByManyUserMultipleString
            ),
            GeoContextProof(homeTimelineStrings.whoToFollowPopularInCountryKey)
          )),
        footer = Some(
          FooterConfig(
            Some(ActionConfig(homeTimelineStrings.whoToFollowModuleFooter, "http://X.com"))))
      )
    }
  }

  override def productMixerProduct: Option[request.Product] = Some(HTLProductMixer)

  private[home_timeline] def mkPromotedAccountsRequest(
    req: ProductRequest
  ): PromotedAccountsFlowRequest = {
    PromotedAccountsFlowRequest(
      req.recommendationRequest.clientContext,
      req.params,
      req.recommendationRequest.displayLocation,
      None,
      req.recommendationRequest.excludedIds.getOrElse(Nil)
    )
  }

  private[home_timeline] def getMaxResults(req: ProductRequest): Int = {
    req.recommendationRequest.maxResults.getOrElse(
      req.params(DefaultMaxResults)
    )
  }
}
