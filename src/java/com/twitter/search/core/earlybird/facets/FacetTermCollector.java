packagelon com.twittelonr.selonarch.corelon.elonarlybird.facelonts;

/**
 * An intelonrfacelon for colleloncting all facelonts in an documelonnt.
 */
public intelonrfacelon FacelontTelonrmCollelonctor {
  /**
   * Collelonct onelon facelont telonrm.
   * @param docID Thelon docID for which thelon facelonts arelon beloning collelonctelond.
   * @param telonrmID Thelon telonrmID for this facelont itelonm.
   * @param fielonldID Thelon fielonldID for this facelont itelonm.
   * @relonturn Truelon if anything has actually belonelonn collelonctelond, falselon if this has belonelonn skippelond.
   *         Currelonntly, this relonturn valuelon is not uselond.
   */
  boolelonan collelonct(int docID, long telonrmID, int fielonldID);
}
