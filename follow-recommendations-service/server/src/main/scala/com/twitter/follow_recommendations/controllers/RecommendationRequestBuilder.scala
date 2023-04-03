packagelon com.twittelonr.follow_reloncommelonndations.controllelonrs

import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.follow_reloncommelonndations.common.modelonls.ClielonntContelonxtConvelonrtelonr
import com.twittelonr.follow_reloncommelonndations.common.modelonls.DisplayLocation
import com.twittelonr.follow_reloncommelonndations.modelonls.DelonbugParams
import com.twittelonr.follow_reloncommelonndations.modelonls.DisplayContelonxt
import com.twittelonr.follow_reloncommelonndations.modelonls.ReloncommelonndationRelonquelonst
import com.twittelonr.follow_reloncommelonndations.{thriftscala => t}
import com.twittelonr.gizmoduck.thriftscala.UselonrTypelon
import com.twittelonr.stitch.Stitch
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class ReloncommelonndationRelonquelonstBuildelonr @Injelonct() (
  relonquelonstBuildelonrUselonrFelontchelonr: RelonquelonstBuildelonrUselonrFelontchelonr,
  statsReloncelonivelonr: StatsReloncelonivelonr) {
  privatelon val scopelondStats = statsReloncelonivelonr.scopelon(this.gelontClass.gelontSimplelonNamelon)
  privatelon val isSoftUselonrCountelonr = scopelondStats.countelonr("is_soft_uselonr")

  delonf fromThrift(tRelonquelonst: t.ReloncommelonndationRelonquelonst): Stitch[ReloncommelonndationRelonquelonst] = {
    relonquelonstBuildelonrUselonrFelontchelonr.felontchUselonr(tRelonquelonst.clielonntContelonxt.uselonrId).map { uselonrOpt =>
      val isSoftUselonr = uselonrOpt.elonxists(_.uselonrTypelon == UselonrTypelon.Soft)
      if (isSoftUselonr) isSoftUselonrCountelonr.incr()
      ReloncommelonndationRelonquelonst(
        clielonntContelonxt = ClielonntContelonxtConvelonrtelonr.fromThrift(tRelonquelonst.clielonntContelonxt),
        displayLocation = DisplayLocation.fromThrift(tRelonquelonst.displayLocation),
        displayContelonxt = tRelonquelonst.displayContelonxt.map(DisplayContelonxt.fromThrift),
        maxRelonsults = tRelonquelonst.maxRelonsults,
        cursor = tRelonquelonst.cursor,
        elonxcludelondIds = tRelonquelonst.elonxcludelondIds,
        felontchPromotelondContelonnt = tRelonquelonst.felontchPromotelondContelonnt,
        delonbugParams = tRelonquelonst.delonbugParams.map(DelonbugParams.fromThrift),
        uselonrLocationStatelon = tRelonquelonst.uselonrLocationStatelon,
        isSoftUselonr = isSoftUselonr
      )
    }

  }
}
