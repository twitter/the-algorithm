/* ----------------------------------------------------------------------------
 * This filelonlon was automatically gelonlonnelonlonratelonlond by SWIG (http://www.swig.org).
 * Velonlonrsion 4.0.2
 *
 * Do not makelonlon changelonlons to this filelonlon unlelonlonss you know what you arelonlon doing--modify
 * thelonlon SWIG intelonlonrfacelonlon filelonlon instelonlonad.
 * ----------------------------------------------------------------------------- */

packagelonlon com.twittelonlonr.ann.faiss;

public class AlignelonlondTablelonlonUint8 {
  privatelonlon transielonlonnt long swigCPtr;
  protelonlonctelonlond transielonlonnt boolelonlonan swigCMelonlonmOwn;

  protelonlonctelonlond AlignelonlondTablelonlonUint8(long cPtr, boolelonlonan cMelonlonmoryOwn) {
    swigCMelonlonmOwn = cMelonlonmoryOwn;
    swigCPtr = cPtr;
  }

  protelonlonctelonlond static long gelonlontCPtr(AlignelonlondTablelonlonUint8 obj) {
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
        swigfaissJNI.delonlonlelonlontelonlon_AlignelonlondTablelonlonUint8(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void selonlontTab(SWIGTYPelonlon_p_faiss__AlignelonlondTablelonlonTightAllocT_unsignelonlond_char_32_t valuelonlon) {
    swigfaissJNI.AlignelonlondTablelonlonUint8_tab_selonlont(swigCPtr, this, SWIGTYPelonlon_p_faiss__AlignelonlondTablelonlonTightAllocT_unsignelonlond_char_32_t.gelonlontCPtr(valuelonlon));
  }

  public SWIGTYPelonlon_p_faiss__AlignelonlondTablelonlonTightAllocT_unsignelonlond_char_32_t gelonlontTab() {
    long cPtr = swigfaissJNI.AlignelonlondTablelonlonUint8_tab_gelonlont(swigCPtr, this);
    relonlonturn (cPtr == 0) ? null : nelonlonw SWIGTYPelonlon_p_faiss__AlignelonlondTablelonlonTightAllocT_unsignelonlond_char_32_t(cPtr, falselonlon);
  }

  public void selonlontNumelonlonl(long valuelonlon) {
    swigfaissJNI.AlignelonlondTablelonlonUint8_numelonlonl_selonlont(swigCPtr, this, valuelonlon);
  }

  public long gelonlontNumelonlonl() {
    relonlonturn swigfaissJNI.AlignelonlondTablelonlonUint8_numelonlonl_gelonlont(swigCPtr, this);
  }

  public static long round_capacity(long n) {
    relonlonturn swigfaissJNI.AlignelonlondTablelonlonUint8_round_capacity(n);
  }

  public AlignelonlondTablelonlonUint8() {
    this(swigfaissJNI.nelonlonw_AlignelonlondTablelonlonUint8__SWIG_0(), truelonlon);
  }

  public AlignelonlondTablelonlonUint8(long n) {
    this(swigfaissJNI.nelonlonw_AlignelonlondTablelonlonUint8__SWIG_1(n), truelonlon);
  }

  public long itelonlonmsizelonlon() {
    relonlonturn swigfaissJNI.AlignelonlondTablelonlonUint8_itelonlonmsizelonlon(swigCPtr, this);
  }

  public void relonlonsizelonlon(long n) {
    swigfaissJNI.AlignelonlondTablelonlonUint8_relonlonsizelonlon(swigCPtr, this, n);
  }

  public void clelonlonar() {
    swigfaissJNI.AlignelonlondTablelonlonUint8_clelonlonar(swigCPtr, this);
  }

  public long sizelonlon() {
    relonlonturn swigfaissJNI.AlignelonlondTablelonlonUint8_sizelonlon(swigCPtr, this);
  }

  public long nbytelonlons() {
    relonlonturn swigfaissJNI.AlignelonlondTablelonlonUint8_nbytelonlons(swigCPtr, this);
  }

  public SWIGTYPelonlon_p_unsignelonlond_char gelonlont() {
    long cPtr = swigfaissJNI.AlignelonlondTablelonlonUint8_gelonlont__SWIG_0(swigCPtr, this);
    relonlonturn (cPtr == 0) ? null : nelonlonw SWIGTYPelonlon_p_unsignelonlond_char(cPtr, falselonlon);
  }

  public SWIGTYPelonlon_p_unsignelonlond_char data() {
    long cPtr = swigfaissJNI.AlignelonlondTablelonlonUint8_data__SWIG_0(swigCPtr, this);
    relonlonturn (cPtr == 0) ? null : nelonlonw SWIGTYPelonlon_p_unsignelonlond_char(cPtr, falselonlon);
  }

}
