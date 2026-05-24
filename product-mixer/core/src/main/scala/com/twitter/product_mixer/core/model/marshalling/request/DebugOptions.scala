package com.twitter.product_mixer.core.model.marshalling.request

import com.twitter.util.Time

trait DebugOptions {
  // Manually override the request time which is useful for writing deterministic Feature tests,
  // since Feature tests do not support mocking Time. For example, URT sort indexes start with a
  // Snowflake ID based on request time if no initialSortIndex is set on the request cursor, so to
  // write a Feature test for this scenario, we can manually set the request time to use here.
  def requestTimeOverride: Option[Time] = None
}

trait HasDebugOptions {
  def debugOptions: Option[DebugOptions]
}
