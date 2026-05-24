package com.twitter.home_mixer.product.scored_tweets.feature_hydrator

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.home_mixer.functional_component.feature_hydrator.TweetypieContentDataRecordFeature
import com.twitter.home_mixer.functional_component.feature_hydrator.WithDefaultFeatureMap
import com.twitter.home_mixer.model.ContentFeatures
import com.twitter.home_mixer.model.HomeFeatures._
import com.twitter.home_mixer.product.scored_tweets.feature_hydrator.adapters.content.InReplyToContentFeatureAdapter
import com.twitter.home_mixer.product.scored_tweets.feature_hydrator.adapters.earlybird.InReplyToEarlybirdAdapter
import com.twitter.home_mixer.util.ReplyRetweetUtil
import com.twitter.ml.api.DataRecord
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.FeatureWithDefaultOnFailure
import com.twitter.product_mixer.core.feature.datarecord.DataRecordInAFeature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.functional_component.feature_hydrator.BulkCandidateFeatureHydrator
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.util.OffloadFuturePools
import com.twitter.search.common.features.thriftscala.ThriftTweetFeatures
import com.twitter.snowflake.id.SnowflakeId
import com.twitter.stitch.Stitch
import com.twitter.timelines.conversation_features.v1.thriftscala.ConversationFeatures
import com.twitter.timelines.conversation_features.{thriftscala => cf}
import com.twitter.timelines.prediction.adapters.conversation_features.ConversationFeaturesAdapter
import com.twitter.util.Duration
import com.twitter.util.Time
import javax.inject.Inject
import javax.inject.Singleton
import scala.collection.JavaConverters._

object InReplyToTweetHydratedEarlybirdFeature
    extends Feature[TweetCandidate, Option[ThriftTweetFeatures]]

object ConversationDataRecordFeature
    extends DataRecordInAFeature[TweetCandidate]
    with FeatureWithDefaultOnFailure[TweetCandidate, DataRecord] {
  override def defaultValue: DataRecord = new DataRecord()
}

object InReplyToEarlybirdDataRecordFeature
    extends DataRecordInAFeature[TweetCandidate]
    with FeatureWithDefaultOnFailure[TweetCandidate, DataRecord] {
  override def defaultValue: DataRecord = new DataRecord()
}

object InReplyToTweetypieContentDataRecordFeature
    extends DataRecordInAFeature[TweetCandidate]
    with FeatureWithDefaultOnFailure[TweetCandidate, DataRecord] {
  override def defaultValue: DataRecord = new DataRecord()
}

/**
 * The purpose of this hydrator is to
 * 1) hydrate simple features into replies and their ancestor tweets
 * 2) keep both the normal replies and ancestor source candidates, but hydrate into the candidates
 * features useful for predicting the quality of the replies and source ancestor tweets.
 */
