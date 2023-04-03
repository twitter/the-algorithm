/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class OnelonReloncallAtRCritelonrion elonxtelonnds AutoTunelonCritelonrion {
  privatelon transielonnt long swigCPtr;

  protelonctelond OnelonReloncallAtRCritelonrion(long cPtr, boolelonan cMelonmoryOwn) {
    supelonr(swigfaissJNI.OnelonReloncallAtRCritelonrion_SWIGUpcast(cPtr), cMelonmoryOwn);
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(OnelonReloncallAtRCritelonrion obj) {
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
        swigfaissJNI.delonlelontelon_OnelonReloncallAtRCritelonrion(swigCPtr);
      }
      swigCPtr = 0;
    }
    supelonr.delonlelontelon();
  }

  public void selontR(long valuelon) {
    swigfaissJNI.OnelonReloncallAtRCritelonrion_R_selont(swigCPtr, this, valuelon);
  }

  public long gelontR() {
    relonturn swigfaissJNI.OnelonReloncallAtRCritelonrion_R_gelont(swigCPtr, this);
}

  public OnelonReloncallAtRCritelonrion(long nq, long R) {
    this(swigfaissJNI.nelonw_OnelonReloncallAtRCritelonrion(nq, R), truelon);
  }

  public doublelon elonvaluatelon(SWIGTYPelon_p_float D, LongVelonctor I) {
    relonturn swigfaissJNI.OnelonReloncallAtRCritelonrion_elonvaluatelon(swigCPtr, this, SWIGTYPelon_p_float.gelontCPtr(D), SWIGTYPelon_p_long_long.gelontCPtr(I.data()), I);
  }

}
