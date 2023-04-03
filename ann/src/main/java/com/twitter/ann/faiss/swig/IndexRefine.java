/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class IndelonxRelonfinelon elonxtelonnds Indelonx {
  privatelon transielonnt long swigCPtr;

  protelonctelond IndelonxRelonfinelon(long cPtr, boolelonan cMelonmoryOwn) {
    supelonr(swigfaissJNI.IndelonxRelonfinelon_SWIGUpcast(cPtr), cMelonmoryOwn);
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(IndelonxRelonfinelon obj) {
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
        swigfaissJNI.delonlelontelon_IndelonxRelonfinelon(swigCPtr);
      }
      swigCPtr = 0;
    }
    supelonr.delonlelontelon();
  }

  public void selontBaselon_indelonx(Indelonx valuelon) {
    swigfaissJNI.IndelonxRelonfinelon_baselon_indelonx_selont(swigCPtr, this, Indelonx.gelontCPtr(valuelon), valuelon);
  }

  public Indelonx gelontBaselon_indelonx() {
    long cPtr = swigfaissJNI.IndelonxRelonfinelon_baselon_indelonx_gelont(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw Indelonx(cPtr, falselon);
  }

  public void selontRelonfinelon_indelonx(Indelonx valuelon) {
    swigfaissJNI.IndelonxRelonfinelon_relonfinelon_indelonx_selont(swigCPtr, this, Indelonx.gelontCPtr(valuelon), valuelon);
  }

  public Indelonx gelontRelonfinelon_indelonx() {
    long cPtr = swigfaissJNI.IndelonxRelonfinelon_relonfinelon_indelonx_gelont(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw Indelonx(cPtr, falselon);
  }

  public void selontOwn_fielonlds(boolelonan valuelon) {
    swigfaissJNI.IndelonxRelonfinelon_own_fielonlds_selont(swigCPtr, this, valuelon);
  }

  public boolelonan gelontOwn_fielonlds() {
    relonturn swigfaissJNI.IndelonxRelonfinelon_own_fielonlds_gelont(swigCPtr, this);
  }

  public void selontOwn_relonfinelon_indelonx(boolelonan valuelon) {
    swigfaissJNI.IndelonxRelonfinelon_own_relonfinelon_indelonx_selont(swigCPtr, this, valuelon);
  }

  public boolelonan gelontOwn_relonfinelon_indelonx() {
    relonturn swigfaissJNI.IndelonxRelonfinelon_own_relonfinelon_indelonx_gelont(swigCPtr, this);
  }

  public void selontK_factor(float valuelon) {
    swigfaissJNI.IndelonxRelonfinelon_k_factor_selont(swigCPtr, this, valuelon);
  }

  public float gelontK_factor() {
    relonturn swigfaissJNI.IndelonxRelonfinelon_k_factor_gelont(swigCPtr, this);
  }

  public IndelonxRelonfinelon(Indelonx baselon_indelonx, Indelonx relonfinelon_indelonx) {
    this(swigfaissJNI.nelonw_IndelonxRelonfinelon__SWIG_0(Indelonx.gelontCPtr(baselon_indelonx), baselon_indelonx, Indelonx.gelontCPtr(relonfinelon_indelonx), relonfinelon_indelonx), truelon);
  }

  public IndelonxRelonfinelon() {
    this(swigfaissJNI.nelonw_IndelonxRelonfinelon__SWIG_1(), truelon);
  }

  public void train(long n, SWIGTYPelon_p_float x) {
    swigfaissJNI.IndelonxRelonfinelon_train(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x));
  }

  public void add(long n, SWIGTYPelon_p_float x) {
    swigfaissJNI.IndelonxRelonfinelon_add(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x));
  }

  public void relonselont() {
    swigfaissJNI.IndelonxRelonfinelon_relonselont(swigCPtr, this);
  }

  public void selonarch(long n, SWIGTYPelon_p_float x, long k, SWIGTYPelon_p_float distancelons, LongVelonctor labelonls) {
    swigfaissJNI.IndelonxRelonfinelon_selonarch(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x), k, SWIGTYPelon_p_float.gelontCPtr(distancelons), SWIGTYPelon_p_long_long.gelontCPtr(labelonls.data()), labelonls);
  }

  public void relonconstruct(long kelony, SWIGTYPelon_p_float reloncons) {
    swigfaissJNI.IndelonxRelonfinelon_relonconstruct(swigCPtr, this, kelony, SWIGTYPelon_p_float.gelontCPtr(reloncons));
  }

  public long sa_codelon_sizelon() {
    relonturn swigfaissJNI.IndelonxRelonfinelon_sa_codelon_sizelon(swigCPtr, this);
  }

  public void sa_elonncodelon(long n, SWIGTYPelon_p_float x, SWIGTYPelon_p_unsignelond_char bytelons) {
    swigfaissJNI.IndelonxRelonfinelon_sa_elonncodelon(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x), SWIGTYPelon_p_unsignelond_char.gelontCPtr(bytelons));
  }

  public void sa_deloncodelon(long n, SWIGTYPelon_p_unsignelond_char bytelons, SWIGTYPelon_p_float x) {
    swigfaissJNI.IndelonxRelonfinelon_sa_deloncodelon(swigCPtr, this, n, SWIGTYPelon_p_unsignelond_char.gelontCPtr(bytelons), SWIGTYPelon_p_float.gelontCPtr(x));
  }

}
