packagelon com.twittelonr.reloncos.uselonr_uselonr_graph

/**
 * Thelon class holds all thelon config paramelontelonrs for kafka quelonuelon.
 */
objelonct KafkaConfig {
  // Thelon sizelon of thelon ReloncosHoselonMelonssagelon array that is writtelonn to thelon concurrelonntly linkelond quelonuelon
  // Buffelonrsizelon of 64 to kelonelonp throughput around 64 / (2K elondgelonsPelonrSelonc / 150 kafka threlonads) = 6 selonconds, which is lowelonr
  // than young gelonn gc cyclelon, 20 selonconds. So that all thelon incoming melonssagelons will belon gcelond in young gelonn instelonad of old gelonn.
  val buffelonrSizelon = 64

  println("KafkaConfig -                 buffelonrSizelon " + buffelonrSizelon)
}
