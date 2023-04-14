try {
package com.twitter.product_mixer.core.model.marshalling.response.urt.item.tombstone

sealed trait TombstoneDisplayType

case object TweetUnavailable extends TombstoneDisplayType
case object DisconnectedRepliesAncestor extends TombstoneDisplayType
case object DisconnectedRepliesDescendant extends TombstoneDisplayType
case object Inline extends TombstoneDisplayType
case object NonCompliant extends TombstoneDisplayType

} catch {
  case e: Exception =>
}
