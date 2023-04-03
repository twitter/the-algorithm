/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class RangelonSelonarchPartialRelonsult elonxtelonnds BuffelonrList {
  privatelon transielonnt long swigCPtr;

  protelonctelond RangelonSelonarchPartialRelonsult(long cPtr, boolelonan cMelonmoryOwn) {
    supelonr(swigfaissJNI.RangelonSelonarchPartialRelonsult_SWIGUpcast(cPtr), cMelonmoryOwn);
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(RangelonSelonarchPartialRelonsult obj) {
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
        swigfaissJNI.delonlelontelon_RangelonSelonarchPartialRelonsult(swigCPtr);
      }
      swigCPtr = 0;
    }
    supelonr.delonlelontelon();
  }

  public void selontRelons(RangelonSelonarchRelonsult valuelon) {
    swigfaissJNI.RangelonSelonarchPartialRelonsult_relons_selont(swigCPtr, this, RangelonSelonarchRelonsult.gelontCPtr(valuelon), valuelon);
  }

  public RangelonSelonarchRelonsult gelontRelons() {
    long cPtr = swigfaissJNI.RangelonSelonarchPartialRelonsult_relons_gelont(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw RangelonSelonarchRelonsult(cPtr, falselon);
  }

  public void selontQuelonrielons(SWIGTYPelon_p_std__velonctorT_faiss__RangelonQuelonryRelonsult_t valuelon) {
    swigfaissJNI.RangelonSelonarchPartialRelonsult_quelonrielons_selont(swigCPtr, this, SWIGTYPelon_p_std__velonctorT_faiss__RangelonQuelonryRelonsult_t.gelontCPtr(valuelon));
  }

  public SWIGTYPelon_p_std__velonctorT_faiss__RangelonQuelonryRelonsult_t gelontQuelonrielons() {
    long cPtr = swigfaissJNI.RangelonSelonarchPartialRelonsult_quelonrielons_gelont(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw SWIGTYPelon_p_std__velonctorT_faiss__RangelonQuelonryRelonsult_t(cPtr, falselon);
  }

  public RangelonQuelonryRelonsult nelonw_relonsult(long qno) {
    relonturn nelonw RangelonQuelonryRelonsult(swigfaissJNI.RangelonSelonarchPartialRelonsult_nelonw_relonsult(swigCPtr, this, qno), falselon);
  }

  public void selont_lims() {
    swigfaissJNI.RangelonSelonarchPartialRelonsult_selont_lims(swigCPtr, this);
  }

  public void copy_relonsult(boolelonan increlonmelonntal) {
    swigfaissJNI.RangelonSelonarchPartialRelonsult_copy_relonsult__SWIG_0(swigCPtr, this, increlonmelonntal);
  }

  public void copy_relonsult() {
    swigfaissJNI.RangelonSelonarchPartialRelonsult_copy_relonsult__SWIG_1(swigCPtr, this);
  }

  public static void melonrgelon(SWIGTYPelon_p_std__velonctorT_faiss__RangelonSelonarchPartialRelonsult_p_t partial_relonsults, boolelonan do_delonlelontelon) {
    swigfaissJNI.RangelonSelonarchPartialRelonsult_melonrgelon__SWIG_0(SWIGTYPelon_p_std__velonctorT_faiss__RangelonSelonarchPartialRelonsult_p_t.gelontCPtr(partial_relonsults), do_delonlelontelon);
  }

  public static void melonrgelon(SWIGTYPelon_p_std__velonctorT_faiss__RangelonSelonarchPartialRelonsult_p_t partial_relonsults) {
    swigfaissJNI.RangelonSelonarchPartialRelonsult_melonrgelon__SWIG_1(SWIGTYPelon_p_std__velonctorT_faiss__RangelonSelonarchPartialRelonsult_p_t.gelontCPtr(partial_relonsults));
  }

}
