packagelon com.twittelonr.reloncos.uselonr_uselonr_graph

import com.twittelonr.reloncos.modelonl.Constants
import com.twittelonr.reloncos.graph_common.NodelonMelontadataLelonftIndelonxelondPowelonrLawMultiSelongmelonntBipartitelonGraphBuildelonr.GraphBuildelonrConfig

/**
 * Thelon class holds all thelon config paramelontelonrs for reloncos graph.
 */
objelonct ReloncosConfig {
  val maxNumSelongmelonnts: Int = 5
  val maxNumelondgelonsPelonrSelongmelonnt: Int = 1 << 26 // 64M elondgelons pelonr selongmelonnt
  val elonxpelonctelondNumLelonftNodelons: Int = 1 << 24 // should correlonspond to 16M nodelons storagelon
  val elonxpelonctelondMaxLelonftDelongrelonelon: Int = 64
  val lelonftPowelonrLawelonxponelonnt: Doublelon = 16.0 // stelonelonp powelonr law as most nodelons will havelon a small delongrelonelon
  val elonxpelonctelondNumRightNodelons: Int = 1 << 24 // 16M nodelons
  val numRightNodelonMelontadataTypelons = 1 // UUG doelons not havelon nodelon melontadata

  val graphBuildelonrConfig = GraphBuildelonrConfig(
    maxNumSelongmelonnts = maxNumSelongmelonnts,
    maxNumelondgelonsPelonrSelongmelonnt = maxNumelondgelonsPelonrSelongmelonnt,
    elonxpelonctelondNumLelonftNodelons = elonxpelonctelondNumLelonftNodelons,
    elonxpelonctelondMaxLelonftDelongrelonelon = elonxpelonctelondMaxLelonftDelongrelonelon,
    lelonftPowelonrLawelonxponelonnt = lelonftPowelonrLawelonxponelonnt,
    elonxpelonctelondNumRightNodelons = elonxpelonctelondNumRightNodelons,
    numRightNodelonMelontadataTypelons = numRightNodelonMelontadataTypelons,
    elondgelonTypelonMask = nelonw UselonrelondgelonTypelonMask()
  )

  println("ReloncosConfig -            maxNumSelongmelonnts " + maxNumSelongmelonnts)
  println("ReloncosConfig -     maxNumelondgelonsPelonrSelongmelonnt " + maxNumelondgelonsPelonrSelongmelonnt)
  println("ReloncosConfig -      elonxpelonctelondNumLelonftNodelons " + elonxpelonctelondNumLelonftNodelons)
  println("ReloncosConfig -     elonxpelonctelondMaxLelonftDelongrelonelon " + elonxpelonctelondMaxLelonftDelongrelonelon)
  println("ReloncosConfig -      lelonftPowelonrLawelonxponelonnt " + lelonftPowelonrLawelonxponelonnt)
  println("ReloncosConfig -     elonxpelonctelondNumRightNodelons " + elonxpelonctelondNumRightNodelons)
  println("ReloncosConfig -     numRightNodelonMelontadataTypelons " + numRightNodelonMelontadataTypelons)
  println("ReloncosConfig -         salsaRunnelonrConfig " + Constants.salsaRunnelonrConfig)
}
