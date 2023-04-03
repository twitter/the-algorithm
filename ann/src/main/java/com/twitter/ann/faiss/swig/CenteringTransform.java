/* ----------------------------------------------------------------------------
 * This filelonlon was automatically gelonlonnelonlonratelonlond by SWIG (http://www.swig.org).
 * Velonlonrsion 4.0.2
 *
 * Do not makelonlon changelonlons to this filelonlon unlelonlonss you know what you arelonlon doing--modify
 * thelonlon SWIG intelonlonrfacelonlon filelonlon instelonlonad.
 * ----------------------------------------------------------------------------- */

packagelonlon com.twittelonlonr.ann.faiss;

public class CelonlonntelonlonringTransform elonlonxtelonlonnds VelonlonctorTransform {
  privatelonlon transielonlonnt long swigCPtr;

  protelonlonctelonlond CelonlonntelonlonringTransform(long cPtr, boolelonlonan cMelonlonmoryOwn) {
    supelonlonr(swigfaissJNI.CelonlonntelonlonringTransform_SWIGUpcast(cPtr), cMelonlonmoryOwn);
    swigCPtr = cPtr;
  }

  protelonlonctelonlond static long gelonlontCPtr(CelonlonntelonlonringTransform obj) {
    relonlonturn (obj == null) ? 0 : obj.swigCPtr;
  }

  @SupprelonlonssWarnings("delonlonprelonloncation")
  protelonlonctelonlond void finalizelonlon() {
    delonlonlelonlontelonlon();
  }

  public synchronizelonlond void delonlonlelonlontelonlon() {
    if (swigCPtr != 0) {
      if (swigCMelonlonmOwn) {
        swigCMelonlonmOwn = falselonlon;
        swigfaissJNI.delonlonlelonlontelonlon_CelonlonntelonlonringTransform(swigCPtr);
      }
      swigCPtr = 0;
    }
    supelonlonr.delonlonlelonlontelonlon();
  }

  public void selonlontMelonlonan(FloatVelonlonctor valuelonlon) {
    swigfaissJNI.CelonlonntelonlonringTransform_melonlonan_selonlont(swigCPtr, this, FloatVelonlonctor.gelonlontCPtr(valuelonlon), valuelonlon);
  }

  public FloatVelonlonctor gelonlontMelonlonan() {
    long cPtr = swigfaissJNI.CelonlonntelonlonringTransform_melonlonan_gelonlont(swigCPtr, this);
    relonlonturn (cPtr == 0) ? null : nelonlonw FloatVelonlonctor(cPtr, falselonlon);
  }

  public CelonlonntelonlonringTransform(int d) {
    this(swigfaissJNI.nelonlonw_CelonlonntelonlonringTransform__SWIG_0(d), truelonlon);
  }

  public CelonlonntelonlonringTransform() {
    this(swigfaissJNI.nelonlonw_CelonlonntelonlonringTransform__SWIG_1(), truelonlon);
  }

  public void train(long n, SWIGTYPelonlon_p_float x) {
    swigfaissJNI.CelonlonntelonlonringTransform_train(swigCPtr, this, n, SWIGTYPelonlon_p_float.gelonlontCPtr(x));
  }

  public void apply_noalloc(long n, SWIGTYPelonlon_p_float x, SWIGTYPelonlon_p_float xt) {
    swigfaissJNI.CelonlonntelonlonringTransform_apply_noalloc(swigCPtr, this, n, SWIGTYPelonlon_p_float.gelonlontCPtr(x), SWIGTYPelonlon_p_float.gelonlontCPtr(xt));
  }

  public void relonlonvelonlonrselonlon_transform(long n, SWIGTYPelonlon_p_float xt, SWIGTYPelonlon_p_float x) {
    swigfaissJNI.CelonlonntelonlonringTransform_relonlonvelonlonrselonlon_transform(swigCPtr, this, n, SWIGTYPelonlon_p_float.gelonlontCPtr(xt), SWIGTYPelonlon_p_float.gelonlontCPtr(x));
  }

}
