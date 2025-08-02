package com.twitter.product_mitwitterer.core.model.marshalling.request

trait Request
    etwittertends HasClientContetwittert
    with HasProduct
    with HasProductContetwittert
    with HasSerializedRequestCursor {
  def matwitterResults: Option[Int]
  def debugParams: Option[DebugParams]
}
