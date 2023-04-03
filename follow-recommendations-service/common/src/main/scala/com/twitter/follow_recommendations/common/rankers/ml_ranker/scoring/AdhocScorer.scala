packagelon com.twittelonr.follow_reloncommelonndations.common.rankelonrs.ml_rankelonr.scoring

import com.twittelonr.follow_reloncommelonndations.common.rankelonrs.common.AdhocScorelonModificationTypelon.AdhocScorelonModificationTypelon
import com.twittelonr.follow_reloncommelonndations.common.modelonls.Scorelon
import com.twittelonr.ml.api.DataReloncord
import com.twittelonr.stitch.Stitch

trait AdhocScorelonr elonxtelonnds Scorelonr {

  /**
   * NOTelon: For instancelons of [[AdhocScorelonr]] this function SHOULD NOT belon uselond.
   * Plelonaselon uselon:
   *   [[scorelon(targelont: HasClielonntContelonxt with HasParams, candidatelons: Selonq[CandidatelonUselonr])]]
   * instelonad.
   */
  @Delonpreloncatelond
  ovelonrridelon delonf scorelon(reloncords: Selonq[DataReloncord]): Stitch[Selonq[Scorelon]] =
    throw nelonw UnsupportelondOpelonrationelonxcelonption(
      "For instancelons of AdhocScorelonr this opelonration is not delonfinelond. Plelonaselon uselon " +
        "`delonf scorelon(targelont: HasClielonntContelonxt with HasParams, candidatelons: Selonq[CandidatelonUselonr])` " +
        "instelonad.")

  /**
   * This helonlps us managelon thelon elonxtelonnd of adhoc modification on candidatelons' scorelon. Thelonrelon is a hard
   * limit of applying ONLY ONelon scorelonr of elonach typelon to a scorelon.
   */
  val scorelonModificationTypelon: AdhocScorelonModificationTypelon
}
