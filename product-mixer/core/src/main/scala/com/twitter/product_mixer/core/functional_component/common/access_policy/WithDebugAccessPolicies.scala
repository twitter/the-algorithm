package com.twitter.product_mixer.core.functional_component.common.access_policy

import com.twitter.product_mixer.core.model.common.Component

private[core] trait WithDebugAccessPolicies { self: Component =>

  /** The [[AccessPolicy]]s that will be used for this component in turntable & other debug tooling
   * to execute arbitrary queries against the component */
  val debugAccessPolicies: Set[AccessPolicy] = Set.empty
}
