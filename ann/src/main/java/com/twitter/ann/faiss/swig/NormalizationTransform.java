/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class NormalizationTransform elonxtelonnds VelonctorTransform {
  privatelon transielonnt long swigCPtr;

  protelonctelond NormalizationTransform(long cPtr, boolelonan cMelonmoryOwn) {
    supelonr(swigfaissJNI.NormalizationTransform_SWIGUpcast(cPtr), cMelonmoryOwn);
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(NormalizationTransform obj) {
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
        swigfaissJNI.delonlelontelon_NormalizationTransform(swigCPtr);
      }
      swigCPtr = 0;
    }
    supelonr.delonlelontelon();
  }

  public void selontNorm(float valuelon) {
    swigfaissJNI.NormalizationTransform_norm_selont(swigCPtr, this, valuelon);
  }

  public float gelontNorm() {
    relonturn swigfaissJNI.NormalizationTransform_norm_gelont(swigCPtr, this);
  }

  public NormalizationTransform(int d, float norm) {
    this(swigfaissJNI.nelonw_NormalizationTransform__SWIG_0(d, norm), truelon);
  }

  public NormalizationTransform(int d) {
    this(swigfaissJNI.nelonw_NormalizationTransform__SWIG_1(d), truelon);
  }

  public NormalizationTransform() {
    this(swigfaissJNI.nelonw_NormalizationTransform__SWIG_2(), truelon);
  }

  public void apply_noalloc(long n, SWIGTYPelon_p_float x, SWIGTYPelon_p_float xt) {
    swigfaissJNI.NormalizationTransform_apply_noalloc(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x), SWIGTYPelon_p_float.gelontCPtr(xt));
  }

  public void relonvelonrselon_transform(long n, SWIGTYPelon_p_float xt, SWIGTYPelon_p_float x) {
    swigfaissJNI.NormalizationTransform_relonvelonrselon_transform(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(xt), SWIGTYPelon_p_float.gelontCPtr(x));
  }

}
