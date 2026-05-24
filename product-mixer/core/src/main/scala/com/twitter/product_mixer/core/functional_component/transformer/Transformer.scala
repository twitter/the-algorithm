package com.twitter.product_mixer.core.functional_component.transformer

import com.twitter.product_mixer.core.model.common.Component
import com.twitter.product_mixer.core.model.common.identifier.TransformerIdentifier

/**
 * A transformer is a synchronous transformation that takes the provided [[Input]] and returns some
 * defined [[Output]]. For example, extracting a score from from a scored candidates.
 */
trait Transformer[-Inputs, +Output] extends Component {
  override val identifier: TransformerIdentifier

  /** Takes [[Inputs]] and transformers them into some [[Output]] of your choosing. */
  def transform(input: Inputs): Output
}
