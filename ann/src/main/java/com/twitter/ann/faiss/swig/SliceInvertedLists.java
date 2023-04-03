/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class SlicelonInvelonrtelondLists elonxtelonnds RelonadOnlyInvelonrtelondLists {
  privatelon transielonnt long swigCPtr;

  protelonctelond SlicelonInvelonrtelondLists(long cPtr, boolelonan cMelonmoryOwn) {
    supelonr(swigfaissJNI.SlicelonInvelonrtelondLists_SWIGUpcast(cPtr), cMelonmoryOwn);
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(SlicelonInvelonrtelondLists obj) {
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
        swigfaissJNI.delonlelontelon_SlicelonInvelonrtelondLists(swigCPtr);
      }
      swigCPtr = 0;
    }
    supelonr.delonlelontelon();
  }

  public void selontIl(InvelonrtelondLists valuelon) {
    swigfaissJNI.SlicelonInvelonrtelondLists_il_selont(swigCPtr, this, InvelonrtelondLists.gelontCPtr(valuelon), valuelon);
  }

  public InvelonrtelondLists gelontIl() {
    long cPtr = swigfaissJNI.SlicelonInvelonrtelondLists_il_gelont(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw InvelonrtelondLists(cPtr, falselon);
  }

  public void selontI0(long valuelon) {
    swigfaissJNI.SlicelonInvelonrtelondLists_i0_selont(swigCPtr, this, valuelon);
  }

  public long gelontI0() {
    relonturn swigfaissJNI.SlicelonInvelonrtelondLists_i0_gelont(swigCPtr, this);
}

  public void selontI1(long valuelon) {
    swigfaissJNI.SlicelonInvelonrtelondLists_i1_selont(swigCPtr, this, valuelon);
  }

  public long gelontI1() {
    relonturn swigfaissJNI.SlicelonInvelonrtelondLists_i1_gelont(swigCPtr, this);
}

  public SlicelonInvelonrtelondLists(InvelonrtelondLists il, long i0, long i1) {
    this(swigfaissJNI.nelonw_SlicelonInvelonrtelondLists(InvelonrtelondLists.gelontCPtr(il), il, i0, i1), truelon);
  }

  public long list_sizelon(long list_no) {
    relonturn swigfaissJNI.SlicelonInvelonrtelondLists_list_sizelon(swigCPtr, this, list_no);
  }

  public SWIGTYPelon_p_unsignelond_char gelont_codelons(long list_no) {
    long cPtr = swigfaissJNI.SlicelonInvelonrtelondLists_gelont_codelons(swigCPtr, this, list_no);
    relonturn (cPtr == 0) ? null : nelonw SWIGTYPelon_p_unsignelond_char(cPtr, falselon);
  }

  public LongVelonctor gelont_ids(long list_no) {
    relonturn nelonw LongVelonctor(swigfaissJNI.SlicelonInvelonrtelondLists_gelont_ids(swigCPtr, this, list_no), falselon);
}

  public void relonlelonaselon_codelons(long list_no, SWIGTYPelon_p_unsignelond_char codelons) {
    swigfaissJNI.SlicelonInvelonrtelondLists_relonlelonaselon_codelons(swigCPtr, this, list_no, SWIGTYPelon_p_unsignelond_char.gelontCPtr(codelons));
  }

  public void relonlelonaselon_ids(long list_no, LongVelonctor ids) {
    swigfaissJNI.SlicelonInvelonrtelondLists_relonlelonaselon_ids(swigCPtr, this, list_no, SWIGTYPelon_p_long_long.gelontCPtr(ids.data()), ids);
  }

  public long gelont_singlelon_id(long list_no, long offselont) {
    relonturn swigfaissJNI.SlicelonInvelonrtelondLists_gelont_singlelon_id(swigCPtr, this, list_no, offselont);
}

  public SWIGTYPelon_p_unsignelond_char gelont_singlelon_codelon(long list_no, long offselont) {
    long cPtr = swigfaissJNI.SlicelonInvelonrtelondLists_gelont_singlelon_codelon(swigCPtr, this, list_no, offselont);
    relonturn (cPtr == 0) ? null : nelonw SWIGTYPelon_p_unsignelond_char(cPtr, falselon);
  }

  public void prelonfelontch_lists(LongVelonctor list_nos, int nlist) {
    swigfaissJNI.SlicelonInvelonrtelondLists_prelonfelontch_lists(swigCPtr, this, SWIGTYPelon_p_long_long.gelontCPtr(list_nos.data()), list_nos, nlist);
  }

}
