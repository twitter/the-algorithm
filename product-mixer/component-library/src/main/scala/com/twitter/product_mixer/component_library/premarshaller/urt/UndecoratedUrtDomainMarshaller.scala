package com.tw ter.product_m xer.component_l brary.premarshaller.urt

 mport com.tw ter.product_m xer.component_l brary.model.cand date.Art cleCand date
 mport com.tw ter.product_m xer.component_l brary.model.cand date.Aud oSpaceCand date
 mport com.tw ter.product_m xer.component_l brary.model.cand date.Top cCand date
 mport com.tw ter.product_m xer.component_l brary.model.cand date.T etCand date
 mport com.tw ter.product_m xer.component_l brary.model.cand date.Tw terL stCand date
 mport com.tw ter.product_m xer.component_l brary.model.cand date.UserCand date
 mport com.tw ter.product_m xer.component_l brary.premarshaller.urt.bu lder.AddEntr es nstruct onBu lder
 mport com.tw ter.product_m xer.component_l brary.premarshaller.urt.bu lder.BaseUrt tadataBu lder
 mport com.tw ter.product_m xer.component_l brary.premarshaller.urt.bu lder.UrtBu lder
 mport com.tw ter.product_m xer.component_l brary.premarshaller.urt.bu lder.UrtCursorBu lder
 mport com.tw ter.product_m xer.component_l brary.premarshaller.urt.bu lder.UrtCursorUpdater
 mport com.tw ter.product_m xer.component_l brary.premarshaller.urt.bu lder.Urt nstruct onBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.premarshaller.Doma nMarshaller
 mport com.tw ter.product_m xer.core.funct onal_component.premarshaller.UnsupportedCand dateDoma nMarshallerExcept on
 mport com.tw ter.product_m xer.core.funct onal_component.premarshaller.UnsupportedModuleDoma nMarshallerExcept on
 mport com.tw ter.product_m xer.core.funct onal_component.premarshaller.UnsupportedPresentat onDoma nMarshallerExcept on
 mport com.tw ter.product_m xer.core.model.common. dent f er.Doma nMarshaller dent f er
 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateW hDeta ls
 mport com.tw ter.product_m xer.core.model.common.presentat on. emCand dateW hDeta ls
 mport com.tw ter.product_m xer.core.model.common.presentat on.ModuleCand dateW hDeta ls
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l ne
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l ne nstruct on
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.art cle.Art cle em
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.art cle.Follow ngL stSeed
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.aud o_space.Aud oSpace em
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.top c.Top c em
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.t et.T et
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.t et.T et em
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.tw ter_l st.Tw terL st em
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.user.User
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.user.User em
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

/**
 * Decorator that  s useful for fast prototyp ng, as   w ll generate URT entr es from only
 * cand date  Ds (no  emPresentat ons or ModulePresentat ons from cand date p pel ne decorators
 * are requ red).
 */
case class UndecoratedUrtDoma nMarshaller[Query <: P pel neQuery](
  overr de val  nstruct onBu lders: Seq[Urt nstruct onBu lder[Query, T  l ne nstruct on]] =
    Seq(AddEntr es nstruct onBu lder()),
  overr de val cursorBu lders: Seq[UrtCursorBu lder[Query]] = Seq.empty,
  overr de val cursorUpdaters: Seq[UrtCursorUpdater[Query]] = Seq.empty,
  overr de val  tadataBu lder: Opt on[BaseUrt tadataBu lder[Query]] = None,
  overr de val sort ndexStep:  nt = 1,
  overr de val  dent f er: Doma nMarshaller dent f er =
    Doma nMarshaller dent f er("UndecoratedUn f edR chT  l ne"))
    extends Doma nMarshaller[Query, T  l ne]
    w h UrtBu lder[Query, T  l ne nstruct on] {

  overr de def apply(
    query: Query,
    select ons: Seq[Cand dateW hDeta ls]
  ): T  l ne = {
    val entr es = select ons.map {
      case  emCand dateW hDeta ls @  emCand dateW hDeta ls(cand date, None, _) =>
        cand date match {
          case cand date: Art cleCand date =>
            Art cle em(
               d = cand date. d,
              art cleSeedType = Follow ngL stSeed,
              sort ndex = None,
              cl entEvent nfo = None,
              feedbackAct on nfo = None,
              d splayType = None,
              soc alContext = None,
            )
          case cand date: Aud oSpaceCand date =>
            Aud oSpace em(
               d = cand date. d,
              sort ndex = None,
              cl entEvent nfo = None,
              feedbackAct on nfo = None)
          case cand date: Top cCand date =>
            Top c em(
               d = cand date. d,
              sort ndex = None,
              cl entEvent nfo = None,
              feedbackAct on nfo = None,
              top cFunct onal yType = None,
              top cD splayType = None
            )
          case cand date: T etCand date =>
            T et em(
               d = cand date. d,
              entryNa space = T et em.T etEntryNa space,
              sort ndex = None,
              cl entEvent nfo = None,
              feedbackAct on nfo = None,
               sP nned = None,
              entry dToReplace = None,
              soc alContext = None,
              h ghl ghts = None,
              d splayType = T et,
               nnerTombstone nfo = None,
              t  l nesScore nfo = None,
              hasModeratedRepl es = None,
              forwardP vot = None,
               nnerForwardP vot = None,
              promoted tadata = None,
              conversat onAnnotat on = None,
              contextualT etRef = None,
              preroll tadata = None,
              replyBadge = None,
              dest nat on = None
            )
          case cand date: Tw terL stCand date =>
            Tw terL st em(
               d = cand date. d,
              sort ndex = None,
              cl entEvent nfo = None,
              feedbackAct on nfo = None,
              d splayType = None
            )
          case cand date: UserCand date =>
            User em(
               d = cand date. d,
              sort ndex = None,
              cl entEvent nfo = None,
              feedbackAct on nfo = None,
               sMarkUnread = None,
              d splayType = User,
              promoted tadata = None,
              soc alContext = None,
              react veTr ggers = None,
              enableReact veBlend ng = None
            )
          case cand date =>
            throw new UnsupportedCand dateDoma nMarshallerExcept on(
              cand date,
               emCand dateW hDeta ls.s ce)
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

    bu ldT  l ne(query, entr es)
  }
}
