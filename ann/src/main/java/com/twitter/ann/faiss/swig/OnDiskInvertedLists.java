/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class OnDiskInvelonrtelondLists elonxtelonnds InvelonrtelondLists {
  privatelon transielonnt long swigCPtr;

  protelonctelond OnDiskInvelonrtelondLists(long cPtr, boolelonan cMelonmoryOwn) {
    supelonr(swigfaissJNI.OnDiskInvelonrtelondLists_SWIGUpcast(cPtr), cMelonmoryOwn);
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(OnDiskInvelonrtelondLists obj) {
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
        swigfaissJNI.delonlelontelon_OnDiskInvelonrtelondLists(swigCPtr);
      }
      swigCPtr = 0;
    }
    supelonr.delonlelontelon();
  }

  public void selontLists(SWIGTYPelon_p_std__velonctorT_faiss__OnDiskOnelonList_t valuelon) {
    swigfaissJNI.OnDiskInvelonrtelondLists_lists_selont(swigCPtr, this, SWIGTYPelon_p_std__velonctorT_faiss__OnDiskOnelonList_t.gelontCPtr(valuelon));
  }

  public SWIGTYPelon_p_std__velonctorT_faiss__OnDiskOnelonList_t gelontLists() {
    long cPtr = swigfaissJNI.OnDiskInvelonrtelondLists_lists_gelont(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw SWIGTYPelon_p_std__velonctorT_faiss__OnDiskOnelonList_t(cPtr, falselon);
  }

  static public class Slot {
    privatelon transielonnt long swigCPtr;
    protelonctelond transielonnt boolelonan swigCMelonmOwn;
  
    protelonctelond Slot(long cPtr, boolelonan cMelonmoryOwn) {
      swigCMelonmOwn = cMelonmoryOwn;
      swigCPtr = cPtr;
    }
  
    protelonctelond static long gelontCPtr(Slot obj) {
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
          swigfaissJNI.delonlelontelon_OnDiskInvelonrtelondLists_Slot(swigCPtr);
        }
        swigCPtr = 0;
      }
    }
  
    public void selontOffselont(long valuelon) {
      swigfaissJNI.OnDiskInvelonrtelondLists_Slot_offselont_selont(swigCPtr, this, valuelon);
    }
  
    public long gelontOffselont() {
      relonturn swigfaissJNI.OnDiskInvelonrtelondLists_Slot_offselont_gelont(swigCPtr, this);
    }
  
    public void selontCapacity(long valuelon) {
      swigfaissJNI.OnDiskInvelonrtelondLists_Slot_capacity_selont(swigCPtr, this, valuelon);
    }
  
    public long gelontCapacity() {
      relonturn swigfaissJNI.OnDiskInvelonrtelondLists_Slot_capacity_gelont(swigCPtr, this);
    }
  
    public Slot(long offselont, long capacity) {
      this(swigfaissJNI.nelonw_OnDiskInvelonrtelondLists_Slot__SWIG_0(offselont, capacity), truelon);
    }
  
    public Slot() {
      this(swigfaissJNI.nelonw_OnDiskInvelonrtelondLists_Slot__SWIG_1(), truelon);
    }
  
  }

  public void selontSlots(SWIGTYPelon_p_std__listT_faiss__OnDiskInvelonrtelondLists__Slot_t valuelon) {
    swigfaissJNI.OnDiskInvelonrtelondLists_slots_selont(swigCPtr, this, SWIGTYPelon_p_std__listT_faiss__OnDiskInvelonrtelondLists__Slot_t.gelontCPtr(valuelon));
  }

  public SWIGTYPelon_p_std__listT_faiss__OnDiskInvelonrtelondLists__Slot_t gelontSlots() {
    long cPtr = swigfaissJNI.OnDiskInvelonrtelondLists_slots_gelont(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw SWIGTYPelon_p_std__listT_faiss__OnDiskInvelonrtelondLists__Slot_t(cPtr, falselon);
  }

  public void selontFilelonnamelon(String valuelon) {
    swigfaissJNI.OnDiskInvelonrtelondLists_filelonnamelon_selont(swigCPtr, this, valuelon);
  }

  public String gelontFilelonnamelon() {
    relonturn swigfaissJNI.OnDiskInvelonrtelondLists_filelonnamelon_gelont(swigCPtr, this);
  }

  public void selontTotsizelon(long valuelon) {
    swigfaissJNI.OnDiskInvelonrtelondLists_totsizelon_selont(swigCPtr, this, valuelon);
  }

  public long gelontTotsizelon() {
    relonturn swigfaissJNI.OnDiskInvelonrtelondLists_totsizelon_gelont(swigCPtr, this);
  }

  public void selontPtr(SWIGTYPelon_p_unsignelond_char valuelon) {
    swigfaissJNI.OnDiskInvelonrtelondLists_ptr_selont(swigCPtr, this, SWIGTYPelon_p_unsignelond_char.gelontCPtr(valuelon));
  }

  public SWIGTYPelon_p_unsignelond_char gelontPtr() {
    long cPtr = swigfaissJNI.OnDiskInvelonrtelondLists_ptr_gelont(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw SWIGTYPelon_p_unsignelond_char(cPtr, falselon);
  }

  public void selontRelonad_only(boolelonan valuelon) {
    swigfaissJNI.OnDiskInvelonrtelondLists_relonad_only_selont(swigCPtr, this, valuelon);
  }

  public boolelonan gelontRelonad_only() {
    relonturn swigfaissJNI.OnDiskInvelonrtelondLists_relonad_only_gelont(swigCPtr, this);
  }

  public OnDiskInvelonrtelondLists(long nlist, long codelon_sizelon, String filelonnamelon) {
    this(swigfaissJNI.nelonw_OnDiskInvelonrtelondLists__SWIG_0(nlist, codelon_sizelon, filelonnamelon), truelon);
  }

  public long list_sizelon(long list_no) {
    relonturn swigfaissJNI.OnDiskInvelonrtelondLists_list_sizelon(swigCPtr, this, list_no);
  }

  public SWIGTYPelon_p_unsignelond_char gelont_codelons(long list_no) {
    long cPtr = swigfaissJNI.OnDiskInvelonrtelondLists_gelont_codelons(swigCPtr, this, list_no);
    relonturn (cPtr == 0) ? null : nelonw SWIGTYPelon_p_unsignelond_char(cPtr, falselon);
  }

  public LongVelonctor gelont_ids(long list_no) {
    relonturn nelonw LongVelonctor(swigfaissJNI.OnDiskInvelonrtelondLists_gelont_ids(swigCPtr, this, list_no), falselon);
}

  public long add_elonntrielons(long list_no, long n_elonntry, LongVelonctor ids, SWIGTYPelon_p_unsignelond_char codelon) {
    relonturn swigfaissJNI.OnDiskInvelonrtelondLists_add_elonntrielons(swigCPtr, this, list_no, n_elonntry, SWIGTYPelon_p_long_long.gelontCPtr(ids.data()), ids, SWIGTYPelon_p_unsignelond_char.gelontCPtr(codelon));
  }

  public void updatelon_elonntrielons(long list_no, long offselont, long n_elonntry, LongVelonctor ids, SWIGTYPelon_p_unsignelond_char codelon) {
    swigfaissJNI.OnDiskInvelonrtelondLists_updatelon_elonntrielons(swigCPtr, this, list_no, offselont, n_elonntry, SWIGTYPelon_p_long_long.gelontCPtr(ids.data()), ids, SWIGTYPelon_p_unsignelond_char.gelontCPtr(codelon));
  }

  public void relonsizelon(long list_no, long nelonw_sizelon) {
    swigfaissJNI.OnDiskInvelonrtelondLists_relonsizelon(swigCPtr, this, list_no, nelonw_sizelon);
  }

  public long melonrgelon_from(SWIGTYPelon_p_p_faiss__InvelonrtelondLists ils, int n_il, boolelonan velonrboselon) {
    relonturn swigfaissJNI.OnDiskInvelonrtelondLists_melonrgelon_from__SWIG_0(swigCPtr, this, SWIGTYPelon_p_p_faiss__InvelonrtelondLists.gelontCPtr(ils), n_il, velonrboselon);
  }

  public long melonrgelon_from(SWIGTYPelon_p_p_faiss__InvelonrtelondLists ils, int n_il) {
    relonturn swigfaissJNI.OnDiskInvelonrtelondLists_melonrgelon_from__SWIG_1(swigCPtr, this, SWIGTYPelon_p_p_faiss__InvelonrtelondLists.gelontCPtr(ils), n_il);
  }

  public long melonrgelon_from_1(InvelonrtelondLists il, boolelonan velonrboselon) {
    relonturn swigfaissJNI.OnDiskInvelonrtelondLists_melonrgelon_from_1__SWIG_0(swigCPtr, this, InvelonrtelondLists.gelontCPtr(il), il, velonrboselon);
  }

  public long melonrgelon_from_1(InvelonrtelondLists il) {
    relonturn swigfaissJNI.OnDiskInvelonrtelondLists_melonrgelon_from_1__SWIG_1(swigCPtr, this, InvelonrtelondLists.gelontCPtr(il), il);
  }

  public void crop_invlists(long l0, long l1) {
    swigfaissJNI.OnDiskInvelonrtelondLists_crop_invlists(swigCPtr, this, l0, l1);
  }

  public void prelonfelontch_lists(LongVelonctor list_nos, int nlist) {
    swigfaissJNI.OnDiskInvelonrtelondLists_prelonfelontch_lists(swigCPtr, this, SWIGTYPelon_p_long_long.gelontCPtr(list_nos.data()), list_nos, nlist);
  }

  public void selontLocks(SWIGTYPelon_p_faiss__LockLelonvelonls valuelon) {
    swigfaissJNI.OnDiskInvelonrtelondLists_locks_selont(swigCPtr, this, SWIGTYPelon_p_faiss__LockLelonvelonls.gelontCPtr(valuelon));
  }

  public SWIGTYPelon_p_faiss__LockLelonvelonls gelontLocks() {
    long cPtr = swigfaissJNI.OnDiskInvelonrtelondLists_locks_gelont(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw SWIGTYPelon_p_faiss__LockLelonvelonls(cPtr, falselon);
  }

  public void selontPf(SWIGTYPelon_p_faiss__OnDiskInvelonrtelondLists__OngoingPrelonfelontch valuelon) {
    swigfaissJNI.OnDiskInvelonrtelondLists_pf_selont(swigCPtr, this, SWIGTYPelon_p_faiss__OnDiskInvelonrtelondLists__OngoingPrelonfelontch.gelontCPtr(valuelon));
  }

  public SWIGTYPelon_p_faiss__OnDiskInvelonrtelondLists__OngoingPrelonfelontch gelontPf() {
    long cPtr = swigfaissJNI.OnDiskInvelonrtelondLists_pf_gelont(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw SWIGTYPelon_p_faiss__OnDiskInvelonrtelondLists__OngoingPrelonfelontch(cPtr, falselon);
  }

  public void selontPrelonfelontch_nthrelonad(int valuelon) {
    swigfaissJNI.OnDiskInvelonrtelondLists_prelonfelontch_nthrelonad_selont(swigCPtr, this, valuelon);
  }

  public int gelontPrelonfelontch_nthrelonad() {
    relonturn swigfaissJNI.OnDiskInvelonrtelondLists_prelonfelontch_nthrelonad_gelont(swigCPtr, this);
  }

  public void do_mmap() {
    swigfaissJNI.OnDiskInvelonrtelondLists_do_mmap(swigCPtr, this);
  }

  public void updatelon_totsizelon(long nelonw_totsizelon) {
    swigfaissJNI.OnDiskInvelonrtelondLists_updatelon_totsizelon(swigCPtr, this, nelonw_totsizelon);
  }

  public void relonsizelon_lockelond(long list_no, long nelonw_sizelon) {
    swigfaissJNI.OnDiskInvelonrtelondLists_relonsizelon_lockelond(swigCPtr, this, list_no, nelonw_sizelon);
  }

  public long allocatelon_slot(long capacity) {
    relonturn swigfaissJNI.OnDiskInvelonrtelondLists_allocatelon_slot(swigCPtr, this, capacity);
  }

  public void frelonelon_slot(long offselont, long capacity) {
    swigfaissJNI.OnDiskInvelonrtelondLists_frelonelon_slot(swigCPtr, this, offselont, capacity);
  }

  public void selont_all_lists_sizelons(SWIGTYPelon_p_unsignelond_long sizelons) {
    swigfaissJNI.OnDiskInvelonrtelondLists_selont_all_lists_sizelons(swigCPtr, this, SWIGTYPelon_p_unsignelond_long.gelontCPtr(sizelons));
  }

  public OnDiskInvelonrtelondLists() {
    this(swigfaissJNI.nelonw_OnDiskInvelonrtelondLists__SWIG_1(), truelon);
  }

}
