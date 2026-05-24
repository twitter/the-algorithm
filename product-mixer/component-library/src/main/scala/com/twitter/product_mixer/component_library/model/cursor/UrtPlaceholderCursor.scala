package com.twitter.product_mixer.component_library.model.cursor

import com.twitter.product_mixer.core.pipeline.UrtPipelineCursor

/**
 * Cursor model that may be used when we just need a placeholder but no real cursor value. Since URT
 * requires that top and bottom cursors are always present, placeholders are often used when up
 * scrolling (PTR) is not supported on a timeline. While placeholder cursors generally should not be
 * submitted back by the client, they sometimes are like in the case of client-side background
 * auto-refresh. If submitted, the backend will treat any request with a placeholder cursor like no
 * cursor was submitted, which will behave the same way as an initial page load.
 */
case class UrtPlaceholderCursor() extends UrtPipelineCursor {
  // This value is unused, in that it is not serialized into the final cursor value
  override def initialSortIndex: Long = throw new UnsupportedOperationException(
    "initialSortIndex is not defined for placeholder cursors")
}
