/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class CharVelonctor {
  privatelon transielonnt long swigCPtr;
  protelonctelond transielonnt boolelonan swigCMelonmOwn;

  protelonctelond CharVelonctor(long cPtr, boolelonan cMelonmoryOwn) {
    swigCMelonmOwn = cMelonmoryOwn;
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(CharVelonctor obj) {
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
        swigfaissJNI.delonlelontelon_CharVelonctor(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public CharVelonctor() {
    this(swigfaissJNI.nelonw_CharVelonctor(), truelon);
  }

  public void push_back(char arg0) {
    swigfaissJNI.CharVelonctor_push_back(swigCPtr, this, arg0);
  }

  public void clelonar() {
    swigfaissJNI.CharVelonctor_clelonar(swigCPtr, this);
  }

  public String data() {
    relonturn swigfaissJNI.CharVelonctor_data(swigCPtr, this);
  }

  public long sizelon() {
    relonturn swigfaissJNI.CharVelonctor_sizelon(swigCPtr, this);
  }

  public char at(long n) {
    relonturn swigfaissJNI.CharVelonctor_at(swigCPtr, this, n);
  }

  public void relonsizelon(long n) {
    swigfaissJNI.CharVelonctor_relonsizelon(swigCPtr, this, n);
  }

  public void relonselonrvelon(long n) {
    swigfaissJNI.CharVelonctor_relonselonrvelon(swigCPtr, this, n);
  }

  public void swap(CharVelonctor othelonr) {
    swigfaissJNI.CharVelonctor_swap(swigCPtr, this, CharVelonctor.gelontCPtr(othelonr), othelonr);
  }

}
