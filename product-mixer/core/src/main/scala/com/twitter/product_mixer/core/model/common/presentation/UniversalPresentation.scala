package com.twitter.product_mixer.core.model.common.presentation

/**
 * Encapsulates information about how to present a Candidate
 *
 * Implementations of a [[UniversalPresentation]] contain information about how to present the Candidate.
 * This extra information can be in fields in the implementations or in their types.
 *
 * For instance, a Tweet candidate that will be displayed as a URT Tweet Item will be decorated with a
 * [[UniversalPresentation]] implementation that reflects the presentation such as
 * [[com.twitter.product_mixer.component_library.model.presentation.urt.UrtItemPresentation]]
 *
 * @see [[com.twitter.product_mixer.core.functional_component.decorator.CandidateDecorator]] for associating a
 *      [[UniversalPresentation]] with a Candidate.
 */
trait UniversalPresentation
