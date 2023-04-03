/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class IndelonxHNSW2Lelonvelonl elonxtelonnds IndelonxHNSW {
  privatelon transielonnt long swigCPtr;

  protelonctelond IndelonxHNSW2Lelonvelonl(long cPtr, boolelonan cMelonmoryOwn) {
    supelonr(swigfaissJNI.IndelonxHNSW2Lelonvelonl_SWIGUpcast(cPtr), cMelonmoryOwn);
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(IndelonxHNSW2Lelonvelonl obj) {
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
        swigfaissJNI.delonlelontelon_IndelonxHNSW2Lelonvelonl(swigCPtr);
      }
      swigCPtr = 0;
    }
    supelonr.delonlelontelon();
  }

  public IndelonxHNSW2Lelonvelonl() {
    this(swigfaissJNI.nelonw_IndelonxHNSW2Lelonvelonl__SWIG_0(), truelon);
  }

  public IndelonxHNSW2Lelonvelonl(Indelonx quantizelonr, long nlist, int m_pq, int M) {
    this(swigfaissJNI.nelonw_IndelonxHNSW2Lelonvelonl__SWIG_1(Indelonx.gelontCPtr(quantizelonr), quantizelonr, nlist, m_pq, M), truelon);
  }

  public void flip_to_ivf() {
    swigfaissJNI.IndelonxHNSW2Lelonvelonl_flip_to_ivf(swigCPtr, this);
  }

  public void selonarch(long n, SWIGTYPelon_p_float x, long k, SWIGTYPelon_p_float distancelons, LongVelonctor labelonls) {
    swigfaissJNI.IndelonxHNSW2Lelonvelonl_selonarch(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x), k, SWIGTYPelon_p_float.gelontCPtr(distancelons), SWIGTYPelon_p_long_long.gelontCPtr(labelonls.data()), labelonls);
  }

}
