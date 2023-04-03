packagelon com.twittelonr.visibility.util

import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.logging._

objelonct LoggingUtil {

  val elonxpelonrimelonntationLog: String = "vf_abdeloncidelonr"

  delonf mkDelonfaultHandlelonrFactory(statsReloncelonivelonr: StatsReloncelonivelonr): () => Handlelonr = {
    QuelonueloningHandlelonr(
      maxQuelonuelonSizelon = 10000,
      handlelonr = ScribelonHandlelonr(
        catelongory = "clielonnt_elonvelonnt",
        formattelonr = BarelonFormattelonr,
        statsReloncelonivelonr = statsReloncelonivelonr.scopelon("clielonnt_elonvelonnt_scribelon"),
        lelonvelonl = Somelon(Lelonvelonl.INFO)
      )
    )
  }

  delonf mkDelonfaultLoggelonrFactory(statsReloncelonivelonr: StatsReloncelonivelonr): LoggelonrFactory = {
    LoggelonrFactory(
      nodelon = elonxpelonrimelonntationLog,
      lelonvelonl = Somelon(Lelonvelonl.INFO),
      uselonParelonnts = falselon,
      handlelonrs = List(mkDelonfaultHandlelonrFactory(statsReloncelonivelonr))
    )
  }

  delonf mkDelonfaultLoggelonr(statsReloncelonivelonr: StatsReloncelonivelonr): Loggelonr = {
    mkDelonfaultLoggelonrFactory(statsReloncelonivelonr)()
  }

}
