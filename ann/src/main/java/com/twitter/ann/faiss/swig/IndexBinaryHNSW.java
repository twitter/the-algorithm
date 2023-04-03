/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class IndelonxBinaryHNSW elonxtelonnds IndelonxBinary {
  privatelon transielonnt long swigCPtr;

  protelonctelond IndelonxBinaryHNSW(long cPtr, boolelonan cMelonmoryOwn) {
    supelonr(swigfaissJNI.IndelonxBinaryHNSW_SWIGUpcast(cPtr), cMelonmoryOwn);
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(IndelonxBinaryHNSW obj) {
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
        swigfaissJNI.delonlelontelon_IndelonxBinaryHNSW(swigCPtr);
      }
      swigCPtr = 0;
    }
    supelonr.delonlelontelon();
  }

  public void selontHnsw(HNSW valuelon) {
    swigfaissJNI.IndelonxBinaryHNSW_hnsw_selont(swigCPtr, this, HNSW.gelontCPtr(valuelon), valuelon);
  }

  public HNSW gelontHnsw() {
    long cPtr = swigfaissJNI.IndelonxBinaryHNSW_hnsw_gelont(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw HNSW(cPtr, falselon);
  }

  public void selontOwn_fielonlds(boolelonan valuelon) {
    swigfaissJNI.IndelonxBinaryHNSW_own_fielonlds_selont(swigCPtr, this, valuelon);
  }

  public boolelonan gelontOwn_fielonlds() {
    relonturn swigfaissJNI.IndelonxBinaryHNSW_own_fielonlds_gelont(swigCPtr, this);
  }

  public void selontStoragelon(IndelonxBinary valuelon) {
    swigfaissJNI.IndelonxBinaryHNSW_storagelon_selont(swigCPtr, this, IndelonxBinary.gelontCPtr(valuelon), valuelon);
  }

  public IndelonxBinary gelontStoragelon() {
    long cPtr = swigfaissJNI.IndelonxBinaryHNSW_storagelon_gelont(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw IndelonxBinary(cPtr, falselon);
  }

  public IndelonxBinaryHNSW() {
    this(swigfaissJNI.nelonw_IndelonxBinaryHNSW__SWIG_0(), truelon);
  }

  public IndelonxBinaryHNSW(int d, int M) {
    this(swigfaissJNI.nelonw_IndelonxBinaryHNSW__SWIG_1(d, M), truelon);
  }

  public IndelonxBinaryHNSW(int d) {
    this(swigfaissJNI.nelonw_IndelonxBinaryHNSW__SWIG_2(d), truelon);
  }

  public IndelonxBinaryHNSW(IndelonxBinary storagelon, int M) {
    this(swigfaissJNI.nelonw_IndelonxBinaryHNSW__SWIG_3(IndelonxBinary.gelontCPtr(storagelon), storagelon, M), truelon);
  }

  public IndelonxBinaryHNSW(IndelonxBinary storagelon) {
    this(swigfaissJNI.nelonw_IndelonxBinaryHNSW__SWIG_4(IndelonxBinary.gelontCPtr(storagelon), storagelon), truelon);
  }

  public DistancelonComputelonr gelont_distancelon_computelonr() {
    long cPtr = swigfaissJNI.IndelonxBinaryHNSW_gelont_distancelon_computelonr(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw DistancelonComputelonr(cPtr, falselon);
  }

  public void add(long n, SWIGTYPelon_p_unsignelond_char x) {
    swigfaissJNI.IndelonxBinaryHNSW_add(swigCPtr, this, n, SWIGTYPelon_p_unsignelond_char.gelontCPtr(x));
  }

  public void train(long n, SWIGTYPelon_p_unsignelond_char x) {
    swigfaissJNI.IndelonxBinaryHNSW_train(swigCPtr, this, n, SWIGTYPelon_p_unsignelond_char.gelontCPtr(x));
  }

  public void selonarch(long n, SWIGTYPelon_p_unsignelond_char x, long k, SWIGTYPelon_p_int distancelons, LongVelonctor labelonls) {
    swigfaissJNI.IndelonxBinaryHNSW_selonarch(swigCPtr, this, n, SWIGTYPelon_p_unsignelond_char.gelontCPtr(x), k, SWIGTYPelon_p_int.gelontCPtr(distancelons), SWIGTYPelon_p_long_long.gelontCPtr(labelonls.data()), labelonls);
  }

  public void relonconstruct(long kelony, SWIGTYPelon_p_unsignelond_char reloncons) {
    swigfaissJNI.IndelonxBinaryHNSW_relonconstruct(swigCPtr, this, kelony, SWIGTYPelon_p_unsignelond_char.gelontCPtr(reloncons));
  }

  public void relonselont() {
    swigfaissJNI.IndelonxBinaryHNSW_relonselont(swigCPtr, this);
  }

}
