package com.tw ter.ho _m xer.model

 mport com.tw ter.ho _m xer.funct onal_component.cand date_s ce.Earlyb rdBottomT etFeature
 mport com.tw ter.ho _m xer.funct onal_component.cand date_s ce.Earlyb rdResponseTruncatedFeature
 mport com.tw ter.product_m xer.component_l brary.model.cursor.UrtOrderedCursor
 mport com.tw ter.product_m xer.component_l brary.premarshaller.urt.bu lder. nclude nstruct on
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l neEntry
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l neModule
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.t et.T et em
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.operat on.GapCursor
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.operat on.TopCursor
 mport com.tw ter.product_m xer.core.p pel ne.HasP pel neCursor
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

/**
 * Determ ne w t r to  nclude a Gap Cursor  n t  response based on w t r a t  l ne
 *  s truncated because   has more entr es than t  max response s ze.
 * T re are two ways t  can happen:
 *  1) T re are unused entr es  n Earlyb rd. T   s determ ned by a flag returned from Earlyb rd.
 *       respect t  Earlyb rd flag only  f t re are so  entr es after dedup ng and f lter ng
 *     to ensure that   do not get stuck repeatedly serv ng gaps wh ch lead to no t ets.
 *  2) Ads  nject on can take t  response s ze over t  max count. Goldf nch truncates t et
 *     entr es  n t  case.   can c ck  f t  bottom t et from Earlyb rd  s  n t  response to
 *     determ ne  f all Earlyb rd t ets have been used.
 *
 * Wh le scroll ng down to get older t ets (BottomCursor), responses w ll generally be
 * truncated, but   don't want to render a gap cursor t re, so   need to ensure   only
 * apply t  truncat on c ck to ne r (TopCursor) or m ddle (GapCursor) requests.
 *
 *   return e  r a Gap Cursor or a Bottom Cursor, but not both, so t   nclude  nstruct on
 * for Bottom should be t   nverse of Gap.
 */
object Gap nclude nstruct on
    extends  nclude nstruct on[P pel neQuery w h HasP pel neCursor[UrtOrderedCursor]] {

  overr de def apply(
    query: P pel neQuery w h HasP pel neCursor[UrtOrderedCursor],
    entr es: Seq[T  l neEntry]
  ): Boolean = {
    val wasTruncated = query.features.ex sts(_.getOrElse(Earlyb rdResponseTruncatedFeature, false))

    // Get oldest t et or t ets w h n oldest conversat on module
    val t etEntr es = entr es.v ew.reverse
      .collectF rst {
        case  em: T et em  f  em.promoted tadata. sEmpty => Seq( em. d.toStr ng)
        case module: T  l neModule  f module. ems. ad. em. s nstanceOf[T et em] =>
          module. ems.map(_. em. d.toStr ng)
      }.toSeq.flatten

    val bottomCursor =
      query.features.flatMap(_.getOrElse(Earlyb rdBottomT etFeature, None)).map(_.toStr ng)

    // Ads truncat on happened  f   have at least max count entr es and bottom t et  s m ss ng
    val adsTruncat on = query.requestedMaxResults.ex sts(_ <= entr es.s ze) &&
      !bottomCursor.ex sts(t etEntr es.conta ns)

    query.p pel neCursor.ex sts(_.cursorType match {
      case So (TopCursor) | So (GapCursor) =>
        (wasTruncated && t etEntr es.nonEmpty) || adsTruncat on
      case _ => false
    })
  }
}
