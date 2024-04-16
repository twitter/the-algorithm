package com.tw ter.product_m xer.component_l brary.premarshaller.urt

 mport com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder.t  l ne_module.ManualModule d
 mport com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder.t  l ne_module.Module dGenerat on
 mport com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder.t  l ne_module.Automat cUn queModule d
 mport com.tw ter.product_m xer.component_l brary.premarshaller.urt.bu lder._
 mport com.tw ter.product_m xer.core.funct onal_component.premarshaller.Doma nMarshaller
 mport com.tw ter.product_m xer.core.funct onal_component.premarshaller.UndecoratedCand dateDoma nMarshallerExcept on
 mport com.tw ter.product_m xer.core.funct onal_component.premarshaller.UndecoratedModuleDoma nMarshallerExcept on
 mport com.tw ter.product_m xer.core.funct onal_component.premarshaller.UnsupportedModuleDoma nMarshallerExcept on
 mport com.tw ter.product_m xer.core.funct onal_component.premarshaller.UnsupportedPresentat onDoma nMarshallerExcept on
 mport com.tw ter.product_m xer.core.model.common. dent f er.Doma nMarshaller dent f er
 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateW hDeta ls
 mport com.tw ter.product_m xer.core.model.common.presentat on. emCand dateW hDeta ls
 mport com.tw ter.product_m xer.core.model.common.presentat on.ModuleCand dateW hDeta ls
 mport com.tw ter.product_m xer.core.model.common.presentat on.urt.BaseUrt emPresentat on
 mport com.tw ter.product_m xer.core.model.common.presentat on.urt.BaseUrtModulePresentat on
 mport com.tw ter.product_m xer.core.model.common.presentat on.urt.BaseUrtOperat onPresentat on
 mport com.tw ter.product_m xer.core.model.common.presentat on.urt. sD spensable
 mport com.tw ter.product_m xer.core.model.common.presentat on.urt.W h emTreeD splay
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.Module em
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l ne
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l ne nstruct on
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

/**
 * Doma n marshaller that generates URT t  l nes automat cally  f t  cand date p pel ne decorators
 * use  em and module presentat ons types that  mple nt [[BaseUrt emPresentat on]] and
 * [[BaseUrtModulePresentat on]], respect vely to hold URT presentat on data.
 */
case class UrtDoma nMarshaller[-Query <: P pel neQuery](
  overr de val  nstruct onBu lders: Seq[Urt nstruct onBu lder[Query, T  l ne nstruct on]] =
    Seq(AddEntr es nstruct onBu lder()),
  overr de val cursorBu lders: Seq[UrtCursorBu lder[Query]] = Seq.empty,
  overr de val cursorUpdaters: Seq[UrtCursorUpdater[Query]] = Seq.empty,
  overr de val  tadataBu lder: Opt on[BaseUrt tadataBu lder[Query]] = None,
  overr de val sort ndexStep:  nt = 1,
  overr de val  dent f er: Doma nMarshaller dent f er =
    Doma nMarshaller dent f er("Un f edR chT  l ne"))
    extends Doma nMarshaller[Query, T  l ne]
    w h UrtBu lder[Query, T  l ne nstruct on] {

  overr de def apply(
    query: Query,
    select ons: Seq[Cand dateW hDeta ls]
  ): T  l ne = {
    val  n  alSort ndex = get n  alSort ndex(query)

    val entr es = select ons.z pW h ndex.map {
      case ( emCand dateW hDeta ls(_, So (presentat on: BaseUrt emPresentat on), _), _) =>
        presentat on.t  l ne em
      case ( emCand dateW hDeta ls(_, So (presentat on: BaseUrtOperat onPresentat on), _), _) =>
        presentat on.t  l neOperat on
      case (
            ModuleCand dateW hDeta ls(
              cand dates,
              So (presentat on: BaseUrtModulePresentat on),
              _),
             ndex) =>
        val module ems = cand dates.collect {
          case  emCand dateW hDeta ls(_, So ( emPresentat on: BaseUrt emPresentat on), _) =>
            bu ldModule em( emPresentat on)
        }

        Module dGenerat on(presentat on.t  l neModule. d) match {
          case _: Automat cUn queModule d =>
            //  Module  Ds are un que us ng t   thod s nce  n  alSort ndex  s based on t   of request comb ned
            //  w h each t  l ne module  ndex
            presentat on.t  l neModule.copy( d =  n  alSort ndex +  ndex,  ems = module ems)
          case ManualModule d(module d) =>
            presentat on.t  l neModule.copy( d = module d,  ems = module ems)
        }
      case (
             emCand dateW hDeta ls @  emCand dateW hDeta ls(cand date, So (presentat on), _),
            _) =>
        throw new UnsupportedPresentat onDoma nMarshallerExcept on(
          cand date,
          presentat on,
           emCand dateW hDeta ls.s ce)
      case ( emCand dateW hDeta ls @  emCand dateW hDeta ls(cand date, None, _), _) =>
        throw new UndecoratedCand dateDoma nMarshallerExcept on(
          cand date,
           emCand dateW hDeta ls.s ce)
      case (
            moduleCand dateW hDeta ls @ ModuleCand dateW hDeta ls(_, presentat on @ So (_), _),
            _) =>
        // handles g ven a non `BaseUrtModulePresentat on` presentat on type
        throw new UnsupportedModuleDoma nMarshallerExcept on(
          presentat on,
          moduleCand dateW hDeta ls.s ce)
      case (moduleCand dateW hDeta ls @ ModuleCand dateW hDeta ls(_, None, _), _) =>
        throw new UndecoratedModuleDoma nMarshallerExcept on(moduleCand dateW hDeta ls.s ce)
    }

    bu ldT  l ne(query, entr es)
  }

  pr vate def bu ldModule em( emPresentat on: BaseUrt emPresentat on): Module em = {
    val  sD spensable =  emPresentat on match {
      case  sD spensable:  sD spensable => So ( sD spensable.d spensable)
      case _ => None
    }
    val treeD splay =  emPresentat on match {
      case w h emTreeD splay: W h emTreeD splay => w h emTreeD splay.treeD splay
      case _ => None
    }
    Module em(
       emPresentat on.t  l ne em,
      d spensable =  sD spensable,
      treeD splay = treeD splay)
  }
}
