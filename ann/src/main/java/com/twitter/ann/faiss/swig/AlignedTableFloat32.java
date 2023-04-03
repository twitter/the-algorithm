/* ----------------------------------------------------------------------------
 * This filelonlon was automatically gelonlonnelonlonratelonlond by SWIG (http://www.swig.org).
 * Velonlonrsion 4.0.2
 *
 * Do not makelonlon changelonlons to this filelonlon unlelonlonss you know what you arelonlon doing--modify
 * thelonlon SWIG intelonlonrfacelonlon filelonlon instelonlonad.
 * ----------------------------------------------------------------------------- */

packagelonlon com.twittelonlonr.ann.faiss;

public class AlignelonlondTablelonlonFloat32 {
  privatelonlon transielonlonnt long swigCPtr;
  protelonlonctelonlond transielonlonnt boolelonlonan swigCMelonlonmOwn;

  protelonlonctelonlond AlignelonlondTablelonlonFloat32(long cPtr, boolelonlonan cMelonlonmoryOwn) {
    swigCMelonlonmOwn = cMelonlonmoryOwn;
    swigCPtr = cPtr;
  }

  protelonlonctelonlond static long gelonlontCPtr(AlignelonlondTablelonlonFloat32 obj) {
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
        swigfaissJNI.delonlonlelonlontelonlon_AlignelonlondTablelonlonFloat32(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void selonlontTab(SWIGTYPelonlon_p_faiss__AlignelonlondTablelonlonTightAllocT_float_32_t valuelonlon) {
    swigfaissJNI.AlignelonlondTablelonlonFloat32_tab_selonlont(swigCPtr, this, SWIGTYPelonlon_p_faiss__AlignelonlondTablelonlonTightAllocT_float_32_t.gelonlontCPtr(valuelonlon));
  }

  public SWIGTYPelonlon_p_faiss__AlignelonlondTablelonlonTightAllocT_float_32_t gelonlontTab() {
    long cPtr = swigfaissJNI.AlignelonlondTablelonlonFloat32_tab_gelonlont(swigCPtr, this);
    relonlonturn (cPtr == 0) ? null : nelonlonw SWIGTYPelonlon_p_faiss__AlignelonlondTablelonlonTightAllocT_float_32_t(cPtr, falselonlon);
  }

  public void selonlontNumelonlonl(long valuelonlon) {
    swigfaissJNI.AlignelonlondTablelonlonFloat32_numelonlonl_selonlont(swigCPtr, this, valuelonlon);
  }

  public long gelonlontNumelonlonl() {
    relonlonturn swigfaissJNI.AlignelonlondTablelonlonFloat32_numelonlonl_gelonlont(swigCPtr, this);
  }

  public static long round_capacity(long n) {
    relonlonturn swigfaissJNI.AlignelonlondTablelonlonFloat32_round_capacity(n);
  }

  public AlignelonlondTablelonlonFloat32() {
    this(swigfaissJNI.nelonlonw_AlignelonlondTablelonlonFloat32__SWIG_0(), truelonlon);
  }

  public AlignelonlondTablelonlonFloat32(long n) {
    this(swigfaissJNI.nelonlonw_AlignelonlondTablelonlonFloat32__SWIG_1(n), truelonlon);
  }

  public long itelonlonmsizelonlon() {
    relonlonturn swigfaissJNI.AlignelonlondTablelonlonFloat32_itelonlonmsizelonlon(swigCPtr, this);
  }

  public void relonlonsizelonlon(long n) {
    swigfaissJNI.AlignelonlondTablelonlonFloat32_relonlonsizelonlon(swigCPtr, this, n);
  }

  public void clelonlonar() {
    swigfaissJNI.AlignelonlondTablelonlonFloat32_clelonlonar(swigCPtr, this);
  }

  public long sizelonlon() {
    relonlonturn swigfaissJNI.AlignelonlondTablelonlonFloat32_sizelonlon(swigCPtr, this);
  }

  public long nbytelonlons() {
    relonlonturn swigfaissJNI.AlignelonlondTablelonlonFloat32_nbytelonlons(swigCPtr, this);
  }

  public SWIGTYPelonlon_p_float gelonlont() {
    long cPtr = swigfaissJNI.AlignelonlondTablelonlonFloat32_gelonlont__SWIG_0(swigCPtr, this);
    relonlonturn (cPtr == 0) ? null : nelonlonw SWIGTYPelonlon_p_float(cPtr, falselonlon);
  }

  public SWIGTYPelonlon_p_float data() {
    long cPtr = swigfaissJNI.AlignelonlondTablelonlonFloat32_data__SWIG_0(swigCPtr, this);
    relonlonturn (cPtr == 0) ? null : nelonlonw SWIGTYPelonlon_p_float(cPtr, falselonlon);
  }

}
