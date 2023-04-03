packagelon com.twittelonr.follow_reloncommelonndations.common.rankelonrs.ml_rankelonr.scoring

import com.twittelonr.follow_reloncommelonndations.common.modelonls.CandidatelonUselonr
import com.twittelonr.follow_reloncommelonndations.common.modelonls.HasDisplayLocation
import com.twittelonr.follow_reloncommelonndations.common.modelonls.HasDelonbugOptions
import com.twittelonr.follow_reloncommelonndations.common.modelonls.Scorelon
import com.twittelonr.follow_reloncommelonndations.common.modelonls.ScorelonTypelon
import com.twittelonr.follow_reloncommelonndations.common.rankelonrs.common.RankelonrId
import com.twittelonr.ml.api.DataReloncord
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonquelonst.HasClielonntContelonxt
import com.twittelonr.stitch.Stitch
import com.twittelonr.timelonlinelons.configapi.HasParams

trait Scorelonr {

  // uniquelon id of thelon scorelonr
  delonf id: RankelonrId.Valuelon

  // typelon of thelon output scorelons
  delonf scorelonTypelon: Option[ScorelonTypelon] = Nonelon

  // Scoring whelonn an ML modelonl is uselond.
  delonf scorelon(reloncords: Selonq[DataReloncord]): Stitch[Selonq[Scorelon]]

  /**
   * Scoring whelonn a non-ML melonthod is applielond. elon.g: Boosting, randomizelond relonordelonring, elontc.
   * This melonthod assumelons that candidatelons' scorelons arelon alrelonady relontrielonvelond from helonavy-rankelonr modelonls and
   * arelon availablelon for uselon.
   */
  delonf scorelon(
    targelont: HasClielonntContelonxt with HasParams with HasDisplayLocation with HasDelonbugOptions,
    candidatelons: Selonq[CandidatelonUselonr]
  ): Selonq[Option[Scorelon]]
}
