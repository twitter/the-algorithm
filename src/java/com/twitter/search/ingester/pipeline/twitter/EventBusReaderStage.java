package com.tw ter.search. ngester.p pel ne.tw ter;

 mport java.ut l.concurrent.T  Un ;

 mport javax.nam ng.Nam ngExcept on;

 mport scala.runt  .BoxedUn ;

 mport com.google.common.annotat ons.V s bleForTest ng;
 mport com.google.common.base.Precond  ons;

 mport org.apac .commons.p pel ne.P pel ne;
 mport org.apac .commons.p pel ne.StageDr ver;
 mport org.apac .thr ft.TBase;
 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.eventbus.cl ent.EventBusSubscr ber;
 mport com.tw ter.search.common.dec der.SearchDec der;
 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search. ngester.model.Prom seConta ner;
 mport com.tw ter.search. ngester.p pel ne.ut l.P pel neUt l;
 mport com.tw ter.ut l.Awa ;
 mport com.tw ter.ut l.Funct on;
 mport com.tw ter.ut l.Future;
 mport com.tw ter.ut l.Prom se;

publ c abstract class EventBusReaderStage<T extends TBase<?, ?>> extends Tw terBaseStage
    <Vo d, Vo d> {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(EventBusReaderStage.class);

  pr vate stat c f nal  nt DEC DER_POLL_ NTERVAL_ N_SECS = 5;

  pr vate SearchCounter totalEventsCount;

  pr vate Str ng env ron nt = null;
  pr vate Str ng eventBusReaderEnabledDec derKey;

  pr vate StageDr ver stageDr ver;

  pr vate EventBusSubscr ber<T> eventBusSubscr ber = null;

  // XML conf gurat on opt ons
  pr vate Str ng eventBusSubscr ber d;
  pr vate  nt maxConcurrentEvents;
  pr vate SearchDec der searchDec der;

  protected EventBusReaderStage() {
  }

  @Overr de
  protected vo d  n Stats() {
    super. n Stats();
    totalEventsCount = SearchCounter.export(getStageNa Pref x() + "_total_events_count");
  }

  @Overr de
  protected vo d do nnerPreprocess() throws Nam ngExcept on {
    searchDec der = new SearchDec der(dec der);

     f (stageDr ver == null) {
      stageDr ver = ((P pel ne) stageContext).getStageDr ver(t );
    }

    eventBusReaderEnabledDec derKey = Str ng.format(
        getDec derKeyTemplate(),
        earlyb rdCluster.getNa ForStats(),
        env ron nt);

    P pel neUt l.feedStartObjectToStage(t );
  }

  protected abstract Prom seConta ner<BoxedUn , T> eventAndProm seToConta ner(
      T  ncom ngEvent,
      Prom se<BoxedUn > p);

  pr vate Future<BoxedUn > processEvent(T  ncom ngEvent) {
    Prom se<BoxedUn > p = new Prom se<>();
    Prom seConta ner<BoxedUn , T> prom seConta ner = eventAndProm seToConta ner( ncom ngEvent, p);
    totalEventsCount. ncre nt();
    em AndCount(prom seConta ner);
    return p;
  }

  pr vate vo d closeEventBusSubscr ber() throws Except on {
     f (eventBusSubscr ber != null) {
      Awa .result(eventBusSubscr ber.close());
      eventBusSubscr ber = null;
    }
  }

  protected abstract Class<T> getThr ftClass();

  protected abstract Str ng getDec derKeyTemplate();

  pr vate vo d startUpEventBusSubscr ber() {
    // Start read ng from eventbus  f    s null
     f (eventBusSubscr ber == null) {
      //no nspect on unc cked
      eventBusSubscr ber = w reModule.createEventBusSubscr ber(
          Funct on.func(t ::processEvent),
          getThr ftClass(),
          eventBusSubscr ber d,
          maxConcurrentEvents);

    }
    Precond  ons.c ckNotNull(eventBusSubscr ber);
  }

  /**
   * T   s only k cked off once w h a start object wh ch  s  gnored. T n   loop
   * c ck ng t  dec der.  f   turns off t n   close t  eventbus reader,
   * and  f   turns on, t n   create a new eventbus reader.
   *
   * @param obj  gnored
   */
  @Overr de
  publ c vo d  nnerProcess(Object obj) {
    boolean  nterrupted = false;

    Precond  ons.c ckNotNull("T  env ron nt  s not set.", env ron nt);

     nt prev ousEventBusReaderEnabledAva lab l y = 0;
    wh le (stageDr ver.getState() == StageDr ver.State.RUNN NG) {
       nt eventBusReaderEnabledAva lab l y =
          searchDec der.getAva lab l y(eventBusReaderEnabledDec derKey);
       f (prev ousEventBusReaderEnabledAva lab l y != eventBusReaderEnabledAva lab l y) {
        LOG. nfo("EventBusReaderStage ava lab l y dec der changed from {} to {}.",
                 prev ousEventBusReaderEnabledAva lab l y, eventBusReaderEnabledAva lab l y);

        //  f t  ava lab l y  s 0 t n d sable t  reader, ot rw se read from EventBus.
         f (eventBusReaderEnabledAva lab l y == 0) {
          try {
            closeEventBusSubscr ber();
          } catch (Except on e) {
            LOG.warn("Except on wh le clos ng eventbus subscr ber", e);
          }
        } else {
          startUpEventBusSubscr ber();
        }
      }
      prev ousEventBusReaderEnabledAva lab l y = eventBusReaderEnabledAva lab l y;

      try {
        clock.wa For(T  Un .SECONDS.toM ll s(DEC DER_POLL_ NTERVAL_ N_SECS));
      } catch ( nterruptedExcept on e) {
         nterrupted = true;
      }
    }
    LOG. nfo("StageDr ver  s not RUNN NG anymore, clos ng EventBus subscr ber");
    try {
      closeEventBusSubscr ber();
    } catch ( nterruptedExcept on e) {
       nterrupted = true;
    } catch (Except on e) {
      LOG.warn("Except on wh le clos ng eventbus subscr ber", e);
    } f nally {
       f ( nterrupted) {
        Thread.currentThread(). nterrupt();
      }
    }
  }

  // T   s needed to set t  value from XML conf g.
  publ c vo d setEventBusSubscr ber d(Str ng eventBusSubscr ber d) {
    t .eventBusSubscr ber d = eventBusSubscr ber d;
    LOG. nfo("EventBusReaderStage w h eventBusSubscr ber d: {}", eventBusSubscr ber d);
  }

  // T   s needed to set t  value from XML conf g.
  publ c vo d setEnv ron nt(Str ng env ron nt) {
    t .env ron nt = env ron nt;
    LOG. nfo(" ngester  s runn ng  n {}", env ron nt);
  }

  // T   s needed to set t  value from XML conf g.
  publ c vo d setMaxConcurrentEvents( nt maxConcurrentEvents) {
    t .maxConcurrentEvents = maxConcurrentEvents;
  }

  @V s bleForTest ng
  publ c vo d setStageDr ver(StageDr ver stageDr ver) {
    t .stageDr ver = stageDr ver;
  }
}
