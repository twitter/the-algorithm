package com.tw ter.search.earlyb rd.search;

 mport java. o. OExcept on;

 mport com.google.common.base.Precond  ons;
 mport com.google.common.pr m  ves.Longs;

 mport org.apac .lucene. ndex.Nu r cDocValues;

 mport com.tw ter.common_ nternal.bloomf lter.BloomF lter;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant;
 mport com.tw ter.search.core.earlyb rd. ndex.Earlyb rd ndexSeg ntAtom cReader;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSoc alF lterType;

/**
 * F lter class used by t  SearchResultsCollector to f lter soc al t ets
 * from t  h s.
 */
publ c class Soc alF lter {
  pr vate  nterface Acceptor {
    boolean accept(long fromUserLong, byte[] user D nBytes);
  }

  pr vate Nu r cDocValues fromUser D;
  pr vate f nal Acceptor acceptor;
  pr vate f nal long searc r d;
  pr vate f nal BloomF lter trustedF lter;
  pr vate f nal BloomF lter followF lter;

  pr vate class FollowsAcceptor  mple nts Acceptor {
    @Overr de
    publ c boolean accept(long fromUserLong, byte[] user d nBytes) {
      return followF lter.conta ns(user d nBytes);
    }
  }

  pr vate class TrustedAcceptor  mple nts Acceptor {
    @Overr de
    publ c boolean accept(long fromUserLong, byte[] user d nBytes) {
      return trustedF lter.conta ns(user d nBytes);
    }
  }

  pr vate class AllAcceptor  mple nts Acceptor {
    @Overr de
    publ c boolean accept(long fromUserLong, byte[] user d nBytes) {
      return trustedF lter.conta ns(user d nBytes)
          || followF lter.conta ns(user d nBytes)
          || fromUserLong == searc r d;
    }
  }

  publ c Soc alF lter(
      Thr ftSoc alF lterType soc alF lterType,
      f nal long searc r d,
      f nal byte[] trustedF lter,
      f nal byte[] followF lter) throws  OExcept on {
    Precond  ons.c ckNotNull(soc alF lterType);
    Precond  ons.c ckNotNull(trustedF lter);
    Precond  ons.c ckNotNull(followF lter);
    t .searc r d = searc r d;
    t .trustedF lter = new BloomF lter(trustedF lter);
    t .followF lter = new BloomF lter(followF lter);


    sw ch (soc alF lterType) {
      case FOLLOWS:
        t .acceptor = new FollowsAcceptor();
        break;
      case TRUSTED:
        t .acceptor = new TrustedAcceptor();
        break;
      case ALL:
        t .acceptor = new AllAcceptor();
        break;
      default:
        throw new UnsupportedOperat onExcept on(" nval d soc al f lter type passed");
    }
  }

  publ c vo d startSeg nt(Earlyb rd ndexSeg ntAtom cReader  ndexReader) throws  OExcept on {
    fromUser D =
         ndexReader.getNu r cDocValues(Earlyb rdF eldConstant.FROM_USER_ D_CSF.getF eldNa ());
  }

  /**
   * Determ nes  f t  g ven doc  D should be accepted.
   */
  publ c boolean accept( nt  nternalDoc D) throws  OExcept on {
     f (!fromUser D.advanceExact( nternalDoc D)) {
      return false;
    }

    long fromUserLong = fromUser D.longValue();
    byte[] user D nBytes = Longs.toByteArray(fromUserLong);
    return acceptor.accept(fromUserLong, user D nBytes);
  }
}
