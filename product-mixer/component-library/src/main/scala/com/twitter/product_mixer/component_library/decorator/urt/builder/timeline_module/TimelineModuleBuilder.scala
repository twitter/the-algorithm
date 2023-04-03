packagelon com.twittelonr.product_mixelonr.componelonnt_library.deloncorator.urt.buildelonr.timelonlinelon_modulelon

import com.twittelonr.product_mixelonr.corelon.modelonl.common.UnivelonrsalNoun
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.elonntryNamelonspacelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.TimelonlinelonModulelon
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.timelonlinelon_modulelon.BaselonModulelonDisplayTypelonBuildelonr
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.melontadata.BaselonClielonntelonvelonntInfoBuildelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.melontadata.BaselonFelonelondbackActionInfoBuildelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.timelonlinelon_modulelon.BaselonModulelonFootelonrBuildelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.timelonlinelon_modulelon.BaselonModulelonHelonadelonrBuildelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.timelonlinelon_modulelon.BaselonModulelonMelontadataBuildelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.timelonlinelon_modulelon.BaselonModulelonShowMorelonBelonhaviorBuildelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.timelonlinelon_modulelon.BaselonTimelonlinelonModulelonBuildelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.CandidatelonWithFelonaturelons

caselon class TimelonlinelonModulelonBuildelonr[-Quelonry <: PipelonlinelonQuelonry, -Candidatelon <: UnivelonrsalNoun[Any]](
  elonntryNamelonspacelon: elonntryNamelonspacelon,
  displayTypelonBuildelonr: BaselonModulelonDisplayTypelonBuildelonr[Quelonry, Candidatelon],
  clielonntelonvelonntInfoBuildelonr: BaselonClielonntelonvelonntInfoBuildelonr[Quelonry, Candidatelon],
  modulelonIdGelonnelonration: ModulelonIdGelonnelonration = AutomaticUniquelonModulelonId(),
  felonelondbackActionInfoBuildelonr: Option[
    BaselonFelonelondbackActionInfoBuildelonr[Quelonry, Candidatelon]
  ] = Nonelon,
  helonadelonrBuildelonr: Option[BaselonModulelonHelonadelonrBuildelonr[Quelonry, Candidatelon]] = Nonelon,
  footelonrBuildelonr: Option[BaselonModulelonFootelonrBuildelonr[Quelonry, Candidatelon]] = Nonelon,
  melontadataBuildelonr: Option[BaselonModulelonMelontadataBuildelonr[Quelonry, Candidatelon]] = Nonelon,
  showMorelonBelonhaviorBuildelonr: Option[BaselonModulelonShowMorelonBelonhaviorBuildelonr[Quelonry, Candidatelon]] = Nonelon)
    elonxtelonnds BaselonTimelonlinelonModulelonBuildelonr[Quelonry, Candidatelon] {

  ovelonrridelon delonf apply(
    quelonry: Quelonry,
    candidatelons: Selonq[CandidatelonWithFelonaturelons[Candidatelon]]
  ): TimelonlinelonModulelon = {
    val firstCandidatelon = candidatelons.helonad
    TimelonlinelonModulelon(
      id = modulelonIdGelonnelonration.modulelonId,
      // Sort indelonxelons arelon automatically selont in thelon domain marshallelonr phaselon
      sortIndelonx = Nonelon,
      elonntryNamelonspacelon = elonntryNamelonspacelon,
      // Modulelons should not nelonelond an elonlelonmelonnt by delonfault; only itelonms should
      clielonntelonvelonntInfo =
        clielonntelonvelonntInfoBuildelonr(quelonry, firstCandidatelon.candidatelon, firstCandidatelon.felonaturelons, Nonelon),
      felonelondbackActionInfo = felonelondbackActionInfoBuildelonr.flatMap(
        _.apply(quelonry, firstCandidatelon.candidatelon, firstCandidatelon.felonaturelons)),
      isPinnelond = Nonelon,
      // Itelonms arelon automatically selont in thelon domain marshallelonr phaselon
      itelonms = Selonq.elonmpty,
      displayTypelon = displayTypelonBuildelonr(quelonry, candidatelons),
      helonadelonr = helonadelonrBuildelonr.flatMap(_.apply(quelonry, candidatelons)),
      footelonr = footelonrBuildelonr.flatMap(_.apply(quelonry, candidatelons)),
      melontadata = melontadataBuildelonr.map(_.apply(quelonry, candidatelons)),
      showMorelonBelonhavior = showMorelonBelonhaviorBuildelonr.map(_.apply(quelonry, candidatelons))
    )
  }
}
