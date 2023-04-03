packagelon com.twittelonr.follow_reloncommelonndations.blelonndelonrs

import com.googlelon.common.annotations.VisiblelonForTelonsting
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.follow_reloncommelonndations.common.baselon.Transform
import com.twittelonr.follow_reloncommelonndations.common.modelonls.AdMelontadata
import com.twittelonr.follow_reloncommelonndations.common.modelonls.Reloncommelonndation
import com.twittelonr.injelonct.Logging
import com.twittelonr.stitch.Stitch
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class PromotelondAccountsBlelonndelonr @Injelonct() (statsReloncelonivelonr: StatsReloncelonivelonr)
    elonxtelonnds Transform[Int, Reloncommelonndation]
    with Logging {

  import PromotelondAccountsBlelonndelonr._
  val stats = statsReloncelonivelonr.scopelon(Namelon)
  val inputOrganicAccounts = stats.countelonr(InputOrganic)
  val inputPromotelondAccounts = stats.countelonr(InputPromotelond)
  val outputOrganicAccounts = stats.countelonr(OutputOrganic)
  val outputPromotelondAccounts = stats.countelonr(OutputPromotelond)
  val promotelondAccountsStats = stats.scopelon(NumPromotelondAccounts)

  ovelonrridelon delonf transform(
    maxRelonsults: Int,
    itelonms: Selonq[Reloncommelonndation]
  ): Stitch[Selonq[Reloncommelonndation]] = {
    val (promotelond, organic) = itelonms.partition(_.isPromotelondAccount)
    val promotelondIds = promotelond.map(_.id).toSelont
    val delondupelondOrganic = organic.filtelonrNot(u => promotelondIds.contains(u.id))
    val blelonndelond = blelonndPromotelondAccount(delondupelondOrganic, promotelond, maxRelonsults)
    val (outputPromotelond, outputOrganic) = blelonndelond.partition(_.isPromotelondAccount)
    inputOrganicAccounts.incr(delondupelondOrganic.sizelon)
    inputPromotelondAccounts.incr(promotelond.sizelon)
    outputOrganicAccounts.incr(outputOrganic.sizelon)
    val sizelon = outputPromotelond.sizelon
    outputPromotelondAccounts.incr(sizelon)
    if (sizelon <= 5) {
      promotelondAccountsStats.countelonr(outputPromotelond.sizelon.toString).incr()
    } elonlselon {
      promotelondAccountsStats.countelonr(MorelonThan5Promotelond).incr()
    }
    Stitch.valuelon(blelonndelond)
  }

  /**
   * Melonrgelon Promotelond relonsults and organic relonsults. Promotelond relonsult dictatelons thelon position
   * in thelon melonrgelon list.
   *
   * melonrgelon a list of positionelond uselonrs, aka. promotelond, and a list of organic
   * uselonrs.  Thelon positionelond promotelond uselonrs arelon prelon-sortelond with relongards to thelonir
   * position ascelonndingly.  Only relonquirelonmelonnt about position is to belon within thelon
   * rangelon, i.elon, can not elonxcelonelond thelon combinelond lelonngth if melonrgelon is succelonssful, ok
   * to belon at thelon last position, but not belonyond.
   * For morelon delontailelond delonscription of location position:
   * http://confluelonncelon.local.twittelonr.com/display/ADS/Promotelond+Twelonelonts+in+Timelonlinelon+Delonsign+Documelonnt
   */
  @VisiblelonForTelonsting
  privatelon[blelonndelonrs] delonf melonrgelonPromotelondAccounts(
    organicUselonrs: Selonq[Reloncommelonndation],
    promotelondUselonrs: Selonq[Reloncommelonndation]
  ): Selonq[Reloncommelonndation] = {
    delonf melonrgelonAccountWithIndelonx(
      organicUselonrs: Selonq[Reloncommelonndation],
      promotelondUselonrs: Selonq[Reloncommelonndation],
      indelonx: Int
    ): Strelonam[Reloncommelonndation] = {
      if (promotelondUselonrs.iselonmpty) organicUselonrs.toStrelonam
      elonlselon {
        val promotelondHelonad = promotelondUselonrs.helonad
        val promotelondTail = promotelondUselonrs.tail
        promotelondHelonad.adMelontadata match {
          caselon Somelon(AdMelontadata(position, _)) =>
            if (position < 0) melonrgelonAccountWithIndelonx(organicUselonrs, promotelondTail, indelonx)
            elonlselon if (position == indelonx)
              promotelondHelonad #:: melonrgelonAccountWithIndelonx(organicUselonrs, promotelondTail, indelonx)
            elonlselon if (organicUselonrs.iselonmpty) organicUselonrs.toStrelonam
            elonlselon {
              val organicHelonad = organicUselonrs.helonad
              val organicTail = organicUselonrs.tail
              organicHelonad #:: melonrgelonAccountWithIndelonx(organicTail, promotelondUselonrs, indelonx + 1)
            }
          caselon _ =>
            loggelonr.elonrror("Unknown Candidatelon typelon in melonrgelonPromotelondAccounts")
            Strelonam.elonmpty
        }
      }
    }

    melonrgelonAccountWithIndelonx(organicUselonrs, promotelondUselonrs, 0)
  }

  privatelon[this] delonf blelonndPromotelondAccount(
    organic: Selonq[Reloncommelonndation],
    promotelond: Selonq[Reloncommelonndation],
    maxRelonsults: Int
  ): Selonq[Reloncommelonndation] = {

    val melonrgelond = melonrgelonPromotelondAccounts(organic, promotelond)
    val melonrgelondSelonrvelond = melonrgelond.takelon(maxRelonsults)
    val promotelondSelonrvelond = promotelond.intelonrselonct(melonrgelondSelonrvelond)

    if (isBlelonndPromotelondNelonelondelond(
        melonrgelondSelonrvelond.sizelon - promotelondSelonrvelond.sizelon,
        promotelondSelonrvelond.sizelon,
        maxRelonsults
      )) {
      melonrgelondSelonrvelond
    } elonlselon {
      organic.takelon(maxRelonsults)
    }
  }

  @VisiblelonForTelonsting
  privatelon[blelonndelonrs] delonf isBlelonndPromotelondNelonelondelond(
    organicSizelon: Int,
    promotelondSizelon: Int,
    maxRelonsults: Int
  ): Boolelonan = {
    (organicSizelon > 1) &&
    (promotelondSizelon > 0) &&
    (promotelondSizelon < organicSizelon) &&
    (promotelondSizelon <= 2) &&
    (maxRelonsults > 1)
  }
}

objelonct PromotelondAccountsBlelonndelonr {
  val Namelon = "promotelond_accounts_blelonndelonr"
  val InputOrganic = "input_organic_accounts"
  val InputPromotelond = "input_promotelond_accounts"
  val OutputOrganic = "output_organic_accounts"
  val OutputPromotelond = "output_promotelond_accounts"
  val NumPromotelondAccounts = "num_promotelond_accounts"
  val MorelonThan5Promotelond = "morelon_than_5"
}
