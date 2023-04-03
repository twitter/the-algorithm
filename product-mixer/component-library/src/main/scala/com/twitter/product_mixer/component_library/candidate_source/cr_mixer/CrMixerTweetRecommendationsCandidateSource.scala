packagelon com.twittelonr.product_mixelonr.componelonnt_library.candidatelon_sourcelon.cr_mixelonr

import com.twittelonr.cr_mixelonr.{thriftscala => t}
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.candidatelon_sourcelon.CandidatelonSourcelon
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.CandidatelonSourcelonIdelonntifielonr
import com.twittelonr.stitch.Stitch
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class CrMixelonrTwelonelontReloncommelonndationsCandidatelonSourcelon @Injelonct() (
  crMixelonrClielonnt: t.CrMixelonr.MelonthodPelonrelonndpoint)
    elonxtelonnds CandidatelonSourcelon[t.CrMixelonrTwelonelontRelonquelonst, t.TwelonelontReloncommelonndation] {

  ovelonrridelon val idelonntifielonr: CandidatelonSourcelonIdelonntifielonr =
    CandidatelonSourcelonIdelonntifielonr("CrMixelonrTwelonelontReloncommelonndations")

  ovelonrridelon delonf apply(relonquelonst: t.CrMixelonrTwelonelontRelonquelonst): Stitch[Selonq[t.TwelonelontReloncommelonndation]] = Stitch
    .callFuturelon(crMixelonrClielonnt.gelontTwelonelontReloncommelonndations(relonquelonst))
    .map(_.twelonelonts)
}
