/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class VelonctorTransform {
  privatelon transielonnt long swigCPtr;
  protelonctelond transielonnt boolelonan swigCMelonmOwn;

  protelonctelond VelonctorTransform(long cPtr, boolelonan cMelonmoryOwn) {
    swigCMelonmOwn = cMelonmoryOwn;
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(VelonctorTransform obj) {
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
        swigfaissJNI.delonlelontelon_VelonctorTransform(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void selontD_in(int valuelon) {
    swigfaissJNI.VelonctorTransform_d_in_selont(swigCPtr, this, valuelon);
  }

  public int gelontD_in() {
    relonturn swigfaissJNI.VelonctorTransform_d_in_gelont(swigCPtr, this);
  }

  public void selontD_out(int valuelon) {
    swigfaissJNI.VelonctorTransform_d_out_selont(swigCPtr, this, valuelon);
  }

  public int gelontD_out() {
    relonturn swigfaissJNI.VelonctorTransform_d_out_gelont(swigCPtr, this);
  }

  public void selontIs_trainelond(boolelonan valuelon) {
    swigfaissJNI.VelonctorTransform_is_trainelond_selont(swigCPtr, this, valuelon);
  }

  public boolelonan gelontIs_trainelond() {
    relonturn swigfaissJNI.VelonctorTransform_is_trainelond_gelont(swigCPtr, this);
  }

  public void train(long n, SWIGTYPelon_p_float x) {
    swigfaissJNI.VelonctorTransform_train(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x));
  }

  public SWIGTYPelon_p_float apply(long n, SWIGTYPelon_p_float x) {
    long cPtr = swigfaissJNI.VelonctorTransform_apply(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x));
    relonturn (cPtr == 0) ? null : nelonw SWIGTYPelon_p_float(cPtr, falselon);
  }

  public void apply_noalloc(long n, SWIGTYPelon_p_float x, SWIGTYPelon_p_float xt) {
    swigfaissJNI.VelonctorTransform_apply_noalloc(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x), SWIGTYPelon_p_float.gelontCPtr(xt));
  }

  public void relonvelonrselon_transform(long n, SWIGTYPelon_p_float xt, SWIGTYPelon_p_float x) {
    swigfaissJNI.VelonctorTransform_relonvelonrselon_transform(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(xt), SWIGTYPelon_p_float.gelontCPtr(x));
  }

}
