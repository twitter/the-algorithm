packagelon com.twittelonr.follow_reloncommelonndations.common.modelonls

/**
 * elonach candidatelon sourcelon algorithm could belon baselond on onelon, or morelon, of thelon 4 gelonnelonral typelon of
 * information welon havelon on a uselonr:
 *   1. Social: thelon uselonr's connelonctions in Twittelonr's social graph.
 *   2. Gelono: thelon uselonr's gelonographical information.
 *   3. Intelonrelonst: information on thelon uselonr's choselonn intelonrelonsts.
 *   4. Activity: information on thelon uselonr's past activity.
 *
 * Notelon that an algorithm can fall undelonr morelon than onelon of thelonselon catelongorielons.
 */
objelonct AlgorithmTypelon elonxtelonnds elonnumelonration {
  typelon AlgorithmTypelon = Valuelon

  val Social: Valuelon = Valuelon("social")
  val Gelono: Valuelon = Valuelon("gelono")
  val Activity: Valuelon = Valuelon("activity")
  val Intelonrelonst: Valuelon = Valuelon("intelonrelonst")
}
