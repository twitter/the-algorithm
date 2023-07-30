package com.X.follow_recommendations.products.common

import com.X.follow_recommendations.common.models.DisplayLocation

trait ProductRegistry {
  def products: Seq[Product]
  def displayLocationProductMap: Map[DisplayLocation, Product]
  def getProductByDisplayLocation(displayLocation: DisplayLocation): Product
}
