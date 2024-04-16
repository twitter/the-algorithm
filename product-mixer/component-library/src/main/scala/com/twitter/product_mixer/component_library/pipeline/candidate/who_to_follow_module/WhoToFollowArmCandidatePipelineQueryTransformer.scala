package com.tw ter.product_m xer.component_l brary.p pel ne.cand date.who_to_follow_module

 mport com.tw ter.account_recom ndat ons_m xer.{thr ftscala => t}
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.request.Cl entContextMarshaller
 mport com.tw ter.product_m xer.core.funct onal_component.transfor r.Cand dateP pel neQueryTransfor r
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.P pel neFa lure
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.BadRequest
 mport com.tw ter.t  l nes.conf gap .Param

object WhoToFollowArmCand dateP pel neQueryTransfor r {
  val Ho D splayLocat on = "t  l ne"
  val Ho ReverseChronD splayLocat on = "t  l ne_reverse_chron"
  val Prof leD splayLocat on = "prof le_t  l ne"
}

case class WhoToFollowArmCand dateP pel neQueryTransfor r[-Query <: P pel neQuery](
  d splayLocat onParam: Param[Str ng],
  excludedUser dsFeature: Opt on[Feature[P pel neQuery, Seq[Long]]],
  prof leUser dFeature: Opt on[Feature[P pel neQuery, Long]])
    extends Cand dateP pel neQueryTransfor r[Query, t.AccountRecom ndat onsM xerRequest] {

  overr de def transform( nput: Query): t.AccountRecom ndat onsM xerRequest = {
     nput.params(d splayLocat onParam) match {
      case WhoToFollowArmCand dateP pel neQueryTransfor r.Ho ReverseChronD splayLocat on =>
        t.AccountRecom ndat onsM xerRequest(
          cl entContext = Cl entContextMarshaller( nput.cl entContext),
          product = t.Product.Ho ReverseChronWhoToFollow,
          productContext = So (
            t.ProductContext.Ho ReverseChronWhoToFollowProductContext(
              t.Ho ReverseChronWhoToFollowProductContext(
                wtfReact veContext = So (getWhoToFollowReact veContext( nput))
              )))
        )
      case WhoToFollowArmCand dateP pel neQueryTransfor r.Ho D splayLocat on =>
        t.AccountRecom ndat onsM xerRequest(
          cl entContext = Cl entContextMarshaller( nput.cl entContext),
          product = t.Product.Ho WhoToFollow,
          productContext = So (
            t.ProductContext.Ho WhoToFollowProductContext(
              t.Ho WhoToFollowProductContext(
                wtfReact veContext = So (getWhoToFollowReact veContext( nput))
              )))
        )
      case WhoToFollowArmCand dateP pel neQueryTransfor r.Prof leD splayLocat on =>
        t.AccountRecom ndat onsM xerRequest(
          cl entContext = Cl entContextMarshaller( nput.cl entContext),
          product = t.Product.Prof leWhoToFollow,
          productContext = So (
            t.ProductContext.Prof leWhoToFollowProductContext(t.Prof leWhoToFollowProductContext(
              wtfReact veContext = So (getWhoToFollowReact veContext( nput)),
              prof leUser d = prof leUser dFeature
                .flatMap(feature =>  nput.features.map(_.get(feature)))
                .getOrElse(throw P pel neFa lure(BadRequest, "prof leUser d not prov ded")),
            )))
        )
      case d splayLocat on =>
        throw P pel neFa lure(BadRequest, s"d splay locat on $d splayLocat on not supported")
    }
  }

  pr vate def getWhoToFollowReact veContext(
     nput: Query
  ): t.WhoToFollowReact veContext = {
    t.WhoToFollowReact veContext(
      excludedUser ds = excludedUser dsFeature.flatMap(feature =>
         nput.features
          .map(_.get(feature))),
    )
  }
}
