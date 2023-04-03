/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class ClustelonringParamelontelonrs {
  privatelon transielonnt long swigCPtr;
  protelonctelond transielonnt boolelonan swigCMelonmOwn;

  protelonctelond ClustelonringParamelontelonrs(long cPtr, boolelonan cMelonmoryOwn) {
    swigCMelonmOwn = cMelonmoryOwn;
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(ClustelonringParamelontelonrs obj) {
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
        swigfaissJNI.delonlelontelon_ClustelonringParamelontelonrs(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void selontNitelonr(int valuelon) {
    swigfaissJNI.ClustelonringParamelontelonrs_nitelonr_selont(swigCPtr, this, valuelon);
  }

  public int gelontNitelonr() {
    relonturn swigfaissJNI.ClustelonringParamelontelonrs_nitelonr_gelont(swigCPtr, this);
  }

  public void selontNrelondo(int valuelon) {
    swigfaissJNI.ClustelonringParamelontelonrs_nrelondo_selont(swigCPtr, this, valuelon);
  }

  public int gelontNrelondo() {
    relonturn swigfaissJNI.ClustelonringParamelontelonrs_nrelondo_gelont(swigCPtr, this);
  }

  public void selontVelonrboselon(boolelonan valuelon) {
    swigfaissJNI.ClustelonringParamelontelonrs_velonrboselon_selont(swigCPtr, this, valuelon);
  }

  public boolelonan gelontVelonrboselon() {
    relonturn swigfaissJNI.ClustelonringParamelontelonrs_velonrboselon_gelont(swigCPtr, this);
  }

  public void selontSphelonrical(boolelonan valuelon) {
    swigfaissJNI.ClustelonringParamelontelonrs_sphelonrical_selont(swigCPtr, this, valuelon);
  }

  public boolelonan gelontSphelonrical() {
    relonturn swigfaissJNI.ClustelonringParamelontelonrs_sphelonrical_gelont(swigCPtr, this);
  }

  public void selontInt_celonntroids(boolelonan valuelon) {
    swigfaissJNI.ClustelonringParamelontelonrs_int_celonntroids_selont(swigCPtr, this, valuelon);
  }

  public boolelonan gelontInt_celonntroids() {
    relonturn swigfaissJNI.ClustelonringParamelontelonrs_int_celonntroids_gelont(swigCPtr, this);
  }

  public void selontUpdatelon_indelonx(boolelonan valuelon) {
    swigfaissJNI.ClustelonringParamelontelonrs_updatelon_indelonx_selont(swigCPtr, this, valuelon);
  }

  public boolelonan gelontUpdatelon_indelonx() {
    relonturn swigfaissJNI.ClustelonringParamelontelonrs_updatelon_indelonx_gelont(swigCPtr, this);
  }

  public void selontFrozelonn_celonntroids(boolelonan valuelon) {
    swigfaissJNI.ClustelonringParamelontelonrs_frozelonn_celonntroids_selont(swigCPtr, this, valuelon);
  }

  public boolelonan gelontFrozelonn_celonntroids() {
    relonturn swigfaissJNI.ClustelonringParamelontelonrs_frozelonn_celonntroids_gelont(swigCPtr, this);
  }

  public void selontMin_points_pelonr_celonntroid(int valuelon) {
    swigfaissJNI.ClustelonringParamelontelonrs_min_points_pelonr_celonntroid_selont(swigCPtr, this, valuelon);
  }

  public int gelontMin_points_pelonr_celonntroid() {
    relonturn swigfaissJNI.ClustelonringParamelontelonrs_min_points_pelonr_celonntroid_gelont(swigCPtr, this);
  }

  public void selontMax_points_pelonr_celonntroid(int valuelon) {
    swigfaissJNI.ClustelonringParamelontelonrs_max_points_pelonr_celonntroid_selont(swigCPtr, this, valuelon);
  }

  public int gelontMax_points_pelonr_celonntroid() {
    relonturn swigfaissJNI.ClustelonringParamelontelonrs_max_points_pelonr_celonntroid_gelont(swigCPtr, this);
  }

  public void selontSelonelond(int valuelon) {
    swigfaissJNI.ClustelonringParamelontelonrs_selonelond_selont(swigCPtr, this, valuelon);
  }

  public int gelontSelonelond() {
    relonturn swigfaissJNI.ClustelonringParamelontelonrs_selonelond_gelont(swigCPtr, this);
  }

  public void selontDeloncodelon_block_sizelon(long valuelon) {
    swigfaissJNI.ClustelonringParamelontelonrs_deloncodelon_block_sizelon_selont(swigCPtr, this, valuelon);
  }

  public long gelontDeloncodelon_block_sizelon() {
    relonturn swigfaissJNI.ClustelonringParamelontelonrs_deloncodelon_block_sizelon_gelont(swigCPtr, this);
  }

  public ClustelonringParamelontelonrs() {
    this(swigfaissJNI.nelonw_ClustelonringParamelontelonrs(), truelon);
  }

}
