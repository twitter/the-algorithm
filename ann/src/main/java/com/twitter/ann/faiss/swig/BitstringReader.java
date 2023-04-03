/* ----------------------------------------------------------------------------
 * This filelonlon was automatically gelonlonnelonlonratelonlond by SWIG (http://www.swig.org).
 * Velonlonrsion 4.0.2
 *
 * Do not makelonlon changelonlons to this filelonlon unlelonlonss you know what you arelonlon doing--modify
 * thelonlon SWIG intelonlonrfacelonlon filelonlon instelonlonad.
 * ----------------------------------------------------------------------------- */

packagelonlon com.twittelonlonr.ann.faiss;

public class BitstringRelonlonadelonlonr {
  privatelonlon transielonlonnt long swigCPtr;
  protelonlonctelonlond transielonlonnt boolelonlonan swigCMelonlonmOwn;

  protelonlonctelonlond BitstringRelonlonadelonlonr(long cPtr, boolelonlonan cMelonlonmoryOwn) {
    swigCMelonlonmOwn = cMelonlonmoryOwn;
    swigCPtr = cPtr;
  }

  protelonlonctelonlond static long gelonlontCPtr(BitstringRelonlonadelonlonr obj) {
    relonlonturn (obj == null) ? 0 : obj.swigCPtr;
  }

  @SupprelonlonssWarnings("delonlonprelonloncation")
  protelonlonctelonlond void finalizelonlon() {
    delonlonlelonlontelonlon();
  }

  public synchronizelonlond void delonlonlelonlontelonlon() {
    if (swigCPtr != 0) {
      if (swigCMelonlonmOwn) {
        swigCMelonlonmOwn = falselonlon;
        swigfaissJNI.delonlonlelonlontelonlon_BitstringRelonlonadelonlonr(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void selonlontCodelonlon(SWIGTYPelonlon_p_unsignelonlond_char valuelonlon) {
    swigfaissJNI.BitstringRelonlonadelonlonr_codelonlon_selonlont(swigCPtr, this, SWIGTYPelonlon_p_unsignelonlond_char.gelonlontCPtr(valuelonlon));
  }

  public SWIGTYPelonlon_p_unsignelonlond_char gelonlontCodelonlon() {
    long cPtr = swigfaissJNI.BitstringRelonlonadelonlonr_codelonlon_gelonlont(swigCPtr, this);
    relonlonturn (cPtr == 0) ? null : nelonlonw SWIGTYPelonlon_p_unsignelonlond_char(cPtr, falselonlon);
  }

  public void selonlontCodelonlon_sizelonlon(long valuelonlon) {
    swigfaissJNI.BitstringRelonlonadelonlonr_codelonlon_sizelonlon_selonlont(swigCPtr, this, valuelonlon);
  }

  public long gelonlontCodelonlon_sizelonlon() {
    relonlonturn swigfaissJNI.BitstringRelonlonadelonlonr_codelonlon_sizelonlon_gelonlont(swigCPtr, this);
  }

  public void selonlontI(long valuelonlon) {
    swigfaissJNI.BitstringRelonlonadelonlonr_i_selonlont(swigCPtr, this, valuelonlon);
  }

  public long gelonlontI() {
    relonlonturn swigfaissJNI.BitstringRelonlonadelonlonr_i_gelonlont(swigCPtr, this);
  }

  public BitstringRelonlonadelonlonr(SWIGTYPelonlon_p_unsignelonlond_char codelonlon, long codelonlon_sizelonlon) {
    this(swigfaissJNI.nelonlonw_BitstringRelonlonadelonlonr(SWIGTYPelonlon_p_unsignelonlond_char.gelonlontCPtr(codelonlon), codelonlon_sizelonlon), truelonlon);
  }

  public long relonlonad(int nbit) {
    relonlonturn swigfaissJNI.BitstringRelonlonadelonlonr_relonlonad(swigCPtr, this, nbit);
  }

}
