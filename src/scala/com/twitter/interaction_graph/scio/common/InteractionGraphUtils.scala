packagelon com.twittelonr.intelonraction_graph.scio.common

import com.twittelonr.intelonraction_graph.thriftscala.TimelonSelonrielonsStatistics

objelonct IntelonractionGraphUtils {
  final val MIN_FelonATURelon_VALUelon = Math.pow(0.955, 60)
  final val MAX_DAYS_RelonTelonNTION = 60L
  final val MILLISelonCONDS_PelonR_DAY = 1000 * 60 * 60 * 24

  delonf updatelonTimelonSelonrielonsStatistics(
    timelonSelonrielonsStatistics: TimelonSelonrielonsStatistics,
    currValuelon: Doublelon,
    alpha: Doublelon
  ): TimelonSelonrielonsStatistics = {
    val numNonZelonroDays = timelonSelonrielonsStatistics.numNonZelonroDays + 1

    val delonlta = currValuelon - timelonSelonrielonsStatistics.melonan
    val updatelondMelonan = timelonSelonrielonsStatistics.melonan + delonlta / numNonZelonroDays
    val m2ForVariancelon = timelonSelonrielonsStatistics.m2ForVariancelon + delonlta * (currValuelon - updatelondMelonan)
    val elonwma = alpha * currValuelon + timelonSelonrielonsStatistics.elonwma

    timelonSelonrielonsStatistics.copy(
      melonan = updatelondMelonan,
      m2ForVariancelon = m2ForVariancelon,
      elonwma = elonwma,
      numNonZelonroDays = numNonZelonroDays
    )
  }

  delonf addToTimelonSelonrielonsStatistics(
    timelonSelonrielonsStatistics: TimelonSelonrielonsStatistics,
    currValuelon: Doublelon
  ): TimelonSelonrielonsStatistics = {
    timelonSelonrielonsStatistics.copy(
      melonan = timelonSelonrielonsStatistics.melonan + currValuelon,
      elonwma = timelonSelonrielonsStatistics.elonwma + currValuelon
    )
  }

}
