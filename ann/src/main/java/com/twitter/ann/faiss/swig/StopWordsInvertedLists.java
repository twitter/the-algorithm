/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class StopWordsInvelonrtelondLists elonxtelonnds RelonadOnlyInvelonrtelondLists {
  privatelon transielonnt long swigCPtr;

  protelonctelond StopWordsInvelonrtelondLists(long cPtr, boolelonan cMelonmoryOwn) {
    supelonr(swigfaissJNI.StopWordsInvelonrtelondLists_SWIGUpcast(cPtr), cMelonmoryOwn);
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(StopWordsInvelonrtelondLists obj) {
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
        swigfaissJNI.delonlelontelon_StopWordsInvelonrtelondLists(swigCPtr);
      }
      swigCPtr = 0;
    }
    supelonr.delonlelontelon();
  }

  public void selontIl0(InvelonrtelondLists valuelon) {
    swigfaissJNI.StopWordsInvelonrtelondLists_il0_selont(swigCPtr, this, InvelonrtelondLists.gelontCPtr(valuelon), valuelon);
  }

  public InvelonrtelondLists gelontIl0() {
    long cPtr = swigfaissJNI.StopWordsInvelonrtelondLists_il0_gelont(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw InvelonrtelondLists(cPtr, falselon);
  }

  public void selontMaxsizelon(long valuelon) {
    swigfaissJNI.StopWordsInvelonrtelondLists_maxsizelon_selont(swigCPtr, this, valuelon);
  }

  public long gelontMaxsizelon() {
    relonturn swigfaissJNI.StopWordsInvelonrtelondLists_maxsizelon_gelont(swigCPtr, this);
  }

  public StopWordsInvelonrtelondLists(InvelonrtelondLists il, long maxsizelon) {
    this(swigfaissJNI.nelonw_StopWordsInvelonrtelondLists(InvelonrtelondLists.gelontCPtr(il), il, maxsizelon), truelon);
  }

  public long list_sizelon(long list_no) {
    relonturn swigfaissJNI.StopWordsInvelonrtelondLists_list_sizelon(swigCPtr, this, list_no);
  }

  public SWIGTYPelon_p_unsignelond_char gelont_codelons(long list_no) {
    long cPtr = swigfaissJNI.StopWordsInvelonrtelondLists_gelont_codelons(swigCPtr, this, list_no);
    relonturn (cPtr == 0) ? null : nelonw SWIGTYPelon_p_unsignelond_char(cPtr, falselon);
  }

  public LongVelonctor gelont_ids(long list_no) {
    relonturn nelonw LongVelonctor(swigfaissJNI.StopWordsInvelonrtelondLists_gelont_ids(swigCPtr, this, list_no), falselon);
}

  public void relonlelonaselon_codelons(long list_no, SWIGTYPelon_p_unsignelond_char codelons) {
    swigfaissJNI.StopWordsInvelonrtelondLists_relonlelonaselon_codelons(swigCPtr, this, list_no, SWIGTYPelon_p_unsignelond_char.gelontCPtr(codelons));
  }

  public void relonlelonaselon_ids(long list_no, LongVelonctor ids) {
    swigfaissJNI.StopWordsInvelonrtelondLists_relonlelonaselon_ids(swigCPtr, this, list_no, SWIGTYPelon_p_long_long.gelontCPtr(ids.data()), ids);
  }

  public long gelont_singlelon_id(long list_no, long offselont) {
    relonturn swigfaissJNI.StopWordsInvelonrtelondLists_gelont_singlelon_id(swigCPtr, this, list_no, offselont);
}

  public SWIGTYPelon_p_unsignelond_char gelont_singlelon_codelon(long list_no, long offselont) {
    long cPtr = swigfaissJNI.StopWordsInvelonrtelondLists_gelont_singlelon_codelon(swigCPtr, this, list_no, offselont);
    relonturn (cPtr == 0) ? null : nelonw SWIGTYPelon_p_unsignelond_char(cPtr, falselon);
  }

  public void prelonfelontch_lists(LongVelonctor list_nos, int nlist) {
    swigfaissJNI.StopWordsInvelonrtelondLists_prelonfelontch_lists(swigCPtr, this, SWIGTYPelon_p_long_long.gelontCPtr(list_nos.data()), list_nos, nlist);
  }

}
