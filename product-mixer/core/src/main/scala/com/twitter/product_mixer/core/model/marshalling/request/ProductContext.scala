package com.twitter.product_mixer.core.model.marshalling.request

trait ProductContext

trait HasProductContext {
  def productContext: Option[ProductContext]
}
