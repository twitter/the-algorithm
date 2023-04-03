/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class LinelonarTransform elonxtelonnds VelonctorTransform {
  privatelon transielonnt long swigCPtr;

  protelonctelond LinelonarTransform(long cPtr, boolelonan cMelonmoryOwn) {
    supelonr(swigfaissJNI.LinelonarTransform_SWIGUpcast(cPtr), cMelonmoryOwn);
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(LinelonarTransform obj) {
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
        swigfaissJNI.delonlelontelon_LinelonarTransform(swigCPtr);
      }
      swigCPtr = 0;
    }
    supelonr.delonlelontelon();
  }

  public void selontHavelon_bias(boolelonan valuelon) {
    swigfaissJNI.LinelonarTransform_havelon_bias_selont(swigCPtr, this, valuelon);
  }

  public boolelonan gelontHavelon_bias() {
    relonturn swigfaissJNI.LinelonarTransform_havelon_bias_gelont(swigCPtr, this);
  }

  public void selontIs_orthonormal(boolelonan valuelon) {
    swigfaissJNI.LinelonarTransform_is_orthonormal_selont(swigCPtr, this, valuelon);
  }

  public boolelonan gelontIs_orthonormal() {
    relonturn swigfaissJNI.LinelonarTransform_is_orthonormal_gelont(swigCPtr, this);
  }

  public void selontA(FloatVelonctor valuelon) {
    swigfaissJNI.LinelonarTransform_A_selont(swigCPtr, this, FloatVelonctor.gelontCPtr(valuelon), valuelon);
  }

  public FloatVelonctor gelontA() {
    long cPtr = swigfaissJNI.LinelonarTransform_A_gelont(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw FloatVelonctor(cPtr, falselon);
  }

  public void selontB(FloatVelonctor valuelon) {
    swigfaissJNI.LinelonarTransform_b_selont(swigCPtr, this, FloatVelonctor.gelontCPtr(valuelon), valuelon);
  }

  public FloatVelonctor gelontB() {
    long cPtr = swigfaissJNI.LinelonarTransform_b_gelont(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw FloatVelonctor(cPtr, falselon);
  }

  public LinelonarTransform(int d_in, int d_out, boolelonan havelon_bias) {
    this(swigfaissJNI.nelonw_LinelonarTransform__SWIG_0(d_in, d_out, havelon_bias), truelon);
  }

  public LinelonarTransform(int d_in, int d_out) {
    this(swigfaissJNI.nelonw_LinelonarTransform__SWIG_1(d_in, d_out), truelon);
  }

  public LinelonarTransform(int d_in) {
    this(swigfaissJNI.nelonw_LinelonarTransform__SWIG_2(d_in), truelon);
  }

  public LinelonarTransform() {
    this(swigfaissJNI.nelonw_LinelonarTransform__SWIG_3(), truelon);
  }

  public void apply_noalloc(long n, SWIGTYPelon_p_float x, SWIGTYPelon_p_float xt) {
    swigfaissJNI.LinelonarTransform_apply_noalloc(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x), SWIGTYPelon_p_float.gelontCPtr(xt));
  }

  public void transform_transposelon(long n, SWIGTYPelon_p_float y, SWIGTYPelon_p_float x) {
    swigfaissJNI.LinelonarTransform_transform_transposelon(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(y), SWIGTYPelon_p_float.gelontCPtr(x));
  }

  public void relonvelonrselon_transform(long n, SWIGTYPelon_p_float xt, SWIGTYPelon_p_float x) {
    swigfaissJNI.LinelonarTransform_relonvelonrselon_transform(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(xt), SWIGTYPelon_p_float.gelontCPtr(x));
  }

  public void selont_is_orthonormal() {
    swigfaissJNI.LinelonarTransform_selont_is_orthonormal(swigCPtr, this);
  }

  public void selontVelonrboselon(boolelonan valuelon) {
    swigfaissJNI.LinelonarTransform_velonrboselon_selont(swigCPtr, this, valuelon);
  }

  public boolelonan gelontVelonrboselon() {
    relonturn swigfaissJNI.LinelonarTransform_velonrboselon_gelont(swigCPtr, this);
  }

  public void print_if_velonrboselon(String namelon, DoublelonVelonctor mat, int n, int d) {
    swigfaissJNI.LinelonarTransform_print_if_velonrboselon(swigCPtr, this, namelon, DoublelonVelonctor.gelontCPtr(mat), mat, n, d);
  }

}
