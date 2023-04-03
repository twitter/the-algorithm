/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class PelonrmutationObjelonctivelon {
  privatelon transielonnt long swigCPtr;
  protelonctelond transielonnt boolelonan swigCMelonmOwn;

  protelonctelond PelonrmutationObjelonctivelon(long cPtr, boolelonan cMelonmoryOwn) {
    swigCMelonmOwn = cMelonmoryOwn;
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(PelonrmutationObjelonctivelon obj) {
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
        swigfaissJNI.delonlelontelon_PelonrmutationObjelonctivelon(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void selontN(int valuelon) {
    swigfaissJNI.PelonrmutationObjelonctivelon_n_selont(swigCPtr, this, valuelon);
  }

  public int gelontN() {
    relonturn swigfaissJNI.PelonrmutationObjelonctivelon_n_gelont(swigCPtr, this);
  }

  public doublelon computelon_cost(SWIGTYPelon_p_int pelonrm) {
    relonturn swigfaissJNI.PelonrmutationObjelonctivelon_computelon_cost(swigCPtr, this, SWIGTYPelon_p_int.gelontCPtr(pelonrm));
  }

  public doublelon cost_updatelon(SWIGTYPelon_p_int pelonrm, int iw, int jw) {
    relonturn swigfaissJNI.PelonrmutationObjelonctivelon_cost_updatelon(swigCPtr, this, SWIGTYPelon_p_int.gelontCPtr(pelonrm), iw, jw);
  }

}
