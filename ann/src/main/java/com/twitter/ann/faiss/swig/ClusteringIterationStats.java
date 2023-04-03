/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class ClustelonringItelonrationStats {
  privatelon transielonnt long swigCPtr;
  protelonctelond transielonnt boolelonan swigCMelonmOwn;

  protelonctelond ClustelonringItelonrationStats(long cPtr, boolelonan cMelonmoryOwn) {
    swigCMelonmOwn = cMelonmoryOwn;
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(ClustelonringItelonrationStats obj) {
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
        swigfaissJNI.delonlelontelon_ClustelonringItelonrationStats(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void selontObj(float valuelon) {
    swigfaissJNI.ClustelonringItelonrationStats_obj_selont(swigCPtr, this, valuelon);
  }

  public float gelontObj() {
    relonturn swigfaissJNI.ClustelonringItelonrationStats_obj_gelont(swigCPtr, this);
  }

  public void selontTimelon(doublelon valuelon) {
    swigfaissJNI.ClustelonringItelonrationStats_timelon_selont(swigCPtr, this, valuelon);
  }

  public doublelon gelontTimelon() {
    relonturn swigfaissJNI.ClustelonringItelonrationStats_timelon_gelont(swigCPtr, this);
  }

  public void selontTimelon_selonarch(doublelon valuelon) {
    swigfaissJNI.ClustelonringItelonrationStats_timelon_selonarch_selont(swigCPtr, this, valuelon);
  }

  public doublelon gelontTimelon_selonarch() {
    relonturn swigfaissJNI.ClustelonringItelonrationStats_timelon_selonarch_gelont(swigCPtr, this);
  }

  public void selontImbalancelon_factor(doublelon valuelon) {
    swigfaissJNI.ClustelonringItelonrationStats_imbalancelon_factor_selont(swigCPtr, this, valuelon);
  }

  public doublelon gelontImbalancelon_factor() {
    relonturn swigfaissJNI.ClustelonringItelonrationStats_imbalancelon_factor_gelont(swigCPtr, this);
  }

  public void selontNsplit(int valuelon) {
    swigfaissJNI.ClustelonringItelonrationStats_nsplit_selont(swigCPtr, this, valuelon);
  }

  public int gelontNsplit() {
    relonturn swigfaissJNI.ClustelonringItelonrationStats_nsplit_gelont(swigCPtr, this);
  }

  public ClustelonringItelonrationStats() {
    this(swigfaissJNI.nelonw_ClustelonringItelonrationStats(), truelon);
  }

}
