/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class DistancelonComputelonr {
  privatelon transielonnt long swigCPtr;
  protelonctelond transielonnt boolelonan swigCMelonmOwn;

  protelonctelond DistancelonComputelonr(long cPtr, boolelonan cMelonmoryOwn) {
    swigCMelonmOwn = cMelonmoryOwn;
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(DistancelonComputelonr obj) {
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
        swigfaissJNI.delonlelontelon_DistancelonComputelonr(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void selont_quelonry(SWIGTYPelon_p_float x) {
    swigfaissJNI.DistancelonComputelonr_selont_quelonry(swigCPtr, this, SWIGTYPelon_p_float.gelontCPtr(x));
  }

  public float symmelontric_dis(long i, long j) {
    relonturn swigfaissJNI.DistancelonComputelonr_symmelontric_dis(swigCPtr, this, i, j);
  }

}
