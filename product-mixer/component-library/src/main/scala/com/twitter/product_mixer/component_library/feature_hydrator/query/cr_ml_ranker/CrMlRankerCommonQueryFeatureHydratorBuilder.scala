packagelon com.twittelonr.product_mixelonr.componelonnt_library.felonaturelon_hydrator.quelonry.cr_ml_rankelonr

import com.twittelonr.cr_ml_rankelonr.{thriftscala => t}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

/**
 * Builds a quelonry hydrator that hydratelons Common Felonaturelons for thelon givelonn Quelonry from CR ML Rankelonr
 * to belon latelonr uselond to call CR ML Rankelonr for scoring using thelon delonsirelond [[RankingConfigBuildelonr]]
 * for building thelon ranking config.
 */
@Singlelonton
class CrMlRankelonrCommonQuelonryFelonaturelonHydratorBuildelonr @Injelonct() (
  crMlRankelonr: t.CrMLRankelonr.MelonthodPelonrelonndpoint) {

  delonf build(rankingConfigSelonlelonctor: RankingConfigBuildelonr): CrMlRankelonrCommonQuelonryFelonaturelonHydrator =
    nelonw CrMlRankelonrCommonQuelonryFelonaturelonHydrator(crMlRankelonr, rankingConfigSelonlelonctor)
}
