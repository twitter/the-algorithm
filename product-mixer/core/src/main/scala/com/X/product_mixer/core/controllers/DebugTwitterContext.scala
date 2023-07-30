package com.X.product_mixer.core.controllers

import com.X.context.XContext
import com.X.context.thriftscala.Viewer
import com.X.product_mixer.XContextPermit
import com.X.product_mixer.core.model.marshalling.request.ClientContext

/**
 * Mixes in support to forge the UserIds in XContext for debug purposes.
 *
 * A thrift controller can extend DebugXContext and wrap it's execution logic:
 *
 * {{{
 * withDebugXContext(request.clientContext) {
 *   Stitch.run(...)
 * }
 * }}}
 */
trait DebugXContext {

  private val ctx = XContext(XContextPermit)

  /**
   * Wrap some function in a debug XContext with hardcoded userIds
   * to the ClientContext.userId.
   *
   * @param clientContext - A product mixer request client context
   * @param f The function to wrap
   */
  def withDebugXContext[T](clientContext: ClientContext)(f: => T): T = {
    ctx.let(
      forgeXContext(
        clientContext.userId
          .getOrElse(throw new IllegalArgumentException("missing required field: user id")))
    )(f)
  }

  // Generate a fake X Context for debug usage.
  // Generally the XContext is created by the API service, and Strato uses it for permission control.
  // When we use our debug endpoint, we instead create our own context so that Strato finds something useful.
  // We enforce ACLs directly via Thrift Web Forms' permission system.
  private def forgeXContext(userId: Long): Viewer = {
    Viewer(
      auditIp = None,
      ipTags = Set.empty,
      userId = Some(userId),
      guestId = None,
      clientApplicationId = None,
      userAgent = None,
      locationToken = None,
      authenticatedUserId = Some(userId),
      guestToken = None
    )
  }
}
