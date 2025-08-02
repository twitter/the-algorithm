package com.twitter.home_mitwitterer.functional_component.side_effect

import com.twitter.home_mitwitterer.model.HomeFeatures.FollowingLastNonPollingTimeFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.NonPollingTimesFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.PollingFeature
import com.twitter.home_mitwitterer.model.request.DeviceContetwittert
import com.twitter.home_mitwitterer.model.request.HasDeviceContetwittert
import com.twitter.home_mitwitterer.model.request.FollowingProduct
import com.twitter.home_mitwitterer.service.HomeMitwittererAlertConfig
import com.twitter.product_mitwitterer.component_library.side_effect.UserSessionStoreUpdateSideEffect
import com.twitter.product_mitwitterer.core.model.common.identifier.SideEffectIdentifier
import com.twitter.product_mitwitterer.core.model.marshalling.HasMarshalling
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.timelineservice.model.util.FinagleRequestContetwittert
import com.twitter.user_session_store.ReadWriteUserSessionStore
import com.twitter.user_session_store.WriteRequest
import com.twitter.user_session_store.thriftscala.NonPollingTimestamps
import com.twitter.user_session_store.thriftscala.UserSessionField
import com.twitter.util.Time

import javatwitter.inject.Inject
import javatwitter.inject.Singleton

/**
 * Side effect that updates the User Session Store (Manhattan) with the timestamps of non polling requests.
 */
@Singleton
class UpdateLastNonPollingTimeSideEffect[
  Query <: PipelineQuery with HasDeviceContetwittert,
  ResponseType <: HasMarshalling] @Inject() (
  override val userSessionStore: ReadWriteUserSessionStore)
    etwittertends UserSessionStoreUpdateSideEffect[
      WriteRequest,
      Query,
      ResponseType
    ] {
  private val MatwitterNonPollingTimes = 10

  override val identifier: SideEffectIdentifier = SideEffectIdentifier("UpdateLastNonPollingTime")

  /**
   * When the request is non polling and is not a background fetch request, update
   * the list of non polling timestamps with the timestamp of the current request
   */
  override def buildWriteRequest(query: Query): Option[WriteRequest] = {
    val isBackgroundFetch = query.deviceContetwittert
      .etwitterists(_.requestContetwittertValue.contains(DeviceContetwittert.RequestContetwittert.BackgroundFetch))

    if (!query.features.etwitterists(_.getOrElse(PollingFeature, false)) && !isBackgroundFetch) {
      val fields = Seq(UserSessionField.NonPollingTimestamps(makeLastNonPollingTimestamps(query)))
      Some(WriteRequest(query.getRequiredUserId, fields))
    } else None
  }

  override val alerts = Seq(
    HomeMitwittererAlertConfig.BusinessHours.defaultSuccessRateAlert(99.96)
  )

  private def makeLastNonPollingTimestamps(query: Query): NonPollingTimestamps = {
    val priorNonPollingTimestamps =
      query.features.map(_.getOrElse(NonPollingTimesFeature, Seq.empty)).toSeq.flatten

    val lastNonPollingTimeMs =
      FinagleRequestContetwittert.default.requestStartTime.get.getOrElse(Time.now).inMillis

    val followingLastNonPollingTime = query.features
      .flatMap(features => features.getOrElse(FollowingLastNonPollingTimeFeature, None))
      .map(_.inMillis)

    NonPollingTimestamps(
      nonPollingTimestampsMs =
        (lastNonPollingTimeMs +: priorNonPollingTimestamps).take(MatwitterNonPollingTimes),
      mostRecentHomeLatestNonPollingTimestampMs =
        if (query.product == FollowingProduct) Some(lastNonPollingTimeMs)
        else followingLastNonPollingTime
    )
  }
}
