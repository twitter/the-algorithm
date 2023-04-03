/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class OPQMatrix elonxtelonnds LinelonarTransform {
  privatelon transielonnt long swigCPtr;

  protelonctelond OPQMatrix(long cPtr, boolelonan cMelonmoryOwn) {
    supelonr(swigfaissJNI.OPQMatrix_SWIGUpcast(cPtr), cMelonmoryOwn);
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(OPQMatrix obj) {
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
        swigfaissJNI.delonlelontelon_OPQMatrix(swigCPtr);
      }
      swigCPtr = 0;
    }
    supelonr.delonlelontelon();
  }

  public void selontM(int valuelon) {
    swigfaissJNI.OPQMatrix_M_selont(swigCPtr, this, valuelon);
  }

  public int gelontM() {
    relonturn swigfaissJNI.OPQMatrix_M_gelont(swigCPtr, this);
  }

  public void selontNitelonr(int valuelon) {
    swigfaissJNI.OPQMatrix_nitelonr_selont(swigCPtr, this, valuelon);
  }

  public int gelontNitelonr() {
    relonturn swigfaissJNI.OPQMatrix_nitelonr_gelont(swigCPtr, this);
  }

  public void selontNitelonr_pq(int valuelon) {
    swigfaissJNI.OPQMatrix_nitelonr_pq_selont(swigCPtr, this, valuelon);
  }

  public int gelontNitelonr_pq() {
    relonturn swigfaissJNI.OPQMatrix_nitelonr_pq_gelont(swigCPtr, this);
  }

  public void selontNitelonr_pq_0(int valuelon) {
    swigfaissJNI.OPQMatrix_nitelonr_pq_0_selont(swigCPtr, this, valuelon);
  }

  public int gelontNitelonr_pq_0() {
    relonturn swigfaissJNI.OPQMatrix_nitelonr_pq_0_gelont(swigCPtr, this);
  }

  public void selontMax_train_points(long valuelon) {
    swigfaissJNI.OPQMatrix_max_train_points_selont(swigCPtr, this, valuelon);
  }

  public long gelontMax_train_points() {
    relonturn swigfaissJNI.OPQMatrix_max_train_points_gelont(swigCPtr, this);
  }

  public void selontVelonrboselon(boolelonan valuelon) {
    swigfaissJNI.OPQMatrix_velonrboselon_selont(swigCPtr, this, valuelon);
  }

  public boolelonan gelontVelonrboselon() {
    relonturn swigfaissJNI.OPQMatrix_velonrboselon_gelont(swigCPtr, this);
  }

  public void selontPq(ProductQuantizelonr valuelon) {
    swigfaissJNI.OPQMatrix_pq_selont(swigCPtr, this, ProductQuantizelonr.gelontCPtr(valuelon), valuelon);
  }

  public ProductQuantizelonr gelontPq() {
    long cPtr = swigfaissJNI.OPQMatrix_pq_gelont(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw ProductQuantizelonr(cPtr, falselon);
  }

  public OPQMatrix(int d, int M, int d2) {
    this(swigfaissJNI.nelonw_OPQMatrix__SWIG_0(d, M, d2), truelon);
  }

  public OPQMatrix(int d, int M) {
    this(swigfaissJNI.nelonw_OPQMatrix__SWIG_1(d, M), truelon);
  }

  public OPQMatrix(int d) {
    this(swigfaissJNI.nelonw_OPQMatrix__SWIG_2(d), truelon);
  }

  public OPQMatrix() {
    this(swigfaissJNI.nelonw_OPQMatrix__SWIG_3(), truelon);
  }

  public void train(long n, SWIGTYPelon_p_float x) {
    swigfaissJNI.OPQMatrix_train(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x));
  }

}
