package com.tw ter.ho _m xer.product.l st_recom nded_users

 mport com.tw ter.ho _m xer.product.l st_recom nded_users.feature_hydrator.RecentL st mbersQueryFeatureHydrator
 mport com.tw ter.ho _m xer.product.l st_recom nded_users.gate.V e r sL stOwnerGate
 mport com.tw ter.ho _m xer.product.l st_recom nded_users.model.L stRecom ndedUsersFeatures. sG zmoduckVal dUserFeature
 mport com.tw ter.ho _m xer.product.l st_recom nded_users.model.L stRecom ndedUsersFeatures. sSGSVal dUserFeature
 mport com.tw ter.ho _m xer.product.l st_recom nded_users.model.L stRecom ndedUsersQuery
 mport com.tw ter.ho _m xer.product.l st_recom nded_users.param.L stRecom ndedUsersParam.Excluded dsMaxLengthParam
 mport com.tw ter.ho _m xer.product.l st_recom nded_users.param.L stRecom ndedUsersParam.ServerMaxResultsParam
 mport com.tw ter.product_m xer.component_l brary.premarshaller.urt.UrtDoma nMarshaller
 mport com.tw ter.product_m xer.component_l brary.premarshaller.urt.bu lder.AddEntr esW hReplace nstruct onBu lder
 mport com.tw ter.product_m xer.component_l brary.premarshaller.urt.bu lder.ReplaceAllEntr es
 mport com.tw ter.product_m xer.component_l brary.premarshaller.urt.bu lder.ReplaceEntry nstruct onBu lder
 mport com.tw ter.product_m xer.component_l brary.premarshaller.urt.bu lder.Stat cT  l neScr beConf gBu lder
 mport com.tw ter.product_m xer.component_l brary.premarshaller.urt.bu lder.UnorderedExclude dsBottomCursorBu lder
 mport com.tw ter.product_m xer.component_l brary.premarshaller.urt.bu lder.Urt tadataBu lder
 mport com.tw ter.product_m xer.component_l brary.selector.DropF lteredCand dates
 mport com.tw ter.product_m xer.component_l brary.selector.DropMaxCand dates
 mport com.tw ter.product_m xer.component_l brary.selector. nsertAppendResults
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.QueryFeatureHydrator
 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.TransportMarshaller
 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt.UrtTransportMarshaller
 mport com.tw ter.product_m xer.core.funct onal_component.premarshaller.Doma nMarshaller
 mport com.tw ter.product_m xer.core.funct onal_component.selector.Selector
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.common. dent f er.M xerP pel ne dent f er
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l ne
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l neScr beConf g
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.user.User em
 mport com.tw ter.product_m xer.core.p pel ne.cand date.Cand dateP pel neConf g
 mport com.tw ter.product_m xer.core.p pel ne.m xer.M xerP pel neConf g
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}

 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class L stRecom ndedUsersM xerP pel neConf g @ nject() (
  l st mberBasedUsersCand dateP pel neConf g: L st mberBasedUsersCand dateP pel neConf g,
  blenderUsersCand dateP pel neConf g: BlenderUsersCand dateP pel neConf g,
  v e r sL stOwnerGate: V e r sL stOwnerGate,
  recentL st mbersQueryFeatureHydrator: RecentL st mbersQueryFeatureHydrator,
  urtTransportMarshaller: UrtTransportMarshaller)
    extends M xerP pel neConf g[L stRecom ndedUsersQuery, T  l ne, urt.T  l neResponse] {

  overr de val  dent f er: M xerP pel ne dent f er = M xerP pel ne dent f er("L stRecom ndedUsers")

  overr de val gates = Seq(v e r sL stOwnerGate)

  overr de val fetchQueryFeatures: Seq[QueryFeatureHydrator[L stRecom ndedUsersQuery]] =
    Seq(recentL st mbersQueryFeatureHydrator)

  overr de val cand dateP pel nes: Seq[
    Cand dateP pel neConf g[L stRecom ndedUsersQuery, _, _, _]
  ] = Seq(
    l st mberBasedUsersCand dateP pel neConf g,
    blenderUsersCand dateP pel neConf g
  )

  pr vate val cand dateP pel ne dent f ers = Set(
    l st mberBasedUsersCand dateP pel neConf g. dent f er,
    blenderUsersCand dateP pel neConf g. dent f er
  )

  overr de val resultSelectors: Seq[Selector[L stRecom ndedUsersQuery]] = Seq(
    DropF lteredCand dates(
      cand dateP pel nes = cand dateP pel ne dent f ers,
      f lter = cand date =>
        cand date.features.getOrElse( sSGSVal dUserFeature, false) &&
          cand date.features.getOrElse( sG zmoduckVal dUserFeature, false)
    ),
    DropMaxCand dates(
      cand dateP pel nes = cand dateP pel ne dent f ers,
      maxSelect onsParam = ServerMaxResultsParam),
     nsertAppendResults(cand dateP pel ne dent f ers)
  )

  overr de val doma nMarshaller: Doma nMarshaller[L stRecom ndedUsersQuery, T  l ne] = {
    val  nstruct onBu lders = Seq(
      ReplaceEntry nstruct onBu lder(ReplaceAllEntr es),
      AddEntr esW hReplace nstruct onBu lder()
    )

    val  tadataBu lder = Urt tadataBu lder(
      t le = None,
      scr beConf gBu lder = So (
        Stat cT  l neScr beConf gBu lder(
          T  l neScr beConf g(
            page = So ("l st_recom nded_users"),
            sect on = None,
            ent yToken = None)))
    )

    val exclude dsSelector: Part alFunct on[Un versalNoun[_], Long] = {
      case  em: User em =>  em. d
    }

    val cursorBu lder = UnorderedExclude dsBottomCursorBu lder(
      excluded dsMaxLengthParam = Excluded dsMaxLengthParam,
      exclude dsSelector = exclude dsSelector)

    UrtDoma nMarshaller(
       nstruct onBu lders =  nstruct onBu lders,
       tadataBu lder = So ( tadataBu lder),
      cursorBu lders = Seq(cursorBu lder)
    )
  }

  overr de val transportMarshaller: TransportMarshaller[T  l ne, urt.T  l neResponse] =
    urtTransportMarshaller
}
