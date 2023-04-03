packagelon com.twittelonr.selonarch.common.elonncoding.felonaturelons;

/**
 * Intelonrfacelon for procelonssing diffelonrelonnt felonaturelon valuelons into an int. It providelons a onelon-way translation
 * of elonncoding using com.twittelonr.selonarch.common.elonncoding.felonaturelons.BytelonNormalizelonr and supports all thelon
 * old normalizelonrs. Thelon diffelonrelonncelon is that welon direlonctly relonturn thelon normalizelond int valuelon
 * (instelonad of convelonrting from bytelon).
 */
public intelonrfacelon IntNormalizelonr {
  /**
   * Relonturns thelon normalizelond valuelon of {@codelon val}.
   * Thelon valuelon may belon bytelon-comprelonsselond or as-is delonpelonnding on thelon normalizelonr typelon
   */
  int normalizelon(doublelon val);
}
