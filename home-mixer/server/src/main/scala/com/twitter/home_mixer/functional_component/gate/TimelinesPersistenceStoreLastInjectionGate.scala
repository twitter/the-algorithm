package com.ExTwitter.home_mixer.functional_component.gate

import com.ExTwitter.common_internal.analytics.ExTwitter_client_user_agent_parser.UserAgent
import com.ExTwitter.product_mixer.core.feature.Feature
import com.ExTwitter.product_mixer.core.functional_component.gate.Gate
import com.ExTwitter.product_mixer.core.model.common.identifier.GateIdentifier
import com.ExTwitter.product_mixer.core.pipeline.PipelineQuery
import com.ExTwitter.stitch.Stitch
import com.ExTwitter.timelinemixer.clients.persistence.TimelineResponseV3
import com.ExTwitter.timelinemixer.injection.store.persistence.TimelinePersistenceUtils
import com.ExTwitter.timelines.configapi.Param
import com.ExTwitter.timelines.util.client_info.ClientPlatform
import com.ExTwitter.timelineservice.model.rich.EntityIdType
import com.ExTwitter.util.Duration
import com.ExTwitter.util.Time

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
  persistenceEntriesFeature: Feature[PipelineQuery, Seq[TimelineResponseV3]],
  entityIdType: EntityIdType.Value)
    extends Gate[PipelineQuery]
    with TimelinePersistenceUtils {

  override val identifier: GateIdentifier = GateIdentifier("TimelinesPersistenceStoreLastInjection")

  override def shouldContinue(query: PipelineQuery): Stitch[Boolean] =
    Stitch(
      query.queryTime.since(getLastInjectionTime(query)) > query.params(minInjectionIntervalParam))

  private def getLastInjectionTime(query: PipelineQuery) = query.features
    .flatMap { featureMap =>
      val timelineResponses = featureMap.getOrElse(persistenceEntriesFeature, Seq.empty)
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
