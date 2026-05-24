package com.twitter.product_mixer.core.product.guice
import com.twitter.product_mixer.core.model.marshalling.request.Product
import com.google.inject.Key

/**
 * A specialization of SimpleScope - a simple Guice Scope that takes an initial Product Mixer Product as a key
 */
class ProductScope extends SimpleScope {
  def let[T](product: Product)(f: => T): T = super.let(Map(Key.get(classOf[Product]) -> product))(f)
}