@Singleton
class ReplyFeatureHydrator @Inject() (statsReceiver: StatsReceiver)
    extends BulkCandidateFeatureHydrator[PipelineQuery, TweetCandidate]
    with WithDefaultFeatureMap {

  override val identifier: FeatureHydratorIdentifier = FeatureHydratorIdentifier("ReplyTweet")

  override val features: Set[Feature[_, _]] = Set(
    ConversationDataRecordFeature,
    InReplyToTweetHydratedEarlybirdFeature,
    InReplyToEarlybirdDataRecordFeature,
    InReplyToTweetypieContentDataRecordFeature
  )

  private val defaultDataRecord: DataRecord = new DataRecord()

  override val defaultFeatureMap = FeatureMap(
    ConversationDataRecordFeature,
    defaultDataRecord,
    InReplyToTweetHydratedEarlybirdFeature,
    None,
    InReplyToEarlybirdDataRecordFeature,
    defaultDataRecord,
    InReplyToTweetypieContentDataRecordFeature,
    defaultDataRecord
  )

  private val scopedStatsReceiver = statsReceiver.scope(getClass.getSimpleName)
  private val hydratedReplyCounter = scopedStatsReceiver.counter("hydratedReply")
  private val hydratedAncestorCounter = scopedStatsReceiver.counter("hydratedAncestor")

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[Seq[FeatureMap]] = OffloadFuturePools.offload {
    // only hydrate for IN candidates
    val eligibleCandidates =
      candidates.filter(_.features.getOrElse(FromInNetworkSourceFeature, false))
    val replyToInReplyToTweetMap =
      ReplyRetweetUtil.replyTweetIdToInReplyToTweetMap(eligibleCandidates)
    val candidatesWithRepliesHydrated = candidates.map { candidate =>
      replyToInReplyToTweetMap
        .get(candidate.candidate.id).map { inReplyToTweet =>
          hydratedReplyCounter.incr()
          hydratedReplyCandidate(candidate, inReplyToTweet)
        }.getOrElse((candidate, None, None, defaultDataRecord))
    }

    /**
     * Update ancestor tweets with descendant replies and hydrate simple features from one of
     * the descendants.
     */
    val ancestorTweetToDescendantRepliesMap =
      ReplyRetweetUtil.ancestorTweetIdToDescendantRepliesMap(eligibleCandidates)
    val candidatesWithRepliesAndAncestorTweetsHydrated = candidatesWithRepliesHydrated.map {
      case (
            maybeAncestorTweetCandidate,
            updatedReplyConversationFeatures,
            inReplyToTweetEarlyBirdFeature,
            inReplyToTweetContentDataRecord
          ) =>
        ancestorTweetToDescendantRepliesMap
          .get(maybeAncestorTweetCandidate.candidate.id)
          .map { descendantReplies =>
            hydratedAncestorCounter.incr()
            val (ancestorTweetCandidate, updatedConversationFeatures): (
              CandidateWithFeatures[TweetCandidate],
              Option[ConversationFeatures]
            ) =
              hydrateAncestorTweetCandidate(
                maybeAncestorTweetCandidate,
                descendantReplies,
                updatedReplyConversationFeatures)
            (
              ancestorTweetCandidate,
              inReplyToTweetEarlyBirdFeature,
              updatedConversationFeatures,
              inReplyToTweetContentDataRecord)
          }
          .getOrElse(
            (
              maybeAncestorTweetCandidate,
              inReplyToTweetEarlyBirdFeature,
              updatedReplyConversationFeatures,
              inReplyToTweetContentDataRecord
            ))
    }

    candidatesWithRepliesAndAncestorTweetsHydrated.map {
      case (
            candidate,
            inReplyToTweetEarlyBirdFeature,
            updatedConversationFeatures,
            inReplyToTweetContentDataRecord) =>
        val conversationDataRecordFeature = updatedConversationFeatures
          .map(f => ConversationFeaturesAdapter.adaptToDataRecord(cf.ConversationFeatures.V1(f)))
          .getOrElse(defaultDataRecord)

        val inReplyToEarlybirdDataRecord =
          InReplyToEarlybirdAdapter
            .adaptToDataRecords(inReplyToTweetEarlyBirdFeature).asScala.head
        val inReplyToContentDataRecord = {
          if (inReplyToTweetContentDataRecord.equals(defaultDataRecord)) {
            InReplyToContentFeatureAdapter
              .adaptToDataRecords(
                inReplyToTweetEarlyBirdFeature.map(ContentFeatures.fromThrift)).asScala.head
          } else
            inReplyToTweetContentDataRecord
        }

        FeatureMap(
          ConversationDataRecordFeature,
          conversationDataRecordFeature,
          InReplyToTweetHydratedEarlybirdFeature,
          inReplyToTweetEarlyBirdFeature,
          InReplyToEarlybirdDataRecordFeature,
          inReplyToEarlybirdDataRecord,
          InReplyToTweetypieContentDataRecordFeature,
          inReplyToContentDataRecord
        )
      case _ => defaultFeatureMap
    }
  }

  private def hydratedReplyCandidate(
    replyCandidate: CandidateWithFeatures[TweetCandidate],
    inReplyToTweetCandidate: CandidateWithFeatures[TweetCandidate]
  ): (
    CandidateWithFeatures[TweetCandidate],
    Option[ConversationFeatures],
    Option[ThriftTweetFeatures],
    DataRecord
  ) = {
    val tweetedAfterInReplyToTweetInSecs =
      (
        originalTweetAgeFromSnowflake(inReplyToTweetCandidate),
        originalTweetAgeFromSnowflake(replyCandidate)) match {
        case (Some(inReplyToTweetAge), Some(replyTweetAge)) =>
          Some((inReplyToTweetAge - replyTweetAge).inSeconds.toLong)
        case _ => None
      }

    val existingConversationFeatures = Some(
      replyCandidate.features
        .getOrElse(ConversationFeature, None).getOrElse(ConversationFeatures()))

    val updatedConversationFeatures = existingConversationFeatures match {
      case Some(v1) =>
        Some(
          v1.copy(
            tweetedAfterInReplyToTweetInSecs = tweetedAfterInReplyToTweetInSecs,
            isSelfReply = Some(
              replyCandidate.features.getOrElse(
                AuthorIdFeature,
                None) == inReplyToTweetCandidate.features.getOrElse(AuthorIdFeature, None))
          )
        )
      case _ => None
    }

    // Note: if inReplyToTweet is a retweet, we need to read early bird feature from the merged
    // early bird feature field from RetweetSourceTweetFeatureHydrator class.
    // But if inReplyToTweet is a reply, we return its early bird feature directly
    val sourceFeatures = inReplyToTweetCandidate.features
      .getOrElse(SourceTweetEarlybirdFeature, None)
    val inReplyToTweetThriftTweetFeaturesOpt = {
      if (inReplyToTweetCandidate.features.getOrElse(IsRetweetFeature, false)
        && sourceFeatures.nonEmpty) {
        sourceFeatures
      } else {
        inReplyToTweetCandidate.features.getOrElse(EarlybirdFeature, None)
      }
    }
    val inReplyToTweetContentDataRecord = inReplyToTweetCandidate.features
      .getOrElse(TweetypieContentDataRecordFeature, defaultDataRecord)

    (
      replyCandidate,
      updatedConversationFeatures,
      inReplyToTweetThriftTweetFeaturesOpt,
      inReplyToTweetContentDataRecord)
  }

  private def hydrateAncestorTweetCandidate(
    ancestorTweetCandidate: CandidateWithFeatures[TweetCandidate],
    descendantReplies: Seq[CandidateWithFeatures[TweetCandidate]],
    updatedReplyConversationFeatures: Option[ConversationFeatures]
  ): (CandidateWithFeatures[TweetCandidate], Option[ConversationFeatures]) = {
    // Ancestor could be a reply. For example, in thread: tweetA -> tweetB -> tweetC,
    // tweetB is a reply and ancestor at the same time. Hence, tweetB's conversation feature
    // will be updated by hydratedReplyCandidate and hydrateAncestorTweetCandidate functions.
    val existingConversationFeatures =
      if (updatedReplyConversationFeatures.nonEmpty)
        updatedReplyConversationFeatures
      else
        Some(
          ancestorTweetCandidate.features
            .getOrElse(ConversationFeature, None).getOrElse(ConversationFeatures()))

    val updatedConversationFeatures = existingConversationFeatures match {
      case Some(v1) =>
        Some(
          v1.copy(
            hasDescendantReplyCandidate = Some(true),
            hasInNetworkDescendantReply =
              Some(descendantReplies.exists(_.features.getOrElse(InNetworkFeature, false)))
          ))
      case _ => None
    }
    (ancestorTweetCandidate, updatedConversationFeatures)
  }

  private def originalTweetAgeFromSnowflake(
    candidate: CandidateWithFeatures[TweetCandidate]
  ): Option[Duration] = {
    SnowflakeId
      .timeFromIdOpt(
        candidate.features
          .getOrElse(SourceTweetIdFeature, None).getOrElse(candidate.candidate.id))
      .map(Time.now - _)
  }
}
