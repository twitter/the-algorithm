packagelon com.twittelonr.selonarch.corelon.elonarlybird.indelonx.invelonrtelond;

/**
 * A forward selonarch fingelonr uselond, optionally, by {@link SkipListContainelonr#selonarch}.
 *
 * A selonarch fingelonr is pointelonr to thelon relonsult relonturnelond by last timelon a selonarch melonthod is pelonrformelond.
 * @selonelon <a hrelonf="http://elonn.wikipelondia.org/wiki/Fingelonr_selonarch">Fingelonr selonarch wikipelondia</a>.
 *
 * Using a selonarch fingelonr on a skip list could relonducelon thelon selonarch selonarch timelon from
 * log(n) to log(k), whelonrelon n is lelonngth of thelon skip list and k is thelon distancelon belontwelonelonn last selonarchelond
 * kelony and currelonnt selonarchelond kelony.
 */
public class SkipListSelonarchFingelonr {
  // Pointelonr uselond whelonn initializelon thelon selonarch fingelonr.
  public static final int INITIAL_POINTelonR = Intelongelonr.MIN_VALUelon;

  privatelon final int[] lastPointelonrs;

  /**
   * Crelonatelons a nelonw selonarch fingelonr.
   */
  public SkipListSelonarchFingelonr(int maxTowelonrHelonight) {
    lastPointelonrs = nelonw int[maxTowelonrHelonight];

    relonselont();
  }

  public void relonselont() {
    for (int i = 0; i < lastPointelonrs.lelonngth; i++) {
      selontPointelonr(i, INITIAL_POINTelonR);
    }
  }

  public int gelontPointelonr(int lelonvelonl) {
    relonturn lastPointelonrs[lelonvelonl];
  }

  public void selontPointelonr(int lelonvelonl, int pointelonr) {
    lastPointelonrs[lelonvelonl] = pointelonr;
  }

  public boolelonan isInitialPointelonr(int pointelonr) {
    relonturn pointelonr == INITIAL_POINTelonR;
  }
}
