package com.twitter.tweet_mixer.functional_component.hydrator

import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.recos.signals.thriftscala.SourceSignal
import com.twitter.stitch.Stitch
import com.twitter.strato.client.Fetcher
import com.twitter.strato.generated.client.recommendations.signals.RetrievalSignalsOnUserClientColumn
import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSParam
import com.twitter.timelines.configapi.Params
import com.twitter.tweet_mixer.feature._
import com.twitter.tweet_mixer.feature.EntityTypes._
import com.twitter.tweet_mixer.param.HighQualitySourceSignalParams._
import com.twitter.tweet_mixer.param.USSParams.UnifiedMaxSourceKeyNum
import com.twitter.usersignalservice.thriftscala.SignalEntity
import com.twitter.usersignalservice.thriftscala.SignalType
import com.twitter.util.Return
import com.twitter.util.Throw
import com.twitter.util.Time
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
case class HighQualitySourceSignalQueryFeatureHydrator @Inject() (
  retrievalSignalsClientColumn: RetrievalSignalsOnUserClientColumn)
    extends QueryFeatureHydrator[PipelineQuery] {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("HighQualitySourceSignal")

  override val features: Set[Feature[_, _]] = Set(HighQualitySourceTweetV2, HighQualitySourceUserV2)

  val fetcher: Fetcher[
    RetrievalSignalsOnUserClientColumn.Key,
    Unit,
    RetrievalSignalsOnUserClientColumn.Value
  ] =
    retrievalSignalsClientColumn.fetcher

  val dayMs = 24 * 60 * 60 * 1000L
  val defaultTimestamp = 0L

  override def hydrate(query: PipelineQuery): Stitch[FeatureMap] = {
    fetcher.fetch(query.getRequiredUserId).map(_.v).transform { response =>
      val sourceSignalList: Seq[SourceSignal] = response match {
        case Return(result) => result.map(_.sourceSignalList).getOrElse(Seq.empty)
        case Throw(ex) => Seq.empty
        case _ => Seq.empty
      }
      val featureMapBuilder = FeatureMapBuilder()
      buildSignalFeature[TweetId](
        sourceSignalList,
        SignalEntity.Tweet,
        SignalType.HighQualitySourceTweet,
        featureMapBuilder,
        HighQualitySourceTweetV2,
        query.params,
        EnableHighQualitySourceTweetV2,
        MaxHighQualitySourceSignalsV2
      )
      buildSignalFeature[UserId](
        sourceSignalList,
        SignalEntity.User,
        SignalType.HighQualitySourceUser,
        featureMapBuilder,
        HighQualitySourceUserV2,
        query.params,
        EnableHighQualitySourceUserV2,
        MaxHighQualitySourceSignalsV2
      )
      Stitch.value(featureMapBuilder.build())
    }
  }

  def buildSignalFeature[T](
    sourceSignalList: Seq[SourceSignal],
    entityType: SignalEntity,
    signalType: SignalType,
    featureMapBuilder: FeatureMapBuilder,
    featureType: USSFeature[T],
    params: Params,
    sourceSignalParam: FSParam[Boolean],
    maxResultsParam: FSBoundedParam[Int] = UnifiedMaxSourceKeyNum
  ): Unit = {
    val decayRate = params(TimeDecayRate)
    val enableTimeDecay = params(EnableTimeDecay)
    val currentTime = Time.now.inMilliseconds
    val signalMap: Map[T, Seq[SignalInfo]] = if (params(sourceSignalParam)) {

      val filterSourceSignals = sourceSignalList.filter(_.sourceSignalEntity.contains(entityType))
      val signals = filterSourceSignals
        .map(signal => (signal, getScore(enableTimeDecay, decayRate, currentTime, signal, params)))
        .filter(_._2 > 0)
        .sortBy {
          case (signal, score) =>
            (-score, getNegativeCount(signal), -getTimestamp(signal))
        }
        .take(params(maxResultsParam))
        .map(_._1)
      signals
        .map { signal =>
          (
            signal.sourceSignalId.asInstanceOf[T],
            SignalInfo(
              signalEntity = entityType,
              signalType = signalType,
              sourceEventTime = Some(Time.fromMilliseconds(getTimestamp(signal))),
              authorId = None
            )
          )
        }
        .groupBy(_._1).mapValues(_.map(_._2))
    } else {
      Map.empty[T, Seq[SignalInfo]]
    }
    featureMapBuilder.add(featureType, signalMap)
  }

  def getScore(
    enableTimeDecay: Boolean,
    decayRate: Double,
    currentTime: Long,
    signal: SourceSignal,
    params: Params
  ): Double = {
    val timeDiffDays: Double = (currentTime - getTimestamp(signal)).toDouble / dayMs
    if (enableTimeDecay) {
      calculateScore(signal, params) * Math.exp(-decayRate * timeDiffDays)
    } else {
      calculateScore(signal, params)
    }
  }

  def getTimestamp(signal: SourceSignal): Long = {
    signal.updatedAt.orElse(signal.createdAt).getOrElse(defaultTimestamp)
  }

  // Computes the score for a SourceSignal using weights from HighQualitySourceSignalParams.
  private def calculateScore(
    signal: SourceSignal,
    params: Params
  ): Double = {
    signal.bookmarkCount.map(_ * params(HighQualitySourceSignalBookmarkWeight)).getOrElse(0.0) +
      signal.favCount.map(_ * params(HighQualitySourceSignalFavWeight)).getOrElse(0.0) +
      signal.replyCount.map(_ * params(HighQualitySourceSignalReplyWeight)).getOrElse(0.0) +
      signal.retweetCount.map(_ * params(HighQualitySourceSignalRetweetWeight)).getOrElse(0.0) +
      signal.quoteCount.map(_ * params(HighQualitySourceSignalQuoteWeight)).getOrElse(0.0) +
      signal.shareCount.map(_ * params(HighQualitySourceSignalShareWeight)).getOrElse(0.0) +
      signal.videoQualityViewCount
        .map(_ * params(HighQualitySourceSignalVideoQualityViewWeight)).getOrElse(0.0) +
      signal.tweetDetailsClickCount
        .map(_ * params(HighQualitySourceSignalTweetDetailsClickWeight)).getOrElse(0.0) +
      signal.tweetDetailsImpressionCount
        .map(_ * params(HighQualitySourceSignalTweetDetailsImpressionWeight)).getOrElse(0.0) +
      signal.notInterestedCount
        .map(_ * params(HighQualitySourceSignalNotInterestedWeight)).getOrElse(0.0) +
      signal.blockCount.map(_ * params(HighQualitySourceSignalBlockWeight)).getOrElse(0.0) +
      signal.muteCount.map(_ * params(HighQualitySourceSignalMuteWeight)).getOrElse(0.0) +
      signal.reportCount.map(_ * params(HighQualitySourceSignalReportWeight)).getOrElse(0.0)
  }

  def getNegativeCount(
    sourceSignal: SourceSignal
  ): Int = {
    sourceSignal.notInterestedCount.getOrElse(0) + sourceSignal.blockCount.getOrElse(
      0) + sourceSignal.muteCount.getOrElse(0) + sourceSignal.reportCount.getOrElse(0)
  }
}
