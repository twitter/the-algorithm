packagelon com.twittelonr.product_mixelonr.componelonnt_library.pipelonlinelon.candidatelon.who_to_follow_modulelon

import com.twittelonr.product_mixelonr.componelonnt_library.candidatelon_sourcelon.account_reloncommelonndations_mixelonr.WhoToFollowModulelonFootelonrFelonaturelon
import com.twittelonr.product_mixelonr.componelonnt_library.candidatelon_sourcelon.account_reloncommelonndations_mixelonr.WhoToFollowModulelonHelonadelonrFelonaturelon
import com.twittelonr.product_mixelonr.componelonnt_library.deloncorator.urt.UrtItelonmCandidatelonDeloncorator
import com.twittelonr.product_mixelonr.componelonnt_library.deloncorator.urt.UrtItelonmInModulelonDeloncorator
import com.twittelonr.product_mixelonr.componelonnt_library.deloncorator.urt.buildelonr.itelonm.uselonr.UselonrCandidatelonUrtItelonmBuildelonr
import com.twittelonr.product_mixelonr.componelonnt_library.deloncorator.urt.buildelonr.melontadata.ClielonntelonvelonntInfoBuildelonr
import com.twittelonr.product_mixelonr.componelonnt_library.deloncorator.urt.buildelonr.melontadata.StaticUrlBuildelonr
import com.twittelonr.product_mixelonr.componelonnt_library.deloncorator.urt.buildelonr.promotelond.FelonaturelonPromotelondMelontadataBuildelonr
import com.twittelonr.product_mixelonr.componelonnt_library.deloncorator.urt.buildelonr.social_contelonxt.WhoToFollowSocialContelonxtBuildelonr
import com.twittelonr.product_mixelonr.componelonnt_library.deloncorator.urt.buildelonr.stringcelonntelonr.StrStatic
import com.twittelonr.product_mixelonr.componelonnt_library.deloncorator.urt.buildelonr.timelonlinelon_modulelon.ModulelonFootelonrBuildelonr
import com.twittelonr.product_mixelonr.componelonnt_library.deloncorator.urt.buildelonr.timelonlinelon_modulelon.ModulelonHelonadelonrBuildelonr
import com.twittelonr.product_mixelonr.componelonnt_library.deloncorator.urt.buildelonr.timelonlinelon_modulelon.TimelonlinelonModulelonBuildelonr
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.UselonrCandidatelon
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.CandidatelonDeloncorator
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.Deloncoration
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.melontadata.BaselonFelonelondbackActionInfoBuildelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.timelonlinelon_modulelon.BaselonModulelonDisplayTypelonBuildelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.CandidatelonWithFelonaturelons
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.elonntryNamelonspacelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.DelonelonpLink
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.stitch.Stitch

objelonct WhoToFollowArmCandidatelonDeloncorator {
  val ClielonntelonvelonntComponelonnt = "suggelonst_who_to_follow"
  val elonntryNamelonspacelonString = "who-to-follow"
}

caselon class WhoToFollowArmCandidatelonDeloncorator[-Quelonry <: PipelonlinelonQuelonry](
  modulelonDisplayTypelonBuildelonr: BaselonModulelonDisplayTypelonBuildelonr[Quelonry, UselonrCandidatelon],
  felonelondbackActionInfoBuildelonr: Option[
    BaselonFelonelondbackActionInfoBuildelonr[Quelonry, UselonrCandidatelon]
  ]) elonxtelonnds CandidatelonDeloncorator[Quelonry, UselonrCandidatelon] {

  ovelonrridelon delonf apply(
    quelonry: Quelonry,
    candidatelons: Selonq[CandidatelonWithFelonaturelons[UselonrCandidatelon]]
  ): Stitch[Selonq[Deloncoration]] = {
    val clielonntelonvelonntDelontailsBuildelonr = WhoToFollowClielonntelonvelonntDelontailsBuildelonr(TrackingTokelonnFelonaturelon)
    val clielonntelonvelonntInfoBuildelonr = ClielonntelonvelonntInfoBuildelonr[Quelonry, UselonrCandidatelon](
      WhoToFollowArmCandidatelonDeloncorator.ClielonntelonvelonntComponelonnt,
      Somelon(clielonntelonvelonntDelontailsBuildelonr))
    val promotelondMelontadataBuildelonr = FelonaturelonPromotelondMelontadataBuildelonr(AdImprelonssionFelonaturelon)
    val socialContelonxtBuildelonr =
      WhoToFollowSocialContelonxtBuildelonr(SocialTelonxtFelonaturelon, HelonrmitContelonxtTypelonFelonaturelon)
    val uselonrItelonmBuildelonr = UselonrCandidatelonUrtItelonmBuildelonr(
      clielonntelonvelonntInfoBuildelonr = clielonntelonvelonntInfoBuildelonr,
      promotelondMelontadataBuildelonr = Somelon(promotelondMelontadataBuildelonr),
      socialContelonxtBuildelonr = Somelon(socialContelonxtBuildelonr))
    val uselonrItelonmDeloncorator = UrtItelonmCandidatelonDeloncorator(uselonrItelonmBuildelonr)

    val whoToFollowModulelonBuildelonr = {
      val whoToFollowHelonadelonrOpt = quelonry.felonaturelons.map(_.gelont(WhoToFollowModulelonHelonadelonrFelonaturelon))
      val whoToFollowFootelonrOpt = quelonry.felonaturelons.flatMap(_.gelont(WhoToFollowModulelonFootelonrFelonaturelon))
      val whoToFollowModulelonHelonadelonrBuildelonr = whoToFollowHelonadelonrOpt.flatMap(_.titlelon).map { titlelon =>
        ModulelonHelonadelonrBuildelonr(telonxtBuildelonr = StrStatic(titlelon), isSticky = Somelon(truelon))
      }
      val whoToFollowModulelonFootelonrBuildelonr = whoToFollowFootelonrOpt.flatMap(_.action).map { action =>
        ModulelonFootelonrBuildelonr(
          telonxtBuildelonr = StrStatic(action.titlelon),
          urlBuildelonr = Somelon(StaticUrlBuildelonr(action.actionUrl, DelonelonpLink)))
      }

      TimelonlinelonModulelonBuildelonr(
        elonntryNamelonspacelon = elonntryNamelonspacelon(WhoToFollowArmCandidatelonDeloncorator.elonntryNamelonspacelonString),
        clielonntelonvelonntInfoBuildelonr = clielonntelonvelonntInfoBuildelonr,
        displayTypelonBuildelonr = modulelonDisplayTypelonBuildelonr,
        helonadelonrBuildelonr = whoToFollowModulelonHelonadelonrBuildelonr,
        footelonrBuildelonr = whoToFollowModulelonFootelonrBuildelonr,
        felonelondbackActionInfoBuildelonr = felonelondbackActionInfoBuildelonr,
      )
    }

    UrtItelonmInModulelonDeloncorator(
      uselonrItelonmDeloncorator,
      whoToFollowModulelonBuildelonr
    ).apply(quelonry, candidatelons)
  }
}
