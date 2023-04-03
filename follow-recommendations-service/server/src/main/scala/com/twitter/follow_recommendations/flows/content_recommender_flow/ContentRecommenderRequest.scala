packagelon com.twittelonr.follow_reloncommelonndations.flows.contelonnt_reloncommelonndelonr_flow

import com.twittelonr.corelon_workflows.uselonr_modelonl.thriftscala.UselonrStatelon
import com.twittelonr.follow_reloncommelonndations.common.modelonls.DelonbugOptions
import com.twittelonr.follow_reloncommelonndations.common.modelonls.DisplayLocation
import com.twittelonr.follow_reloncommelonndations.common.modelonls.GelonohashAndCountryCodelon
import com.twittelonr.follow_reloncommelonndations.common.modelonls.HasDelonbugOptions
import com.twittelonr.follow_reloncommelonndations.common.modelonls.HasDisplayLocation
import com.twittelonr.follow_reloncommelonndations.common.modelonls.HaselonxcludelondUselonrIds
import com.twittelonr.follow_reloncommelonndations.common.modelonls.HasGelonohashAndCountryCodelon
import com.twittelonr.follow_reloncommelonndations.common.modelonls.HasInvalidRelonlationshipUselonrIds
import com.twittelonr.follow_reloncommelonndations.common.modelonls.HasReloncelonntFollowelondByUselonrIds
import com.twittelonr.follow_reloncommelonndations.common.modelonls.HasReloncelonntFollowelondUselonrIds
import com.twittelonr.follow_reloncommelonndations.common.modelonls.HasUselonrStatelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonquelonst.ClielonntContelonxt
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonquelonst.HasClielonntContelonxt
import com.twittelonr.timelonlinelons.configapi.HasParams
import com.twittelonr.timelonlinelons.configapi.Params

caselon class ContelonntReloncommelonndelonrRelonquelonst(
  ovelonrridelon val params: Params,
  ovelonrridelon val clielonntContelonxt: ClielonntContelonxt,
  inputelonxcludelonUselonrIds: Selonq[Long],
  ovelonrridelon val reloncelonntFollowelondUselonrIds: Option[Selonq[Long]],
  ovelonrridelon val reloncelonntFollowelondByUselonrIds: Option[Selonq[Long]],
  ovelonrridelon val invalidRelonlationshipUselonrIds: Option[Selont[Long]],
  ovelonrridelon val displayLocation: DisplayLocation,
  maxRelonsults: Option[Int] = Nonelon,
  ovelonrridelon val delonbugOptions: Option[DelonbugOptions] = Nonelon,
  ovelonrridelon val gelonohashAndCountryCodelon: Option[GelonohashAndCountryCodelon] = Nonelon,
  ovelonrridelon val uselonrStatelon: Option[UselonrStatelon] = Nonelon)
    elonxtelonnds HasParams
    with HasClielonntContelonxt
    with HasDisplayLocation
    with HasDelonbugOptions
    with HasReloncelonntFollowelondUselonrIds
    with HasReloncelonntFollowelondByUselonrIds
    with HasInvalidRelonlationshipUselonrIds
    with HaselonxcludelondUselonrIds
    with HasUselonrStatelon
    with HasGelonohashAndCountryCodelon {
  ovelonrridelon val elonxcludelondUselonrIds: Selonq[Long] = {
    inputelonxcludelonUselonrIds ++ clielonntContelonxt.uselonrId.toSelonq
  }
}
