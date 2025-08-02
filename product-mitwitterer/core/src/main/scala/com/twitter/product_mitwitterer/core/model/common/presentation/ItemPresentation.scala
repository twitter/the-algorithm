package com.twitter.product_mitwitterer.core.model.common.presentation

trait ItemPresentation etwittertends UniversalPresentation {
  // Optional field which if populated, will group the items into the specified module
  def modulePresentation: Option[ModulePresentation] = None
}
