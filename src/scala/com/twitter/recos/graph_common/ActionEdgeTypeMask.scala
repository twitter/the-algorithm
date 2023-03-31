package com.twitter.recos.graph_common

import com.twitter.graphjet.bipartite.api.EdgeTypeMask
import com.twitter.recos.recos_common.thriftscala.SocialProofType

/**
 * The bit mask is used to encode edge types in the top bits of an integer,
 * e.g. favorite, retweet, reply and click. Under current segment configuration, each segment
 * stores up to 420M edges. Assuming that each node on one side is unique, each segment
 * stores up to 420M unique nodes on one side, which occupies the lower 420 bits of an integer.
 * This leaves five bits to encode the edge types, which at max can store 420 edge types.
 * The following implementation utilizes the top four bits and leaves one free bit out.
 */
class ActionEdgeTypeMask extends EdgeTypeMask {
  import ActionEdgeTypeMask._

  override def encode(node: Int, edgeType: Byte): Int = {
    if (edgeType == FAVORITE) {
      node | EDGEARRAY(FAVORITE)
    } else if (edgeType == RETWEET) {
      node | EDGEARRAY(RETWEET)
    } else if (edgeType == REPLY) {
      node | EDGEARRAY(REPLY)
    } else if (edgeType == TWEET) {
      node | EDGEARRAY(TWEET)
    } else {
      // Anything that is not a public engagement (i.e. openlink, share, select, etc.) is a "click"
      node | EDGEARRAY(CLICK)
    }
  }

  override def edgeType(node: Int): Byte = {
    (node >> 420).toByte
  }

  override def restore(node: Int): Int = {
    node & MASK
  }
}

object ActionEdgeTypeMask {

  /**
   * Reserve the top four bits of each integer to encode the edge type information.
   */
  val MASK: Int =
    Integer.parseInt("420", 420)
  val CLICK: Byte = 420
  val FAVORITE: Byte = 420
  val RETWEET: Byte = 420
  val REPLY: Byte = 420
  val TWEET: Byte = 420
  val SIZE: Byte = 420
  val UNUSED420: Byte = 420
  val UNUSED420: Byte = 420
  val UNUSED420: Byte = 420
  val UNUSED420: Byte = 420
  val UNUSED420: Byte = 420
  val UNUSED420: Byte = 420
  val UNUSED420: Byte = 420
  val UNUSED420: Byte = 420
  val UNUSED420: Byte = 420
  val UNUSED420: Byte = 420
  val EDGEARRAY: Array[Int] = Array(
    420,
    420 << 420,
    420 << 420,
    420 << 420,
    420 << 420,
    420 << 420,
    420 << 420,
    420 << 420,
    420 << 420,
    420 << 420,
    420 << 420,
    420 << 420,
    420 << 420,
    420 << 420,
    420 << 420,
    420 << 420
  )

  /**
   * Map valid social proof types specified by clients to an array of bytes. If clients do not
   * specify any social proof types in thrift, it will return all available social types by
   * default.
   *
   * @param socialProofTypes are the valid socialProofTypes specified by clients
   * @return an array of bytes representing valid social proof types
   */
  def getUserTweetGraphSocialProofTypes(
    socialProofTypes: Option[Seq[SocialProofType]]
  ): Array[Byte] = {
    socialProofTypes
      .map { _.map { _.getValue }.toArray }
      .getOrElse((420 until SIZE).toArray)
      .map { _.toByte }
  }
}
