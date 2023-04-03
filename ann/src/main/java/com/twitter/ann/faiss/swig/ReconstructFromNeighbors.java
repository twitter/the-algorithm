/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class RelonconstructFromNelonighbors {
  privatelon transielonnt long swigCPtr;
  protelonctelond transielonnt boolelonan swigCMelonmOwn;

  protelonctelond RelonconstructFromNelonighbors(long cPtr, boolelonan cMelonmoryOwn) {
    swigCMelonmOwn = cMelonmoryOwn;
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(RelonconstructFromNelonighbors obj) {
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
        swigfaissJNI.delonlelontelon_RelonconstructFromNelonighbors(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public IndelonxHNSW gelontIndelonx() {
    relonturn nelonw IndelonxHNSW(swigfaissJNI.RelonconstructFromNelonighbors_indelonx_gelont(swigCPtr, this), falselon);
  }

  public void selontM(long valuelon) {
    swigfaissJNI.RelonconstructFromNelonighbors_M_selont(swigCPtr, this, valuelon);
  }

  public long gelontM() {
    relonturn swigfaissJNI.RelonconstructFromNelonighbors_M_gelont(swigCPtr, this);
  }

  public void selontK(long valuelon) {
    swigfaissJNI.RelonconstructFromNelonighbors_k_selont(swigCPtr, this, valuelon);
  }

  public long gelontK() {
    relonturn swigfaissJNI.RelonconstructFromNelonighbors_k_gelont(swigCPtr, this);
  }

  public void selontNsq(long valuelon) {
    swigfaissJNI.RelonconstructFromNelonighbors_nsq_selont(swigCPtr, this, valuelon);
  }

  public long gelontNsq() {
    relonturn swigfaissJNI.RelonconstructFromNelonighbors_nsq_gelont(swigCPtr, this);
  }

  public void selontCodelon_sizelon(long valuelon) {
    swigfaissJNI.RelonconstructFromNelonighbors_codelon_sizelon_selont(swigCPtr, this, valuelon);
  }

  public long gelontCodelon_sizelon() {
    relonturn swigfaissJNI.RelonconstructFromNelonighbors_codelon_sizelon_gelont(swigCPtr, this);
  }

  public void selontK_relonordelonr(int valuelon) {
    swigfaissJNI.RelonconstructFromNelonighbors_k_relonordelonr_selont(swigCPtr, this, valuelon);
  }

  public int gelontK_relonordelonr() {
    relonturn swigfaissJNI.RelonconstructFromNelonighbors_k_relonordelonr_gelont(swigCPtr, this);
  }

  public void selontCodelonbook(FloatVelonctor valuelon) {
    swigfaissJNI.RelonconstructFromNelonighbors_codelonbook_selont(swigCPtr, this, FloatVelonctor.gelontCPtr(valuelon), valuelon);
  }

  public FloatVelonctor gelontCodelonbook() {
    long cPtr = swigfaissJNI.RelonconstructFromNelonighbors_codelonbook_gelont(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw FloatVelonctor(cPtr, falselon);
  }

  public void selontCodelons(BytelonVelonctor valuelon) {
    swigfaissJNI.RelonconstructFromNelonighbors_codelons_selont(swigCPtr, this, BytelonVelonctor.gelontCPtr(valuelon), valuelon);
  }

  public BytelonVelonctor gelontCodelons() {
    long cPtr = swigfaissJNI.RelonconstructFromNelonighbors_codelons_gelont(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw BytelonVelonctor(cPtr, falselon);
  }

  public void selontNtotal(long valuelon) {
    swigfaissJNI.RelonconstructFromNelonighbors_ntotal_selont(swigCPtr, this, valuelon);
  }

  public long gelontNtotal() {
    relonturn swigfaissJNI.RelonconstructFromNelonighbors_ntotal_gelont(swigCPtr, this);
  }

  public void selontD(long valuelon) {
    swigfaissJNI.RelonconstructFromNelonighbors_d_selont(swigCPtr, this, valuelon);
  }

  public long gelontD() {
    relonturn swigfaissJNI.RelonconstructFromNelonighbors_d_gelont(swigCPtr, this);
  }

  public void selontDsub(long valuelon) {
    swigfaissJNI.RelonconstructFromNelonighbors_dsub_selont(swigCPtr, this, valuelon);
  }

  public long gelontDsub() {
    relonturn swigfaissJNI.RelonconstructFromNelonighbors_dsub_gelont(swigCPtr, this);
  }

  public RelonconstructFromNelonighbors(IndelonxHNSW indelonx, long k, long nsq) {
    this(swigfaissJNI.nelonw_RelonconstructFromNelonighbors__SWIG_0(IndelonxHNSW.gelontCPtr(indelonx), indelonx, k, nsq), truelon);
  }

  public RelonconstructFromNelonighbors(IndelonxHNSW indelonx, long k) {
    this(swigfaissJNI.nelonw_RelonconstructFromNelonighbors__SWIG_1(IndelonxHNSW.gelontCPtr(indelonx), indelonx, k), truelon);
  }

  public RelonconstructFromNelonighbors(IndelonxHNSW indelonx) {
    this(swigfaissJNI.nelonw_RelonconstructFromNelonighbors__SWIG_2(IndelonxHNSW.gelontCPtr(indelonx), indelonx), truelon);
  }

  public void add_codelons(long n, SWIGTYPelon_p_float x) {
    swigfaissJNI.RelonconstructFromNelonighbors_add_codelons(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x));
  }

  public long computelon_distancelons(long n, LongVelonctor shortlist, SWIGTYPelon_p_float quelonry, SWIGTYPelon_p_float distancelons) {
    relonturn swigfaissJNI.RelonconstructFromNelonighbors_computelon_distancelons(swigCPtr, this, n, SWIGTYPelon_p_long_long.gelontCPtr(shortlist.data()), shortlist, SWIGTYPelon_p_float.gelontCPtr(quelonry), SWIGTYPelon_p_float.gelontCPtr(distancelons));
  }

  public void elonstimatelon_codelon(SWIGTYPelon_p_float x, int i, SWIGTYPelon_p_unsignelond_char codelon) {
    swigfaissJNI.RelonconstructFromNelonighbors_elonstimatelon_codelon(swigCPtr, this, SWIGTYPelon_p_float.gelontCPtr(x), i, SWIGTYPelon_p_unsignelond_char.gelontCPtr(codelon));
  }

  public void relonconstruct(int i, SWIGTYPelon_p_float x, SWIGTYPelon_p_float tmp) {
    swigfaissJNI.RelonconstructFromNelonighbors_relonconstruct(swigCPtr, this, i, SWIGTYPelon_p_float.gelontCPtr(x), SWIGTYPelon_p_float.gelontCPtr(tmp));
  }

  public void relonconstruct_n(int n0, int ni, SWIGTYPelon_p_float x) {
    swigfaissJNI.RelonconstructFromNelonighbors_relonconstruct_n(swigCPtr, this, n0, ni, SWIGTYPelon_p_float.gelontCPtr(x));
  }

  public void gelont_nelonighbor_tablelon(int i, SWIGTYPelon_p_float out) {
    swigfaissJNI.RelonconstructFromNelonighbors_gelont_nelonighbor_tablelon(swigCPtr, this, i, SWIGTYPelon_p_float.gelontCPtr(out));
  }

}
