package com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder. em.alert

 mport com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder. em.alert.ShowAlertCand dateUrt emBu lder.ShowAlertCl entEvent nfoEle nt
 mport com.tw ter.product_m xer.component_l brary.model.cand date.ShowAlertCand date
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder.Cand dateUrtEntryBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. em.alert.BaseDurat onBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. em.alert.BaseShowAlertColorConf gurat onBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. em.alert.BaseShowAlertD splayLocat onBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. em.alert.BaseShowAlert conD splay nfoBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. em.alert.BaseShowAlertNav gat on tadataBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. em.alert.BaseShowAlertUser dsBu lder
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.ShowAlert
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. tadata.BaseCl entEvent nfoBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder.r chtext.BaseR chTextBu lder
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.alert.ShowAlertType

object ShowAlertCand dateUrt emBu lder {
  val ShowAlertCl entEvent nfoEle nt: Str ng = "showAlert"
}

case class ShowAlertCand dateUrt emBu lder[-Query <: P pel neQuery](
  alertType: ShowAlertType,
  d splayLocat onBu lder: BaseShowAlertD splayLocat onBu lder[Query],
  colorConf gBu lder: BaseShowAlertColorConf gurat onBu lder[Query],
  tr ggerDelayBu lder: Opt on[BaseDurat onBu lder[Query]] = None,
  d splayDurat onBu lder: Opt on[BaseDurat onBu lder[Query]] = None,
  cl entEvent nfoBu lder: Opt on[BaseCl entEvent nfoBu lder[Query, ShowAlertCand date]] = None,
  collapseDelayBu lder: Opt on[BaseDurat onBu lder[Query]] = None,
  user dsBu lder: Opt on[BaseShowAlertUser dsBu lder[Query]] = None,
  r chTextBu lder: Opt on[BaseR chTextBu lder[Query, ShowAlertCand date]] = None,
   conD splay nfoBu lder: Opt on[BaseShowAlert conD splay nfoBu lder[Query]] = None,
  nav gat on tadataBu lder: Opt on[BaseShowAlertNav gat on tadataBu lder[Query]] = None)
    extends Cand dateUrtEntryBu lder[
      Query,
      ShowAlertCand date,
      ShowAlert
    ] {

  overr de def apply(
    query: Query,
    cand date: ShowAlertCand date,
    features: FeatureMap,
  ): ShowAlert = ShowAlert(
     d = cand date. d,
    sort ndex = None,
    alertType = alertType,
    tr ggerDelay = tr ggerDelayBu lder.flatMap(_.apply(query, cand date, features)),
    d splayDurat on = d splayDurat onBu lder.flatMap(_.apply(query, cand date, features)),
    cl entEvent nfo = cl entEvent nfoBu lder.flatMap(
      _.apply(query, cand date, features, So (ShowAlertCl entEvent nfoEle nt))),
    collapseDelay = collapseDelayBu lder.flatMap(_.apply(query, cand date, features)),
    user ds = user dsBu lder.flatMap(_.apply(query, cand date, features)),
    r chText = r chTextBu lder.map(_.apply(query, cand date, features)),
     conD splay nfo =  conD splay nfoBu lder.flatMap(_.apply(query, cand date, features)),
    d splayLocat on = d splayLocat onBu lder(query, cand date, features),
    colorConf g = colorConf gBu lder(query, cand date, features),
    nav gat on tadata = nav gat on tadataBu lder.flatMap(_.apply(query, cand date, features)),
  )
}
