package com.X.home_mixer.service

import com.X.product_mixer.core.functional_component.common.access_policy.AccessPolicy
import com.X.product_mixer.core.functional_component.common.access_policy.AllowedLdapGroups

object HomeMixerAccessPolicy {

  /**
   * Access policies can be configured on a product-by-product basis but you may also want products
   * to have a common policy.
   */
  val DefaultHomeMixerAccessPolicy: Set[AccessPolicy] = Set(AllowedLdapGroups(Set.empty[String]))
}
