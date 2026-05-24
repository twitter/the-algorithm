package com.twitter.home_mixer.product.scored_tweets.side_effect

import com.twitter.finagle.mtls.authentication.ServiceIdentifier
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.home_mixer.model.HomeFeatures.AuthorIdFeature
import com.twitter.home_mixer.model.HomeFeatures.ServedTypeFeature
import com.twitter.home_mixer.model.HomeFeatures.SGSValidLikedByUserIdsFeature
import com.twitter.home_mixer.model.HomeFeatures.SourceSignalFeature
import com.twitter.home_mixer.model.HomeFeatures.SourceTweetIdFeature
import com.twitter.home_mixer.param.HomeMixerInjectionNames.BatchedStratoClientWithLongTimeout
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.EnableCacheRetrievalSignalParam
import com.twitter.home_mixer.{thriftscala => hmt}
import com.twitter.product_mixer.core.functional_component.side_effect.PipelineResultSideEffect
import com.twitter.product_mixer.core.functional_component.side_effect.PipelineResultSideEffect.Conditionally
import com.twitter.product_mixer.core.model.common.identifier.SideEffectIdentifier
import com.twitter.product_mixer.core.model.common.presentation.CandidateWithDetails
import com.twitter.product_mixer.core.model.marshalling.HasMarshalling
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.strato.client.Client
import com.twitter.strato.client.Putter
import com.twitter.strato.generated.client.home_mixer.RetrievalSignalv2ClientColumn
import com.twitter.usersignalservice.{thriftscala => se}
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class CacheRetrievalSignalSideEffect @Inject() (
  @Named(BatchedStratoClientWithLongTimeout) stratoClient: Client,
  serviceIdentifier: ServiceIdentifier,
  statsReceiver: StatsReceiver)
    extends PipelineResultSideEffect[PipelineQuery, HasMarshalling]
    with Conditionally[PipelineQuery, HasMarshalling] {

  override val identifier: SideEffectIdentifier = SideEffectIdentifier("CacheRetrievalSignal")

  private val scopedStats = statsReceiver.scope(getClass.getSimpleName)

  private val isProdEnv = serviceIdentifier.environment == "prod"

  private val retrievalSignalPutter: Putter[(Long, Long), hmt.RetrievalSignal] =
    new RetrievalSignalv2ClientColumn(stratoClient).putter

  override def onlyIf(
    query: PipelineQuery,
    selectedCandidates: Seq[CandidateWithDetails],
    remainingCandidates: Seq[CandidateWithDetails],
    droppedCandidates: Seq[CandidateWithDetails],
    response: HasMarshalling
  ): Boolean = query.params(EnableCacheRetrievalSignalParam) && isProdEnv

  override def apply(
    inputs: PipelineResultSideEffect.Inputs[PipelineQuery, HasMarshalling]
  ): Stitch[Unit] = {
    scopedStats.counter("side_effect_applied").incr(1)

    val sourceSignalWrites = inputs.selectedCandidates.map { candidate =>
      scopedStats.counter("total_candidates_processed").incr(1)

      val userId = inputs.query.getRequiredUserId
      val signalFeature = candidate.features.getOrElse(SourceSignalFeature, None)
      val servedType = candidate.features.get(ServedTypeFeature)

      val retrievalSignalOpt = (signalFeature, servedType) match {
        case (Some(signal), _) if signal.id > 0L && signal.signalEntity.isDefined =>
          scopedStats.counter("valid_existing_signal_feature").incr(1)
          signal.signalEntity.flatMap { entity =>
            if (entity != se.SignalEntity.User || signal.id != userId) {
              Some(
                hmt.RetrievalSignal(
                  signalId = signal.id,
                  signalEntity = entity,
                  authorId = signal.authorId.filter(_ > 0L)
                ))
            } else None
          }

        case (None, hmt.ServedType.ForYouInNetwork) =>
          scopedStats.counter("valid_in_network_signal_feature").incr(1)
          candidate.features.get(AuthorIdFeature).map { authorId =>
            hmt.RetrievalSignal(
              signalId = authorId,
              signalEntity = se.SignalEntity.User,
              authorId = None
            )
          }

        case (None, hmt.ServedType.ForYouUteg) =>
          scopedStats.counter("valid_uteg_liked_by_signal_feature").incr(1)
          val validLikedByUserIds =
            candidate.features.getOrElse(SGSValidLikedByUserIdsFeature, Seq.empty)
          validLikedByUserIds.headOption.map { lastLikedByUserId =>
            hmt.RetrievalSignal(
              signalId = lastLikedByUserId,
              signalEntity = se.SignalEntity.User,
              authorId = None
            )
          }

        case _ =>
          scopedStats.counter("missing_or_invalid_signal_feature").incr(1)
          None
      }
      retrievalSignalOpt
        .map { retrievalSignal =>
          val sourceTweetId: Long =
            candidate.features
              .getOrElse(SourceTweetIdFeature, None).getOrElse(candidate.candidateIdLong)
          retrievalSignalPutter.put((userId, sourceTweetId), retrievalSignal)
        }.getOrElse(Stitch.Unit)
    }.toSeq

    Stitch.collect(sourceSignalWrites).map(_ => ())
  }
}
