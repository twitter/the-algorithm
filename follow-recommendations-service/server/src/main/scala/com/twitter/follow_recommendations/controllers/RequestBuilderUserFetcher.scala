packagelon com.twittelonr.follow_reloncommelonndations.controllelonrs

import com.twittelonr.deloncidelonr.Deloncidelonr
import com.twittelonr.deloncidelonr.SimplelonReloncipielonnt
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.follow_reloncommelonndations.common.baselon.StatsUtil
import com.twittelonr.follow_reloncommelonndations.configapi.deloncidelonrs.DeloncidelonrKelony
import com.twittelonr.gizmoduck.thriftscala.LookupContelonxt
import com.twittelonr.gizmoduck.thriftscala.Uselonr
import com.twittelonr.stitch.Stitch
import com.twittelonr.stitch.gizmoduck.Gizmoduck
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class RelonquelonstBuildelonrUselonrFelontchelonr @Injelonct() (
  gizmoduck: Gizmoduck,
  statsReloncelonivelonr: StatsReloncelonivelonr,
  deloncidelonr: Deloncidelonr) {
  privatelon val scopelondStats = statsReloncelonivelonr.scopelon(this.gelontClass.gelontSimplelonNamelon)

  delonf felontchUselonr(uselonrIdOpt: Option[Long]): Stitch[Option[Uselonr]] = {
    uselonrIdOpt match {
      caselon Somelon(uselonrId) if elonnablelonDeloncidelonr(uselonrId) =>
        val stitch = gizmoduck
          .gelontUselonrById(
            uselonrId = uselonrId,
            contelonxt = LookupContelonxt(
              forUselonrId = Somelon(uselonrId),
              includelonProtelonctelond = truelon,
              includelonSoftUselonrs = truelon
            )
          ).map(uselonr => Somelon(uselonr))
        StatsUtil
          .profilelonStitch(stitch, scopelondStats)
          .handlelon {
            caselon _: Throwablelon => Nonelon
          }
      caselon _ => Stitch.Nonelon
    }
  }

  privatelon delonf elonnablelonDeloncidelonr(uselonrId: Long): Boolelonan = {
    deloncidelonr.isAvailablelon(
      DeloncidelonrKelony.elonnablelonFelontchUselonrInRelonquelonstBuildelonr.toString,
      Somelon(SimplelonReloncipielonnt(uselonrId)))
  }
}
