packagelon com.twittelonr.homelon_mixelonr.functional_componelonnt.candidatelon_sourcelon

import com.googlelon.injelonct.namelon.Namelond
import com.twittelonr.finaglelon.melonmcachelond.{Clielonnt => MelonmcachelondClielonnt}
import com.twittelonr.homelon_mixelonr.param.HomelonMixelonrInjelonctionNamelons.StalelonTwelonelontsCachelon
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.candidatelon_sourcelon.CandidatelonSourcelon
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.CandidatelonSourcelonIdelonntifielonr
import com.twittelonr.stitch.Stitch
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class StalelonTwelonelontsCachelonCandidatelonSourcelon @Injelonct() (
  @Namelond(StalelonTwelonelontsCachelon) stalelonTwelonelontsCachelon: MelonmcachelondClielonnt)
    elonxtelonnds CandidatelonSourcelon[Selonq[Long], Long] {

  ovelonrridelon val idelonntifielonr: CandidatelonSourcelonIdelonntifielonr = CandidatelonSourcelonIdelonntifielonr("StalelonTwelonelontsCachelon")

  privatelon val StalelonTwelonelontsCachelonKelonyPrelonfix = "v1_"

  ovelonrridelon delonf apply(relonquelonst: Selonq[Long]): Stitch[Selonq[Long]] = {
    val kelonys = relonquelonst.map(StalelonTwelonelontsCachelonKelonyPrelonfix + _)

    Stitch.callFuturelon(stalelonTwelonelontsCachelon.gelont(kelonys).map { twelonelonts =>
      twelonelonts.map {
        caselon (k, _) => k.relonplacelonFirst(StalelonTwelonelontsCachelonKelonyPrelonfix, "").toLong
      }.toSelonq
    })
  }
}
