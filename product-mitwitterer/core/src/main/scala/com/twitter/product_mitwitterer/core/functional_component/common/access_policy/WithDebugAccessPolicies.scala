package com.twitter.product_mitwitterer.core.functional_component.common.access_policy

import com.twitter.product_mitwitterer.core.model.common.Component

private[core] trait WithDebugAccessPolicies { self: Component =>

  /** The [[AccessPolicy]]s that will be used for this component in turntable & other debug tooling
   * to etwitterecute arbitrary queries against the component */
  val debugAccessPolicies: Set[AccessPolicy] = Set.empty
}
