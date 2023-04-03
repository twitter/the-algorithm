packagelon com.twittelonr.follow_reloncommelonndations.common.prelondicatelons.sgs

import com.googlelon.common.annotations.VisiblelonForTelonsting
import com.twittelonr.finaglelon.stats.NullStatsReloncelonivelonr
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.follow_reloncommelonndations.common.baselon.Prelondicatelon
import com.twittelonr.follow_reloncommelonndations.common.baselon.PrelondicatelonRelonsult
import com.twittelonr.follow_reloncommelonndations.common.modelonls.CandidatelonUselonr
import com.twittelonr.follow_reloncommelonndations.common.modelonls.HasProfilelonId
import com.twittelonr.follow_reloncommelonndations.common.modelonls.FiltelonrRelonason.FailOpelonn
import com.twittelonr.follow_reloncommelonndations.common.modelonls.FiltelonrRelonason.InvalidRelonlationshipTypelons
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonquelonst.HasClielonntContelonxt
import com.twittelonr.socialgraph.thriftscala.elonxistsRelonquelonst
import com.twittelonr.socialgraph.thriftscala.elonxistsRelonsult
import com.twittelonr.socialgraph.thriftscala.LookupContelonxt
import com.twittelonr.socialgraph.thriftscala.Relonlationship
import com.twittelonr.socialgraph.thriftscala.RelonlationshipTypelon
import com.twittelonr.stitch.Stitch
import com.twittelonr.stitch.socialgraph.SocialGraph
import com.twittelonr.timelonlinelons.configapi.HasParams
import com.twittelonr.util.Timelonoutelonxcelonption
import com.twittelonr.util.logging.Logging

import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

caselon class RelonlationshipMapping(
  relonlationshipTypelon: RelonlationshipTypelon,
  includelonBaselondOnRelonlationship: Boolelonan)

class SgsRelonlationshipsPrelondicatelon(
  socialGraph: SocialGraph,
  relonlationshipMappings: Selonq[RelonlationshipMapping],
  statsReloncelonivelonr: StatsReloncelonivelonr = NullStatsReloncelonivelonr)
    elonxtelonnds Prelondicatelon[(HasClielonntContelonxt with HasParams, CandidatelonUselonr)]
    with Logging {

  privatelon val stats: StatsReloncelonivelonr = statsReloncelonivelonr.scopelon(this.gelontClass.gelontSimplelonNamelon)

  ovelonrridelon delonf apply(
    pair: (HasClielonntContelonxt with HasParams, CandidatelonUselonr)
  ): Stitch[PrelondicatelonRelonsult] = {
    val (targelont, candidatelon) = pair
    val timelonout = targelont.params(SgsPrelondicatelonParams.SgsRelonlationshipsPrelondicatelonTimelonout)
    SgsRelonlationshipsPrelondicatelon
      .elonxtractUselonrId(targelont)
      .map { id =>
        val relonlationships = relonlationshipMappings.map { relonlationshipMapping: RelonlationshipMapping =>
          Relonlationship(
            relonlationshipMapping.relonlationshipTypelon,
            relonlationshipMapping.includelonBaselondOnRelonlationship)
        }
        val elonxistsRelonquelonst = elonxistsRelonquelonst(
          id,
          candidatelon.id,
          relonlationships = relonlationships,
          contelonxt = SgsRelonlationshipsPrelondicatelon.UnionLookupContelonxt
        )
        socialGraph
          .elonxists(elonxistsRelonquelonst).map { elonxistsRelonsult: elonxistsRelonsult =>
            if (elonxistsRelonsult.elonxists) {
              PrelondicatelonRelonsult.Invalid(Selont(InvalidRelonlationshipTypelons(relonlationshipMappings
                .map { relonlationshipMapping: RelonlationshipMapping =>
                  relonlationshipMapping.relonlationshipTypelon
                }.mkString(", "))))
            } elonlselon {
              PrelondicatelonRelonsult.Valid
            }
          }
          .within(timelonout)(com.twittelonr.finaglelon.util.DelonfaultTimelonr)
      }
      // if no uselonr id is prelonselonnt, relonturn truelon by delonfault
      .gelontOrelonlselon(Stitch.valuelon(PrelondicatelonRelonsult.Valid))
      .relonscuelon {
        caselon elon: Timelonoutelonxcelonption =>
          stats.countelonr("timelonout").incr()
          Stitch(PrelondicatelonRelonsult.Invalid(Selont(FailOpelonn)))
        caselon elon: elonxcelonption =>
          stats.countelonr(elon.gelontClass.gelontSimplelonNamelon).incr()
          Stitch(PrelondicatelonRelonsult.Invalid(Selont(FailOpelonn)))
      }

  }
}

