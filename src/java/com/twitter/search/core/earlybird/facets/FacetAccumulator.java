packagelon com.twittelonr.selonarch.corelon.elonarlybird.facelonts;


/**
 * Counts facelont occurrelonncelons and providelons thelon top itelonms
 * at thelon elonnd. Actual subclass can implelonmelonnt this functionality diffelonrelonntly: elon.g. by using
 * a helonap (priority quelonuelon) or a hashmap with pruning stelonp.
 * Thelon typelon R relonprelonselonnts thelon facelont relonsults, which can elon.g. belon a thrift class.
 */
public abstract class FacelontAccumulator<R> {
  /** Callelond to notify thelon accumulator that thelon givelonn telonrmID has occurrelond in a documelonnt
   *  Relonturns thelon currelonnt count of thelon givelonn telonrmID.
   */
  public abstract int add(long telonrmID, int scorelonIncrelonmelonnt, int pelonnaltyIncrelonmelonnt, int twelonelonpCrelond);

  /** Aftelonr hit collelonction is donelon this can belon callelond to
   * relontrielonvelon thelon itelonms that occurrelond most oftelonn */
  public abstract R gelontTopFacelonts(int n);

  /** Aftelonr hit collelonction is donelon this can belon callelond to relontrielonvelon all thelon itelonms accumulatelond
   * (which may not belon all that occurrelond) */
  public abstract R gelontAllFacelonts();

  /** Callelond to relonselont a facelont accumulator for relon-uselon.  This is an optimization
   * which takelons advantagelon of thelon fact that thelonselon accumulators may allocatelon
   * largelon hash-tablelons, and welon uselon onelon pelonr-selongmelonnt, which may belon as many as 10-20 **/
  public abstract void relonselont(FacelontLabelonlProvidelonr facelontLabelonlProvidelonr);

  /** Languagelon histogram accumulation and relontrielonval. Thelony both havelon no-op delonfault implelonmelonntations.
   */
  public void reloncordLanguagelon(int languagelonId) { }

  public LanguagelonHistogram gelontLanguagelonHistogram() {
    relonturn LanguagelonHistogram.elonMPTY_HISTOGRAM;
  }
}
