package com.tw ter.recos.user_user_graph

 mport com.tw ter.graphjet.b part e.ap .EdgeTypeMask
 mport com.tw ter.recos.recos_common.thr ftscala.UserSoc alProofType

/**
 * T  b  mask  s used to encode edge types  n t  top b s of an  nteger,
 * e.g. Follow,  nt on, and  d atag. Under current seg nt conf gurat on, each seg nt
 * stores up to 128M edges. Assum ng that each node on one s de  s un que, each seg nt
 * stores up to 128M un que nodes on one s de, wh ch occup es t  lo r 27 b s of an  nteger.
 * T  leaves f ve b s to encode t  edge types, wh ch at max can store 32 edge types.
 * T  follow ng  mple ntat on ut l zes t  top f  b s and leaves one free b  out.
 */
class UserEdgeTypeMask extends EdgeTypeMask {
   mport UserEdgeTypeMask._
  overr de def encode(node:  nt, edgeType: Byte):  nt = {
    requ re(
      edgeType == FOLLOW || edgeType == MENT ON || edgeType == MED ATAG,
      s"encode:  llegal edge type argu nt $edgeType")
    node | EDGEARRAY(edgeType)
  }

  overr de def edgeType(node:  nt): Byte = {
    (node >> 28).toByte
  }

  overr de def restore(node:  nt):  nt = {
    node & MASK
  }
}

object UserEdgeTypeMask {

  /**
   * Reserve t  top f  b s of each  nteger to encode t  edge type  nformat on.
   */
  val MASK:  nt =
     nteger.parse nt("00001111111111111111111111111111", 2)
  val FOLLOW: Byte = 0
  val MENT ON: Byte = 1
  val MED ATAG: Byte = 2
  val S ZE: Byte = 3
  val UNUSED3: Byte = 3
  val UNUSED4: Byte = 4
  val UNUSED5: Byte = 5
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
  val EDGEARRAY: Array[ nt] = Array(
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
   * Map val d soc al proof types spec f ed by cl ents to an array of bytes.  f cl ents do not
   * spec fy any soc al proof types  n thr ft,   w ll return all ava lable soc al types by
   * default.
   *
   * @param soc alProofTypes are t  val d soc alProofTypes spec f ed by cl ents
   * @return an array of bytes represent ng val d soc al proof types
   */
  def getUserUserGraphSoc alProofTypes(
    soc alProofTypes: Opt on[Seq[UserSoc alProofType]]
  ): Array[Byte] = {
    soc alProofTypes
      .map { _.map { _.getValue }.toArray }
      .getOrElse((0 unt l S ZE).toArray)
      .map { _.toByte }
  }
}
