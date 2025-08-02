package com.twitter.home_mitwitterer.service

import com.twitter.product_mitwitterer.core.functional_component.common.access_policy.AccessPolicy
import com.twitter.product_mitwitterer.core.functional_component.common.access_policy.AllowedLdapGroups

object HomeMitwittererAccessPolicy {

  /**
   * Access policies can be configured on a product-by-product basis but you may also want products
   * to have a common policy.
   */
  val DefaultHomeMitwittererAccessPolicy: Set[AccessPolicy] = Set(AllowedLdapGroups(Set.empty[String]))
}
