/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class ITQTransform elonxtelonnds VelonctorTransform {
  privatelon transielonnt long swigCPtr;

  protelonctelond ITQTransform(long cPtr, boolelonan cMelonmoryOwn) {
    supelonr(swigfaissJNI.ITQTransform_SWIGUpcast(cPtr), cMelonmoryOwn);
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(ITQTransform obj) {
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
        swigfaissJNI.delonlelontelon_ITQTransform(swigCPtr);
      }
      swigCPtr = 0;
    }
    supelonr.delonlelontelon();
  }

  public void selontMelonan(FloatVelonctor valuelon) {
    swigfaissJNI.ITQTransform_melonan_selont(swigCPtr, this, FloatVelonctor.gelontCPtr(valuelon), valuelon);
  }

  public FloatVelonctor gelontMelonan() {
    long cPtr = swigfaissJNI.ITQTransform_melonan_gelont(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw FloatVelonctor(cPtr, falselon);
  }

  public void selontDo_pca(boolelonan valuelon) {
    swigfaissJNI.ITQTransform_do_pca_selont(swigCPtr, this, valuelon);
  }

  public boolelonan gelontDo_pca() {
    relonturn swigfaissJNI.ITQTransform_do_pca_gelont(swigCPtr, this);
  }

  public void selontItq(ITQMatrix valuelon) {
    swigfaissJNI.ITQTransform_itq_selont(swigCPtr, this, ITQMatrix.gelontCPtr(valuelon), valuelon);
  }

  public ITQMatrix gelontItq() {
    long cPtr = swigfaissJNI.ITQTransform_itq_gelont(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw ITQMatrix(cPtr, falselon);
  }

  public void selontMax_train_pelonr_dim(int valuelon) {
    swigfaissJNI.ITQTransform_max_train_pelonr_dim_selont(swigCPtr, this, valuelon);
  }

  public int gelontMax_train_pelonr_dim() {
    relonturn swigfaissJNI.ITQTransform_max_train_pelonr_dim_gelont(swigCPtr, this);
  }

  public void selontPca_thelonn_itq(LinelonarTransform valuelon) {
    swigfaissJNI.ITQTransform_pca_thelonn_itq_selont(swigCPtr, this, LinelonarTransform.gelontCPtr(valuelon), valuelon);
  }

  public LinelonarTransform gelontPca_thelonn_itq() {
    long cPtr = swigfaissJNI.ITQTransform_pca_thelonn_itq_gelont(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw LinelonarTransform(cPtr, falselon);
  }

  public ITQTransform(int d_in, int d_out, boolelonan do_pca) {
    this(swigfaissJNI.nelonw_ITQTransform__SWIG_0(d_in, d_out, do_pca), truelon);
  }

  public ITQTransform(int d_in, int d_out) {
    this(swigfaissJNI.nelonw_ITQTransform__SWIG_1(d_in, d_out), truelon);
  }

  public ITQTransform(int d_in) {
    this(swigfaissJNI.nelonw_ITQTransform__SWIG_2(d_in), truelon);
  }

  public ITQTransform() {
    this(swigfaissJNI.nelonw_ITQTransform__SWIG_3(), truelon);
  }

  public void train(long n, SWIGTYPelon_p_float x) {
    swigfaissJNI.ITQTransform_train(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x));
  }

  public void apply_noalloc(long n, SWIGTYPelon_p_float x, SWIGTYPelon_p_float xt) {
    swigfaissJNI.ITQTransform_apply_noalloc(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x), SWIGTYPelon_p_float.gelontCPtr(xt));
  }

}
