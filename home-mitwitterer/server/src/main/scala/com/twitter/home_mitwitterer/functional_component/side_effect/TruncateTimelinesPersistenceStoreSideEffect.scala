package com.twitter.home_mitwitterer.functional_component.side_effect

import com.twitter.home_mitwitterer.model.HomeFeatures.PersistenceEntriesFeature
import com.twitter.home_mitwitterer.model.request.FollowingProduct
import com.twitter.home_mitwitterer.model.request.ForYouProduct
import com.twitter.home_mitwitterer.param.HomeGlobalParams.TimelinesPersistenceStoreMatwitterEntriesPerClient
import com.twitter.home_mitwitterer.service.HomeMitwittererAlertConfig
import com.twitter.product_mitwitterer.core.functional_component.side_effect.PipelineResultSideEffect
import com.twitter.product_mitwitterer.core.model.common.identifier.SideEffectIdentifier
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.Timeline
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.timelinemitwitterer.clients.persistence.TimelineResponseBatchesClient
import com.twitter.timelinemitwitterer.clients.persistence.TimelineResponseV3
import com.twitter.timelineservice.model.TimelineQuery
import com.twitter.timelineservice.model.core.TimelineKind
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

/**
 * Side effect that truncates entries in the Timelines Persistence store
 * based on the number of entries per client.
 */
@Singleton
class TruncateTimelinesPersistenceStoreSideEffect @Inject() (
  timelineResponseBatchesClient: TimelineResponseBatchesClient[TimelineResponseV3])
    etwittertends PipelineResultSideEffect[PipelineQuery, Timeline] {

  override val identifier: SideEffectIdentifier =
    SideEffectIdentifier("TruncateTimelinesPersistenceStore")

  def getResponsesToDelete(query: PipelineQuery): Seq[TimelineResponseV3] = {
    val responses =
      query.features.map(_.getOrElse(PersistenceEntriesFeature, Seq.empty)).toSeq.flatten
    val responsesByClient = responses.groupBy(_.clientPlatform).values.toSeq
    val matwitterEntriesPerClient = query.params(TimelinesPersistenceStoreMatwitterEntriesPerClient)

    responsesByClient.flatMap {
      _.sortBy(_.servedTime.inMilliseconds)
        .foldRight((Seq.empty[TimelineResponseV3], matwitterEntriesPerClient)) {
          case (response, (responsesToDelete, remainingCap)) =>
            if (remainingCap > 0) (responsesToDelete, remainingCap - response.entries.size)
            else (response +: responsesToDelete, remainingCap)
        } match { case (responsesToDelete, _) => responsesToDelete }
    }
  }

  final override def apply(
    inputs: PipelineResultSideEffect.Inputs[PipelineQuery, Timeline]
  ): Stitch[Unit] = {
    val timelineKind = inputs.query.product match {
      case FollowingProduct => TimelineKind.homeLatest
      case ForYouProduct => TimelineKind.home
      case other => throw new UnsupportedOperationEtwitterception(s"Unknown product: $other")
    }
    val timelineQuery = TimelineQuery(id = inputs.query.getRequiredUserId, kind = timelineKind)

    val responsesToDelete = getResponsesToDelete(inputs.query)

    if (responsesToDelete.nonEmpty)
      Stitch.callFuture(timelineResponseBatchesClient.delete(timelineQuery, responsesToDelete))
    else Stitch.Unit
  }

  override val alerts = Seq(
    HomeMitwittererAlertConfig.BusinessHours.defaultSuccessRateAlert(99.8)
  )
}
