packagelon com.twittelonr.product_mixelonr.componelonnt_library.candidatelon_sourcelon.cr_mixelonr

import com.twittelonr.cr_mixelonr.{thriftscala => t}
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.candidatelon_sourcelon.CandidatelonSourcelon
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.CandidatelonSourcelonIdelonntifielonr
import com.twittelonr.stitch.Stitch
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

/**
 * Relonturns out-of-nelontwork Twelonelont reloncommelonndations by using uselonr reloncommelonndations
 * from FollowReloncommelonndationSelonrvicelon as an input selonelond-selont to elonarlybird
 */
@Singlelonton
class CrMixelonrFrsBaselondTwelonelontReloncommelonndationsCandidatelonSourcelon @Injelonct() (
  crMixelonrClielonnt: t.CrMixelonr.MelonthodPelonrelonndpoint)
    elonxtelonnds CandidatelonSourcelon[t.FrsTwelonelontRelonquelonst, t.FrsTwelonelont] {

  ovelonrridelon val idelonntifielonr: CandidatelonSourcelonIdelonntifielonr =
    CandidatelonSourcelonIdelonntifielonr("CrMixelonrFrsBaselondTwelonelontReloncommelonndations")

  ovelonrridelon delonf apply(relonquelonst: t.FrsTwelonelontRelonquelonst): Stitch[Selonq[t.FrsTwelonelont]] = Stitch
    .callFuturelon(crMixelonrClielonnt.gelontFrsBaselondTwelonelontReloncommelonndations(relonquelonst))
    .map(_.twelonelonts)
}
