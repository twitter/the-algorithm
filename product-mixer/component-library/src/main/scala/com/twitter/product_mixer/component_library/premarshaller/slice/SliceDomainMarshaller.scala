package com.tw ter.product_m xer.component_l brary.premarshaller.sl ce

 mport com.tw ter.product_m xer.component_l brary.model.cand date._
 mport com.tw ter.product_m xer.component_l brary.model.cand date.hubble.AdCreat veCand date
 mport com.tw ter.product_m xer.component_l brary.model.cand date.hubble.AdGroupCand date
 mport com.tw ter.product_m xer.component_l brary.model.cand date.hubble.AdUn Cand date
 mport com.tw ter.product_m xer.component_l brary.model.cand date.hubble.Campa gnCand date
 mport com.tw ter.product_m xer.component_l brary.model.cand date.hubble.Fund ngS ceCand date
 mport com.tw ter.product_m xer.component_l brary.model.cand date.suggest on.QuerySuggest onCand date
 mport com.tw ter.product_m xer.component_l brary.model.cand date.suggest on.Typea adEventCand date
 mport com.tw ter.product_m xer.component_l brary.premarshaller.sl ce.bu lder.Sl ceBu lder
 mport com.tw ter.product_m xer.component_l brary.premarshaller.sl ce.bu lder.Sl ceCursorBu lder
 mport com.tw ter.product_m xer.component_l brary.premarshaller.sl ce.bu lder.Sl ceCursorUpdater
 mport com.tw ter.product_m xer.core.funct onal_component.premarshaller.Doma nMarshaller
 mport com.tw ter.product_m xer.core.funct onal_component.premarshaller.UndecoratedCand dateDoma nMarshallerExcept on
 mport com.tw ter.product_m xer.core.funct onal_component.premarshaller.UnsupportedCand dateDoma nMarshallerExcept on
 mport com.tw ter.product_m xer.core.funct onal_component.premarshaller.UnsupportedModuleDoma nMarshallerExcept on
 mport com.tw ter.product_m xer.core.funct onal_component.premarshaller.UnsupportedPresentat onDoma nMarshallerExcept on
 mport com.tw ter.product_m xer.core.model.common. dent f er.Doma nMarshaller dent f er
 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateW hDeta ls
 mport com.tw ter.product_m xer.core.model.common.presentat on. emCand dateW hDeta ls
 mport com.tw ter.product_m xer.core.model.common.presentat on.ModuleCand dateW hDeta ls
 mport com.tw ter.product_m xer.core.model.common.presentat on.sl ce.BaseSl ce emPresentat on
 mport com.tw ter.product_m xer.core.model.marshall ng.response.sl ce._
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

/**
 * Doma n marshaller that generates Sl ces automat cally for most cand dates but a d fferent
 * presentat on can be prov ded by decorators that  mple nt [[BaseSl ce emPresentat on]]. T  w ll
 * only be necessary  n t  rare case that a cand date conta ns more than an  d. For example,
 * cursors requ re a value/type rat r than an  d.
 */
case class Sl ceDoma nMarshaller[-Query <: P pel neQuery](
  overr de val cursorBu lders: Seq[Sl ceCursorBu lder[Query]] = Seq.empty,
  overr de val cursorUpdaters: Seq[Sl ceCursorUpdater[Query]] = Seq.empty,
  overr de val  dent f er: Doma nMarshaller dent f er = Doma nMarshaller dent f er("Sl ce"))
    extends Doma nMarshaller[Query, Sl ce]
    w h Sl ceBu lder[Query] {

  overr de def apply(
    query: Query,
    select ons: Seq[Cand dateW hDeta ls]
  ): Sl ce = {
    val entr es = select ons.map {
      case  emCand dateW hDeta ls(_, So (presentat on: BaseSl ce emPresentat on), _) =>
        presentat on.sl ce em
      case cand dateW hDeta ls @  emCand dateW hDeta ls(cand date, None, _) =>
        val s ce = cand dateW hDeta ls.s ce
        cand date match {
          case cand date: BaseTop cCand date => Top c em(cand date. d)
          case cand date: BaseT etCand date => T et em(cand date. d)
          case cand date: BaseUserCand date => User em(cand date. d)
          case cand date: Tw terL stCand date => Tw terL st em(cand date. d)
          case cand date: DMConvoSearchCand date =>
            DMConvoSearch em(cand date. d, cand date.lastReadableEvent d)
          case cand date: DMEventCand date =>
            DMEvent em(cand date. d)
          case cand date: DMConvoCand date =>
            DMConvo em(cand date. d, cand date.lastReadableEvent d)
          case cand date: DM ssageSearchCand date => DM ssageSearch em(cand date. d)
          case cand date: QuerySuggest onCand date =>
            Typea adQuerySuggest on em(cand date. d, cand date. tadata)
          case cand date: Typea adEventCand date =>
            Typea adEvent em(cand date. d, cand date. tadata)
          case cand date: AdUn Cand date =>
            Ad em(cand date. d, cand date.adAccount d)
          case cand date: AdCreat veCand date =>
            AdCreat ve em(cand date. d, cand date.adType, cand date.adAccount d)
          case cand date: AdGroupCand date =>
            AdGroup em(cand date. d, cand date.adAccount d)
          case cand date: Campa gnCand date =>
            Campa gn em(cand date. d, cand date.adAccount d)
          case cand date: Fund ngS ceCand date =>
            Fund ngS ce em(cand date. d, cand date.adAccount d)
          case cand date: CursorCand date =>
            // Cursors must conta n a cursor type wh ch  s def ned by t  presentat on. As a result,
            // cursors are expected to be handled by t  So (presentat on) case above, and must not
            // fall  nto t  case.
            throw new UndecoratedCand dateDoma nMarshallerExcept on(cand date, s ce)
          case cand date =>
            throw new UnsupportedCand dateDoma nMarshallerExcept on(cand date, s ce)
        }
      case  emCand dateW hDeta ls @  emCand dateW hDeta ls(cand date, So (presentat on), _) =>
        throw new UnsupportedPresentat onDoma nMarshallerExcept on(
          cand date,
          presentat on,
           emCand dateW hDeta ls.s ce)
      case moduleCand dateW hDeta ls @ ModuleCand dateW hDeta ls(_, presentat on, _) =>
        throw new UnsupportedModuleDoma nMarshallerExcept on(
          presentat on,
          moduleCand dateW hDeta ls.s ce)
    }

    bu ldSl ce(query, entr es)
  }
}
