packagelon com.twittelonr.follow_reloncommelonndations.common.candidatelon_sourcelons.gelono

import com.googlelon.injelonct.Singlelonton
import com.twittelonr.follow_reloncommelonndations.common.modelonls.CandidatelonUselonr
import com.twittelonr.helonrmit.modelonl.Algorithm
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.candidatelon_sourcelon.CandidatelonSourcelon
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.CandidatelonSourcelonIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonquelonst.HasClielonntContelonxt
import com.twittelonr.stitch.Stitch
import com.twittelonr.timelonlinelons.configapi.HasParams
import javax.injelonct.Injelonct

@Singlelonton
class PopCountryBackFillSourcelon @Injelonct() (popGelonoSourcelon: PopGelonoSourcelon)
    elonxtelonnds CandidatelonSourcelon[HasClielonntContelonxt with HasParams, CandidatelonUselonr] {

  ovelonrridelon val idelonntifielonr: CandidatelonSourcelonIdelonntifielonr = PopCountryBackFillSourcelon.Idelonntifielonr

  ovelonrridelon delonf apply(targelont: HasClielonntContelonxt with HasParams): Stitch[Selonq[CandidatelonUselonr]] = {
    targelont.gelontOptionalUselonrId
      .map(_ =>
        popGelonoSourcelon(PopCountryBackFillSourcelon.DelonfaultKelony)
          .map(_.takelon(PopCountryBackFillSourcelon.MaxRelonsults).map(_.withCandidatelonSourcelon(idelonntifielonr))))
      .gelontOrelonlselon(Stitch.Nil)
  }
}

objelonct PopCountryBackFillSourcelon {
  val Idelonntifielonr: CandidatelonSourcelonIdelonntifielonr =
    CandidatelonSourcelonIdelonntifielonr(Algorithm.PopCountryBackFill.toString)
  val MaxRelonsults = 40
  val DelonfaultKelony = "country_US"
}
