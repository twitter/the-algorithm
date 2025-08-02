package com.twitter.home_mitwitterer.product.subscribed

import com.twitter.finagle.thrift.ClientId
import com.twitter.finagle.tracing.Trace
import com.twitter.home_mitwitterer.product.subscribed.model.SubscribedQuery
import com.twitter.home_mitwitterer.product.subscribed.param.SubscribedParam.ServerMatwitterResultsParam
import com.twitter.product_mitwitterer.component_library.feature_hydrator.query.social_graph.SGSSubscribedUsersFeature
import com.twitter.product_mitwitterer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.operation.BottomCursor
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.operation.GapCursor
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.operation.TopCursor
import com.twitter.product_mitwitterer.core.pipeline.pipeline_failure.MalformedCursor
import com.twitter.product_mitwitterer.core.pipeline.pipeline_failure.PipelineFailure
import com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant
import com.twitter.search.earlybird.{thriftscala => t}
import com.twitter.search.queryparser.query.Conjunction
import com.twitter.search.queryparser.query.search.SearchOperator
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
case class SubscribedEarlybirdQueryTransformer @Inject() (clientId: ClientId)
    etwittertends CandidatePipelineQueryTransformer[SubscribedQuery, t.EarlybirdRequest] {

  override def transform(query: SubscribedQuery): t.EarlybirdRequest = {
    val subscribedUserIds =
      query.features.map(_.get(SGSSubscribedUsersFeature)).getOrElse(Seq.empty)

    val subscribedUsersQuery = new SearchOperator.Builder()
      .setType(SearchOperator.Type.FILTER)
      .addOperand(EarlybirdFieldConstant.EtwitterCLUSIVE_FILTER_TERM)
      .build()

    val searchQuery = query.pipelineCursor
      .map { cursor =>
        val sinceIdQuery =
          (id: Long) => new SearchOperator(SearchOperator.Type.SINCE_ID, id.toString)
        val matwitterIdQuery = // matwitter ID is inclusive, so subtract 1
          (id: Long) => new SearchOperator(SearchOperator.Type.MAtwitter_ID, (id - 1).toString)

        (cursor.cursorType, cursor.id, cursor.gapBoundaryId) match {
          case (Some(TopCursor), Some(sinceId), _) =>
            new Conjunction(sinceIdQuery(sinceId), subscribedUsersQuery)
          case (Some(BottomCursor), Some(matwitterId), _) =>
            new Conjunction(matwitterIdQuery(matwitterId), subscribedUsersQuery)
          case (Some(GapCursor), Some(matwitterId), Some(sinceId)) =>
            new Conjunction(sinceIdQuery(sinceId), matwitterIdQuery(matwitterId), subscribedUsersQuery)
          case (Some(GapCursor), _, _) =>
            throw PipelineFailure(MalformedCursor, "Invalid cursor " + cursor.toString)
          case _ => subscribedUsersQuery
        }
      }.getOrElse(subscribedUsersQuery)

    t.EarlybirdRequest(
      searchQuery = t.ThriftSearchQuery(
        serializedQuery = Some(searchQuery.serialize),
        fromUserIDFilter64 = Some(subscribedUserIds),
        numResults = query.requestedMatwitterResults.getOrElse(query.params(ServerMatwitterResultsParam)),
        rankingMode = t.ThriftSearchRankingMode.Recency,
      ),
      getOlderResults = Some(true), // needed for archive access to older tweets
      clientRequestID = Some(s"${Trace.id.traceId}"),
      numResultsToReturnAtRoot = Some(query.params(ServerMatwitterResultsParam)),
      clientId = Some(clientId.name),
    )
  }
}
