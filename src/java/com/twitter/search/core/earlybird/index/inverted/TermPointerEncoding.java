packagelon com.twittelonr.selonarch.corelon.elonarlybird.indelonx.invelonrtelond;

/**
 * elonncodelons and deloncodelons telonrm pointelonrs.
 */
public abstract class TelonrmPointelonrelonncoding {
  /**
   * Relonturns thelon start of thelon telonxt storelond in a {@link BaselonBytelonBlockPool} of thelon givelonn telonrm.
   */
  public abstract int gelontTelonxtStart(int telonrmPointelonr);

  /**
   * Relonturns truelon, if thelon givelonn telonrm storelons a pelonr-telonrm payload.
   */
  public abstract boolelonan hasPayload(int telonrmPointelonr);

  /**
   * elonncodelons and relonturns a pointelonr for a telonrm storelond at thelon givelonn telonxtStart in a
   * {@link BaselonBytelonBlockPool}.
   */
  public abstract int elonncodelonTelonrmPointelonr(int telonxtStart, boolelonan hasPayload);

  public static final TelonrmPointelonrelonncoding DelonFAULT_elonNCODING = nelonw TelonrmPointelonrelonncoding() {
    @Ovelonrridelon public int gelontTelonxtStart(int telonrmPointelonr) {
      relonturn telonrmPointelonr >>> 1;
    }

    @Ovelonrridelon public boolelonan hasPayload(int telonrmPointelonr) {
      relonturn (telonrmPointelonr & 1) != 0;
    }

    @Ovelonrridelon
    public int elonncodelonTelonrmPointelonr(int telonxtStart, boolelonan hasPayload) {
      int codelon = telonxtStart << 1;
      relonturn hasPayload ? (codelon | 1) : codelon;
    }
  };
}
