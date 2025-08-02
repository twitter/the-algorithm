package com.twitter.timelineranker.parameters.util

import com.twitter.servo.util.FunctionArrow
import com.twitter.timelineranker.config.RuntimeConfiguration
import com.twitter.timelineranker.model.RecapQuery
import com.twitter.timelines.configapi.Config
import com.twitter.util.Future

class RecapQueryParamInitializer(config: Config, runtimeConfig: RuntimeConfiguration)
    etwittertends FunctionArrow[RecapQuery, Future[RecapQuery]] {
  private[this] val requestContetwittertBuilder =
    new RequestContetwittertBuilderImpl(runtimeConfig.configApiConfiguration.requestContetwittertFactory)

  def apply(query: RecapQuery): Future[RecapQuery] = {
    requestContetwittertBuilder(Some(query.userId), query.deviceContetwittert).map { baseContetwittert =>
      val params = config(baseContetwittert, runtimeConfig.statsReceiver)
      query.copy(params = params)
    }
  }
}
