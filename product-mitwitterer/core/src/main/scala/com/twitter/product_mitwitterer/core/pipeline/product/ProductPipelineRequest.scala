package com.twitter.product_mitwitterer.core.pipeline.product

import com.twitter.timelines.configapi.Params

case class ProductPipelineRequest[RequestType](request: RequestType, params: Params)
