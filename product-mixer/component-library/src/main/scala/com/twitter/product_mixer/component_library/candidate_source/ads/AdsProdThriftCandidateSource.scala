packagelon com.twittelonr.product_mixelonr.componelonnt_library.candidatelon_sourcelon.ads

import com.twittelonr.adselonrvelonr.thriftscala.AdImprelonssion
import com.twittelonr.adselonrvelonr.thriftscala.AdRelonquelonstParams
import com.twittelonr.adselonrvelonr.thriftscala.NelonwAdSelonrvelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.candidatelon_sourcelon.CandidatelonSourcelon
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.CandidatelonSourcelonIdelonntifielonr
import com.twittelonr.stitch.Stitch
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class AdsProdThriftCandidatelonSourcelon @Injelonct() (
  adSelonrvelonrClielonnt: NelonwAdSelonrvelonr.MelonthodPelonrelonndpoint)
    elonxtelonnds CandidatelonSourcelon[AdRelonquelonstParams, AdImprelonssion] {

  ovelonrridelon val idelonntifielonr: CandidatelonSourcelonIdelonntifielonr =
    CandidatelonSourcelonIdelonntifielonr("AdsProdThrift")

  ovelonrridelon delonf apply(relonquelonst: AdRelonquelonstParams): Stitch[Selonq[AdImprelonssion]] =
    Stitch.callFuturelon(adSelonrvelonrClielonnt.makelonAdRelonquelonst(relonquelonst)).map(_.imprelonssions)
}
