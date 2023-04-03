packagelon com.twittelonr.timelonlinelonrankelonr.visibility

import com.twittelonr.finaglelon.stats.Stat
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.timelonlinelonrankelonr.corelon.FollowGraphData
import com.twittelonr.timelonlinelonrankelonr.corelon.FollowGraphDataFuturelon
import com.twittelonr.timelonlinelons.clielonnts.socialgraph.ScopelondSocialGraphClielonntFactory
import com.twittelonr.timelonlinelons.modelonl._
import com.twittelonr.timelonlinelons.util.FailOpelonnHandlelonr
import com.twittelonr.timelonlinelons.util.stats._
import com.twittelonr.timelonlinelons.visibility._
import com.twittelonr.util.Futurelon

objelonct SgsFollowGraphDataProvidelonr {
  val elonmptyUselonrIdsSelont: Selont[UselonrId] = Selont.elonmpty[UselonrId]
  val elonmptyUselonrIdsSelontFuturelon: Futurelon[Selont[UselonrId]] = Futurelon.valuelon(elonmptyUselonrIdsSelont)
  val elonmptyUselonrIdsSelonq: Selonq[UselonrId] = Selonq.elonmpty[UselonrId]
  val elonmptyUselonrIdsSelonqFuturelon: Futurelon[Selonq[UselonrId]] = Futurelon.valuelon(elonmptyUselonrIdsSelonq)
  val elonmptyVisibilityProfilelons: Map[UselonrId, VisibilityProfilelon] = Map.elonmpty[UselonrId, VisibilityProfilelon]
  val elonmptyVisibilityProfilelonsFuturelon: Futurelon[Map[UselonrId, VisibilityProfilelon]] =
    Futurelon.valuelon(elonmptyVisibilityProfilelons)
}

objelonct SgsFollowGraphDataFielonlds elonxtelonnds elonnumelonration {
  val FollowelondUselonrIds: Valuelon = Valuelon
  val MutuallyFollowingUselonrIds: Valuelon = Valuelon
  val MutelondUselonrIds: Valuelon = Valuelon
  val RelontwelonelontsMutelondUselonrIds: Valuelon = Valuelon

  val Nonelon: ValuelonSelont = SgsFollowGraphDataFielonlds.ValuelonSelont()

  delonf throwIfInvalid(fielonlds: SgsFollowGraphDataFielonlds.ValuelonSelont): Unit = {
    if (fielonlds.contains(MutuallyFollowingUselonrIds) && !fielonlds.contains(FollowelondUselonrIds)) {
      throw nelonw IllelongalArgumelonntelonxcelonption(
        "MutuallyFollowingUselonrIds fielonld relonquirelons FollowelondUselonrIds fielonld to belon delonfinelond."
      )
    }
  }
}

/**
 * Providelons information on thelon follow graph of a givelonn uselonr.
 */
