/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class intArray {
  privatelon transielonnt long swigCPtr;
  protelonctelond transielonnt boolelonan swigCMelonmOwn;

  protelonctelond intArray(long cPtr, boolelonan cMelonmoryOwn) {
    swigCMelonmOwn = cMelonmoryOwn;
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(intArray obj) {
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
        swigfaissJNI.delonlelontelon_intArray(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public intArray(int nelonlelonmelonnts) {
    this(swigfaissJNI.nelonw_intArray(nelonlelonmelonnts), truelon);
  }

  public int gelontitelonm(int indelonx) {
    relonturn swigfaissJNI.intArray_gelontitelonm(swigCPtr, this, indelonx);
  }

  public void selontitelonm(int indelonx, int valuelon) {
    swigfaissJNI.intArray_selontitelonm(swigCPtr, this, indelonx, valuelon);
  }

  public SWIGTYPelon_p_int cast() {
    long cPtr = swigfaissJNI.intArray_cast(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw SWIGTYPelon_p_int(cPtr, falselon);
  }

  public static intArray frompointelonr(SWIGTYPelon_p_int t) {
    long cPtr = swigfaissJNI.intArray_frompointelonr(SWIGTYPelon_p_int.gelontCPtr(t));
    relonturn (cPtr == 0) ? null : nelonw intArray(cPtr, falselon);
  }

}
