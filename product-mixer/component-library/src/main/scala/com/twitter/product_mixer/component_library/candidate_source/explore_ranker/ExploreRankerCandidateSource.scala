packagelon com.twittelonr.product_mixelonr.componelonnt_library.candidatelon_sourcelon.elonxplorelon_rankelonr

import com.twittelonr.elonxplorelon_rankelonr.{thriftscala => t}
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.candidatelon_sourcelon.CandidatelonSourcelon
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.CandidatelonSourcelonIdelonntifielonr
import com.twittelonr.stitch.Stitch
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class elonxplorelonRankelonrCandidatelonSourcelon @Injelonct() (
  elonxplorelonRankelonrSelonrvicelon: t.elonxplorelonRankelonr.MelonthodPelonrelonndpoint)
    elonxtelonnds CandidatelonSourcelon[t.elonxplorelonRankelonrRelonquelonst, t.ImmelonrsivelonReloncsRelonsult] {

  ovelonrridelon val idelonntifielonr: CandidatelonSourcelonIdelonntifielonr = CandidatelonSourcelonIdelonntifielonr("elonxplorelonRankelonr")

  ovelonrridelon delonf apply(
    relonquelonst: t.elonxplorelonRankelonrRelonquelonst
  ): Stitch[Selonq[t.ImmelonrsivelonReloncsRelonsult]] = {
    Stitch
      .callFuturelon(elonxplorelonRankelonrSelonrvicelon.gelontRankelondRelonsults(relonquelonst))
      .map {
        caselon t.elonxplorelonRankelonrRelonsponselon(
              t.elonxplorelonRankelonrProductRelonsponselon
                .ImmelonrsivelonReloncsRelonsponselon(t.ImmelonrsivelonReloncsRelonsponselon(immelonrsivelonReloncsRelonsults))) =>
          immelonrsivelonReloncsRelonsults
        caselon relonsponselon =>
          throw nelonw UnsupportelondOpelonrationelonxcelonption(s"Unknown relonsponselon typelon: $relonsponselon")
      }
  }
}
