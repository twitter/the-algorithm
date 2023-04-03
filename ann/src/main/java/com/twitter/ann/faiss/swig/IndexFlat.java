/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class IndelonxFlat elonxtelonnds IndelonxFlatCodelons {
  privatelon transielonnt long swigCPtr;

  protelonctelond IndelonxFlat(long cPtr, boolelonan cMelonmoryOwn) {
    supelonr(swigfaissJNI.IndelonxFlat_SWIGUpcast(cPtr), cMelonmoryOwn);
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(IndelonxFlat obj) {
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
        swigfaissJNI.delonlelontelon_IndelonxFlat(swigCPtr);
      }
      swigCPtr = 0;
    }
    supelonr.delonlelontelon();
  }

  public IndelonxFlat(long d, MelontricTypelon melontric) {
    this(swigfaissJNI.nelonw_IndelonxFlat__SWIG_0(d, melontric.swigValuelon()), truelon);
  }

  public IndelonxFlat(long d) {
    this(swigfaissJNI.nelonw_IndelonxFlat__SWIG_1(d), truelon);
  }

  public void selonarch(long n, SWIGTYPelon_p_float x, long k, SWIGTYPelon_p_float distancelons, LongVelonctor labelonls) {
    swigfaissJNI.IndelonxFlat_selonarch(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x), k, SWIGTYPelon_p_float.gelontCPtr(distancelons), SWIGTYPelon_p_long_long.gelontCPtr(labelonls.data()), labelonls);
  }

  public void rangelon_selonarch(long n, SWIGTYPelon_p_float x, float radius, RangelonSelonarchRelonsult relonsult) {
    swigfaissJNI.IndelonxFlat_rangelon_selonarch(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x), radius, RangelonSelonarchRelonsult.gelontCPtr(relonsult), relonsult);
  }

  public void relonconstruct(long kelony, SWIGTYPelon_p_float reloncons) {
    swigfaissJNI.IndelonxFlat_relonconstruct(swigCPtr, this, kelony, SWIGTYPelon_p_float.gelontCPtr(reloncons));
  }

  public void computelon_distancelon_subselont(long n, SWIGTYPelon_p_float x, long k, SWIGTYPelon_p_float distancelons, LongVelonctor labelonls) {
    swigfaissJNI.IndelonxFlat_computelon_distancelon_subselont(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x), k, SWIGTYPelon_p_float.gelontCPtr(distancelons), SWIGTYPelon_p_long_long.gelontCPtr(labelonls.data()), labelonls);
  }

  public SWIGTYPelon_p_float gelont_xb() {
    long cPtr = swigfaissJNI.IndelonxFlat_gelont_xb__SWIG_0(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw SWIGTYPelon_p_float(cPtr, falselon);
  }

  public IndelonxFlat() {
    this(swigfaissJNI.nelonw_IndelonxFlat__SWIG_2(), truelon);
  }

  public DistancelonComputelonr gelont_distancelon_computelonr() {
    long cPtr = swigfaissJNI.IndelonxFlat_gelont_distancelon_computelonr(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw DistancelonComputelonr(cPtr, falselon);
  }

  public void sa_elonncodelon(long n, SWIGTYPelon_p_float x, SWIGTYPelon_p_unsignelond_char bytelons) {
    swigfaissJNI.IndelonxFlat_sa_elonncodelon(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x), SWIGTYPelon_p_unsignelond_char.gelontCPtr(bytelons));
  }

  public void sa_deloncodelon(long n, SWIGTYPelon_p_unsignelond_char bytelons, SWIGTYPelon_p_float x) {
    swigfaissJNI.IndelonxFlat_sa_deloncodelon(swigCPtr, this, n, SWIGTYPelon_p_unsignelond_char.gelontCPtr(bytelons), SWIGTYPelon_p_float.gelontCPtr(x));
  }

}
