package com.twitter.home_mixer.product.scored_tweets.query_transformer.earlybird

import com.twitter.conversions.DurationOps._
import com.twitter.finagle.thrift.ClientId
import com.twitter.finagle.tracing.Trace
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.EarlyBirdCommunitiesMaxSearchResultsParam
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.EnableEarlybirdCommunitiesQueryLinearRankingParam
import com.twitter.home_mixer.util.earlybird.RelevanceSearchUtil
import com.twitter.product_mixer.component_library.feature_hydrator.query.communities.CommunityMembershipsFeature
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.search.common.query.thriftjava.thriftscala.CollectorParams
import com.twitter.search.common.query.thriftjava.thriftscala.CollectorTerminationParams
import com.twitter.search.common.ranking.{thriftscala => scr}
import com.twitter.search.earlybird.{thriftscala => eb}
import com.twitter.search.earlybird.{thriftscala => t}
import com.twitter.search.queryparser.query.Conjunction
import com.twitter.search.queryparser.query.Disjunction
import com.twitter.search.queryparser.query.search.SearchOperator
import com.twitter.search.queryparser.query.search.SearchOperatorConstants
import com.twitter.search.queryparser.query.{Query => SearchQuery}
import javax.inject.Inject
import javax.inject.Singleton
import scala.collection.JavaConverters._

@Singleton
class CommunitiesEarlybirdQueryTransformer @Inject() (clientId: ClientId)
    extends CandidatePipelineQueryTransformer[PipelineQuery, eb.EarlybirdRequest] {
  private val SinceDuration = 48.hours
  private val DefaultSearchProcessingTimeout = 200.milliseconds
  private val TensorflowModel = "timelines_unified_prod"

  override def transform(query: PipelineQuery): t.EarlybirdRequest = {

    val maxSearchResults = query.params(EarlyBirdCommunitiesMaxSearchResultsParam)

    val communityIds =
      query.features.map(_.getOrElse(CommunityMembershipsFeature, Seq.empty)).toSeq.flatten.toSet
    val entityIdsQuery = createEntityIdsQuery(communityIds)
    val nullcastQuery =
      new SearchOperator(SearchOperator.Type.INCLUDE, SearchOperatorConstants.NULLCAST)
    val excludeRepliesQuery =
      new SearchOperator(SearchOperator.Type.EXCLUDE, SearchOperatorConstants.REPLIES)
    val sinceTimeQuery =
      new SearchOperator(SearchOperator.Type.SINCE_TIME, SinceDuration.ago.inSeconds.toString)
    val searchQuery =
      new Conjunction(entityIdsQuery, excludeRepliesQuery, nullcastQuery, sinceTimeQuery)

    val metadataOptions = t.ThriftSearchResultMetadataOptions(
      getResultLocation = false,
      getLuceneScore = false,
      getInReplyToStatusId = true,
      getReferencedTweetAuthorId = true,
      getMediaBits = true,
      getAllFeatures = true,
      returnSearchResultFeatures = true,
      getFromUserId = true
    )

    val ebRankingParams = Some(
      scr.ThriftRankingParams(
        `type` = Some(scr.ThriftScoringFunctionType.TensorflowBased),
        selectedTensorflowModel = Some(TensorflowModel),
        minScore = -1.0e100,
        applyBoosts = false,
      )
    )

    val linearRelevanceOptions = t.ThriftSearchRelevanceOptions(
      rankingParams = Some(
        scr.ThriftRankingParams(
          `type` = Some(scr.ThriftScoringFunctionType.Linear),
          applyBoosts = false,
          favCountParams = Some(scr.ThriftLinearFeatureRankingParams(weight = 1000.0)),
          replyCountParams = Some(scr.ThriftLinearFeatureRankingParams(weight = 10000.0)),
          quotedCountParams = Some(scr.ThriftLinearFeatureRankingParams(weight = 1000.0))
        )
      ),
      returnAllResults = Some(false)
    )

    val relOptions = RelevanceSearchUtil.RelevanceOptions.copy(
      rankingParams = ebRankingParams,
      returnAllResults = Some(false)
    )

    val relevanceOptions =
      if (query.params(EnableEarlybirdCommunitiesQueryLinearRankingParam)) linearRelevanceOptions
      else relOptions

    val collectorParams = CollectorParams(
      numResultsToReturn = maxSearchResults,
      terminationParams = Some(
        CollectorTerminationParams(
          timeoutMs = DefaultSearchProcessingTimeout.inMilliseconds.toInt
        )
      )
    )

    t.EarlybirdRequest(
      searchQuery = t.ThriftSearchQuery(
        serializedQuery = Some(searchQuery.serialize),
        rankingMode = t.ThriftSearchRankingMode.Relevance,
        numResults = maxSearchResults,
        resultMetadataOptions = Some(metadataOptions),
        searcherId = query.getOptionalUserId,
        relevanceOptions = Some(relevanceOptions),
        maxHitsPerUser = -1,
        collectorParams = Some(collectorParams)
      ),
      clientRequestID = Some(s"${Trace.id.traceId}"),
      numResultsToReturnAtRoot = Some(maxSearchResults),
      clientId = Some(clientId.name),
    )
  }

  private def createEntityIdsQuery(entityIds: Set[Long]): Disjunction = {
    val entityIdStrings = entityIds.map(_.toString)
    val queryOps: Seq[SearchQuery] = entityIdStrings.map { entityId =>
      new SearchOperator(SearchOperator.Type.ENTITY_ID, entityId)
    }.toSeq
    new Disjunction(queryOps.asJava)
  }
}
