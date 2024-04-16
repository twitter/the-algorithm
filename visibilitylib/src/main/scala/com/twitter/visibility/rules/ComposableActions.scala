package com.tw ter.v s b l y.rules

object ComposableAct ons {

  object ComposableAct onsW hConversat onSect onAbus veQual y {
    def unapply(
      composableAct ons: T et nterst  al
    ): Opt on[Conversat onSect onAbus veQual y.type] = {
      composableAct ons.abus veQual y
    }
  }

  object ComposableAct onsW hSoft ntervent on {
    def unapply(composableAct ons: T et nterst  al): Opt on[Soft ntervent on] = {
      composableAct ons.soft ntervent on match {
        case So (s : Soft ntervent on) => So (s )
        case _ => None
      }
    }
  }

  object ComposableAct onsW h nterst  alL m edEngage nts {
    def unapply(composableAct ons: T et nterst  al): Opt on[ nterst  alL m edEngage nts] = {
      composableAct ons. nterst  al match {
        case So ( le:  nterst  alL m edEngage nts) => So ( le)
        case _ => None
      }
    }
  }

  object ComposableAct onsW h nterst  al {
    def unapply(composableAct ons: T et nterst  al): Opt on[ nterst  al] = {
      composableAct ons. nterst  al match {
        case So ( :  nterst  al) => So ( )
        case _ => None
      }
    }
  }

  object ComposableAct onsW hAppealable {
    def unapply(composableAct ons: T et nterst  al): Opt on[Appealable] = {
      composableAct ons.appealable
    }
  }
}
