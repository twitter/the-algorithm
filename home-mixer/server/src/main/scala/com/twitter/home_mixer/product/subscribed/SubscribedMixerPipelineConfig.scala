package com.tw ter.ho _m xer.product.subscr bed

 mport com.tw ter.cl entapp.{thr ftscala => ca}
 mport com.tw ter.ho _m xer.cand date_p pel ne.Conversat onServ ceCand dateP pel neConf gBu lder
 mport com.tw ter.ho _m xer.cand date_p pel ne.Ed edT etsCand dateP pel neConf g
 mport com.tw ter.ho _m xer.cand date_p pel ne.NewT etsP llCand dateP pel neConf g
 mport com.tw ter.ho _m xer.funct onal_component.decorator.Ho Conversat onServ ceCand dateDecorator
 mport com.tw ter.ho _m xer.funct onal_component.decorator.urt.bu lder.Ho FeedbackAct on nfoBu lder
 mport com.tw ter.ho _m xer.funct onal_component.feature_hydrator._
 mport com.tw ter.ho _m xer.funct onal_component.selector.UpdateHo Cl entEventDeta ls
 mport com.tw ter.ho _m xer.funct onal_component.selector.UpdateNewT etsP llDecorat on
 mport com.tw ter.ho _m xer.funct onal_component.s de_effect._
 mport com.tw ter.ho _m xer.model.Gap nclude nstruct on
 mport com.tw ter.ho _m xer.param.Ho GlobalParams.MaxNumberReplace nstruct onsParam
 mport com.tw ter.ho _m xer.param.Ho M xerFlagNa .Scr beCl entEventsFlag
 mport com.tw ter.ho _m xer.product.follow ng.model.Ho M xerExternalStr ngs
 mport com.tw ter.ho _m xer.product.subscr bed.model.Subscr bedQuery
 mport com.tw ter.ho _m xer.product.subscr bed.param.Subscr bedParam.ServerMaxResultsParam
 mport com.tw ter.ho _m xer.ut l.Cand datesUt l
 mport com.tw ter. nject.annotat ons.Flag
 mport com.tw ter.logp pel ne.cl ent.common.EventPubl s r
 mport com.tw ter.product_m xer.component_l brary.feature_hydrator.query.async.AsyncQueryFeatureHydrator
 mport com.tw ter.product_m xer.component_l brary.feature_hydrator.query. mpressed_t ets. mpressedT etsQueryFeatureHydrator
 mport com.tw ter.product_m xer.component_l brary.feature_hydrator.query.soc al_graph.SGSFollo dUsersQueryFeatureHydrator
 mport com.tw ter.product_m xer.component_l brary.feature_hydrator.query.soc al_graph.SGSSubscr bedUsersQueryFeatureHydrator
 mport com.tw ter.product_m xer.component_l brary.gate.NonEmptyCand datesGate
 mport com.tw ter.product_m xer.component_l brary.model.cand date.T etCand date
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
 mport com.tw ter.product_m xer.component_l brary.selector.DropMaxCand dates
 mport com.tw ter.product_m xer.component_l brary.selector. nsertAppendResults
 mport com.tw ter.product_m xer.component_l brary.selector.SelectCond  onally
 mport com.tw ter.product_m xer.component_l brary.selector.UpdateSortCand dates
 mport com.tw ter.product_m xer.core.funct onal_component.common.Spec f cP pel ne
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
 mport com.tw ter.product_m xer.core.product.gu ce.scope.ProductScoped
 mport com.tw ter.str ngcenter.cl ent.Str ngCenter
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.Prov der
 mport javax. nject.S ngleton

