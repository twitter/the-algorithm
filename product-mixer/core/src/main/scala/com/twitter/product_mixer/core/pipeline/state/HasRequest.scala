package com.twitter.product_mixer.core.pipeline.state

import com.twitter.product_mixer.core.model.marshalling.request.Request

trait HasRequest[TRequest <: Request] {
  def request: TRequest
}
