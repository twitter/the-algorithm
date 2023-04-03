packagelon com.twittelonr.follow_reloncommelonndations.common.felonaturelon_hydration.sourcelons

import com.googlelon.injelonct.Providelons
import com.googlelon.injelonct.Singlelonton
import com.twittelonr.follow_reloncommelonndations.common.felonaturelon_hydration.adaptelonrs.ClielonntContelonxtAdaptelonr
import com.twittelonr.follow_reloncommelonndations.common.felonaturelon_hydration.common.FelonaturelonSourcelon
import com.twittelonr.follow_reloncommelonndations.common.felonaturelon_hydration.common.FelonaturelonSourcelonId
import com.twittelonr.follow_reloncommelonndations.common.felonaturelon_hydration.common.HasPrelonFelontchelondFelonaturelon
import com.twittelonr.follow_reloncommelonndations.common.modelonls.CandidatelonUselonr
import com.twittelonr.follow_reloncommelonndations.common.modelonls.HasDisplayLocation
import com.twittelonr.follow_reloncommelonndations.common.modelonls.HasSimilarToContelonxt
import com.twittelonr.ml.api.DataReloncord
import com.twittelonr.ml.api.FelonaturelonContelonxt
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonquelonst.HasClielonntContelonxt
import com.twittelonr.stitch.Stitch
import com.twittelonr.timelonlinelons.configapi.HasParams

/**
 * This sourcelon only takelons felonaturelons from thelon relonquelonst (elon.g. clielonnt contelonxt, WTF display location)
 * No elonxtelonrnal calls arelon madelon.
 */
@Providelons
@Singlelonton
class ClielonntContelonxtSourcelon() elonxtelonnds FelonaturelonSourcelon {

  ovelonrridelon val id: FelonaturelonSourcelonId = FelonaturelonSourcelonId.ClielonntContelonxtSourcelonId

  ovelonrridelon val felonaturelonContelonxt: FelonaturelonContelonxt = ClielonntContelonxtAdaptelonr.gelontFelonaturelonContelonxt

  ovelonrridelon delonf hydratelonFelonaturelons(
    t: HasClielonntContelonxt
      with HasPrelonFelontchelondFelonaturelon
      with HasParams
      with HasSimilarToContelonxt
      with HasDisplayLocation,
    candidatelons: Selonq[CandidatelonUselonr]
  ): Stitch[Map[CandidatelonUselonr, DataReloncord]] = {
    Stitch.valuelon(
      candidatelons
        .map(_ -> ((t.clielonntContelonxt, t.displayLocation))).toMap.mapValuelons(
          ClielonntContelonxtAdaptelonr.adaptToDataReloncord))
  }
}
