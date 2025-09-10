package com.twitter.tweet_mixer.functional_component.transformer

import com.twitter.conversions.DurationOps._
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.twitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mixer.core.model.marshalling.request.HasExcludedIds
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.search.common.query.thriftjava.thriftscala.CollectorParams
import com.twitter.search.common.query.thriftjava.thriftscala.CollectorTerminationParams
import com.twitter.search.earlybird.{thriftscala => eb}
import com.twitter.search.queryparser.query.{Query => SearchQuery}
import com.twitter.search.queryparser.query.search.SearchOperator
import com.twitter.timelines.util.SnowflakeSortIndexHelper
import com.twitter.util.Time
import com.twitter.search.queryparser.query.Conjunction
import com.twitter.tweet_mixer.candidate_source.earlybird_realtime_cg.InNetworkRequest
import com.twitter.tweet_mixer.functional_component.hydrator.HaploMissFeature
import com.twitter.tweet_mixer.functional_component.hydrator.SGSFollowedUsersFeature
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.MaxTweetAgeHoursParam
import scala.collection.JavaConverters._

object EarlybirdInNetworkQueryTransformer {
  private val MaxFollowUsers = 1500
  private val NumEarlybirdPartitions = 3
  private val DefaultEarlybirdCandidates = 4500
  private val MaxEarlybirdCandidates = 6600

  private val EarlybirdRequestMetadataOptions: eb.ThriftSearchResultMetadataOptions =
    eb.ThriftSearchResultMetadataOptions(
      getInReplyToStatusId = true,
      getFromUserId = true,
      getReferencedTweetAuthorId = true
    )
}

case class EarlybirdInNetworkQueryTransformer[Query <: PipelineQuery with HasExcludedIds](
  candidatePipelineIdentifier: CandidatePipelineIdentifier,
  clientId: Option[String])
    extends CandidatePipelineQueryTransformer[Query, InNetworkRequest] {

  import EarlybirdInNetworkQueryTransformer._

  def createConjunction(clauses: Seq[SearchQuery]): Option[SearchQuery] = {
    clauses.size match {
      case 0 => None
      case 1 => Some(clauses.head)
      case _ => Some(new Conjunction(clauses.asJava))
    }
  }

  def createRangeQuery(
    beforeTweetIdExclusive: Option[Long],
    afterTweetIdExclusive: Option[Long]
  ): Option[SearchQuery] = {
    val beforeIdClause = beforeTweetIdExclusive.map { beforeId =>
      new SearchOperator(SearchOperator.Type.SINCE_ID, beforeId.toString)
    }
    val afterIdClause = afterTweetIdExclusive.map { afterId =>
      // MAX_ID is an inclusive value therefore we subtract 1 from afterId
      new SearchOperator(SearchOperator.Type.MAX_ID, (afterId - 1).toString)
    }
    createConjunction(Seq(beforeIdClause, afterIdClause).flatten)
  }

  override def transform(query: Query): InNetworkRequest = {

    val duration = query.params(MaxTweetAgeHoursParam)
    val sinceTime: Time = duration.ago
    val untilTime: Time = Time.now

    val fromTweetIdExclusive = SnowflakeSortIndexHelper.timestampToFakeId(sinceTime)
    val toTweetIdExclusive = SnowflakeSortIndexHelper.timestampToFakeId(untilTime)

    val followedUserIds =
      query.features
        .map(v =>
          v.getOrElse(SGSFollowedUsersFeature, Seq.empty[Long]) ++ Seq(query.getRequiredUserId))
        .map { _.take(MaxFollowUsers) }

    val excludedIds = query.excludedIds

    //Getting default number of candidates if there are no excludedIds. If there are excludedIds, get more tweets but cap it
    val numResultsPerPartition = math.min(
      DefaultEarlybirdCandidates + excludedIds.size,
      MaxEarlybirdCandidates) / NumEarlybirdPartitions

    val collectorParams = CollectorParams(
      numResultsToReturn = numResultsPerPartition,
      terminationParams = Some(
        CollectorTerminationParams(
          maxHitsToProcess = Some(numResultsPerPartition), // return all Hits
          timeoutMs = 200.milliseconds.inMilliseconds.toInt
        ))
    )

    val rangeQuery = createRangeQuery(Some(fromTweetIdExclusive), Some(toTweetIdExclusive))
    val searchQuery: Option[SearchQuery] = rangeQuery

    val thriftQuery = eb.ThriftSearchQuery(
      serializedQuery = searchQuery.map { _.serialize },
      fromUserIDFilter64 = followedUserIds,
      maxHitsPerUser = -1, // disable maxHitsPerUser so we can avoid antigaming filter in Earlybird
      numResults = -1, // disable
      collectConversationId = true,
      rankingMode = eb.ThriftSearchRankingMode.Recency,
      relevanceOptions = None,
      resultMetadataOptions = Some(EarlybirdRequestMetadataOptions),
      collectorParams = Some(collectorParams),
      searcherId = Some(query.getRequiredUserId),
      searchStatusIds = None,
      namedDisjunctionMap = None
    )

    val ebRequest = eb.EarlybirdRequest(
      searchQuery = thriftQuery,
      clientId = clientId,
      getOlderResults = Some(false),
      followedUserIds = followedUserIds,
      getProtectedTweetsOnly = Some(false),
      timeoutMs = 200.milliseconds.inMilliseconds.toInt,
      skipVeryRecentTweets = true,
      numResultsToReturnAtRoot = Some(numResultsPerPartition * NumEarlybirdPartitions)
    )

    val writeBackToHaplo =
      query.features.getOrElse(FeatureMap.empty).getOrElse(HaploMissFeature, false)
    InNetworkRequest(ebRequest, excludedIds, writeBackToHaplo)
  }
}
