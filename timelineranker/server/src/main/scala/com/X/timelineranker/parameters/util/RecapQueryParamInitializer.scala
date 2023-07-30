package com.X.timelineranker.parameters.util

import com.X.servo.util.FunctionArrow
import com.X.timelineranker.config.RuntimeConfiguration
import com.X.timelineranker.model.RecapQuery
import com.X.timelines.configapi.Config
import com.X.util.Future

class RecapQueryParamInitializer(config: Config, runtimeConfig: RuntimeConfiguration)
    extends FunctionArrow[RecapQuery, Future[RecapQuery]] {
  private[this] val requestContextBuilder =
    new RequestContextBuilderImpl(runtimeConfig.configApiConfiguration.requestContextFactory)

  def apply(query: RecapQuery): Future[RecapQuery] = {
    requestContextBuilder(Some(query.userId), query.deviceContext).map { baseContext =>
      val params = config(baseContext, runtimeConfig.statsReceiver)
      query.copy(params = params)
    }
  }
}
