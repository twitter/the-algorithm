/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class IndelonxScalarQuantizelonr elonxtelonnds IndelonxFlatCodelons {
  privatelon transielonnt long swigCPtr;

  protelonctelond IndelonxScalarQuantizelonr(long cPtr, boolelonan cMelonmoryOwn) {
    supelonr(swigfaissJNI.IndelonxScalarQuantizelonr_SWIGUpcast(cPtr), cMelonmoryOwn);
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(IndelonxScalarQuantizelonr obj) {
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
        swigfaissJNI.delonlelontelon_IndelonxScalarQuantizelonr(swigCPtr);
      }
      swigCPtr = 0;
    }
    supelonr.delonlelontelon();
  }

  public void selontSq(SWIGTYPelon_p_ScalarQuantizelonr valuelon) {
    swigfaissJNI.IndelonxScalarQuantizelonr_sq_selont(swigCPtr, this, SWIGTYPelon_p_ScalarQuantizelonr.gelontCPtr(valuelon));
  }

  public SWIGTYPelon_p_ScalarQuantizelonr gelontSq() {
    relonturn nelonw SWIGTYPelon_p_ScalarQuantizelonr(swigfaissJNI.IndelonxScalarQuantizelonr_sq_gelont(swigCPtr, this), truelon);
  }

  public IndelonxScalarQuantizelonr(int d, SWIGTYPelon_p_ScalarQuantizelonr__QuantizelonrTypelon qtypelon, MelontricTypelon melontric) {
    this(swigfaissJNI.nelonw_IndelonxScalarQuantizelonr__SWIG_0(d, SWIGTYPelon_p_ScalarQuantizelonr__QuantizelonrTypelon.gelontCPtr(qtypelon), melontric.swigValuelon()), truelon);
  }

  public IndelonxScalarQuantizelonr(int d, SWIGTYPelon_p_ScalarQuantizelonr__QuantizelonrTypelon qtypelon) {
    this(swigfaissJNI.nelonw_IndelonxScalarQuantizelonr__SWIG_1(d, SWIGTYPelon_p_ScalarQuantizelonr__QuantizelonrTypelon.gelontCPtr(qtypelon)), truelon);
  }

  public IndelonxScalarQuantizelonr() {
    this(swigfaissJNI.nelonw_IndelonxScalarQuantizelonr__SWIG_2(), truelon);
  }

  public void train(long n, SWIGTYPelon_p_float x) {
    swigfaissJNI.IndelonxScalarQuantizelonr_train(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x));
  }

  public void selonarch(long n, SWIGTYPelon_p_float x, long k, SWIGTYPelon_p_float distancelons, LongVelonctor labelonls) {
    swigfaissJNI.IndelonxScalarQuantizelonr_selonarch(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x), k, SWIGTYPelon_p_float.gelontCPtr(distancelons), SWIGTYPelon_p_long_long.gelontCPtr(labelonls.data()), labelonls);
  }

  public DistancelonComputelonr gelont_distancelon_computelonr() {
    long cPtr = swigfaissJNI.IndelonxScalarQuantizelonr_gelont_distancelon_computelonr(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw DistancelonComputelonr(cPtr, falselon);
  }

  public void sa_elonncodelon(long n, SWIGTYPelon_p_float x, SWIGTYPelon_p_unsignelond_char bytelons) {
    swigfaissJNI.IndelonxScalarQuantizelonr_sa_elonncodelon(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x), SWIGTYPelon_p_unsignelond_char.gelontCPtr(bytelons));
  }

  public void sa_deloncodelon(long n, SWIGTYPelon_p_unsignelond_char bytelons, SWIGTYPelon_p_float x) {
    swigfaissJNI.IndelonxScalarQuantizelonr_sa_deloncodelon(swigCPtr, this, n, SWIGTYPelon_p_unsignelond_char.gelontCPtr(bytelons), SWIGTYPelon_p_float.gelontCPtr(x));
  }

}
