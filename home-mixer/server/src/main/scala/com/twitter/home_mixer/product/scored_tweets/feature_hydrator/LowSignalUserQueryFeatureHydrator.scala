package com.twitter.home_mixer.product.scored_tweets.feature_hydrator

import com.twitter.conversions.DurationOps.richDurationFromInt
import com.twitter.home_mixer.model.HomeFeatures._
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.LowSignalUserMaxSignalCount
import com.twitter.home_mixer.util.SignalUtil
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.strato.client.Fetcher
import com.twitter.strato.generated.client.recommendations.user_signal_service.SignalsClientColumn
import com.twitter.usersignalservice.{thriftscala => uss}
import com.twitter.util.Time
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
case class LowSignalUserQueryFeatureHydrator @Inject() (
  signalsClientColumn: SignalsClientColumn)
    extends QueryFeatureHydrator[PipelineQuery] {

  override val identifier: FeatureHydratorIdentifier = FeatureHydratorIdentifier("LowSignalUser")

  override val features: Set[Feature[_, _]] =
    Set(
      LowSignalUserFeature,
      UserRecentEngagementTweetIdsFeature,
      UserLastExplicitSignalTimeFeature)

  val fetcher: Fetcher[SignalsClientColumn.Key, Unit, SignalsClientColumn.Value] =
    signalsClientColumn.fetcher

  val MaxFetch = 15L
  val MinSignalFavCount = 0
  val MaxSignalFavCount = 5000000
  val LowSignalUserMaxSignalAge = 90.days

  private def getTimestamp(signal: uss.Signal): Option[Time] = {
    if (signal.timestamp == 0L) None else Some(Time.fromMilliseconds(signal.timestamp))
  }

  override def hydrate(query: PipelineQuery): Stitch[FeatureMap] = {
    val signalRequests = SignalUtil.ExplicitSignals.map { signal =>
      uss.SignalRequest(
        maxResults = Some(MaxFetch),
        signalType = signal,
        minFavCount = Some(MinSignalFavCount),
        maxFavCount = Some(MaxSignalFavCount)
      )
    }

    val batchSignalRequest = uss.BatchSignalRequest(
      userId = query.getRequiredUserId,
      signalRequest = signalRequests,
      clientId = Some(uss.ClientIdentifier.CrMixerHome)
    )

    fetcher.fetch(batchSignalRequest).map { response =>
      val signals = response.v.map(_.signalResponse.values.toSeq.flatten).getOrElse(Seq.empty)
      val tweetIds = signals.collect {
        case signal if signal.targetInternalId.isDefined =>
          signal.targetInternalId.get match {
            case id: com.twitter.simclusters_v2.thriftscala.InternalId.TweetId =>
              Some(id.tweetId.toLong)
            case _ => None
          }
      }.flatten

      val timeFilteredSignals = signals.filter { signal =>
        getTimestamp(signal).exists { signalTime =>
          Time.now.since(signalTime) < LowSignalUserMaxSignalAge
        }
      }

      val mostRecentTimestamp =
        timeFilteredSignals.flatMap(getTimestamp).reduceOption((a, b) => if (a > b) a else b)

      val lowSignalUser = timeFilteredSignals.size < query.params(LowSignalUserMaxSignalCount)
      FeatureMapBuilder()
        .add(LowSignalUserFeature, lowSignalUser)
        .add(UserRecentEngagementTweetIdsFeature, tweetIds.toList)
        .add(UserLastExplicitSignalTimeFeature, mostRecentTimestamp)
        .build()
    }
  }
}
