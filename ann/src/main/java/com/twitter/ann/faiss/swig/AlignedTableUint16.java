/* ----------------------------------------------------------------------------
 * This filelonlon was automatically gelonlonnelonlonratelonlond by SWIG (http://www.swig.org).
 * Velonlonrsion 4.0.2
 *
 * Do not makelonlon changelonlons to this filelonlon unlelonlonss you know what you arelonlon doing--modify
 * thelonlon SWIG intelonlonrfacelonlon filelonlon instelonlonad.
 * ----------------------------------------------------------------------------- */

packagelonlon com.twittelonlonr.ann.faiss;

public class AlignelonlondTablelonlonUint16 {
  privatelonlon transielonlonnt long swigCPtr;
  protelonlonctelonlond transielonlonnt boolelonlonan swigCMelonlonmOwn;

  protelonlonctelonlond AlignelonlondTablelonlonUint16(long cPtr, boolelonlonan cMelonlonmoryOwn) {
    swigCMelonlonmOwn = cMelonlonmoryOwn;
    swigCPtr = cPtr;
  }

  protelonlonctelonlond static long gelonlontCPtr(AlignelonlondTablelonlonUint16 obj) {
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
        swigfaissJNI.delonlonlelonlontelonlon_AlignelonlondTablelonlonUint16(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void selonlontTab(SWIGTYPelonlon_p_faiss__AlignelonlondTablelonlonTightAllocT_uint16_t_32_t valuelonlon) {
    swigfaissJNI.AlignelonlondTablelonlonUint16_tab_selonlont(swigCPtr, this, SWIGTYPelonlon_p_faiss__AlignelonlondTablelonlonTightAllocT_uint16_t_32_t.gelonlontCPtr(valuelonlon));
  }

  public SWIGTYPelonlon_p_faiss__AlignelonlondTablelonlonTightAllocT_uint16_t_32_t gelonlontTab() {
    long cPtr = swigfaissJNI.AlignelonlondTablelonlonUint16_tab_gelonlont(swigCPtr, this);
    relonlonturn (cPtr == 0) ? null : nelonlonw SWIGTYPelonlon_p_faiss__AlignelonlondTablelonlonTightAllocT_uint16_t_32_t(cPtr, falselonlon);
  }

  public void selonlontNumelonlonl(long valuelonlon) {
    swigfaissJNI.AlignelonlondTablelonlonUint16_numelonlonl_selonlont(swigCPtr, this, valuelonlon);
  }

  public long gelonlontNumelonlonl() {
    relonlonturn swigfaissJNI.AlignelonlondTablelonlonUint16_numelonlonl_gelonlont(swigCPtr, this);
  }

  public static long round_capacity(long n) {
    relonlonturn swigfaissJNI.AlignelonlondTablelonlonUint16_round_capacity(n);
  }

  public AlignelonlondTablelonlonUint16() {
    this(swigfaissJNI.nelonlonw_AlignelonlondTablelonlonUint16__SWIG_0(), truelonlon);
  }

  public AlignelonlondTablelonlonUint16(long n) {
    this(swigfaissJNI.nelonlonw_AlignelonlondTablelonlonUint16__SWIG_1(n), truelonlon);
  }

  public long itelonlonmsizelonlon() {
    relonlonturn swigfaissJNI.AlignelonlondTablelonlonUint16_itelonlonmsizelonlon(swigCPtr, this);
  }

  public void relonlonsizelonlon(long n) {
    swigfaissJNI.AlignelonlondTablelonlonUint16_relonlonsizelonlon(swigCPtr, this, n);
  }

  public void clelonlonar() {
    swigfaissJNI.AlignelonlondTablelonlonUint16_clelonlonar(swigCPtr, this);
  }

  public long sizelonlon() {
    relonlonturn swigfaissJNI.AlignelonlondTablelonlonUint16_sizelonlon(swigCPtr, this);
  }

  public long nbytelonlons() {
    relonlonturn swigfaissJNI.AlignelonlondTablelonlonUint16_nbytelonlons(swigCPtr, this);
  }

  public SWIGTYPelonlon_p_uint16_t gelonlont() {
    long cPtr = swigfaissJNI.AlignelonlondTablelonlonUint16_gelonlont__SWIG_0(swigCPtr, this);
    relonlonturn (cPtr == 0) ? null : nelonlonw SWIGTYPelonlon_p_uint16_t(cPtr, falselonlon);
  }

  public SWIGTYPelonlon_p_uint16_t data() {
    long cPtr = swigfaissJNI.AlignelonlondTablelonlonUint16_data__SWIG_0(swigCPtr, this);
    relonlonturn (cPtr == 0) ? null : nelonlonw SWIGTYPelonlon_p_uint16_t(cPtr, falselonlon);
  }

}
