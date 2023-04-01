package com.twitter.product_mixer.core.model.common

import com.twitter.product_mixer.core.functional_component.common.alert.Alert
import com.twitter.product_mixer.core.model.common.identifier.ComponentIdentifier
import com.twitter.product_mixer.core.model.common.identifier.HasComponentIdentifier

/**
 * Components are very generically reusable composable pieces
 * Components are uniquely identifiable and centrally registered
 */
trait Component extends HasComponentIdentifier {

  /** @see [[ComponentIdentifier]] */
  override val identifier: ComponentIdentifier

  /** the [[Alert]]s that will be used for this component. */
  val alerts: Seq[Alert] = Seq.empty
}
