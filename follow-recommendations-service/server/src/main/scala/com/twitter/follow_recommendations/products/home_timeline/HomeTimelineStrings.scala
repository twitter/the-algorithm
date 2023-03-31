package com.twitter.follow_recommendations.products.home_timeline

import com.twitter.product_mixer.core.product.guice.scope.ProductScoped
import com.twitter.stringcenter.client.ExternalStringRegistry
import com.twitter.stringcenter.client.core.ExternalString
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class HomeTimelineStrings @Inject() (
  @ProductScoped externalStringRegistryProvider: Provider[ExternalStringRegistry]) {
  private val externalStringRegistry = externalStringRegistryProvider.get()
  val whoToFollowFollowedByManyUserSingleString: ExternalString =
    externalStringRegistry.createProdString("WtfRecommendationContext.followedByManyUserSingle")
  val whoToFollowFollowedByManyUserDoubleString: ExternalString =
    externalStringRegistry.createProdString("WtfRecommendationContext.followedByManyUserDouble")
  val whoToFollowFollowedByManyUserMultipleString: ExternalString =
    externalStringRegistry.createProdString("WtfRecommendationContext.followedByManyUserMultiple")
  val whoToFollowPopularInCountryKey: ExternalString =
    externalStringRegistry.createProdString("WtfRecommendationContext.popularInCountry")
  val whoToFollowModuleTitle: ExternalString =
    externalStringRegistry.createProdString("WhoToFollowModule.title")
  val whoToFollowModuleFooter: ExternalString =
    externalStringRegistry.createProdString("WhoToFollowModule.pivot")
}
