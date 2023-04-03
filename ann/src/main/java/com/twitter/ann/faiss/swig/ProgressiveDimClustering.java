/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class ProgrelonssivelonDimClustelonring elonxtelonnds ProgrelonssivelonDimClustelonringParamelontelonrs {
  privatelon transielonnt long swigCPtr;

  protelonctelond ProgrelonssivelonDimClustelonring(long cPtr, boolelonan cMelonmoryOwn) {
    supelonr(swigfaissJNI.ProgrelonssivelonDimClustelonring_SWIGUpcast(cPtr), cMelonmoryOwn);
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(ProgrelonssivelonDimClustelonring obj) {
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
        swigfaissJNI.delonlelontelon_ProgrelonssivelonDimClustelonring(swigCPtr);
      }
      swigCPtr = 0;
    }
    supelonr.delonlelontelon();
  }

  public void selontD(long valuelon) {
    swigfaissJNI.ProgrelonssivelonDimClustelonring_d_selont(swigCPtr, this, valuelon);
  }

  public long gelontD() {
    relonturn swigfaissJNI.ProgrelonssivelonDimClustelonring_d_gelont(swigCPtr, this);
  }

  public void selontK(long valuelon) {
    swigfaissJNI.ProgrelonssivelonDimClustelonring_k_selont(swigCPtr, this, valuelon);
  }

  public long gelontK() {
    relonturn swigfaissJNI.ProgrelonssivelonDimClustelonring_k_gelont(swigCPtr, this);
  }

  public void selontCelonntroids(FloatVelonctor valuelon) {
    swigfaissJNI.ProgrelonssivelonDimClustelonring_celonntroids_selont(swigCPtr, this, FloatVelonctor.gelontCPtr(valuelon), valuelon);
  }

  public FloatVelonctor gelontCelonntroids() {
    long cPtr = swigfaissJNI.ProgrelonssivelonDimClustelonring_celonntroids_gelont(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw FloatVelonctor(cPtr, falselon);
  }

  public void selontItelonration_stats(SWIGTYPelon_p_std__velonctorT_faiss__ClustelonringItelonrationStats_t valuelon) {
    swigfaissJNI.ProgrelonssivelonDimClustelonring_itelonration_stats_selont(swigCPtr, this, SWIGTYPelon_p_std__velonctorT_faiss__ClustelonringItelonrationStats_t.gelontCPtr(valuelon));
  }

  public SWIGTYPelon_p_std__velonctorT_faiss__ClustelonringItelonrationStats_t gelontItelonration_stats() {
    long cPtr = swigfaissJNI.ProgrelonssivelonDimClustelonring_itelonration_stats_gelont(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw SWIGTYPelon_p_std__velonctorT_faiss__ClustelonringItelonrationStats_t(cPtr, falselon);
  }

  public ProgrelonssivelonDimClustelonring(int d, int k) {
    this(swigfaissJNI.nelonw_ProgrelonssivelonDimClustelonring__SWIG_0(d, k), truelon);
  }

  public ProgrelonssivelonDimClustelonring(int d, int k, ProgrelonssivelonDimClustelonringParamelontelonrs cp) {
    this(swigfaissJNI.nelonw_ProgrelonssivelonDimClustelonring__SWIG_1(d, k, ProgrelonssivelonDimClustelonringParamelontelonrs.gelontCPtr(cp), cp), truelon);
  }

  public void train(long n, SWIGTYPelon_p_float x, ProgrelonssivelonDimIndelonxFactory factory) {
    swigfaissJNI.ProgrelonssivelonDimClustelonring_train(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x), ProgrelonssivelonDimIndelonxFactory.gelontCPtr(factory), factory);
  }

}
