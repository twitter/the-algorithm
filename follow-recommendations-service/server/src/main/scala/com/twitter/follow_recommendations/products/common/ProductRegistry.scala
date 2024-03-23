package com.ExTwitter.follow_recommendations.products.common

import com.ExTwitter.follow_recommendations.common.models.DisplayLocation

trait ProductRegistry {
  def products: Seq[Product]
  def displayLocationProductMap: Map[DisplayLocation, Product]
  def getProductByDisplayLocation(displayLocation: DisplayLocation): Product
}
