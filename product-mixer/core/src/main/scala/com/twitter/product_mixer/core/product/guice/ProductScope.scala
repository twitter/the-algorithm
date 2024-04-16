package com.tw ter.product_m xer.core.product.gu ce
 mport com.tw ter.product_m xer.core.model.marshall ng.request.Product
 mport com.google. nject.Key

/**
 * A spec al zat on of S mpleScope - a s mple Gu ce Scope that takes an  n  al Product M xer Product as a key
 */
class ProductScope extends S mpleScope {
  def let[T](product: Product)(f: => T): T = super.let(Map(Key.get(classOf[Product]) -> product))(f)
}
