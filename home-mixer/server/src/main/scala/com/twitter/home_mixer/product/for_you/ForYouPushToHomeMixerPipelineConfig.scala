package com.tw ter.ho _m xer.product.for_ 

 mport com.tw ter.ho _m xer.product.for_ .model.For Query
 mport com.tw ter.ho _m xer.product.for_ .param.For Param
 mport com.tw ter.product_m xer.component_l brary.premarshaller.urt.UrtDoma nMarshaller
 mport com.tw ter.product_m xer.component_l brary.premarshaller.urt.bu lder.AddEntr es nstruct onBu lder
 mport com.tw ter.product_m xer.component_l brary.premarshaller.urt.bu lder.ClearCac  nstruct onBu lder
 mport com.tw ter.product_m xer.component_l brary.premarshaller.urt.bu lder.OrderedBottomCursorBu lder
 mport com.tw ter.product_m xer.component_l brary.premarshaller.urt.bu lder.OrderedTopCursorBu lder
 mport com.tw ter.product_m xer.component_l brary.premarshaller.urt.bu lder.ParamGated nclude nstruct on
 mport com.tw ter.product_m xer.component_l brary.premarshaller.urt.bu lder.Stat cT  l neScr beConf gBu lder
 mport com.tw ter.product_m xer.component_l brary.premarshaller.urt.bu lder.Urt tadataBu lder
 mport com.tw ter.product_m xer.component_l brary.selector. nsertAppendResults
 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.TransportMarshaller
 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt.UrtTransportMarshaller
 mport com.tw ter.product_m xer.core.funct onal_component.premarshaller.Doma nMarshaller
 mport com.tw ter.product_m xer.core.funct onal_component.selector.Selector
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.common. dent f er.M xerP pel ne dent f er
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l ne
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l neScr beConf g
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.t et.T et em
 mport com.tw ter.product_m xer.core.p pel ne.cand date.Cand dateP pel neConf g
 mport com.tw ter.product_m xer.core.p pel ne.m xer.M xerP pel neConf g
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class For PushToHo M xerP pel neConf g @ nject() (
  for PushToHo T etCand dateP pel neConf g: For PushToHo T etCand dateP pel neConf g,
  urtTransportMarshaller: UrtTransportMarshaller)
    extends M xerP pel neConf g[For Query, T  l ne, urt.T  l neResponse] {

  overr de val  dent f er: M xerP pel ne dent f er = M xerP pel ne dent f er("For PushToHo ")

  overr de val cand dateP pel nes: Seq[Cand dateP pel neConf g[For Query, _, _, _]] =
    Seq(for PushToHo T etCand dateP pel neConf g)

  overr de val resultSelectors: Seq[Selector[For Query]] =
    Seq( nsertAppendResults(for PushToHo T etCand dateP pel neConf g. dent f er))

  overr de val doma nMarshaller: Doma nMarshaller[For Query, T  l ne] = {
    val  nstruct onBu lders = Seq(
      ClearCac  nstruct onBu lder(
        ParamGated nclude nstruct on(For Param.EnableClearCac OnPushToHo )),
      AddEntr es nstruct onBu lder())

    val  dSelector: Part alFunct on[Un versalNoun[_], Long] = { case  em: T et em =>  em. d }
    val topCursorBu lder = OrderedTopCursorBu lder( dSelector)
    val bottomCursorBu lder = OrderedBottomCursorBu lder( dSelector)

    val  tadataBu lder = Urt tadataBu lder(
      t le = None,
      scr beConf gBu lder = So (
        Stat cT  l neScr beConf gBu lder(
          T  l neScr beConf g(
            page = So ("for_ _push_to_ho "),
            sect on = None,
            ent yToken = None)
        )
      )
    )

    UrtDoma nMarshaller(
       nstruct onBu lders =  nstruct onBu lders,
       tadataBu lder = So ( tadataBu lder),
      cursorBu lders = Seq(topCursorBu lder, bottomCursorBu lder)
    )
  }

  overr de val transportMarshaller: TransportMarshaller[T  l ne, urt.T  l neResponse] =
    urtTransportMarshaller
}
