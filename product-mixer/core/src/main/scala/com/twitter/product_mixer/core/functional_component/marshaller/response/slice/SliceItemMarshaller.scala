package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.sl ce

 mport com.tw ter.product_m xer.core.model.marshall ng.response.sl ce.AdType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.sl ce
 mport com.tw ter.strato.graphql.{thr ftscala => t}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Sl ce emMarshaller @ nject() () {
  def apply( em: sl ce.Sl ce em): t.Sl ce em = {
     em match {
      case  em: sl ce.T et em =>
        t.Sl ce em.T et em(t.T et em( d =  em. d))
      case  em: sl ce.User em =>
        t.Sl ce em.User em(t.User em( d =  em. d))
      case  em: sl ce.Tw terL st em =>
        t.Sl ce em.Tw terL st em(t.Tw terL st em( d =  em. d))
      case  em: sl ce.DMConvoSearch em =>
        t.Sl ce em.DmConvoSearch em(t.DMConvoSearch em( d =  em. d))
      case  em: sl ce.DMConvo em =>
        t.Sl ce em.DmConvo em(t.DMConvo em( d =  em. d))
      case  em: sl ce.DMEvent em =>
        t.Sl ce em.DmEvent em(t.DMEvent em( d =  em. d))
      case  em: sl ce.DM ssageSearch em =>
        t.Sl ce em.Dm ssageSearch em(t.DM ssageSearch em( d =  em. d))
      case  em: sl ce.Top c em =>
        t.Sl ce em.Top c em(t.Top c em( d =  em. d.toStr ng))
      case  em: sl ce.Typea adEvent em =>
        t.Sl ce em.Typea adEvent em(
          t.Typea adEvent em(
            event d =  em.event d,
             tadata =  em. tadata.map(marshalTypea ad tadata)
          )
        )
      case  em: sl ce.Typea adQuerySuggest on em =>
        t.Sl ce em.Typea adQuerySuggest on em(
          t.Typea adQuerySuggest on em(
            query =  em.query,
             tadata =  em. tadata.map(marshalTypea ad tadata)
          )
        )
      case  em: sl ce.Typea adUser em =>
        t.Sl ce em.Typea adUser em(
          t.Typea adUser em(
            user d =  em.user d,
             tadata =  em. tadata.map(marshalTypea ad tadata),
            badges = So ( em.badges.map { badge =>
              t.UserBadge(
                badgeUrl = badge.badgeUrl,
                descr pt on = So (badge.descr pt on),
                badgeType = So (badge.badgeType))
            })
          )
        )
      case  em: sl ce.Ad em =>
        t.Sl ce em.Ad em(
          t.Ad em(
            adKey = t.AdKey(
              adAccount d =  em.adAccount d,
              adUn  d =  em.adUn  d,
            )
          )
        )
      case  em: sl ce.AdCreat ve em =>
        t.Sl ce em.AdCreat ve em(
          t.AdCreat ve em(
            adCreat veKey = t.AdCreat veKey(
              adAccount d =  em.adAccount d,
              adType = marshalAdType( em.adType),
              creat ve d =  em.creat ve d
            )
          )
        )
      case  em: sl ce.AdGroup em =>
        t.Sl ce em.AdGroup em(
          t.AdGroup em(
            adGroupKey = t.AdGroupKey(
              adAccount d =  em.adAccount d,
              adGroup d =  em.adGroup d
            )
          )
        )
      case  em: sl ce.Campa gn em =>
        t.Sl ce em.Campa gn em(
          t.Campa gn em(
            campa gnKey = t.Campa gnKey(
              adAccount d =  em.adAccount d,
              campa gn d =  em.campa gn d
            )
          )
        )
      case  em: sl ce.Fund ngS ce em =>
        t.Sl ce em.Fund ngS ce em(
          t.Fund ngS ce em(
            fund ngS ceKey = t.Fund ngS ceKey(
              adAccount d =  em.adAccount d,
              fund ngS ce d =  em.fund ngS ce d
            )
          )
        )
    }
  }

  pr vate def marshalTypea ad tadata( tadata: sl ce.Typea ad tadata) = {
    t.Typea ad tadata(
      score =  tadata.score,
      s ce =  tadata.s ce,
      resultContext =  tadata.context.map(context =>
        t.Typea adResultContext(
          d splayStr ng = context.d splayStr ng,
          contextType = marshalRequestContextType(context.contextType),
           conUrl = context. conUrl
        ))
    )
  }

  pr vate def marshalRequestContextType(
    context: sl ce.Typea adResultContextType
  ): t.Typea adResultContextType = {
    context match {
      case sl ce.  => t.Typea adResultContextType. 
      case sl ce.Locat on => t.Typea adResultContextType.Locat on
      case sl ce.NumFollo rs => t.Typea adResultContextType.NumFollo rs
      case sl ce.FollowRelat onsh p => t.Typea adResultContextType.FollowRelat onsh p
      case sl ce.B o => t.Typea adResultContextType.B o
      case sl ce.NumT ets => t.Typea adResultContextType.NumT ets
      case sl ce.Trend ng => t.Typea adResultContextType.Trend ng
      case sl ce.H ghl ghtedLabel => t.Typea adResultContextType.H ghl ghtedLabel
      case _ => t.Typea adResultContextType.Undef ned
    }
  }

  pr vate def marshalAdType(
    adType: AdType
  ): t.AdType = {
    adType match {
      case AdType.T et => t.AdType.T et
      case AdType.Account => t.AdType.Account
      case AdType. nStreamV deo => t.AdType. nStreamV deo
      case AdType.D splayCreat ve => t.AdType.D splayCreat ve
      case AdType.Trend => t.AdType.Trend
      case AdType.Spotl ght => t.AdType.Spotl ght
      case AdType.Takeover => t.AdType.Takeover
    }
  }
}
