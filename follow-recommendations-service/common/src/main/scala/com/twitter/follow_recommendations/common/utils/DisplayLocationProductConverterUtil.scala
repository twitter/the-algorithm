package com.twitter.follow_recommendations.common.utils

import com.twitter.follow_recommendations.common.models.DisplayLocation
import com.twitter.follow_recommendations.common.models.Product
import com.twitter.product_mixer.core.model.marshalling.request.Product

object DisplayLocationProductConverterUtil {
  def productToDisplayLocation(product: Product): DisplayLocation = {
    product match {
      case Product.MagicRecs => DisplayLocation.MagicRecs
      case _ =>
        throw UnconvertibleProductMixerProductException(
          s"Cannot convert Product Mixer Product ${product.identifier.name} into a FRS DisplayLocation.")
    }
  }

  def displayLocationToProduct(displayLocation: DisplayLocation): Product = {
    displayLocation match {
      case DisplayLocation.MagicRecs => Product.MagicRecs
      case _ =>
        throw UnconvertibleProductMixerProductException(
          s"Cannot convert DisplayLocation ${displayLocation.toFsName} into a Product Mixer Product.")
    }
  }
}

case class UnconvertibleProductMixerProductException(message: String) extends Exception(message)
