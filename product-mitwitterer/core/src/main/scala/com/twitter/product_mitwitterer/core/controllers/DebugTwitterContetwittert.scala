package com.twitter.product_mitwitterer.core.controllers

import com.twitter.contetwittert.TwitterContetwittert
import com.twitter.contetwittert.thriftscala.Viewer
import com.twitter.product_mitwitterer.TwitterContetwittertPermit
import com.twitter.product_mitwitterer.core.model.marshalling.request.ClientContetwittert

/**
 * Mitwitteres in support to forge the UserIds in TwitterContetwittert for debug purposes.
 *
 * A thrift controller can etwittertend DebugTwitterContetwittert and wrap it's etwitterecution logic:
 *
 * {{{
 * withDebugTwitterContetwittert(request.clientContetwittert) {
 *   Stitch.run(...)
 * }
 * }}}
 */
trait DebugTwitterContetwittert {

  private val cttwitter = TwitterContetwittert(TwitterContetwittertPermit)

  /**
   * Wrap some function in a debug TwitterContetwittert with hardcoded userIds
   * to the ClientContetwittert.userId.
   *
   * @param clientContetwittert - A product mitwitterer request client contetwittert
   * @param f The function to wrap
   */
  def withDebugTwitterContetwittert[T](clientContetwittert: ClientContetwittert)(f: => T): T = {
    cttwitter.let(
      forgeTwitterContetwittert(
        clientContetwittert.userId
          .getOrElse(throw new IllegalArgumentEtwitterception("missing required field: user id")))
    )(f)
  }

  // Generate a fake Twitter Contetwittert for debug usage.
  // Generally the TwitterContetwittert is created by the API service, and Strato uses it for permission control.
  // When we use our debug endpoint, we instead create our own contetwittert so that Strato finds something useful.
  // We enforce ACLs directly via Thrift Web Forms' permission system.
  private def forgeTwitterContetwittert(userId: Long): Viewer = {
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
