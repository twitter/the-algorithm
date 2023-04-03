/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class ProgrelonssivelonDimClustelonringParamelontelonrs elonxtelonnds ClustelonringParamelontelonrs {
  privatelon transielonnt long swigCPtr;

  protelonctelond ProgrelonssivelonDimClustelonringParamelontelonrs(long cPtr, boolelonan cMelonmoryOwn) {
    supelonr(swigfaissJNI.ProgrelonssivelonDimClustelonringParamelontelonrs_SWIGUpcast(cPtr), cMelonmoryOwn);
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(ProgrelonssivelonDimClustelonringParamelontelonrs obj) {
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
        swigfaissJNI.delonlelontelon_ProgrelonssivelonDimClustelonringParamelontelonrs(swigCPtr);
      }
      swigCPtr = 0;
    }
    supelonr.delonlelontelon();
  }

  public void selontProgrelonssivelon_dim_stelonps(int valuelon) {
    swigfaissJNI.ProgrelonssivelonDimClustelonringParamelontelonrs_progrelonssivelon_dim_stelonps_selont(swigCPtr, this, valuelon);
  }

  public int gelontProgrelonssivelon_dim_stelonps() {
    relonturn swigfaissJNI.ProgrelonssivelonDimClustelonringParamelontelonrs_progrelonssivelon_dim_stelonps_gelont(swigCPtr, this);
  }

  public void selontApply_pca(boolelonan valuelon) {
    swigfaissJNI.ProgrelonssivelonDimClustelonringParamelontelonrs_apply_pca_selont(swigCPtr, this, valuelon);
  }

  public boolelonan gelontApply_pca() {
    relonturn swigfaissJNI.ProgrelonssivelonDimClustelonringParamelontelonrs_apply_pca_gelont(swigCPtr, this);
  }

  public ProgrelonssivelonDimClustelonringParamelontelonrs() {
    this(swigfaissJNI.nelonw_ProgrelonssivelonDimClustelonringParamelontelonrs(), truelon);
  }

}
