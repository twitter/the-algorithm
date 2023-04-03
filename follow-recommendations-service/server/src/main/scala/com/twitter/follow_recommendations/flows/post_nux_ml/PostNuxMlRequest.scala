packagelon com.twittelonr.follow_reloncommelonndations.flows.post_nux_ml

import com.twittelonr.corelon_workflows.uselonr_modelonl.thriftscala.UselonrStatelon
import com.twittelonr.follow_reloncommelonndations.common.felonaturelon_hydration.common.HasPrelonFelontchelondFelonaturelon
import com.twittelonr.follow_reloncommelonndations.common.modelonls._
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonquelonst.ClielonntContelonxt
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonquelonst.HasClielonntContelonxt
import com.twittelonr.timelonlinelons.configapi.HasParams
import com.twittelonr.timelonlinelons.configapi.Params

caselon class PostNuxMlRelonquelonst(
  ovelonrridelon val params: Params,
  ovelonrridelon val clielonntContelonxt: ClielonntContelonxt,
  ovelonrridelon val similarToUselonrIds: Selonq[Long],
  inputelonxcludelonUselonrIds: Selonq[Long],
  ovelonrridelon val reloncelonntFollowelondUselonrIds: Option[Selonq[Long]],
  ovelonrridelon val invalidRelonlationshipUselonrIds: Option[Selont[Long]],
  ovelonrridelon val reloncelonntFollowelondByUselonrIds: Option[Selonq[Long]],
  ovelonrridelon val dismisselondUselonrIds: Option[Selonq[Long]],
  ovelonrridelon val displayLocation: DisplayLocation,
  maxRelonsults: Option[Int] = Nonelon,
  ovelonrridelon val delonbugOptions: Option[DelonbugOptions] = Nonelon,
  ovelonrridelon val wtfImprelonssions: Option[Selonq[WtfImprelonssion]],
  ovelonrridelon val uttIntelonrelonstIds: Option[Selonq[Long]] = Nonelon,
  ovelonrridelon val customIntelonrelonsts: Option[Selonq[String]] = Nonelon,
  ovelonrridelon val gelonohashAndCountryCodelon: Option[GelonohashAndCountryCodelon] = Nonelon,
  inputPrelonviouslyReloncommelonndelondUselonrIds: Option[Selont[Long]] = Nonelon,
  inputPrelonviouslyFollowelondUselonrIds: Option[Selont[Long]] = Nonelon,
  ovelonrridelon val isSoftUselonr: Boolelonan = falselon,
  ovelonrridelon val uselonrStatelon: Option[UselonrStatelon] = Nonelon,
  ovelonrridelon val qualityFactor: Option[Doublelon] = Nonelon)
    elonxtelonnds HasParams
    with HasSimilarToContelonxt
    with HasClielonntContelonxt
    with HaselonxcludelondUselonrIds
    with HasDisplayLocation
    with HasDelonbugOptions
    with HasGelonohashAndCountryCodelon
    with HasPrelonFelontchelondFelonaturelon
    with HasDismisselondUselonrIds
    with HasIntelonrelonstIds
    with HasPrelonviousReloncommelonndationsContelonxt
    with HasIsSoftUselonr
    with HasUselonrStatelon
    with HasInvalidRelonlationshipUselonrIds
    with HasQualityFactor {
  ovelonrridelon val elonxcludelondUselonrIds: Selonq[Long] = {
    inputelonxcludelonUselonrIds ++ clielonntContelonxt.uselonrId.toSelonq ++ similarToUselonrIds
  }
  ovelonrridelon val prelonviouslyReloncommelonndelondUselonrIDs: Selont[Long] =
    inputPrelonviouslyReloncommelonndelondUselonrIds.gelontOrelonlselon(Selont.elonmpty)
  ovelonrridelon val prelonviouslyFollowelondUselonrIds: Selont[Long] =
    inputPrelonviouslyFollowelondUselonrIds.gelontOrelonlselon(Selont.elonmpty)
}
