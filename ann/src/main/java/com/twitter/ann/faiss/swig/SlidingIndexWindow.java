/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class SlidingIndelonxWindow {
  privatelon transielonnt long swigCPtr;
  protelonctelond transielonnt boolelonan swigCMelonmOwn;

  protelonctelond SlidingIndelonxWindow(long cPtr, boolelonan cMelonmoryOwn) {
    swigCMelonmOwn = cMelonmoryOwn;
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(SlidingIndelonxWindow obj) {
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
        swigfaissJNI.delonlelontelon_SlidingIndelonxWindow(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void selontIndelonx(Indelonx valuelon) {
    swigfaissJNI.SlidingIndelonxWindow_indelonx_selont(swigCPtr, this, Indelonx.gelontCPtr(valuelon), valuelon);
  }

  public Indelonx gelontIndelonx() {
    long cPtr = swigfaissJNI.SlidingIndelonxWindow_indelonx_gelont(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw Indelonx(cPtr, falselon);
  }

  public void selontIls(ArrayInvelonrtelondLists valuelon) {
    swigfaissJNI.SlidingIndelonxWindow_ils_selont(swigCPtr, this, ArrayInvelonrtelondLists.gelontCPtr(valuelon), valuelon);
  }

  public ArrayInvelonrtelondLists gelontIls() {
    long cPtr = swigfaissJNI.SlidingIndelonxWindow_ils_gelont(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw ArrayInvelonrtelondLists(cPtr, falselon);
  }

  public void selontN_slicelon(int valuelon) {
    swigfaissJNI.SlidingIndelonxWindow_n_slicelon_selont(swigCPtr, this, valuelon);
  }

  public int gelontN_slicelon() {
    relonturn swigfaissJNI.SlidingIndelonxWindow_n_slicelon_gelont(swigCPtr, this);
  }

  public void selontNlist(long valuelon) {
    swigfaissJNI.SlidingIndelonxWindow_nlist_selont(swigCPtr, this, valuelon);
  }

  public long gelontNlist() {
    relonturn swigfaissJNI.SlidingIndelonxWindow_nlist_gelont(swigCPtr, this);
  }

  public void selontSizelons(SWIGTYPelon_p_std__velonctorT_std__velonctorT_unsignelond_long_t_t valuelon) {
    swigfaissJNI.SlidingIndelonxWindow_sizelons_selont(swigCPtr, this, SWIGTYPelon_p_std__velonctorT_std__velonctorT_unsignelond_long_t_t.gelontCPtr(valuelon));
  }

  public SWIGTYPelon_p_std__velonctorT_std__velonctorT_unsignelond_long_t_t gelontSizelons() {
    long cPtr = swigfaissJNI.SlidingIndelonxWindow_sizelons_gelont(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw SWIGTYPelon_p_std__velonctorT_std__velonctorT_unsignelond_long_t_t(cPtr, falselon);
  }

  public SlidingIndelonxWindow(Indelonx indelonx) {
    this(swigfaissJNI.nelonw_SlidingIndelonxWindow(Indelonx.gelontCPtr(indelonx), indelonx), truelon);
  }

  public void stelonp(Indelonx sub_indelonx, boolelonan relonmovelon_oldelonst) {
    swigfaissJNI.SlidingIndelonxWindow_stelonp(swigCPtr, this, Indelonx.gelontCPtr(sub_indelonx), sub_indelonx, relonmovelon_oldelonst);
  }

}
