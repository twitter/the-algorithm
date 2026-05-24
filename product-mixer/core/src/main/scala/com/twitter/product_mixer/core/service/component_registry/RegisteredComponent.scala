package com.twitter.product_mixer.core.service.component_registry

import com.twitter.product_mixer.core.model.common.Component
import com.twitter.product_mixer.core.model.common.identifier.ComponentIdentifier

object RegisteredComponent {
  // Sort by ComponentIdentifier which has its own implicit ordering defined
  implicit val ordering: Ordering[RegisteredComponent] =
    Ordering.by[RegisteredComponent, ComponentIdentifier](_.component.identifier)
}

case class RegisteredComponent(
  identifier: ComponentIdentifier,
  component: Component,
  sourceFile: String)
