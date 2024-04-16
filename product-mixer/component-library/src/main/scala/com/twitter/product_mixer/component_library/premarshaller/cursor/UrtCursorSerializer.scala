package com.tw ter.product_m xer.component_l brary.premarshaller.cursor

 mport com.tw ter.product_m xer.component_l brary.model.cursor.UrtOrderedCursor
 mport com.tw ter.product_m xer.component_l brary.model.cursor.UrtPassThroughCursor
 mport com.tw ter.product_m xer.component_l brary.model.cursor.UrtPlaceholderCursor
 mport com.tw ter.product_m xer.component_l brary.model.cursor.UrtUnorderedBloomF lterCursor
 mport com.tw ter.product_m xer.component_l brary.model.cursor.UrtUnorderedExclude dsCursor
 mport com.tw ter.product_m xer.component_l brary.premarshaller.cursor.CursorSer al zer.CursorThr ftSer al zer
 mport com.tw ter.product_m xer.component_l brary.{thr ftscala => t}
 mport com.tw ter.product_m xer.core.p pel ne.P pel neCursorSer al zer.deser al zeCursor
 mport com.tw ter.product_m xer.core.p pel ne.P pel neCursor
 mport com.tw ter.product_m xer.core.p pel ne.P pel neCursorSer al zer
 mport com.tw ter.product_m xer.core.p pel ne.UrtP pel neCursor
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure. llegalStateFa lure
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.Malfor dCursor
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.P pel neFa lure
 mport com.tw ter.search.common.ut l.bloomf lter.Adapt veLong ntBloomF lterSer al zer
 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt.operat on.CursorTypeMarshaller

/**
 * Handles ser al zat on and deser al zat on for all supported URT cursors
 */
object UrtCursorSer al zer extends P pel neCursorSer al zer[UrtP pel neCursor] {

  val Ser al zedUrtPlaceholderCursor = CursorThr ftSer al zer.toStr ng(
    t.ProductM xerRequestCursor.UrtPlaceholderCursor(t.UrtPlaceholderCursor()))

  val cursorTypeMarshaller = new CursorTypeMarshaller()

  overr de def ser al zeCursor(cursor: UrtP pel neCursor): Str ng =
    cursor match {
      case UrtOrderedCursor( n  alSort ndex,  d, cursorType, gapBoundary d) =>
        val thr ftCursor = t.ProductM xerRequestCursor.UrtOrderedCursor(
          t.UrtOrderedCursor(
             n  alSort ndex =  n  alSort ndex,
             d =  d,
            cursorType.map(cursorTypeMarshaller.apply),
            gapBoundary d = gapBoundary d))

        CursorThr ftSer al zer.toStr ng(thr ftCursor)
      case UrtUnorderedExclude dsCursor( n  alSort ndex, excluded ds) =>
        val thr ftCursor = t.ProductM xerRequestCursor.UrtUnorderedExclude dsCursor(
          t.UrtUnorderedExclude dsCursor(
             n  alSort ndex =  n  alSort ndex,
            excluded ds = So (excluded ds)))

        CursorThr ftSer al zer.toStr ng(thr ftCursor)
      case UrtUnorderedBloomF lterCursor( n  alSort ndex, long ntBloomF lter) =>
        val thr ftCursor = t.ProductM xerRequestCursor.UrtUnorderedBloomF lterCursor(
          t.UrtUnorderedBloomF lterCursor(
             n  alSort ndex =  n  alSort ndex,
            ser al zedLong ntBloomF lter =
              Adapt veLong ntBloomF lterSer al zer.ser al ze(long ntBloomF lter)
          ))

        CursorThr ftSer al zer.toStr ng(thr ftCursor)
      case UrtPassThroughCursor( n  alSort ndex, cursorValue, cursorType) =>
        val thr ftCursor = t.ProductM xerRequestCursor.UrtPassThroughCursor(
          t.UrtPassThroughCursor(
             n  alSort ndex =  n  alSort ndex,
            cursorValue = cursorValue,
            cursorType = cursorType.map(cursorTypeMarshaller.apply)
          ))

        CursorThr ftSer al zer.toStr ng(thr ftCursor)
      case UrtPlaceholderCursor() =>
        Ser al zedUrtPlaceholderCursor
      case _ =>
        throw P pel neFa lure( llegalStateFa lure, "Unknown cursor type")
    }

