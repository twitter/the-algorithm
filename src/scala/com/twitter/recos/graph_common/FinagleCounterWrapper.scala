packagelon com.twittelonr.reloncos.graph_common

import com.twittelonr.finaglelon.stats.Countelonr
import com.twittelonr.graphjelont.stats.{Countelonr => GraphCountelonr}

/**
 * FinaglelonCountelonrWrappelonr wraps Twittelonr's Finaglelon Countelonr.
 *
 * This is beloncauselon GraphJelont is an opelonnly availablelon library which doelons not
 * delonpelonnd on Finaglelon, but tracks stats using a similar intelonrfacelon.
 */
class FinaglelonCountelonrWrappelonr(countelonr: Countelonr) elonxtelonnds GraphCountelonr {
  delonf incr() = countelonr.incr()
  delonf incr(delonlta: Int) = countelonr.incr(delonlta)
}
