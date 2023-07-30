package com.X.timelineranker.parameters.util

import com.X.servo.util.Gate
import com.X.timelines.configapi.BaseRequestContext
import com.X.timelines.configapi.WithExperimentContext
import com.X.timelines.configapi.WithFeatureContext
import com.X.timelines.configapi.WithUserId
import com.X.timelines.model.UserId
import com.X.timelineservice.DeviceContext
import com.X.timelineservice.model.RequestContextFactory
import com.X.util.Future

trait CommonRequestContext
    extends BaseRequestContext
    with WithExperimentContext
    with WithUserId
    with WithFeatureContext

trait RequestContextBuilder {
  def apply(
    recipientUserId: Option[UserId],
    deviceContext: Option[DeviceContext]
  ): Future[CommonRequestContext]
}

class RequestContextBuilderImpl(requestContextFactory: RequestContextFactory)
    extends RequestContextBuilder {
  override def apply(
    recipientUserId: Option[UserId],
    deviceContextOpt: Option[DeviceContext]
  ): Future[CommonRequestContext] = {
    val requestContextFut = requestContextFactory(
      contextualUserIdOpt = recipientUserId,
      deviceContext = deviceContextOpt.getOrElse(DeviceContext.empty),
      experimentConfigurationOpt = None,
      requestLogOpt = None,
      contextualUserContext = None,
      useRolesCache = Gate.True,
      timelineId = None
    )

    requestContextFut.map { requestContext =>
      new CommonRequestContext {
        override val userId = recipientUserId
        override val experimentContext = requestContext.experimentContext
        override val featureContext = requestContext.featureContext
      }
    }
  }
}
