package com.twitter.recos.graph_common

import com.twitter.graphjet.bipartite.api.EdgeTypeMask
import com.twitter.recos.recos_common.thriftscala.SocialProofType

/**
 * The bit mask is used to encode edge types in the top bits of an integer,
 * e.g. favorite, retweet, reply and click. Under current segment configuration, each segment
 * stores up to 128M edges. Assuming that each node on one side is unique, each segment
 * stores up to 128M unique nodes on one side, which occupies the lower 27 bits of an integer.
 * This leaves five bits to encode the edge types, which at max can store 32 edge types.
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
    (node >> 28).toByte
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
    Integer.parseInt("00001111111111111111111111111111", 2)
  val CLICK: Byte = 0
  val FAVORITE: Byte = 1
  val RETWEET: Byte = 2
  val REPLY: Byte = 3
  val TWEET: Byte = 4
  val SIZE: Byte = 5
  val UNUSED6: Byte = 6
  val UNUSED7: Byte = 7
  val UNUSED8: Byte = 8
  val UNUSED9: Byte = 9
  val UNUSED10: Byte = 10
  val UNUSED11: Byte = 11
  val UNUSED12: Byte = 12
  val UNUSED13: Byte = 13
  val UNUSED14: Byte = 14
  val UNUSED15: Byte = 15
  val EDGEARRAY: Array[Int] = Array(
    0,
    1 << 28,
    2 << 28,
    3 << 28,
    4 << 28,
    5 << 28,
    6 << 28,
    7 << 28,
    8 << 28,
    9 << 28,
    10 << 28,
    11 << 28,
    12 << 28,
    13 << 28,
    14 << 28,
    15 << 28
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
      .getOrElse((0 until SIZE).toArray)
      .map { _.toByte }
  }
}
