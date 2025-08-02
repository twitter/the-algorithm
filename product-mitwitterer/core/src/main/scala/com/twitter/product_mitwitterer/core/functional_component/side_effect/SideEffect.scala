package com.twitter.product_mitwitterer.core.functional_component.side_effect

import com.twitter.product_mitwitterer.core.model.common.Component
import com.twitter.product_mitwitterer.core.model.common.identifier.SideEffectIdentifier
import com.twitter.stitch.Stitch

/**
 * A side-effect is a ancillary action that doesn't affect the result of etwitterecution directly.
 *
 * For etwitterample: Logging, history stores
 *
 * Implementing components can etwitterpress failures by throwing an etwitterception. These etwitterceptions
 * will be caught and not affect the request processing.
 *
 * @note Side effects etwitterecute asynchronously in a fire-and-forget way, it's important to add alerts
 *       to the [[SideEffect]] component itself since a failures wont show up in metrics
 *       that just monitor your pipeline as a whole.
 *
 * @see [[EtwitterecuteSynchronously]] for modifying a [[SideEffect]] to etwitterecute with synchronously with
 *      the request waiting on the side effect to complete, this will impact the overall request's latency
 **/
trait SideEffect[-Inputs] etwittertends Component {

  /** @see [[SideEffectIdentifier]] */
  override val identifier: SideEffectIdentifier

  def apply(inputs: Inputs): Stitch[Unit]
}
