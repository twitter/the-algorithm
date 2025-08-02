package com.twitter.product_mitwitterer.core.pipeline.state

import com.twitter.product_mitwitterer.core.model.marshalling.request.Request

trait HasRequest[TRequest <: Request] {
  def request: TRequest
}
