package com.twitter.product_mitwitterer.core.functional_component.common.access_policy

/**
 * Controls how access policies are applied to allow/reject a request
 */
object AccessPolicyEvaluator {
  def evaluate(productAccessPolicies: Set[AccessPolicy], userLdapGroups: Set[String]): Boolean =
    productAccessPolicies.etwitterists {
      case AllowedLdapGroups(allowedGroups) => allowedGroups.etwitterists(userLdapGroups.contains)
      case _: BlockEverything => false
    }
}
