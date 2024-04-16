package com.tw ter.ho _m xer.cand date_p pel ne

 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.ho _m xer.funct onal_component.gate.RequestContextNotGate
 mport com.tw ter.ho _m xer.model.Ho Features.GetNe rFeature
 mport com.tw ter.ho _m xer.model.request.Dev ceContext
 mport com.tw ter.ho _m xer.model.request.HasDev ceContext
 mport com.tw ter.ho _m xer.serv ce.Ho M xerAlertConf g
 mport com.tw ter.product_m xer.component_l brary.decorator.urt.Urt emCand dateDecorator
 mport com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder. em.alert.Durat onParamBu lder
 mport com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder. em.alert.ShowAlertCand dateUrt emBu lder
 mport com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder. em.alert.Stat cShowAlertColorConf gurat onBu lder
 mport com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder. em.alert.Stat cShowAlertD splayLocat onBu lder
 mport com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder. em.alert.Stat cShowAlert conD splay nfoBu lder
 mport com.tw ter.product_m xer.component_l brary.gate.FeatureGate
 mport com.tw ter.product_m xer.component_l brary.model.cand date.ShowAlertCand date
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.Cand dateS ce
 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.Stat cCand dateS ce
 mport com.tw ter.product_m xer.core.funct onal_component.conf gap .Stat cParam
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.Cand dateDecorator
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. em.alert.BaseDurat onBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.gate.Gate
 mport com.tw ter.product_m xer.core.funct onal_component.transfor r.Cand dateP pel neQueryTransfor r
 mport com.tw ter.product_m xer.core.funct onal_component.transfor r.Cand dateP pel neResultsTransfor r
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateP pel ne dent f er
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateS ce dent f er
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.alert.NewT ets
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.alert.ShowAlertColorConf gurat on
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.alert.ShowAlert conD splay nfo
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.alert.Top
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.alert.UpArrow
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.color.Tw terBlueRosettaColor
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.color.Wh eRosettaColor
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.p pel ne.cand date.DependentCand dateP pel neConf g
 mport com.tw ter.ut l.Durat on
 mport javax. nject. nject
 mport javax. nject.S ngleton

/**
 * Cand date P pel ne Conf g that creates t  New T ets P ll
 */
@S ngleton
class NewT etsP llCand dateP pel neConf g[Query <: P pel neQuery w h HasDev ceContext] @ nject() (
) extends DependentCand dateP pel neConf g[
      Query,
      Un ,
      ShowAlertCand date,
      ShowAlertCand date
    ] {
   mport NewT etsP llCand dateP pel neConf g._

  overr de val  dent f er: Cand dateP pel ne dent f er =
    Cand dateP pel ne dent f er("NewT etsP ll")

  overr de val gates: Seq[Gate[Query]] = Seq(
    RequestContextNotGate(Seq(Dev ceContext.RequestContext.PullToRefresh)),
    FeatureGate.fromFeature(GetNe rFeature)
  )

  overr de val cand dateS ce: Cand dateS ce[Un , ShowAlertCand date] =
    Stat cCand dateS ce(
      Cand dateS ce dent f er( dent f er.na ),
      Seq(ShowAlertCand date( d =  dent f er.na , user ds = Seq.empty))
    )

  overr de val queryTransfor r: Cand dateP pel neQueryTransfor r[Query, Un ] = { _ => Un  }

  overr de val resultTransfor r: Cand dateP pel neResultsTransfor r[
    ShowAlertCand date,
    ShowAlertCand date
  ] = { cand date => cand date }

  overr de val decorator: Opt on[Cand dateDecorator[Query, ShowAlertCand date]] = {
    val tr ggerDelayBu lder = new BaseDurat onBu lder[Query] {
      overr de def apply(
        query: Query,
        cand date: ShowAlertCand date,
        features: FeatureMap
      ): Opt on[Durat on] = {
        val delay = query.dev ceContext.flatMap(_.requestContextValue) match {
          case So (Dev ceContext.RequestContext.T etSelfThread) => 0.m ll s
          case So (Dev ceContext.RequestContext.ManualRefresh) => 0.m ll s
          case _ => Tr ggerDelay
        }

        So (delay)
      }
    }

    val ho ShowAlertCand dateBu lder = ShowAlertCand dateUrt emBu lder(
      alertType = NewT ets,
      colorConf gBu lder = Stat cShowAlertColorConf gurat onBu lder(DefaultColorConf g),
      d splayLocat onBu lder = Stat cShowAlertD splayLocat onBu lder(Top),
      tr ggerDelayBu lder = So (tr ggerDelayBu lder),
      d splayDurat onBu lder = So (Durat onParamBu lder(Stat cParam(D splayDurat on))),
       conD splay nfoBu lder = So (Stat cShowAlert conD splay nfoBu lder(Default conD splay nfo))
    )

    So (Urt emCand dateDecorator(ho ShowAlertCand dateBu lder))
  }

  overr de val alerts = Seq(
    Ho M xerAlertConf g.Bus nessH s.defaultSuccessRateAlert(),
    Ho M xerAlertConf g.Bus nessH s.defaultEmptyResponseRateAlert()
  )
}

object NewT etsP llCand dateP pel neConf g {
  val DefaultColorConf g: ShowAlertColorConf gurat on = ShowAlertColorConf gurat on(
    background = Tw terBlueRosettaColor,
    text = Wh eRosettaColor,
    border = So (Wh eRosettaColor)
  )

  val Default conD splay nfo: ShowAlert conD splay nfo =
    ShowAlert conD splay nfo( con = UpArrow, t nt = Wh eRosettaColor)

  // Unl m ed d splay t   (unt l user takes act on)
  val D splayDurat on = -1.m ll second
  val Tr ggerDelay = 4.m nutes
}
