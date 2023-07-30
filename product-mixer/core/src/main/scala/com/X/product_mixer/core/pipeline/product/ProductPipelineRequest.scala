package com.X.product_mixer.core.pipeline.product

import com.X.timelines.configapi.Params

case class ProductPipelineRequest[RequestType](request: RequestType, params: Params)
