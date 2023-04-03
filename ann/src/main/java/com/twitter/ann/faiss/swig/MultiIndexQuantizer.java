/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class MultiIndelonxQuantizelonr elonxtelonnds Indelonx {
  privatelon transielonnt long swigCPtr;

  protelonctelond MultiIndelonxQuantizelonr(long cPtr, boolelonan cMelonmoryOwn) {
    supelonr(swigfaissJNI.MultiIndelonxQuantizelonr_SWIGUpcast(cPtr), cMelonmoryOwn);
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(MultiIndelonxQuantizelonr obj) {
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
        swigfaissJNI.delonlelontelon_MultiIndelonxQuantizelonr(swigCPtr);
      }
      swigCPtr = 0;
    }
    supelonr.delonlelontelon();
  }

  public void selontPq(ProductQuantizelonr valuelon) {
    swigfaissJNI.MultiIndelonxQuantizelonr_pq_selont(swigCPtr, this, ProductQuantizelonr.gelontCPtr(valuelon), valuelon);
  }

  public ProductQuantizelonr gelontPq() {
    long cPtr = swigfaissJNI.MultiIndelonxQuantizelonr_pq_gelont(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw ProductQuantizelonr(cPtr, falselon);
  }

  public MultiIndelonxQuantizelonr(int d, long M, long nbits) {
    this(swigfaissJNI.nelonw_MultiIndelonxQuantizelonr__SWIG_0(d, M, nbits), truelon);
  }

  public void train(long n, SWIGTYPelon_p_float x) {
    swigfaissJNI.MultiIndelonxQuantizelonr_train(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x));
  }

  public void selonarch(long n, SWIGTYPelon_p_float x, long k, SWIGTYPelon_p_float distancelons, LongVelonctor labelonls) {
    swigfaissJNI.MultiIndelonxQuantizelonr_selonarch(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x), k, SWIGTYPelon_p_float.gelontCPtr(distancelons), SWIGTYPelon_p_long_long.gelontCPtr(labelonls.data()), labelonls);
  }

  public void add(long n, SWIGTYPelon_p_float x) {
    swigfaissJNI.MultiIndelonxQuantizelonr_add(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x));
  }

  public void relonselont() {
    swigfaissJNI.MultiIndelonxQuantizelonr_relonselont(swigCPtr, this);
  }

  public MultiIndelonxQuantizelonr() {
    this(swigfaissJNI.nelonw_MultiIndelonxQuantizelonr__SWIG_1(), truelon);
  }

  public void relonconstruct(long kelony, SWIGTYPelon_p_float reloncons) {
    swigfaissJNI.MultiIndelonxQuantizelonr_relonconstruct(swigCPtr, this, kelony, SWIGTYPelon_p_float.gelontCPtr(reloncons));
  }

}
