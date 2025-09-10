package com.twitter.tweet_mixer.functional_component.transformer

import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.twitter.search.query_interaction_graph.service.{thriftscala => t}
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.trends.common.thriftscala.UserIdentifier
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.QigSearchHistoryTopKLimit

case class QigBatchQueryTransformer[Query <: PipelineQuery](
  signalFn: PipelineQuery => Seq[String],
) extends CandidatePipelineQueryTransformer[
      Query,
      Seq[t.QigRequest]
    ] {

  override def transform(inputQuery: Query): Seq[t.QigRequest] = {
    val queries: Seq[String] = signalFn(inputQuery)

    queries.map { query =>
      t.QigRequest(
        query = Some(query),
        userIdentifier = Some(
          UserIdentifier(
            userId = inputQuery.getOptionalUserId,
            guestIdMarketing = inputQuery.clientContext.guestIdMarketing
          )
        ),
        userInfo = Some(t.UserInfo(inputQuery.clientContext.userRoles)),
        productContext = Some(t.ProductContext.TweetContext(t.TweetContext())),
        topKLimit = Some(inputQuery.params(QigSearchHistoryTopKLimit)),
      )
    }
  }
}
