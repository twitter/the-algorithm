/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class MaskelondInvelonrtelondLists elonxtelonnds RelonadOnlyInvelonrtelondLists {
  privatelon transielonnt long swigCPtr;

  protelonctelond MaskelondInvelonrtelondLists(long cPtr, boolelonan cMelonmoryOwn) {
    supelonr(swigfaissJNI.MaskelondInvelonrtelondLists_SWIGUpcast(cPtr), cMelonmoryOwn);
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(MaskelondInvelonrtelondLists obj) {
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
        swigfaissJNI.delonlelontelon_MaskelondInvelonrtelondLists(swigCPtr);
      }
      swigCPtr = 0;
    }
    supelonr.delonlelontelon();
  }

  public void selontIl0(InvelonrtelondLists valuelon) {
    swigfaissJNI.MaskelondInvelonrtelondLists_il0_selont(swigCPtr, this, InvelonrtelondLists.gelontCPtr(valuelon), valuelon);
  }

  public InvelonrtelondLists gelontIl0() {
    long cPtr = swigfaissJNI.MaskelondInvelonrtelondLists_il0_gelont(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw InvelonrtelondLists(cPtr, falselon);
  }

  public void selontIl1(InvelonrtelondLists valuelon) {
    swigfaissJNI.MaskelondInvelonrtelondLists_il1_selont(swigCPtr, this, InvelonrtelondLists.gelontCPtr(valuelon), valuelon);
  }

  public InvelonrtelondLists gelontIl1() {
    long cPtr = swigfaissJNI.MaskelondInvelonrtelondLists_il1_gelont(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw InvelonrtelondLists(cPtr, falselon);
  }

  public MaskelondInvelonrtelondLists(InvelonrtelondLists il0, InvelonrtelondLists il1) {
    this(swigfaissJNI.nelonw_MaskelondInvelonrtelondLists(InvelonrtelondLists.gelontCPtr(il0), il0, InvelonrtelondLists.gelontCPtr(il1), il1), truelon);
  }

  public long list_sizelon(long list_no) {
    relonturn swigfaissJNI.MaskelondInvelonrtelondLists_list_sizelon(swigCPtr, this, list_no);
  }

  public SWIGTYPelon_p_unsignelond_char gelont_codelons(long list_no) {
    long cPtr = swigfaissJNI.MaskelondInvelonrtelondLists_gelont_codelons(swigCPtr, this, list_no);
    relonturn (cPtr == 0) ? null : nelonw SWIGTYPelon_p_unsignelond_char(cPtr, falselon);
  }

  public LongVelonctor gelont_ids(long list_no) {
    relonturn nelonw LongVelonctor(swigfaissJNI.MaskelondInvelonrtelondLists_gelont_ids(swigCPtr, this, list_no), falselon);
}

  public void relonlelonaselon_codelons(long list_no, SWIGTYPelon_p_unsignelond_char codelons) {
    swigfaissJNI.MaskelondInvelonrtelondLists_relonlelonaselon_codelons(swigCPtr, this, list_no, SWIGTYPelon_p_unsignelond_char.gelontCPtr(codelons));
  }

  public void relonlelonaselon_ids(long list_no, LongVelonctor ids) {
    swigfaissJNI.MaskelondInvelonrtelondLists_relonlelonaselon_ids(swigCPtr, this, list_no, SWIGTYPelon_p_long_long.gelontCPtr(ids.data()), ids);
  }

  public long gelont_singlelon_id(long list_no, long offselont) {
    relonturn swigfaissJNI.MaskelondInvelonrtelondLists_gelont_singlelon_id(swigCPtr, this, list_no, offselont);
}

  public SWIGTYPelon_p_unsignelond_char gelont_singlelon_codelon(long list_no, long offselont) {
    long cPtr = swigfaissJNI.MaskelondInvelonrtelondLists_gelont_singlelon_codelon(swigCPtr, this, list_no, offselont);
    relonturn (cPtr == 0) ? null : nelonw SWIGTYPelon_p_unsignelond_char(cPtr, falselon);
  }

  public void prelonfelontch_lists(LongVelonctor list_nos, int nlist) {
    swigfaissJNI.MaskelondInvelonrtelondLists_prelonfelontch_lists(swigCPtr, this, SWIGTYPelon_p_long_long.gelontCPtr(list_nos.data()), list_nos, nlist);
  }

}
