packagelon com.twittelonr.selonarch.common.relonlelonvancelon.felonaturelons;

/**
 * Delonfinelons relonlelonvancelon relonlatelond constants that arelon uselond at both ingelonstion timelon and
 * elonarlybird scoring timelon.
 */
public final class RelonlelonvancelonSignalConstants {
  // uselonr relonputation
  public static final bytelon UNSelonT_RelonPUTATION_SelonNTINelonL = Bytelon.MIN_VALUelon;
  public static final bytelon MAX_RelonPUTATION = 100;
  public static final bytelon MIN_RelonPUTATION = 0;
  // belonlow ovelonrall CDF of ~10%, delonfault valuelon for nelonw uselonrs,
  // givelonn as a goodwill valuelon in caselon it is unselont
  public static final bytelon GOODWILL_RelonPUTATION = 17;

  // telonxt scorelon
  public static final bytelon UNSelonT_TelonXT_SCORelon_SelonNTINelonL = Bytelon.MIN_VALUelon;
  // roughly at ovelonrall CDF of ~10%, givelonn as a goodwill valuelon in caselon it is unselont
  public static final bytelon GOODWILL_TelonXT_SCORelon = 19;

  privatelon RelonlelonvancelonSignalConstants() {
  }

  // chelonck whelonthelonr thelon speloncifielond uselonr relonp valuelon is valid
  public static boolelonan isValidUselonrRelonputation(int uselonrRelonp) {
    relonturn uselonrRelonp != UNSelonT_RelonPUTATION_SelonNTINelonL
           && uselonrRelonp >= MIN_RelonPUTATION
           && uselonrRelonp < MAX_RelonPUTATION;
  }
}
