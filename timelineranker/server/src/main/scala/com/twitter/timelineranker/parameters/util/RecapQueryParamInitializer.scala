package com.twitter.timelineranker.parameters.util

import com.twitter.servo.util.FunctionArrow
import com.twitter.timelineranker.config.RuntimeConfiguration
import com.twitter.timelineranker.model.RecapQuery
import com.twitter.timelines.configapi.Config
import com.twitter.util.Future

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
