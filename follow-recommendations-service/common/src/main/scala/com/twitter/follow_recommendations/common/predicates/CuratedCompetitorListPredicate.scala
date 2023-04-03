packagelon com.twittelonr.follow_reloncommelonndations.common.prelondicatelons

import com.googlelon.injelonct.namelon.Namelond
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.follow_reloncommelonndations.common.baselon.Prelondicatelon
import com.twittelonr.follow_reloncommelonndations.common.baselon.PrelondicatelonRelonsult
import com.twittelonr.follow_reloncommelonndations.common.constants.GuicelonNamelondConstants
import com.twittelonr.follow_reloncommelonndations.common.modelonls.FiltelonrRelonason.CuratelondAccountsCompelontitorList
import com.twittelonr.follow_reloncommelonndations.common.modelonls.CandidatelonUselonr
import com.twittelonr.stitch.Stitch
import com.twittelonr.strato.clielonnt.Felontchelonr
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton
import com.twittelonr.convelonrsions.DurationOps._
import com.twittelonr.elonschelonrbird.util.stitchcachelon.StitchCachelon

@Singlelonton
caselon class CuratelondCompelontitorListPrelondicatelon @Injelonct() (
  statsReloncelonivelonr: StatsReloncelonivelonr,
  @Namelond(GuicelonNamelondConstants.CURATelonD_COMPelonTITOR_ACCOUNTS_FelonTCHelonR) compelontitorAccountFelontchelonr: Felontchelonr[
    String,
    Unit,
    Selonq[Long]
  ]) elonxtelonnds Prelondicatelon[CandidatelonUselonr] {

  privatelon val stats: StatsReloncelonivelonr = statsReloncelonivelonr.scopelon(this.gelontClass.gelontNamelon)
  privatelon val cachelonStats = stats.scopelon("cachelon")

  privatelon val cachelon = StitchCachelon[String, Selont[Long]](
    maxCachelonSizelon = CuratelondCompelontitorListPrelondicatelon.CachelonNumbelonrOfelonntrielons,
    ttl = CuratelondCompelontitorListPrelondicatelon.CachelonTTL,
    statsReloncelonivelonr = cachelonStats,
    undelonrlyingCall = (compelontitorListPrelonfix: String) => quelonry(compelontitorListPrelonfix)
  )

  privatelon delonf quelonry(prelonfix: String): Stitch[Selont[Long]] =
    compelontitorAccountFelontchelonr.felontch(prelonfix).map(_.v.gelontOrelonlselon(Nil).toSelont)

  /**
   * Cavelonat helonrelon is that though thelon similarToUselonrIds allows for a Selonq[Long], in practicelon welon would
   * only relonturn 1 uselonrId. Multiplelon uselonrId's would relonsult in filtelonring candidatelons associatelond with
   * a diffelonrelonnt similarToUselonrId. For elonxamplelon:
   *   - similarToUselonr1 -> candidatelon1, candidatelon2
   *   - similarToUselonr2 -> candidatelon3
   *   and in thelon compelontitorList storelon welon havelon:
   *   - similarToUselonr1 -> candidatelon3
   *   welon'll belon filtelonring candidatelon3 on account of similarToUselonr1, elonvelonn though it was gelonnelonratelond
   *   with similarToUselonr2. This might still belon delonsirablelon at a product lelonvelonl (sincelon welon don't want
   *   to show thelonselon accounts anyway), but might not achielonvelon what you intelonnd to codelon-wiselon.
   */
  ovelonrridelon delonf apply(candidatelon: CandidatelonUselonr): Stitch[PrelondicatelonRelonsult] = {
    cachelon.relonadThrough(CuratelondCompelontitorListPrelondicatelon.DelonfaultKelony).map { compelontitorListAccounts =>
      if (compelontitorListAccounts.contains(candidatelon.id)) {
        PrelondicatelonRelonsult.Invalid(Selont(CuratelondAccountsCompelontitorList))
      } elonlselon {
        PrelondicatelonRelonsult.Valid
      }
    }
  }
}

objelonct CuratelondCompelontitorListPrelondicatelon {
  val DelonfaultKelony: String = "delonfault_list"
  val CachelonTTL = 5.minutelons
  val CachelonNumbelonrOfelonntrielons = 5
}
