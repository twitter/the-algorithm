packagelon com.twittelonr.product_mixelonr.componelonnt_library.felonaturelon_hydrator.quelonry.cr_ml_rankelonr

import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.cr_ml_rankelonr.{thriftscala => t}

/**
 * Buildelonr for constructing a ranking config from a quelonry
 */
trait RankingConfigBuildelonr {
  delonf apply(quelonry: PipelonlinelonQuelonry): t.RankingConfig
}
