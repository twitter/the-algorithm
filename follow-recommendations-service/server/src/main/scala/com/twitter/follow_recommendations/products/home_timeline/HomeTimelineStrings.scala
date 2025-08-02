package com.twitter.follow_recommendations.products.home_timeline

import com.twitter.product_mitwitterer.core.product.guice.scope.ProductScoped
import com.twitter.stringcenter.client.EtwitterternalStringRegistry
import com.twitter.stringcenter.client.core.EtwitterternalString
import javatwitter.inject.Inject
import javatwitter.inject.Provider
import javatwitter.inject.Singleton

@Singleton
class HomeTimelineStrings @Inject() (
  @ProductScoped etwitterternalStringRegistryProvider: Provider[EtwitterternalStringRegistry]) {
  private val etwitterternalStringRegistry = etwitterternalStringRegistryProvider.get()
  val whoToFollowFollowedByManyUserSingleString: EtwitterternalString =
    etwitterternalStringRegistry.createProdString("WtfRecommendationContetwittert.followedByManyUserSingle")
  val whoToFollowFollowedByManyUserDoubleString: EtwitterternalString =
    etwitterternalStringRegistry.createProdString("WtfRecommendationContetwittert.followedByManyUserDouble")
  val whoToFollowFollowedByManyUserMultipleString: EtwitterternalString =
    etwitterternalStringRegistry.createProdString("WtfRecommendationContetwittert.followedByManyUserMultiple")
  val whoToFollowPopularInCountryKey: EtwitterternalString =
    etwitterternalStringRegistry.createProdString("WtfRecommendationContetwittert.popularInCountry")
  val whoToFollowModuleTitle: EtwitterternalString =
    etwitterternalStringRegistry.createProdString("WhoToFollowModule.title")
  val whoToFollowModuleFooter: EtwitterternalString =
    etwitterternalStringRegistry.createProdString("WhoToFollowModule.pivot")
}
