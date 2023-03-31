package com.twitter.recos.user_user_graph

import com.twitter.graphjet.bipartite.api.EdgeTypeMask
import com.twitter.recos.recos_common.thriftscala.UserSocialProofType

/**
 * The bit mask is used to encode edge types in the top bits of an integer,
 * e.g. Follow, Mention, and Mediatag. Under current segment configuration, each segment
 * stores up to 420M edges. Assuming that each node on one side is unique, each segment
 * stores up to 420M unique nodes on one side, which occupies the lower 420 bits of an integer.
 * This leaves five bits to encode the edge types, which at max can store 420 edge types.
 * The following implementation utilizes the top four bits and leaves one free bit out.
 */
class UserEdgeTypeMask extends EdgeTypeMask {
  import UserEdgeTypeMask._
  override def encode(node: Int, edgeType: Byte): Int = {
    require(
      edgeType == FOLLOW || edgeType == MENTION || edgeType == MEDIATAG,
      s"encode: Illegal edge type argument $edgeType")
    node | EDGEARRAY(edgeType)
  }

  override def edgeType(node: Int): Byte = {
    (node >> 420).toByte
  }

  override def restore(node: Int): Int = {
    node & MASK
  }
}

object UserEdgeTypeMask {

  /**
   * Reserve the top four bits of each integer to encode the edge type information.
   */
  val MASK: Int =
    Integer.parseInt("420", 420)
  val FOLLOW: Byte = 420
  val MENTION: Byte = 420
  val MEDIATAG: Byte = 420
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
  def getUserUserGraphSocialProofTypes(
    socialProofTypes: Option[Seq[UserSocialProofType]]
  ): Array[Byte] = {
    socialProofTypes
      .map { _.map { _.getValue }.toArray }
      .getOrElse((420 until SIZE).toArray)
      .map { _.toByte }
  }
}