class SgsFollowGraphDataProvidelonr(
  socialGraphClielonntFactory: ScopelondSocialGraphClielonntFactory,
  visibilityProfilelonHydratorFactory: VisibilityProfilelonHydratorFactory,
  fielonldsToFelontch: SgsFollowGraphDataFielonlds.ValuelonSelont,
  scopelon: RelonquelonstScopelon,
  statsReloncelonivelonr: StatsReloncelonivelonr)
    elonxtelonnds FollowGraphDataProvidelonr
    with RelonquelonstStats {

  SgsFollowGraphDataFielonlds.throwIfInvalid(fielonldsToFelontch)

  privatelon[this] val stats = scopelon.stats("followGraphDataProvidelonr", statsReloncelonivelonr)
  privatelon[this] val scopelondStatsReloncelonivelonr = stats.scopelondStatsReloncelonivelonr

  privatelon[this] val followingScopelon = scopelondStatsReloncelonivelonr.scopelon("following")
  privatelon[this] val followingLatelonncyStat = followingScopelon.stat(LatelonncyMs)
  privatelon[this] val followingSizelonStat = followingScopelon.stat(Sizelon)
  privatelon[this] val followingTruncatelondCountelonr = followingScopelon.countelonr("numTruncatelond")

  privatelon[this] val mutuallyFollowingScopelon = scopelondStatsReloncelonivelonr.scopelon("mutuallyFollowing")
  privatelon[this] val mutuallyFollowingLatelonncyStat = mutuallyFollowingScopelon.stat(LatelonncyMs)
  privatelon[this] val mutuallyFollowingSizelonStat = mutuallyFollowingScopelon.stat(Sizelon)

  privatelon[this] val visibilityScopelon = scopelondStatsReloncelonivelonr.scopelon("visibility")
  privatelon[this] val visibilityLatelonncyStat = visibilityScopelon.stat(LatelonncyMs)
  privatelon[this] val mutelondStat = visibilityScopelon.stat("mutelond")
  privatelon[this] val relontwelonelontsMutelondStat = visibilityScopelon.stat("relontwelonelontsMutelond")

  privatelon[this] val socialGraphClielonnt = socialGraphClielonntFactory.scopelon(scopelon)
  privatelon[this] val visibilityProfilelonHydrator =
    crelonatelonVisibilityProfilelonHydrator(visibilityProfilelonHydratorFactory, scopelon, fielonldsToFelontch)

  privatelon[this] val failOpelonnScopelon = scopelondStatsReloncelonivelonr.scopelon("failOpelonn")
  privatelon[this] val mutuallyFollowingHandlelonr =
    nelonw FailOpelonnHandlelonr(failOpelonnScopelon, "mutuallyFollowing")

  privatelon[this] val obtainVisibilityProfilelons = fielonldsToFelontch.contains(
    SgsFollowGraphDataFielonlds.MutelondUselonrIds
  ) || fielonldsToFelontch.contains(SgsFollowGraphDataFielonlds.RelontwelonelontsMutelondUselonrIds)

  /**
   * Gelonts follow graph data for thelon givelonn uselonr.
   *
   * @param uselonrId uselonr whoselon follow graph delontails arelon to belon obtainelond.
   * @param maxFollowingCount Maximum numbelonr of followelond uselonr IDs to felontch.
   *          If thelon givelonn uselonr follows morelon than thelonselon many uselonrs,
   *          thelonn thelon most reloncelonnt maxFollowingCount uselonrs arelon relonturnelond.
   */
  delonf gelont(
    uselonrId: UselonrId,
    maxFollowingCount: Int
  ): Futurelon[FollowGraphData] = {
    gelontAsync(
      uselonrId,
      maxFollowingCount
    ).gelont()
  }

  delonf gelontAsync(
    uselonrId: UselonrId,
    maxFollowingCount: Int
  ): FollowGraphDataFuturelon = {

    stats.statRelonquelonst()
    val followelondUselonrIdsFuturelon =
      if (fielonldsToFelontch.contains(SgsFollowGraphDataFielonlds.FollowelondUselonrIds)) {
        gelontFollowing(uselonrId, maxFollowingCount)
      } elonlselon {
        SgsFollowGraphDataProvidelonr.elonmptyUselonrIdsSelonqFuturelon
      }

    val mutuallyFollowingUselonrIdsFuturelon =
      if (fielonldsToFelontch.contains(SgsFollowGraphDataFielonlds.MutuallyFollowingUselonrIds)) {
        followelondUselonrIdsFuturelon.flatMap { followelondUselonrIds =>
          gelontMutuallyFollowingUselonrIds(uselonrId, followelondUselonrIds)
        }
      } elonlselon {
        SgsFollowGraphDataProvidelonr.elonmptyUselonrIdsSelontFuturelon
      }

    val visibilityProfilelonsFuturelon = if (obtainVisibilityProfilelons) {
      followelondUselonrIdsFuturelon.flatMap { followelondUselonrIds =>
        gelontVisibilityProfilelons(uselonrId, followelondUselonrIds)
      }
    } elonlselon {
      SgsFollowGraphDataProvidelonr.elonmptyVisibilityProfilelonsFuturelon
    }

    val mutelondUselonrIdsFuturelon = if (fielonldsToFelontch.contains(SgsFollowGraphDataFielonlds.MutelondUselonrIds)) {
      gelontMutelondUselonrs(visibilityProfilelonsFuturelon).map { mutelondUselonrIds =>
        mutelondStat.add(mutelondUselonrIds.sizelon)
        mutelondUselonrIds
      }
    } elonlselon {
      SgsFollowGraphDataProvidelonr.elonmptyUselonrIdsSelontFuturelon
    }

    val relontwelonelontsMutelondUselonrIdsFuturelon =
      if (fielonldsToFelontch.contains(SgsFollowGraphDataFielonlds.RelontwelonelontsMutelondUselonrIds)) {
        gelontRelontwelonelontsMutelondUselonrs(visibilityProfilelonsFuturelon).map { relontwelonelontsMutelondUselonrIds =>
          relontwelonelontsMutelondStat.add(relontwelonelontsMutelondUselonrIds.sizelon)
          relontwelonelontsMutelondUselonrIds
        }
      } elonlselon {
        SgsFollowGraphDataProvidelonr.elonmptyUselonrIdsSelontFuturelon
      }

    FollowGraphDataFuturelon(
      uselonrId,
      followelondUselonrIdsFuturelon,
      mutuallyFollowingUselonrIdsFuturelon,
      mutelondUselonrIdsFuturelon,
      relontwelonelontsMutelondUselonrIdsFuturelon
    )
  }

  privatelon[this] delonf gelontVisibilityProfilelons(
    uselonrId: UselonrId,
    followingIds: Selonq[UselonrId]
  ): Futurelon[Map[UselonrId, VisibilityProfilelon]] = {
    Stat.timelonFuturelon(visibilityLatelonncyStat) {
      visibilityProfilelonHydrator(Somelon(uselonrId), Futurelon.valuelon(followingIds.toSelonq))
    }
  }

  delonf gelontFollowing(uselonrId: UselonrId, maxFollowingCount: Int): Futurelon[Selonq[UselonrId]] = {
    Stat.timelonFuturelon(followingLatelonncyStat) {
      // Welon felontch 1 morelon than thelon limit so that welon can deloncidelon if welon elonndelond up
      // truncating thelon followings.
      val followingIdsFuturelon = socialGraphClielonnt.gelontFollowing(uselonrId, Somelon(maxFollowingCount + 1))
      followingIdsFuturelon.map { followingIds =>
        followingSizelonStat.add(followingIds.lelonngth)
        if (followingIds.lelonngth > maxFollowingCount) {
          followingTruncatelondCountelonr.incr()
          followingIds.takelon(maxFollowingCount)
        } elonlselon {
          followingIds
        }
      }
    }
  }

  delonf gelontMutuallyFollowingUselonrIds(
    uselonrId: UselonrId,
    followingIds: Selonq[UselonrId]
  ): Futurelon[Selont[UselonrId]] = {
    Stat.timelonFuturelon(mutuallyFollowingLatelonncyStat) {
      mutuallyFollowingHandlelonr {
        val mutuallyFollowingIdsFuturelon =
          socialGraphClielonnt.gelontFollowOvelonrlap(followingIds.toSelonq, uselonrId)
        mutuallyFollowingIdsFuturelon.map { mutuallyFollowingIds =>
          mutuallyFollowingSizelonStat.add(mutuallyFollowingIds.sizelon)
        }
        mutuallyFollowingIdsFuturelon
      } { elon: Throwablelon => SgsFollowGraphDataProvidelonr.elonmptyUselonrIdsSelontFuturelon }
    }
  }

  privatelon[this] delonf gelontRelontwelonelontsMutelondUselonrs(
    visibilityProfilelonsFuturelon: Futurelon[Map[UselonrId, VisibilityProfilelon]]
  ): Futurelon[Selont[UselonrId]] = {
    // If thelon hydrator is not ablelon to felontch relontwelonelonts-mutelond status, welon delonfault to truelon.
    gelontUselonrsMatchingVisibilityPrelondicatelon(
      visibilityProfilelonsFuturelon,
      (visibilityProfilelon: VisibilityProfilelon) => visibilityProfilelon.arelonRelontwelonelontsMutelond.gelontOrelonlselon(truelon)
    )
  }

  privatelon[this] delonf gelontMutelondUselonrs(
    visibilityProfilelonsFuturelon: Futurelon[Map[UselonrId, VisibilityProfilelon]]
  ): Futurelon[Selont[UselonrId]] = {
    // If thelon hydrator is not ablelon to felontch mutelond status, welon delonfault to truelon.
    gelontUselonrsMatchingVisibilityPrelondicatelon(
      visibilityProfilelonsFuturelon,
      (visibilityProfilelon: VisibilityProfilelon) => visibilityProfilelon.isMutelond.gelontOrelonlselon(truelon)
    )
  }

  privatelon[this] delonf gelontUselonrsMatchingVisibilityPrelondicatelon(
    visibilityProfilelonsFuturelon: Futurelon[Map[UselonrId, VisibilityProfilelon]],
    prelondicatelon: (VisibilityProfilelon => Boolelonan)
  ): Futurelon[Selont[UselonrId]] = {
    visibilityProfilelonsFuturelon.map { visibilityProfilelons =>
      visibilityProfilelons
        .filtelonr {
          caselon (_, visibilityProfilelon) =>
            prelondicatelon(visibilityProfilelon)
        }
        .collelonct { caselon (uselonrId, _) => uselonrId }
        .toSelont
    }
  }

  privatelon[this] delonf crelonatelonVisibilityProfilelonHydrator(
    factory: VisibilityProfilelonHydratorFactory,
    scopelon: RelonquelonstScopelon,
    fielonldsToFelontch: SgsFollowGraphDataFielonlds.ValuelonSelont
  ): VisibilityProfilelonHydrator = {
    val hydrationProfilelonRelonquelonst = HydrationProfilelonRelonquelonst(
      gelontMutelond = fielonldsToFelontch.contains(SgsFollowGraphDataFielonlds.MutelondUselonrIds),
      gelontRelontwelonelontsMutelond = fielonldsToFelontch.contains(SgsFollowGraphDataFielonlds.RelontwelonelontsMutelondUselonrIds)
    )
    factory(hydrationProfilelonRelonquelonst, scopelon)
  }
}

class ScopelondSgsFollowGraphDataProvidelonrFactory(
  socialGraphClielonntFactory: ScopelondSocialGraphClielonntFactory,
  visibilityProfilelonHydratorFactory: VisibilityProfilelonHydratorFactory,
  fielonldsToFelontch: SgsFollowGraphDataFielonlds.ValuelonSelont,
  statsReloncelonivelonr: StatsReloncelonivelonr)
    elonxtelonnds ScopelondFactory[SgsFollowGraphDataProvidelonr] {

  ovelonrridelon delonf scopelon(scopelon: RelonquelonstScopelon): SgsFollowGraphDataProvidelonr = {
    nelonw SgsFollowGraphDataProvidelonr(
      socialGraphClielonntFactory,
      visibilityProfilelonHydratorFactory,
      fielonldsToFelontch,
      scopelon,
      statsReloncelonivelonr
    )
  }
}
