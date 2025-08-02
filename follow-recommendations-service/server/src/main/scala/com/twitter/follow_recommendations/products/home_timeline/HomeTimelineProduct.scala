package com.twitter.follow_recommendations.products.home_timeline

import com.twitter.follow_recommendations.assembler.models.ActionConfig
import com.twitter.follow_recommendations.assembler.models.FollowedByUsersProof
import com.twitter.follow_recommendations.assembler.models.FooterConfig
import com.twitter.follow_recommendations.assembler.models.GeoContetwittertProof
import com.twitter.follow_recommendations.assembler.models.HeaderConfig
import com.twitter.follow_recommendations.assembler.models.Layout
import com.twitter.follow_recommendations.assembler.models.TitleConfig
import com.twitter.follow_recommendations.assembler.models.UserListLayout
import com.twitter.follow_recommendations.assembler.models.UserListOptions
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
import com.twitter.follow_recommendations.products.home_timeline.configapi.HomeTimelineParams._
import com.twitter.inject.Injector
import com.twitter.product_mitwitterer.core.model.marshalling.request
import com.twitter.product_mitwitterer.core.product.guice.ProductScope
import com.twitter.stitch.Stitch
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class HomeTimelineProduct @Inject() (
  postNutwitterMlFlow: PostNutwitterMlFlow,
  postNutwitterMlRequestBuilder: PostNutwitterMlRequestBuilder,
  promotedAccountsFlow: PromotedAccountsFlow,
  promotedAccountsBlender: PromotedAccountsBlender,
  productScope: ProductScope,
  injector: Injector,
) etwittertends Product {

  override val name: String = "Home Timeline"

  override val identifier: String = "home-timeline"

  override val displayLocation: DisplayLocation = DisplayLocation.HomeTimeline

  override def selectWorkflows(
    request: ProductRequest
  ): Stitch[Seq[BaseRecommendationFlow[ProductRequest, _ <: Recommendation]]] = {
    postNutwitterMlRequestBuilder.build(request).map { postNutwitterMlRequest =>
      Seq(
        postNutwitterMlFlow.mapKey({ request: ProductRequest => postNutwitterMlRequest }),
        promotedAccountsFlow.mapKey(mkPromotedAccountsRequest))
    }
  }

  override val blender: Transform[ProductRequest, Recommendation] = {
    promotedAccountsBlender.mapTarget[ProductRequest](getMatwitterResults)
  }

  private val identityTransform = new IdentityTransform[ProductRequest, Recommendation]

  override def resultsTransformer(
    request: ProductRequest
  ): Stitch[Transform[ProductRequest, Recommendation]] = Stitch.value(identityTransform)

  override def enabled(request: ProductRequest): Stitch[Boolean] =
    Stitch.value(request.params(EnableProduct))

  override def layout: Option[Layout] = {
    productMitwittererProduct.map { product =>
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
            GeoContetwittertProof(homeTimelineStrings.whoToFollowPopularInCountryKey)
          )),
        footer = Some(
          FooterConfig(
            Some(ActionConfig(homeTimelineStrings.whoToFollowModuleFooter, "http://twitter.com"))))
      )
    }
  }

  override def productMitwittererProduct: Option[request.Product] = Some(HTLProductMitwitterer)

  private[home_timeline] def mkPromotedAccountsRequest(
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

  private[home_timeline] def getMatwitterResults(req: ProductRequest): Int = {
    req.recommendationRequest.matwitterResults.getOrElse(
      req.params(DefaultMatwitterResults)
    )
  }
}
