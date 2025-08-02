package com.twitter.timelineranker.parameters.util

import com.twitter.servo.util.Gate
import com.twitter.timelines.configapi.BaseRequestContetwittert
import com.twitter.timelines.configapi.WithEtwitterperimentContetwittert
import com.twitter.timelines.configapi.WithFeatureContetwittert
import com.twitter.timelines.configapi.WithUserId
import com.twitter.timelines.model.UserId
import com.twitter.timelineservice.DeviceContetwittert
import com.twitter.timelineservice.model.RequestContetwittertFactory
import com.twitter.util.Future

trait CommonRequestContetwittert
    etwittertends BaseRequestContetwittert
    with WithEtwitterperimentContetwittert
    with WithUserId
    with WithFeatureContetwittert

trait RequestContetwittertBuilder {
  def apply(
    recipientUserId: Option[UserId],
    deviceContetwittert: Option[DeviceContetwittert]
  ): Future[CommonRequestContetwittert]
}

class RequestContetwittertBuilderImpl(requestContetwittertFactory: RequestContetwittertFactory)
    etwittertends RequestContetwittertBuilder {
  override def apply(
    recipientUserId: Option[UserId],
    deviceContetwittertOpt: Option[DeviceContetwittert]
  ): Future[CommonRequestContetwittert] = {
    val requestContetwittertFut = requestContetwittertFactory(
      contetwittertualUserIdOpt = recipientUserId,
      deviceContetwittert = deviceContetwittertOpt.getOrElse(DeviceContetwittert.empty),
      etwitterperimentConfigurationOpt = None,
      requestLogOpt = None,
      contetwittertualUserContetwittert = None,
      useRolesCache = Gate.True,
      timelineId = None
    )

    requestContetwittertFut.map { requestContetwittert =>
      new CommonRequestContetwittert {
        override val userId = recipientUserId
        override val etwitterperimentContetwittert = requestContetwittert.etwitterperimentContetwittert
        override val featureContetwittert = requestContetwittert.featureContetwittert
      }
    }
  }
}
