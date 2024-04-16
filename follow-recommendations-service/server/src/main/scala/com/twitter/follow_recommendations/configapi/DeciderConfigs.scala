package com.tw ter.follow_recom ndat ons.conf gap 

 mport com.tw ter.dec der.Rec p ent
 mport com.tw ter.dec der.S mpleRec p ent
 mport com.tw ter.follow_recom ndat ons.conf gap .dec ders.Dec derKey
 mport com.tw ter.follow_recom ndat ons.conf gap .dec ders.Dec derParams
 mport com.tw ter.follow_recom ndat ons.products.ho _t  l ne_t et_recs.conf gap .Ho T  l neT etRecsParams
 mport com.tw ter.servo.dec der.Dec derGateBu lder
 mport com.tw ter.t  l nes.conf gap ._
 mport com.tw ter.t  l nes.conf gap .dec der.Dec derSw chOverr deValue
 mport com.tw ter.t  l nes.conf gap .dec der.GuestRec p ent
 mport com.tw ter.t  l nes.conf gap .dec der.Rec p entBu lder
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Dec derConf gs @ nject() (dec derGateBu lder: Dec derGateBu lder) {
  val overr des: Seq[Opt onalOverr de[_]] = Dec derConf gs.ParamsToDec derMap.map {
    case (params, dec derKey) =>
      params.opt onalOverr deValue(
        Dec derSw chOverr deValue(
          feature = dec derGateBu lder.keyToFeature(dec derKey),
          enabledValue = true,
          rec p entBu lder = Dec derConf gs.UserOrGuestOrRequest
        )
      )
  }.toSeq

  val conf g: BaseConf g = BaseConf gBu lder(overr des).bu ld("FollowRecom ndat onServ ceDec ders")
}

object Dec derConf gs {
  val ParamsToDec derMap = Map(
    Dec derParams.EnableRecom ndat ons -> Dec derKey.EnableRecom ndat ons,
    Dec derParams.EnableScoreUserCand dates -> Dec derKey.EnableScoreUserCand dates,
    Ho T  l neT etRecsParams.EnableProduct -> Dec derKey.EnableHo T  l neT etRecsProduct,
  )

  object UserOrGuestOrRequest extends Rec p entBu lder {

    def apply(requestContext: BaseRequestContext): Opt on[Rec p ent] = requestContext match {
      case c: W hUser d  f c.user d. sDef ned =>
        c.user d.map(S mpleRec p ent)
      case c: W hGuest d  f c.guest d. sDef ned =>
        c.guest d.map(GuestRec p ent)
      case c: W hGuest d =>
        Rec p entBu lder.Request(c)
      case _ =>
        throw new Undef nedUser dNorGuest DExcept on(requestContext)
    }
  }
}
