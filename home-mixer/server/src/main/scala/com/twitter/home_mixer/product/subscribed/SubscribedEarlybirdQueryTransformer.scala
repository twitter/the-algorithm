package com.twitter.home_mixer.product.subscribed

import com.twitter.finagle.thrift.ClientId
import com.twitter.finagle.tracing.Trace
import com.twitter.home_mixer.product.subscribed.model.SubscribedQuery
import com.twitter.home_mixer.product.subscribed.param.SubscribedParam.ServerMaxResultsParam
import com.twitter.product_mixer.component_library.feature_hydrator.query.social_graph.SGSSubscribedUsersFeature
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.twitter.product_mixer.core.model.marshalling.response.urt.operation.BottomCursor
import com.twitter.product_mixer.core.model.marshalling.response.urt.operation.GapCursor
import com.twitter.product_mixer.core.model.marshalling.response.urt.operation.TopCursor
import com.twitter.product_mixer.core.pipeline.pipeline_failure.MalformedCursor
import com.twitter.product_mixer.core.pipeline.pipeline_failure.PipelineFailure
import com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant
import com.twitter.search.earlybird.{thriftscala => t}
import com.twitter.search.queryparser.query.Conjunction
import com.twitter.search.queryparser.query.search.SearchOperator
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
case class SubscribedEarlybirdQueryTransformer @Inject() (clientId: ClientId)
    extends CandidatePipelineQueryTransformer[SubscribedQuery, t.EarlybirdRequest] {

  override def transform(query: SubscribedQuery): t.EarlybirdRequest = {
    val subscribedUserIds =
      query.features.map(_.get(SGSSubscribedUsersFeature)).getOrElse(Seq.empty)

    val subscribedUsersQuery = new SearchOperator.Builder()
      .setType(SearchOperator.Type.FILTER)
      .addOperand(EarlybirdFieldConstant.EXCLUSIVE_FILTER_TERM)
      .build()

    val searchQuery = query.pipelineCursor
      .map { cursor =>
        val sinceIdQuery =
          (id: Long) => new SearchOperator(SearchOperator.Type.SINCE_ID, id.toString)
        val maxIdQuery = // max ID is inclusive, so subtract 1
          (id: Long) => new SearchOperator(SearchOperator.Type.MAX_ID, (id - 1).toString)

        (cursor.cursorType, cursor.id, cursor.gapBoundaryId) match {
          case (Some(TopCursor), Some(sinceId), _) =>
            new Conjunction(sinceIdQuery(sinceId), subscribedUsersQuery)
          case (Some(BottomCursor), Some(maxId), _) =>
            new Conjunction(maxIdQuery(maxId), subscribedUsersQuery)
          case (Some(GapCursor), Some(maxId), Some(sinceId)) =>
            new Conjunction(sinceIdQuery(sinceId), maxIdQuery(maxId), subscribedUsersQuery)
          case (Some(GapCursor), _, _) =>
            throw PipelineFailure(MalformedCursor, "Invalid cursor " + cursor.toString)
          case _ => subscribedUsersQuery
        }
      }.getOrElse(subscribedUsersQuery)

    t.EarlybirdRequest(
      searchQuery = t.ThriftSearchQuery(
        serializedQuery = Some(searchQuery.serialize),
        fromUserIDFilter64 = Some(subscribedUserIds),
        numResults = query.requestedMaxResults.getOrElse(query.params(ServerMaxResultsParam)),
        rankingMode = t.ThriftSearchRankingMode.Recency,
      ),
      getOlderResults = Some(true), // needed for archive access to older tweets
      clientRequestID = Some(s"${Trace.id.traceId}"),
      numResultsToReturnAtRoot = Some(query.params(ServerMaxResultsParam)),
      clientId = Some(clientId.name),
    )
  }
}
