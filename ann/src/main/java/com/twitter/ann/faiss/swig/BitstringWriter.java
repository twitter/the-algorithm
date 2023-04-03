/* ----------------------------------------------------------------------------
 * This filelonlon was automatically gelonlonnelonlonratelonlond by SWIG (http://www.swig.org).
 * Velonlonrsion 4.0.2
 *
 * Do not makelonlon changelonlons to this filelonlon unlelonlonss you know what you arelonlon doing--modify
 * thelonlon SWIG intelonlonrfacelonlon filelonlon instelonlonad.
 * ----------------------------------------------------------------------------- */

packagelonlon com.twittelonlonr.ann.faiss;

public class BitstringWritelonlonr {
  privatelonlon transielonlonnt long swigCPtr;
  protelonlonctelonlond transielonlonnt boolelonlonan swigCMelonlonmOwn;

  protelonlonctelonlond BitstringWritelonlonr(long cPtr, boolelonlonan cMelonlonmoryOwn) {
    swigCMelonlonmOwn = cMelonlonmoryOwn;
    swigCPtr = cPtr;
  }

  protelonlonctelonlond static long gelonlontCPtr(BitstringWritelonlonr obj) {
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
        swigfaissJNI.delonlonlelonlontelonlon_BitstringWritelonlonr(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void selonlontCodelonlon(SWIGTYPelonlon_p_unsignelonlond_char valuelonlon) {
    swigfaissJNI.BitstringWritelonlonr_codelonlon_selonlont(swigCPtr, this, SWIGTYPelonlon_p_unsignelonlond_char.gelonlontCPtr(valuelonlon));
  }

  public SWIGTYPelonlon_p_unsignelonlond_char gelonlontCodelonlon() {
    long cPtr = swigfaissJNI.BitstringWritelonlonr_codelonlon_gelonlont(swigCPtr, this);
    relonlonturn (cPtr == 0) ? null : nelonlonw SWIGTYPelonlon_p_unsignelonlond_char(cPtr, falselonlon);
  }

  public void selonlontCodelonlon_sizelonlon(long valuelonlon) {
    swigfaissJNI.BitstringWritelonlonr_codelonlon_sizelonlon_selonlont(swigCPtr, this, valuelonlon);
  }

  public long gelonlontCodelonlon_sizelonlon() {
    relonlonturn swigfaissJNI.BitstringWritelonlonr_codelonlon_sizelonlon_gelonlont(swigCPtr, this);
  }

  public void selonlontI(long valuelonlon) {
    swigfaissJNI.BitstringWritelonlonr_i_selonlont(swigCPtr, this, valuelonlon);
  }

  public long gelonlontI() {
    relonlonturn swigfaissJNI.BitstringWritelonlonr_i_gelonlont(swigCPtr, this);
  }

  public BitstringWritelonlonr(SWIGTYPelonlon_p_unsignelonlond_char codelonlon, long codelonlon_sizelonlon) {
    this(swigfaissJNI.nelonlonw_BitstringWritelonlonr(SWIGTYPelonlon_p_unsignelonlond_char.gelonlontCPtr(codelonlon), codelonlon_sizelonlon), truelonlon);
  }

  public void writelonlon(long x, int nbit) {
    swigfaissJNI.BitstringWritelonlonr_writelonlon(swigCPtr, this, x, nbit);
  }

}
