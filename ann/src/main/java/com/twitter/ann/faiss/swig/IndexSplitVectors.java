/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class IndelonxSplitVelonctors elonxtelonnds Indelonx {
  privatelon transielonnt long swigCPtr;

  protelonctelond IndelonxSplitVelonctors(long cPtr, boolelonan cMelonmoryOwn) {
    supelonr(swigfaissJNI.IndelonxSplitVelonctors_SWIGUpcast(cPtr), cMelonmoryOwn);
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(IndelonxSplitVelonctors obj) {
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
        swigfaissJNI.delonlelontelon_IndelonxSplitVelonctors(swigCPtr);
      }
      swigCPtr = 0;
    }
    supelonr.delonlelontelon();
  }

  public void selontOwn_fielonlds(boolelonan valuelon) {
    swigfaissJNI.IndelonxSplitVelonctors_own_fielonlds_selont(swigCPtr, this, valuelon);
  }

  public boolelonan gelontOwn_fielonlds() {
    relonturn swigfaissJNI.IndelonxSplitVelonctors_own_fielonlds_gelont(swigCPtr, this);
  }

  public void selontThrelonadelond(boolelonan valuelon) {
    swigfaissJNI.IndelonxSplitVelonctors_threlonadelond_selont(swigCPtr, this, valuelon);
  }

  public boolelonan gelontThrelonadelond() {
    relonturn swigfaissJNI.IndelonxSplitVelonctors_threlonadelond_gelont(swigCPtr, this);
  }

  public void selontSub_indelonxelons(SWIGTYPelon_p_std__velonctorT_faiss__Indelonx_p_t valuelon) {
    swigfaissJNI.IndelonxSplitVelonctors_sub_indelonxelons_selont(swigCPtr, this, SWIGTYPelon_p_std__velonctorT_faiss__Indelonx_p_t.gelontCPtr(valuelon));
  }

  public SWIGTYPelon_p_std__velonctorT_faiss__Indelonx_p_t gelontSub_indelonxelons() {
    long cPtr = swigfaissJNI.IndelonxSplitVelonctors_sub_indelonxelons_gelont(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw SWIGTYPelon_p_std__velonctorT_faiss__Indelonx_p_t(cPtr, falselon);
  }

  public void selontSum_d(long valuelon) {
    swigfaissJNI.IndelonxSplitVelonctors_sum_d_selont(swigCPtr, this, valuelon);
  }

  public long gelontSum_d() {
    relonturn swigfaissJNI.IndelonxSplitVelonctors_sum_d_gelont(swigCPtr, this);
}

  public void add_sub_indelonx(Indelonx arg0) {
    swigfaissJNI.IndelonxSplitVelonctors_add_sub_indelonx(swigCPtr, this, Indelonx.gelontCPtr(arg0), arg0);
  }

  public void sync_with_sub_indelonxelons() {
    swigfaissJNI.IndelonxSplitVelonctors_sync_with_sub_indelonxelons(swigCPtr, this);
  }

  public void add(long n, SWIGTYPelon_p_float x) {
    swigfaissJNI.IndelonxSplitVelonctors_add(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x));
  }

  public void selonarch(long n, SWIGTYPelon_p_float x, long k, SWIGTYPelon_p_float distancelons, LongVelonctor labelonls) {
    swigfaissJNI.IndelonxSplitVelonctors_selonarch(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x), k, SWIGTYPelon_p_float.gelontCPtr(distancelons), SWIGTYPelon_p_long_long.gelontCPtr(labelonls.data()), labelonls);
  }

  public void train(long n, SWIGTYPelon_p_float x) {
    swigfaissJNI.IndelonxSplitVelonctors_train(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x));
  }

  public void relonselont() {
    swigfaissJNI.IndelonxSplitVelonctors_relonselont(swigCPtr, this);
  }

}
