package com.twitter.home_mitwitterer.product.for_you.query_transformer

import com.twitter.conversions.DurationOps.richDurationFromInt
import com.twitter.finagle.thrift.ClientId
import com.twitter.finagle.tracing.Trace
import com.twitter.product_mitwitterer.component_library.feature_hydrator.query.social_graph.PreviewCreatorsFeature
import com.twitter.product_mitwitterer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.search.common.ranking.{thriftscala => scr}
import com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant
import com.twitter.search.earlybird.{thriftscala => t}
import com.twitter.search.queryparser.query.Conjunction
import com.twitter.search.queryparser.query.Query
import com.twitter.search.queryparser.query.search.SearchOperator
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class TweetPreviewsQueryTransformer @Inject() (clientId: ClientId)
    etwittertends CandidatePipelineQueryTransformer[PipelineQuery, t.EarlybirdRequest] {

  private val MatwitterPreviewTweets = 200
  private val EarlybirdRelevanceTensorflowModel = "timelines_rectweet_replica"
  private val SinceDuration = 7.days

  val MetadataOptions = t.ThriftSearchResultMetadataOptions(
    getReferencedTweetAuthorId = true,
    getFromUserId = true
  )

  override def transform(query: PipelineQuery): t.EarlybirdRequest = {
    val candidatePreviewCreatorIds =
      query.features.map(_.get(PreviewCreatorsFeature)).getOrElse(Seq.empty)

    val searchQuery = new Conjunction(
      // Include subscriber only (aka etwitterclusive) Tweets
      new SearchOperator.Builder()
        .setType(SearchOperator.Type.FILTER)
        .addOperand(EarlybirdFieldConstant.EtwitterCLUSIVE_FILTER_TERM)
        .build(),
      // Include only original Tweets
      new SearchOperator.Builder()
        .setType(SearchOperator.Type.FILTER)
        .addOperand(EarlybirdFieldConstant.NATIVE_RETWEETS_FILTER_TERM)
        .setOccur(Query.Occur.MUST_NOT)
        .build(),
      new SearchOperator.Builder()
        .setType(SearchOperator.Type.FILTER)
        .addOperand(EarlybirdFieldConstant.REPLIES_FILTER_TERM)
        .setOccur(Query.Occur.MUST_NOT)
        .build(),
      new SearchOperator.Builder()
        .setType(SearchOperator.Type.FILTER)
        .addOperand(EarlybirdFieldConstant.QUOTE_FILTER_TERM)
        .setOccur(Query.Occur.MUST_NOT)
        .build(),
      new SearchOperator(SearchOperator.Type.SINCE_TIME, SinceDuration.ago.inSeconds.toString)
    )

    t.EarlybirdRequest(
      searchQuery = t.ThriftSearchQuery(
        serializedQuery = Some(searchQuery.serialize),
        fromUserIDFilter64 = Some(candidatePreviewCreatorIds),
        numResults = MatwitterPreviewTweets,
        rankingMode = t.ThriftSearchRankingMode.Relevance,
        relevanceOptions = Some(
          t.ThriftSearchRelevanceOptions(
            filterDups = true,
            keepDupWithHigherScore = true,
            protwitterimityScoring = true,
            matwitterConsecutiveSameUser = Some(5),
            rankingParams = Some(
              scr.ThriftRankingParams(
                `type` = Some(scr.ThriftScoringFunctionType.TensorflowBased),
                selectedTensorflowModel = Some(EarlybirdRelevanceTensorflowModel),
                minScore = -1.0e100,
                applyBoosts = false,
              )
            ),
          ),
        ),
        resultMetadataOptions = Some(MetadataOptions),
        searcherId = query.getOptionalUserId,
      ),
      getOlderResults = Some(true), // needed for archive access to older tweets
      clientRequestID = Some(s"${Trace.id.traceId}"),
      followedUserIds = Some(candidatePreviewCreatorIds.toSeq),
      numResultsToReturnAtRoot = Some(MatwitterPreviewTweets),
      clientId = Some(clientId.name),
    )
  }
}
