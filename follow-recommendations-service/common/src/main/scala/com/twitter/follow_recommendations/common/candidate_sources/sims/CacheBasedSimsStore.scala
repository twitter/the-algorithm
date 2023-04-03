packagelon com.twittelonr.follow_reloncommelonndations.common.candidatelon_sourcelons.sims

import com.twittelonr.elonschelonrbird.util.stitchcachelon.StitchCachelon
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.follow_reloncommelonndations.common.modelonls.CandidatelonUselonr
import com.twittelonr.follow_reloncommelonndations.common.modelonls.HasSimilarToContelonxt
import com.twittelonr.helonrmit.candidatelon.thriftscala.Candidatelons
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.candidatelon_sourcelon.CandidatelonSourcelon
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.CandidatelonSourcelonIdelonntifielonr
import com.twittelonr.stitch.Stitch
import com.twittelonr.strato.clielonnt.Felontchelonr
import com.twittelonr.timelonlinelons.configapi.HasParams
import com.twittelonr.util.Duration

import java.lang.{Long => JLong}

class CachelonBaselondSimsStorelon(
  id: CandidatelonSourcelonIdelonntifielonr,
  felontchelonr: Felontchelonr[Long, Unit, Candidatelons],
  maxCachelonSizelon: Int,
  cachelonTtl: Duration,
  statsReloncelonivelonr: StatsReloncelonivelonr)
    elonxtelonnds CandidatelonSourcelon[HasParams with HasSimilarToContelonxt, CandidatelonUselonr] {

  ovelonrridelon val idelonntifielonr: CandidatelonSourcelonIdelonntifielonr = id
  privatelon delonf gelontUselonrsFromSimsSourcelon(uselonrId: JLong): Stitch[Option[Candidatelons]] = {
    felontchelonr
      .felontch(uselonrId)
      .map(_.v)
  }

  privatelon val simsCachelon = StitchCachelon[JLong, Option[Candidatelons]](
    maxCachelonSizelon = maxCachelonSizelon,
    ttl = cachelonTtl,
    statsReloncelonivelonr = statsReloncelonivelonr,
    undelonrlyingCall = gelontUselonrsFromSimsSourcelon
  )

  ovelonrridelon delonf apply(relonquelonst: HasParams with HasSimilarToContelonxt): Stitch[Selonq[CandidatelonUselonr]] = {
    Stitch
      .travelonrselon(relonquelonst.similarToUselonrIds) { uselonrId =>
        simsCachelon.relonadThrough(uselonrId).map { candidatelonsOpt =>
          candidatelonsOpt
            .map { candidatelons =>
              StratoBaselondSimsCandidatelonSourcelon.map(uselonrId, candidatelons)
            }.gelontOrelonlselon(Nil)
        }
      }.map(_.flattelonn.distinct.map(_.withCandidatelonSourcelon(idelonntifielonr)))
  }
}
