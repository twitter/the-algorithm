package com.tw ter.ho _m xer.product.l st_t ets

 mport com.tw ter.cl entapp.{thr ftscala => ca}
 mport com.tw ter.goldf nch.ap .Ads nject onSurfaceAreas
 mport com.tw ter.ho _m xer.cand date_p pel ne.Conversat onServ ceCand dateP pel neConf gBu lder
 mport com.tw ter.ho _m xer.funct onal_component.feature_hydrator.RequestQueryFeatureHydrator
 mport com.tw ter.ho _m xer.funct onal_component.s de_effect.Ho Scr beCl entEventS deEffect
 mport com.tw ter.ho _m xer.model.Gap nclude nstruct on
 mport com.tw ter.ho _m xer.param.Ho M xerFlagNa .Scr beCl entEventsFlag
 mport com.tw ter.ho _m xer.product.l st_t ets.decorator.L stConversat onServ ceCand dateDecorator
 mport com.tw ter.ho _m xer.product.l st_t ets.model.L stT etsQuery
 mport com.tw ter.ho _m xer.ut l.Cand datesUt l
 mport com.tw ter. nject.annotat ons.Flag
 mport com.tw ter.logp pel ne.cl ent.common.EventPubl s r
 mport com.tw ter.product_m xer.component_l brary.feature_hydrator.query.soc al_graph.SGSFollo dUsersQueryFeatureHydrator
 mport com.tw ter.product_m xer.component_l brary.gate.NonEmptyCand datesGate
 mport com.tw ter.product_m xer.component_l brary.premarshaller.urt.UrtDoma nMarshaller
 mport com.tw ter.product_m xer.component_l brary.premarshaller.urt.bu lder.AddEntr esW hReplaceAndShowAlert nstruct onBu lder
 mport com.tw ter.product_m xer.component_l brary.premarshaller.urt.bu lder.OrderedBottomCursorBu lder
 mport com.tw ter.product_m xer.component_l brary.premarshaller.urt.bu lder.OrderedGapCursorBu lder
 mport com.tw ter.product_m xer.component_l brary.premarshaller.urt.bu lder.OrderedTopCursorBu lder
 mport com.tw ter.product_m xer.component_l brary.premarshaller.urt.bu lder.ReplaceAllEntr es
 mport com.tw ter.product_m xer.component_l brary.premarshaller.urt.bu lder.ReplaceEntry nstruct onBu lder
 mport com.tw ter.product_m xer.component_l brary.premarshaller.urt.bu lder.ShowAlert nstruct onBu lder
 mport com.tw ter.product_m xer.component_l brary.premarshaller.urt.bu lder.Stat cT  l neScr beConf gBu lder
 mport com.tw ter.product_m xer.component_l brary.premarshaller.urt.bu lder.Urt tadataBu lder
 mport com.tw ter.product_m xer.component_l brary.selector. nsertAppendResults
 mport com.tw ter.product_m xer.component_l brary.selector.UpdateSortCand dates
 mport com.tw ter.product_m xer.component_l brary.selector.ads.Ads njector
 mport com.tw ter.product_m xer.component_l brary.selector.ads. nsertAdResults
 mport com.tw ter.product_m xer.core.funct onal_component.common.Spec f cP pel nes
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.QueryFeatureHydrator
 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.TransportMarshaller
 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt.UrtTransportMarshaller
 mport com.tw ter.product_m xer.core.funct onal_component.premarshaller.Doma nMarshaller
 mport com.tw ter.product_m xer.core.funct onal_component.selector.Selector
 mport com.tw ter.product_m xer.core.funct onal_component.s de_effect.P pel neResultS deEffect
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateP pel ne dent f er
 mport com.tw ter.product_m xer.core.model.common. dent f er.M xerP pel ne dent f er
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l ne
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l neModule
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l neScr beConf g
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.t et.T et em
 mport com.tw ter.product_m xer.core.p pel ne.Fa lOpenPol cy
 mport com.tw ter.product_m xer.core.p pel ne.cand date.Cand dateP pel neConf g
 mport com.tw ter.product_m xer.core.p pel ne.cand date.DependentCand dateP pel neConf g
 mport com.tw ter.product_m xer.core.p pel ne.m xer.M xerP pel neConf g
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class L stT etsM xerP pel neConf g @ nject() (
  l stT etsT  l neServ ceCand dateP pel neConf g: L stT etsT  l neServ ceCand dateP pel neConf g,
  conversat onServ ceCand dateP pel neConf gBu lder: Conversat onServ ceCand dateP pel neConf gBu lder[
    L stT etsQuery
  ],
  l stT etsAdsCand dateP pel neBu lder: L stT etsAdsCand dateP pel neBu lder,
  requestQueryFeatureHydrator: RequestQueryFeatureHydrator[L stT etsQuery],
  sgsFollo dUsersQueryFeatureHydrator: SGSFollo dUsersQueryFeatureHydrator,
  ads njector: Ads njector,
  cl entEventsScr beEventPubl s r: EventPubl s r[ca.LogEvent],
  urtTransportMarshaller: UrtTransportMarshaller,
  @Flag(Scr beCl entEventsFlag) enableScr beCl entEvents: Boolean)
    extends M xerP pel neConf g[L stT etsQuery, T  l ne, urt.T  l neResponse] {

  overr de val  dent f er: M xerP pel ne dent f er = M xerP pel ne dent f er("L stT ets")

  pr vate val conversat onServ ceCand dateP pel neConf g =
    conversat onServ ceCand dateP pel neConf gBu lder.bu ld(
      Seq(
        NonEmptyCand datesGate(
          Spec f cP pel nes(l stT etsT  l neServ ceCand dateP pel neConf g. dent f er))
      ),
      L stConversat onServ ceCand dateDecorator()
    )

  pr vate val l stT etsAdsCand dateP pel neConf g = l stT etsAdsCand dateP pel neBu lder.bu ld(
    Spec f cP pel nes(l stT etsT  l neServ ceCand dateP pel neConf g. dent f er)
  )

  overr de val cand dateP pel nes: Seq[Cand dateP pel neConf g[L stT etsQuery, _, _, _]] =
    Seq(l stT etsT  l neServ ceCand dateP pel neConf g)

  overr de val dependentCand dateP pel nes: Seq[
    DependentCand dateP pel neConf g[L stT etsQuery, _, _, _]
  ] =
    Seq(conversat onServ ceCand dateP pel neConf g, l stT etsAdsCand dateP pel neConf g)

  overr de val fa lOpenPol c es: Map[Cand dateP pel ne dent f er, Fa lOpenPol cy] = Map(
    conversat onServ ceCand dateP pel neConf g. dent f er -> Fa lOpenPol cy.Always,
    l stT etsAdsCand dateP pel neConf g. dent f er -> Fa lOpenPol cy.Always)

  overr de val resultSelectors: Seq[Selector[L stT etsQuery]] = Seq(
    UpdateSortCand dates(
      order ng = Cand datesUt l.reverseChronT etsOrder ng,
      cand dateP pel ne = conversat onServ ceCand dateP pel neConf g. dent f er
    ),
     nsertAppendResults(cand dateP pel ne = conversat onServ ceCand dateP pel neConf g. dent f er),
     nsertAdResults(
      surfaceAreaNa  = Ads nject onSurfaceAreas.Ho T  l ne,
      ads njector = ads njector.forSurfaceArea(Ads nject onSurfaceAreas.Ho T  l ne),
      adsCand dateP pel ne = l stT etsAdsCand dateP pel neConf g. dent f er
    ),
  )

  overr de val fetchQueryFeatures: Seq[QueryFeatureHydrator[L stT etsQuery]] = Seq(
    requestQueryFeatureHydrator,
    sgsFollo dUsersQueryFeatureHydrator
  )

  pr vate val ho Scr beCl entEventS deEffect = Ho Scr beCl entEventS deEffect(
    enableScr beCl entEvents = enableScr beCl entEvents,
    logP pel nePubl s r = cl entEventsScr beEventPubl s r,
     njectedT etsCand dateP pel ne dent f ers =
      Seq(conversat onServ ceCand dateP pel neConf g. dent f er),
    adsCand dateP pel ne dent f er = So (l stT etsAdsCand dateP pel neConf g. dent f er),
  )

  overr de val resultS deEffects: Seq[P pel neResultS deEffect[L stT etsQuery, T  l ne]] =
    Seq(ho Scr beCl entEventS deEffect)

  overr de val doma nMarshaller: Doma nMarshaller[L stT etsQuery, T  l ne] = {
    val  nstruct onBu lders = Seq(
      ReplaceEntry nstruct onBu lder(ReplaceAllEntr es),
      AddEntr esW hReplaceAndShowAlert nstruct onBu lder(),
      ShowAlert nstruct onBu lder()
    )

    val  dSelector: Part alFunct on[Un versalNoun[_], Long] = {
      // exclude ads wh le determ n ng t et cursor values
      case  em: T et em  f  em.promoted tadata. sEmpty =>  em. d
      case module: T  l neModule
           f module. ems. adOpt on.ex sts(_. em. s nstanceOf[T et em]) =>
        module. ems.last. em match {
          case  em: T et em =>  em. d
        }
    }

    val topCursorBu lder = OrderedTopCursorBu lder( dSelector)
    val bottomCursorBu lder =
      OrderedBottomCursorBu lder( dSelector, Gap nclude nstruct on. nverse())
    val gapCursorBu lder = OrderedGapCursorBu lder( dSelector, Gap nclude nstruct on)

    val  tadataBu lder = Urt tadataBu lder(
      t le = None,
      scr beConf gBu lder = So (
        Stat cT  l neScr beConf gBu lder(
          T  l neScr beConf g(page = So ("l st_t ets"), sect on = None, ent yToken = None)))
    )

    UrtDoma nMarshaller(
       nstruct onBu lders =  nstruct onBu lders,
       tadataBu lder = So ( tadataBu lder),
      cursorBu lders = Seq(topCursorBu lder, bottomCursorBu lder, gapCursorBu lder)
    )
  }

  overr de val transportMarshaller: TransportMarshaller[T  l ne, urt.T  l neResponse] =
    urtTransportMarshaller
}
