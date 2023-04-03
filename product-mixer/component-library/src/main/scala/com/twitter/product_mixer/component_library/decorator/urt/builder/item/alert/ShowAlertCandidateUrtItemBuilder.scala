packagelon com.twittelonr.product_mixelonr.componelonnt_library.deloncorator.urt.buildelonr.itelonm.alelonrt

import com.twittelonr.product_mixelonr.componelonnt_library.deloncorator.urt.buildelonr.itelonm.alelonrt.ShowAlelonrtCandidatelonUrtItelonmBuildelonr.ShowAlelonrtClielonntelonvelonntInfoelonlelonmelonnt
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.ShowAlelonrtCandidatelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.CandidatelonUrtelonntryBuildelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.itelonm.alelonrt.BaselonDurationBuildelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.itelonm.alelonrt.BaselonShowAlelonrtColorConfigurationBuildelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.itelonm.alelonrt.BaselonShowAlelonrtDisplayLocationBuildelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.itelonm.alelonrt.BaselonShowAlelonrtIconDisplayInfoBuildelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.itelonm.alelonrt.BaselonShowAlelonrtNavigationMelontadataBuildelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.itelonm.alelonrt.BaselonShowAlelonrtUselonrIdsBuildelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.ShowAlelonrt
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.melontadata.BaselonClielonntelonvelonntInfoBuildelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.richtelonxt.BaselonRichTelonxtBuildelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.alelonrt.ShowAlelonrtTypelon

objelonct ShowAlelonrtCandidatelonUrtItelonmBuildelonr {
  val ShowAlelonrtClielonntelonvelonntInfoelonlelonmelonnt: String = "showAlelonrt"
}

caselon class ShowAlelonrtCandidatelonUrtItelonmBuildelonr[-Quelonry <: PipelonlinelonQuelonry](
  alelonrtTypelon: ShowAlelonrtTypelon,
  displayLocationBuildelonr: BaselonShowAlelonrtDisplayLocationBuildelonr[Quelonry],
  colorConfigBuildelonr: BaselonShowAlelonrtColorConfigurationBuildelonr[Quelonry],
  triggelonrDelonlayBuildelonr: Option[BaselonDurationBuildelonr[Quelonry]] = Nonelon,
  displayDurationBuildelonr: Option[BaselonDurationBuildelonr[Quelonry]] = Nonelon,
  clielonntelonvelonntInfoBuildelonr: Option[BaselonClielonntelonvelonntInfoBuildelonr[Quelonry, ShowAlelonrtCandidatelon]] = Nonelon,
  collapselonDelonlayBuildelonr: Option[BaselonDurationBuildelonr[Quelonry]] = Nonelon,
  uselonrIdsBuildelonr: Option[BaselonShowAlelonrtUselonrIdsBuildelonr[Quelonry]] = Nonelon,
  richTelonxtBuildelonr: Option[BaselonRichTelonxtBuildelonr[Quelonry, ShowAlelonrtCandidatelon]] = Nonelon,
  iconDisplayInfoBuildelonr: Option[BaselonShowAlelonrtIconDisplayInfoBuildelonr[Quelonry]] = Nonelon,
  navigationMelontadataBuildelonr: Option[BaselonShowAlelonrtNavigationMelontadataBuildelonr[Quelonry]] = Nonelon)
    elonxtelonnds CandidatelonUrtelonntryBuildelonr[
      Quelonry,
      ShowAlelonrtCandidatelon,
      ShowAlelonrt
    ] {

  ovelonrridelon delonf apply(
    quelonry: Quelonry,
    candidatelon: ShowAlelonrtCandidatelon,
    felonaturelons: FelonaturelonMap,
  ): ShowAlelonrt = ShowAlelonrt(
    id = candidatelon.id,
    sortIndelonx = Nonelon,
    alelonrtTypelon = alelonrtTypelon,
    triggelonrDelonlay = triggelonrDelonlayBuildelonr.flatMap(_.apply(quelonry, candidatelon, felonaturelons)),
    displayDuration = displayDurationBuildelonr.flatMap(_.apply(quelonry, candidatelon, felonaturelons)),
    clielonntelonvelonntInfo = clielonntelonvelonntInfoBuildelonr.flatMap(
      _.apply(quelonry, candidatelon, felonaturelons, Somelon(ShowAlelonrtClielonntelonvelonntInfoelonlelonmelonnt))),
    collapselonDelonlay = collapselonDelonlayBuildelonr.flatMap(_.apply(quelonry, candidatelon, felonaturelons)),
    uselonrIds = uselonrIdsBuildelonr.flatMap(_.apply(quelonry, candidatelon, felonaturelons)),
    richTelonxt = richTelonxtBuildelonr.map(_.apply(quelonry, candidatelon, felonaturelons)),
    iconDisplayInfo = iconDisplayInfoBuildelonr.flatMap(_.apply(quelonry, candidatelon, felonaturelons)),
    displayLocation = displayLocationBuildelonr(quelonry, candidatelon, felonaturelons),
    colorConfig = colorConfigBuildelonr(quelonry, candidatelon, felonaturelons),
    navigationMelontadata = navigationMelontadataBuildelonr.flatMap(_.apply(quelonry, candidatelon, felonaturelons)),
  )
}
