package com.twitter.tweetypie.federated.columns

import com.twitter.passbird.bitfield.clientprivileges.thriftscala.{Constants => ClientAppPrivileges}
import com.twitter.strato.access.Access.AuthenticatedTwitterUserNotSuspended
import com.twitter.strato.access.Access.ClientApplicationPrivilege
import com.twitter.strato.access.Access.TwitterUserNotSuspended
import com.twitter.strato.access.ClientApplicationPrivilegeVariant
import com.twitter.strato.config._

object AccessPolicy {

  /**
   * All Tweet Mutation operations require all of:
   *   - Twitter user authentication
   *   - Twitter user is not suspended
   *   - Contributor user, if provided, is not suspended
   *   - "Teams Access": user is acting their own behalf, or is a
   *      contributor using a client with ClientAppPriviledges.CONTRIBUTORS
   *   - Write privileges
   */
  val TweetMutationCommonAccessPolicies: Policy =
    AllOf(
      Seq(
        AllowTwitterUserId,
        Has(
          TwitterUserNotSuspended
        ),
        Has(
          AuthenticatedTwitterUserNotSuspended
        ),
        AnyOf(
          Seq(
            TwitterUserContributingAsSelf,
            Has(principal = ClientApplicationPrivilege(ClientApplicationPrivilegeVariant
              .byId(ClientAppPrivileges.CONTRIBUTORS.toShort).get))
          )),
        AllowWritableAccessToken
      )
    )

}
