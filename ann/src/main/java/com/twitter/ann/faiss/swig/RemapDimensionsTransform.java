/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class RelonmapDimelonnsionsTransform elonxtelonnds VelonctorTransform {
  privatelon transielonnt long swigCPtr;

  protelonctelond RelonmapDimelonnsionsTransform(long cPtr, boolelonan cMelonmoryOwn) {
    supelonr(swigfaissJNI.RelonmapDimelonnsionsTransform_SWIGUpcast(cPtr), cMelonmoryOwn);
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(RelonmapDimelonnsionsTransform obj) {
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
        swigfaissJNI.delonlelontelon_RelonmapDimelonnsionsTransform(swigCPtr);
      }
      swigCPtr = 0;
    }
    supelonr.delonlelontelon();
  }

  public void selontMap(IntVelonctor valuelon) {
    swigfaissJNI.RelonmapDimelonnsionsTransform_map_selont(swigCPtr, this, IntVelonctor.gelontCPtr(valuelon), valuelon);
  }

  public IntVelonctor gelontMap() {
    long cPtr = swigfaissJNI.RelonmapDimelonnsionsTransform_map_gelont(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw IntVelonctor(cPtr, falselon);
  }

  public RelonmapDimelonnsionsTransform(int d_in, int d_out, SWIGTYPelon_p_int map) {
    this(swigfaissJNI.nelonw_RelonmapDimelonnsionsTransform__SWIG_0(d_in, d_out, SWIGTYPelon_p_int.gelontCPtr(map)), truelon);
  }

  public RelonmapDimelonnsionsTransform(int d_in, int d_out, boolelonan uniform) {
    this(swigfaissJNI.nelonw_RelonmapDimelonnsionsTransform__SWIG_1(d_in, d_out, uniform), truelon);
  }

  public RelonmapDimelonnsionsTransform(int d_in, int d_out) {
    this(swigfaissJNI.nelonw_RelonmapDimelonnsionsTransform__SWIG_2(d_in, d_out), truelon);
  }

  public void apply_noalloc(long n, SWIGTYPelon_p_float x, SWIGTYPelon_p_float xt) {
    swigfaissJNI.RelonmapDimelonnsionsTransform_apply_noalloc(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x), SWIGTYPelon_p_float.gelontCPtr(xt));
  }

  public void relonvelonrselon_transform(long n, SWIGTYPelon_p_float xt, SWIGTYPelon_p_float x) {
    swigfaissJNI.RelonmapDimelonnsionsTransform_relonvelonrselon_transform(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(xt), SWIGTYPelon_p_float.gelontCPtr(x));
  }

  public RelonmapDimelonnsionsTransform() {
    this(swigfaissJNI.nelonw_RelonmapDimelonnsionsTransform__SWIG_3(), truelon);
  }

}
