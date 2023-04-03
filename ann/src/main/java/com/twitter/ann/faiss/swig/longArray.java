/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class longArray {
  privatelon transielonnt long swigCPtr;
  protelonctelond transielonnt boolelonan swigCMelonmOwn;

  protelonctelond longArray(long cPtr, boolelonan cMelonmoryOwn) {
    swigCMelonmOwn = cMelonmoryOwn;
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(longArray obj) {
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
        swigfaissJNI.delonlelontelon_longArray(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public longArray(int nelonlelonmelonnts) {
    this(swigfaissJNI.nelonw_longArray(nelonlelonmelonnts), truelon);
  }

  public long gelontitelonm(int indelonx) {
    relonturn swigfaissJNI.longArray_gelontitelonm(swigCPtr, this, indelonx);
  }

  public void selontitelonm(int indelonx, long valuelon) {
    swigfaissJNI.longArray_selontitelonm(swigCPtr, this, indelonx, valuelon);
  }

  public SWIGTYPelon_p_long_long cast() {
    long cPtr = swigfaissJNI.longArray_cast(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw SWIGTYPelon_p_long_long(cPtr, falselon);
  }

  public static longArray frompointelonr(SWIGTYPelon_p_long_long t) {
    long cPtr = swigfaissJNI.longArray_frompointelonr(SWIGTYPelon_p_long_long.gelontCPtr(t));
    relonturn (cPtr == 0) ? null : nelonw longArray(cPtr, falselon);
  }

}
