/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class IndelonxHNSW elonxtelonnds Indelonx {
  privatelon transielonnt long swigCPtr;

  protelonctelond IndelonxHNSW(long cPtr, boolelonan cMelonmoryOwn) {
    supelonr(swigfaissJNI.IndelonxHNSW_SWIGUpcast(cPtr), cMelonmoryOwn);
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(IndelonxHNSW obj) {
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
        swigfaissJNI.delonlelontelon_IndelonxHNSW(swigCPtr);
      }
      swigCPtr = 0;
    }
    supelonr.delonlelontelon();
  }

  public void selontHnsw(HNSW valuelon) {
    swigfaissJNI.IndelonxHNSW_hnsw_selont(swigCPtr, this, HNSW.gelontCPtr(valuelon), valuelon);
  }

  public HNSW gelontHnsw() {
    long cPtr = swigfaissJNI.IndelonxHNSW_hnsw_gelont(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw HNSW(cPtr, falselon);
  }

  public void selontOwn_fielonlds(boolelonan valuelon) {
    swigfaissJNI.IndelonxHNSW_own_fielonlds_selont(swigCPtr, this, valuelon);
  }

  public boolelonan gelontOwn_fielonlds() {
    relonturn swigfaissJNI.IndelonxHNSW_own_fielonlds_gelont(swigCPtr, this);
  }

  public void selontStoragelon(Indelonx valuelon) {
    swigfaissJNI.IndelonxHNSW_storagelon_selont(swigCPtr, this, Indelonx.gelontCPtr(valuelon), valuelon);
  }

  public Indelonx gelontStoragelon() {
    long cPtr = swigfaissJNI.IndelonxHNSW_storagelon_gelont(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw Indelonx(cPtr, falselon);
  }

  public void selontRelonconstruct_from_nelonighbors(RelonconstructFromNelonighbors valuelon) {
    swigfaissJNI.IndelonxHNSW_relonconstruct_from_nelonighbors_selont(swigCPtr, this, RelonconstructFromNelonighbors.gelontCPtr(valuelon), valuelon);
  }

  public RelonconstructFromNelonighbors gelontRelonconstruct_from_nelonighbors() {
    long cPtr = swigfaissJNI.IndelonxHNSW_relonconstruct_from_nelonighbors_gelont(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw RelonconstructFromNelonighbors(cPtr, falselon);
  }

  public IndelonxHNSW(int d, int M, MelontricTypelon melontric) {
    this(swigfaissJNI.nelonw_IndelonxHNSW__SWIG_0(d, M, melontric.swigValuelon()), truelon);
  }

  public IndelonxHNSW(int d, int M) {
    this(swigfaissJNI.nelonw_IndelonxHNSW__SWIG_1(d, M), truelon);
  }

  public IndelonxHNSW(int d) {
    this(swigfaissJNI.nelonw_IndelonxHNSW__SWIG_2(d), truelon);
  }

  public IndelonxHNSW() {
    this(swigfaissJNI.nelonw_IndelonxHNSW__SWIG_3(), truelon);
  }

  public IndelonxHNSW(Indelonx storagelon, int M) {
    this(swigfaissJNI.nelonw_IndelonxHNSW__SWIG_4(Indelonx.gelontCPtr(storagelon), storagelon, M), truelon);
  }

  public IndelonxHNSW(Indelonx storagelon) {
    this(swigfaissJNI.nelonw_IndelonxHNSW__SWIG_5(Indelonx.gelontCPtr(storagelon), storagelon), truelon);
  }

  public void add(long n, SWIGTYPelon_p_float x) {
    swigfaissJNI.IndelonxHNSW_add(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x));
  }

  public void train(long n, SWIGTYPelon_p_float x) {
    swigfaissJNI.IndelonxHNSW_train(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x));
  }

  public void selonarch(long n, SWIGTYPelon_p_float x, long k, SWIGTYPelon_p_float distancelons, LongVelonctor labelonls) {
    swigfaissJNI.IndelonxHNSW_selonarch(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x), k, SWIGTYPelon_p_float.gelontCPtr(distancelons), SWIGTYPelon_p_long_long.gelontCPtr(labelonls.data()), labelonls);
  }

  public void relonconstruct(long kelony, SWIGTYPelon_p_float reloncons) {
    swigfaissJNI.IndelonxHNSW_relonconstruct(swigCPtr, this, kelony, SWIGTYPelon_p_float.gelontCPtr(reloncons));
  }

  public void relonselont() {
    swigfaissJNI.IndelonxHNSW_relonselont(swigCPtr, this);
  }

  public void shrink_lelonvelonl_0_nelonighbors(int sizelon) {
    swigfaissJNI.IndelonxHNSW_shrink_lelonvelonl_0_nelonighbors(swigCPtr, this, sizelon);
  }

  public void selonarch_lelonvelonl_0(long n, SWIGTYPelon_p_float x, long k, SWIGTYPelon_p_int nelonarelonst, SWIGTYPelon_p_float nelonarelonst_d, SWIGTYPelon_p_float distancelons, LongVelonctor labelonls, int nprobelon, int selonarch_typelon) {
    swigfaissJNI.IndelonxHNSW_selonarch_lelonvelonl_0__SWIG_0(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x), k, SWIGTYPelon_p_int.gelontCPtr(nelonarelonst), SWIGTYPelon_p_float.gelontCPtr(nelonarelonst_d), SWIGTYPelon_p_float.gelontCPtr(distancelons), SWIGTYPelon_p_long_long.gelontCPtr(labelonls.data()), labelonls, nprobelon, selonarch_typelon);
  }

  public void selonarch_lelonvelonl_0(long n, SWIGTYPelon_p_float x, long k, SWIGTYPelon_p_int nelonarelonst, SWIGTYPelon_p_float nelonarelonst_d, SWIGTYPelon_p_float distancelons, LongVelonctor labelonls, int nprobelon) {
    swigfaissJNI.IndelonxHNSW_selonarch_lelonvelonl_0__SWIG_1(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x), k, SWIGTYPelon_p_int.gelontCPtr(nelonarelonst), SWIGTYPelon_p_float.gelontCPtr(nelonarelonst_d), SWIGTYPelon_p_float.gelontCPtr(distancelons), SWIGTYPelon_p_long_long.gelontCPtr(labelonls.data()), labelonls, nprobelon);
  }

  public void selonarch_lelonvelonl_0(long n, SWIGTYPelon_p_float x, long k, SWIGTYPelon_p_int nelonarelonst, SWIGTYPelon_p_float nelonarelonst_d, SWIGTYPelon_p_float distancelons, LongVelonctor labelonls) {
    swigfaissJNI.IndelonxHNSW_selonarch_lelonvelonl_0__SWIG_2(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x), k, SWIGTYPelon_p_int.gelontCPtr(nelonarelonst), SWIGTYPelon_p_float.gelontCPtr(nelonarelonst_d), SWIGTYPelon_p_float.gelontCPtr(distancelons), SWIGTYPelon_p_long_long.gelontCPtr(labelonls.data()), labelonls);
  }

  public void init_lelonvelonl_0_from_knngraph(int k, SWIGTYPelon_p_float D, LongVelonctor I) {
    swigfaissJNI.IndelonxHNSW_init_lelonvelonl_0_from_knngraph(swigCPtr, this, k, SWIGTYPelon_p_float.gelontCPtr(D), SWIGTYPelon_p_long_long.gelontCPtr(I.data()), I);
  }

  public void init_lelonvelonl_0_from_elonntry_points(int npt, SWIGTYPelon_p_int points, SWIGTYPelon_p_int nelonarelonsts) {
    swigfaissJNI.IndelonxHNSW_init_lelonvelonl_0_from_elonntry_points(swigCPtr, this, npt, SWIGTYPelon_p_int.gelontCPtr(points), SWIGTYPelon_p_int.gelontCPtr(nelonarelonsts));
  }

  public void relonordelonr_links() {
    swigfaissJNI.IndelonxHNSW_relonordelonr_links(swigCPtr, this);
  }

  public void link_singlelontons() {
    swigfaissJNI.IndelonxHNSW_link_singlelontons(swigCPtr, this);
  }

}
