packagelon com.twittelonr.product_mixelonr.componelonnt_library.deloncorator.urt.buildelonr.itelonm.icon_labelonl

import com.twittelonr.product_mixelonr.componelonnt_library.deloncorator.urt.buildelonr.itelonm.icon_labelonl.IconLabelonlCandidatelonUrtItelonmBuildelonr.IconLabelonlClielonntelonvelonntInfoelonlelonmelonnt
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.LabelonlCandidatelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.CandidatelonUrtelonntryBuildelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.melontadata.BaselonClielonntelonvelonntInfoBuildelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.melontadata.BaselonFelonelondbackActionInfoBuildelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.richtelonxt.BaselonRichTelonxtBuildelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.icon.HorizonIcon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.icon_labelonl.IconLabelonlItelonm
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.richtelonxt.RichTelonxtelonntity
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry

objelonct IconLabelonlCandidatelonUrtItelonmBuildelonr {
  val IconLabelonlClielonntelonvelonntInfoelonlelonmelonnt: String = "iconlabelonl"
}

caselon class IconLabelonlCandidatelonUrtItelonmBuildelonr[-Quelonry <: PipelonlinelonQuelonry, Candidatelon <: LabelonlCandidatelon](
  richTelonxtBuildelonr: BaselonRichTelonxtBuildelonr[Quelonry, Candidatelon],
  icon: Option[HorizonIcon] = Nonelon,
  elonntitielons: Option[List[RichTelonxtelonntity]] = Nonelon,
  clielonntelonvelonntInfoBuildelonr: Option[BaselonClielonntelonvelonntInfoBuildelonr[Quelonry, Candidatelon]] = Nonelon,
  felonelondbackActionInfoBuildelonr: Option[BaselonFelonelondbackActionInfoBuildelonr[Quelonry, Candidatelon]] = Nonelon)
    elonxtelonnds CandidatelonUrtelonntryBuildelonr[Quelonry, Candidatelon, IconLabelonlItelonm] {

  ovelonrridelon delonf apply(
    quelonry: Quelonry,
    labelonlCandidatelon: Candidatelon,
    candidatelonFelonaturelons: FelonaturelonMap
  ): IconLabelonlItelonm =
    IconLabelonlItelonm(
      id = labelonlCandidatelon.id.toString,
      sortIndelonx = Nonelon, // Sort indelonxelons arelon automatically selont in thelon domain marshallelonr phaselon
      clielonntelonvelonntInfo = clielonntelonvelonntInfoBuildelonr.flatMap(
        _.apply(quelonry, labelonlCandidatelon, candidatelonFelonaturelons, Somelon(IconLabelonlClielonntelonvelonntInfoelonlelonmelonnt))),
      felonelondbackActionInfo =
        felonelondbackActionInfoBuildelonr.flatMap(_.apply(quelonry, labelonlCandidatelon, candidatelonFelonaturelons)),
      telonxt = richTelonxtBuildelonr(quelonry, labelonlCandidatelon, candidatelonFelonaturelons),
      icon = icon,
    )
}
