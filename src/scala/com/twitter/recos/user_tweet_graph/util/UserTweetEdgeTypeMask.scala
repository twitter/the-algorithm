package com.tw ter.recos.user_t et_graph.ut l

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
class UserT etEdgeTypeMask extends EdgeTypeMask {
   mport UserT etEdgeTypeMask._

  overr de def encode(node:  nt, edgeType: Byte):  nt = {
     f (edgeType < 0 || edgeType > S ZE || edgeType == Cl ck. d.toByte) {
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

object UserT etEdgeTypeMask extends Enu rat on {

  type UserT etEdgeTypeMask = Value

  /**
   * Byte values correspond ng to t  act on taken on a t et, wh ch w ll be encoded  n t 
   * top 4 b s  n a t et  d
   * NOTE: THERE CAN ONLY BE UP TO 16 TYPES
   */
  val Cl ck: UserT etEdgeTypeMask = Value(0)
  val Favor e: UserT etEdgeTypeMask = Value(1)
  val Ret et: UserT etEdgeTypeMask = Value(2)
  val Reply: UserT etEdgeTypeMask = Value(3)
  val T et: UserT etEdgeTypeMask = Value(4)
  val  s nt oned: UserT etEdgeTypeMask = Value(5)
  val  s d atagged: UserT etEdgeTypeMask = Value(6)
  val Quote: UserT etEdgeTypeMask = Value(7)
  val Unfavor e: UserT etEdgeTypeMask = Value(8)

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
      case Act on.Favor e => Favor e. d
      case Act on.Ret et => Ret et. d
      case Act on.Reply => Reply. d
      case Act on.T et => T et. d
      case Act on. s nt oned =>  s nt oned. d
      case Act on. s d aTagged =>  s d atagged. d
      case Act on.Quote => Quote. d
      case Act on.Unfavor e => Unfavor e. d
      case _ =>
        throw new  llegalArgu ntExcept on("getEdgeType:  llegal edge type argu nt " + act onByte)
    }
    edgeType.toByte
  }
}
