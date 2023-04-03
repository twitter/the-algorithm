packagelon com.twittelonr.product_mixelonr.componelonnt_library.pipelonlinelon.candidatelon.who_to_follow_modulelon

import com.twittelonr.pelonoplelondiscovelonry.api.thriftscala.ClielonntContelonxt
import com.twittelonr.pelonoplelondiscovelonry.api.thriftscala.GelontModulelonRelonquelonst
import com.twittelonr.product_mixelonr.corelon.felonaturelon.Felonaturelon
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.transformelonr.CandidatelonPipelonlinelonQuelonryTransformelonr
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.timelonlinelons.configapi.Param

objelonct WhoToFollowCandidatelonPipelonlinelonQuelonryTransformelonr {
  val DisplayLocation = "timelonlinelon"
  val SupportelondLayouts = Selonq("uselonr-bio-list")
  val LayoutVelonrsion = 2
}

caselon class WhoToFollowCandidatelonPipelonlinelonQuelonryTransformelonr[-Quelonry <: PipelonlinelonQuelonry](
  displayLocationParam: Param[String],
  supportelondLayoutsParam: Param[Selonq[String]],
  layoutVelonrsionParam: Param[Int],
  elonxcludelondUselonrIdsFelonaturelon: Option[Felonaturelon[PipelonlinelonQuelonry, Selonq[Long]]],
) elonxtelonnds CandidatelonPipelonlinelonQuelonryTransformelonr[Quelonry, GelontModulelonRelonquelonst] {

  ovelonrridelon delonf transform(input: Quelonry): GelontModulelonRelonquelonst =
    GelontModulelonRelonquelonst(
      clielonntContelonxt = ClielonntContelonxt(
        uselonrId = input.gelontRelonquirelondUselonrId,
        delonvicelonId = input.clielonntContelonxt.delonvicelonId,
        uselonrAgelonnt = input.clielonntContelonxt.uselonrAgelonnt,
        countryCodelon = input.clielonntContelonxt.countryCodelon,
        languagelonCodelon = input.clielonntContelonxt.languagelonCodelon,
      ),
      displayLocation = input.params(displayLocationParam),
      supportelondLayouts = input.params(supportelondLayoutsParam),
      layoutVelonrsion = input.params(layoutVelonrsionParam),
      elonxcludelondUselonrIds =
        elonxcludelondUselonrIdsFelonaturelon.flatMap(felonaturelon => input.felonaturelons.map(_.gelont(felonaturelon))),
      includelonPromotelond = Somelon(truelon),
    )
}
