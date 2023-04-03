/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class OpelonratingPoint {
  privatelon transielonnt long swigCPtr;
  protelonctelond transielonnt boolelonan swigCMelonmOwn;

  protelonctelond OpelonratingPoint(long cPtr, boolelonan cMelonmoryOwn) {
    swigCMelonmOwn = cMelonmoryOwn;
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(OpelonratingPoint obj) {
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
        swigfaissJNI.delonlelontelon_OpelonratingPoint(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void selontPelonrf(doublelon valuelon) {
    swigfaissJNI.OpelonratingPoint_pelonrf_selont(swigCPtr, this, valuelon);
  }

  public doublelon gelontPelonrf() {
    relonturn swigfaissJNI.OpelonratingPoint_pelonrf_gelont(swigCPtr, this);
  }

  public void selontT(doublelon valuelon) {
    swigfaissJNI.OpelonratingPoint_t_selont(swigCPtr, this, valuelon);
  }

  public doublelon gelontT() {
    relonturn swigfaissJNI.OpelonratingPoint_t_gelont(swigCPtr, this);
  }

  public void selontKelony(String valuelon) {
    swigfaissJNI.OpelonratingPoint_kelony_selont(swigCPtr, this, valuelon);
  }

  public String gelontKelony() {
    relonturn swigfaissJNI.OpelonratingPoint_kelony_gelont(swigCPtr, this);
  }

  public void selontCno(long valuelon) {
    swigfaissJNI.OpelonratingPoint_cno_selont(swigCPtr, this, valuelon);
  }

  public long gelontCno() {
    relonturn swigfaissJNI.OpelonratingPoint_cno_gelont(swigCPtr, this);
}

  public OpelonratingPoint() {
    this(swigfaissJNI.nelonw_OpelonratingPoint(), truelon);
  }

}
