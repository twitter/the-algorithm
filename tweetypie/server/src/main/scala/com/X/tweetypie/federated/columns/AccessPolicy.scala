package com.X.tweetypie.federated.columns

import com.X.passbird.bitfield.clientprivileges.thriftscala.{Constants => ClientAppPrivileges}
import com.X.strato.access.Access.AuthenticatedXUserNotSuspended
import com.X.strato.access.Access.ClientApplicationPrivilege
import com.X.strato.access.Access.XUserNotSuspended
import com.X.strato.access.ClientApplicationPrivilegeVariant
import com.X.strato.config._

object AccessPolicy {

  /**
   * All Tweet Mutation operations require all of:
   *   - X user authentication
   *   - X user is not suspended
   *   - Contributor user, if provided, is not suspended
   *   - "Teams Access": user is acting their own behalf, or is a
   *      contributor using a client with ClientAppPriviledges.CONTRIBUTORS
   *   - Write privileges
   */
  val TweetMutationCommonAccessPolicies: Policy =
    AllOf(
      Seq(
        AllowXUserId,
        Has(
          XUserNotSuspended
        ),
        Has(
          AuthenticatedXUserNotSuspended
        ),
        AnyOf(
          Seq(
            XUserContributingAsSelf,
            Has(principal = ClientApplicationPrivilege(ClientApplicationPrivilegeVariant
              .byId(ClientAppPrivileges.CONTRIBUTORS.toShort).get))
          )),
        AllowWritableAccessToken
      )
    )

}
