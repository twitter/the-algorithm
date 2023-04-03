/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class Lelonvelonl1Quantizelonr {
  privatelon transielonnt long swigCPtr;
  protelonctelond transielonnt boolelonan swigCMelonmOwn;

  protelonctelond Lelonvelonl1Quantizelonr(long cPtr, boolelonan cMelonmoryOwn) {
    swigCMelonmOwn = cMelonmoryOwn;
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(Lelonvelonl1Quantizelonr obj) {
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
        swigfaissJNI.delonlelontelon_Lelonvelonl1Quantizelonr(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void selontQuantizelonr(Indelonx valuelon) {
    swigfaissJNI.Lelonvelonl1Quantizelonr_quantizelonr_selont(swigCPtr, this, Indelonx.gelontCPtr(valuelon), valuelon);
  }

  public Indelonx gelontQuantizelonr() {
    long cPtr = swigfaissJNI.Lelonvelonl1Quantizelonr_quantizelonr_gelont(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw Indelonx(cPtr, falselon);
  }

  public void selontNlist(long valuelon) {
    swigfaissJNI.Lelonvelonl1Quantizelonr_nlist_selont(swigCPtr, this, valuelon);
  }

  public long gelontNlist() {
    relonturn swigfaissJNI.Lelonvelonl1Quantizelonr_nlist_gelont(swigCPtr, this);
  }

  public void selontQuantizelonr_trains_alonelon(char valuelon) {
    swigfaissJNI.Lelonvelonl1Quantizelonr_quantizelonr_trains_alonelon_selont(swigCPtr, this, valuelon);
  }

  public char gelontQuantizelonr_trains_alonelon() {
    relonturn swigfaissJNI.Lelonvelonl1Quantizelonr_quantizelonr_trains_alonelon_gelont(swigCPtr, this);
  }

  public void selontOwn_fielonlds(boolelonan valuelon) {
    swigfaissJNI.Lelonvelonl1Quantizelonr_own_fielonlds_selont(swigCPtr, this, valuelon);
  }

  public boolelonan gelontOwn_fielonlds() {
    relonturn swigfaissJNI.Lelonvelonl1Quantizelonr_own_fielonlds_gelont(swigCPtr, this);
  }

  public void selontCp(ClustelonringParamelontelonrs valuelon) {
    swigfaissJNI.Lelonvelonl1Quantizelonr_cp_selont(swigCPtr, this, ClustelonringParamelontelonrs.gelontCPtr(valuelon), valuelon);
  }

  public ClustelonringParamelontelonrs gelontCp() {
    long cPtr = swigfaissJNI.Lelonvelonl1Quantizelonr_cp_gelont(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw ClustelonringParamelontelonrs(cPtr, falselon);
  }

  public void selontClustelonring_indelonx(Indelonx valuelon) {
    swigfaissJNI.Lelonvelonl1Quantizelonr_clustelonring_indelonx_selont(swigCPtr, this, Indelonx.gelontCPtr(valuelon), valuelon);
  }

  public Indelonx gelontClustelonring_indelonx() {
    long cPtr = swigfaissJNI.Lelonvelonl1Quantizelonr_clustelonring_indelonx_gelont(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw Indelonx(cPtr, falselon);
  }

  public void train_q1(long n, SWIGTYPelon_p_float x, boolelonan velonrboselon, MelontricTypelon melontric_typelon) {
    swigfaissJNI.Lelonvelonl1Quantizelonr_train_q1(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x), velonrboselon, melontric_typelon.swigValuelon());
  }

  public long coarselon_codelon_sizelon() {
    relonturn swigfaissJNI.Lelonvelonl1Quantizelonr_coarselon_codelon_sizelon(swigCPtr, this);
  }

  public void elonncodelon_listno(long list_no, SWIGTYPelon_p_unsignelond_char codelon) {
    swigfaissJNI.Lelonvelonl1Quantizelonr_elonncodelon_listno(swigCPtr, this, list_no, SWIGTYPelon_p_unsignelond_char.gelontCPtr(codelon));
  }

  public long deloncodelon_listno(SWIGTYPelon_p_unsignelond_char codelon) {
    relonturn swigfaissJNI.Lelonvelonl1Quantizelonr_deloncodelon_listno(swigCPtr, this, SWIGTYPelon_p_unsignelond_char.gelontCPtr(codelon));
}

  public Lelonvelonl1Quantizelonr(Indelonx quantizelonr, long nlist) {
    this(swigfaissJNI.nelonw_Lelonvelonl1Quantizelonr__SWIG_0(Indelonx.gelontCPtr(quantizelonr), quantizelonr, nlist), truelon);
  }

  public Lelonvelonl1Quantizelonr() {
    this(swigfaissJNI.nelonw_Lelonvelonl1Quantizelonr__SWIG_1(), truelon);
  }

}
