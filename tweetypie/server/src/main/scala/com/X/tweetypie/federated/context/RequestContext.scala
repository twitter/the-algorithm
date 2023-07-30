package com.X.tweetypie
package federated.context

import com.X.common.ip_address_utils.ClientIpAddressUtils
import com.X.context.thriftscala.Viewer
import com.X.context.XContext
import com.X.finagle.core.util.InetAddressUtil
import com.X.passbird.bitfield.clientprivileges.thriftscala.{Constants => ClientAppPrivileges}
import com.X.finatra.tfe.HttpHeaderNames
import com.X.spam.rtf.thriftscala.SafetyLevel
import com.X.strato.access.Access.ClientApplicationPrivilege
import com.X.strato.access.Access
import com.X.strato.access.ClientApplicationPrivilegeVariant
import com.X.strato.context.StratoContext
import com.X.strato.opcontext.OpContext
import com.X.strato.response.Err
import com.X.weaverbird.common.GetPlatformKey

/**
 * [[RequestContext]] exists to avoid wiring the federated column
 * implementations directly to the request data that is derived from the
 * contextual environment. Columns should not directly reference
 * XContext, StratoContext, strato.access.Access, HTTP headers, etc.
 * Each column operation operates on two input parameters: a request (i.e.
 * a column operation's Arg) and a [[RequestContext]].
 */
private[federated] case class RequestContext(
  clientApplicationId: Option[AppId] = None,
  deviceSource: Option[String] = None,
  knownDeviceToken: Option[KnownDeviceToken] = None,
  remoteHost: Option[String] = None,
  XUserId: UserId,
  contributorId: Option[UserId] = None,
  isDarkRequest: Boolean = false,
  hasPrivilegeNullcastingAccess: Boolean = false,
  hasPrivilegePromotedTweetsInTimeline: Boolean = false,
  sessionHash: Option[String] = None,
  cardsPlatformKey: Option[String] = None,
  safetyLevel: Option[SafetyLevel] = None,
) {
  def isContributorRequest = contributorId.exists(_ != XUserId)
}

/**
 * Provides a single place to derive request data from the contextual
 * environment. Defined as a sealed class (vs an object) to allow mocking
 * in unit tests.
 */
private[federated] sealed class GetRequestContext() {
  // Bring Tweetypie permitted XContext into scope
  private[this] val XContext: XContext =
    com.X.context.XContext(com.X.tweetypie.XContextPermit)

  /**
   * When XUserIdNotDefined is thrown, it's likely that the column
   * access control configuration lacks `AllowXUserId` or other
   * Policy that ensures the caller is authenticated.
   */
  private[federated] val XUserIdNotDefined =
    Err(Err.Authentication, "User authentication is required for this operation.")

  private[this] val SessionHashHeaderName = "x-tfe-session-hash"
  private[this] def hasClientApplicationPrivilege(id: Int): Boolean =
    Access.getPrincipals.contains(
      ClientApplicationPrivilege(
        ClientApplicationPrivilegeVariant
          .byId(id.toShort).get))

  private[this] def getRequestHeader(headerName: String): Option[String] =
    StratoContext
      .current()
      .propagatedHeaders
      .flatMap(_.get(headerName))

  def apply(opContext: OpContext): RequestContext = {
    val XUserId = Access.getXUserId match {
      // Access.getXUserId should return a value as long as the column
      // policy includes AllowXUserId, which guarantees the presence of
      // the value.
      case Some(XUser) => XUser.id
      case None => throw XUserIdNotDefined
    }

    // contributorId should only be defined when the authenticated user differs
    // from the "X user"
    val contributorId =
      Access.getAuthenticatedXUserId.map(_.id).filter(_ != XUserId)

    val XContext = XContext().getOrElse(Viewer())

    val deviceSource = XContext.clientApplicationId.map("oauth:" + _)

    // Ported from StatusesUpdateController#getBirdherdOptions and
    // BirdherdOption.UserIp(request.clientHost)
    val remoteHost: Option[String] =
      getRequestHeader(HttpHeaderNames.X_TWITTER_AUDIT_IP_THRIFT.toLowerCase) // use the new header
        .flatMap(ClientIpAddressUtils.decodeClientIpAddress(_))
        .flatMap(ClientIpAddressUtils.getString(_))
        .orElse(
          getRequestHeader(
            HttpHeaderNames.X_TWITTER_AUDIT_IP.toLowerCase
          ) // fallback to old way before migration is completed
            .map(h => InetAddressUtil.getByName(h.trim).getHostAddress)
        )

    val isDarkRequest = opContext.darkRequest.isDefined

    val sessionHash = getRequestHeader(SessionHashHeaderName)

    val cardsPlatformKey = XContext.clientApplicationId.map(GetPlatformKey(_))

    val safetyLevel = opContext.safetyLevel

    RequestContext(
      clientApplicationId = XContext.clientApplicationId,
      deviceSource = deviceSource,
      knownDeviceToken = XContext.knownDeviceToken,
      remoteHost = remoteHost,
      XUserId = XUserId,
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
