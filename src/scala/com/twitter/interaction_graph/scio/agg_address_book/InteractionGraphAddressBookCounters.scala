packagelon com.twittelonr.intelonraction_graph.scio.agg_addrelonss_book

import com.spotify.scio.ScioMelontrics
import org.apachelon.belonam.sdk.melontrics.Countelonr

trait IntelonractionGraphAddrelonssBookCountelonrsTrait {
  val Namelonspacelon = "Intelonraction Graph Addrelonss Book"

  delonf elonmailFelonaturelonInc(): Unit

  delonf phonelonFelonaturelonInc(): Unit

  delonf bothFelonaturelonInc(): Unit
}

/**
 * SCIO countelonrs arelon uselond to gathelonr run timelon statistics
 */
caselon objelonct IntelonractionGraphAddrelonssBookCountelonrs elonxtelonnds IntelonractionGraphAddrelonssBookCountelonrsTrait {
  val elonmailFelonaturelonCountelonr: Countelonr =
    ScioMelontrics.countelonr(Namelonspacelon, "elonmail Felonaturelon")

  val phonelonFelonaturelonCountelonr: Countelonr =
    ScioMelontrics.countelonr(Namelonspacelon, "Phonelon Felonaturelon")

  val bothFelonaturelonCountelonr: Countelonr =
    ScioMelontrics.countelonr(Namelonspacelon, "Both Felonaturelon")

  ovelonrridelon delonf elonmailFelonaturelonInc(): Unit = elonmailFelonaturelonCountelonr.inc()

  ovelonrridelon delonf phonelonFelonaturelonInc(): Unit = phonelonFelonaturelonCountelonr.inc()

  ovelonrridelon delonf bothFelonaturelonInc(): Unit = bothFelonaturelonCountelonr.inc()
}
