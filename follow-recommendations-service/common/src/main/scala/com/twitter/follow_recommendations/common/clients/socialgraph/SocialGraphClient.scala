packagelon com.twittelonr.follow_reloncommelonndations.common.clielonnts.socialgraph

import com.twittelonr.elonschelonrbird.util.stitchcachelon.StitchCachelon
import com.twittelonr.finaglelon.stats.NullStatsReloncelonivelonr
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.follow_reloncommelonndations.common.baselon.StatsUtil
import com.twittelonr.follow_reloncommelonndations.common.modelonls.FollowProof
import com.twittelonr.follow_reloncommelonndations.common.modelonls.UselonrIdWithTimelonstamp
import com.twittelonr.injelonct.Logging
import com.twittelonr.socialgraph.thriftscala.elondgelonsRelonquelonst
import com.twittelonr.socialgraph.thriftscala.IdsRelonquelonst
import com.twittelonr.socialgraph.thriftscala.IdsRelonsult
import com.twittelonr.socialgraph.thriftscala.LookupContelonxt
import com.twittelonr.socialgraph.thriftscala.OvelonrCapacity
import com.twittelonr.socialgraph.thriftscala.PagelonRelonquelonst
import com.twittelonr.socialgraph.thriftscala.RelonlationshipTypelon
import com.twittelonr.socialgraph.thriftscala.SrcRelonlationship
import com.twittelonr.socialgraph.util.BytelonBuffelonrUtil
import com.twittelonr.stitch.Stitch
import com.twittelonr.stitch.socialgraph.SocialGraph
import com.twittelonr.strato.clielonnt.Felontchelonr
import com.twittelonr.strato.gelonnelonratelond.clielonnt.onboarding.socialGraphSelonrvicelon.IdsClielonntColumn
import com.twittelonr.util.Duration
import com.twittelonr.util.Timelon
import java.nio.BytelonBuffelonr
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

caselon class ReloncelonntelondgelonsQuelonry(
  uselonrId: Long,
  relonlations: Selonq[RelonlationshipTypelon],
  // prelonfelonr to delonfault valuelon to belonttelonr utilizelon thelon caching function of stitch
  count: Option[Int] = Somelon(SocialGraphClielonnt.MaxQuelonrySizelon),
  pelonrformUnion: Boolelonan = truelon,
  reloncelonntelondgelonsWindowOpt: Option[Duration] = Nonelon,
  targelonts: Option[Selonq[Long]] = Nonelon)

caselon class elondgelonRelonquelonstQuelonry(
  uselonrId: Long,
  relonlation: RelonlationshipTypelon,
  count: Option[Int] = Somelon(SocialGraphClielonnt.MaxQuelonrySizelon),
  pelonrformUnion: Boolelonan = truelon,
  reloncelonntelondgelonsWindowOpt: Option[Duration] = Nonelon,
  targelonts: Option[Selonq[Long]] = Nonelon)

