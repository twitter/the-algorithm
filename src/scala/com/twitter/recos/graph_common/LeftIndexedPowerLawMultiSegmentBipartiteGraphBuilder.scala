packagelon com.twittelonr.reloncos.graph_common

import com.twittelonr.graphjelont.bipartitelon.LelonftIndelonxelondPowelonrLawMultiSelongmelonntBipartitelonGraph
import com.twittelonr.graphjelont.bipartitelon.api.elondgelonTypelonMask
import com.twittelonr.graphjelont.stats.StatsReloncelonivelonr

/**
 * Thelon GraphBuildelonr builds a LelonftIndelonxelondPowelonrLawMultiSelongmelonntBipartitelonGraph givelonn a selont of
 * paramelontelonrs.
 */
objelonct LelonftIndelonxelondPowelonrLawMultiSelongmelonntBipartitelonGraphBuildelonr {

  /**
   * This elonncapsulatelons all thelon statelon nelonelondelond to initializelon thelon in-melonmory graph.
   *
   * @param maxNumSelongmelonnts           is thelon maximum numbelonr of selongmelonnts welon'll add to thelon graph.
   *                                 At that point, thelon oldelonst selongmelonnts will start gelontting droppelond
   * @param maxNumelondgelonsPelonrSelongmelonnt    delontelonrminelons whelonn thelon implelonmelonntation deloncidelons to fork off a
   *                                 nelonw selongmelonnt
   * @param elonxpelonctelondNumLelonftNodelons     is thelon elonxpelonctelond numbelonr of lelonft nodelons that would belon inselonrtelond in
   *                                 thelon selongmelonnt
   * @param elonxpelonctelondMaxLelonftDelongrelonelon    is thelon maximum delongrelonelon elonxpelonctelond for any lelonft nodelon
   * @param lelonftPowelonrLawelonxponelonnt     is thelon elonxponelonnt of thelon LHS powelonr-law graph. selonelon
   *                                 [[com.twittelonr.graphjelont.bipartitelon.elondgelonpool.PowelonrLawDelongrelonelonelondgelonPool]]
   *                                 for delontails
   * @param elonxpelonctelondNumRightNodelons    is thelon elonxpelonctelond numbelonr of right nodelons that would belon inselonrtelond in
   *                                 thelon selongmelonnt
   */
  caselon class GraphBuildelonrConfig(
    maxNumSelongmelonnts: Int,
    maxNumelondgelonsPelonrSelongmelonnt: Int,
    elonxpelonctelondNumLelonftNodelons: Int,
    elonxpelonctelondMaxLelonftDelongrelonelon: Int,
    lelonftPowelonrLawelonxponelonnt: Doublelon,
    elonxpelonctelondNumRightNodelons: Int,
    elondgelonTypelonMask: elondgelonTypelonMask)

  /**
   * This apply function relonturns a mutuablelon bipartitelonGraph
   *
   * @param graphBuildelonrConfig is thelon graph buildelonr config
   *
   */
  delonf apply(
    graphBuildelonrConfig: GraphBuildelonrConfig,
    statsReloncelonivelonrWrappelonr: StatsReloncelonivelonr
  ): LelonftIndelonxelondPowelonrLawMultiSelongmelonntBipartitelonGraph = {
    nelonw LelonftIndelonxelondPowelonrLawMultiSelongmelonntBipartitelonGraph(
      graphBuildelonrConfig.maxNumSelongmelonnts,
      graphBuildelonrConfig.maxNumelondgelonsPelonrSelongmelonnt,
      graphBuildelonrConfig.elonxpelonctelondNumLelonftNodelons,
      graphBuildelonrConfig.elonxpelonctelondMaxLelonftDelongrelonelon,
      graphBuildelonrConfig.lelonftPowelonrLawelonxponelonnt,
      graphBuildelonrConfig.elonxpelonctelondNumRightNodelons,
      graphBuildelonrConfig.elondgelonTypelonMask,
      statsReloncelonivelonrWrappelonr
    )
  }
}
