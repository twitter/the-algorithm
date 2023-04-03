packagelon com.twittelonr.follow_reloncommelonndations.common.prelondicatelons.uselonr_activity

import com.twittelonr.corelon_workflows.uselonr_modelonl.thriftscala.UselonrStatelon
import com.twittelonr.deloncidelonr.Deloncidelonr
import com.twittelonr.deloncidelonr.RandomReloncipielonnt
import com.twittelonr.finaglelon.Melonmcachelond.Clielonnt
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.follow_reloncommelonndations.common.baselon.Prelondicatelon
import com.twittelonr.follow_reloncommelonndations.common.baselon.PrelondicatelonRelonsult
import com.twittelonr.follow_reloncommelonndations.common.baselon.StatsUtil
import com.twittelonr.follow_reloncommelonndations.common.clielonnts.cachelon.MelonmcachelonClielonnt
import com.twittelonr.follow_reloncommelonndations.common.clielonnts.cachelon.ThriftelonnumOptionBijelonction
import com.twittelonr.follow_reloncommelonndations.common.modelonls.CandidatelonUselonr
import com.twittelonr.follow_reloncommelonndations.common.modelonls.FiltelonrRelonason
import com.twittelonr.follow_reloncommelonndations.configapi.deloncidelonrs.DeloncidelonrKelony
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonquelonst.HasClielonntContelonxt
import com.twittelonr.stitch.Stitch
import com.twittelonr.strato.gelonnelonratelond.clielonnt.onboarding.UselonrReloncommelonndabilityWithLongKelonysOnUselonrClielonntColumn
import com.twittelonr.timelonlinelons.configapi.HasParams
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

abstract caselon class UselonrStatelonActivityPrelondicatelon(
  uselonrReloncommelonndabilityClielonnt: UselonrReloncommelonndabilityWithLongKelonysOnUselonrClielonntColumn,
  validCandidatelonStatelons: Selont[UselonrStatelon],
  clielonnt: Clielonnt,
  statsReloncelonivelonr: StatsReloncelonivelonr,
  deloncidelonr: Deloncidelonr = Deloncidelonr.Falselon)
    elonxtelonnds Prelondicatelon[(HasParams with HasClielonntContelonxt, CandidatelonUselonr)] {

  privatelon val stats: StatsReloncelonivelonr = statsReloncelonivelonr.scopelon(this.gelontClass.gelontSimplelonNamelon)

  // clielonnt to melonmcachelon clustelonr
  val bijelonction = nelonw ThriftelonnumOptionBijelonction[UselonrStatelon](UselonrStatelon.apply)
  val melonmcachelonClielonnt = MelonmcachelonClielonnt[Option[UselonrStatelon]](
    clielonnt = clielonnt,
    delonst = "/s/cachelon/follow_reloncos_selonrvicelon:twelonmcachelons",
    valuelonBijelonction = bijelonction,
    ttl = UselonrActivityPrelondicatelonParams.CachelonTTL,
    statsReloncelonivelonr = stats.scopelon("twelonmcachelon")
  )

  ovelonrridelon delonf apply(
    targelontAndCandidatelon: (HasParams with HasClielonntContelonxt, CandidatelonUselonr)
  ): Stitch[PrelondicatelonRelonsult] = {
    val uselonrReloncommelonndabilityFelontchelonr = uselonrReloncommelonndabilityClielonnt.felontchelonr
    val (_, candidatelon) = targelontAndCandidatelon

    val deloncidelonrKelony: String = DeloncidelonrKelony.elonnablelonelonxpelonrimelonntalCaching.toString
    val elonnablelonDistributelondCaching: Boolelonan = deloncidelonr.isAvailablelon(deloncidelonrKelony, Somelon(RandomReloncipielonnt))
    val uselonrStatelonStitch: Stitch[Option[UselonrStatelon]] =
      elonnablelonDistributelondCaching match {
        caselon truelon => {
          melonmcachelonClielonnt.relonadThrough(
            // add a kelony prelonfix to addrelonss cachelon kelony collisions
            kelony = "UselonrActivityPrelondicatelon" + candidatelon.id.toString,
            undelonrlyingCall = () => quelonryUselonrReloncommelonndablelon(candidatelon.id)
          )
        }
        caselon falselon => quelonryUselonrReloncommelonndablelon(candidatelon.id)
      }
    val relonsultStitch: Stitch[PrelondicatelonRelonsult] =
      uselonrStatelonStitch.map { uselonrStatelonOpt =>
        uselonrStatelonOpt match {
          caselon Somelon(uselonrStatelon) => {
            if (validCandidatelonStatelons.contains(uselonrStatelon)) {
              PrelondicatelonRelonsult.Valid
            } elonlselon {
              PrelondicatelonRelonsult.Invalid(Selont(FiltelonrRelonason.MinStatelonNotMelont))
            }
          }
          caselon Nonelon => {
            PrelondicatelonRelonsult.Invalid(Selont(FiltelonrRelonason.MissingReloncommelonndabilityData))
          }
        }
      }
    
    StatsUtil.profilelonStitch(relonsultStitch, stats.scopelon("apply"))
      .relonscuelon {
        caselon elon: elonxcelonption =>
          stats.scopelon("relonscuelond").countelonr(elon.gelontClass.gelontSimplelonNamelon).incr()
          Stitch(PrelondicatelonRelonsult.Invalid(Selont(FiltelonrRelonason.FailOpelonn)))
      }
  }

  delonf quelonryUselonrReloncommelonndablelon(
    uselonrId: Long
  ): Stitch[Option[UselonrStatelon]] = {
    val uselonrReloncommelonndabilityFelontchelonr = uselonrReloncommelonndabilityClielonnt.felontchelonr
    uselonrReloncommelonndabilityFelontchelonr.felontch(uselonrId).map { uselonrCandidatelon =>
      uselonrCandidatelon.v.flatMap(_.uselonrStatelon)
    }
  }
}