@S ngleton
class Subscr bedM xerP pel neConf g @ nject() (
  subscr bedEarlyb rdCand dateP pel neConf g: Subscr bedEarlyb rdCand dateP pel neConf g,
  conversat onServ ceCand dateP pel neConf gBu lder: Conversat onServ ceCand dateP pel neConf gBu lder[
    Subscr bedQuery
  ],
  ho FeedbackAct on nfoBu lder: Ho FeedbackAct on nfoBu lder,
  ed edT etsCand dateP pel neConf g: Ed edT etsCand dateP pel neConf g,
  newT etsP llCand dateP pel neConf g: NewT etsP llCand dateP pel neConf g[Subscr bedQuery],
  d sm ss nfoQueryFeatureHydrator: D sm ss nfoQueryFeatureHydrator,
  g zmoduckUserQueryFeatureHydrator: G zmoduckUserQueryFeatureHydrator,
  requestQueryFeatureHydrator: RequestQueryFeatureHydrator[Subscr bedQuery],
  sgsFollo dUsersQueryFeatureHydrator: SGSFollo dUsersQueryFeatureHydrator,
  sgsSubscr bedUsersQueryFeatureHydrator: SGSSubscr bedUsersQueryFeatureHydrator,
  manhattanT et mpress onsQueryFeatureHydrator: T et mpress onsQueryFeatureHydrator[
    Subscr bedQuery
  ],
   mcac T et mpress onsQueryFeatureHydrator:  mpressedT etsQueryFeatureHydrator,
  publ shCl entSent mpress onsEventBusS deEffect: Publ shCl entSent mpress onsEventBusS deEffect,
  publ shCl entSent mpress onsManhattanS deEffect: Publ shCl entSent mpress onsManhattanS deEffect,
  ho T  l neServedCand datesS deEffect: Ho Scr beServedCand datesS deEffect,
  cl entEventsScr beEventPubl s r: EventPubl s r[ca.LogEvent],
  externalStr ngs: Ho M xerExternalStr ngs,
  @ProductScoped str ngCenterProv der: Prov der[Str ngCenter],
  urtTransportMarshaller: UrtTransportMarshaller,
  @Flag(Scr beCl entEventsFlag) enableScr beCl entEvents: Boolean)
    extends M xerP pel neConf g[Subscr bedQuery, T  l ne, urt.T  l neResponse] {

  overr de val  dent f er: M xerP pel ne dent f er = M xerP pel ne dent f er("Subscr bed")

  pr vate val dependentCand datesStep = M xerP pel neConf g.dependentCand dateP pel nesStep
  pr vate val resultSelectorsStep = M xerP pel neConf g.resultSelectorsStep

  overr de val fetchQueryFeatures: Seq[QueryFeatureHydrator[Subscr bedQuery]] = Seq(
    requestQueryFeatureHydrator,
    sgsFollo dUsersQueryFeatureHydrator,
    sgsSubscr bedUsersQueryFeatureHydrator,
    AsyncQueryFeatureHydrator(dependentCand datesStep, d sm ss nfoQueryFeatureHydrator),
    AsyncQueryFeatureHydrator(dependentCand datesStep, g zmoduckUserQueryFeatureHydrator),
    AsyncQueryFeatureHydrator(resultSelectorsStep, manhattanT et mpress onsQueryFeatureHydrator),
    AsyncQueryFeatureHydrator(resultSelectorsStep,  mcac T et mpress onsQueryFeatureHydrator)
  )

  pr vate val earlyb rdCand dateP pel neScope =
    Spec f cP pel ne(subscr bedEarlyb rdCand dateP pel neConf g. dent f er)

  pr vate val conversat onServ ceCand dateP pel neConf g =
    conversat onServ ceCand dateP pel neConf gBu lder.bu ld(
      Seq(NonEmptyCand datesGate(earlyb rdCand dateP pel neScope)),
      Ho Conversat onServ ceCand dateDecorator(ho FeedbackAct on nfoBu lder)
    )

  overr de val cand dateP pel nes: Seq[Cand dateP pel neConf g[Subscr bedQuery, _, _, _]] =
    Seq(subscr bedEarlyb rdCand dateP pel neConf g)

  overr de val dependentCand dateP pel nes: Seq[
    DependentCand dateP pel neConf g[Subscr bedQuery, _, _, _]
  ] = Seq(
    conversat onServ ceCand dateP pel neConf g,
    ed edT etsCand dateP pel neConf g,
    newT etsP llCand dateP pel neConf g
  )

  overr de val fa lOpenPol c es: Map[Cand dateP pel ne dent f er, Fa lOpenPol cy] = Map(
    ed edT etsCand dateP pel neConf g. dent f er -> Fa lOpenPol cy.Always,
    newT etsP llCand dateP pel neConf g. dent f er -> Fa lOpenPol cy.Always,
  )

  overr de val resultSelectors: Seq[Selector[Subscr bedQuery]] = Seq(
    UpdateSortCand dates(
      order ng = Cand datesUt l.reverseChronT etsOrder ng,
      cand dateP pel ne = conversat onServ ceCand dateP pel neConf g. dent f er
    ),
    DropMaxCand dates(
      cand dateP pel ne = ed edT etsCand dateP pel neConf g. dent f er,
      maxSelect onsParam = MaxNumberReplace nstruct onsParam
    ),
    DropMaxCand dates(
      cand dateP pel ne = conversat onServ ceCand dateP pel neConf g. dent f er,
      maxSelect onsParam = ServerMaxResultsParam
    ),
     nsertAppendResults(cand dateP pel ne = conversat onServ ceCand dateP pel neConf g. dent f er),
    // T  selector must co  after t  t ets are  nserted  nto t  results
    UpdateNewT etsP llDecorat on(
      p pel neScope = Spec f cP pel nes(
        conversat onServ ceCand dateP pel neConf g. dent f er,
        newT etsP llCand dateP pel neConf g. dent f er
      ),
      str ngCenter = str ngCenterProv der.get(),
      seeNewT etsStr ng = externalStr ngs.seeNewT etsStr ng,
      t etedStr ng = externalStr ngs.t etedStr ng
    ),
     nsertAppendResults(cand dateP pel ne = ed edT etsCand dateP pel neConf g. dent f er),
    SelectCond  onally(
      selector =
         nsertAppendResults(cand dateP pel ne = newT etsP llCand dateP pel neConf g. dent f er),
       ncludeSelector = (_, _, results) => Cand datesUt l.conta nsType[T etCand date](results)
    ),
    UpdateHo Cl entEventDeta ls(
      cand dateP pel nes = Set(conversat onServ ceCand dateP pel neConf g. dent f er)
    ),
  )

  pr vate val ho Scr beCl entEventS deEffect = Ho Scr beCl entEventS deEffect(
    enableScr beCl entEvents = enableScr beCl entEvents,
    logP pel nePubl s r = cl entEventsScr beEventPubl s r,
     njectedT etsCand dateP pel ne dent f ers =
      Seq(conversat onServ ceCand dateP pel neConf g. dent f er),
  )

  overr de val resultS deEffects: Seq[P pel neResultS deEffect[Subscr bedQuery, T  l ne]] = Seq(
    ho Scr beCl entEventS deEffect,
    ho T  l neServedCand datesS deEffect,
    publ shCl entSent mpress onsEventBusS deEffect,
    publ shCl entSent mpress onsManhattanS deEffect
  )

  overr de val doma nMarshaller: Doma nMarshaller[Subscr bedQuery, T  l ne] = {
    val  nstruct onBu lders = Seq(
      ReplaceEntry nstruct onBu lder(ReplaceAllEntr es),
      AddEntr esW hReplaceAndShowAlert nstruct onBu lder(),
      ShowAlert nstruct onBu lder(),
    )

    val  dSelector: Part alFunct on[Un versalNoun[_], Long] = {
      // exclude ads wh le determ n ng t et cursor values
      case  em: T et em  f  em.promoted tadata. sEmpty =>  em. d
      case module: T  l neModule
           f module. ems. adOpt on.ex sts(_. em. s nstanceOf[T et em]) =>
        module. ems.last. em match { case  em: T et em =>  em. d }
    }

    val topCursorBu lder = OrderedTopCursorBu lder( dSelector)
    val bottomCursorBu lder =
      OrderedBottomCursorBu lder( dSelector, Gap nclude nstruct on. nverse())
    val gapCursorBu lder = OrderedGapCursorBu lder( dSelector, Gap nclude nstruct on)

    val  tadataBu lder = Urt tadataBu lder(
      t le = None,
      scr beConf gBu lder = So (
        Stat cT  l neScr beConf gBu lder(
          T  l neScr beConf g(page = So ("subscr bed"), sect on = None, ent yToken = None)))
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
