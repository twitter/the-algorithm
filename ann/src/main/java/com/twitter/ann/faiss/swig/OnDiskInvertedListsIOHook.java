/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class OnDiskInvelonrtelondListsIOHook {
  privatelon transielonnt long swigCPtr;
  protelonctelond transielonnt boolelonan swigCMelonmOwn;

  protelonctelond OnDiskInvelonrtelondListsIOHook(long cPtr, boolelonan cMelonmoryOwn) {
    swigCMelonmOwn = cMelonmoryOwn;
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(OnDiskInvelonrtelondListsIOHook obj) {
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
        swigfaissJNI.delonlelontelon_OnDiskInvelonrtelondListsIOHook(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public OnDiskInvelonrtelondListsIOHook() {
    this(swigfaissJNI.nelonw_OnDiskInvelonrtelondListsIOHook(), truelon);
  }

  public void writelon(InvelonrtelondLists ils, SWIGTYPelon_p_IOWritelonr f) {
    swigfaissJNI.OnDiskInvelonrtelondListsIOHook_writelon(swigCPtr, this, InvelonrtelondLists.gelontCPtr(ils), ils, SWIGTYPelon_p_IOWritelonr.gelontCPtr(f));
  }

  public InvelonrtelondLists relonad(SWIGTYPelon_p_IORelonadelonr f, int io_flags) {
    long cPtr = swigfaissJNI.OnDiskInvelonrtelondListsIOHook_relonad(swigCPtr, this, SWIGTYPelon_p_IORelonadelonr.gelontCPtr(f), io_flags);
    relonturn (cPtr == 0) ? null : nelonw InvelonrtelondLists(cPtr, falselon);
  }

  public InvelonrtelondLists relonad_ArrayInvelonrtelondLists(SWIGTYPelon_p_IORelonadelonr f, int io_flags, long nlist, long codelon_sizelon, Uint64Velonctor sizelons) {
    long cPtr = swigfaissJNI.OnDiskInvelonrtelondListsIOHook_relonad_ArrayInvelonrtelondLists(swigCPtr, this, SWIGTYPelon_p_IORelonadelonr.gelontCPtr(f), io_flags, nlist, codelon_sizelon, Uint64Velonctor.gelontCPtr(sizelons), sizelons);
    relonturn (cPtr == 0) ? null : nelonw InvelonrtelondLists(cPtr, falselon);
  }

}