  def deser al zeOrderedCursor(cursorStr ng: Str ng): Opt on[UrtOrderedCursor] = {
    deser al zeUrtCursor(
      cursorStr ng,
      {
        case So (
              t.ProductM xerRequestCursor.UrtOrderedCursor(
                t.UrtOrderedCursor( n  alSort ndex,  d, cursorType, gapBoundary d))) =>
          So (
            UrtOrderedCursor(
               n  alSort ndex =  n  alSort ndex,
               d =  d,
              cursorType = cursorType.map(cursorTypeMarshaller.unmarshall),
              gapBoundary d))
      }
    )
  }

  def deser al zeUnorderedExclude dsCursor(
    cursorStr ng: Str ng
  ): Opt on[UrtUnorderedExclude dsCursor] = {
    deser al zeUrtCursor(
      cursorStr ng,
      {
        case So (
              t.ProductM xerRequestCursor.UrtUnorderedExclude dsCursor(
                t.UrtUnorderedExclude dsCursor( n  alSort ndex, excluded dsOpt))) =>
          So (
            UrtUnorderedExclude dsCursor(
               n  alSort ndex =  n  alSort ndex,
              excluded ds = excluded dsOpt.getOrElse(Seq.empty)))
      }
    )
  }

  def deser al zeUnorderedBloomF lterCursor(
    cursorStr ng: Str ng
  ): Opt on[UrtUnorderedBloomF lterCursor] = {
    deser al zeUrtCursor(
      cursorStr ng,
      {
        case So (
              t.ProductM xerRequestCursor.UrtUnorderedBloomF lterCursor(
                t.UrtUnorderedBloomF lterCursor( n  alSort ndex, ser al zedLong ntBloomF lter))) =>
          val long ntBloomF lter = Adapt veLong ntBloomF lterSer al zer
            .deser al ze(ser al zedLong ntBloomF lter).getOrElse(
              throw P pel neFa lure(
                Malfor dCursor,
                s"Fa led to deser al ze UrtUnorderedBloomF lterCursor from cursor str ng: $cursorStr ng")
            )

          So (
            UrtUnorderedBloomF lterCursor(
               n  alSort ndex =  n  alSort ndex,
              long ntBloomF lter = long ntBloomF lter))
      }
    )
  }

  def deser al zePassThroughCursor(cursorStr ng: Str ng): Opt on[UrtPassThroughCursor] = {
    deser al zeUrtCursor(
      cursorStr ng,
      {
        case So (
              t.ProductM xerRequestCursor
                .UrtPassThroughCursor(
                  t.UrtPassThroughCursor( n  alSort ndex, cursorValue, cursorType))) =>
          So (
            UrtPassThroughCursor(
               n  alSort ndex =  n  alSort ndex,
              cursorValue = cursorValue,
              cursorType = cursorType.map(cursorTypeMarshaller.unmarshall)))
      }
    )
  }

  pr vate def deser al zeUrtCursor[Cursor <: P pel neCursor](
    cursorStr ng: Str ng,
    deser al zePf: Part alFunct on[Opt on[t.ProductM xerRequestCursor], Opt on[Cursor]]
  ): Opt on[Cursor] = {
    deser al zeCursor[t.ProductM xerRequestCursor, Cursor](
      cursorStr ng,
      CursorThr ftSer al zer,
      deser al zePf orElse {
        case So (t.ProductM xerRequestCursor.UrtPlaceholderCursor(t.UrtPlaceholderCursor())) =>
          // Treat subm ted placeholder cursor l ke an  n  al page load
          None
      },
    )
  }
}
