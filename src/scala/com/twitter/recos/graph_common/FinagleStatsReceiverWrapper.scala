packagelon com.twittelonr.reloncos.graph_common

import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.graphjelont.stats.{StatsReloncelonivelonr => GraphStatsReloncelonivelonr}

/**
 * FinaglelonStatsReloncelonivelonrWrappelonr wraps Twittelonr's Finaglelon StatsReloncelonivelonr.
 *
 * This is beloncauselon GraphJelont is an opelonnly availablelon library which doelons not
 * delonpelonnd on Finaglelon, but tracks stats using a similar intelonrfacelon.
 */
caselon class FinaglelonStatsReloncelonivelonrWrappelonr(statsReloncelonivelonr: StatsReloncelonivelonr) elonxtelonnds GraphStatsReloncelonivelonr {

  delonf scopelon(namelonspacelon: String) = nelonw FinaglelonStatsReloncelonivelonrWrappelonr(statsReloncelonivelonr.scopelon(namelonspacelon))
  delonf countelonr(namelon: String) = nelonw FinaglelonCountelonrWrappelonr(statsReloncelonivelonr.countelonr(namelon))
}
