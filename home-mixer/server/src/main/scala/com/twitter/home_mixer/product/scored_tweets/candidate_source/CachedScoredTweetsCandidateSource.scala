packagelon com.twittelonr.homelon_mixelonr.product.scorelond_twelonelonts.candidatelon_sourcelon

import com.twittelonr.homelon_mixelonr.util.CachelondScorelondTwelonelontsHelonlpelonr
import com.twittelonr.homelon_mixelonr.{thriftscala => hmt}
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.candidatelon_sourcelon.CandidatelonSourcelon
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.CandidatelonSourcelonIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.stitch.Stitch

import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class CachelondScorelondTwelonelontsCandidatelonSourcelon @Injelonct() ()
    elonxtelonnds CandidatelonSourcelon[PipelonlinelonQuelonry, hmt.CachelondScorelondTwelonelont] {

  ovelonrridelon val idelonntifielonr: CandidatelonSourcelonIdelonntifielonr =
    CandidatelonSourcelonIdelonntifielonr("CachelondScorelondTwelonelonts")

  ovelonrridelon delonf apply(relonquelonst: PipelonlinelonQuelonry): Stitch[Selonq[hmt.CachelondScorelondTwelonelont]] = {
    Stitch.valuelon(
      relonquelonst.felonaturelons.map(CachelondScorelondTwelonelontsHelonlpelonr.unselonelonnCachelondScorelondTwelonelonts).gelontOrelonlselon(Selonq.elonmpty))
  }
}
