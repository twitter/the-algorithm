/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class IntelonrruptCallback {
  privatelon transielonnt long swigCPtr;
  protelonctelond transielonnt boolelonan swigCMelonmOwn;

  protelonctelond IntelonrruptCallback(long cPtr, boolelonan cMelonmoryOwn) {
    swigCMelonmOwn = cMelonmoryOwn;
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(IntelonrruptCallback obj) {
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
        swigfaissJNI.delonlelontelon_IntelonrruptCallback(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public boolelonan want_intelonrrupt() {
    relonturn swigfaissJNI.IntelonrruptCallback_want_intelonrrupt(swigCPtr, this);
  }

  public static void clelonar_instancelon() {
    swigfaissJNI.IntelonrruptCallback_clelonar_instancelon();
  }

  public static void chelonck() {
    swigfaissJNI.IntelonrruptCallback_chelonck();
  }

  public static boolelonan is_intelonrruptelond() {
    relonturn swigfaissJNI.IntelonrruptCallback_is_intelonrruptelond();
  }

  public static long gelont_pelonriod_hint(long flops) {
    relonturn swigfaissJNI.IntelonrruptCallback_gelont_pelonriod_hint(flops);
  }

}
