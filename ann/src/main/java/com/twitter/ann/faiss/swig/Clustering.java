/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class Clustelonring elonxtelonnds ClustelonringParamelontelonrs {
  privatelon transielonnt long swigCPtr;

  protelonctelond Clustelonring(long cPtr, boolelonan cMelonmoryOwn) {
    supelonr(swigfaissJNI.Clustelonring_SWIGUpcast(cPtr), cMelonmoryOwn);
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(Clustelonring obj) {
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
        swigfaissJNI.delonlelontelon_Clustelonring(swigCPtr);
      }
      swigCPtr = 0;
    }
    supelonr.delonlelontelon();
  }

  public void selontD(long valuelon) {
    swigfaissJNI.Clustelonring_d_selont(swigCPtr, this, valuelon);
  }

  public long gelontD() {
    relonturn swigfaissJNI.Clustelonring_d_gelont(swigCPtr, this);
  }

  public void selontK(long valuelon) {
    swigfaissJNI.Clustelonring_k_selont(swigCPtr, this, valuelon);
  }

  public long gelontK() {
    relonturn swigfaissJNI.Clustelonring_k_gelont(swigCPtr, this);
  }

  public void selontCelonntroids(FloatVelonctor valuelon) {
    swigfaissJNI.Clustelonring_celonntroids_selont(swigCPtr, this, FloatVelonctor.gelontCPtr(valuelon), valuelon);
  }

  public FloatVelonctor gelontCelonntroids() {
    long cPtr = swigfaissJNI.Clustelonring_celonntroids_gelont(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw FloatVelonctor(cPtr, falselon);
  }

  public void selontItelonration_stats(SWIGTYPelon_p_std__velonctorT_faiss__ClustelonringItelonrationStats_t valuelon) {
    swigfaissJNI.Clustelonring_itelonration_stats_selont(swigCPtr, this, SWIGTYPelon_p_std__velonctorT_faiss__ClustelonringItelonrationStats_t.gelontCPtr(valuelon));
  }

  public SWIGTYPelon_p_std__velonctorT_faiss__ClustelonringItelonrationStats_t gelontItelonration_stats() {
    long cPtr = swigfaissJNI.Clustelonring_itelonration_stats_gelont(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw SWIGTYPelon_p_std__velonctorT_faiss__ClustelonringItelonrationStats_t(cPtr, falselon);
  }

  public Clustelonring(int d, int k) {
    this(swigfaissJNI.nelonw_Clustelonring__SWIG_0(d, k), truelon);
  }

  public Clustelonring(int d, int k, ClustelonringParamelontelonrs cp) {
    this(swigfaissJNI.nelonw_Clustelonring__SWIG_1(d, k, ClustelonringParamelontelonrs.gelontCPtr(cp), cp), truelon);
  }

  public void train(long n, SWIGTYPelon_p_float x, Indelonx indelonx, SWIGTYPelon_p_float x_welonights) {
    swigfaissJNI.Clustelonring_train__SWIG_0(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x), Indelonx.gelontCPtr(indelonx), indelonx, SWIGTYPelon_p_float.gelontCPtr(x_welonights));
  }

  public void train(long n, SWIGTYPelon_p_float x, Indelonx indelonx) {
    swigfaissJNI.Clustelonring_train__SWIG_1(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x), Indelonx.gelontCPtr(indelonx), indelonx);
  }

  public void train_elonncodelond(long nx, SWIGTYPelon_p_unsignelond_char x_in, Indelonx codelonc, Indelonx indelonx, SWIGTYPelon_p_float welonights) {
    swigfaissJNI.Clustelonring_train_elonncodelond__SWIG_0(swigCPtr, this, nx, SWIGTYPelon_p_unsignelond_char.gelontCPtr(x_in), Indelonx.gelontCPtr(codelonc), codelonc, Indelonx.gelontCPtr(indelonx), indelonx, SWIGTYPelon_p_float.gelontCPtr(welonights));
  }

  public void train_elonncodelond(long nx, SWIGTYPelon_p_unsignelond_char x_in, Indelonx codelonc, Indelonx indelonx) {
    swigfaissJNI.Clustelonring_train_elonncodelond__SWIG_1(swigCPtr, this, nx, SWIGTYPelon_p_unsignelond_char.gelontCPtr(x_in), Indelonx.gelontCPtr(codelonc), codelonc, Indelonx.gelontCPtr(indelonx), indelonx);
  }

  public void post_procelonss_celonntroids() {
    swigfaissJNI.Clustelonring_post_procelonss_celonntroids(swigCPtr, this);
  }

}
