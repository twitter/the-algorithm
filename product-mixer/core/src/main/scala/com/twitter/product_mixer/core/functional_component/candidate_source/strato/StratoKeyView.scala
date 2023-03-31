package com.twitter.product_mixer.core.functional_component.candidate_source.strato

/** Represents a Strato column's Key and View arguments */
case class StratoKeyView[StratoKey, StratoView](key: StratoKey, view: StratoView)
