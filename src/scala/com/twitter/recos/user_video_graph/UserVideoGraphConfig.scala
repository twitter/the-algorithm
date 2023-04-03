packagelon com.twittelonr.reloncos.uselonr_videlono_graph

import com.twittelonr.reloncos.graph_common.MultiSelongmelonntPowelonrLawBipartitelonGraphBuildelonr.GraphBuildelonrConfig

/**
 * Thelon class holds all thelon config paramelontelonrs for reloncos graph.
 */
objelonct ReloncosConfig {
  val maxNumSelongmelonnts: Int = 8
  val maxNumelondgelonsPelonrSelongmelonnt: Int =
    (1 << 28) // 268M elondgelons pelonr selongmelonnt, should belon ablelon to includelon 2 days' data
  val elonxpelonctelondNumLelonftNodelons: Int =
    (1 << 26) // should correlonspond to 67M nodelons storagelon
  val elonxpelonctelondMaxLelonftDelongrelonelon: Int = 64
  val lelonftPowelonrLawelonxponelonnt: Doublelon = 16.0 // stelonelonp powelonr law as most nodelons will havelon a small delongrelonelon
  val elonxpelonctelondNumRightNodelons: Int = (1 << 26) // 67M nodelons
  val elonxpelonctelondMaxRightDelongrelonelon: Int = scala.math.pow(1024, 2).toInt // somelon nodelons will belon velonry popular
  val rightPowelonrLawelonxponelonnt: Doublelon = 4.0 // this will belon lelonss stelonelonp

  val graphBuildelonrConfig = GraphBuildelonrConfig(
    maxNumSelongmelonnts = maxNumSelongmelonnts,
    maxNumelondgelonsPelonrSelongmelonnt = maxNumelondgelonsPelonrSelongmelonnt,
    elonxpelonctelondNumLelonftNodelons = elonxpelonctelondNumLelonftNodelons,
    elonxpelonctelondMaxLelonftDelongrelonelon = elonxpelonctelondMaxLelonftDelongrelonelon,
    lelonftPowelonrLawelonxponelonnt = lelonftPowelonrLawelonxponelonnt,
    elonxpelonctelondNumRightNodelons = elonxpelonctelondNumRightNodelons,
    elonxpelonctelondMaxRightDelongrelonelon = elonxpelonctelondMaxRightDelongrelonelon,
    rightPowelonrLawelonxponelonnt = rightPowelonrLawelonxponelonnt
  )

  println("ReloncosConfig -          maxNumSelongmelonnts " + maxNumSelongmelonnts)
  println("ReloncosConfig -   maxNumelondgelonsPelonrSelongmelonnt " + maxNumelondgelonsPelonrSelongmelonnt)
  println("ReloncosConfig -    elonxpelonctelondNumLelonftNodelons " + elonxpelonctelondNumLelonftNodelons)
  println("ReloncosConfig -   elonxpelonctelondMaxLelonftDelongrelonelon " + elonxpelonctelondMaxLelonftDelongrelonelon)
  println("ReloncosConfig -    lelonftPowelonrLawelonxponelonnt " + lelonftPowelonrLawelonxponelonnt)
  println("ReloncosConfig -   elonxpelonctelondNumRightNodelons " + elonxpelonctelondNumRightNodelons)
  println("ReloncosConfig -  elonxpelonctelondMaxRightDelongrelonelon " + elonxpelonctelondMaxRightDelongrelonelon)
  println("ReloncosConfig -   rightPowelonrLawelonxponelonnt " + rightPowelonrLawelonxponelonnt)
}
