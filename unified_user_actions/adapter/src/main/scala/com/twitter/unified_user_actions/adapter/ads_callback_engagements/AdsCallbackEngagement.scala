package com.tw ter.un f ed_user_act ons.adapter.ads_callback_engage nts

 mport com.tw ter.ads.spendserver.thr ftscala.SpendServerEvent
 mport com.tw ter.un f ed_user_act ons.thr ftscala._

object AdsCallbackEngage nt {
  object PromotedT etFav extends BaseAdsCallbackEngage nt(Act onType.ServerPromotedT etFav)

  object PromotedT etUnfav extends BaseAdsCallbackEngage nt(Act onType.ServerPromotedT etUnfav)

  object PromotedT etReply extends BaseAdsCallbackEngage nt(Act onType.ServerPromotedT etReply)

  object PromotedT etRet et
      extends BaseAdsCallbackEngage nt(Act onType.ServerPromotedT etRet et)

  object PromotedT etBlockAuthor
      extends BaseAdsCallbackEngage nt(Act onType.ServerPromotedT etBlockAuthor)

  object PromotedT etUnblockAuthor
      extends BaseAdsCallbackEngage nt(Act onType.ServerPromotedT etUnblockAuthor)

  object PromotedT etComposeT et
      extends BaseAdsCallbackEngage nt(Act onType.ServerPromotedT etComposeT et)

  object PromotedT etCl ck extends BaseAdsCallbackEngage nt(Act onType.ServerPromotedT etCl ck)

  object PromotedT etReport extends BaseAdsCallbackEngage nt(Act onType.ServerPromotedT etReport)

  object PromotedProf leFollow
      extends Prof leAdsCallbackEngage nt(Act onType.ServerPromotedProf leFollow)

  object PromotedProf leUnfollow
      extends Prof leAdsCallbackEngage nt(Act onType.ServerPromotedProf leUnfollow)

  object PromotedT etMuteAuthor
      extends BaseAdsCallbackEngage nt(Act onType.ServerPromotedT etMuteAuthor)

  object PromotedT etCl ckProf le
      extends BaseAdsCallbackEngage nt(Act onType.ServerPromotedT etCl ckProf le)

  object PromotedT etCl ckHashtag
      extends BaseAdsCallbackEngage nt(Act onType.ServerPromotedT etCl ckHashtag)

  object PromotedT etOpenL nk
      extends BaseAdsCallbackEngage nt(Act onType.ServerPromotedT etOpenL nk) {
    overr de def get em( nput: SpendServerEvent): Opt on[ em] = {
       nput.engage ntEvent.flatMap { e =>
        e. mpress onData.flatMap {   =>
          getPromotedT et nfo(
             .promotedT et d,
             .advert ser d,
            t etAct on nfoOpt = So (
              T etAct on nfo.ServerPromotedT etOpenL nk(
                ServerPromotedT etOpenL nk(url = e.url))))
        }
      }
    }
  }

  object PromotedT etCarouselSw peNext
      extends BaseAdsCallbackEngage nt(Act onType.ServerPromotedT etCarouselSw peNext)

  object PromotedT etCarouselSw pePrev ous
      extends BaseAdsCallbackEngage nt(Act onType.ServerPromotedT etCarouselSw pePrev ous)

  object PromotedT etL nger mpress onShort
      extends BaseAdsCallbackEngage nt(Act onType.ServerPromotedT etL nger mpress onShort)

  object PromotedT etL nger mpress on d um
      extends BaseAdsCallbackEngage nt(Act onType.ServerPromotedT etL nger mpress on d um)

  object PromotedT etL nger mpress onLong
      extends BaseAdsCallbackEngage nt(Act onType.ServerPromotedT etL nger mpress onLong)

  object PromotedT etCl ckSpotl ght
      extends BaseTrendAdsCallbackEngage nt(Act onType.ServerPromotedT etCl ckSpotl ght)

  object PromotedT etV ewSpotl ght
      extends BaseTrendAdsCallbackEngage nt(Act onType.ServerPromotedT etV ewSpotl ght)

  object PromotedTrendV ew
      extends BaseTrendAdsCallbackEngage nt(Act onType.ServerPromotedTrendV ew)

  object PromotedTrendCl ck
      extends BaseTrendAdsCallbackEngage nt(Act onType.ServerPromotedTrendCl ck)

  object PromotedT etV deoPlayback25
      extends BaseV deoAdsCallbackEngage nt(Act onType.ServerPromotedT etV deoPlayback25)

  object PromotedT etV deoPlayback50
      extends BaseV deoAdsCallbackEngage nt(Act onType.ServerPromotedT etV deoPlayback50)

  object PromotedT etV deoPlayback75
      extends BaseV deoAdsCallbackEngage nt(Act onType.ServerPromotedT etV deoPlayback75)

  object PromotedT etV deoAdPlayback25
      extends BaseV deoAdsCallbackEngage nt(Act onType.ServerPromotedT etV deoAdPlayback25)

  object PromotedT etV deoAdPlayback50
      extends BaseV deoAdsCallbackEngage nt(Act onType.ServerPromotedT etV deoAdPlayback50)

  object PromotedT etV deoAdPlayback75
      extends BaseV deoAdsCallbackEngage nt(Act onType.ServerPromotedT etV deoAdPlayback75)

  object T etV deoAdPlayback25
      extends BaseV deoAdsCallbackEngage nt(Act onType.ServerT etV deoAdPlayback25)

  object T etV deoAdPlayback50
      extends BaseV deoAdsCallbackEngage nt(Act onType.ServerT etV deoAdPlayback50)

  object T etV deoAdPlayback75
      extends BaseV deoAdsCallbackEngage nt(Act onType.ServerT etV deoAdPlayback75)

  object PromotedT etD sm ssW houtReason
      extends BaseAdsCallbackEngage nt(Act onType.ServerPromotedT etD sm ssW houtReason)

  object PromotedT etD sm ssUn nterest ng
      extends BaseAdsCallbackEngage nt(Act onType.ServerPromotedT etD sm ssUn nterest ng)

  object PromotedT etD sm ssRepet  ve
      extends BaseAdsCallbackEngage nt(Act onType.ServerPromotedT etD sm ssRepet  ve)

  object PromotedT etD sm ssSpam
      extends BaseAdsCallbackEngage nt(Act onType.ServerPromotedT etD sm ssSpam)
}
