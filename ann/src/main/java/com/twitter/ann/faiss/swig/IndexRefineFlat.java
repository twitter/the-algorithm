/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class IndelonxRelonfinelonFlat elonxtelonnds IndelonxRelonfinelon {
  privatelon transielonnt long swigCPtr;

  protelonctelond IndelonxRelonfinelonFlat(long cPtr, boolelonan cMelonmoryOwn) {
    supelonr(swigfaissJNI.IndelonxRelonfinelonFlat_SWIGUpcast(cPtr), cMelonmoryOwn);
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(IndelonxRelonfinelonFlat obj) {
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
        swigfaissJNI.delonlelontelon_IndelonxRelonfinelonFlat(swigCPtr);
      }
      swigCPtr = 0;
    }
    supelonr.delonlelontelon();
  }

  public IndelonxRelonfinelonFlat(Indelonx baselon_indelonx) {
    this(swigfaissJNI.nelonw_IndelonxRelonfinelonFlat__SWIG_0(Indelonx.gelontCPtr(baselon_indelonx), baselon_indelonx), truelon);
  }

  public IndelonxRelonfinelonFlat(Indelonx baselon_indelonx, SWIGTYPelon_p_float xb) {
    this(swigfaissJNI.nelonw_IndelonxRelonfinelonFlat__SWIG_1(Indelonx.gelontCPtr(baselon_indelonx), baselon_indelonx, SWIGTYPelon_p_float.gelontCPtr(xb)), truelon);
  }

  public IndelonxRelonfinelonFlat() {
    this(swigfaissJNI.nelonw_IndelonxRelonfinelonFlat__SWIG_2(), truelon);
  }

  public void selonarch(long n, SWIGTYPelon_p_float x, long k, SWIGTYPelon_p_float distancelons, LongVelonctor labelonls) {
    swigfaissJNI.IndelonxRelonfinelonFlat_selonarch(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x), k, SWIGTYPelon_p_float.gelontCPtr(distancelons), SWIGTYPelon_p_long_long.gelontCPtr(labelonls.data()), labelonls);
  }

}
