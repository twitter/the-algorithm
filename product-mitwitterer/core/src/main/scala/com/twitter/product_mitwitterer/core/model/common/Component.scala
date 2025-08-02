package com.twitter.product_mitwitterer.core.model.common

import com.twitter.product_mitwitterer.core.functional_component.common.alert.Alert
import com.twitter.product_mitwitterer.core.model.common.identifier.ComponentIdentifier
import com.twitter.product_mitwitterer.core.model.common.identifier.HasComponentIdentifier

/**
 * Components are very generically reusable composable pieces
 * Components are uniquely identifiable and centrally registered
 */
trait Component etwittertends HasComponentIdentifier {

  /** @see [[ComponentIdentifier]] */
  override val identifier: ComponentIdentifier

  /** the [[Alert]]s that will be used for this component. */
  val alerts: Seq[Alert] = Seq.empty
}
