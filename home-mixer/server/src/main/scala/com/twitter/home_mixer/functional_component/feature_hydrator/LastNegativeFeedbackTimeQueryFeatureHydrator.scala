package com.twitter.home_mixer.functional_component.feature_hydrator

import com.twitter.home_mixer.model.HomeFeatures.LastNegativeFeedbackTimeFeature
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.strato.generated.client.recommendations.user_signal_service.SignalsClientColumn
import com.twitter.usersignalservice.thriftscala.BatchSignalRequest
import com.twitter.usersignalservice.thriftscala.ClientIdentifier
import com.twitter.usersignalservice.thriftscala.SignalRequest
import com.twitter.usersignalservice.thriftscala.SignalType
import com.twitter.usersignalservice.thriftscala.{Signal => UssSignal}
import com.twitter.util.Time
import com.twitter.util.logging.Logging
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LastNegativeFeedbackTimeQueryFeatureHydrator @Inject() (
  signalsClientColumn: SignalsClientColumn)
    extends QueryFeatureHydrator[PipelineQuery]
    with Logging {

  override val identifier: FeatureHydratorIdentifier = FeatureHydratorIdentifier("USSSignals")
  private val fetcher = signalsClientColumn.fetcher

  override val features: Set[Feature[_, _]] = Set(
    LastNegativeFeedbackTimeFeature,
  )

  override def hydrate(query: PipelineQuery): Stitch[FeatureMap] = {
    val lastNegativeFeedbackTime = getLastNegativeFeedbackTime(query.getRequiredUserId)
    lastNegativeFeedbackTime.map { FeatureMap(LastNegativeFeedbackTimeFeature, _) }
  }

  def getLastNegativeFeedbackTime(userId: Long): Stitch[Option[Time]] = {
    val enabledNegativeSignalTypes = Seq(
      SignalType.AccountBlock,
      SignalType.AccountMute,
      SignalType.TweetSeeFewer,
      SignalType.TweetReport,
      SignalType.TweetDontLike)

    // negative signals
    val maybeNegativeSignals =
      enabledNegativeSignalTypes.map { negativeSignal =>
        SignalRequest(
          maxResults = Some(1), // Only most recent needed
          signalType = negativeSignal
        )
      }

    val batchSignalRequest =
      BatchSignalRequest(userId, maybeNegativeSignals, Some(ClientIdentifier.CrMixerHome))

    val signalsStitch = fetcher
      .fetch(batchSignalRequest)
      .map { result =>
        result.v
          .map(_.signalResponse.toSeq.flatMap {
            case (_, signals) =>
              signals
          }).getOrElse(Seq.empty)
      }
    val getTimestamp: UssSignal => Option[Time] = signal =>
      if (signal.timestamp == 0L) None else Some(Time.fromMilliseconds(signal.timestamp))
    signalsStitch.map {
      _.map(getTimestamp).flatten.reduceOption((a, b) => if (a < b) a else b)
    }
  }

}
