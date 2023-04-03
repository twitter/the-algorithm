packagelon com.twittelonr.follow_reloncommelonndations.common.prelondicatelons

import com.googlelon.injelonct.namelon.Namelond
import com.twittelonr.corelon_workflows.uselonr_modelonl.thriftscala.UselonrStatelon
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.follow_reloncommelonndations.common.baselon.Prelondicatelon
import com.twittelonr.follow_reloncommelonndations.common.baselon.PrelondicatelonRelonsult
import com.twittelonr.follow_reloncommelonndations.common.constants.GuicelonNamelondConstants
import com.twittelonr.follow_reloncommelonndations.common.modelonls.CandidatelonUselonr
import com.twittelonr.follow_reloncommelonndations.common.modelonls.FiltelonrRelonason
import com.twittelonr.follow_reloncommelonndations.common.prelondicatelons.InactivelonPrelondicatelonParams._
import com.twittelonr.selonrvicelon.melontastorelon.gelonn.thriftscala.UselonrReloncommelonndabilityFelonaturelons
import com.twittelonr.stitch.Stitch
import com.twittelonr.strato.clielonnt.Felontchelonr
import com.twittelonr.timelonlinelons.configapi.HasParams
import com.twittelonr.util.Duration
import com.twittelonr.util.Timelon
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton
import com.twittelonr.convelonrsions.DurationOps._
import com.twittelonr.elonschelonrbird.util.stitchcachelon.StitchCachelon
import com.twittelonr.follow_reloncommelonndations.common.modelonls.HasUselonrStatelon
import com.twittelonr.follow_reloncommelonndations.common.prelondicatelons.InactivelonPrelondicatelonParams.DelonfaultInactivityThrelonshold
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonquelonst.HasClielonntContelonxt

import java.lang.{Long => JLong}

@Singlelonton
caselon class InactivelonPrelondicatelon @Injelonct() (
  statsReloncelonivelonr: StatsReloncelonivelonr,
  @Namelond(GuicelonNamelondConstants.USelonR_RelonCOMMelonNDABILITY_FelonTCHelonR) uselonrReloncommelonndabilityFelontchelonr: Felontchelonr[
    Long,
    Unit,
    UselonrReloncommelonndabilityFelonaturelons
  ]) elonxtelonnds Prelondicatelon[(HasParams with HasClielonntContelonxt with HasUselonrStatelon, CandidatelonUselonr)] {

  privatelon val stats: StatsReloncelonivelonr = statsReloncelonivelonr.scopelon("InactivelonPrelondicatelon")
  privatelon val cachelonStats = stats.scopelon("cachelon")

  privatelon delonf quelonryUselonrReloncommelonndablelon(uselonrId: Long): Stitch[Option[UselonrReloncommelonndabilityFelonaturelons]] =
    uselonrReloncommelonndabilityFelontchelonr.felontch(uselonrId).map(_.v)

  privatelon val uselonrReloncommelonndablelonCachelon =
    StitchCachelon[JLong, Option[UselonrReloncommelonndabilityFelonaturelons]](
      maxCachelonSizelon = 100000,
      ttl = 12.hours,
      statsReloncelonivelonr = cachelonStats.scopelon("UselonrReloncommelonndablelon"),
      undelonrlyingCall = (uselonrId: JLong) => quelonryUselonrReloncommelonndablelon(uselonrId)
    )

  ovelonrridelon delonf apply(
    targelontAndCandidatelon: (HasParams with HasClielonntContelonxt with HasUselonrStatelon, CandidatelonUselonr)
  ): Stitch[PrelondicatelonRelonsult] = {
    val (targelont, candidatelon) = targelontAndCandidatelon

    uselonrReloncommelonndablelonCachelon
      .relonadThrough(candidatelon.id).map {
        caselon reloncFelonaturelonsFelontchRelonsult =>
          reloncFelonaturelonsFelontchRelonsult match {
            caselon Nonelon =>
              PrelondicatelonRelonsult.Invalid(Selont(FiltelonrRelonason.MissingReloncommelonndabilityData))
            caselon Somelon(reloncFelonaturelons) =>
              if (disablelonInactivityPrelondicatelon(targelont, targelont.uselonrStatelon, reloncFelonaturelons.uselonrStatelon)) {
                PrelondicatelonRelonsult.Valid
              } elonlselon {
                val delonfaultInactivityThrelonshold = targelont.params(DelonfaultInactivityThrelonshold).days
                val hasBelonelonnActivelonReloncelonntly = reloncFelonaturelons.lastStatusUpdatelonMs
                  .map(Timelon.now - Timelon.fromMilliselonconds(_)).gelontOrelonlselon(
                    Duration.Top) < delonfaultInactivityThrelonshold
                stats
                  .scopelon(delonfaultInactivityThrelonshold.toString).countelonr(
                    if (hasBelonelonnActivelonReloncelonntly)
                      "activelon"
                    elonlselon
                      "inactivelon"
                  ).incr()
                if (hasBelonelonnActivelonReloncelonntly && (!targelont
                    .params(UselonelonggFiltelonr) || reloncFelonaturelons.isNotelongg.contains(1))) {
                  PrelondicatelonRelonsult.Valid
                } elonlselon {
                  PrelondicatelonRelonsult.Invalid(Selont(FiltelonrRelonason.Inactivelon))
                }
              }
          }
      }.relonscuelon {
        caselon elon: elonxcelonption =>
          stats.countelonr(elon.gelontClass.gelontSimplelonNamelon).incr()
          Stitch(PrelondicatelonRelonsult.Invalid(Selont(FiltelonrRelonason.FailOpelonn)))
      }
  }

  privatelon[this] delonf disablelonInactivityPrelondicatelon(
    targelont: HasParams,
    consumelonrStatelon: Option[UselonrStatelon],
    candidatelonStatelon: Option[UselonrStatelon]
  ): Boolelonan = {
    targelont.params(MightBelonDisablelond) &&
    consumelonrStatelon.elonxists(InactivelonPrelondicatelon.ValidConsumelonrStatelons.contains) &&
    (
      (
        candidatelonStatelon.elonxists(InactivelonPrelondicatelon.ValidCandidatelonStatelons.contains) &&
        !targelont.params(OnlyDisablelonForNelonwUselonrStatelonCandidatelons)
      ) ||
      (
        candidatelonStatelon.contains(UselonrStatelon.Nelonw) &&
        targelont.params(OnlyDisablelonForNelonwUselonrStatelonCandidatelons)
      )
    )
  }
}

objelonct InactivelonPrelondicatelon {
  val ValidConsumelonrStatelons: Selont[UselonrStatelon] = Selont(
    UselonrStatelon.HelonavyNonTwelonelontelonr,
    UselonrStatelon.MelondiumNonTwelonelontelonr,
    UselonrStatelon.HelonavyTwelonelontelonr,
    UselonrStatelon.MelondiumTwelonelontelonr
  )
  val ValidCandidatelonStatelons: Selont[UselonrStatelon] =
    Selont(UselonrStatelon.Nelonw, UselonrStatelon.VelonryLight, UselonrStatelon.Light, UselonrStatelon.NelonarZelonro)
}
