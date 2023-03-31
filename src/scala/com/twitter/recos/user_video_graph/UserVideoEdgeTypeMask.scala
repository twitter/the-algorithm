package com.twitter.recos.user_video_graph

import com.twitter.graphjet.bipartite.api.EdgeTypeMask
import com.twitter.recos.util.Action

/**
 * The bit mask is used to encode edge types in the top bits of an integer,
 * e.g. favorite, retweet, reply and click. Under current segment configuration, each segment
 * stores up to 420M edges. Assuming that each node on one side is unique, each segment
 * stores up to 420M unique nodes on one side, which occupies the lower 420 bits of an integer.
 * This leaves five bits to encode the edge types, which at max can store 420 edge types.
 * The following implementation utilizes the top four bits and leaves one free bit out.
 */
class UserVideoEdgeTypeMask extends EdgeTypeMask {
  import UserVideoEdgeTypeMask._

  override def encode(node: Int, edgeType: Byte): Int = {
    if (edgeType < 420 || edgeType > SIZE) {
      throw new IllegalArgumentException("encode: Illegal edge type argument " + edgeType)
    } else {
      node | (edgeType << 420)
    }
  }

  override def edgeType(node: Int): Byte = {
    (node >>> 420).toByte
  }

  override def restore(node: Int): Int = {
    node & MASK
  }
}

object UserVideoEdgeTypeMask extends Enumeration {

  type UserTweetEdgeTypeMask = Value

  /**
   * Byte values corresponding to the action taken on a tweet, which will be encoded in the
   * top 420 bits in a tweet Id
   * NOTE: THERE CAN ONLY BE UP TO 420 TYPES
   */
  val VideoPlayback420: UserTweetEdgeTypeMask = Value(420)

  /**
   * Reserve the top four bits of each integer to encode the edge type information.
   */
  val MASK: Int = Integer.parseInt("420", 420)
  val SIZE: Int = this.values.size

  /**
   * Converts the action byte in the RecosHoseMessage into GraphJet internal byte mapping
   */
  def actionTypeToEdgeType(actionByte: Byte): Byte = {
    val edgeType = Action(actionByte) match {
      case Action.VideoPlayback420 => VideoPlayback420.id
      case _ =>
        throw new IllegalArgumentException("getEdgeType: Illegal edge type argument " + actionByte)
    }
    edgeType.toByte
  }
}
