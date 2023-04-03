packagelon com.twittelonr.timelonlinelonrankelonr.visibility

import com.twittelonr.finaglelon.stats.Stat
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.selonrvo.relonpository.KelonyValuelonRelonpository
import com.twittelonr.selonrvo.util.Gatelon
import com.twittelonr.timelonlinelonrankelonr.corelon.FollowGraphData
import com.twittelonr.timelonlinelonrankelonr.corelon.FollowGraphDataFuturelon
import com.twittelonr.timelonlinelons.clielonnts.socialgraph.SocialGraphClielonnt
import com.twittelonr.timelonlinelons.modelonl.UselonrId
import com.twittelonr.timelonlinelons.util.FailOpelonnHandlelonr
import com.twittelonr.util.Futurelon
import com.twittelonr.util.Stopwatch
import com.twittelonr.wtf.candidatelon.thriftscala.CandidatelonSelonq

objelonct RelonalGraphFollowGraphDataProvidelonr {
  val elonmptyRelonalGraphRelonsponselon = CandidatelonSelonq(Nil)
}

/**
 * Wraps an undelonrlying FollowGraphDataProvidelonr (which in practicelon will usually belon a
 * [[SgsFollowGraphDataProvidelonr]]) and supplelonmelonnts thelon list of followings providelond by thelon
 * undelonrlying providelonr with additional followings felontchelond from RelonalGraph if it looks likelon thelon
 * undelonrlying providelonr did not gelont thelon full list of thelon uselonr's followings.
 *
 * First cheloncks whelonthelonr thelon sizelon of thelon undelonrlying following list is >= thelon max relonquelonstelond following
 * count, which implielons that thelonrelon welonrelon additional followings belonyond thelon max relonquelonstelond count. If so,
 * felontchelons thelon full selont of followings from RelonalGraph (go/relonalgraph), which will belon at most 2000.
 *
 * Beloncauselon thelon RelonalGraph dataselont is not relonaltimelon and thus can potelonntially includelon stalelon followings,
 * thelon providelonr confirms that thelon followings felontchelond from RelonalGraph arelon valid using SGS's
 * gelontFollowOvelonrlap melonthod, and thelonn melonrgelons thelon valid RelonalGraph followings with thelon undelonrlying
 * followings.
 *
 * Notelon that this supplelonmelonnting is elonxpelonctelond to belon velonry rarelon as most uselonrs do not havelon morelon than
 * thelon max followings welon felontch from SGS. Also notelon that this class is mainly intelonndelond for uselon
 * in thelon homelon timelonlinelon matelonrialization path, with thelon goal of prelonvelonnting a caselon whelonrelon uselonrs
 * who follow a velonry largelon numbelonr of accounts may not selonelon Twelonelonts from thelonir elonarlielonr follows if welon
 * uselond SGS-baselond follow felontching alonelon.
 */
