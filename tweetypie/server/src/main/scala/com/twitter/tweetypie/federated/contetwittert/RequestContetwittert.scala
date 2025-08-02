package com.twitter.tweetypie
package federated.contetwittert

import com.twitter.common.ip_address_utils.ClientIpAddressUtils
import com.twitter.contetwittert.thriftscala.Viewer
import com.twitter.contetwittert.TwitterContetwittert
import com.twitter.finagle.core.util.InetAddressUtil
import com.twitter.passbird.bitfield.clientprivileges.thriftscala.{Constants => ClientAppPrivileges}
import com.twitter.finatra.tfe.HttpHeaderNames
import com.twitter.spam.rtf.thriftscala.SafetyLevel
import com.twitter.strato.access.Access.ClientApplicationPrivilege
import com.twitter.strato.access.Access
import com.twitter.strato.access.ClientApplicationPrivilegeVariant
import com.twitter.strato.contetwittert.StratoContetwittert
import com.twitter.strato.opcontetwittert.OpContetwittert
import com.twitter.strato.response.Err
import com.twitter.weaverbird.common.GetPlatformKey

/**
 * [[RequestContetwittert]] etwitterists to avoid wiring the federated column
 * implementations directly to the request data that is derived from the
 * contetwittertual environment. Columns should not directly reference
 * TwitterContetwittert, StratoContetwittert, strato.access.Access, HTTP headers, etc.
 * Each column operation operates on two input parameters: a request (i.e.
 * a column operation's Arg) and a [[RequestContetwittert]].
 */
private[federated] case class RequestContetwittert(
  clientApplicationId: Option[AppId] = None,
  deviceSource: Option[String] = None,
  knownDeviceToken: Option[KnownDeviceToken] = None,
  remoteHost: Option[String] = None,
  twitterUserId: UserId,
  contributorId: Option[UserId] = None,
  isDarkRequest: Boolean = false,
  hasPrivilegeNullcastingAccess: Boolean = false,
  hasPrivilegePromotedTweetsInTimeline: Boolean = false,
  sessionHash: Option[String] = None,
  cardsPlatformKey: Option[String] = None,
  safetyLevel: Option[SafetyLevel] = None,
) {
  def isContributorRequest = contributorId.etwitterists(_ != twitterUserId)
}

/**
 * Provides a single place to derive request data from the contetwittertual
 * environment. Defined as a sealed class (vs an object) to allow mocking
 * in unit tests.
 */
private[federated] sealed class GetRequestContetwittert() {
  // Bring Tweetypie permitted TwitterContetwittert into scope
  private[this] val TwitterContetwittert: TwitterContetwittert =
    com.twitter.contetwittert.TwitterContetwittert(com.twitter.tweetypie.TwitterContetwittertPermit)

  /**
   * When TwitterUserIdNotDefined is thrown, it's likely that the column
   * access control configuration lacks `AllowTwitterUserId` or other
   * Policy that ensures the caller is authenticated.
   */
  private[federated] val TwitterUserIdNotDefined =
    Err(Err.Authentication, "User authentication is required for this operation.")

  private[this] val SessionHashHeaderName = "twitter-tfe-session-hash"
  private[this] def hasClientApplicationPrivilege(id: Int): Boolean =
    Access.getPrincipals.contains(
      ClientApplicationPrivilege(
        ClientApplicationPrivilegeVariant
          .byId(id.toShort).get))

  private[this] def getRequestHeader(headerName: String): Option[String] =
    StratoContetwittert
      .current()
      .propagatedHeaders
      .flatMap(_.get(headerName))

  def apply(opContetwittert: OpContetwittert): RequestContetwittert = {
    val twitterUserId = Access.getTwitterUserId match {
      // Access.getTwitterUserId should return a value as long as the column
      // policy includes AllowTwitterUserId, which guarantees the presence of
      // the value.
      case Some(twitterUser) => twitterUser.id
      case None => throw TwitterUserIdNotDefined
    }

    // contributorId should only be defined when the authenticated user differs
    // from the "Twitter user"
    val contributorId =
      Access.getAuthenticatedTwitterUserId.map(_.id).filter(_ != twitterUserId)

    val twitterContetwittert = TwitterContetwittert().getOrElse(Viewer())

    val deviceSource = twitterContetwittert.clientApplicationId.map("oauth:" + _)

    // Ported from StatusesUpdateController#getBirdherdOptions and
    // BirdherdOption.UserIp(request.clientHost)
    val remoteHost: Option[String] =
      getRequestHeader(HttpHeaderNames.twitter_TWITTER_AUDIT_IP_THRIFT.toLowerCase) // use the new header
        .flatMap(ClientIpAddressUtils.decodeClientIpAddress(_))
        .flatMap(ClientIpAddressUtils.getString(_))
        .orElse(
          getRequestHeader(
            HttpHeaderNames.twitter_TWITTER_AUDIT_IP.toLowerCase
          ) // fallback to old way before migration is completed
            .map(h => InetAddressUtil.getByName(h.trim).getHostAddress)
        )

    val isDarkRequest = opContetwittert.darkRequest.isDefined

    val sessionHash = getRequestHeader(SessionHashHeaderName)

    val cardsPlatformKey = twitterContetwittert.clientApplicationId.map(GetPlatformKey(_))

    val safetyLevel = opContetwittert.safetyLevel

    RequestContetwittert(
      clientApplicationId = twitterContetwittert.clientApplicationId,
      deviceSource = deviceSource,
      knownDeviceToken = twitterContetwittert.knownDeviceToken,
      remoteHost = remoteHost,
      twitterUserId = twitterUserId,
      contributorId = contributorId,
      isDarkRequest = isDarkRequest,
      hasPrivilegeNullcastingAccess =
        hasClientApplicationPrivilege(ClientAppPrivileges.NULLCASTING_ACCESS),
      hasPrivilegePromotedTweetsInTimeline =
        hasClientApplicationPrivilege(ClientAppPrivileges.PROMOTED_TWEETS_IN_TIMELINE),
      sessionHash = sessionHash,
      cardsPlatformKey = cardsPlatformKey,
      safetyLevel = safetyLevel,
    )
  }
}
