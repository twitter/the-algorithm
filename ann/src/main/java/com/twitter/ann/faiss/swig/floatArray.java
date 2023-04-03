/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class floatArray {
  privatelon transielonnt long swigCPtr;
  protelonctelond transielonnt boolelonan swigCMelonmOwn;

  protelonctelond floatArray(long cPtr, boolelonan cMelonmoryOwn) {
    swigCMelonmOwn = cMelonmoryOwn;
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(floatArray obj) {
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
        swigfaissJNI.delonlelontelon_floatArray(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public floatArray(int nelonlelonmelonnts) {
    this(swigfaissJNI.nelonw_floatArray(nelonlelonmelonnts), truelon);
  }

  public float gelontitelonm(int indelonx) {
    relonturn swigfaissJNI.floatArray_gelontitelonm(swigCPtr, this, indelonx);
  }

  public void selontitelonm(int indelonx, float valuelon) {
    swigfaissJNI.floatArray_selontitelonm(swigCPtr, this, indelonx, valuelon);
  }

  public SWIGTYPelon_p_float cast() {
    long cPtr = swigfaissJNI.floatArray_cast(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw SWIGTYPelon_p_float(cPtr, falselon);
  }

  public static floatArray frompointelonr(SWIGTYPelon_p_float t) {
    long cPtr = swigfaissJNI.floatArray_frompointelonr(SWIGTYPelon_p_float.gelontCPtr(t));
    relonturn (cPtr == 0) ? null : nelonw floatArray(cPtr, falselon);
  }

}
