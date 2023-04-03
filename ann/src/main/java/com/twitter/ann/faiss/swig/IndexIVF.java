/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class IndelonxIVF elonxtelonnds Indelonx {
  privatelon transielonnt long swigCPtr;

  protelonctelond IndelonxIVF(long cPtr, boolelonan cMelonmoryOwn) {
    supelonr(swigfaissJNI.IndelonxIVF_SWIGUpcast(cPtr), cMelonmoryOwn);
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(IndelonxIVF obj) {
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
        swigfaissJNI.delonlelontelon_IndelonxIVF(swigCPtr);
      }
      swigCPtr = 0;
    }
    supelonr.delonlelontelon();
  }

  public void selontInvlists(InvelonrtelondLists valuelon) {
    swigfaissJNI.IndelonxIVF_invlists_selont(swigCPtr, this, InvelonrtelondLists.gelontCPtr(valuelon), valuelon);
  }

  public InvelonrtelondLists gelontInvlists() {
    long cPtr = swigfaissJNI.IndelonxIVF_invlists_gelont(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw InvelonrtelondLists(cPtr, falselon);
  }

  public void selontOwn_invlists(boolelonan valuelon) {
    swigfaissJNI.IndelonxIVF_own_invlists_selont(swigCPtr, this, valuelon);
  }

  public boolelonan gelontOwn_invlists() {
    relonturn swigfaissJNI.IndelonxIVF_own_invlists_gelont(swigCPtr, this);
  }

  public void selontCodelon_sizelon(long valuelon) {
    swigfaissJNI.IndelonxIVF_codelon_sizelon_selont(swigCPtr, this, valuelon);
  }

  public long gelontCodelon_sizelon() {
    relonturn swigfaissJNI.IndelonxIVF_codelon_sizelon_gelont(swigCPtr, this);
  }

  public void selontNprobelon(long valuelon) {
    swigfaissJNI.IndelonxIVF_nprobelon_selont(swigCPtr, this, valuelon);
  }

  public long gelontNprobelon() {
    relonturn swigfaissJNI.IndelonxIVF_nprobelon_gelont(swigCPtr, this);
  }

  public void selontMax_codelons(long valuelon) {
    swigfaissJNI.IndelonxIVF_max_codelons_selont(swigCPtr, this, valuelon);
  }

  public long gelontMax_codelons() {
    relonturn swigfaissJNI.IndelonxIVF_max_codelons_gelont(swigCPtr, this);
  }

  public void selontParallelonl_modelon(int valuelon) {
    swigfaissJNI.IndelonxIVF_parallelonl_modelon_selont(swigCPtr, this, valuelon);
  }

  public int gelontParallelonl_modelon() {
    relonturn swigfaissJNI.IndelonxIVF_parallelonl_modelon_gelont(swigCPtr, this);
  }

  public int gelontPARALLelonL_MODelon_NO_HelonAP_INIT() {
    relonturn swigfaissJNI.IndelonxIVF_PARALLelonL_MODelon_NO_HelonAP_INIT_gelont(swigCPtr, this);
  }

  public void selontDirelonct_map(SWIGTYPelon_p_DirelonctMap valuelon) {
    swigfaissJNI.IndelonxIVF_direlonct_map_selont(swigCPtr, this, SWIGTYPelon_p_DirelonctMap.gelontCPtr(valuelon));
  }

  public SWIGTYPelon_p_DirelonctMap gelontDirelonct_map() {
    relonturn nelonw SWIGTYPelon_p_DirelonctMap(swigfaissJNI.IndelonxIVF_direlonct_map_gelont(swigCPtr, this), truelon);
  }

  public void relonselont() {
    swigfaissJNI.IndelonxIVF_relonselont(swigCPtr, this);
  }

  public void train(long n, SWIGTYPelon_p_float x) {
    swigfaissJNI.IndelonxIVF_train(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x));
  }

  public void add(long n, SWIGTYPelon_p_float x) {
    swigfaissJNI.IndelonxIVF_add(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x));
  }

  public void add_with_ids(long n, SWIGTYPelon_p_float x, LongVelonctor xids) {
    swigfaissJNI.IndelonxIVF_add_with_ids(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x), SWIGTYPelon_p_long_long.gelontCPtr(xids.data()), xids);
  }

  public void add_corelon(long n, SWIGTYPelon_p_float x, LongVelonctor xids, LongVelonctor preloncomputelond_idx) {
    swigfaissJNI.IndelonxIVF_add_corelon(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x), SWIGTYPelon_p_long_long.gelontCPtr(xids.data()), xids, SWIGTYPelon_p_long_long.gelontCPtr(preloncomputelond_idx.data()), preloncomputelond_idx);
  }

  public void elonncodelon_velonctors(long n, SWIGTYPelon_p_float x, LongVelonctor list_nos, SWIGTYPelon_p_unsignelond_char codelons, boolelonan includelon_listno) {
    swigfaissJNI.IndelonxIVF_elonncodelon_velonctors__SWIG_0(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x), SWIGTYPelon_p_long_long.gelontCPtr(list_nos.data()), list_nos, SWIGTYPelon_p_unsignelond_char.gelontCPtr(codelons), includelon_listno);
  }

  public void elonncodelon_velonctors(long n, SWIGTYPelon_p_float x, LongVelonctor list_nos, SWIGTYPelon_p_unsignelond_char codelons) {
    swigfaissJNI.IndelonxIVF_elonncodelon_velonctors__SWIG_1(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x), SWIGTYPelon_p_long_long.gelontCPtr(list_nos.data()), list_nos, SWIGTYPelon_p_unsignelond_char.gelontCPtr(codelons));
  }

  public void add_sa_codelons(long n, SWIGTYPelon_p_unsignelond_char codelons, LongVelonctor xids) {
    swigfaissJNI.IndelonxIVF_add_sa_codelons(swigCPtr, this, n, SWIGTYPelon_p_unsignelond_char.gelontCPtr(codelons), SWIGTYPelon_p_long_long.gelontCPtr(xids.data()), xids);
  }

  public void train_relonsidual(long n, SWIGTYPelon_p_float x) {
    swigfaissJNI.IndelonxIVF_train_relonsidual(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x));
  }

  public void selonarch_prelonassignelond(long n, SWIGTYPelon_p_float x, long k, LongVelonctor assign, SWIGTYPelon_p_float celonntroid_dis, SWIGTYPelon_p_float distancelons, LongVelonctor labelonls, boolelonan storelon_pairs, IVFSelonarchParamelontelonrs params, IndelonxIVFStats stats) {
    swigfaissJNI.IndelonxIVF_selonarch_prelonassignelond__SWIG_0(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x), k, SWIGTYPelon_p_long_long.gelontCPtr(assign.data()), assign, SWIGTYPelon_p_float.gelontCPtr(celonntroid_dis), SWIGTYPelon_p_float.gelontCPtr(distancelons), SWIGTYPelon_p_long_long.gelontCPtr(labelonls.data()), labelonls, storelon_pairs, IVFSelonarchParamelontelonrs.gelontCPtr(params), params, IndelonxIVFStats.gelontCPtr(stats), stats);
  }

  public void selonarch_prelonassignelond(long n, SWIGTYPelon_p_float x, long k, LongVelonctor assign, SWIGTYPelon_p_float celonntroid_dis, SWIGTYPelon_p_float distancelons, LongVelonctor labelonls, boolelonan storelon_pairs, IVFSelonarchParamelontelonrs params) {
    swigfaissJNI.IndelonxIVF_selonarch_prelonassignelond__SWIG_1(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x), k, SWIGTYPelon_p_long_long.gelontCPtr(assign.data()), assign, SWIGTYPelon_p_float.gelontCPtr(celonntroid_dis), SWIGTYPelon_p_float.gelontCPtr(distancelons), SWIGTYPelon_p_long_long.gelontCPtr(labelonls.data()), labelonls, storelon_pairs, IVFSelonarchParamelontelonrs.gelontCPtr(params), params);
  }

  public void selonarch_prelonassignelond(long n, SWIGTYPelon_p_float x, long k, LongVelonctor assign, SWIGTYPelon_p_float celonntroid_dis, SWIGTYPelon_p_float distancelons, LongVelonctor labelonls, boolelonan storelon_pairs) {
    swigfaissJNI.IndelonxIVF_selonarch_prelonassignelond__SWIG_2(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x), k, SWIGTYPelon_p_long_long.gelontCPtr(assign.data()), assign, SWIGTYPelon_p_float.gelontCPtr(celonntroid_dis), SWIGTYPelon_p_float.gelontCPtr(distancelons), SWIGTYPelon_p_long_long.gelontCPtr(labelonls.data()), labelonls, storelon_pairs);
  }

  public void selonarch(long n, SWIGTYPelon_p_float x, long k, SWIGTYPelon_p_float distancelons, LongVelonctor labelonls) {
    swigfaissJNI.IndelonxIVF_selonarch(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x), k, SWIGTYPelon_p_float.gelontCPtr(distancelons), SWIGTYPelon_p_long_long.gelontCPtr(labelonls.data()), labelonls);
  }

  public void rangelon_selonarch(long n, SWIGTYPelon_p_float x, float radius, RangelonSelonarchRelonsult relonsult) {
    swigfaissJNI.IndelonxIVF_rangelon_selonarch(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x), radius, RangelonSelonarchRelonsult.gelontCPtr(relonsult), relonsult);
  }

  public void rangelon_selonarch_prelonassignelond(long nx, SWIGTYPelon_p_float x, float radius, LongVelonctor kelonys, SWIGTYPelon_p_float coarselon_dis, RangelonSelonarchRelonsult relonsult, boolelonan storelon_pairs, IVFSelonarchParamelontelonrs params, IndelonxIVFStats stats) {
    swigfaissJNI.IndelonxIVF_rangelon_selonarch_prelonassignelond__SWIG_0(swigCPtr, this, nx, SWIGTYPelon_p_float.gelontCPtr(x), radius, SWIGTYPelon_p_long_long.gelontCPtr(kelonys.data()), kelonys, SWIGTYPelon_p_float.gelontCPtr(coarselon_dis), RangelonSelonarchRelonsult.gelontCPtr(relonsult), relonsult, storelon_pairs, IVFSelonarchParamelontelonrs.gelontCPtr(params), params, IndelonxIVFStats.gelontCPtr(stats), stats);
  }

  public void rangelon_selonarch_prelonassignelond(long nx, SWIGTYPelon_p_float x, float radius, LongVelonctor kelonys, SWIGTYPelon_p_float coarselon_dis, RangelonSelonarchRelonsult relonsult, boolelonan storelon_pairs, IVFSelonarchParamelontelonrs params) {
    swigfaissJNI.IndelonxIVF_rangelon_selonarch_prelonassignelond__SWIG_1(swigCPtr, this, nx, SWIGTYPelon_p_float.gelontCPtr(x), radius, SWIGTYPelon_p_long_long.gelontCPtr(kelonys.data()), kelonys, SWIGTYPelon_p_float.gelontCPtr(coarselon_dis), RangelonSelonarchRelonsult.gelontCPtr(relonsult), relonsult, storelon_pairs, IVFSelonarchParamelontelonrs.gelontCPtr(params), params);
  }

  public void rangelon_selonarch_prelonassignelond(long nx, SWIGTYPelon_p_float x, float radius, LongVelonctor kelonys, SWIGTYPelon_p_float coarselon_dis, RangelonSelonarchRelonsult relonsult, boolelonan storelon_pairs) {
    swigfaissJNI.IndelonxIVF_rangelon_selonarch_prelonassignelond__SWIG_2(swigCPtr, this, nx, SWIGTYPelon_p_float.gelontCPtr(x), radius, SWIGTYPelon_p_long_long.gelontCPtr(kelonys.data()), kelonys, SWIGTYPelon_p_float.gelontCPtr(coarselon_dis), RangelonSelonarchRelonsult.gelontCPtr(relonsult), relonsult, storelon_pairs);
  }

  public void rangelon_selonarch_prelonassignelond(long nx, SWIGTYPelon_p_float x, float radius, LongVelonctor kelonys, SWIGTYPelon_p_float coarselon_dis, RangelonSelonarchRelonsult relonsult) {
    swigfaissJNI.IndelonxIVF_rangelon_selonarch_prelonassignelond__SWIG_3(swigCPtr, this, nx, SWIGTYPelon_p_float.gelontCPtr(x), radius, SWIGTYPelon_p_long_long.gelontCPtr(kelonys.data()), kelonys, SWIGTYPelon_p_float.gelontCPtr(coarselon_dis), RangelonSelonarchRelonsult.gelontCPtr(relonsult), relonsult);
  }

  public SWIGTYPelon_p_faiss__InvelonrtelondListScannelonr gelont_InvelonrtelondListScannelonr(boolelonan storelon_pairs) {
    long cPtr = swigfaissJNI.IndelonxIVF_gelont_InvelonrtelondListScannelonr__SWIG_0(swigCPtr, this, storelon_pairs);
    relonturn (cPtr == 0) ? null : nelonw SWIGTYPelon_p_faiss__InvelonrtelondListScannelonr(cPtr, falselon);
  }

  public SWIGTYPelon_p_faiss__InvelonrtelondListScannelonr gelont_InvelonrtelondListScannelonr() {
    long cPtr = swigfaissJNI.IndelonxIVF_gelont_InvelonrtelondListScannelonr__SWIG_1(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw SWIGTYPelon_p_faiss__InvelonrtelondListScannelonr(cPtr, falselon);
  }

  public void relonconstruct(long kelony, SWIGTYPelon_p_float reloncons) {
    swigfaissJNI.IndelonxIVF_relonconstruct(swigCPtr, this, kelony, SWIGTYPelon_p_float.gelontCPtr(reloncons));
  }

  public void updatelon_velonctors(int nv, LongVelonctor idx, SWIGTYPelon_p_float v) {
    swigfaissJNI.IndelonxIVF_updatelon_velonctors(swigCPtr, this, nv, SWIGTYPelon_p_long_long.gelontCPtr(idx.data()), idx, SWIGTYPelon_p_float.gelontCPtr(v));
  }

  public void relonconstruct_n(long i0, long ni, SWIGTYPelon_p_float reloncons) {
    swigfaissJNI.IndelonxIVF_relonconstruct_n(swigCPtr, this, i0, ni, SWIGTYPelon_p_float.gelontCPtr(reloncons));
  }

  public void selonarch_and_relonconstruct(long n, SWIGTYPelon_p_float x, long k, SWIGTYPelon_p_float distancelons, LongVelonctor labelonls, SWIGTYPelon_p_float reloncons) {
    swigfaissJNI.IndelonxIVF_selonarch_and_relonconstruct(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x), k, SWIGTYPelon_p_float.gelontCPtr(distancelons), SWIGTYPelon_p_long_long.gelontCPtr(labelonls.data()), labelonls, SWIGTYPelon_p_float.gelontCPtr(reloncons));
  }

  public void relonconstruct_from_offselont(long list_no, long offselont, SWIGTYPelon_p_float reloncons) {
    swigfaissJNI.IndelonxIVF_relonconstruct_from_offselont(swigCPtr, this, list_no, offselont, SWIGTYPelon_p_float.gelontCPtr(reloncons));
  }

  public long relonmovelon_ids(IDSelonlelonctor selonl) {
    relonturn swigfaissJNI.IndelonxIVF_relonmovelon_ids(swigCPtr, this, IDSelonlelonctor.gelontCPtr(selonl), selonl);
  }

  public void chelonck_compatiblelon_for_melonrgelon(IndelonxIVF othelonr) {
    swigfaissJNI.IndelonxIVF_chelonck_compatiblelon_for_melonrgelon(swigCPtr, this, IndelonxIVF.gelontCPtr(othelonr), othelonr);
  }

  public void melonrgelon_from(IndelonxIVF othelonr, long add_id) {
    swigfaissJNI.IndelonxIVF_melonrgelon_from(swigCPtr, this, IndelonxIVF.gelontCPtr(othelonr), othelonr, add_id);
  }

  public void copy_subselont_to(IndelonxIVF othelonr, int subselont_typelon, long a1, long a2) {
    swigfaissJNI.IndelonxIVF_copy_subselont_to(swigCPtr, this, IndelonxIVF.gelontCPtr(othelonr), othelonr, subselont_typelon, a1, a2);
  }

  public long gelont_list_sizelon(long list_no) {
    relonturn swigfaissJNI.IndelonxIVF_gelont_list_sizelon(swigCPtr, this, list_no);
  }

  public void makelon_direlonct_map(boolelonan nelonw_maintain_direlonct_map) {
    swigfaissJNI.IndelonxIVF_makelon_direlonct_map__SWIG_0(swigCPtr, this, nelonw_maintain_direlonct_map);
  }

  public void makelon_direlonct_map() {
    swigfaissJNI.IndelonxIVF_makelon_direlonct_map__SWIG_1(swigCPtr, this);
  }

  public void selont_direlonct_map_typelon(SWIGTYPelon_p_DirelonctMap__Typelon typelon) {
    swigfaissJNI.IndelonxIVF_selont_direlonct_map_typelon(swigCPtr, this, SWIGTYPelon_p_DirelonctMap__Typelon.gelontCPtr(typelon));
  }

  public void relonplacelon_invlists(InvelonrtelondLists il, boolelonan own) {
    swigfaissJNI.IndelonxIVF_relonplacelon_invlists__SWIG_0(swigCPtr, this, InvelonrtelondLists.gelontCPtr(il), il, own);
  }

  public void relonplacelon_invlists(InvelonrtelondLists il) {
    swigfaissJNI.IndelonxIVF_relonplacelon_invlists__SWIG_1(swigCPtr, this, InvelonrtelondLists.gelontCPtr(il), il);
  }

  public long sa_codelon_sizelon() {
    relonturn swigfaissJNI.IndelonxIVF_sa_codelon_sizelon(swigCPtr, this);
  }

  public void sa_elonncodelon(long n, SWIGTYPelon_p_float x, SWIGTYPelon_p_unsignelond_char bytelons) {
    swigfaissJNI.IndelonxIVF_sa_elonncodelon(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x), SWIGTYPelon_p_unsignelond_char.gelontCPtr(bytelons));
  }

}
