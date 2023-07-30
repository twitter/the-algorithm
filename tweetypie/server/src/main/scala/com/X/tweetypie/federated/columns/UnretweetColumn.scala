package com.X.tweetypie
package federated.columns

import com.X.stitch.Stitch
import com.X.strato.catalog.OpMetadata
import com.X.strato.config.ContactInfo
import com.X.strato.config.Policy
import com.X.strato.data.Conv
import com.X.strato.data.Description.PlainText
import com.X.strato.data.Lifecycle.Production
import com.X.strato.fed.StratoFed
import com.X.strato.opcontext.OpContext
import com.X.strato.thrift.ScroogeConv
import com.X.tweetypie.federated.context.GetRequestContext
import com.X.tweetypie.federated.context.RequestContext
import com.X.tweetypie.thriftscala.{graphql => gql}
import com.X.tweetypie.{thriftscala => thrift}

class UnretweetColumn(
  unretweet: thrift.UnretweetRequest => Future[thrift.UnretweetResult],
  getRequestContext: GetRequestContext,
) extends StratoFed.Column("tweetypie/unretweet.Tweet")
    with StratoFed.Execute.StitchWithContext
    with StratoFed.HandleDarkRequests {

  override val policy: Policy = AccessPolicy.TweetMutationCommonAccessPolicies

  // It's acceptable to retry or reapply an unretweet operation,
  // as multiple calls result in the same end state.
  override val isIdempotent: Boolean = true

  override type Arg = gql.UnretweetRequest
  override type Result = gql.UnretweetResponseWithSubqueryPrefetchItems

  override val argConv: Conv[Arg] = ScroogeConv.fromStruct
  override val resultConv: Conv[Result] = ScroogeConv.fromStruct

  override val contactInfo: ContactInfo = TweetypieContactInfo
  override val metadata: OpMetadata =
    OpMetadata(
      Some(Production),
      Some(PlainText("Removes any retweets by the calling user of the given source tweet.")))

  override def execute(gqlRequest: Arg, opContext: OpContext): Stitch[Result] = {
    val ctx: RequestContext = getRequestContext(opContext)
    val req = thrift.UnretweetRequest(
      ctx.XUserId,
      gqlRequest.sourceTweetId,
    )

    val stitchUnretweet = handleDarkRequest(opContext)(
      light = Stitch.callFuture(unretweet(req)),
      // For dark requests, we don't want to send traffic to tweetypie.
      // Since the response is the same regardless of the request, we take a no-op
      // action instead.
      dark = Stitch.value(thrift.UnretweetResult(state = thrift.TweetDeleteState.Ok))
    )

    stitchUnretweet.map { _ =>
      gql.UnretweetResponseWithSubqueryPrefetchItems(
        data = Some(gql.UnretweetResponse(Some(gqlRequest.sourceTweetId)))
      )
    }
  }
}

object UnretweetColumn {
  val Path = "tweetypie/unretweet.Tweet"
}