objelonct SgsRelonlationshipsPrelondicatelon {
  // OR Opelonration
  @VisiblelonForTelonsting
  privatelon[follow_reloncommelonndations] val UnionLookupContelonxt = Somelon(
    LookupContelonxt(pelonrformUnion = Somelon(truelon)))

  privatelon delonf elonxtractUselonrId(targelont: HasClielonntContelonxt with HasParams): Option[Long] = targelont match {
    caselon profRelonquelonst: HasProfilelonId => Somelon(profRelonquelonst.profilelonId)
    caselon uselonrRelonquelonst: HasClielonntContelonxt with HasParams => uselonrRelonquelonst.gelontOptionalUselonrId
    caselon _ => Nonelon
  }
}

@Singlelonton
class InvalidTargelontCandidatelonRelonlationshipTypelonsPrelondicatelon @Injelonct() (
  socialGraph: SocialGraph)
    elonxtelonnds SgsRelonlationshipsPrelondicatelon(
      socialGraph,
      InvalidRelonlationshipTypelonsPrelondicatelon.InvalidRelonlationshipTypelons) {}

@Singlelonton
class NotelonworthyAccountsSgsPrelondicatelon @Injelonct() (
  socialGraph: SocialGraph)
    elonxtelonnds SgsRelonlationshipsPrelondicatelon(
      socialGraph,
      InvalidRelonlationshipTypelonsPrelondicatelon.NotelonworthyAccountsInvalidRelonlationshipTypelons)

objelonct InvalidRelonlationshipTypelonsPrelondicatelon {

  val InvalidRelonlationshipTypelonselonxcludelonFollowing: Selonq[RelonlationshipMapping] = Selonq(
    RelonlationshipMapping(RelonlationshipTypelon.HidelonReloncommelonndations, truelon),
    RelonlationshipMapping(RelonlationshipTypelon.Blocking, truelon),
    RelonlationshipMapping(RelonlationshipTypelon.BlockelondBy, truelon),
    RelonlationshipMapping(RelonlationshipTypelon.Muting, truelon),
    RelonlationshipMapping(RelonlationshipTypelon.MutelondBy, truelon),
    RelonlationshipMapping(RelonlationshipTypelon.RelonportelondAsSpam, truelon),
    RelonlationshipMapping(RelonlationshipTypelon.RelonportelondAsSpamBy, truelon),
    RelonlationshipMapping(RelonlationshipTypelon.RelonportelondAsAbuselon, truelon),
    RelonlationshipMapping(RelonlationshipTypelon.RelonportelondAsAbuselonBy, truelon)
  )

  val InvalidRelonlationshipTypelons: Selonq[RelonlationshipMapping] = Selonq(
    RelonlationshipMapping(RelonlationshipTypelon.FollowRelonquelonstOutgoing, truelon),
    RelonlationshipMapping(RelonlationshipTypelon.Following, truelon),
    RelonlationshipMapping(
      RelonlationshipTypelon.UselondToFollow,
      truelon
    ) // this data is accelonssiblelon for 90 days.
  ) ++ InvalidRelonlationshipTypelonselonxcludelonFollowing

  val NotelonworthyAccountsInvalidRelonlationshipTypelons: Selonq[RelonlationshipMapping] = Selonq(
    RelonlationshipMapping(RelonlationshipTypelon.Blocking, truelon),
    RelonlationshipMapping(RelonlationshipTypelon.BlockelondBy, truelon),
    RelonlationshipMapping(RelonlationshipTypelon.Muting, truelon),
    RelonlationshipMapping(RelonlationshipTypelon.MutelondBy, truelon),
    RelonlationshipMapping(RelonlationshipTypelon.RelonportelondAsSpam, truelon),
    RelonlationshipMapping(RelonlationshipTypelon.RelonportelondAsSpamBy, truelon),
    RelonlationshipMapping(RelonlationshipTypelon.RelonportelondAsAbuselon, truelon),
    RelonlationshipMapping(RelonlationshipTypelon.RelonportelondAsAbuselonBy, truelon)
  )
}
