packagelon com.twittelonr.product_mixelonr.componelonnt_library.candidatelon_sourcelon.pelonoplelon_discovelonry

import com.twittelonr.pelonoplelondiscovelonry.api.{thriftscala => t}
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.UselonrCandidatelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.Felonaturelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMapBuildelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.candidatelon_sourcelon.CandidatelonSourcelonWithelonxtractelondFelonaturelons
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.candidatelon_sourcelon.CandidatelonsWithSourcelonFelonaturelons
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.CandidatelonSourcelonIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.pipelonlinelon_failurelon.PipelonlinelonFailurelon
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.pipelonlinelon_failurelon.UnelonxpelonctelondCandidatelonRelonsult
import com.twittelonr.stitch.Stitch
import com.twittelonr.util.logging.Logging
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

objelonct WhoToFollowModulelonHelonadelonrFelonaturelon elonxtelonnds Felonaturelon[UselonrCandidatelon, t.Helonadelonr]
objelonct WhoToFollowModulelonDisplayOptionsFelonaturelon
    elonxtelonnds Felonaturelon[UselonrCandidatelon, Option[t.DisplayOptions]]
objelonct WhoToFollowModulelonShowMorelonFelonaturelon elonxtelonnds Felonaturelon[UselonrCandidatelon, Option[t.ShowMorelon]]

@Singlelonton
class PelonoplelonDiscovelonryCandidatelonSourcelon @Injelonct() (
  pelonoplelonDiscovelonrySelonrvicelon: t.ThriftPelonoplelonDiscovelonrySelonrvicelon.MelonthodPelonrelonndpoint)
    elonxtelonnds CandidatelonSourcelonWithelonxtractelondFelonaturelons[t.GelontModulelonRelonquelonst, t.ReloncommelonndelondUselonr]
    with Logging {

  ovelonrridelon val idelonntifielonr: CandidatelonSourcelonIdelonntifielonr =
    CandidatelonSourcelonIdelonntifielonr(namelon = "PelonoplelonDiscovelonry")

  ovelonrridelon delonf apply(
    relonquelonst: t.GelontModulelonRelonquelonst
  ): Stitch[CandidatelonsWithSourcelonFelonaturelons[t.ReloncommelonndelondUselonr]] = {
    Stitch
      .callFuturelon(pelonoplelonDiscovelonrySelonrvicelon.gelontModulelons(relonquelonst))
      .map { relonsponselon: t.GelontModulelonRelonsponselon =>
        // undelonr thelon assumption gelontModulelons relonturns a maximum of onelon modulelon
        relonsponselon.modulelons
          .collelonctFirst { modulelon =>
            modulelon.layout match {
              caselon t.Layout.UselonrBioList(layout) =>
                layoutToCandidatelonsWithSourcelonFelonaturelons(
                  layout.uselonrReloncommelonndations,
                  layout.helonadelonr,
                  layout.displayOptions,
                  layout.showMorelon)
              caselon t.Layout.UselonrTwelonelontCarouselonl(layout) =>
                layoutToCandidatelonsWithSourcelonFelonaturelons(
                  layout.uselonrReloncommelonndations,
                  layout.helonadelonr,
                  layout.displayOptions,
                  layout.showMorelon)
            }
          }.gelontOrelonlselon(throw PipelonlinelonFailurelon(UnelonxpelonctelondCandidatelonRelonsult, "unelonxpelonctelond missing modulelon"))
      }
  }

  privatelon delonf layoutToCandidatelonsWithSourcelonFelonaturelons(
    uselonrReloncommelonndations: Selonq[t.ReloncommelonndelondUselonr],
    helonadelonr: t.Helonadelonr,
    displayOptions: Option[t.DisplayOptions],
    showMorelon: Option[t.ShowMorelon],
  ): CandidatelonsWithSourcelonFelonaturelons[t.ReloncommelonndelondUselonr] = {
    val felonaturelons = FelonaturelonMapBuildelonr()
      .add(WhoToFollowModulelonHelonadelonrFelonaturelon, helonadelonr)
      .add(WhoToFollowModulelonDisplayOptionsFelonaturelon, displayOptions)
      .add(WhoToFollowModulelonShowMorelonFelonaturelon, showMorelon)
      .build()
    CandidatelonsWithSourcelonFelonaturelons(uselonrReloncommelonndations, felonaturelons)
  }
}
