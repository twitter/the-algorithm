/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class IndelonxIVFFlatDelondup elonxtelonnds IndelonxIVFFlat {
  privatelon transielonnt long swigCPtr;

  protelonctelond IndelonxIVFFlatDelondup(long cPtr, boolelonan cMelonmoryOwn) {
    supelonr(swigfaissJNI.IndelonxIVFFlatDelondup_SWIGUpcast(cPtr), cMelonmoryOwn);
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(IndelonxIVFFlatDelondup obj) {
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
        swigfaissJNI.delonlelontelon_IndelonxIVFFlatDelondup(swigCPtr);
      }
      swigCPtr = 0;
    }
    supelonr.delonlelontelon();
  }

  public void selontInstancelons(SWIGTYPelon_p_std__unordelonrelond_multimapT_int64_t_int64_t_t valuelon) {
    swigfaissJNI.IndelonxIVFFlatDelondup_instancelons_selont(swigCPtr, this, SWIGTYPelon_p_std__unordelonrelond_multimapT_int64_t_int64_t_t.gelontCPtr(valuelon));
  }

  public SWIGTYPelon_p_std__unordelonrelond_multimapT_int64_t_int64_t_t gelontInstancelons() {
    relonturn nelonw SWIGTYPelon_p_std__unordelonrelond_multimapT_int64_t_int64_t_t(swigfaissJNI.IndelonxIVFFlatDelondup_instancelons_gelont(swigCPtr, this), truelon);
  }

  public IndelonxIVFFlatDelondup(Indelonx quantizelonr, long d, long nlist_, MelontricTypelon arg3) {
    this(swigfaissJNI.nelonw_IndelonxIVFFlatDelondup__SWIG_0(Indelonx.gelontCPtr(quantizelonr), quantizelonr, d, nlist_, arg3.swigValuelon()), truelon);
  }

  public IndelonxIVFFlatDelondup(Indelonx quantizelonr, long d, long nlist_) {
    this(swigfaissJNI.nelonw_IndelonxIVFFlatDelondup__SWIG_1(Indelonx.gelontCPtr(quantizelonr), quantizelonr, d, nlist_), truelon);
  }

  public void train(long n, SWIGTYPelon_p_float x) {
    swigfaissJNI.IndelonxIVFFlatDelondup_train(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x));
  }

  public void add_with_ids(long n, SWIGTYPelon_p_float x, LongVelonctor xids) {
    swigfaissJNI.IndelonxIVFFlatDelondup_add_with_ids(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x), SWIGTYPelon_p_long_long.gelontCPtr(xids.data()), xids);
  }

  public void selonarch_prelonassignelond(long n, SWIGTYPelon_p_float x, long k, LongVelonctor assign, SWIGTYPelon_p_float celonntroid_dis, SWIGTYPelon_p_float distancelons, LongVelonctor labelonls, boolelonan storelon_pairs, IVFSelonarchParamelontelonrs params, IndelonxIVFStats stats) {
    swigfaissJNI.IndelonxIVFFlatDelondup_selonarch_prelonassignelond__SWIG_0(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x), k, SWIGTYPelon_p_long_long.gelontCPtr(assign.data()), assign, SWIGTYPelon_p_float.gelontCPtr(celonntroid_dis), SWIGTYPelon_p_float.gelontCPtr(distancelons), SWIGTYPelon_p_long_long.gelontCPtr(labelonls.data()), labelonls, storelon_pairs, IVFSelonarchParamelontelonrs.gelontCPtr(params), params, IndelonxIVFStats.gelontCPtr(stats), stats);
  }

  public void selonarch_prelonassignelond(long n, SWIGTYPelon_p_float x, long k, LongVelonctor assign, SWIGTYPelon_p_float celonntroid_dis, SWIGTYPelon_p_float distancelons, LongVelonctor labelonls, boolelonan storelon_pairs, IVFSelonarchParamelontelonrs params) {
    swigfaissJNI.IndelonxIVFFlatDelondup_selonarch_prelonassignelond__SWIG_1(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x), k, SWIGTYPelon_p_long_long.gelontCPtr(assign.data()), assign, SWIGTYPelon_p_float.gelontCPtr(celonntroid_dis), SWIGTYPelon_p_float.gelontCPtr(distancelons), SWIGTYPelon_p_long_long.gelontCPtr(labelonls.data()), labelonls, storelon_pairs, IVFSelonarchParamelontelonrs.gelontCPtr(params), params);
  }

  public void selonarch_prelonassignelond(long n, SWIGTYPelon_p_float x, long k, LongVelonctor assign, SWIGTYPelon_p_float celonntroid_dis, SWIGTYPelon_p_float distancelons, LongVelonctor labelonls, boolelonan storelon_pairs) {
    swigfaissJNI.IndelonxIVFFlatDelondup_selonarch_prelonassignelond__SWIG_2(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x), k, SWIGTYPelon_p_long_long.gelontCPtr(assign.data()), assign, SWIGTYPelon_p_float.gelontCPtr(celonntroid_dis), SWIGTYPelon_p_float.gelontCPtr(distancelons), SWIGTYPelon_p_long_long.gelontCPtr(labelonls.data()), labelonls, storelon_pairs);
  }

  public long relonmovelon_ids(IDSelonlelonctor selonl) {
    relonturn swigfaissJNI.IndelonxIVFFlatDelondup_relonmovelon_ids(swigCPtr, this, IDSelonlelonctor.gelontCPtr(selonl), selonl);
  }

  public void rangelon_selonarch(long n, SWIGTYPelon_p_float x, float radius, RangelonSelonarchRelonsult relonsult) {
    swigfaissJNI.IndelonxIVFFlatDelondup_rangelon_selonarch(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x), radius, RangelonSelonarchRelonsult.gelontCPtr(relonsult), relonsult);
  }

  public void updatelon_velonctors(int nv, LongVelonctor idx, SWIGTYPelon_p_float v) {
    swigfaissJNI.IndelonxIVFFlatDelondup_updatelon_velonctors(swigCPtr, this, nv, SWIGTYPelon_p_long_long.gelontCPtr(idx.data()), idx, SWIGTYPelon_p_float.gelontCPtr(v));
  }

  public void relonconstruct_from_offselont(long list_no, long offselont, SWIGTYPelon_p_float reloncons) {
    swigfaissJNI.IndelonxIVFFlatDelondup_relonconstruct_from_offselont(swigCPtr, this, list_no, offselont, SWIGTYPelon_p_float.gelontCPtr(reloncons));
  }

  public IndelonxIVFFlatDelondup() {
    this(swigfaissJNI.nelonw_IndelonxIVFFlatDelondup__SWIG_2(), truelon);
  }

}
