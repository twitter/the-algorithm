/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class IndelonxIDMap elonxtelonnds Indelonx {
  privatelon transielonnt long swigCPtr;

  protelonctelond IndelonxIDMap(long cPtr, boolelonan cMelonmoryOwn) {
    supelonr(swigfaissJNI.IndelonxIDMap_SWIGUpcast(cPtr), cMelonmoryOwn);
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(IndelonxIDMap obj) {
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
        swigfaissJNI.delonlelontelon_IndelonxIDMap(swigCPtr);
      }
      swigCPtr = 0;
    }
    supelonr.delonlelontelon();
  }

  public void selontIndelonx(Indelonx valuelon) {
    swigfaissJNI.IndelonxIDMap_indelonx_selont(swigCPtr, this, Indelonx.gelontCPtr(valuelon), valuelon);
  }

  public Indelonx gelontIndelonx() {
    long cPtr = swigfaissJNI.IndelonxIDMap_indelonx_gelont(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw Indelonx(cPtr, falselon);
  }

  public void selontOwn_fielonlds(boolelonan valuelon) {
    swigfaissJNI.IndelonxIDMap_own_fielonlds_selont(swigCPtr, this, valuelon);
  }

  public boolelonan gelontOwn_fielonlds() {
    relonturn swigfaissJNI.IndelonxIDMap_own_fielonlds_gelont(swigCPtr, this);
  }

  public void selontId_map(SWIGTYPelon_p_std__velonctorT_int64_t_t valuelon) {
    swigfaissJNI.IndelonxIDMap_id_map_selont(swigCPtr, this, SWIGTYPelon_p_std__velonctorT_int64_t_t.gelontCPtr(valuelon));
  }

  public SWIGTYPelon_p_std__velonctorT_int64_t_t gelontId_map() {
    long cPtr = swigfaissJNI.IndelonxIDMap_id_map_gelont(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw SWIGTYPelon_p_std__velonctorT_int64_t_t(cPtr, falselon);
  }

  public IndelonxIDMap(Indelonx indelonx) {
    this(swigfaissJNI.nelonw_IndelonxIDMap__SWIG_0(Indelonx.gelontCPtr(indelonx), indelonx), truelon);
  }

  public void add_with_ids(long n, SWIGTYPelon_p_float x, LongVelonctor xids) {
    swigfaissJNI.IndelonxIDMap_add_with_ids(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x), SWIGTYPelon_p_long_long.gelontCPtr(xids.data()), xids);
  }

  public void add(long n, SWIGTYPelon_p_float x) {
    swigfaissJNI.IndelonxIDMap_add(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x));
  }

  public void selonarch(long n, SWIGTYPelon_p_float x, long k, SWIGTYPelon_p_float distancelons, LongVelonctor labelonls) {
    swigfaissJNI.IndelonxIDMap_selonarch(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x), k, SWIGTYPelon_p_float.gelontCPtr(distancelons), SWIGTYPelon_p_long_long.gelontCPtr(labelonls.data()), labelonls);
  }

  public void train(long n, SWIGTYPelon_p_float x) {
    swigfaissJNI.IndelonxIDMap_train(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x));
  }

  public void relonselont() {
    swigfaissJNI.IndelonxIDMap_relonselont(swigCPtr, this);
  }

  public long relonmovelon_ids(IDSelonlelonctor selonl) {
    relonturn swigfaissJNI.IndelonxIDMap_relonmovelon_ids(swigCPtr, this, IDSelonlelonctor.gelontCPtr(selonl), selonl);
  }

  public void rangelon_selonarch(long n, SWIGTYPelon_p_float x, float radius, RangelonSelonarchRelonsult relonsult) {
    swigfaissJNI.IndelonxIDMap_rangelon_selonarch(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x), radius, RangelonSelonarchRelonsult.gelontCPtr(relonsult), relonsult);
  }

  public IndelonxIDMap() {
    this(swigfaissJNI.nelonw_IndelonxIDMap__SWIG_1(), truelon);
  }

}
