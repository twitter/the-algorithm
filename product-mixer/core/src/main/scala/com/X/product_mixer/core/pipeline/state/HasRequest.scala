package com.X.product_mixer.core.pipeline.state

import com.X.product_mixer.core.model.marshalling.request.Request

trait HasRequest[TRequest <: Request] {
  def request: TRequest
}
