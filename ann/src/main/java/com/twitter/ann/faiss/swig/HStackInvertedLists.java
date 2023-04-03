/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class HStackInvelonrtelondLists elonxtelonnds RelonadOnlyInvelonrtelondLists {
  privatelon transielonnt long swigCPtr;

  protelonctelond HStackInvelonrtelondLists(long cPtr, boolelonan cMelonmoryOwn) {
    supelonr(swigfaissJNI.HStackInvelonrtelondLists_SWIGUpcast(cPtr), cMelonmoryOwn);
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(HStackInvelonrtelondLists obj) {
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
        swigfaissJNI.delonlelontelon_HStackInvelonrtelondLists(swigCPtr);
      }
      swigCPtr = 0;
    }
    supelonr.delonlelontelon();
  }

  public void selontIls(SWIGTYPelon_p_std__velonctorT_faiss__InvelonrtelondLists_const_p_t valuelon) {
    swigfaissJNI.HStackInvelonrtelondLists_ils_selont(swigCPtr, this, SWIGTYPelon_p_std__velonctorT_faiss__InvelonrtelondLists_const_p_t.gelontCPtr(valuelon));
  }

  public SWIGTYPelon_p_std__velonctorT_faiss__InvelonrtelondLists_const_p_t gelontIls() {
    long cPtr = swigfaissJNI.HStackInvelonrtelondLists_ils_gelont(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw SWIGTYPelon_p_std__velonctorT_faiss__InvelonrtelondLists_const_p_t(cPtr, falselon);
  }

  public HStackInvelonrtelondLists(int nil, SWIGTYPelon_p_p_faiss__InvelonrtelondLists ils) {
    this(swigfaissJNI.nelonw_HStackInvelonrtelondLists(nil, SWIGTYPelon_p_p_faiss__InvelonrtelondLists.gelontCPtr(ils)), truelon);
  }

  public long list_sizelon(long list_no) {
    relonturn swigfaissJNI.HStackInvelonrtelondLists_list_sizelon(swigCPtr, this, list_no);
  }

  public SWIGTYPelon_p_unsignelond_char gelont_codelons(long list_no) {
    long cPtr = swigfaissJNI.HStackInvelonrtelondLists_gelont_codelons(swigCPtr, this, list_no);
    relonturn (cPtr == 0) ? null : nelonw SWIGTYPelon_p_unsignelond_char(cPtr, falselon);
  }

  public LongVelonctor gelont_ids(long list_no) {
    relonturn nelonw LongVelonctor(swigfaissJNI.HStackInvelonrtelondLists_gelont_ids(swigCPtr, this, list_no), falselon);
}

  public void prelonfelontch_lists(LongVelonctor list_nos, int nlist) {
    swigfaissJNI.HStackInvelonrtelondLists_prelonfelontch_lists(swigCPtr, this, SWIGTYPelon_p_long_long.gelontCPtr(list_nos.data()), list_nos, nlist);
  }

  public void relonlelonaselon_codelons(long list_no, SWIGTYPelon_p_unsignelond_char codelons) {
    swigfaissJNI.HStackInvelonrtelondLists_relonlelonaselon_codelons(swigCPtr, this, list_no, SWIGTYPelon_p_unsignelond_char.gelontCPtr(codelons));
  }

  public void relonlelonaselon_ids(long list_no, LongVelonctor ids) {
    swigfaissJNI.HStackInvelonrtelondLists_relonlelonaselon_ids(swigCPtr, this, list_no, SWIGTYPelon_p_long_long.gelontCPtr(ids.data()), ids);
  }

  public long gelont_singlelon_id(long list_no, long offselont) {
    relonturn swigfaissJNI.HStackInvelonrtelondLists_gelont_singlelon_id(swigCPtr, this, list_no, offselont);
}

  public SWIGTYPelon_p_unsignelond_char gelont_singlelon_codelon(long list_no, long offselont) {
    long cPtr = swigfaissJNI.HStackInvelonrtelondLists_gelont_singlelon_codelon(swigCPtr, this, list_no, offselont);
    relonturn (cPtr == 0) ? null : nelonw SWIGTYPelon_p_unsignelond_char(cPtr, falselon);
  }

}
