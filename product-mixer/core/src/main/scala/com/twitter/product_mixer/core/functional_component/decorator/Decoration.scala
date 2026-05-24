package com.twitter.product_mixer.core.functional_component.decorator

import com.twitter.product_mixer.core.model.common.UniversalNoun
import com.twitter.product_mixer.core.model.common.presentation.UniversalPresentation

/**
 * Decoration associates specific [[UniversalPresentation]] with a candidate
 */
case class Decoration(
  candidate: UniversalNoun[Any],
  presentation: UniversalPresentation)
