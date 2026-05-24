package com.twitter.product_mixer.core.model.common.presentation.urt

import com.twitter.product_mixer.core.model.marshalling.response.urt.ModuleItemTreeDisplay

/*
 * Tree state declaring item’s parent relationship with any other items in
 * the module, any display indentation information, and/or collapsed display state.
 */
trait WithItemTreeDisplay { self: BaseUrtItemPresentation =>
  def treeDisplay: Option[ModuleItemTreeDisplay]
}
