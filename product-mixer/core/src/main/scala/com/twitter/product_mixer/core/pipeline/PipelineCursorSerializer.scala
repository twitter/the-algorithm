package com.tw ter.product_m xer.core.p pel ne

 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.Malfor dCursor
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.P pel neFa lure
 mport com.tw ter.scrooge.B naryThr ftStructSer al zer
 mport com.tw ter.scrooge.Thr ftStruct
 mport com.tw ter.ut l.Return
 mport com.tw ter.ut l.Throw
 mport com.tw ter.ut l.Try

/**
 * Ser al zes a [[P pel neCursor]]  nto thr ft and t n  nto a base64 encoded str ng
 */
tra  P pel neCursorSer al zer[-Cursor <: P pel neCursor] {
  def ser al zeCursor(cursor: Cursor): Str ng
}

object P pel neCursorSer al zer {

  /**
   * Deser al zes a cursor str ng  nto thr ft and t n  nto a [[P pel neCursor]]
   *
   * @param cursorStr ng to deser al ze, wh ch  s base64 encoded thr ft
   * @param cursorThr ftSer al zer to deser al ze t  cursor str ng  nto thr ft
   * @param deser al zePf spec f es how to transform t  ser al zed thr ft  nto a [[P pel neCursor]]
   * @return opt onal [[P pel neCursor]]. `None` may or may not be a fa lure depend ng on t 
   *          mple ntat on of deser al zePf.
   *
   * @note T  "A" type of deser al zePf cannot be  nferred due to t  thr ft type not be ng present
   *       on t  P pel neCursorSer al zer tra . T refore  nvokers must often add an expl c  type
   *       on t  deser al zeCursor call to  lp out t  comp ler w n pass ng deser al zePf  nl ne.
   *       Alternat vely, deser al zePf can be declared as a val w h a type annotat on before    s
   *       passed  nto t   thod.
   */
  def deser al zeCursor[Thr ft <: Thr ftStruct, Cursor <: P pel neCursor](
    cursorStr ng: Str ng,
    cursorThr ftSer al zer: B naryThr ftStructSer al zer[Thr ft],
    deser al zePf: Part alFunct on[Opt on[Thr ft], Opt on[Cursor]]
  ): Opt on[Cursor] = {
    val thr ftCursor: Opt on[Thr ft] =
      Try {
        cursorThr ftSer al zer.fromStr ng(cursorStr ng)
      } match {
        case Return(thr ftCursor) => So (thr ftCursor)
        case Throw(_) => None
      }

    // Add type annotat on to  lp out t  comp ler s nce t  type  s lost due to t  _ match
    val defaultDeser al zePf: Part alFunct on[Opt on[Thr ft], Opt on[Cursor]] = {
      case _ =>
        // T  case  s t  result of t  cl ent subm t ng a cursor   do not expect
        throw P pel neFa lure(Malfor dCursor, s"Unknown request cursor: $cursorStr ng")
    }

    (deser al zePf orElse defaultDeser al zePf)(thr ftCursor)
  }
}
