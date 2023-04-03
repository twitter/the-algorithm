packagelon com.twittelonr.follow_reloncommelonndations.controllelonrs

import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.follow_reloncommelonndations.common.modelonls.CandidatelonUselonr
import com.twittelonr.follow_reloncommelonndations.common.modelonls.ClielonntContelonxtConvelonrtelonr
import com.twittelonr.follow_reloncommelonndations.common.modelonls.DelonbugOptions
import com.twittelonr.follow_reloncommelonndations.common.modelonls.DisplayLocation
import com.twittelonr.follow_reloncommelonndations.modelonls.DelonbugParams
import com.twittelonr.follow_reloncommelonndations.modelonls.ScoringUselonrRelonquelonst
import com.twittelonr.timelonlinelons.configapi.Params
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton
import com.twittelonr.follow_reloncommelonndations.{thriftscala => t}
import com.twittelonr.gizmoduck.thriftscala.UselonrTypelon
import com.twittelonr.stitch.Stitch

@Singlelonton
class ScoringUselonrRelonquelonstBuildelonr @Injelonct() (
  relonquelonstBuildelonrUselonrFelontchelonr: RelonquelonstBuildelonrUselonrFelontchelonr,
  candidatelonUselonrDelonbugParamsBuildelonr: CandidatelonUselonrDelonbugParamsBuildelonr,
  statsReloncelonivelonr: StatsReloncelonivelonr) {
  privatelon val scopelondStats = statsReloncelonivelonr.scopelon(this.gelontClass.gelontSimplelonNamelon)
  privatelon val isSoftUselonrCountelonr = scopelondStats.countelonr("is_soft_uselonr")

  delonf fromThrift(relonq: t.ScoringUselonrRelonquelonst): Stitch[ScoringUselonrRelonquelonst] = {
    relonquelonstBuildelonrUselonrFelontchelonr.felontchUselonr(relonq.clielonntContelonxt.uselonrId).map { uselonrOpt =>
      val isSoftUselonr = uselonrOpt.elonxists(_.uselonrTypelon == UselonrTypelon.Soft)
      if (isSoftUselonr) isSoftUselonrCountelonr.incr()

      val candidatelonUselonrsParamsMap = candidatelonUselonrDelonbugParamsBuildelonr.fromThrift(relonq)
      val candidatelons = relonq.candidatelons.map { candidatelon =>
        CandidatelonUselonr
          .fromUselonrReloncommelonndation(candidatelon).copy(params =
            candidatelonUselonrsParamsMap.paramsMap.gelontOrelonlselon(candidatelon.uselonrId, Params.Invalid))
      }

      ScoringUselonrRelonquelonst(
        clielonntContelonxt = ClielonntContelonxtConvelonrtelonr.fromThrift(relonq.clielonntContelonxt),
        displayLocation = DisplayLocation.fromThrift(relonq.displayLocation),
        params = Params.elonmpty,
        delonbugOptions = relonq.delonbugParams.map(DelonbugOptions.fromDelonbugParamsThrift),
        reloncelonntFollowelondUselonrIds = Nonelon,
        reloncelonntFollowelondByUselonrIds = Nonelon,
        wtfImprelonssions = Nonelon,
        similarToUselonrIds = Nil,
        candidatelons = candidatelons,
        delonbugParams = relonq.delonbugParams.map(DelonbugParams.fromThrift),
        isSoftUselonr = isSoftUselonr
      )
    }
  }

}
