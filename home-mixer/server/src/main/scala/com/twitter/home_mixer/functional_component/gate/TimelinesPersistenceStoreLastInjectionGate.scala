package com.twitter.home_mixer.functional_component.gate

import com.twitter.common_internal.analytics.twitter_client_user_agent_parser.UserAgent
import com.twitter.home_mixer.model.HomeFeatures.PersistenceEntriesFeature
import com.twitter.product_mixer.core.functional_component.gate.Gate
import com.twitter.product_mixer.core.model.common.identifier.GateIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.timelinemixer.injection.store.persistence.TimelinePersistenceUtils
import com.twitter.timelines.configapi.Param
import com.twitter.timelines.util.client_info.ClientPlatform
import com.twitter.timelineservice.model.rich.EntityIdType
import com.twitter.util.Duration
import com.twitter.util.Time

/**
 * Gate used to reduce the frequency of injections. Note that the actual interval between injections may be
 * less than the specified minInjectionIntervalParam if data is unavailable or missing. For example, being deleted by
 * the persistence store via a TTL or similar mechanism.
 *
 * @param minInjectionIntervalParam the desired minimum interval between injections
 * @param persistenceEntriesFeature the feature for retrieving persisted timeline responses
 */
case class TimelinesPersistenceStoreLastInjectionGate(
  minInjectionIntervalParam: Param[Duration],
  entityIdType: EntityIdType.Value)
    extends Gate[PipelineQuery]
    with TimelinePersistenceUtils {

  override val identifier: GateIdentifier = GateIdentifier("TimelinesPersistenceStoreLastInjection")

  override def shouldContinue(query: PipelineQuery): Stitch[Boolean] =
    Stitch(
      query.queryTime.since(getLastInjectionTime(query)) > query.params(minInjectionIntervalParam))

  private def getLastInjectionTime(query: PipelineQuery) = query.features
    .flatMap { featureMap =>
      val timelineResponses = featureMap.getOrElse(PersistenceEntriesFeature, Seq.empty)
      val clientPlatform = ClientPlatform.fromQueryOptions(
        clientAppId = query.clientContext.appId,
        userAgent = query.clientContext.userAgent.flatMap(UserAgent.fromString)
      )
      val sortedResponses = responseByClient(clientPlatform, timelineResponses)
      val latestResponseWithEntityIdTypeEntry =
        sortedResponses.find(_.entries.exists(_.entityIdType == entityIdType))

      latestResponseWithEntityIdTypeEntry.map(_.servedTime)
    }.getOrElse(Time.Bottom)
}
