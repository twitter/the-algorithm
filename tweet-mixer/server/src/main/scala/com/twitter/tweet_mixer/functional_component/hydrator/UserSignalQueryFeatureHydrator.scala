package com.twitter.tweet_mixer.functional_component.hydrator

import com.twitter.conversions.DurationOps.richDurationFromInt
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.FeatureWithDefaultOnFailure
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.simclusters_v2.thriftscala.InternalId
import com.twitter.stitch.Stitch
import com.twitter.strato.client.Fetcher
import com.twitter.strato.generated.client.recommendations.user_signal_service.SignalsClientColumn
import com.twitter.usersignalservice.thriftscala.SignalType
import com.twitter.usersignalservice.{thriftscala => uss}
import com.twitter.util.Time
import javax.inject.Inject
import javax.inject.Singleton

object UserEngagedAuthorIdsFeature
    extends FeatureWithDefaultOnFailure[PipelineQuery, Option[Seq[Long]]] {
  override def defaultValue: Option[Seq[Long]] = None
}
@Singleton
case class UserSignalQueryFeatureHydrator @Inject() (
  signalsClientColumn: SignalsClientColumn)
    extends QueryFeatureHydrator[PipelineQuery] {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("UserSignalQueryFeatureHydrator")

  override val features: Set[Feature[_, _]] = Set(UserEngagedAuthorIdsFeature)

  val fetcher: Fetcher[SignalsClientColumn.Key, Unit, SignalsClientColumn.Value] =
    signalsClientColumn.fetcher

  val MaxFetch = 15L
  val MaxSignalAge = 14.days

  private val enabledSignalTypes = Seq(
    SignalType.TweetFavorite,
    SignalType.Retweet,
    SignalType.Reply,
    SignalType.TweetBookmarkV1,
  )

  private def getTimestamp(signal: uss.Signal): Option[Time] = {
    if (signal.timestamp == 0L) None else Some(Time.fromMilliseconds(signal.timestamp))
  }

  override def hydrate(query: PipelineQuery): Stitch[FeatureMap] = {
    val signalRequests = enabledSignalTypes.map { signal =>
      uss.SignalRequest(maxResults = Some(MaxFetch), signalType = signal)
    }

    val batchSignalRequest = uss.BatchSignalRequest(
      userId = query.getRequiredUserId,
      signalRequest = signalRequests,
      clientId = Some(uss.ClientIdentifier.CrMixerHome)
    )

    fetcher.fetch(batchSignalRequest).map { response =>
      val signals = response.v.map(_.signalResponse.values.toSeq.flatten).getOrElse(Seq.empty)

      val timeFilteredSignals = signals.filter { signal =>
        getTimestamp(signal).exists { signalTime =>
          Time.now.since(signalTime) < MaxSignalAge
        }
      }

      val authorIds: Seq[Long] = timeFilteredSignals.flatMap { signal =>
        signal.authorId match {
          case Some(InternalId.UserId(authorId)) => Some(authorId)
          case _ => None
        }
      }

      FeatureMap(UserEngagedAuthorIdsFeature, Some(authorIds))
    }
  }
}
