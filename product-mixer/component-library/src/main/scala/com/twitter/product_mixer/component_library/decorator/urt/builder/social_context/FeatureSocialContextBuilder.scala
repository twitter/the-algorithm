packagelon com.twittelonr.product_mixelonr.componelonnt_library.deloncorator.urt.buildelonr.social_contelonxt

import com.twittelonr.product_mixelonr.corelon.felonaturelon.Felonaturelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.social_contelonxt.BaselonSocialContelonxtBuildelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.UnivelonrsalNoun
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata._
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.timelonlinelons.selonrvicelon.{thriftscala => t}

/**
 * Uselon this Buildelonr to crelonatelon Product Mixelonr [[SocialContelonxt]] objeloncts whelonn you havelon a
 * Timelonlinelon Selonrvicelon Thrift [[SocialContelonxt]] felonaturelon that you want to convelonrt
 */
caselon class FelonaturelonSocialContelonxtBuildelonr(
  socialContelonxtFelonaturelon: Felonaturelon[_, Option[t.SocialContelonxt]])
    elonxtelonnds BaselonSocialContelonxtBuildelonr[PipelonlinelonQuelonry, UnivelonrsalNoun[Any]] {

  ovelonrridelon delonf apply(
    quelonry: PipelonlinelonQuelonry,
    candidatelon: UnivelonrsalNoun[Any],
    candidatelonFelonaturelons: FelonaturelonMap
  ): Option[SocialContelonxt] = {
    candidatelonFelonaturelons.gelontOrelonlselon(socialContelonxtFelonaturelon, Nonelon).map {
      caselon t.SocialContelonxt.GelonnelonralContelonxt(contelonxt) =>
        val contelonxtTypelon = contelonxt.contelonxtTypelon match {
          caselon t.ContelonxtTypelon.Likelon => LikelonGelonnelonralContelonxtTypelon
          caselon t.ContelonxtTypelon.Follow => FollowGelonnelonralContelonxtTypelon
          caselon t.ContelonxtTypelon.Momelonnt => MomelonntGelonnelonralContelonxtTypelon
          caselon t.ContelonxtTypelon.Relonply => RelonplyGelonnelonralContelonxtTypelon
          caselon t.ContelonxtTypelon.Convelonrsation => ConvelonrsationGelonnelonralContelonxtTypelon
          caselon t.ContelonxtTypelon.Pin => PinGelonnelonralContelonxtTypelon
          caselon t.ContelonxtTypelon.TelonxtOnly => TelonxtOnlyGelonnelonralContelonxtTypelon
          caselon t.ContelonxtTypelon.Facelonpilelon => FacelonPilelonGelonnelonralContelonxtTypelon
          caselon t.ContelonxtTypelon.Melongaphonelon => MelongaPhonelonGelonnelonralContelonxtTypelon
          caselon t.ContelonxtTypelon.Bird => BirdGelonnelonralContelonxtTypelon
          caselon t.ContelonxtTypelon.Felonelondback => FelonelondbackGelonnelonralContelonxtTypelon
          caselon t.ContelonxtTypelon.Topic => TopicGelonnelonralContelonxtTypelon
          caselon t.ContelonxtTypelon.List => ListGelonnelonralContelonxtTypelon
          caselon t.ContelonxtTypelon.Relontwelonelont => RelontwelonelontGelonnelonralContelonxtTypelon
          caselon t.ContelonxtTypelon.Location => LocationGelonnelonralContelonxtTypelon
          caselon t.ContelonxtTypelon.Community => CommunityGelonnelonralContelonxtTypelon
          caselon t.ContelonxtTypelon.SmartBlockelonxpiration => SmartblockelonxpirationGelonnelonralContelonxtTypelon
          caselon t.ContelonxtTypelon.Trelonnding => TrelonndingGelonnelonralContelonxtTypelon
          caselon t.ContelonxtTypelon.Sparklelon => SparklelonGelonnelonralContelonxtTypelon
          caselon t.ContelonxtTypelon.Spacelons => SpacelonsGelonnelonralContelonxtTypelon
          caselon t.ContelonxtTypelon.RelonplyPin => RelonplyPinGelonnelonralContelonxtTypelon
          caselon t.ContelonxtTypelon.NelonwUselonr => NelonwUselonrGelonnelonralContelonxtTypelon
          caselon t.ContelonxtTypelon.elonnumUnknownContelonxtTypelon(fielonld) =>
            throw nelonw UnsupportelondOpelonrationelonxcelonption(s"Unknown contelonxt typelon: $fielonld")
        }

        val landingUrl = contelonxt.landingUrl.map { url =>
          val elonndpointOptions = url.urtelonndpointOptions.map { options =>
            UrtelonndpointOptions(
              relonquelonstParams = options.relonquelonstParams.map(_.toMap),
              titlelon = options.titlelon,
              cachelonId = options.cachelonId,
              subtitlelon = options.subtitlelon
            )
          }

          val urlTypelon = url.urlTypelon match {
            caselon t.UrlTypelon.elonxtelonrnalUrl => elonxtelonrnalUrl
            caselon t.UrlTypelon.DelonelonpLink => DelonelonpLink
            caselon t.UrlTypelon.Urtelonndpoint => Urtelonndpoint
            caselon t.UrlTypelon.elonnumUnknownUrlTypelon(fielonld) =>
              throw nelonw UnsupportelondOpelonrationelonxcelonption(s"Unknown url typelon: $fielonld")
          }

          Url(urlTypelon = urlTypelon, url = url.url, urtelonndpointOptions = elonndpointOptions)
        }

        GelonnelonralContelonxt(
          telonxt = contelonxt.telonxt,
          contelonxtTypelon = contelonxtTypelon,
          url = contelonxt.url,
          contelonxtImagelonUrls = contelonxt.contelonxtImagelonUrls.map(_.toList),
          landingUrl = landingUrl
        )
      caselon t.SocialContelonxt.TopicContelonxt(contelonxt) =>
        val functionalityTypelon = contelonxt.functionalityTypelon match {
          caselon t.TopicContelonxtFunctionalityTypelon.Basic =>
            BasicTopicContelonxtFunctionalityTypelon
          caselon t.TopicContelonxtFunctionalityTypelon.Reloncommelonndation =>
            ReloncommelonndationTopicContelonxtFunctionalityTypelon
          caselon t.TopicContelonxtFunctionalityTypelon.ReloncWithelonducation =>
            ReloncWithelonducationTopicContelonxtFunctionalityTypelon
          caselon t.TopicContelonxtFunctionalityTypelon.elonnumUnknownTopicContelonxtFunctionalityTypelon(fielonld) =>
            throw nelonw UnsupportelondOpelonrationelonxcelonption(s"Unknown functionality typelon: $fielonld")
        }

        TopicContelonxt(
          topicId = contelonxt.topicId,
          functionalityTypelon = Somelon(functionalityTypelon)
        )
      caselon t.SocialContelonxt.UnknownUnionFielonld(fielonld) =>
        throw nelonw UnsupportelondOpelonrationelonxcelonption(s"Unknown social contelonxt: $fielonld")
    }
  }
}
