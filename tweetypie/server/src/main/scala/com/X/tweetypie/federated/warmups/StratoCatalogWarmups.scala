package com.X.tweetypie
package federated
package warmups

import com.X.context.XContext
import com.X.context.thriftscala.Viewer
import com.X.spam.rtf.thriftscala.SafetyLevel
import com.X.stitch.Stitch
import com.X.strato.access.Access
import com.X.strato.access.Access.AccessToken
import com.X.strato.access.Access.AuthenticatedXUserId
import com.X.strato.access.Access.AuthenticatedXUserNotSuspended
import com.X.strato.access.Access.XUserId
import com.X.strato.access.Access.XUserNotSuspended
import com.X.strato.catalog.Ops
import com.X.strato.client.StaticClient
import com.X.strato.context.StratoContext
import com.X.strato.opcontext.DarkRequest
import com.X.strato.opcontext.OpContext
import com.X.strato.test.config.bouncer.TestPrincipals
import com.X.strato.thrift.ScroogeConvImplicits._
import com.X.tweetypie.federated.columns.CreateRetweetColumn
import com.X.tweetypie.federated.columns.CreateTweetColumn
import com.X.tweetypie.federated.columns.DeleteTweetColumn
import com.X.tweetypie.federated.columns.UnretweetColumn
import com.X.tweetypie.service.WarmupQueriesSettings
import com.X.tweetypie.thriftscala.graphql._
import com.X.util.logging.Logger
import com.X.util.Future
import com.X.util.Stopwatch

object StratoCatalogWarmups {
  private[this] val log = Logger(getClass)

  // Performs warmup queries, failing after 30 seconds
  def warmup(
    warmupSettings: WarmupQueriesSettings,
    catalog: PartialFunction[String, Ops]
  ): Future[Unit] = {
    val elapsed = Stopwatch.start()
    // note: we need to supply bouncer principals here, because the
    //       columns are gated by a bouncer policy
    Access
      .withPrincipals(WarmupPrincipals) {
        StratoContext.withOpContext(WarmupOpContext) {
          XContext.let(viewer = WarmupViewer) {
            warmupSettings.clientId.asCurrent {
              Stitch.run(executeDarkly(catalog))
            }
          }
        }
      }
      .onSuccess { _ => log.info("warmup completed in %s".format(elapsed())) }
      .onFailure { t => log.error("could not complete warmup queries before startup.", t) }
  }

  private val WarmupXUserId = 0L

  private val WarmupPrincipals = Set(
    TestPrincipals.normalStratoBouncerAccessPrincipal,
    AuthenticatedXUserId(WarmupXUserId),
    XUserId(WarmupXUserId),
    XUserNotSuspended,
    AuthenticatedXUserNotSuspended,
    AccessToken(isWritable = true)
  )

  private[this] val RwebClientId = 0L

  private[this] val WarmupViewer = Viewer(
    userId = Some(WarmupXUserId),
    authenticatedUserId = Some(WarmupXUserId),
    clientApplicationId = Some(RwebClientId),
  )

  private[this] val WarmupOpContext =
    OpContext
      .safetyLevel(SafetyLevel.TweetWritesApi.name)
      .copy(darkRequest = Some(DarkRequest()))
      .toThrift()

  private[this] val EllenOscarSelfie = 440322224407314432L

  private[this] val XContext: XContext =
    com.X.context.XContext(com.X.tweetypie.XContextPermit)

  private[this] def executeDarkly(catalog: PartialFunction[String, Ops]): Stitch[Unit] = {
    val stratoClient = new StaticClient(catalog)
    val tweetCreator =
      stratoClient.executer[CreateTweetRequest, CreateTweetResponseWithSubqueryPrefetchItems](
        CreateTweetColumn.Path)

    val tweetDeletor =
      stratoClient
        .executer[DeleteTweetRequest, DeleteTweetResponseWithSubqueryPrefetchItems](
          DeleteTweetColumn.Path)

    val retweetCreator =
      stratoClient
        .executer[CreateRetweetRequest, CreateRetweetResponseWithSubqueryPrefetchItems](
          CreateRetweetColumn.Path)

    val unretweetor =
      stratoClient
        .executer[UnretweetRequest, UnretweetResponseWithSubqueryPrefetchItems](
          UnretweetColumn.Path)

    val stitchCreateTweet =
      tweetCreator
        .execute(CreateTweetRequest("getting warmer"))
        .onSuccess(_ => log.info(s"${CreateTweetColumn.Path} warmup success"))
        .onFailure(e => log.info(s"${CreateTweetColumn.Path} warmup fail: $e"))

    val stitchDeleteTweet =
      tweetDeletor
        .execute(DeleteTweetRequest(-1L))
        .onSuccess(_ => log.info(s"${DeleteTweetColumn.Path} warmup success"))
        .onFailure(e => log.info(s"${DeleteTweetColumn.Path} warmup fail: $e"))

    val stitchCreateRetweet =
      retweetCreator
        .execute(CreateRetweetRequest(EllenOscarSelfie))
        .onSuccess(_ => log.info(s"${CreateRetweetColumn.Path} warmup success"))
        .onFailure(e => log.info(s"${CreateRetweetColumn.Path} warmup fail: $e"))

    val stitchUnretweet =
      unretweetor
        .execute(UnretweetRequest(EllenOscarSelfie))
        .onSuccess(_ => log.info(s"${UnretweetColumn.Path} warmup success"))
        .onFailure(e => log.info(s"${UnretweetColumn.Path} warmup fail: $e"))

    Stitch
      .join(
        stitchCreateTweet,
        stitchDeleteTweet,
        stitchCreateRetweet,
        stitchUnretweet,
      ).unit
  }
}
