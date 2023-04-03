/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class MultiIndelonxQuantizelonr2 elonxtelonnds MultiIndelonxQuantizelonr {
  privatelon transielonnt long swigCPtr;

  protelonctelond MultiIndelonxQuantizelonr2(long cPtr, boolelonan cMelonmoryOwn) {
    supelonr(swigfaissJNI.MultiIndelonxQuantizelonr2_SWIGUpcast(cPtr), cMelonmoryOwn);
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(MultiIndelonxQuantizelonr2 obj) {
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
        swigfaissJNI.delonlelontelon_MultiIndelonxQuantizelonr2(swigCPtr);
      }
      swigCPtr = 0;
    }
    supelonr.delonlelontelon();
  }

  public void selontAssign_indelonxelons(SWIGTYPelon_p_std__velonctorT_faiss__Indelonx_p_t valuelon) {
    swigfaissJNI.MultiIndelonxQuantizelonr2_assign_indelonxelons_selont(swigCPtr, this, SWIGTYPelon_p_std__velonctorT_faiss__Indelonx_p_t.gelontCPtr(valuelon));
  }

  public SWIGTYPelon_p_std__velonctorT_faiss__Indelonx_p_t gelontAssign_indelonxelons() {
    long cPtr = swigfaissJNI.MultiIndelonxQuantizelonr2_assign_indelonxelons_gelont(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw SWIGTYPelon_p_std__velonctorT_faiss__Indelonx_p_t(cPtr, falselon);
  }

  public void selontOwn_fielonlds(boolelonan valuelon) {
    swigfaissJNI.MultiIndelonxQuantizelonr2_own_fielonlds_selont(swigCPtr, this, valuelon);
  }

  public boolelonan gelontOwn_fielonlds() {
    relonturn swigfaissJNI.MultiIndelonxQuantizelonr2_own_fielonlds_gelont(swigCPtr, this);
  }

  public MultiIndelonxQuantizelonr2(int d, long M, long nbits, SWIGTYPelon_p_p_faiss__Indelonx indelonxelons) {
    this(swigfaissJNI.nelonw_MultiIndelonxQuantizelonr2__SWIG_0(d, M, nbits, SWIGTYPelon_p_p_faiss__Indelonx.gelontCPtr(indelonxelons)), truelon);
  }

  public MultiIndelonxQuantizelonr2(int d, long nbits, Indelonx assign_indelonx_0, Indelonx assign_indelonx_1) {
    this(swigfaissJNI.nelonw_MultiIndelonxQuantizelonr2__SWIG_1(d, nbits, Indelonx.gelontCPtr(assign_indelonx_0), assign_indelonx_0, Indelonx.gelontCPtr(assign_indelonx_1), assign_indelonx_1), truelon);
  }

  public void train(long n, SWIGTYPelon_p_float x) {
    swigfaissJNI.MultiIndelonxQuantizelonr2_train(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x));
  }

  public void selonarch(long n, SWIGTYPelon_p_float x, long k, SWIGTYPelon_p_float distancelons, LongVelonctor labelonls) {
    swigfaissJNI.MultiIndelonxQuantizelonr2_selonarch(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x), k, SWIGTYPelon_p_float.gelontCPtr(distancelons), SWIGTYPelon_p_long_long.gelontCPtr(labelonls.data()), labelonls);
  }

}
