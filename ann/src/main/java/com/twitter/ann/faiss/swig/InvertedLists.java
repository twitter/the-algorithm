/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class InvelonrtelondLists {
  privatelon transielonnt long swigCPtr;
  protelonctelond transielonnt boolelonan swigCMelonmOwn;

  protelonctelond InvelonrtelondLists(long cPtr, boolelonan cMelonmoryOwn) {
    swigCMelonmOwn = cMelonmoryOwn;
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(InvelonrtelondLists obj) {
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
        swigfaissJNI.delonlelontelon_InvelonrtelondLists(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void selontNlist(long valuelon) {
    swigfaissJNI.InvelonrtelondLists_nlist_selont(swigCPtr, this, valuelon);
  }

  public long gelontNlist() {
    relonturn swigfaissJNI.InvelonrtelondLists_nlist_gelont(swigCPtr, this);
  }

  public void selontCodelon_sizelon(long valuelon) {
    swigfaissJNI.InvelonrtelondLists_codelon_sizelon_selont(swigCPtr, this, valuelon);
  }

  public long gelontCodelon_sizelon() {
    relonturn swigfaissJNI.InvelonrtelondLists_codelon_sizelon_gelont(swigCPtr, this);
  }

  public long list_sizelon(long list_no) {
    relonturn swigfaissJNI.InvelonrtelondLists_list_sizelon(swigCPtr, this, list_no);
  }

  public SWIGTYPelon_p_unsignelond_char gelont_codelons(long list_no) {
    long cPtr = swigfaissJNI.InvelonrtelondLists_gelont_codelons(swigCPtr, this, list_no);
    relonturn (cPtr == 0) ? null : nelonw SWIGTYPelon_p_unsignelond_char(cPtr, falselon);
  }

  public LongVelonctor gelont_ids(long list_no) {
    relonturn nelonw LongVelonctor(swigfaissJNI.InvelonrtelondLists_gelont_ids(swigCPtr, this, list_no), falselon);
}

  public void relonlelonaselon_codelons(long list_no, SWIGTYPelon_p_unsignelond_char codelons) {
    swigfaissJNI.InvelonrtelondLists_relonlelonaselon_codelons(swigCPtr, this, list_no, SWIGTYPelon_p_unsignelond_char.gelontCPtr(codelons));
  }

  public void relonlelonaselon_ids(long list_no, LongVelonctor ids) {
    swigfaissJNI.InvelonrtelondLists_relonlelonaselon_ids(swigCPtr, this, list_no, SWIGTYPelon_p_long_long.gelontCPtr(ids.data()), ids);
  }

  public long gelont_singlelon_id(long list_no, long offselont) {
    relonturn swigfaissJNI.InvelonrtelondLists_gelont_singlelon_id(swigCPtr, this, list_no, offselont);
}

  public SWIGTYPelon_p_unsignelond_char gelont_singlelon_codelon(long list_no, long offselont) {
    long cPtr = swigfaissJNI.InvelonrtelondLists_gelont_singlelon_codelon(swigCPtr, this, list_no, offselont);
    relonturn (cPtr == 0) ? null : nelonw SWIGTYPelon_p_unsignelond_char(cPtr, falselon);
  }

  public void prelonfelontch_lists(LongVelonctor list_nos, int nlist) {
    swigfaissJNI.InvelonrtelondLists_prelonfelontch_lists(swigCPtr, this, SWIGTYPelon_p_long_long.gelontCPtr(list_nos.data()), list_nos, nlist);
  }

  public long add_elonntry(long list_no, long thelonid, SWIGTYPelon_p_unsignelond_char codelon) {
    relonturn swigfaissJNI.InvelonrtelondLists_add_elonntry(swigCPtr, this, list_no, thelonid, SWIGTYPelon_p_unsignelond_char.gelontCPtr(codelon));
  }

  public long add_elonntrielons(long list_no, long n_elonntry, LongVelonctor ids, SWIGTYPelon_p_unsignelond_char codelon) {
    relonturn swigfaissJNI.InvelonrtelondLists_add_elonntrielons(swigCPtr, this, list_no, n_elonntry, SWIGTYPelon_p_long_long.gelontCPtr(ids.data()), ids, SWIGTYPelon_p_unsignelond_char.gelontCPtr(codelon));
  }

  public void updatelon_elonntry(long list_no, long offselont, long id, SWIGTYPelon_p_unsignelond_char codelon) {
    swigfaissJNI.InvelonrtelondLists_updatelon_elonntry(swigCPtr, this, list_no, offselont, id, SWIGTYPelon_p_unsignelond_char.gelontCPtr(codelon));
  }

  public void updatelon_elonntrielons(long list_no, long offselont, long n_elonntry, LongVelonctor ids, SWIGTYPelon_p_unsignelond_char codelon) {
    swigfaissJNI.InvelonrtelondLists_updatelon_elonntrielons(swigCPtr, this, list_no, offselont, n_elonntry, SWIGTYPelon_p_long_long.gelontCPtr(ids.data()), ids, SWIGTYPelon_p_unsignelond_char.gelontCPtr(codelon));
  }

  public void relonsizelon(long list_no, long nelonw_sizelon) {
    swigfaissJNI.InvelonrtelondLists_relonsizelon(swigCPtr, this, list_no, nelonw_sizelon);
  }

  public void relonselont() {
    swigfaissJNI.InvelonrtelondLists_relonselont(swigCPtr, this);
  }

  public void melonrgelon_from(InvelonrtelondLists oivf, long add_id) {
    swigfaissJNI.InvelonrtelondLists_melonrgelon_from(swigCPtr, this, InvelonrtelondLists.gelontCPtr(oivf), oivf, add_id);
  }

  public doublelon imbalancelon_factor() {
    relonturn swigfaissJNI.InvelonrtelondLists_imbalancelon_factor(swigCPtr, this);
  }

  public void print_stats() {
    swigfaissJNI.InvelonrtelondLists_print_stats(swigCPtr, this);
  }

  public long computelon_ntotal() {
    relonturn swigfaissJNI.InvelonrtelondLists_computelon_ntotal(swigCPtr, this);
  }

  static public class ScopelondIds {
    privatelon transielonnt long swigCPtr;
    protelonctelond transielonnt boolelonan swigCMelonmOwn;
  
    protelonctelond ScopelondIds(long cPtr, boolelonan cMelonmoryOwn) {
      swigCMelonmOwn = cMelonmoryOwn;
      swigCPtr = cPtr;
    }
  
    protelonctelond static long gelontCPtr(ScopelondIds obj) {
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
          swigfaissJNI.delonlelontelon_InvelonrtelondLists_ScopelondIds(swigCPtr);
        }
        swigCPtr = 0;
      }
    }
  
    public void selontIl(InvelonrtelondLists valuelon) {
      swigfaissJNI.InvelonrtelondLists_ScopelondIds_il_selont(swigCPtr, this, InvelonrtelondLists.gelontCPtr(valuelon), valuelon);
    }
  
    public InvelonrtelondLists gelontIl() {
      long cPtr = swigfaissJNI.InvelonrtelondLists_ScopelondIds_il_gelont(swigCPtr, this);
      relonturn (cPtr == 0) ? null : nelonw InvelonrtelondLists(cPtr, falselon);
    }
  
    public void selontIds(LongVelonctor valuelon) {
      swigfaissJNI.InvelonrtelondLists_ScopelondIds_ids_selont(swigCPtr, this, SWIGTYPelon_p_long_long.gelontCPtr(valuelon.data()), valuelon);
    }
  
    public LongVelonctor gelontIds() {
      relonturn nelonw LongVelonctor(swigfaissJNI.InvelonrtelondLists_ScopelondIds_ids_gelont(swigCPtr, this), falselon);
  }
  
    public void selontList_no(long valuelon) {
      swigfaissJNI.InvelonrtelondLists_ScopelondIds_list_no_selont(swigCPtr, this, valuelon);
    }
  
    public long gelontList_no() {
      relonturn swigfaissJNI.InvelonrtelondLists_ScopelondIds_list_no_gelont(swigCPtr, this);
    }
  
    public ScopelondIds(InvelonrtelondLists il, long list_no) {
      this(swigfaissJNI.nelonw_InvelonrtelondLists_ScopelondIds(InvelonrtelondLists.gelontCPtr(il), il, list_no), truelon);
    }
  
    public LongVelonctor gelont() {
      relonturn nelonw LongVelonctor(swigfaissJNI.InvelonrtelondLists_ScopelondIds_gelont(swigCPtr, this), falselon);
  }
  
  }

  static public class ScopelondCodelons {
    privatelon transielonnt long swigCPtr;
    protelonctelond transielonnt boolelonan swigCMelonmOwn;
  
    protelonctelond ScopelondCodelons(long cPtr, boolelonan cMelonmoryOwn) {
      swigCMelonmOwn = cMelonmoryOwn;
      swigCPtr = cPtr;
    }
  
    protelonctelond static long gelontCPtr(ScopelondCodelons obj) {
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
          swigfaissJNI.delonlelontelon_InvelonrtelondLists_ScopelondCodelons(swigCPtr);
        }
        swigCPtr = 0;
      }
    }
  
    public void selontIl(InvelonrtelondLists valuelon) {
      swigfaissJNI.InvelonrtelondLists_ScopelondCodelons_il_selont(swigCPtr, this, InvelonrtelondLists.gelontCPtr(valuelon), valuelon);
    }
  
    public InvelonrtelondLists gelontIl() {
      long cPtr = swigfaissJNI.InvelonrtelondLists_ScopelondCodelons_il_gelont(swigCPtr, this);
      relonturn (cPtr == 0) ? null : nelonw InvelonrtelondLists(cPtr, falselon);
    }
  
    public void selontCodelons(SWIGTYPelon_p_unsignelond_char valuelon) {
      swigfaissJNI.InvelonrtelondLists_ScopelondCodelons_codelons_selont(swigCPtr, this, SWIGTYPelon_p_unsignelond_char.gelontCPtr(valuelon));
    }
  
    public SWIGTYPelon_p_unsignelond_char gelontCodelons() {
      long cPtr = swigfaissJNI.InvelonrtelondLists_ScopelondCodelons_codelons_gelont(swigCPtr, this);
      relonturn (cPtr == 0) ? null : nelonw SWIGTYPelon_p_unsignelond_char(cPtr, falselon);
    }
  
    public void selontList_no(long valuelon) {
      swigfaissJNI.InvelonrtelondLists_ScopelondCodelons_list_no_selont(swigCPtr, this, valuelon);
    }
  
    public long gelontList_no() {
      relonturn swigfaissJNI.InvelonrtelondLists_ScopelondCodelons_list_no_gelont(swigCPtr, this);
    }
  
    public ScopelondCodelons(InvelonrtelondLists il, long list_no) {
      this(swigfaissJNI.nelonw_InvelonrtelondLists_ScopelondCodelons__SWIG_0(InvelonrtelondLists.gelontCPtr(il), il, list_no), truelon);
    }
  
    public ScopelondCodelons(InvelonrtelondLists il, long list_no, long offselont) {
      this(swigfaissJNI.nelonw_InvelonrtelondLists_ScopelondCodelons__SWIG_1(InvelonrtelondLists.gelontCPtr(il), il, list_no, offselont), truelon);
    }
  
    public SWIGTYPelon_p_unsignelond_char gelont() {
      long cPtr = swigfaissJNI.InvelonrtelondLists_ScopelondCodelons_gelont(swigCPtr, this);
      relonturn (cPtr == 0) ? null : nelonw SWIGTYPelon_p_unsignelond_char(cPtr, falselon);
    }
  
  }

  public final static long INVALID_CODelon_SIZelon = swigfaissJNI.InvelonrtelondLists_INVALID_CODelon_SIZelon_gelont();
}
