package com.tw ter.search.earlyb rd.docu nt;

 mport java. o. OExcept on;
 mport javax.annotat on.Nullable;

 mport org.apac .commons.codec.b nary.Base64;
 mport org.apac .lucene.docu nt.Docu nt;
 mport org.apac .lucene.docu nt.F eld;
 mport org.apac .lucene.docu nt.F eldType;
 mport org.apac .lucene. ndex. ndexableF eld;
 mport org.apac .thr ft.TBase;
 mport org.apac .thr ft.TExcept on;
 mport org.apac .thr ft.TSer al zer;
 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.common.ut l.text.Om NormTextF eld;
 mport com.tw ter.search.earlyb rd.except on.Cr  calExcept onHandler;

/**
 * Factory that constructs a Lucene docu nt from a thr ft object stored  n T format.
 *
 * @param <T> Thr ftStatus or Thr ft ndex ngEvent, to be converted to a Lucene Docu nt.
 */
publ c abstract class Docu ntFactory<T extends TBase<T, ?>> {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(Docu ntFactory.class);
  pr vate stat c f nal  nt MAX_ALLOWED_ NVAL D_DOCUMENTS = 100;

  pr vate stat c f nal SearchCounter  NVAL D_DOCUMENTS_COUNTER =
      SearchCounter.export(" nval d_docu nts");

  pr vate f nal Cr  calExcept onHandler cr  calExcept onHandler;

  publ c Docu ntFactory(Cr  calExcept onHandler cr  calExcept onHandler) {
    t .cr  calExcept onHandler = cr  calExcept onHandler;
  }

  /**
   * G ven t  thr ft representat on of a t et, returns t  assoc ated t et d.
   */
  publ c abstract long getStatus d(T thr ftObject);

  /**
   * G ven t  thr ft representat on of a t et, returns a Lucene Docu nt w h all t  f elds
   * that need to be  ndexed.
   */
  @Nullable
  publ c f nal Docu nt newDocu nt(T thr ftObject) {
    try {
      return  nnerNewDocu nt(thr ftObject);
    } catch (Except on e) {
      Str ng status d = "Not ava lable";
       f (thr ftObject != null) {
        try {
          status d = Long.toStr ng(getStatus d(thr ftObject));
        } catch (Except on ex) {
          LOG.error("Unable to get t et  d for docu nt", ex);
          status d = "Not parsable";
        }
      }
      LOG.error("Unexpected except on wh le  ndex ng. Status  d: " + status d, e);

       f (thr ftObject != null) {
        // Log t  status  n base64 for debugg ng
        try {
          LOG.warn("Bad Thr ftStatus.  d: " + status d + " base 64: "
              + Base64.encodeBase64Str ng(new TSer al zer().ser al ze(thr ftObject)));
        } catch (TExcept on e1) {
          //  gnored s nce t   s logg ng for debugg ng.
        }
      }
       NVAL D_DOCUMENTS_COUNTER. ncre nt();
       f ( NVAL D_DOCUMENTS_COUNTER.get() > MAX_ALLOWED_ NVAL D_DOCUMENTS) {
        cr  calExcept onHandler.handle(t , e);
      }
      return new Docu nt();
    }
  }

  /**
   * G ven t  thr ft representat on of a t et, returns a Lucene Docu nt w h all t  f elds
   * that need to be  ndexed.
   *
   * Return null  f t  g ven thr ft object  s  nval d.
   *
   * @throws  OExcept on  f t re are problems read ng t   nput of produc ng t  output. Except on
   *          s handled  n {@l nk #newDocu nt(TBase)}.
   */
  @Nullable
  protected abstract Docu nt  nnerNewDocu nt(T thr ftObject) throws  OExcept on;

  //  lper  thods that prevent us from add ng null f elds to t  lucene  ndex
  protected vo d addF eld(Docu nt docu nt,  ndexableF eld f eld) {
     f (f eld != null) {
      docu nt.add(f eld);
    }
  }

  protected F eld newF eld(Str ng data, Str ng f eldNa ) {
    return newF eld(data, f eldNa , Om NormTextF eld.TYPE_NOT_STORED);
  }

  protected F eld newF eld(Str ng data, Str ng f eldNa , F eldType f eldType) {
     f (data != null) {
      return new F eld(f eldNa , data, f eldType);
    }
    return null;
  }
}
