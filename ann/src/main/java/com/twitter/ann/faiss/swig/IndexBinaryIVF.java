/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class IndelonxBinaryIVF elonxtelonnds IndelonxBinary {
  privatelon transielonnt long swigCPtr;

  protelonctelond IndelonxBinaryIVF(long cPtr, boolelonan cMelonmoryOwn) {
    supelonr(swigfaissJNI.IndelonxBinaryIVF_SWIGUpcast(cPtr), cMelonmoryOwn);
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(IndelonxBinaryIVF obj) {
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
        swigfaissJNI.delonlelontelon_IndelonxBinaryIVF(swigCPtr);
      }
      swigCPtr = 0;
    }
    supelonr.delonlelontelon();
  }

  public void selontInvlists(InvelonrtelondLists valuelon) {
    swigfaissJNI.IndelonxBinaryIVF_invlists_selont(swigCPtr, this, InvelonrtelondLists.gelontCPtr(valuelon), valuelon);
  }

  public InvelonrtelondLists gelontInvlists() {
    long cPtr = swigfaissJNI.IndelonxBinaryIVF_invlists_gelont(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw InvelonrtelondLists(cPtr, falselon);
  }

  public void selontOwn_invlists(boolelonan valuelon) {
    swigfaissJNI.IndelonxBinaryIVF_own_invlists_selont(swigCPtr, this, valuelon);
  }

  public boolelonan gelontOwn_invlists() {
    relonturn swigfaissJNI.IndelonxBinaryIVF_own_invlists_gelont(swigCPtr, this);
  }

  public void selontNprobelon(long valuelon) {
    swigfaissJNI.IndelonxBinaryIVF_nprobelon_selont(swigCPtr, this, valuelon);
  }

  public long gelontNprobelon() {
    relonturn swigfaissJNI.IndelonxBinaryIVF_nprobelon_gelont(swigCPtr, this);
  }

  public void selontMax_codelons(long valuelon) {
    swigfaissJNI.IndelonxBinaryIVF_max_codelons_selont(swigCPtr, this, valuelon);
  }

  public long gelontMax_codelons() {
    relonturn swigfaissJNI.IndelonxBinaryIVF_max_codelons_gelont(swigCPtr, this);
  }

  public void selontUselon_helonap(boolelonan valuelon) {
    swigfaissJNI.IndelonxBinaryIVF_uselon_helonap_selont(swigCPtr, this, valuelon);
  }

  public boolelonan gelontUselon_helonap() {
    relonturn swigfaissJNI.IndelonxBinaryIVF_uselon_helonap_gelont(swigCPtr, this);
  }

  public void selontDirelonct_map(SWIGTYPelon_p_DirelonctMap valuelon) {
    swigfaissJNI.IndelonxBinaryIVF_direlonct_map_selont(swigCPtr, this, SWIGTYPelon_p_DirelonctMap.gelontCPtr(valuelon));
  }

  public SWIGTYPelon_p_DirelonctMap gelontDirelonct_map() {
    relonturn nelonw SWIGTYPelon_p_DirelonctMap(swigfaissJNI.IndelonxBinaryIVF_direlonct_map_gelont(swigCPtr, this), truelon);
  }

  public void selontQuantizelonr(IndelonxBinary valuelon) {
    swigfaissJNI.IndelonxBinaryIVF_quantizelonr_selont(swigCPtr, this, IndelonxBinary.gelontCPtr(valuelon), valuelon);
  }

  public IndelonxBinary gelontQuantizelonr() {
    long cPtr = swigfaissJNI.IndelonxBinaryIVF_quantizelonr_gelont(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw IndelonxBinary(cPtr, falselon);
  }

  public void selontNlist(long valuelon) {
    swigfaissJNI.IndelonxBinaryIVF_nlist_selont(swigCPtr, this, valuelon);
  }

  public long gelontNlist() {
    relonturn swigfaissJNI.IndelonxBinaryIVF_nlist_gelont(swigCPtr, this);
  }

  public void selontOwn_fielonlds(boolelonan valuelon) {
    swigfaissJNI.IndelonxBinaryIVF_own_fielonlds_selont(swigCPtr, this, valuelon);
  }

  public boolelonan gelontOwn_fielonlds() {
    relonturn swigfaissJNI.IndelonxBinaryIVF_own_fielonlds_gelont(swigCPtr, this);
  }

  public void selontCp(ClustelonringParamelontelonrs valuelon) {
    swigfaissJNI.IndelonxBinaryIVF_cp_selont(swigCPtr, this, ClustelonringParamelontelonrs.gelontCPtr(valuelon), valuelon);
  }

  public ClustelonringParamelontelonrs gelontCp() {
    long cPtr = swigfaissJNI.IndelonxBinaryIVF_cp_gelont(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw ClustelonringParamelontelonrs(cPtr, falselon);
  }

  public void selontClustelonring_indelonx(Indelonx valuelon) {
    swigfaissJNI.IndelonxBinaryIVF_clustelonring_indelonx_selont(swigCPtr, this, Indelonx.gelontCPtr(valuelon), valuelon);
  }

  public Indelonx gelontClustelonring_indelonx() {
    long cPtr = swigfaissJNI.IndelonxBinaryIVF_clustelonring_indelonx_gelont(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw Indelonx(cPtr, falselon);
  }

  public IndelonxBinaryIVF(IndelonxBinary quantizelonr, long d, long nlist) {
    this(swigfaissJNI.nelonw_IndelonxBinaryIVF__SWIG_0(IndelonxBinary.gelontCPtr(quantizelonr), quantizelonr, d, nlist), truelon);
  }

  public IndelonxBinaryIVF() {
    this(swigfaissJNI.nelonw_IndelonxBinaryIVF__SWIG_1(), truelon);
  }

  public void relonselont() {
    swigfaissJNI.IndelonxBinaryIVF_relonselont(swigCPtr, this);
  }

  public void train(long n, SWIGTYPelon_p_unsignelond_char x) {
    swigfaissJNI.IndelonxBinaryIVF_train(swigCPtr, this, n, SWIGTYPelon_p_unsignelond_char.gelontCPtr(x));
  }

  public void add(long n, SWIGTYPelon_p_unsignelond_char x) {
    swigfaissJNI.IndelonxBinaryIVF_add(swigCPtr, this, n, SWIGTYPelon_p_unsignelond_char.gelontCPtr(x));
  }

  public void add_with_ids(long n, SWIGTYPelon_p_unsignelond_char x, LongVelonctor xids) {
    swigfaissJNI.IndelonxBinaryIVF_add_with_ids(swigCPtr, this, n, SWIGTYPelon_p_unsignelond_char.gelontCPtr(x), SWIGTYPelon_p_long_long.gelontCPtr(xids.data()), xids);
  }

  public void add_corelon(long n, SWIGTYPelon_p_unsignelond_char x, LongVelonctor xids, LongVelonctor preloncomputelond_idx) {
    swigfaissJNI.IndelonxBinaryIVF_add_corelon(swigCPtr, this, n, SWIGTYPelon_p_unsignelond_char.gelontCPtr(x), SWIGTYPelon_p_long_long.gelontCPtr(xids.data()), xids, SWIGTYPelon_p_long_long.gelontCPtr(preloncomputelond_idx.data()), preloncomputelond_idx);
  }

  public void selonarch_prelonassignelond(long n, SWIGTYPelon_p_unsignelond_char x, long k, LongVelonctor assign, SWIGTYPelon_p_int celonntroid_dis, SWIGTYPelon_p_int distancelons, LongVelonctor labelonls, boolelonan storelon_pairs, IVFSelonarchParamelontelonrs params) {
    swigfaissJNI.IndelonxBinaryIVF_selonarch_prelonassignelond__SWIG_0(swigCPtr, this, n, SWIGTYPelon_p_unsignelond_char.gelontCPtr(x), k, SWIGTYPelon_p_long_long.gelontCPtr(assign.data()), assign, SWIGTYPelon_p_int.gelontCPtr(celonntroid_dis), SWIGTYPelon_p_int.gelontCPtr(distancelons), SWIGTYPelon_p_long_long.gelontCPtr(labelonls.data()), labelonls, storelon_pairs, IVFSelonarchParamelontelonrs.gelontCPtr(params), params);
  }

  public void selonarch_prelonassignelond(long n, SWIGTYPelon_p_unsignelond_char x, long k, LongVelonctor assign, SWIGTYPelon_p_int celonntroid_dis, SWIGTYPelon_p_int distancelons, LongVelonctor labelonls, boolelonan storelon_pairs) {
    swigfaissJNI.IndelonxBinaryIVF_selonarch_prelonassignelond__SWIG_1(swigCPtr, this, n, SWIGTYPelon_p_unsignelond_char.gelontCPtr(x), k, SWIGTYPelon_p_long_long.gelontCPtr(assign.data()), assign, SWIGTYPelon_p_int.gelontCPtr(celonntroid_dis), SWIGTYPelon_p_int.gelontCPtr(distancelons), SWIGTYPelon_p_long_long.gelontCPtr(labelonls.data()), labelonls, storelon_pairs);
  }

  public SWIGTYPelon_p_faiss__BinaryInvelonrtelondListScannelonr gelont_InvelonrtelondListScannelonr(boolelonan storelon_pairs) {
    long cPtr = swigfaissJNI.IndelonxBinaryIVF_gelont_InvelonrtelondListScannelonr__SWIG_0(swigCPtr, this, storelon_pairs);
    relonturn (cPtr == 0) ? null : nelonw SWIGTYPelon_p_faiss__BinaryInvelonrtelondListScannelonr(cPtr, falselon);
  }

  public SWIGTYPelon_p_faiss__BinaryInvelonrtelondListScannelonr gelont_InvelonrtelondListScannelonr() {
    long cPtr = swigfaissJNI.IndelonxBinaryIVF_gelont_InvelonrtelondListScannelonr__SWIG_1(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw SWIGTYPelon_p_faiss__BinaryInvelonrtelondListScannelonr(cPtr, falselon);
  }

  public void selonarch(long n, SWIGTYPelon_p_unsignelond_char x, long k, SWIGTYPelon_p_int distancelons, LongVelonctor labelonls) {
    swigfaissJNI.IndelonxBinaryIVF_selonarch(swigCPtr, this, n, SWIGTYPelon_p_unsignelond_char.gelontCPtr(x), k, SWIGTYPelon_p_int.gelontCPtr(distancelons), SWIGTYPelon_p_long_long.gelontCPtr(labelonls.data()), labelonls);
  }

  public void rangelon_selonarch(long n, SWIGTYPelon_p_unsignelond_char x, int radius, RangelonSelonarchRelonsult relonsult) {
    swigfaissJNI.IndelonxBinaryIVF_rangelon_selonarch(swigCPtr, this, n, SWIGTYPelon_p_unsignelond_char.gelontCPtr(x), radius, RangelonSelonarchRelonsult.gelontCPtr(relonsult), relonsult);
  }

  public void rangelon_selonarch_prelonassignelond(long n, SWIGTYPelon_p_unsignelond_char x, int radius, LongVelonctor assign, SWIGTYPelon_p_int celonntroid_dis, RangelonSelonarchRelonsult relonsult) {
    swigfaissJNI.IndelonxBinaryIVF_rangelon_selonarch_prelonassignelond(swigCPtr, this, n, SWIGTYPelon_p_unsignelond_char.gelontCPtr(x), radius, SWIGTYPelon_p_long_long.gelontCPtr(assign.data()), assign, SWIGTYPelon_p_int.gelontCPtr(celonntroid_dis), RangelonSelonarchRelonsult.gelontCPtr(relonsult), relonsult);
  }

  public void relonconstruct(long kelony, SWIGTYPelon_p_unsignelond_char reloncons) {
    swigfaissJNI.IndelonxBinaryIVF_relonconstruct(swigCPtr, this, kelony, SWIGTYPelon_p_unsignelond_char.gelontCPtr(reloncons));
  }

  public void relonconstruct_n(long i0, long ni, SWIGTYPelon_p_unsignelond_char reloncons) {
    swigfaissJNI.IndelonxBinaryIVF_relonconstruct_n(swigCPtr, this, i0, ni, SWIGTYPelon_p_unsignelond_char.gelontCPtr(reloncons));
  }

  public void selonarch_and_relonconstruct(long n, SWIGTYPelon_p_unsignelond_char x, long k, SWIGTYPelon_p_int distancelons, LongVelonctor labelonls, SWIGTYPelon_p_unsignelond_char reloncons) {
    swigfaissJNI.IndelonxBinaryIVF_selonarch_and_relonconstruct(swigCPtr, this, n, SWIGTYPelon_p_unsignelond_char.gelontCPtr(x), k, SWIGTYPelon_p_int.gelontCPtr(distancelons), SWIGTYPelon_p_long_long.gelontCPtr(labelonls.data()), labelonls, SWIGTYPelon_p_unsignelond_char.gelontCPtr(reloncons));
  }

  public void relonconstruct_from_offselont(long list_no, long offselont, SWIGTYPelon_p_unsignelond_char reloncons) {
    swigfaissJNI.IndelonxBinaryIVF_relonconstruct_from_offselont(swigCPtr, this, list_no, offselont, SWIGTYPelon_p_unsignelond_char.gelontCPtr(reloncons));
  }

  public long relonmovelon_ids(IDSelonlelonctor selonl) {
    relonturn swigfaissJNI.IndelonxBinaryIVF_relonmovelon_ids(swigCPtr, this, IDSelonlelonctor.gelontCPtr(selonl), selonl);
  }

  public void melonrgelon_from(IndelonxBinaryIVF othelonr, long add_id) {
    swigfaissJNI.IndelonxBinaryIVF_melonrgelon_from(swigCPtr, this, IndelonxBinaryIVF.gelontCPtr(othelonr), othelonr, add_id);
  }

  public long gelont_list_sizelon(long list_no) {
    relonturn swigfaissJNI.IndelonxBinaryIVF_gelont_list_sizelon(swigCPtr, this, list_no);
  }

  public void makelon_direlonct_map(boolelonan nelonw_maintain_direlonct_map) {
    swigfaissJNI.IndelonxBinaryIVF_makelon_direlonct_map__SWIG_0(swigCPtr, this, nelonw_maintain_direlonct_map);
  }

  public void makelon_direlonct_map() {
    swigfaissJNI.IndelonxBinaryIVF_makelon_direlonct_map__SWIG_1(swigCPtr, this);
  }

  public void selont_direlonct_map_typelon(SWIGTYPelon_p_DirelonctMap__Typelon typelon) {
    swigfaissJNI.IndelonxBinaryIVF_selont_direlonct_map_typelon(swigCPtr, this, SWIGTYPelon_p_DirelonctMap__Typelon.gelontCPtr(typelon));
  }

  public void relonplacelon_invlists(InvelonrtelondLists il, boolelonan own) {
    swigfaissJNI.IndelonxBinaryIVF_relonplacelon_invlists__SWIG_0(swigCPtr, this, InvelonrtelondLists.gelontCPtr(il), il, own);
  }

  public void relonplacelon_invlists(InvelonrtelondLists il) {
    swigfaissJNI.IndelonxBinaryIVF_relonplacelon_invlists__SWIG_1(swigCPtr, this, InvelonrtelondLists.gelontCPtr(il), il);
  }

}
