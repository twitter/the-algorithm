package com.ExTwitter.home_mixer.functional_component.side_effect

import com.ExTwitter.home_mixer.model.HomeFeatures.PersistenceEntriesFeature
import com.ExTwitter.home_mixer.model.request.FollowingProduct
import com.ExTwitter.home_mixer.model.request.ForYouProduct
import com.ExTwitter.home_mixer.param.HomeGlobalParams.TimelinesPersistenceStoreMaxEntriesPerClient
import com.ExTwitter.home_mixer.service.HomeMixerAlertConfig
import com.ExTwitter.product_mixer.core.functional_component.side_effect.PipelineResultSideEffect
import com.ExTwitter.product_mixer.core.model.common.identifier.SideEffectIdentifier
import com.ExTwitter.product_mixer.core.model.marshalling.response.urt.Timeline
import com.ExTwitter.product_mixer.core.pipeline.PipelineQuery
import com.ExTwitter.stitch.Stitch
import com.ExTwitter.timelinemixer.clients.persistence.TimelineResponseBatchesClient
import com.ExTwitter.timelinemixer.clients.persistence.TimelineResponseV3
import com.ExTwitter.timelineservice.model.TimelineQuery
import com.ExTwitter.timelineservice.model.core.TimelineKind
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Side effect that truncates entries in the Timelines Persistence store
 * based on the number of entries per client.
 */
@Singleton
class TruncateTimelinesPersistenceStoreSideEffect @Inject() (
  timelineResponseBatchesClient: TimelineResponseBatchesClient[TimelineResponseV3])
    extends PipelineResultSideEffect[PipelineQuery, Timeline] {

  override val identifier: SideEffectIdentifier =
    SideEffectIdentifier("TruncateTimelinesPersistenceStore")

  def getResponsesToDelete(query: PipelineQuery): Seq[TimelineResponseV3] = {
    val responses =
      query.features.map(_.getOrElse(PersistenceEntriesFeature, Seq.empty)).toSeq.flatten
    val responsesByClient = responses.groupBy(_.clientPlatform).values.toSeq
    val maxEntriesPerClient = query.params(TimelinesPersistenceStoreMaxEntriesPerClient)

    responsesByClient.flatMap {
      _.sortBy(_.servedTime.inMilliseconds)
        .foldRight((Seq.empty[TimelineResponseV3], maxEntriesPerClient)) {
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
      case other => throw new UnsupportedOperationException(s"Unknown product: $other")
    }
    val timelineQuery = TimelineQuery(id = inputs.query.getRequiredUserId, kind = timelineKind)

    val responsesToDelete = getResponsesToDelete(inputs.query)

    if (responsesToDelete.nonEmpty)
      Stitch.callFuture(timelineResponseBatchesClient.delete(timelineQuery, responsesToDelete))
    else Stitch.Unit
  }

  override val alerts = Seq(
    HomeMixerAlertConfig.BusinessHours.defaultSuccessRateAlert(99.8)
  )
}
