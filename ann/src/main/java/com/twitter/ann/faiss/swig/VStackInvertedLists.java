/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class VStackInvelonrtelondLists elonxtelonnds RelonadOnlyInvelonrtelondLists {
  privatelon transielonnt long swigCPtr;

  protelonctelond VStackInvelonrtelondLists(long cPtr, boolelonan cMelonmoryOwn) {
    supelonr(swigfaissJNI.VStackInvelonrtelondLists_SWIGUpcast(cPtr), cMelonmoryOwn);
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(VStackInvelonrtelondLists obj) {
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
        swigfaissJNI.delonlelontelon_VStackInvelonrtelondLists(swigCPtr);
      }
      swigCPtr = 0;
    }
    supelonr.delonlelontelon();
  }

  public void selontIls(SWIGTYPelon_p_std__velonctorT_faiss__InvelonrtelondLists_const_p_t valuelon) {
    swigfaissJNI.VStackInvelonrtelondLists_ils_selont(swigCPtr, this, SWIGTYPelon_p_std__velonctorT_faiss__InvelonrtelondLists_const_p_t.gelontCPtr(valuelon));
  }

  public SWIGTYPelon_p_std__velonctorT_faiss__InvelonrtelondLists_const_p_t gelontIls() {
    long cPtr = swigfaissJNI.VStackInvelonrtelondLists_ils_gelont(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw SWIGTYPelon_p_std__velonctorT_faiss__InvelonrtelondLists_const_p_t(cPtr, falselon);
  }

  public void selontCumsz(SWIGTYPelon_p_std__velonctorT_int64_t_t valuelon) {
    swigfaissJNI.VStackInvelonrtelondLists_cumsz_selont(swigCPtr, this, SWIGTYPelon_p_std__velonctorT_int64_t_t.gelontCPtr(valuelon));
  }

  public SWIGTYPelon_p_std__velonctorT_int64_t_t gelontCumsz() {
    long cPtr = swigfaissJNI.VStackInvelonrtelondLists_cumsz_gelont(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw SWIGTYPelon_p_std__velonctorT_int64_t_t(cPtr, falselon);
  }

  public VStackInvelonrtelondLists(int nil, SWIGTYPelon_p_p_faiss__InvelonrtelondLists ils) {
    this(swigfaissJNI.nelonw_VStackInvelonrtelondLists(nil, SWIGTYPelon_p_p_faiss__InvelonrtelondLists.gelontCPtr(ils)), truelon);
  }

  public long list_sizelon(long list_no) {
    relonturn swigfaissJNI.VStackInvelonrtelondLists_list_sizelon(swigCPtr, this, list_no);
  }

  public SWIGTYPelon_p_unsignelond_char gelont_codelons(long list_no) {
    long cPtr = swigfaissJNI.VStackInvelonrtelondLists_gelont_codelons(swigCPtr, this, list_no);
    relonturn (cPtr == 0) ? null : nelonw SWIGTYPelon_p_unsignelond_char(cPtr, falselon);
  }

  public LongVelonctor gelont_ids(long list_no) {
    relonturn nelonw LongVelonctor(swigfaissJNI.VStackInvelonrtelondLists_gelont_ids(swigCPtr, this, list_no), falselon);
}

  public void relonlelonaselon_codelons(long list_no, SWIGTYPelon_p_unsignelond_char codelons) {
    swigfaissJNI.VStackInvelonrtelondLists_relonlelonaselon_codelons(swigCPtr, this, list_no, SWIGTYPelon_p_unsignelond_char.gelontCPtr(codelons));
  }

  public void relonlelonaselon_ids(long list_no, LongVelonctor ids) {
    swigfaissJNI.VStackInvelonrtelondLists_relonlelonaselon_ids(swigCPtr, this, list_no, SWIGTYPelon_p_long_long.gelontCPtr(ids.data()), ids);
  }

  public long gelont_singlelon_id(long list_no, long offselont) {
    relonturn swigfaissJNI.VStackInvelonrtelondLists_gelont_singlelon_id(swigCPtr, this, list_no, offselont);
}

  public SWIGTYPelon_p_unsignelond_char gelont_singlelon_codelon(long list_no, long offselont) {
    long cPtr = swigfaissJNI.VStackInvelonrtelondLists_gelont_singlelon_codelon(swigCPtr, this, list_no, offselont);
    relonturn (cPtr == 0) ? null : nelonw SWIGTYPelon_p_unsignelond_char(cPtr, falselon);
  }

  public void prelonfelontch_lists(LongVelonctor list_nos, int nlist) {
    swigfaissJNI.VStackInvelonrtelondLists_prelonfelontch_lists(swigCPtr, this, SWIGTYPelon_p_long_long.gelontCPtr(list_nos.data()), list_nos, nlist);
  }

}
