package com.tw ter.un f ed_user_act ons.adapter.ads_callback_engage nts

 mport com.tw ter.ads.eventstream.thr ftscala.Engage ntEvent
 mport com.tw ter.adserver.thr ftscala.Engage ntType
 mport com.tw ter.un f ed_user_act ons.adapter.ads_callback_engage nts.AdsCallbackEngage nt._

object Engage ntTypeMapp ngs {

  /**
   * Ads could be T ets or non-T ets. S nce UUA expl c ly sets t   em type,    s
   * poss ble that one Ads Callback engage nt type maps to mult ple UUA act on types.
   */
  def getEngage ntMapp ngs(
    engage ntEvent: Opt on[Engage ntEvent]
  ): Seq[BaseAdsCallbackEngage nt] = {
    val promotedT et d: Opt on[Long] =
      engage ntEvent.flatMap(_. mpress onData).flatMap(_.promotedT et d)
    engage ntEvent
      .map(event =>
        event.engage ntType match {
          case Engage ntType.Fav => Seq(PromotedT etFav)
          case Engage ntType.Unfav => Seq(PromotedT etUnfav)
          case Engage ntType.Reply => Seq(PromotedT etReply)
          case Engage ntType.Ret et => Seq(PromotedT etRet et)
          case Engage ntType.Block => Seq(PromotedT etBlockAuthor)
          case Engage ntType.Unblock => Seq(PromotedT etUnblockAuthor)
          case Engage ntType.Send => Seq(PromotedT etComposeT et)
          case Engage ntType.Deta l => Seq(PromotedT etCl ck)
          case Engage ntType.Report => Seq(PromotedT etReport)
          case Engage ntType.Follow => Seq(PromotedProf leFollow)
          case Engage ntType.Unfollow => Seq(PromotedProf leUnfollow)
          case Engage ntType.Mute => Seq(PromotedT etMuteAuthor)
          case Engage ntType.Prof leP c => Seq(PromotedT etCl ckProf le)
          case Engage ntType.ScreenNa  => Seq(PromotedT etCl ckProf le)
          case Engage ntType.UserNa  => Seq(PromotedT etCl ckProf le)
          case Engage ntType.Hashtag => Seq(PromotedT etCl ckHashtag)
          case Engage ntType.Url => Seq(PromotedT etOpenL nk)
          case Engage ntType.CarouselSw peNext => Seq(PromotedT etCarouselSw peNext)
          case Engage ntType.CarouselSw pePrev ous => Seq(PromotedT etCarouselSw pePrev ous)
          case Engage ntType.D llShort => Seq(PromotedT etL nger mpress onShort)
          case Engage ntType.D ll d um => Seq(PromotedT etL nger mpress on d um)
          case Engage ntType.D llLong => Seq(PromotedT etL nger mpress onLong)
          case Engage ntType.Spotl ghtCl ck => Seq(PromotedT etCl ckSpotl ght)
          case Engage ntType.Spotl ghtV ew => Seq(PromotedT etV ewSpotl ght)
          case Engage ntType.TrendV ew => Seq(PromotedTrendV ew)
          case Engage ntType.TrendCl ck => Seq(PromotedTrendCl ck)
          case Engage ntType.V deoContentPlayback25 => Seq(PromotedT etV deoPlayback25)
          case Engage ntType.V deoContentPlayback50 => Seq(PromotedT etV deoPlayback50)
          case Engage ntType.V deoContentPlayback75 => Seq(PromotedT etV deoPlayback75)
          case Engage ntType.V deoAdPlayback25  f promotedT et d. sDef ned =>
            Seq(PromotedT etV deoAdPlayback25)
          case Engage ntType.V deoAdPlayback25  f promotedT et d. sEmpty =>
            Seq(T etV deoAdPlayback25)
          case Engage ntType.V deoAdPlayback50  f promotedT et d. sDef ned =>
            Seq(PromotedT etV deoAdPlayback50)
          case Engage ntType.V deoAdPlayback50  f promotedT et d. sEmpty =>
            Seq(T etV deoAdPlayback50)
          case Engage ntType.V deoAdPlayback75  f promotedT et d. sDef ned =>
            Seq(PromotedT etV deoAdPlayback75)
          case Engage ntType.V deoAdPlayback75  f promotedT et d. sEmpty =>
            Seq(T etV deoAdPlayback75)
          case Engage ntType.D sm ssRepet  ve => Seq(PromotedT etD sm ssRepet  ve)
          case Engage ntType.D sm ssSpam => Seq(PromotedT etD sm ssSpam)
          case Engage ntType.D sm ssUn nterest ng => Seq(PromotedT etD sm ssUn nterest ng)
          case Engage ntType.D sm ssW houtReason => Seq(PromotedT etD sm ssW houtReason)
          case _ => N l
        }).toSeq.flatten
  }
}
