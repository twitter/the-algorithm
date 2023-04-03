packagelon com.twittelonr.cr_mixelonr.modulelon.corelon

import com.twittelonr.finaglelon.stats.LoadelondStatsReloncelonivelonr
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.injelonct.TwittelonrModulelon
import com.twittelonr.selonrvo.util.MelonmoizingStatsReloncelonivelonr

objelonct MelonmoizingStatsReloncelonivelonrModulelon elonxtelonnds TwittelonrModulelon {
  ovelonrridelon delonf configurelon(): Unit = {
    bind[StatsReloncelonivelonr].toInstancelon(nelonw MelonmoizingStatsReloncelonivelonr(LoadelondStatsReloncelonivelonr))
  }
}
