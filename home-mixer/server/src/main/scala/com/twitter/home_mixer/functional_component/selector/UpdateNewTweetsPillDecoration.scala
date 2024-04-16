package com.tw ter.ho _m xer.funct onal_component.selector

 mport com.tw ter.ho _m xer.funct onal_component.selector.UpdateNewT etsP llDecorat on.NumAvatars
 mport com.tw ter.ho _m xer.model.Ho Features.Author dFeature
 mport com.tw ter.ho _m xer.model.Ho Features. sRet etFeature
 mport com.tw ter.ho _m xer.model.request.HasDev ceContext
 mport com.tw ter.ho _m xer.param.Ho GlobalParams.EnableNewT etsP llAvatarsParam
 mport com.tw ter.ho _m xer.ut l.Cand datesUt l
 mport com.tw ter.product_m xer.component_l brary.model.cand date.ShowAlertCand date
 mport com.tw ter.product_m xer.component_l brary.model.cand date.T etCand date
 mport com.tw ter.product_m xer.component_l brary.model.presentat on.urt.Urt emPresentat on
 mport com.tw ter.product_m xer.core.funct onal_component.common.Cand dateScope
 mport com.tw ter.product_m xer.core.funct onal_component.selector.Selector
 mport com.tw ter.product_m xer.core.funct onal_component.selector.SelectorResult
 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateW hDeta ls
 mport com.tw ter.product_m xer.core.model.common.presentat on. emCand dateW hDeta ls
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.ShowAlert
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.r chtext.R chText
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.str ngcenter.cl ent.Str ngCenter
 mport com.tw ter.str ngcenter.cl ent.core.ExternalStr ng

object UpdateNewT etsP llDecorat on {
  val NumAvatars = 3
}

case class UpdateNewT etsP llDecorat on[Query <: P pel neQuery w h HasDev ceContext](
  overr de val p pel neScope: Cand dateScope,
  str ngCenter: Str ngCenter,
  seeNewT etsStr ng: ExternalStr ng,
  t etedStr ng: ExternalStr ng)
    extends Selector[Query] {

  overr de def apply(
    query: Query,
    rema n ngCand dates: Seq[Cand dateW hDeta ls],
    result: Seq[Cand dateW hDeta ls]
  ): SelectorResult = {
    val (alerts, ot rCand dates) =
      rema n ngCand dates.part  on(cand date =>
        cand date. sCand dateType[ShowAlertCand date]() && p pel neScope.conta ns(cand date))
    val updatedCand dates = alerts
      .collectF rst {
        case newT etsP ll:  emCand dateW hDeta ls =>
          val user ds = Cand datesUt l
            .get emCand datesW hOnlyModuleLast(result)
            .f lter(cand date =>
              cand date. sCand dateType[T etCand date]() && p pel neScope.conta ns(cand date))
            .f lterNot(_.features.getOrElse( sRet etFeature, false))
            .flatMap(_.features.getOrElse(Author dFeature, None))
            .f lterNot(_ == query.getRequ redUser d)
            .d st nct

          val updatedPresentat on = newT etsP ll.presentat on.map {
            case presentat on: Urt emPresentat on =>
              presentat on.t  l ne em match {
                case alert: ShowAlert =>
                  val text =  f (useAvatars(query, user ds)) t etedStr ng else seeNewT etsStr ng
                  val r chText = R chText(
                    text = str ngCenter.prepare(text),
                    ent  es = L st.empty,
                    rtl = None,
                    al gn nt = None)

                  val updatedAlert =
                    alert.copy(user ds = So (user ds.take(NumAvatars)), r chText = So (r chText))
                  presentat on.copy(t  l ne em = updatedAlert)
              }
          }
          ot rCand dates :+ newT etsP ll.copy(presentat on = updatedPresentat on)
      }.getOrElse(rema n ngCand dates)

    SelectorResult(rema n ngCand dates = updatedCand dates, result = result)
  }

  pr vate def useAvatars(query: Query, user ds: Seq[Long]): Boolean = {
    val enableAvatars = query.params(EnableNewT etsP llAvatarsParam)
    enableAvatars && user ds.s ze >= NumAvatars
  }
}
