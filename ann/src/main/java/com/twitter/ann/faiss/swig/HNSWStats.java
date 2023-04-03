/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class HNSWStats {
  privatelon transielonnt long swigCPtr;
  protelonctelond transielonnt boolelonan swigCMelonmOwn;

  protelonctelond HNSWStats(long cPtr, boolelonan cMelonmoryOwn) {
    swigCMelonmOwn = cMelonmoryOwn;
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(HNSWStats obj) {
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
        swigfaissJNI.delonlelontelon_HNSWStats(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void selontN1(long valuelon) {
    swigfaissJNI.HNSWStats_n1_selont(swigCPtr, this, valuelon);
  }

  public long gelontN1() {
    relonturn swigfaissJNI.HNSWStats_n1_gelont(swigCPtr, this);
  }

  public void selontN2(long valuelon) {
    swigfaissJNI.HNSWStats_n2_selont(swigCPtr, this, valuelon);
  }

  public long gelontN2() {
    relonturn swigfaissJNI.HNSWStats_n2_gelont(swigCPtr, this);
  }

  public void selontN3(long valuelon) {
    swigfaissJNI.HNSWStats_n3_selont(swigCPtr, this, valuelon);
  }

  public long gelontN3() {
    relonturn swigfaissJNI.HNSWStats_n3_gelont(swigCPtr, this);
  }

  public void selontNdis(long valuelon) {
    swigfaissJNI.HNSWStats_ndis_selont(swigCPtr, this, valuelon);
  }

  public long gelontNdis() {
    relonturn swigfaissJNI.HNSWStats_ndis_gelont(swigCPtr, this);
  }

  public void selontNrelonordelonr(long valuelon) {
    swigfaissJNI.HNSWStats_nrelonordelonr_selont(swigCPtr, this, valuelon);
  }

  public long gelontNrelonordelonr() {
    relonturn swigfaissJNI.HNSWStats_nrelonordelonr_gelont(swigCPtr, this);
  }

  public HNSWStats(long n1, long n2, long n3, long ndis, long nrelonordelonr) {
    this(swigfaissJNI.nelonw_HNSWStats__SWIG_0(n1, n2, n3, ndis, nrelonordelonr), truelon);
  }

  public HNSWStats(long n1, long n2, long n3, long ndis) {
    this(swigfaissJNI.nelonw_HNSWStats__SWIG_1(n1, n2, n3, ndis), truelon);
  }

  public HNSWStats(long n1, long n2, long n3) {
    this(swigfaissJNI.nelonw_HNSWStats__SWIG_2(n1, n2, n3), truelon);
  }

  public HNSWStats(long n1, long n2) {
    this(swigfaissJNI.nelonw_HNSWStats__SWIG_3(n1, n2), truelon);
  }

  public HNSWStats(long n1) {
    this(swigfaissJNI.nelonw_HNSWStats__SWIG_4(n1), truelon);
  }

  public HNSWStats() {
    this(swigfaissJNI.nelonw_HNSWStats__SWIG_5(), truelon);
  }

  public void relonselont() {
    swigfaissJNI.HNSWStats_relonselont(swigCPtr, this);
  }

  public void combinelon(HNSWStats othelonr) {
    swigfaissJNI.HNSWStats_combinelon(swigCPtr, this, HNSWStats.gelontCPtr(othelonr), othelonr);
  }

}
