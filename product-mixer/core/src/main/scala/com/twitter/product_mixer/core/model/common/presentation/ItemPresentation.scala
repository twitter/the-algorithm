package com.twitter.product_mixer.core.model.common.presentation

trait ItemPresentation extends UniversalPresentation {
  // Optional field which if populated, will group the items into the specified module
  def modulePresentation: Option[ModulePresentation] = None
}
