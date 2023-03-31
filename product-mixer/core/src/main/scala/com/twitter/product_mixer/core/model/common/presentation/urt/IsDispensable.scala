package com.twitter.product_mixer.core.model.common.presentation.urt

/**
 * Whether an item is considered dispensable within a module.
 * Dispensable module items should never be left as the final remaining
 * items within a module. Whenever a module would be left with only
 * dispensable contents (through removal or dismissal of other items) the
 * entire module should be discarded as if contained 0 items.
 *
 * @see http://go/urtDispensableModuleItems
 */
trait IsDispensable { self: BaseUrtItemPresentation =>
  def dispensable: Boolean
}
