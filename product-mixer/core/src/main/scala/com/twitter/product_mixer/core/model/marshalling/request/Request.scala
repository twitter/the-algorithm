package com.twitter.product_mixer.core.model.marshalling.request

trait Request
    extends HasClientContext
    with HasProduct
    with HasProductContext
    with HasSerializedRequestCursor {
  def maxResults: Option[Int]
  def debugParams: Option[DebugParams]
}
