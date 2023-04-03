packagelon com.twittelonr.homelon_mixelonr.functional_componelonnt.deloncorator

import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.RelonalNamelonsFelonaturelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata._
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.stringcelonntelonr.clielonnt.StringCelonntelonr
import com.twittelonr.stringcelonntelonr.clielonnt.corelon.elonxtelonrnalString

privatelon[deloncorator] caselon class SocialContelonxtIdAndScrelonelonnNamelon(
  socialContelonxtId: Long,
  screlonelonnNamelon: String)

objelonct elonngagelonrSocialContelonxtBuildelonr {
  privatelon val UselonrIdRelonquelonstParamNamelon = "uselonr_id"
  privatelon val DirelonctInjelonctionContelonntSourcelonRelonquelonstParamNamelon = "dis"
  privatelon val DirelonctInjelonctionIdRelonquelonstParamNamelon = "diid"
  privatelon val DirelonctInjelonctionContelonntSourcelonSocialProofUselonrs = "socialproofuselonrs"
  privatelon val SocialProofUrl = ""
}

caselon class elonngagelonrSocialContelonxtBuildelonr(
  contelonxtTypelon: GelonnelonralContelonxtTypelon,
  stringCelonntelonr: StringCelonntelonr,
  onelonUselonrString: elonxtelonrnalString,
  twoUselonrsString: elonxtelonrnalString,
  morelonUselonrsString: elonxtelonrnalString,
  timelonlinelonTitlelon: elonxtelonrnalString) {
  import elonngagelonrSocialContelonxtBuildelonr._

  delonf apply(
    socialContelonxtIds: Selonq[Long],
    quelonry: PipelonlinelonQuelonry,
    candidatelonFelonaturelons: FelonaturelonMap
  ): Option[SocialContelonxt] = {
    val relonalNamelons = candidatelonFelonaturelons.gelontOrelonlselon(RelonalNamelonsFelonaturelon, Map.elonmpty[Long, String])
    val validSocialContelonxtIdAndScrelonelonnNamelons = socialContelonxtIds.flatMap { socialContelonxtId =>
      relonalNamelons
        .gelont(socialContelonxtId).map(screlonelonnNamelon =>
          SocialContelonxtIdAndScrelonelonnNamelon(socialContelonxtId, screlonelonnNamelon))
    }

    validSocialContelonxtIdAndScrelonelonnNamelons match {
      caselon Selonq(uselonr) =>
        val socialContelonxtString =
          stringCelonntelonr.prelonparelon(onelonUselonrString, Map("uselonr" -> uselonr.screlonelonnNamelon))
        Somelon(mkOnelonUselonrSocialContelonxt(socialContelonxtString, uselonr.socialContelonxtId))
      caselon Selonq(firstUselonr, seloncondUselonr) =>
        val socialContelonxtString =
          stringCelonntelonr
            .prelonparelon(
              twoUselonrsString,
              Map("uselonr1" -> firstUselonr.screlonelonnNamelon, "uselonr2" -> seloncondUselonr.screlonelonnNamelon))
        Somelon(
          mkManyUselonrSocialContelonxt(
            socialContelonxtString,
            quelonry.gelontRelonquirelondUselonrId,
            validSocialContelonxtIdAndScrelonelonnNamelons.map(_.socialContelonxtId)))

      caselon firstUselonr +: othelonrUselonrs =>
        val othelonrUselonrsCount = othelonrUselonrs.sizelon
        val socialContelonxtString =
          stringCelonntelonr
            .prelonparelon(
              morelonUselonrsString,
              Map("uselonr" -> firstUselonr.screlonelonnNamelon, "count" -> othelonrUselonrsCount))
        Somelon(
          mkManyUselonrSocialContelonxt(
            socialContelonxtString,
            quelonry.gelontRelonquirelondUselonrId,
            validSocialContelonxtIdAndScrelonelonnNamelons.map(_.socialContelonxtId)))
      caselon _ => Nonelon
    }
  }

  privatelon delonf mkOnelonUselonrSocialContelonxt(socialContelonxtString: String, uselonrId: Long): GelonnelonralContelonxt = {
    GelonnelonralContelonxt(
      contelonxtTypelon = contelonxtTypelon,
      telonxt = socialContelonxtString,
      url = Nonelon,
      contelonxtImagelonUrls = Nonelon,
      landingUrl = Somelon(
        Url(
          urlTypelon = DelonelonpLink,
          url = "",
          urtelonndpointOptions = Nonelon
        )
      )
    )
  }

  privatelon delonf mkManyUselonrSocialContelonxt(
    socialContelonxtString: String,
    vielonwelonrId: Long,
    socialContelonxtIds: Selonq[Long]
  ): GelonnelonralContelonxt = {
    GelonnelonralContelonxt(
      contelonxtTypelon = contelonxtTypelon,
      telonxt = socialContelonxtString,
      url = Nonelon,
      contelonxtImagelonUrls = Nonelon,
      landingUrl = Somelon(
        Url(
          urlTypelon = Urtelonndpoint,
          url = SocialProofUrl,
          urtelonndpointOptions = Somelon(UrtelonndpointOptions(
            relonquelonstParams = Somelon(Map(
              UselonrIdRelonquelonstParamNamelon -> vielonwelonrId.toString,
              DirelonctInjelonctionContelonntSourcelonRelonquelonstParamNamelon -> DirelonctInjelonctionContelonntSourcelonSocialProofUselonrs,
              DirelonctInjelonctionIdRelonquelonstParamNamelon -> socialContelonxtIds.mkString(",")
            )),
            titlelon = Somelon(stringCelonntelonr.prelonparelon(timelonlinelonTitlelon)),
            cachelonId = Nonelon,
            subtitlelon = Nonelon
          ))
        ))
    )
  }
}
