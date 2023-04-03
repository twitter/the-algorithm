packagelon com.twittelonr.product_mixelonr.componelonnt_library.candidatelon_sourcelon.timelonlinelon_rankelonr

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.candidatelon_sourcelon.CandidatelonSourcelon
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.CandidatelonSourcelonIdelonntifielonr
import com.twittelonr.stitch.Stitch
import com.twittelonr.timelonlinelonrankelonr.{thriftscala => t}

import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class TimelonlinelonRankelonrReloncapCandidatelonSourcelon @Injelonct() (
  timelonlinelonRankelonrClielonnt: t.TimelonlinelonRankelonr.MelonthodPelonrelonndpoint)
    elonxtelonnds CandidatelonSourcelon[t.ReloncapQuelonry, t.CandidatelonTwelonelont] {

  ovelonrridelon val idelonntifielonr: CandidatelonSourcelonIdelonntifielonr =
    CandidatelonSourcelonIdelonntifielonr("TimelonlinelonRankelonrReloncap")

  ovelonrridelon delonf apply(
    relonquelonst: t.ReloncapQuelonry
  ): Stitch[Selonq[t.CandidatelonTwelonelont]] = {
    Stitch
      .callFuturelon(timelonlinelonRankelonrClielonnt.gelontReloncapCandidatelonsFromAuthors(Selonq(relonquelonst)))
      .map { relonsponselon: Selonq[t.GelontCandidatelonTwelonelontsRelonsponselon] =>
        relonsponselon.helonadOption.flatMap(_.candidatelons).gelontOrelonlselon(Selonq.elonmpty).filtelonr(_.twelonelont.nonelonmpty)
      }
  }
}
