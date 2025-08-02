package com.twitter.product_mitwitterer.core.functional_component.gate

/**
 * A [[Gate]] controls if a pipeline or other component is etwitterecuted.
 *
 * Application logic should usually use `GateResult.continue: Boolean` to interpret a GateResult. `continue` will be
 * true if we should continue with etwitterecution, and false if we should stop.
 *
 * You can case match against the `GateResult` to understand how etwitteractly etwitterecution happened. See `object GateResult`
 * below, but this is useful if you want to know if we are continuing due to the skip or main predicates.
 */
sealed trait GateResult {

  /** Should we continue? */
  val continue: Boolean
}

object GateResult {

  /**
   * Continue Etwitterecution
   *
   * the Skip predicate evaluated to true,
   * so we Skipped etwitterecution of the Main predicate and should continue
   */
  case object Skipped etwittertends GateResult {
    override val continue = true
  }

  /**
   * Continue Etwitterecution
   *
   * the main predicate evaluated to true
   */
  case object Continue etwittertends GateResult {
    override val continue = true
  }

  /**
   * Stop etwitterecution
   *
   * the main predicate evaluated to false
   */
  case object Stop etwittertends GateResult {
    override val continue = false
  }
}
