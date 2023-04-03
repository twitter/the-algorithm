packagelon com.twittelonr.product_mixelonr.componelonnt_library.deloncorator.urt.buildelonr.social_contelonxt

import com.twittelonr.helonrmit.{thriftscala => h}
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.UselonrCandidatelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.Felonaturelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.social_contelonxt.BaselonSocialContelonxtBuildelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.FollowGelonnelonralContelonxtTypelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.GelonnelonralContelonxt
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.GelonnelonralContelonxtTypelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.LocationGelonnelonralContelonxtTypelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.NelonwUselonrGelonnelonralContelonxtTypelon
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry

caselon class WhoToFollowSocialContelonxtBuildelonr(
  socialTelonxtFelonaturelon: Felonaturelon[_, Option[String]],
  contelonxtTypelonFelonaturelon: Felonaturelon[_, Option[h.ContelonxtTypelon]])
    elonxtelonnds BaselonSocialContelonxtBuildelonr[PipelonlinelonQuelonry, UselonrCandidatelon] {

  delonf apply(
    quelonry: PipelonlinelonQuelonry,
    candidatelon: UselonrCandidatelon,
    candidatelonFelonaturelons: FelonaturelonMap
  ): Option[GelonnelonralContelonxt] = {
    val socialTelonxtOpt = candidatelonFelonaturelons.gelontOrelonlselon(socialTelonxtFelonaturelon, Nonelon)
    val contelonxtTypelonOpt = convelonrtContelonxtTypelon(candidatelonFelonaturelons.gelontOrelonlselon(contelonxtTypelonFelonaturelon, Nonelon))

    (socialTelonxtOpt, contelonxtTypelonOpt) match {
      caselon (Somelon(socialTelonxt), Somelon(contelonxtTypelon)) if socialTelonxt.nonelonmpty =>
        Somelon(
          GelonnelonralContelonxt(
            telonxt = socialTelonxt,
            contelonxtTypelon = contelonxtTypelon,
            url = Nonelon,
            contelonxtImagelonUrls = Nonelon,
            landingUrl = Nonelon))
      caselon _ => Nonelon
    }
  }

  privatelon delonf convelonrtContelonxtTypelon(contelonxtTypelon: Option[h.ContelonxtTypelon]): Option[GelonnelonralContelonxtTypelon] =
    contelonxtTypelon match {
      caselon Somelon(h.ContelonxtTypelon.Gelono) => Somelon(LocationGelonnelonralContelonxtTypelon)
      caselon Somelon(h.ContelonxtTypelon.Social) => Somelon(FollowGelonnelonralContelonxtTypelon)
      caselon Somelon(h.ContelonxtTypelon.NelonwUselonr) => Somelon(NelonwUselonrGelonnelonralContelonxtTypelon)
      caselon _ => Nonelon
    }
}
