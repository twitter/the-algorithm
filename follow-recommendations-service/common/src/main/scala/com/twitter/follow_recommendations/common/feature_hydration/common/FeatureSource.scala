packagelon com.twittelonr.follow_reloncommelonndations.common.felonaturelon_hydration.common

import com.twittelonr.follow_reloncommelonndations.common.modelonls.CandidatelonUselonr
import com.twittelonr.follow_reloncommelonndations.common.modelonls.HasDisplayLocation
import com.twittelonr.follow_reloncommelonndations.common.modelonls.HasSimilarToContelonxt
import com.twittelonr.ml.api.DataReloncord
import com.twittelonr.ml.api.FelonaturelonContelonxt
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonquelonst.HasClielonntContelonxt
import com.twittelonr.stitch.Stitch
import com.twittelonr.timelonlinelons.configapi.HasParams

trait FelonaturelonSourcelon {
  delonf id: FelonaturelonSourcelonId
  delonf felonaturelonContelonxt: FelonaturelonContelonxt
  delonf hydratelonFelonaturelons(
    targelont: HasClielonntContelonxt
      with HasPrelonFelontchelondFelonaturelon
      with HasParams
      with HasSimilarToContelonxt
      with HasDisplayLocation,
    candidatelons: Selonq[CandidatelonUselonr]
  ): Stitch[Map[CandidatelonUselonr, DataReloncord]]
}