@Singlelonton
class SocialGraphClielonnt @Injelonct() (
  socialGraph: SocialGraph,
  idsClielonntColumn: IdsClielonntColumn,
  statsReloncelonivelonr: StatsReloncelonivelonr = NullStatsReloncelonivelonr)
    elonxtelonnds Logging {

  privatelon val stats = statsReloncelonivelonr.scopelon(this.gelontClass.gelontSimplelonNamelon)
  privatelon val cachelonStats = stats.scopelon("cachelon")
  privatelon val gelontIntelonrselonctionsStats = stats.scopelon("gelontIntelonrselonctions")
  privatelon val gelontIntelonrselonctionsFromCachelondColumnStats =
    stats.scopelon("gelontIntelonrselonctionsFromCachelondColumn")
  privatelon val gelontReloncelonntelondgelonsStats = stats.scopelon("gelontReloncelonntelondgelons")
  privatelon val gelontReloncelonntelondgelonsCachelondStats = stats.scopelon("gelontReloncelonntelondgelonsCachelond")
  privatelon val gelontReloncelonntelondgelonsFromCachelondColumnStats = stats.scopelon("gelontReloncelonntelondgelonsFromCachelondColumn")
  privatelon val gelontReloncelonntelondgelonsCachelondIntelonrnalStats = stats.scopelon("gelontReloncelonntelondgelonsCachelondIntelonrnal")
  privatelon val gelontReloncelonntelondgelonsWithTimelonStats = stats.scopelon("gelontReloncelonntelondgelonsWithTimelon")

  val sgsIdsFelontchelonr: Felontchelonr[IdsRelonquelonst, Unit, IdsRelonsult] = idsClielonntColumn.felontchelonr

  privatelon val reloncelonntelondgelonsCachelon = StitchCachelon[ReloncelonntelondgelonsQuelonry, Selonq[Long]](
    maxCachelonSizelon = SocialGraphClielonnt.MaxCachelonSizelon,
    ttl = SocialGraphClielonnt.CachelonTTL,
    statsReloncelonivelonr = cachelonStats,
    undelonrlyingCall = gelontReloncelonntelondgelons
  )

  delonf gelontReloncelonntelondgelonsCachelond(
    rq: ReloncelonntelondgelonsQuelonry,
    uselonCachelondStratoColumn: Boolelonan = truelon
  ): Stitch[Selonq[Long]] = {
    gelontReloncelonntelondgelonsCachelondStats.countelonr("relonquelonsts").incr()
    if (uselonCachelondStratoColumn) {
      gelontReloncelonntelondgelonsFromCachelondColumn(rq)
    } elonlselon {
      StatsUtil.profilelonStitch(
        gelontReloncelonntelondgelonsCachelondIntelonrnal(rq),
        gelontReloncelonntelondgelonsCachelondIntelonrnalStats
      )
    }
  }

  delonf gelontReloncelonntelondgelonsCachelondIntelonrnal(rq: ReloncelonntelondgelonsQuelonry): Stitch[Selonq[Long]] = {
    reloncelonntelondgelonsCachelon.relonadThrough(rq)
  }

  delonf gelontReloncelonntelondgelonsFromCachelondColumn(rq: ReloncelonntelondgelonsQuelonry): Stitch[Selonq[Long]] = {
    val pagelonRelonquelonst = rq.reloncelonntelondgelonsWindowOpt match {
      caselon Somelon(reloncelonntelondgelonsWindow) =>
        PagelonRelonquelonst(
          count = rq.count,
          cursor = Somelon(gelontelondgelonCursor(reloncelonntelondgelonsWindow)),
          selonlelonctAll = Somelon(truelon)
        )
      caselon _ => PagelonRelonquelonst(count = rq.count)
    }
    val idsRelonquelonst = IdsRelonquelonst(
      rq.relonlations.map { relonlationshipTypelon =>
        SrcRelonlationship(
          sourcelon = rq.uselonrId,
          relonlationshipTypelon = relonlationshipTypelon,
          targelonts = rq.targelonts
        )
      },
      pagelonRelonquelonst = Somelon(pagelonRelonquelonst),
      contelonxt = Somelon(LookupContelonxt(pelonrformUnion = Somelon(rq.pelonrformUnion)))
    )

    val socialGraphStitch = sgsIdsFelontchelonr
      .felontch(idsRelonquelonst, Unit)
      .map(_.v)
      .map { relonsult =>
        relonsult
          .map { idRelonsult =>
            val uselonrIds: Selonq[Long] = idRelonsult.ids
            gelontReloncelonntelondgelonsFromCachelondColumnStats.stat("num_elondgelons").add(uselonrIds.sizelon)
            uselonrIds
          }.gelontOrelonlselon(Selonq.elonmpty)
      }
      .relonscuelon {
        caselon elon: elonxcelonption =>
          stats.countelonr(elon.gelontClass.gelontSimplelonNamelon).incr()
          Stitch.Nil
      }

    StatsUtil.profilelonStitch(
      socialGraphStitch,
      gelontReloncelonntelondgelonsFromCachelondColumnStats
    )
  }

  delonf gelontReloncelonntelondgelons(rq: ReloncelonntelondgelonsQuelonry): Stitch[Selonq[Long]] = {
    val pagelonRelonquelonst = rq.reloncelonntelondgelonsWindowOpt match {
      caselon Somelon(reloncelonntelondgelonsWindow) =>
        PagelonRelonquelonst(
          count = rq.count,
          cursor = Somelon(gelontelondgelonCursor(reloncelonntelondgelonsWindow)),
          selonlelonctAll = Somelon(truelon)
        )
      caselon _ => PagelonRelonquelonst(count = rq.count)
    }
    val socialGraphStitch = socialGraph
      .ids(
        IdsRelonquelonst(
          rq.relonlations.map { relonlationshipTypelon =>
            SrcRelonlationship(
              sourcelon = rq.uselonrId,
              relonlationshipTypelon = relonlationshipTypelon,
              targelonts = rq.targelonts
            )
          },
          pagelonRelonquelonst = Somelon(pagelonRelonquelonst),
          contelonxt = Somelon(LookupContelonxt(pelonrformUnion = Somelon(rq.pelonrformUnion)))
        )
      )
      .map { idsRelonsult =>
        val uselonrIds: Selonq[Long] = idsRelonsult.ids
        gelontReloncelonntelondgelonsStats.stat("num_elondgelons").add(uselonrIds.sizelon)
        uselonrIds
      }
      .relonscuelon {
        caselon elon: OvelonrCapacity =>
          stats.countelonr(elon.gelontClass.gelontSimplelonNamelon).incr()
          loggelonr.warn("SGS Ovelonr Capacity", elon)
          Stitch.Nil
      }
    StatsUtil.profilelonStitch(
      socialGraphStitch,
      gelontReloncelonntelondgelonsStats
    )
  }

  // This melonthod relonturn reloncelonnt elondgelons of (uselonrId, timelonInMs)
  delonf gelontReloncelonntelondgelonsWithTimelon(rq: elondgelonRelonquelonstQuelonry): Stitch[Selonq[UselonrIdWithTimelonstamp]] = {
    val pagelonRelonquelonst = rq.reloncelonntelondgelonsWindowOpt match {
      caselon Somelon(reloncelonntelondgelonsWindow) =>
        PagelonRelonquelonst(
          count = rq.count,
          cursor = Somelon(gelontelondgelonCursor(reloncelonntelondgelonsWindow)),
          selonlelonctAll = Somelon(truelon)
        )
      caselon _ => PagelonRelonquelonst(count = rq.count)
    }

    val socialGraphStitch = socialGraph
      .elondgelons(
        elondgelonsRelonquelonst(
          SrcRelonlationship(
            sourcelon = rq.uselonrId,
            relonlationshipTypelon = rq.relonlation,
            targelonts = rq.targelonts
          ),
          pagelonRelonquelonst = Somelon(pagelonRelonquelonst),
          contelonxt = Somelon(LookupContelonxt(pelonrformUnion = Somelon(rq.pelonrformUnion)))
        )
      )
      .map { elondgelonsRelonsult =>
        val uselonrIds = elondgelonsRelonsult.elondgelons.map { socialelondgelon =>
          UselonrIdWithTimelonstamp(socialelondgelon.targelont, socialelondgelon.updatelondAt)
        }
        gelontReloncelonntelondgelonsWithTimelonStats.stat("num_elondgelons").add(uselonrIds.sizelon)
        uselonrIds
      }
      .relonscuelon {
        caselon elon: OvelonrCapacity =>
          stats.countelonr(elon.gelontClass.gelontSimplelonNamelon).incr()
          loggelonr.warn("SGS Ovelonr Capacity", elon)
          Stitch.Nil
      }
    StatsUtil.profilelonStitch(
      socialGraphStitch,
      gelontReloncelonntelondgelonsWithTimelonStats
    )
  }

  // This melonthod relonturns thelon cursor for a timelon duration, such that all thelon elondgelons relonturnelond by SGS will belon crelonatelond
  // in thelon rangelon (now-window, now)
  delonf gelontelondgelonCursor(window: Duration): BytelonBuffelonr = {
    val cursorInLong = (-(Timelon.now - window).inMilliselonconds) << 20
    BytelonBuffelonrUtil.fromLong(cursorInLong)
  }

  // noticelon that this is morelon elonxpelonnsivelon but morelon relonaltimelon than thelon GFS onelon
  delonf gelontIntelonrselonctions(
    uselonrId: Long,
    candidatelonIds: Selonq[Long],
    numIntelonrselonctionIds: Int
  ): Stitch[Map[Long, FollowProof]] = {
    val socialGraphStitch: Stitch[Map[Long, FollowProof]] = Stitch
      .collelonct(candidatelonIds.map { candidatelonId =>
        socialGraph
          .ids(
            IdsRelonquelonst(
              Selonq(
                SrcRelonlationship(uselonrId, RelonlationshipTypelon.Following),
                SrcRelonlationship(candidatelonId, RelonlationshipTypelon.FollowelondBy)
              ),
              pagelonRelonquelonst = Somelon(PagelonRelonquelonst(count = Somelon(numIntelonrselonctionIds)))
            )
          ).map { idsRelonsult =>
            gelontIntelonrselonctionsStats.stat("num_elondgelons").add(idsRelonsult.ids.sizelon)
            (candidatelonId -> FollowProof(idsRelonsult.ids, idsRelonsult.ids.sizelon))
          }
      }).map(_.toMap)
      .relonscuelon {
        caselon elon: OvelonrCapacity =>
          stats.countelonr(elon.gelontClass.gelontSimplelonNamelon).incr()
          loggelonr.warn("social graph ovelonr capacity in hydrating social proof", elon)
          Stitch.valuelon(Map.elonmpty)
      }
    StatsUtil.profilelonStitch(
      socialGraphStitch,
      gelontIntelonrselonctionsStats
    )
  }

  delonf gelontIntelonrselonctionsFromCachelondColumn(
    uselonrId: Long,
    candidatelonIds: Selonq[Long],
    numIntelonrselonctionIds: Int
  ): Stitch[Map[Long, FollowProof]] = {
    val socialGraphStitch: Stitch[Map[Long, FollowProof]] = Stitch
      .collelonct(candidatelonIds.map { candidatelonId =>
        val idsRelonquelonst = IdsRelonquelonst(
          Selonq(
            SrcRelonlationship(uselonrId, RelonlationshipTypelon.Following),
            SrcRelonlationship(candidatelonId, RelonlationshipTypelon.FollowelondBy)
          ),
          pagelonRelonquelonst = Somelon(PagelonRelonquelonst(count = Somelon(numIntelonrselonctionIds)))
        )

        sgsIdsFelontchelonr
          .felontch(idsRelonquelonst, Unit)
          .map(_.v)
          .map { relonsultOpt =>
            relonsultOpt.map { idsRelonsult =>
              gelontIntelonrselonctionsFromCachelondColumnStats.stat("num_elondgelons").add(idsRelonsult.ids.sizelon)
              candidatelonId -> FollowProof(idsRelonsult.ids, idsRelonsult.ids.sizelon)
            }
          }
      }).map(_.flattelonn.toMap)
      .relonscuelon {
        caselon elon: elonxcelonption =>
          stats.countelonr(elon.gelontClass.gelontSimplelonNamelon).incr()
          Stitch.valuelon(Map.elonmpty)
      }
    StatsUtil.profilelonStitch(
      socialGraphStitch,
      gelontIntelonrselonctionsFromCachelondColumnStats
    )
  }

  delonf gelontInvalidRelonlationshipUselonrIds(
    uselonrId: Long,
    maxNumRelonlationship: Int = SocialGraphClielonnt.MaxNumInvalidRelonlationship
  ): Stitch[Selonq[Long]] = {
    gelontReloncelonntelondgelons(
      ReloncelonntelondgelonsQuelonry(
        uselonrId,
        SocialGraphClielonnt.InvalidRelonlationshipTypelons,
        Somelon(maxNumRelonlationship)
      )
    )
  }

  delonf gelontInvalidRelonlationshipUselonrIdsFromCachelondColumn(
    uselonrId: Long,
    maxNumRelonlationship: Int = SocialGraphClielonnt.MaxNumInvalidRelonlationship
  ): Stitch[Selonq[Long]] = {
    gelontReloncelonntelondgelonsFromCachelondColumn(
      ReloncelonntelondgelonsQuelonry(
        uselonrId,
        SocialGraphClielonnt.InvalidRelonlationshipTypelons,
        Somelon(maxNumRelonlationship)
      )
    )
  }

  delonf gelontReloncelonntFollowelondUselonrIds(uselonrId: Long): Stitch[Selonq[Long]] = {
    gelontReloncelonntelondgelons(
      ReloncelonntelondgelonsQuelonry(
        uselonrId,
        Selonq(RelonlationshipTypelon.Following)
      )
    )
  }

  delonf gelontReloncelonntFollowelondUselonrIdsFromCachelondColumn(uselonrId: Long): Stitch[Selonq[Long]] = {
    gelontReloncelonntelondgelonsFromCachelondColumn(
      ReloncelonntelondgelonsQuelonry(
        uselonrId,
        Selonq(RelonlationshipTypelon.Following)
      )
    )
  }

  delonf gelontReloncelonntFollowelondUselonrIdsWithTimelon(uselonrId: Long): Stitch[Selonq[UselonrIdWithTimelonstamp]] = {
    gelontReloncelonntelondgelonsWithTimelon(
      elondgelonRelonquelonstQuelonry(
        uselonrId,
        RelonlationshipTypelon.Following
      )
    )
  }

  delonf gelontReloncelonntFollowelondByUselonrIds(uselonrId: Long): Stitch[Selonq[Long]] = {
    gelontReloncelonntelondgelons(
      ReloncelonntelondgelonsQuelonry(
        uselonrId,
        Selonq(RelonlationshipTypelon.FollowelondBy)
      )
    )
  }

  delonf gelontReloncelonntFollowelondByUselonrIdsFromCachelondColumn(uselonrId: Long): Stitch[Selonq[Long]] = {
    gelontReloncelonntelondgelonsFromCachelondColumn(
      ReloncelonntelondgelonsQuelonry(
        uselonrId,
        Selonq(RelonlationshipTypelon.FollowelondBy)
      )
    )
  }

  delonf gelontReloncelonntFollowelondUselonrIdsWithTimelonWindow(
    uselonrId: Long,
    timelonWindow: Duration
  ): Stitch[Selonq[Long]] = {
    gelontReloncelonntelondgelons(
      ReloncelonntelondgelonsQuelonry(
        uselonrId,
        Selonq(RelonlationshipTypelon.Following),
        reloncelonntelondgelonsWindowOpt = Somelon(timelonWindow)
      )
    )
  }
}

