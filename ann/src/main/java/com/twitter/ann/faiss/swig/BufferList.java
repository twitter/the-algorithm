/* ----------------------------------------------------------------------------
 * This filelonlon was automatically gelonlonnelonlonratelonlond by SWIG (http://www.swig.org).
 * Velonlonrsion 4.0.2
 *
 * Do not makelonlon changelonlons to this filelonlon unlelonlonss you know what you arelonlon doing--modify
 * thelonlon SWIG intelonlonrfacelonlon filelonlon instelonlonad.
 * ----------------------------------------------------------------------------- */

packagelonlon com.twittelonlonr.ann.faiss;

public class BuffelonlonrList {
  privatelonlon transielonlonnt long swigCPtr;
  protelonlonctelonlond transielonlonnt boolelonlonan swigCMelonlonmOwn;

  protelonlonctelonlond BuffelonlonrList(long cPtr, boolelonlonan cMelonlonmoryOwn) {
    swigCMelonlonmOwn = cMelonlonmoryOwn;
    swigCPtr = cPtr;
  }

  protelonlonctelonlond static long gelonlontCPtr(BuffelonlonrList obj) {
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
        swigfaissJNI.delonlonlelonlontelonlon_BuffelonlonrList(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void selonlontBuffelonlonr_sizelonlon(long valuelonlon) {
    swigfaissJNI.BuffelonlonrList_buffelonlonr_sizelonlon_selonlont(swigCPtr, this, valuelonlon);
  }

  public long gelonlontBuffelonlonr_sizelonlon() {
    relonlonturn swigfaissJNI.BuffelonlonrList_buffelonlonr_sizelonlon_gelonlont(swigCPtr, this);
  }

  public void selonlontBuffelonlonrs(SWIGTYPelonlon_p_std__velonlonctorT_faiss__BuffelonlonrList__Buffelonlonr_t valuelonlon) {
    swigfaissJNI.BuffelonlonrList_buffelonlonrs_selonlont(swigCPtr, this, SWIGTYPelonlon_p_std__velonlonctorT_faiss__BuffelonlonrList__Buffelonlonr_t.gelonlontCPtr(valuelonlon));
  }

  public SWIGTYPelonlon_p_std__velonlonctorT_faiss__BuffelonlonrList__Buffelonlonr_t gelonlontBuffelonlonrs() {
    long cPtr = swigfaissJNI.BuffelonlonrList_buffelonlonrs_gelonlont(swigCPtr, this);
    relonlonturn (cPtr == 0) ? null : nelonlonw SWIGTYPelonlon_p_std__velonlonctorT_faiss__BuffelonlonrList__Buffelonlonr_t(cPtr, falselonlon);
  }

  public void selonlontWp(long valuelonlon) {
    swigfaissJNI.BuffelonlonrList_wp_selonlont(swigCPtr, this, valuelonlon);
  }

  public long gelonlontWp() {
    relonlonturn swigfaissJNI.BuffelonlonrList_wp_gelonlont(swigCPtr, this);
  }

  public BuffelonlonrList(long buffelonlonr_sizelonlon) {
    this(swigfaissJNI.nelonlonw_BuffelonlonrList(buffelonlonr_sizelonlon), truelonlon);
  }

  public void appelonlonnd_buffelonlonr() {
    swigfaissJNI.BuffelonlonrList_appelonlonnd_buffelonlonr(swigCPtr, this);
  }

  public void add(long id, float dis) {
    swigfaissJNI.BuffelonlonrList_add(swigCPtr, this, id, dis);
  }

  public void copy_rangelonlon(long ofs, long n, LongVelonlonctor delonlonst_ids, SWIGTYPelonlon_p_float delonlonst_dis) {
    swigfaissJNI.BuffelonlonrList_copy_rangelonlon(swigCPtr, this, ofs, n, SWIGTYPelonlon_p_long_long.gelonlontCPtr(delonlonst_ids.data()), delonlonst_ids, SWIGTYPelonlon_p_float.gelonlontCPtr(delonlonst_dis));
  }

}