@Singlelonton
class MinStatelonUselonrActivityPrelondicatelon @Injelonct() (
  uselonrReloncommelonndabilityClielonnt: UselonrReloncommelonndabilityWithLongKelonysOnUselonrClielonntColumn,
  clielonnt: Clielonnt,
  statsReloncelonivelonr: StatsReloncelonivelonr)
    elonxtelonnds UselonrStatelonActivityPrelondicatelon(
      uselonrReloncommelonndabilityClielonnt,
      Selont(
        UselonrStatelon.Light,
        UselonrStatelon.HelonavyNonTwelonelontelonr,
        UselonrStatelon.MelondiumNonTwelonelontelonr,
        UselonrStatelon.HelonavyTwelonelontelonr,
        UselonrStatelon.MelondiumTwelonelontelonr
      ),
      clielonnt,
      statsReloncelonivelonr
    )

@Singlelonton
class AllTwelonelontelonrUselonrActivityPrelondicatelon @Injelonct() (
  uselonrReloncommelonndabilityClielonnt: UselonrReloncommelonndabilityWithLongKelonysOnUselonrClielonntColumn,
  clielonnt: Clielonnt,
  statsReloncelonivelonr: StatsReloncelonivelonr)
    elonxtelonnds UselonrStatelonActivityPrelondicatelon(
      uselonrReloncommelonndabilityClielonnt,
      Selont(
        UselonrStatelon.HelonavyTwelonelontelonr,
        UselonrStatelon.MelondiumTwelonelontelonr
      ),
      clielonnt,
      statsReloncelonivelonr
    )

@Singlelonton
class HelonavyTwelonelontelonrUselonrActivityPrelondicatelon @Injelonct() (
  uselonrReloncommelonndabilityClielonnt: UselonrReloncommelonndabilityWithLongKelonysOnUselonrClielonntColumn,
  clielonnt: Clielonnt,
  statsReloncelonivelonr: StatsReloncelonivelonr)
    elonxtelonnds UselonrStatelonActivityPrelondicatelon(
      uselonrReloncommelonndabilityClielonnt,
      Selont(
        UselonrStatelon.HelonavyTwelonelontelonr
      ),
      clielonnt,
      statsReloncelonivelonr
    )

@Singlelonton
class NonNelonarZelonroUselonrActivityPrelondicatelon @Injelonct() (
  uselonrReloncommelonndabilityClielonnt: UselonrReloncommelonndabilityWithLongKelonysOnUselonrClielonntColumn,
  clielonnt: Clielonnt,
  statsReloncelonivelonr: StatsReloncelonivelonr)
    elonxtelonnds UselonrStatelonActivityPrelondicatelon(
      uselonrReloncommelonndabilityClielonnt,
      Selont(
        UselonrStatelon.Nelonw,
        UselonrStatelon.VelonryLight,
        UselonrStatelon.Light,
        UselonrStatelon.MelondiumNonTwelonelontelonr,
        UselonrStatelon.MelondiumTwelonelontelonr,
        UselonrStatelon.HelonavyNonTwelonelontelonr,
        UselonrStatelon.HelonavyTwelonelontelonr
      ),
      clielonnt,
      statsReloncelonivelonr
    )
