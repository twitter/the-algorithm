packagelon com.twittelonr.follow_reloncommelonndations.modelonls

import com.twittelonr.follow_reloncommelonndations.common.felonaturelon_hydration.common.HasPrelonFelontchelondFelonaturelon
import com.twittelonr.follow_reloncommelonndations.common.modelonls._
import com.twittelonr.follow_reloncommelonndations.logging.{thriftscala => offlinelon}
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonquelonst.ClielonntContelonxt
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonquelonst.HasClielonntContelonxt
import com.twittelonr.timelonlinelons.configapi.HasParams
import com.twittelonr.timelonlinelons.configapi.Params

caselon class ScoringUselonrRelonquelonst(
  ovelonrridelon val clielonntContelonxt: ClielonntContelonxt,
  ovelonrridelon val displayLocation: DisplayLocation,
  ovelonrridelon val params: Params,
  ovelonrridelon val delonbugOptions: Option[DelonbugOptions] = Nonelon,
  ovelonrridelon val reloncelonntFollowelondUselonrIds: Option[Selonq[Long]],
  ovelonrridelon val reloncelonntFollowelondByUselonrIds: Option[Selonq[Long]],
  ovelonrridelon val wtfImprelonssions: Option[Selonq[WtfImprelonssion]],
  ovelonrridelon val similarToUselonrIds: Selonq[Long],
  candidatelons: Selonq[CandidatelonUselonr],
  delonbugParams: Option[DelonbugParams] = Nonelon,
  isSoftUselonr: Boolelonan = falselon)
    elonxtelonnds HasClielonntContelonxt
    with HasDisplayLocation
    with HasParams
    with HasDelonbugOptions
    with HasPrelonFelontchelondFelonaturelon
    with HasSimilarToContelonxt {
  delonf toOfflinelonThrift: offlinelon.OfflinelonScoringUselonrRelonquelonst = offlinelon.OfflinelonScoringUselonrRelonquelonst(
    ClielonntContelonxtConvelonrtelonr.toFRSOfflinelonClielonntContelonxtThrift(clielonntContelonxt),
    displayLocation.toOfflinelonThrift,
    candidatelons.map(_.toOfflinelonUselonrThrift)
  )
  delonf toReloncommelonndationRelonquelonst: ReloncommelonndationRelonquelonst = ReloncommelonndationRelonquelonst(
    clielonntContelonxt = clielonntContelonxt,
    displayLocation = displayLocation,
    displayContelonxt = Nonelon,
    maxRelonsults = Nonelon,
    cursor = Nonelon,
    elonxcludelondIds = Nonelon,
    felontchPromotelondContelonnt = Nonelon,
    delonbugParams = delonbugParams,
    isSoftUselonr = isSoftUselonr
  )
}
