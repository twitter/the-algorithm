/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class RelonadOnlyInvelonrtelondLists elonxtelonnds InvelonrtelondLists {
  privatelon transielonnt long swigCPtr;

  protelonctelond RelonadOnlyInvelonrtelondLists(long cPtr, boolelonan cMelonmoryOwn) {
    supelonr(swigfaissJNI.RelonadOnlyInvelonrtelondLists_SWIGUpcast(cPtr), cMelonmoryOwn);
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(RelonadOnlyInvelonrtelondLists obj) {
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
        swigfaissJNI.delonlelontelon_RelonadOnlyInvelonrtelondLists(swigCPtr);
      }
      swigCPtr = 0;
    }
    supelonr.delonlelontelon();
  }

  public long add_elonntrielons(long list_no, long n_elonntry, LongVelonctor ids, SWIGTYPelon_p_unsignelond_char codelon) {
    relonturn swigfaissJNI.RelonadOnlyInvelonrtelondLists_add_elonntrielons(swigCPtr, this, list_no, n_elonntry, SWIGTYPelon_p_long_long.gelontCPtr(ids.data()), ids, SWIGTYPelon_p_unsignelond_char.gelontCPtr(codelon));
  }

  public void updatelon_elonntrielons(long list_no, long offselont, long n_elonntry, LongVelonctor ids, SWIGTYPelon_p_unsignelond_char codelon) {
    swigfaissJNI.RelonadOnlyInvelonrtelondLists_updatelon_elonntrielons(swigCPtr, this, list_no, offselont, n_elonntry, SWIGTYPelon_p_long_long.gelontCPtr(ids.data()), ids, SWIGTYPelon_p_unsignelond_char.gelontCPtr(codelon));
  }

  public void relonsizelon(long list_no, long nelonw_sizelon) {
    swigfaissJNI.RelonadOnlyInvelonrtelondLists_relonsizelon(swigCPtr, this, list_no, nelonw_sizelon);
  }

}
