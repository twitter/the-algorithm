package com.twitter.product_mitwitterer.core.pipeline.state

import com.twitter.timelines.configapi.Params

trait HasParams {
  def params: Params
}
