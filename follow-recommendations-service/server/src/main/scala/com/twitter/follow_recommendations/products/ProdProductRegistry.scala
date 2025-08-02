package com.twitter.follow_recommendations.products

import com.twitter.follow_recommendations.common.models.DisplayLocation
import com.twitter.follow_recommendations.products.common.ProductRegistry
import com.twitter.follow_recommendations.products.etwitterplore_tab.EtwitterploreTabProduct
import com.twitter.follow_recommendations.products.home_timeline.HomeTimelineProduct
import com.twitter.follow_recommendations.products.home_timeline_tweet_recs.HomeTimelineTweetRecsProduct
import com.twitter.follow_recommendations.products.sidebar.SidebarProduct

import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class ProdProductRegistry @Inject() (
  etwitterploreTabProduct: EtwitterploreTabProduct,
  homeTimelineProduct: HomeTimelineProduct,
  homeTimelineTweetRecsProduct: HomeTimelineTweetRecsProduct,
  sidebarProduct: SidebarProduct,
) etwittertends ProductRegistry {

  override val products: Seq[common.Product] =
    Seq(
      etwitterploreTabProduct,
      homeTimelineProduct,
      homeTimelineTweetRecsProduct,
      sidebarProduct
    )

  override val displayLocationProductMap: Map[DisplayLocation, common.Product] =
    products.groupBy(_.displayLocation).flatMap {
      case (loc, products) =>
        assert(products.size == 1, s"Found more than 1 Product for ${loc}")
        products.headOption.map { product => loc -> product }
    }

  override def getProductByDisplayLocation(displayLocation: DisplayLocation): common.Product = {
    displayLocationProductMap.getOrElse(
      displayLocation,
      throw new MissingProductEtwitterception(displayLocation))
  }
}

class MissingProductEtwitterception(displayLocation: DisplayLocation)
    etwittertends Etwitterception(s"No Product found for ${displayLocation}")
