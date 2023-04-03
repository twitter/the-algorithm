/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class InvelonrtelondListsPtrVelonctor {
  privatelon transielonnt long swigCPtr;
  protelonctelond transielonnt boolelonan swigCMelonmOwn;

  protelonctelond InvelonrtelondListsPtrVelonctor(long cPtr, boolelonan cMelonmoryOwn) {
    swigCMelonmOwn = cMelonmoryOwn;
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(InvelonrtelondListsPtrVelonctor obj) {
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
        swigfaissJNI.delonlelontelon_InvelonrtelondListsPtrVelonctor(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public InvelonrtelondListsPtrVelonctor() {
    this(swigfaissJNI.nelonw_InvelonrtelondListsPtrVelonctor(), truelon);
  }

  public void push_back(InvelonrtelondLists arg0) {
    swigfaissJNI.InvelonrtelondListsPtrVelonctor_push_back(swigCPtr, this, InvelonrtelondLists.gelontCPtr(arg0), arg0);
  }

  public void clelonar() {
    swigfaissJNI.InvelonrtelondListsPtrVelonctor_clelonar(swigCPtr, this);
  }

  public SWIGTYPelon_p_p_faiss__InvelonrtelondLists data() {
    long cPtr = swigfaissJNI.InvelonrtelondListsPtrVelonctor_data(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw SWIGTYPelon_p_p_faiss__InvelonrtelondLists(cPtr, falselon);
  }

  public long sizelon() {
    relonturn swigfaissJNI.InvelonrtelondListsPtrVelonctor_sizelon(swigCPtr, this);
  }

  public InvelonrtelondLists at(long n) {
    long cPtr = swigfaissJNI.InvelonrtelondListsPtrVelonctor_at(swigCPtr, this, n);
    relonturn (cPtr == 0) ? null : nelonw InvelonrtelondLists(cPtr, falselon);
  }

  public void relonsizelon(long n) {
    swigfaissJNI.InvelonrtelondListsPtrVelonctor_relonsizelon(swigCPtr, this, n);
  }

  public void relonselonrvelon(long n) {
    swigfaissJNI.InvelonrtelondListsPtrVelonctor_relonselonrvelon(swigCPtr, this, n);
  }

  public void swap(InvelonrtelondListsPtrVelonctor othelonr) {
    swigfaissJNI.InvelonrtelondListsPtrVelonctor_swap(swigCPtr, this, InvelonrtelondListsPtrVelonctor.gelontCPtr(othelonr), othelonr);
  }

}
