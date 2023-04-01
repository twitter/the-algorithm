package com.twitter.product_mixer.core.functional_component.common.access_policy

/**
 * Controls how access policies are applied to allow/reject a request
 */
object AccessPolicyEvaluator {
  def evaluate(productAccessPolicies: Set[AccessPolicy], userLdapGroups: Set[String]): Boolean =
    productAccessPolicies.exists {
      case AllowedLdapGroups(allowedGroups) => allowedGroups.exists(userLdapGroups.contains)
      case _: BlockEverything => false
    }
}
