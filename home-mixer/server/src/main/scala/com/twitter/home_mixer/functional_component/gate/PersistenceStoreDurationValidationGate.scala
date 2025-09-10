package com.twitter.home_mixer.functional_component.gate

import com.twitter.common_internal.analytics.twitter_client_user_agent_parser.UserAgent
import com.twitter.conversions.DurationOps.richDurationFromInt
import com.twitter.home_mixer.model.HomeFeatures.PersistenceEntriesFeature
import com.twitter.product_mixer.core.functional_component.configapi.StaticParam
import com.twitter.product_mixer.core.functional_component.gate.Gate
import com.twitter.product_mixer.core.model.common.identifier.GateIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.timelinemixer.injection.store.persistence.TimelinePersistenceUtils
import com.twitter.timelines.configapi.Param
import com.twitter.timelines.util.client_info.ClientPlatform
import com.twitter.util.Duration
import com.twitter.util.Time

/**
 * For users who max out the persistence store, we may serve certain modules too frequently.
 * Use this gate to prevent that.
 *
 * Gate stops the request if the time since the oldest entry < input duration
 * (or if there aren't enough entries, which can also cause a small duration)
 *
 * @param minInjectionIntervalParam the desired minimum interval between injections
 */
case class PersistenceStoreDurationValidationGate(
  minDuration: Param[Duration] = StaticParam(48.hours))
    extends Gate[PipelineQuery]
    with TimelinePersistenceUtils {

  override val identifier: GateIdentifier = GateIdentifier("PersistenceStoreDurationValidation")

  private val MinEntries = 1500

  override def shouldContinue(query: PipelineQuery): Stitch[Boolean] = {
    val continue = query.features.map { featureMap =>
      val timelineResponses = featureMap.getOrElse(PersistenceEntriesFeature, Seq.empty)
      val clientPlatform = ClientPlatform.fromQueryOptions(
        clientAppId = query.clientContext.appId,
        userAgent = query.clientContext.userAgent.flatMap(UserAgent.fromString)
      )
      val sortedResponses = responseByClient(clientPlatform, timelineResponses)
      val entryCount = sortedResponses.flatMap(_.entries).size

      val oldestTime = sortedResponses.lastOption.map(_.servedTime).getOrElse(Time.Bottom)

      val duration = Time.now.since(oldestTime)
      duration > query.params(minDuration) || entryCount < MinEntries
    }

    Stitch.value(continue.getOrElse(true))
  }
}