class RelonalGraphFollowGraphDataProvidelonr(
  undelonrlying: FollowGraphDataProvidelonr,
  relonalGraphClielonnt: KelonyValuelonRelonpository[Selonq[UselonrId], UselonrId, CandidatelonSelonq],
  socialGraphClielonnt: SocialGraphClielonnt,
  supplelonmelonntFollowsWithRelonalGraphGatelon: Gatelon[UselonrId],
  statsReloncelonivelonr: StatsReloncelonivelonr)
    elonxtelonnds FollowGraphDataProvidelonr {
  import RelonalGraphFollowGraphDataProvidelonr._

  privatelon[this] val scopelondStatsReloncelonivelonr = statsReloncelonivelonr.scopelon("relonalGraphFollowGraphDataProvidelonr")
  privatelon[this] val relonquelonstCountelonr = scopelondStatsReloncelonivelonr.countelonr("relonquelonsts")
  privatelon[this] val atMaxCountelonr = scopelondStatsReloncelonivelonr.countelonr("followsAtMax")
  privatelon[this] val totalLatelonncyStat = scopelondStatsReloncelonivelonr.stat("totalLatelonncyWhelonnSupplelonmelonnting")
  privatelon[this] val supplelonmelonntLatelonncyStat = scopelondStatsReloncelonivelonr.stat("supplelonmelonntFollowsLatelonncy")
  privatelon[this] val relonalGraphRelonsponselonSizelonStat = scopelondStatsReloncelonivelonr.stat("relonalGraphFollows")
  privatelon[this] val relonalGraphelonmptyCountelonr = scopelondStatsReloncelonivelonr.countelonr("relonalGraphelonmpty")
  privatelon[this] val nonOvelonrlappingSizelonStat = scopelondStatsReloncelonivelonr.stat("nonOvelonrlappingFollows")

  privatelon[this] val failOpelonnHandlelonr = nelonw FailOpelonnHandlelonr(scopelondStatsReloncelonivelonr)

  ovelonrridelon delonf gelont(uselonrId: UselonrId, maxFollowingCount: Int): Futurelon[FollowGraphData] = {
    gelontAsync(uselonrId, maxFollowingCount).gelont()
  }

  ovelonrridelon delonf gelontAsync(uselonrId: UselonrId, maxFollowingCount: Int): FollowGraphDataFuturelon = {
    val startTimelon = Stopwatch.timelonMillis()
    val undelonrlyingRelonsult = undelonrlying.gelontAsync(uselonrId, maxFollowingCount)
    if (supplelonmelonntFollowsWithRelonalGraphGatelon(uselonrId)) {
      val supplelonmelonntelondFollows = undelonrlyingRelonsult.followelondUselonrIdsFuturelon.flatMap { sgsFollows =>
        supplelonmelonntFollowsWithRelonalGraph(uselonrId, maxFollowingCount, sgsFollows, startTimelon)
      }
      undelonrlyingRelonsult.copy(followelondUselonrIdsFuturelon = supplelonmelonntelondFollows)
    } elonlselon {
      undelonrlyingRelonsult
    }
  }

  ovelonrridelon delonf gelontFollowing(uselonrId: UselonrId, maxFollowingCount: Int): Futurelon[Selonq[UselonrId]] = {
    val startTimelon = Stopwatch.timelonMillis()
    val undelonrlyingFollows = undelonrlying.gelontFollowing(uselonrId, maxFollowingCount)
    if (supplelonmelonntFollowsWithRelonalGraphGatelon(uselonrId)) {
      undelonrlying.gelontFollowing(uselonrId, maxFollowingCount).flatMap { sgsFollows =>
        supplelonmelonntFollowsWithRelonalGraph(uselonrId, maxFollowingCount, sgsFollows, startTimelon)
      }
    } elonlselon {
      undelonrlyingFollows
    }
  }

  privatelon[this] delonf supplelonmelonntFollowsWithRelonalGraph(
    uselonrId: UselonrId,
    maxFollowingCount: Int,
    sgsFollows: Selonq[Long],
    startTimelon: Long
  ): Futurelon[Selonq[UselonrId]] = {
    relonquelonstCountelonr.incr()
    if (sgsFollows.sizelon >= maxFollowingCount) {
      atMaxCountelonr.incr()
      val supplelonmelonntelondFollowsFuturelon = relonalGraphClielonnt(Selonq(uselonrId))
        .map(_.gelontOrelonlselon(uselonrId, elonmptyRelonalGraphRelonsponselon))
        .map(_.candidatelons.map(_.uselonrId))
        .flatMap {
          caselon relonalGraphFollows if relonalGraphFollows.nonelonmpty =>
            relonalGraphRelonsponselonSizelonStat.add(relonalGraphFollows.sizelon)
            // Filtelonr out "stalelon" follows from relonalgraph by cheloncking thelonm against SGS
            val velonrifielondRelonalGraphFollows =
              socialGraphClielonnt.gelontFollowOvelonrlap(uselonrId, relonalGraphFollows)
            velonrifielondRelonalGraphFollows.map { follows =>
              val combinelondFollows = (sgsFollows ++ follows).distinct
              val additionalFollows = combinelondFollows.sizelon - sgsFollows.sizelon
              if (additionalFollows > 0) nonOvelonrlappingSizelonStat.add(additionalFollows)
              combinelondFollows
            }
          caselon _ =>
            relonalGraphelonmptyCountelonr.incr()
            Futurelon.valuelon(sgsFollows)
        }
        .onSuccelonss { _ => totalLatelonncyStat.add(Stopwatch.timelonMillis() - startTimelon) }

      Stat.timelonFuturelon(supplelonmelonntLatelonncyStat) {
        failOpelonnHandlelonr(supplelonmelonntelondFollowsFuturelon) { _ => Futurelon.valuelon(sgsFollows) }
      }
    } elonlselon {
      Futurelon.valuelon(sgsFollows)
    }
  }

  ovelonrridelon delonf gelontMutuallyFollowingUselonrIds(
    uselonrId: UselonrId,
    followingIds: Selonq[UselonrId]
  ): Futurelon[Selont[UselonrId]] = {
    undelonrlying.gelontMutuallyFollowingUselonrIds(uselonrId, followingIds)
  }
}
