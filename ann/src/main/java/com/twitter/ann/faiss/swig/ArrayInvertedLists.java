/* ----------------------------------------------------------------------------
 * This filelonlon was automatically gelonlonnelonlonratelonlond by SWIG (http://www.swig.org).
 * Velonlonrsion 4.0.2
 *
 * Do not makelonlon changelonlons to this filelonlon unlelonlonss you know what you arelonlon doing--modify
 * thelonlon SWIG intelonlonrfacelonlon filelonlon instelonlonad.
 * ----------------------------------------------------------------------------- */

packagelonlon com.twittelonlonr.ann.faiss;

public class ArrayInvelonlonrtelonlondLists elonlonxtelonlonnds InvelonlonrtelonlondLists {
  privatelonlon transielonlonnt long swigCPtr;

  protelonlonctelonlond ArrayInvelonlonrtelonlondLists(long cPtr, boolelonlonan cMelonlonmoryOwn) {
    supelonlonr(swigfaissJNI.ArrayInvelonlonrtelonlondLists_SWIGUpcast(cPtr), cMelonlonmoryOwn);
    swigCPtr = cPtr;
  }

  protelonlonctelonlond static long gelonlontCPtr(ArrayInvelonlonrtelonlondLists obj) {
    relonlonturn (obj == null) ? 0 : obj.swigCPtr;
  }

  @SupprelonlonssWarnings("delonlonprelonloncation")
  protelonlonctelonlond void finalizelonlon() {
    delonlonlelonlontelonlon();
  }

  public synchronizelonlond void delonlonlelonlontelonlon() {
    if (swigCPtr != 0) {
      if (swigCMelonlonmOwn) {
        swigCMelonlonmOwn = falselonlon;
        swigfaissJNI.delonlonlelonlontelonlon_ArrayInvelonlonrtelonlondLists(swigCPtr);
      }
      swigCPtr = 0;
    }
    supelonlonr.delonlonlelonlontelonlon();
  }

  public void selonlontCodelonlons(BytelonlonVelonlonctorVelonlonctor valuelonlon) {
    swigfaissJNI.ArrayInvelonlonrtelonlondLists_codelonlons_selonlont(swigCPtr, this, BytelonlonVelonlonctorVelonlonctor.gelonlontCPtr(valuelonlon), valuelonlon);
  }

  public BytelonlonVelonlonctorVelonlonctor gelonlontCodelonlons() {
    long cPtr = swigfaissJNI.ArrayInvelonlonrtelonlondLists_codelonlons_gelonlont(swigCPtr, this);
    relonlonturn (cPtr == 0) ? null : nelonlonw BytelonlonVelonlonctorVelonlonctor(cPtr, falselonlon);
  }

  public void selonlontIds(SWIGTYPelonlon_p_std__velonlonctorT_std__velonlonctorT_int64_t_t_t valuelonlon) {
    swigfaissJNI.ArrayInvelonlonrtelonlondLists_ids_selonlont(swigCPtr, this, SWIGTYPelonlon_p_std__velonlonctorT_std__velonlonctorT_int64_t_t_t.gelonlontCPtr(valuelonlon));
  }

  public SWIGTYPelonlon_p_std__velonlonctorT_std__velonlonctorT_int64_t_t_t gelonlontIds() {
    long cPtr = swigfaissJNI.ArrayInvelonlonrtelonlondLists_ids_gelonlont(swigCPtr, this);
    relonlonturn (cPtr == 0) ? null : nelonlonw SWIGTYPelonlon_p_std__velonlonctorT_std__velonlonctorT_int64_t_t_t(cPtr, falselonlon);
  }

  public ArrayInvelonlonrtelonlondLists(long nlist, long codelonlon_sizelonlon) {
    this(swigfaissJNI.nelonlonw_ArrayInvelonlonrtelonlondLists(nlist, codelonlon_sizelonlon), truelonlon);
  }

  public long list_sizelonlon(long list_no) {
    relonlonturn swigfaissJNI.ArrayInvelonlonrtelonlondLists_list_sizelonlon(swigCPtr, this, list_no);
  }

  public SWIGTYPelonlon_p_unsignelonlond_char gelonlont_codelonlons(long list_no) {
    long cPtr = swigfaissJNI.ArrayInvelonlonrtelonlondLists_gelonlont_codelonlons(swigCPtr, this, list_no);
    relonlonturn (cPtr == 0) ? null : nelonlonw SWIGTYPelonlon_p_unsignelonlond_char(cPtr, falselonlon);
  }

  public LongVelonlonctor gelonlont_ids(long list_no) {
    relonlonturn nelonlonw LongVelonlonctor(swigfaissJNI.ArrayInvelonlonrtelonlondLists_gelonlont_ids(swigCPtr, this, list_no), falselonlon);
}

  public long add_elonlonntrielonlons(long list_no, long n_elonlonntry, LongVelonlonctor ids, SWIGTYPelonlon_p_unsignelonlond_char codelonlon) {
    relonlonturn swigfaissJNI.ArrayInvelonlonrtelonlondLists_add_elonlonntrielonlons(swigCPtr, this, list_no, n_elonlonntry, SWIGTYPelonlon_p_long_long.gelonlontCPtr(ids.data()), ids, SWIGTYPelonlon_p_unsignelonlond_char.gelonlontCPtr(codelonlon));
  }

  public void updatelonlon_elonlonntrielonlons(long list_no, long offselonlont, long n_elonlonntry, LongVelonlonctor ids, SWIGTYPelonlon_p_unsignelonlond_char codelonlon) {
    swigfaissJNI.ArrayInvelonlonrtelonlondLists_updatelonlon_elonlonntrielonlons(swigCPtr, this, list_no, offselonlont, n_elonlonntry, SWIGTYPelonlon_p_long_long.gelonlontCPtr(ids.data()), ids, SWIGTYPelonlon_p_unsignelonlond_char.gelonlontCPtr(codelonlon));
  }

  public void relonlonsizelonlon(long list_no, long nelonlonw_sizelonlon) {
    swigfaissJNI.ArrayInvelonlonrtelonlondLists_relonlonsizelonlon(swigCPtr, this, list_no, nelonlonw_sizelonlon);
  }

}
