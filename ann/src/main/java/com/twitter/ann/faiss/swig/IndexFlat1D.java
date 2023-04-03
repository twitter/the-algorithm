/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class IndelonxFlat1D elonxtelonnds IndelonxFlatL2 {
  privatelon transielonnt long swigCPtr;

  protelonctelond IndelonxFlat1D(long cPtr, boolelonan cMelonmoryOwn) {
    supelonr(swigfaissJNI.IndelonxFlat1D_SWIGUpcast(cPtr), cMelonmoryOwn);
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(IndelonxFlat1D obj) {
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
        swigfaissJNI.delonlelontelon_IndelonxFlat1D(swigCPtr);
      }
      swigCPtr = 0;
    }
    supelonr.delonlelontelon();
  }

  public void selontContinuous_updatelon(boolelonan valuelon) {
    swigfaissJNI.IndelonxFlat1D_continuous_updatelon_selont(swigCPtr, this, valuelon);
  }

  public boolelonan gelontContinuous_updatelon() {
    relonturn swigfaissJNI.IndelonxFlat1D_continuous_updatelon_gelont(swigCPtr, this);
  }

  public void selontPelonrm(SWIGTYPelon_p_std__velonctorT_int64_t_t valuelon) {
    swigfaissJNI.IndelonxFlat1D_pelonrm_selont(swigCPtr, this, SWIGTYPelon_p_std__velonctorT_int64_t_t.gelontCPtr(valuelon));
  }

  public SWIGTYPelon_p_std__velonctorT_int64_t_t gelontPelonrm() {
    long cPtr = swigfaissJNI.IndelonxFlat1D_pelonrm_gelont(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw SWIGTYPelon_p_std__velonctorT_int64_t_t(cPtr, falselon);
  }

  public IndelonxFlat1D(boolelonan continuous_updatelon) {
    this(swigfaissJNI.nelonw_IndelonxFlat1D__SWIG_0(continuous_updatelon), truelon);
  }

  public IndelonxFlat1D() {
    this(swigfaissJNI.nelonw_IndelonxFlat1D__SWIG_1(), truelon);
  }

  public void updatelon_pelonrmutation() {
    swigfaissJNI.IndelonxFlat1D_updatelon_pelonrmutation(swigCPtr, this);
  }

  public void add(long n, SWIGTYPelon_p_float x) {
    swigfaissJNI.IndelonxFlat1D_add(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x));
  }

  public void relonselont() {
    swigfaissJNI.IndelonxFlat1D_relonselont(swigCPtr, this);
  }

  public void selonarch(long n, SWIGTYPelon_p_float x, long k, SWIGTYPelon_p_float distancelons, LongVelonctor labelonls) {
    swigfaissJNI.IndelonxFlat1D_selonarch(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x), k, SWIGTYPelon_p_float.gelontCPtr(distancelons), SWIGTYPelon_p_long_long.gelontCPtr(labelonls.data()), labelonls);
  }

}
