package com.tw ter.product_m xer.component_l brary.premarshaller.cursor

 mport com.tw ter.product_m xer.component_l brary.model.cursor.OrderedCursor
 mport com.tw ter.product_m xer.component_l brary.model.cursor.PassThroughCursor
 mport com.tw ter.product_m xer.component_l brary.model.cursor.UnorderedBloomF lterCursor
 mport com.tw ter.product_m xer.component_l brary.model.cursor.UnorderedExclude dsCursor
 mport com.tw ter.product_m xer.component_l brary.{thr ftscala => t}
 mport com.tw ter.product_m xer.core.p pel ne.P pel neCursor
 mport com.tw ter.product_m xer.core.p pel ne.P pel neCursorSer al zer
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure. llegalStateFa lure
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.Malfor dCursor
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.P pel neFa lure
 mport com.tw ter.scrooge.B naryThr ftStructSer al zer
 mport com.tw ter.scrooge.Thr ftStructCodec
 mport com.tw ter.search.common.ut l.bloomf lter.Adapt veLong ntBloomF lterSer al zer
 mport com.tw ter.ut l.Base64UrlSafeStr ngEncoder
 mport com.tw ter.ut l.Str ngEncoder
 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.sl ce.CursorTypeMarshaller

/**
 * Handles ser al zat on and deser al zat on for all supported gener c cursors. Note that gener c
 * cursors may be used for Sl ces or any bespoke marshall ng format.
 */
object CursorSer al zer extends P pel neCursorSer al zer[P pel neCursor] {

  pr vate[cursor] val CursorThr ftSer al zer: B naryThr ftStructSer al zer[
    t.ProductM xerRequestCursor
  ] =
    new B naryThr ftStructSer al zer[t.ProductM xerRequestCursor] {
      overr de def codec: Thr ftStructCodec[t.ProductM xerRequestCursor] =
        t.ProductM xerRequestCursor
      overr de def encoder: Str ngEncoder = Base64UrlSafeStr ngEncoder
    }

  overr de def ser al zeCursor(cursor: P pel neCursor): Str ng =
    cursor match {
      case OrderedCursor( d, cursorType, gapBoundary d) =>
        val cursorTypeMarshaller = new CursorTypeMarshaller()
        val thr ftCursor = t.ProductM xerRequestCursor.OrderedCursor(
          t.OrderedCursor(
             d =  d,
            cursorType = cursorType.map(cursorTypeMarshaller.apply),
            gapBoundary d))

        CursorThr ftSer al zer.toStr ng(thr ftCursor)
      case UnorderedExclude dsCursor(excluded ds) =>
        val thr ftCursor = t.ProductM xerRequestCursor.UnorderedExclude dsCursor(
          t.UnorderedExclude dsCursor(excluded ds = So (excluded ds)))

        CursorThr ftSer al zer.toStr ng(thr ftCursor)
      case UnorderedBloomF lterCursor(long ntBloomF lter) =>
        val thr ftCursor = t.ProductM xerRequestCursor.UnorderedBloomF lterCursor(
          t.UnorderedBloomF lterCursor(
            ser al zedLong ntBloomF lter =
              Adapt veLong ntBloomF lterSer al zer.ser al ze(long ntBloomF lter)
          ))

        CursorThr ftSer al zer.toStr ng(thr ftCursor)
      case PassThroughCursor(cursorValue, cursorType) =>
        val cursorTypeMarshaller = new CursorTypeMarshaller()
        val thr ftCursor = t.ProductM xerRequestCursor.PassThroughCursor(
          t.PassThroughCursor(
            cursorValue = cursorValue,
            cursorType = cursorType.map(cursorTypeMarshaller.apply)
          ))

        CursorThr ftSer al zer.toStr ng(thr ftCursor)
      case _ =>
        throw P pel neFa lure( llegalStateFa lure, "Unknown cursor type")
    }

  def deser al zeOrderedCursor(cursorStr ng: Str ng): Opt on[OrderedCursor] =
    deser al zeCursor(
      cursorStr ng,
      {
        case So (
              t.ProductM xerRequestCursor
                .OrderedCursor(t.OrderedCursor( d, cursorType, gapBoundary d))) =>
          val cursorTypeMarshaller = new CursorTypeMarshaller()
          So (
            OrderedCursor(
               d =  d,
              cursorType = cursorType.map(cursorTypeMarshaller.unmarshall),
              gapBoundary d))
      }
    )

  def deser al zeUnorderedExclude dsCursor(
    cursorStr ng: Str ng
  ): Opt on[UnorderedExclude dsCursor] = {
    deser al zeCursor(
      cursorStr ng,
      {
        case So (
              t.ProductM xerRequestCursor
                .UnorderedExclude dsCursor(t.UnorderedExclude dsCursor(excluded dsOpt))) =>
          So (UnorderedExclude dsCursor(excluded ds = excluded dsOpt.getOrElse(Seq.empty)))
      }
    )
  }

  def deser al zeUnorderedBloomF lterCursor(
    cursorStr ng: Str ng
  ): Opt on[UnorderedBloomF lterCursor] =
    deser al zeCursor(
      cursorStr ng,
      {
        case So (
              t.ProductM xerRequestCursor.UnorderedBloomF lterCursor(
                t.UnorderedBloomF lterCursor(ser al zedLong ntBloomF lter))) =>
          val bloomF lter = Adapt veLong ntBloomF lterSer al zer
            .deser al ze(ser al zedLong ntBloomF lter).getOrElse(
              throw P pel neFa lure(
                Malfor dCursor,
                s"Fa led to deser al ze UnorderedBloomF lterCursor from cursor str ng: $cursorStr ng")
            )

          So (UnorderedBloomF lterCursor(long ntBloomF lter = bloomF lter))
      }
    )

  def deser al zePassThroughCursor(cursorStr ng: Str ng): Opt on[PassThroughCursor] =
    deser al zeCursor(
      cursorStr ng,
      {
        case So (
              t.ProductM xerRequestCursor
                .PassThroughCursor(t.PassThroughCursor(cursorValue, cursorType))) =>
          val cursorTypeMarshaller = new CursorTypeMarshaller()
          So (
            PassThroughCursor(
              cursorValue = cursorValue,
              cursorType = cursorType.map(cursorTypeMarshaller.unmarshall)))
      }
    )

  // Note that t  "A" type of t  Part alFunct on cannot be  nferred due to t  thr ft type not
  // be ng present on t  P pel neCursorSer al zer tra . By us ng t  pr vate def w h t 
  // deser al zePf type declared,   can be  nferred.
  pr vate def deser al zeCursor[Cursor <: P pel neCursor](
    cursorStr ng: Str ng,
    deser al zePf: Part alFunct on[Opt on[t.ProductM xerRequestCursor], Opt on[Cursor]]
  ): Opt on[Cursor] =
    P pel neCursorSer al zer.deser al zeCursor(
      cursorStr ng,
      CursorThr ftSer al zer,
      deser al zePf
    )
}
