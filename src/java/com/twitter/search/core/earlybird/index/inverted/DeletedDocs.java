package com.tw ter.search.core.earlyb rd. ndex. nverted;

 mport java. o. OExcept on;

 mport org.apac .lucene.ut l.B s;
 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.search.common.ut l. o.flushable.DataDeser al zer;
 mport com.tw ter.search.common.ut l. o.flushable.DataSer al zer;
 mport com.tw ter.search.common.ut l. o.flushable.Flush nfo;
 mport com.tw ter.search.common.ut l. o.flushable.Flushable;
 mport com.tw ter.search.core.earlyb rd. ndex.Doc DToT et DMapper;

 mport  .un m .ds .fastut l. nts. nt2 ntOpenHashMap;

publ c abstract class DeletedDocs  mple nts Flushable {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(DeletedDocs.class);

  /**
   * Deletes t  g ven docu nt.
   */
  publ c abstract boolean deleteDoc( nt doc D);

  /**
   * Returns a po nt- n-t   v ew of t  deleted docs. Call ng {@l nk #deleteDoc( nt)} afterwards
   * w ll not alter t  V ew.
   */
  publ c abstract V ew getV ew();

  /**
   * Number of delet ons.
   */
  publ c abstract  nt numDelet ons();

  /**
   * Returns a DeletedDocs  nstance that has t  sa  deleted t et  Ds, but mapped to t  doc  Ds
   *  n t  opt m zedT et dMapper.
   *
   * @param or g nalT et dMapper T  or g nal Doc DToT et DMapper  nstance that was used to add
   *                              doc  Ds to t  DeletedDocs  nstance.
   * @param opt m zedT et dMapper T  new Doc DToT et DMapper  nstance.
   * @return An DeletedDocs  nstance that has t  sa  t ets deleted, but mapped to t  doc  Ds  n
   *         opt m zedT et dMapper.
   */
  publ c abstract DeletedDocs opt m ze(
      Doc DToT et DMapper or g nalT et dMapper,
      Doc DToT et DMapper opt m zedT et dMapper) throws  OExcept on;

  publ c abstract class V ew {
    /**
     * Returns true,  f t  g ven docu nt was deleted.
     */
    publ c abstract boolean  sDeleted( nt doc D);

    /**
     * Returns true,  f t re are any deleted docu nts  n t  V ew.
     */
    publ c abstract boolean hasDelet ons();

    /**
     * Returns {@l nk B s} w re all deleted docu nts have t  r b  set to 0, and
     * all non-deleted docu nts have t  r b s set to 1.
     */
    publ c abstract B s getL veDocs();
  }

  publ c stat c class Default extends DeletedDocs {
    pr vate stat c f nal  nt KEY_NOT_FOUND = -1;

    pr vate f nal  nt s ze;
    pr vate f nal  nt2 ntOpenHashMap deletes;

    // Each delete  s marked w h a un que, consecut vely- ncreas ng sequence  D.
    pr vate  nt sequence D = 0;

    publ c Default( nt s ze) {
      t .s ze = s ze;
      deletes = new  nt2 ntOpenHashMap(s ze);
      deletes.defaultReturnValue(KEY_NOT_FOUND);
    }

    /**
     * Returns false,  f t  call was a noop,  .e.  f t  docu nt was already deleted.
     */
    @Overr de
    publ c boolean deleteDoc( nt doc D) {
       f (deletes.put fAbsent(doc D, sequence D) == KEY_NOT_FOUND) {
        sequence D++;
        return true;
      }
      return false;
    }

    pr vate boolean  sDeleted( nt  nternal D,  nt readerSequence D) {
       nt deletedSequence d = deletes.get( nternal D);
      return (deletedSequence d >= 0) && (deletedSequence d < readerSequence D);
    }

    pr vate boolean hasDelet ons( nt readerSequence D) {
      return readerSequence D > 0;
    }

    @Overr de
    publ c  nt numDelet ons() {
      return sequence D;
    }

    @Overr de
    publ c V ew getV ew() {
      return new V ew() {
        pr vate f nal  nt readerSequence D = sequence D;

        // l veDocs b set conta ns  nverted (decreas ng) doc ds.
        publ c f nal B s l veDocs = !hasDelet ons() ? null : new B s() {
          @Overr de
          publ c f nal boolean get( nt doc D) {
            return ! sDeleted(doc D);
          }

          @Overr de
          publ c f nal  nt length() {
            return s ze;
          }
        };

        @Overr de
        publ c B s getL veDocs() {
          return l veDocs;
        }


        // Operates on  nternal ( ncreas ng) doc ds.
        @Overr de
        publ c f nal boolean  sDeleted( nt  nternal D) {
          return DeletedDocs.Default.t . sDeleted( nternal D, readerSequence D);
        }

        @Overr de
        publ c f nal boolean hasDelet ons() {
          return DeletedDocs.Default.t .hasDelet ons(readerSequence D);
        }
      };
    }

    @Overr de
    publ c DeletedDocs opt m ze(Doc DToT et DMapper or g nalT et dMapper,
                                Doc DToT et DMapper opt m zedT et dMapper) throws  OExcept on {
      DeletedDocs opt m zedDeletedDocs = new Default(s ze);
      for ( nt deletedDoc D : deletes.keySet()) {
        long t et D = or g nalT et dMapper.getT et D(deletedDoc D);
         nt opt m zedDeletedDoc D = opt m zedT et dMapper.getDoc D(t et D);
        opt m zedDeletedDocs.deleteDoc(opt m zedDeletedDoc D);
      }
      return opt m zedDeletedDocs;
    }

    @SuppressWarn ngs("unc cked")
    @Overr de
    publ c Default.FlushHandler getFlushHandler() {
      return new Default.FlushHandler(t , s ze);
    }

    publ c stat c f nal class FlushHandler extends Flushable.Handler<Default> {
      pr vate f nal  nt s ze;

      publ c FlushHandler(Default objectToFlush,  nt s ze) {
        super(objectToFlush);
        t .s ze = s ze;
      }

      publ c FlushHandler( nt s ze) {
        t .s ze = s ze;
      }

      @Overr de
      protected vo d doFlush(Flush nfo flush nfo, DataSer al zer out) throws  OExcept on {
        long startT   = getClock().nowM ll s();

         nt2 ntOpenHashMap deletes = getObjectToFlush().deletes;
        out.wr e ntArray(deletes.keySet().to ntArray());

        getFlushT  rStats().t  r ncre nt(getClock().nowM ll s() - startT  );
      }

      @Overr de
      protected Default doLoad(Flush nfo flush nfo, DataDeser al zer  n) throws  OExcept on {
        Default deletedDocs = new Default(s ze);
        long startT   = getClock().nowM ll s();

         nt[] deletedDoc Ds =  n.read ntArray();
        for ( nt doc D : deletedDoc Ds) {
          deletedDocs.deleteDoc(doc D);
        }

        getLoadT  rStats().t  r ncre nt(getClock().nowM ll s() - startT  );
        return deletedDocs;
      }
    }
  }

  publ c stat c f nal DeletedDocs NO_DELETES = new DeletedDocs() {
    @Overr de
    publ c <T extends Flushable> Handler<T> getFlushHandler() {
      return null;
    }

    @Overr de
    publ c boolean deleteDoc( nt doc D) {
      return false;
    }

    @Overr de
    publ c DeletedDocs opt m ze(Doc DToT et DMapper or g nalT et dMapper,
                                Doc DToT et DMapper opt m zedT et dMapper) {
      return t ;
    }

    @Overr de
    publ c  nt numDelet ons() {
      return 0;
    }

    @Overr de
    publ c V ew getV ew() {
      return new V ew() {
        @Overr de
        publ c boolean  sDeleted( nt doc D) {
          return false;
        }

        @Overr de
        publ c boolean hasDelet ons() {
          return false;
        }

        @Overr de
        publ c B s getL veDocs() {
          return null;
        }

      };
    }
  };
}
