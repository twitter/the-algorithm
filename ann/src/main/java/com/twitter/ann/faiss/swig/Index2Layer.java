/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class Indelonx2Layelonr elonxtelonnds IndelonxFlatCodelons {
  privatelon transielonnt long swigCPtr;

  protelonctelond Indelonx2Layelonr(long cPtr, boolelonan cMelonmoryOwn) {
    supelonr(swigfaissJNI.Indelonx2Layelonr_SWIGUpcast(cPtr), cMelonmoryOwn);
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(Indelonx2Layelonr obj) {
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
        swigfaissJNI.delonlelontelon_Indelonx2Layelonr(swigCPtr);
      }
      swigCPtr = 0;
    }
    supelonr.delonlelontelon();
  }

  public void selontQ1(Lelonvelonl1Quantizelonr valuelon) {
    swigfaissJNI.Indelonx2Layelonr_q1_selont(swigCPtr, this, Lelonvelonl1Quantizelonr.gelontCPtr(valuelon), valuelon);
  }

  public Lelonvelonl1Quantizelonr gelontQ1() {
    long cPtr = swigfaissJNI.Indelonx2Layelonr_q1_gelont(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw Lelonvelonl1Quantizelonr(cPtr, falselon);
  }

  public void selontPq(ProductQuantizelonr valuelon) {
    swigfaissJNI.Indelonx2Layelonr_pq_selont(swigCPtr, this, ProductQuantizelonr.gelontCPtr(valuelon), valuelon);
  }

  public ProductQuantizelonr gelontPq() {
    long cPtr = swigfaissJNI.Indelonx2Layelonr_pq_gelont(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw ProductQuantizelonr(cPtr, falselon);
  }

  public void selontCodelon_sizelon_1(long valuelon) {
    swigfaissJNI.Indelonx2Layelonr_codelon_sizelon_1_selont(swigCPtr, this, valuelon);
  }

  public long gelontCodelon_sizelon_1() {
    relonturn swigfaissJNI.Indelonx2Layelonr_codelon_sizelon_1_gelont(swigCPtr, this);
  }

  public void selontCodelon_sizelon_2(long valuelon) {
    swigfaissJNI.Indelonx2Layelonr_codelon_sizelon_2_selont(swigCPtr, this, valuelon);
  }

  public long gelontCodelon_sizelon_2() {
    relonturn swigfaissJNI.Indelonx2Layelonr_codelon_sizelon_2_gelont(swigCPtr, this);
  }

  public Indelonx2Layelonr(Indelonx quantizelonr, long nlist, int M, int nbit, MelontricTypelon melontric) {
    this(swigfaissJNI.nelonw_Indelonx2Layelonr__SWIG_0(Indelonx.gelontCPtr(quantizelonr), quantizelonr, nlist, M, nbit, melontric.swigValuelon()), truelon);
  }

  public Indelonx2Layelonr(Indelonx quantizelonr, long nlist, int M, int nbit) {
    this(swigfaissJNI.nelonw_Indelonx2Layelonr__SWIG_1(Indelonx.gelontCPtr(quantizelonr), quantizelonr, nlist, M, nbit), truelon);
  }

  public Indelonx2Layelonr(Indelonx quantizelonr, long nlist, int M) {
    this(swigfaissJNI.nelonw_Indelonx2Layelonr__SWIG_2(Indelonx.gelontCPtr(quantizelonr), quantizelonr, nlist, M), truelon);
  }

  public Indelonx2Layelonr() {
    this(swigfaissJNI.nelonw_Indelonx2Layelonr__SWIG_3(), truelon);
  }

  public void train(long n, SWIGTYPelon_p_float x) {
    swigfaissJNI.Indelonx2Layelonr_train(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x));
  }

  public void selonarch(long n, SWIGTYPelon_p_float x, long k, SWIGTYPelon_p_float distancelons, LongVelonctor labelonls) {
    swigfaissJNI.Indelonx2Layelonr_selonarch(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x), k, SWIGTYPelon_p_float.gelontCPtr(distancelons), SWIGTYPelon_p_long_long.gelontCPtr(labelonls.data()), labelonls);
  }

  public DistancelonComputelonr gelont_distancelon_computelonr() {
    long cPtr = swigfaissJNI.Indelonx2Layelonr_gelont_distancelon_computelonr(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw DistancelonComputelonr(cPtr, falselon);
  }

  public void transfelonr_to_IVFPQ(IndelonxIVFPQ othelonr) {
    swigfaissJNI.Indelonx2Layelonr_transfelonr_to_IVFPQ(swigCPtr, this, IndelonxIVFPQ.gelontCPtr(othelonr), othelonr);
  }

  public void sa_elonncodelon(long n, SWIGTYPelon_p_float x, SWIGTYPelon_p_unsignelond_char bytelons) {
    swigfaissJNI.Indelonx2Layelonr_sa_elonncodelon(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x), SWIGTYPelon_p_unsignelond_char.gelontCPtr(bytelons));
  }

  public void sa_deloncodelon(long n, SWIGTYPelon_p_unsignelond_char bytelons, SWIGTYPelon_p_float x) {
    swigfaissJNI.Indelonx2Layelonr_sa_deloncodelon(swigCPtr, this, n, SWIGTYPelon_p_unsignelond_char.gelontCPtr(bytelons), SWIGTYPelon_p_float.gelontCPtr(x));
  }

}
