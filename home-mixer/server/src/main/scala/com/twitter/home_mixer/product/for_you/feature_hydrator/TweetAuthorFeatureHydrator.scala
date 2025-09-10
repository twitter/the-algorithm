package com.twitter.home_mixer.product.for_you.feature_hydrator

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.home_mixer.model.HomeFeatures.{
  TLSOriginalTweetsWithAuthorFeature,
  TLSOriginalTweetsWithConfirmedAuthorFeature
}
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.{FeatureMap, FeatureMapBuilder}
import com.twitter.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.spam.rtf.{thriftscala => rtf}
import com.twitter.stitch.Stitch
import com.twitter.strato.generated.client.tweetypie.managed.HomeMixerTesOnTweetClientColumn
import com.twitter.strato.client.Client
import com.twitter.tweetypie.{thriftscala => tp}
import javax.inject.{Inject, Singleton}
import com.twitter.util.logging.Logging
import javax.inject.Named
import com.twitter.home_mixer.param.HomeMixerInjectionNames.BatchedStratoClientWithLongTimeout

@Singleton
class TweetAuthorFeatureHydrator @Inject() (
  @Named(BatchedStratoClientWithLongTimeout) stratoClient: Client,
  stats: StatsReceiver)
  extends QueryFeatureHydrator[PipelineQuery]
  with Logging {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("TweetAuthor")

  override val features: Set[Feature[_, _]] =
    Set(TLSOriginalTweetsWithConfirmedAuthorFeature)

  private def fetchTweetDataFromTES(tweetId: Long, tweetFieldsOptions: tp.GetTweetFieldsOptions): Stitch[Option[(Long, Long)]] = {
    val fetcher = new HomeMixerTesOnTweetClientColumn(stratoClient).fetcher
    fetcher
      .fetch(tweetId, tweetFieldsOptions)
      .map(_.v)
      .map {
        case Some(result) =>
          result.tweetResult match {
            case tp.TweetFieldsResultState.Found(tweetResult) =>
              val isRetweet = tweetResult.retweetedTweet.isDefined
              tweetResult.tweet.coreData.flatMap { coreData =>
                val isReply = coreData.reply.isDefined
                if (!isRetweet && !isReply) Some(tweetId -> coreData.userId) else None
              }
            case _ => None
          }
        case None => None
      }
  }

  override def hydrate(query: PipelineQuery): Stitch[FeatureMap] = {
    val originals: Seq[(Long, Option[Long])] = query.features.map(_.getOrElse(TLSOriginalTweetsWithAuthorFeature, Seq())).getOrElse(Seq.empty)

    if (originals.isEmpty)
      return Stitch.value(
        FeatureMapBuilder()
          .add(TLSOriginalTweetsWithConfirmedAuthorFeature, Seq.empty[(Long, Long)])
          .build()
      )

    val missingIds = originals.collect { case (tid, None) => tid }

    val tweetFieldsOptions = tp.GetTweetFieldsOptions(
      tweetIncludes = Set(tp.TweetInclude.TweetFieldId(tp.Tweet.CoreDataField.id), tp.TweetInclude.TweetFieldId(tp.Tweet.CountsField.id)),
      safetyLevel = Some(rtf.SafetyLevel.TimelineHome),
      forUserId = query.getOptionalUserId
    )

    val fetchedTweetData: Stitch[Map[Long, Long]] =
      Stitch
        .collect(missingIds.map { id =>
          fetchTweetDataFromTES(id, tweetFieldsOptions)
        })
        .map(_.flatten.toMap)

    fetchedTweetData.map { found =>
      val rebuilt: Seq[(Long, Long)] =
        originals.collect {
          case (tid, authorOpt) =>
            authorOpt.orElse(found.get(tid)).map(aid => (tid, aid))
        }.flatten

      FeatureMapBuilder()
        .add(TLSOriginalTweetsWithConfirmedAuthorFeature, rebuilt)
        .build()
    }
  }
}
