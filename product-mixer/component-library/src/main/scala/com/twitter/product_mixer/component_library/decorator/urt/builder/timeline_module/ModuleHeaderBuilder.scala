packagelon com.twittelonr.product_mixelonr.componelonnt_library.deloncorator.urt.buildelonr.timelonlinelon_modulelon

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.icon.BaselonHorizonIconBuildelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.melontadata.BaselonStr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.social_contelonxt.BaselonModulelonSocialContelonxtBuildelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.timelonlinelon_modulelon.BaselonModulelonHelonadelonrBuildelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.timelonlinelon_modulelon.BaselonModulelonHelonadelonrDisplayTypelonBuildelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.timelonlinelon_modulelon.Classic
import com.twittelonr.product_mixelonr.corelon.modelonl.common.CandidatelonWithFelonaturelons
import com.twittelonr.product_mixelonr.corelon.modelonl.common.UnivelonrsalNoun
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.ImagelonVariant
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.timelonlinelon_modulelon.ModulelonHelonadelonr
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry

caselon class ModulelonHelonadelonrBuildelonr[-Quelonry <: PipelonlinelonQuelonry, -Candidatelon <: UnivelonrsalNoun[Any]](
  telonxtBuildelonr: BaselonStr[Quelonry, Candidatelon],
  isSticky: Option[Boolelonan] = Nonelon,
  modulelonHelonadelonrIconBuildelonr: Option[BaselonHorizonIconBuildelonr[Quelonry, Candidatelon]] = Nonelon,
  customIcon: Option[ImagelonVariant] = Nonelon,
  modulelonSocialContelonxtBuildelonr: Option[BaselonModulelonSocialContelonxtBuildelonr[Quelonry, Candidatelon]] = Nonelon,
  modulelonHelonadelonrDisplayTypelonBuildelonr: BaselonModulelonHelonadelonrDisplayTypelonBuildelonr[Quelonry, Candidatelon] =
    ModulelonHelonadelonrDisplayTypelonBuildelonr(Classic))
    elonxtelonnds BaselonModulelonHelonadelonrBuildelonr[Quelonry, Candidatelon] {

  ovelonrridelon delonf apply(
    quelonry: Quelonry,
    candidatelons: Selonq[CandidatelonWithFelonaturelons[Candidatelon]]
  ): Option[ModulelonHelonadelonr] = {
    val firstCandidatelon = candidatelons.helonad
    Somelon(
      ModulelonHelonadelonr(
        telonxt = telonxtBuildelonr(quelonry, firstCandidatelon.candidatelon, firstCandidatelon.felonaturelons),
        sticky = isSticky,
        customIcon = customIcon,
        socialContelonxt = modulelonSocialContelonxtBuildelonr.flatMap(_.apply(quelonry, candidatelons)),
        icon = modulelonHelonadelonrIconBuildelonr.flatMap(_.apply(quelonry, candidatelons)),
        modulelonHelonadelonrDisplayTypelon = modulelonHelonadelonrDisplayTypelonBuildelonr(quelonry, candidatelons),
      )
    )
  }
}
