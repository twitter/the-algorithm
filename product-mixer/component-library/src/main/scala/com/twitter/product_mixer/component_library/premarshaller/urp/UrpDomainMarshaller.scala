package com.tw ter.product_m xer.component_l brary.premarshaller.urp

 mport com.tw ter.product_m xer.component_l brary.premarshaller.urp.bu lder.PageBodyBu lder
 mport com.tw ter.product_m xer.component_l brary.premarshaller.urp.bu lder.Page aderBu lder
 mport com.tw ter.product_m xer.component_l brary.premarshaller.urp.bu lder.PageNavBarBu lder
 mport com.tw ter.product_m xer.component_l brary.premarshaller.urp.bu lder.T  l neScr beConf gBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.premarshaller.Doma nMarshaller
 mport com.tw ter.product_m xer.core.model.common. dent f er.Doma nMarshaller dent f er
 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateW hDeta ls
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urp._
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

object UrpDoma nMarshaller {
  val Page dSuff x = "-Page"
}

/**
 * Doma n marshaller that g ven t  bu lders for t  body,  ader and navbar w ll generate a URP Page
 *
 * @param pageBodyBu lder     PageBody bu lder that generates a PageBody w h t  query and select ons
 * @param scr beConf gBu lder Scr be Conf g bu lder that generates t  conf gurat on for scr b ng of t  page
 * @param page aderBu lder   Page ader bu lder that generates a Page ader w h t  query and select ons
 * @param pageNavBarBu lder   PageNavBar bu lder that generates a PageNavBar w h t  query and select ons
 * @tparam Query T  type of Query that t  Marshaller operates w h
 */
case class UrpDoma nMarshaller[-Query <: P pel neQuery](
  pageBodyBu lder: PageBodyBu lder[Query],
  page aderBu lder: Opt on[Page aderBu lder[Query]] = None,
  pageNavBarBu lder: Opt on[PageNavBarBu lder[Query]] = None,
  scr beConf gBu lder: Opt on[T  l neScr beConf gBu lder[Query]] = None,
  overr de val  dent f er: Doma nMarshaller dent f er =
    Doma nMarshaller dent f er("Un f edR chPage"))
    extends Doma nMarshaller[Query, Page] {

  overr de def apply(
    query: Query,
    select ons: Seq[Cand dateW hDeta ls]
  ): Page = {
    val pageBody = pageBodyBu lder.bu ld(query, select ons)
    val page ader = page aderBu lder.flatMap(_.bu ld(query, select ons))
    val pageNavBar = pageNavBarBu lder.flatMap(_.bu ld(query, select ons))
    val scr beConf g = scr beConf gBu lder.flatMap(_.bu ld(query, pageBody, page ader, pageNavBar))

    Page(
       d = query.product. dent f er.toStr ng + UrpDoma nMarshaller.Page dSuff x,
      pageBody = pageBody,
      scr beConf g = scr beConf g,
      page ader = page ader,
      pageNavBar = pageNavBar
    )
  }
}
