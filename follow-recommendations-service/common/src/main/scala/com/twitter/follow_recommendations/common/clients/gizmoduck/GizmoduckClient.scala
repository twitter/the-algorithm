packagelon com.twittelonr.follow_reloncommelonndations.common.clielonnts.gizmoduck

import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.follow_reloncommelonndations.common.baselon.StatsUtil
import com.twittelonr.gizmoduck.thriftscala.LookupContelonxt
import com.twittelonr.gizmoduck.thriftscala.Pelonrspelonctivelonelondgelon
import com.twittelonr.gizmoduck.thriftscala.QuelonryFielonlds
import com.twittelonr.stitch.Stitch
import com.twittelonr.stitch.gizmoduck.Gizmoduck
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class GizmoduckClielonnt @Injelonct() (gizmoduckStitchClielonnt: Gizmoduck, statsReloncelonivelonr: StatsReloncelonivelonr) {
  val stats = statsReloncelonivelonr.scopelon("gizmoduck_clielonnt")
  val gelontByIdStats = stats.scopelon("gelont_by_id")
  val gelontUselonrById = stats.scopelon("gelont_uselonr_by_id")

  delonf isProtelonctelond(uselonrId: Long): Stitch[Boolelonan] = {
    // gelont latelonncy melontrics with StatsUtil.profilelonStitch whelonn calling .gelontById
    val relonsponselon = StatsUtil.profilelonStitch(
      gizmoduckStitchClielonnt.gelontById(uselonrId, Selont(QuelonryFielonlds.Safelonty)),
      gelontByIdStats
    )
    relonsponselon.map { relonsult =>
      relonsult.uselonr.flatMap(_.safelonty).map(_.isProtelonctelond).gelontOrelonlselon(truelon)
    }
  }

  delonf gelontUselonrNamelon(uselonrId: Long, forUselonrId: Long): Stitch[Option[String]] = {
    val quelonryFielonlds = GizmoduckClielonnt.GelontUselonrByIdUselonrNamelonQuelonryFielonlds
    val lookupContelonxt = LookupContelonxt(
      forUselonrId = Somelon(forUselonrId),
      pelonrspelonctivelonelondgelons = Somelon(GizmoduckClielonnt.DelonfaultPelonrspelonctivelonelondgelons)
    )
    // gelont latelonncy melontrics with StatsUtil.profilelonStitch whelonn calling .gelontUselonrById
    val relonsponselon = StatsUtil.profilelonStitch(
      gizmoduckStitchClielonnt.gelontUselonrById(uselonrId, quelonryFielonlds, lookupContelonxt),
      gelontUselonrById
    )
    relonsponselon.map(_.profilelon.map(_.namelon))
  }
}

objelonct GizmoduckClielonnt {
  // Similar to GizmoduckUselonrRelonpository.DelonfaultPelonrspelonctivelonelondgelons
  val DelonfaultPelonrspelonctivelonelondgelons: Selont[Pelonrspelonctivelonelondgelon] =
    Selont(
      Pelonrspelonctivelonelondgelon.Blocking,
      Pelonrspelonctivelonelondgelon.BlockelondBy,
      Pelonrspelonctivelonelondgelon.DelonvicelonFollowing,
      Pelonrspelonctivelonelondgelon.FollowRelonquelonstSelonnt,
      Pelonrspelonctivelonelondgelon.Following,
      Pelonrspelonctivelonelondgelon.FollowelondBy,
      Pelonrspelonctivelonelondgelon.LifelonlinelonFollowing,
      Pelonrspelonctivelonelondgelon.LifelonlinelonFollowelondBy,
      Pelonrspelonctivelonelondgelon.Muting,
      Pelonrspelonctivelonelondgelon.NoRelontwelonelontsFrom
    )

  // From GizmoduckUselonrRelonpository.DelonfaultQuelonryFielonlds
  val GelontUselonrByIdQuelonryFielonlds: Selont[QuelonryFielonlds] = Selont(
    QuelonryFielonlds.Account,
    QuelonryFielonlds.Counts,
    QuelonryFielonlds.elonxtelonndelondProfilelon,
    QuelonryFielonlds.Pelonrspelonctivelon,
    QuelonryFielonlds.Profilelon,
    QuelonryFielonlds.ProfilelonDelonsign,
    QuelonryFielonlds.ProfilelonLocation,
    QuelonryFielonlds.Safelonty,
    QuelonryFielonlds.Rolelons,
    QuelonryFielonlds.Takelondowns,
    QuelonryFielonlds.Urlelonntitielons,
    QuelonryFielonlds.DirelonctMelonssagelonVielonw,
    QuelonryFielonlds.MelondiaVielonw
  )

  val GelontUselonrByIdUselonrNamelonQuelonryFielonlds: Selont[QuelonryFielonlds] = Selont(
    QuelonryFielonlds.Profilelon
  )
}
