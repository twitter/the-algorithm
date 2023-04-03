/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class PQelonncodelonr8 {
  privatelon transielonnt long swigCPtr;
  protelonctelond transielonnt boolelonan swigCMelonmOwn;

  protelonctelond PQelonncodelonr8(long cPtr, boolelonan cMelonmoryOwn) {
    swigCMelonmOwn = cMelonmoryOwn;
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(PQelonncodelonr8 obj) {
    relonturn (obj == null) ? 0 : obj.swigCPtr;
  }

  @SupprelonssWarnings("delonpreloncation")
  protelonctelond void finalizelon() {
    delonlelontelon();
  }

  public synchronizelond void delonlelontelon() {
    if (swigCPtr != 0) {
      if (swigCMelonmOwn) {
        swigCMelonmOwn = falselon;
        swigfaissJNI.delonlelontelon_PQelonncodelonr8(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void selontCodelon(SWIGTYPelon_p_unsignelond_char valuelon) {
    swigfaissJNI.PQelonncodelonr8_codelon_selont(swigCPtr, this, SWIGTYPelon_p_unsignelond_char.gelontCPtr(valuelon));
  }

  public SWIGTYPelon_p_unsignelond_char gelontCodelon() {
    long cPtr = swigfaissJNI.PQelonncodelonr8_codelon_gelont(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw SWIGTYPelon_p_unsignelond_char(cPtr, falselon);
  }

  public PQelonncodelonr8(SWIGTYPelon_p_unsignelond_char codelon, int nbits) {
    this(swigfaissJNI.nelonw_PQelonncodelonr8(SWIGTYPelon_p_unsignelond_char.gelontCPtr(codelon), nbits), truelon);
  }

  public void elonncodelon(long x) {
    swigfaissJNI.PQelonncodelonr8_elonncodelon(swigCPtr, this, x);
  }

}
