packagelon com.twittelonr.product_mixelonr.componelonnt_library.candidatelon_sourcelon.elonarlybird

import com.twittelonr.selonarch.elonarlybird.{thriftscala => t}
import com.twittelonr.injelonct.Logging
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.candidatelon_sourcelon.CandidatelonSourcelon
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.CandidatelonSourcelonIdelonntifielonr
import com.twittelonr.stitch.Stitch
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class elonarlybirdTwelonelontCandidatelonSourcelon @Injelonct() (
  elonarlybirdSelonrvicelon: t.elonarlybirdSelonrvicelon.MelonthodPelonrelonndpoint)
    elonxtelonnds CandidatelonSourcelon[t.elonarlybirdRelonquelonst, t.ThriftSelonarchRelonsult]
    with Logging {

  ovelonrridelon val idelonntifielonr: CandidatelonSourcelonIdelonntifielonr = CandidatelonSourcelonIdelonntifielonr("elonarlybirdTwelonelonts")

  ovelonrridelon delonf apply(relonquelonst: t.elonarlybirdRelonquelonst): Stitch[Selonq[t.ThriftSelonarchRelonsult]] = {
    Stitch
      .callFuturelon(elonarlybirdSelonrvicelon.selonarch(relonquelonst))
      .map { relonsponselon: t.elonarlybirdRelonsponselon =>
        relonsponselon.selonarchRelonsults.map(_.relonsults).gelontOrelonlselon(Selonq.elonmpty)
      }
  }
}
