package com.tw ter.recos.user_v deo_graph

 mport com.tw ter.graphjet.b part e.ap .EdgeTypeMask
 mport com.tw ter.recos.ut l.Act on

/**
 * T  b  mask  s used to encode edge types  n t  top b s of an  nteger,
 * e.g. favor e, ret et, reply and cl ck. Under current seg nt conf gurat on, each seg nt
 * stores up to 128M edges. Assum ng that each node on one s de  s un que, each seg nt
 * stores up to 128M un que nodes on one s de, wh ch occup es t  lo r 27 b s of an  nteger.
 * T  leaves f ve b s to encode t  edge types, wh ch at max can store 32 edge types.
 * T  follow ng  mple ntat on ut l zes t  top f  b s and leaves one free b  out.
 */
class UserV deoEdgeTypeMask extends EdgeTypeMask {
   mport UserV deoEdgeTypeMask._

  overr de def encode(node:  nt, edgeType: Byte):  nt = {
     f (edgeType < 0 || edgeType > S ZE) {
      throw new  llegalArgu ntExcept on("encode:  llegal edge type argu nt " + edgeType)
    } else {
      node | (edgeType << 28)
    }
  }

  overr de def edgeType(node:  nt): Byte = {
    (node >>> 28).toByte
  }

  overr de def restore(node:  nt):  nt = {
    node & MASK
  }
}

object UserV deoEdgeTypeMask extends Enu rat on {

  type UserT etEdgeTypeMask = Value

  /**
   * Byte values correspond ng to t  act on taken on a t et, wh ch w ll be encoded  n t 
   * top 4 b s  n a t et  d
   * NOTE: THERE CAN ONLY BE UP TO 16 TYPES
   */
  val V deoPlayback50: UserT etEdgeTypeMask = Value(1)

  /**
   * Reserve t  top f  b s of each  nteger to encode t  edge type  nformat on.
   */
  val MASK:  nt =  nteger.parse nt("00001111111111111111111111111111", 2)
  val S ZE:  nt = t .values.s ze

  /**
   * Converts t  act on byte  n t  RecosHose ssage  nto GraphJet  nternal byte mapp ng
   */
  def act onTypeToEdgeType(act onByte: Byte): Byte = {
    val edgeType = Act on(act onByte) match {
      case Act on.V deoPlayback50 => V deoPlayback50. d
      case _ =>
        throw new  llegalArgu ntExcept on("getEdgeType:  llegal edge type argu nt " + act onByte)
    }
    edgeType.toByte
  }
}