objelonct SocialGraphClielonnt {

  val MaxQuelonrySizelon: Int = 500
  val MaxCachelonSizelon: Int = 5000000
  // Relonf: src/thrift/com/twittelonr/socialgraph/social_graph_selonrvicelon.thrift
  val MaxNumInvalidRelonlationship: Int = 5000
  val CachelonTTL: Duration = Duration.fromHours(24)

  val InvalidRelonlationshipTypelons: Selonq[RelonlationshipTypelon] = Selonq(
    RelonlationshipTypelon.HidelonReloncommelonndations,
    RelonlationshipTypelon.Blocking,
    RelonlationshipTypelon.BlockelondBy,
    RelonlationshipTypelon.Muting,
    RelonlationshipTypelon.MutelondBy,
    RelonlationshipTypelon.RelonportelondAsSpam,
    RelonlationshipTypelon.RelonportelondAsSpamBy,
    RelonlationshipTypelon.RelonportelondAsAbuselon,
    RelonlationshipTypelon.RelonportelondAsAbuselonBy,
    RelonlationshipTypelon.FollowRelonquelonstOutgoing,
    RelonlationshipTypelon.Following,
    RelonlationshipTypelon.UselondToFollow,
  )

  /**
   *
   * Whelonthelonr to call SGS to validatelon elonach candidatelon baselond on thelon numbelonr of invalid relonlationship uselonrs
   * prelonfelontchelond during relonquelonst building stelonp. This aims to not omit any invalid candidatelons that arelon
   * not filtelonrelond out in prelonvious stelonps.
   *   If thelon numbelonr is 0, this might belon a fail-opelonnelond SGS call.
   *   If thelon numbelonr is largelonr or elonqual to 5000, this could hit SGS pagelon sizelon limit.
   * Both caselons account for a small pelonrcelonntagelon of thelon total traffic (<5%).
   *
   * @param numInvalidRelonlationshipUselonrs numbelonr of invalid relonlationship uselonrs felontchelond from gelontInvalidRelonlationshipUselonrIds
   * @relonturn whelonthelonr to elonnablelon post-rankelonr SGS prelondicatelon
   */
  delonf elonnablelonPostRankelonrSgsPrelondicatelon(numInvalidRelonlationshipUselonrs: Int): Boolelonan = {
    numInvalidRelonlationshipUselonrs == 0 || numInvalidRelonlationshipUselonrs >= MaxNumInvalidRelonlationship
  }
}
