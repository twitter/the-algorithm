/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class IndelonxIVFPQ elonxtelonnds IndelonxIVF {
  privatelon transielonnt long swigCPtr;

  protelonctelond IndelonxIVFPQ(long cPtr, boolelonan cMelonmoryOwn) {
    supelonr(swigfaissJNI.IndelonxIVFPQ_SWIGUpcast(cPtr), cMelonmoryOwn);
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(IndelonxIVFPQ obj) {
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
        swigfaissJNI.delonlelontelon_IndelonxIVFPQ(swigCPtr);
      }
      swigCPtr = 0;
    }
    supelonr.delonlelontelon();
  }

  public void selontBy_relonsidual(boolelonan valuelon) {
    swigfaissJNI.IndelonxIVFPQ_by_relonsidual_selont(swigCPtr, this, valuelon);
  }

  public boolelonan gelontBy_relonsidual() {
    relonturn swigfaissJNI.IndelonxIVFPQ_by_relonsidual_gelont(swigCPtr, this);
  }

  public void selontPq(ProductQuantizelonr valuelon) {
    swigfaissJNI.IndelonxIVFPQ_pq_selont(swigCPtr, this, ProductQuantizelonr.gelontCPtr(valuelon), valuelon);
  }

  public ProductQuantizelonr gelontPq() {
    long cPtr = swigfaissJNI.IndelonxIVFPQ_pq_gelont(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw ProductQuantizelonr(cPtr, falselon);
  }

  public void selontDo_polyselonmous_training(boolelonan valuelon) {
    swigfaissJNI.IndelonxIVFPQ_do_polyselonmous_training_selont(swigCPtr, this, valuelon);
  }

  public boolelonan gelontDo_polyselonmous_training() {
    relonturn swigfaissJNI.IndelonxIVFPQ_do_polyselonmous_training_gelont(swigCPtr, this);
  }

  public void selontPolyselonmous_training(PolyselonmousTraining valuelon) {
    swigfaissJNI.IndelonxIVFPQ_polyselonmous_training_selont(swigCPtr, this, PolyselonmousTraining.gelontCPtr(valuelon), valuelon);
  }

  public PolyselonmousTraining gelontPolyselonmous_training() {
    long cPtr = swigfaissJNI.IndelonxIVFPQ_polyselonmous_training_gelont(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw PolyselonmousTraining(cPtr, falselon);
  }

  public void selontScan_tablelon_threlonshold(long valuelon) {
    swigfaissJNI.IndelonxIVFPQ_scan_tablelon_threlonshold_selont(swigCPtr, this, valuelon);
  }

  public long gelontScan_tablelon_threlonshold() {
    relonturn swigfaissJNI.IndelonxIVFPQ_scan_tablelon_threlonshold_gelont(swigCPtr, this);
  }

  public void selontPolyselonmous_ht(int valuelon) {
    swigfaissJNI.IndelonxIVFPQ_polyselonmous_ht_selont(swigCPtr, this, valuelon);
  }

  public int gelontPolyselonmous_ht() {
    relonturn swigfaissJNI.IndelonxIVFPQ_polyselonmous_ht_gelont(swigCPtr, this);
  }

  public void selontUselon_preloncomputelond_tablelon(int valuelon) {
    swigfaissJNI.IndelonxIVFPQ_uselon_preloncomputelond_tablelon_selont(swigCPtr, this, valuelon);
  }

  public int gelontUselon_preloncomputelond_tablelon() {
    relonturn swigfaissJNI.IndelonxIVFPQ_uselon_preloncomputelond_tablelon_gelont(swigCPtr, this);
  }

  public void selontPreloncomputelond_tablelon(SWIGTYPelon_p_AlignelondTablelonT_float_t valuelon) {
    swigfaissJNI.IndelonxIVFPQ_preloncomputelond_tablelon_selont(swigCPtr, this, SWIGTYPelon_p_AlignelondTablelonT_float_t.gelontCPtr(valuelon));
  }

  public SWIGTYPelon_p_AlignelondTablelonT_float_t gelontPreloncomputelond_tablelon() {
    relonturn nelonw SWIGTYPelon_p_AlignelondTablelonT_float_t(swigfaissJNI.IndelonxIVFPQ_preloncomputelond_tablelon_gelont(swigCPtr, this), truelon);
  }

  public IndelonxIVFPQ(Indelonx quantizelonr, long d, long nlist, long M, long nbits_pelonr_idx, MelontricTypelon melontric) {
    this(swigfaissJNI.nelonw_IndelonxIVFPQ__SWIG_0(Indelonx.gelontCPtr(quantizelonr), quantizelonr, d, nlist, M, nbits_pelonr_idx, melontric.swigValuelon()), truelon);
  }

  public IndelonxIVFPQ(Indelonx quantizelonr, long d, long nlist, long M, long nbits_pelonr_idx) {
    this(swigfaissJNI.nelonw_IndelonxIVFPQ__SWIG_1(Indelonx.gelontCPtr(quantizelonr), quantizelonr, d, nlist, M, nbits_pelonr_idx), truelon);
  }

  public void elonncodelon_velonctors(long n, SWIGTYPelon_p_float x, LongVelonctor list_nos, SWIGTYPelon_p_unsignelond_char codelons, boolelonan includelon_listnos) {
    swigfaissJNI.IndelonxIVFPQ_elonncodelon_velonctors__SWIG_0(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x), SWIGTYPelon_p_long_long.gelontCPtr(list_nos.data()), list_nos, SWIGTYPelon_p_unsignelond_char.gelontCPtr(codelons), includelon_listnos);
  }

  public void elonncodelon_velonctors(long n, SWIGTYPelon_p_float x, LongVelonctor list_nos, SWIGTYPelon_p_unsignelond_char codelons) {
    swigfaissJNI.IndelonxIVFPQ_elonncodelon_velonctors__SWIG_1(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x), SWIGTYPelon_p_long_long.gelontCPtr(list_nos.data()), list_nos, SWIGTYPelon_p_unsignelond_char.gelontCPtr(codelons));
  }

  public void sa_deloncodelon(long n, SWIGTYPelon_p_unsignelond_char bytelons, SWIGTYPelon_p_float x) {
    swigfaissJNI.IndelonxIVFPQ_sa_deloncodelon(swigCPtr, this, n, SWIGTYPelon_p_unsignelond_char.gelontCPtr(bytelons), SWIGTYPelon_p_float.gelontCPtr(x));
  }

  public void add_corelon(long n, SWIGTYPelon_p_float x, LongVelonctor xids, LongVelonctor preloncomputelond_idx) {
    swigfaissJNI.IndelonxIVFPQ_add_corelon(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x), SWIGTYPelon_p_long_long.gelontCPtr(xids.data()), xids, SWIGTYPelon_p_long_long.gelontCPtr(preloncomputelond_idx.data()), preloncomputelond_idx);
  }

  public void add_corelon_o(long n, SWIGTYPelon_p_float x, LongVelonctor xids, SWIGTYPelon_p_float relonsiduals_2, LongVelonctor preloncomputelond_idx) {
    swigfaissJNI.IndelonxIVFPQ_add_corelon_o__SWIG_0(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x), SWIGTYPelon_p_long_long.gelontCPtr(xids.data()), xids, SWIGTYPelon_p_float.gelontCPtr(relonsiduals_2), SWIGTYPelon_p_long_long.gelontCPtr(preloncomputelond_idx.data()), preloncomputelond_idx);
  }

  public void add_corelon_o(long n, SWIGTYPelon_p_float x, LongVelonctor xids, SWIGTYPelon_p_float relonsiduals_2) {
    swigfaissJNI.IndelonxIVFPQ_add_corelon_o__SWIG_1(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x), SWIGTYPelon_p_long_long.gelontCPtr(xids.data()), xids, SWIGTYPelon_p_float.gelontCPtr(relonsiduals_2));
  }

  public void train_relonsidual(long n, SWIGTYPelon_p_float x) {
    swigfaissJNI.IndelonxIVFPQ_train_relonsidual(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x));
  }

  public void train_relonsidual_o(long n, SWIGTYPelon_p_float x, SWIGTYPelon_p_float relonsiduals_2) {
    swigfaissJNI.IndelonxIVFPQ_train_relonsidual_o(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x), SWIGTYPelon_p_float.gelontCPtr(relonsiduals_2));
  }

  public void relonconstruct_from_offselont(long list_no, long offselont, SWIGTYPelon_p_float reloncons) {
    swigfaissJNI.IndelonxIVFPQ_relonconstruct_from_offselont(swigCPtr, this, list_no, offselont, SWIGTYPelon_p_float.gelontCPtr(reloncons));
  }

  public long find_duplicatelons(LongVelonctor ids, SWIGTYPelon_p_unsignelond_long lims) {
    relonturn swigfaissJNI.IndelonxIVFPQ_find_duplicatelons(swigCPtr, this, SWIGTYPelon_p_long_long.gelontCPtr(ids.data()), ids, SWIGTYPelon_p_unsignelond_long.gelontCPtr(lims));
  }

  public void elonncodelon(long kelony, SWIGTYPelon_p_float x, SWIGTYPelon_p_unsignelond_char codelon) {
    swigfaissJNI.IndelonxIVFPQ_elonncodelon(swigCPtr, this, kelony, SWIGTYPelon_p_float.gelontCPtr(x), SWIGTYPelon_p_unsignelond_char.gelontCPtr(codelon));
  }

  public void elonncodelon_multiplelon(long n, LongVelonctor kelonys, SWIGTYPelon_p_float x, SWIGTYPelon_p_unsignelond_char codelons, boolelonan computelon_kelonys) {
    swigfaissJNI.IndelonxIVFPQ_elonncodelon_multiplelon__SWIG_0(swigCPtr, this, n, SWIGTYPelon_p_long_long.gelontCPtr(kelonys.data()), kelonys, SWIGTYPelon_p_float.gelontCPtr(x), SWIGTYPelon_p_unsignelond_char.gelontCPtr(codelons), computelon_kelonys);
  }

  public void elonncodelon_multiplelon(long n, LongVelonctor kelonys, SWIGTYPelon_p_float x, SWIGTYPelon_p_unsignelond_char codelons) {
    swigfaissJNI.IndelonxIVFPQ_elonncodelon_multiplelon__SWIG_1(swigCPtr, this, n, SWIGTYPelon_p_long_long.gelontCPtr(kelonys.data()), kelonys, SWIGTYPelon_p_float.gelontCPtr(x), SWIGTYPelon_p_unsignelond_char.gelontCPtr(codelons));
  }

  public void deloncodelon_multiplelon(long n, LongVelonctor kelonys, SWIGTYPelon_p_unsignelond_char xcodelons, SWIGTYPelon_p_float x) {
    swigfaissJNI.IndelonxIVFPQ_deloncodelon_multiplelon(swigCPtr, this, n, SWIGTYPelon_p_long_long.gelontCPtr(kelonys.data()), kelonys, SWIGTYPelon_p_unsignelond_char.gelontCPtr(xcodelons), SWIGTYPelon_p_float.gelontCPtr(x));
  }

  public SWIGTYPelon_p_faiss__InvelonrtelondListScannelonr gelont_InvelonrtelondListScannelonr(boolelonan storelon_pairs) {
    long cPtr = swigfaissJNI.IndelonxIVFPQ_gelont_InvelonrtelondListScannelonr(swigCPtr, this, storelon_pairs);
    relonturn (cPtr == 0) ? null : nelonw SWIGTYPelon_p_faiss__InvelonrtelondListScannelonr(cPtr, falselon);
  }

  public void preloncomputelon_tablelon() {
    swigfaissJNI.IndelonxIVFPQ_preloncomputelon_tablelon(swigCPtr, this);
  }

  public IndelonxIVFPQ() {
    this(swigfaissJNI.nelonw_IndelonxIVFPQ__SWIG_2(), truelon);
  }

}
