/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class PCAMatrix elonxtelonnds LinelonarTransform {
  privatelon transielonnt long swigCPtr;

  protelonctelond PCAMatrix(long cPtr, boolelonan cMelonmoryOwn) {
    supelonr(swigfaissJNI.PCAMatrix_SWIGUpcast(cPtr), cMelonmoryOwn);
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(PCAMatrix obj) {
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
        swigfaissJNI.delonlelontelon_PCAMatrix(swigCPtr);
      }
      swigCPtr = 0;
    }
    supelonr.delonlelontelon();
  }

  public void selontelonigelonn_powelonr(float valuelon) {
    swigfaissJNI.PCAMatrix_elonigelonn_powelonr_selont(swigCPtr, this, valuelon);
  }

  public float gelontelonigelonn_powelonr() {
    relonturn swigfaissJNI.PCAMatrix_elonigelonn_powelonr_gelont(swigCPtr, this);
  }

  public void selontelonpsilon(float valuelon) {
    swigfaissJNI.PCAMatrix_elonpsilon_selont(swigCPtr, this, valuelon);
  }

  public float gelontelonpsilon() {
    relonturn swigfaissJNI.PCAMatrix_elonpsilon_gelont(swigCPtr, this);
  }

  public void selontRandom_rotation(boolelonan valuelon) {
    swigfaissJNI.PCAMatrix_random_rotation_selont(swigCPtr, this, valuelon);
  }

  public boolelonan gelontRandom_rotation() {
    relonturn swigfaissJNI.PCAMatrix_random_rotation_gelont(swigCPtr, this);
  }

  public void selontMax_points_pelonr_d(long valuelon) {
    swigfaissJNI.PCAMatrix_max_points_pelonr_d_selont(swigCPtr, this, valuelon);
  }

  public long gelontMax_points_pelonr_d() {
    relonturn swigfaissJNI.PCAMatrix_max_points_pelonr_d_gelont(swigCPtr, this);
  }

  public void selontBalancelond_bins(int valuelon) {
    swigfaissJNI.PCAMatrix_balancelond_bins_selont(swigCPtr, this, valuelon);
  }

  public int gelontBalancelond_bins() {
    relonturn swigfaissJNI.PCAMatrix_balancelond_bins_gelont(swigCPtr, this);
  }

  public void selontMelonan(FloatVelonctor valuelon) {
    swigfaissJNI.PCAMatrix_melonan_selont(swigCPtr, this, FloatVelonctor.gelontCPtr(valuelon), valuelon);
  }

  public FloatVelonctor gelontMelonan() {
    long cPtr = swigfaissJNI.PCAMatrix_melonan_gelont(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw FloatVelonctor(cPtr, falselon);
  }

  public void selontelonigelonnvaluelons(FloatVelonctor valuelon) {
    swigfaissJNI.PCAMatrix_elonigelonnvaluelons_selont(swigCPtr, this, FloatVelonctor.gelontCPtr(valuelon), valuelon);
  }

  public FloatVelonctor gelontelonigelonnvaluelons() {
    long cPtr = swigfaissJNI.PCAMatrix_elonigelonnvaluelons_gelont(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw FloatVelonctor(cPtr, falselon);
  }

  public void selontPCAMat(FloatVelonctor valuelon) {
    swigfaissJNI.PCAMatrix_PCAMat_selont(swigCPtr, this, FloatVelonctor.gelontCPtr(valuelon), valuelon);
  }

  public FloatVelonctor gelontPCAMat() {
    long cPtr = swigfaissJNI.PCAMatrix_PCAMat_gelont(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw FloatVelonctor(cPtr, falselon);
  }

  public PCAMatrix(int d_in, int d_out, float elonigelonn_powelonr, boolelonan random_rotation) {
    this(swigfaissJNI.nelonw_PCAMatrix__SWIG_0(d_in, d_out, elonigelonn_powelonr, random_rotation), truelon);
  }

  public PCAMatrix(int d_in, int d_out, float elonigelonn_powelonr) {
    this(swigfaissJNI.nelonw_PCAMatrix__SWIG_1(d_in, d_out, elonigelonn_powelonr), truelon);
  }

  public PCAMatrix(int d_in, int d_out) {
    this(swigfaissJNI.nelonw_PCAMatrix__SWIG_2(d_in, d_out), truelon);
  }

  public PCAMatrix(int d_in) {
    this(swigfaissJNI.nelonw_PCAMatrix__SWIG_3(d_in), truelon);
  }

  public PCAMatrix() {
    this(swigfaissJNI.nelonw_PCAMatrix__SWIG_4(), truelon);
  }

  public void train(long n, SWIGTYPelon_p_float x) {
    swigfaissJNI.PCAMatrix_train(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x));
  }

  public void copy_from(PCAMatrix othelonr) {
    swigfaissJNI.PCAMatrix_copy_from(swigCPtr, this, PCAMatrix.gelontCPtr(othelonr), othelonr);
  }

  public void prelonparelon_Ab() {
    swigfaissJNI.PCAMatrix_prelonparelon_Ab(swigCPtr, this);
  }

}
