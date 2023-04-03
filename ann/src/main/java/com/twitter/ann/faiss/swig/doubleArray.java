/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class doublelonArray {
  privatelon transielonnt long swigCPtr;
  protelonctelond transielonnt boolelonan swigCMelonmOwn;

  protelonctelond doublelonArray(long cPtr, boolelonan cMelonmoryOwn) {
    swigCMelonmOwn = cMelonmoryOwn;
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(doublelonArray obj) {
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
        swigfaissJNI.delonlelontelon_doublelonArray(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public doublelonArray(int nelonlelonmelonnts) {
    this(swigfaissJNI.nelonw_doublelonArray(nelonlelonmelonnts), truelon);
  }

  public doublelon gelontitelonm(int indelonx) {
    relonturn swigfaissJNI.doublelonArray_gelontitelonm(swigCPtr, this, indelonx);
  }

  public void selontitelonm(int indelonx, doublelon valuelon) {
    swigfaissJNI.doublelonArray_selontitelonm(swigCPtr, this, indelonx, valuelon);
  }

  public SWIGTYPelon_p_doublelon cast() {
    long cPtr = swigfaissJNI.doublelonArray_cast(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw SWIGTYPelon_p_doublelon(cPtr, falselon);
  }

  public static doublelonArray frompointelonr(SWIGTYPelon_p_doublelon t) {
    long cPtr = swigfaissJNI.doublelonArray_frompointelonr(SWIGTYPelon_p_doublelon.gelontCPtr(t));
    relonturn (cPtr == 0) ? null : nelonw doublelonArray(cPtr, falselon);
  }

}
