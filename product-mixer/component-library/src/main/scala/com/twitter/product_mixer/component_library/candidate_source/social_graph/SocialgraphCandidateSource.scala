package com.tw ter.product_m xer.component_l brary.cand date_s ce.soc al_graph

 mport com.tw ter.product_m xer.component_l brary.model.cand date.CursorType
 mport com.tw ter.product_m xer.component_l brary.model.cand date.NextCursor
 mport com.tw ter.product_m xer.component_l brary.model.cand date.Prev ousCursor
 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.strato.StratoKeyV ewFetc rS ce
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateS ce dent f er
 mport com.tw ter.soc algraph.thr ftscala
 mport com.tw ter.soc algraph.thr ftscala. dsRequest
 mport com.tw ter.soc algraph.thr ftscala. dsResult
 mport com.tw ter.soc algraph.ut l.ByteBufferUt l
 mport com.tw ter.strato.cl ent.Fetc r
 mport javax. nject. nject
 mport javax. nject.S ngleton

sealed tra  Soc algraphResponse
case class Soc algraphResult( d: Long) extends Soc algraphResponse
case class Soc algraphCursor(cursor: Long, cursorType: CursorType) extends Soc algraphResponse

@S ngleton
class Soc algraphCand dateS ce @ nject() (
  overr de val fetc r: Fetc r[thr ftscala. dsRequest, Opt on[
    thr ftscala.RequestContext
  ], thr ftscala. dsResult])
    extends StratoKeyV ewFetc rS ce[
      thr ftscala. dsRequest,
      Opt on[thr ftscala.RequestContext],
      thr ftscala. dsResult,
      Soc algraphResponse
    ] {

  overr de val  dent f er: Cand dateS ce dent f er = Cand dateS ce dent f er("Soc algraph")

  overr de def stratoResultTransfor r(
    stratoKey:  dsRequest,
    stratoResult:  dsResult
  ): Seq[Soc algraphResponse] = {
    val prevCursor =
      Soc algraphCursor(ByteBufferUt l.toLong(stratoResult.pageResult.prevCursor), Prev ousCursor)
    /* W n an end cursor  s passed to Soc algraph,
     * Soc algraph returns t  start cursor. To prevent
     * cl ents from c rcularly fetch ng t  t  l ne aga n,
     *  f   see a start cursor returned from Soc algraph,
     *   replace   w h an end cursor.
     */
    val nextCursor = ByteBufferUt l.toLong(stratoResult.pageResult.nextCursor) match {
      case Soc algraphCursorConstants.StartCursor =>
        Soc algraphCursor(Soc algraphCursorConstants.EndCursor, NextCursor)
      case cursor => Soc algraphCursor(cursor, NextCursor)
    }

    stratoResult. ds
      .map {  d =>
        Soc algraphResult( d)
      } ++ Seq(nextCursor, prevCursor)
  }
}
