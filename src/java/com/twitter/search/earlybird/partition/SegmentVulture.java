package com.tw ter.search.earlyb rd.part  on;

 mport java. o.F le;
 mport java. o. OExcept on;
 mport java.ut l.L st;
 mport java.ut l.Set;
 mport java.ut l.SortedSet;
 mport java.ut l.TreeSet;

 mport javax.annotat on.Nonnull;

 mport com.google.common.annotat ons.V s bleForTest ng;
 mport com.google.common.base.Precond  ons;
 mport com.google.common.collect.Sets;

 mport org.apac .commons. o.F leUt ls;
 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.search.common.part  on ng.base.Seg nt;
 mport com.tw ter.search.common.sc ma.earlyb rd.FlushVers on;
 mport com.tw ter.search.earlyb rd.arch ve.Arch veSearchPart  onManager;
 mport com.tw ter.search.earlyb rd.arch ve.Arch veT  Sl cer;
 mport com.tw ter.search.earlyb rd.arch ve.Arch veT  Sl cer.Arch veT  Sl ce;
 mport com.tw ter.search.earlyb rd.common.conf g.Earlyb rdConf g;
 mport com.tw ter.search.earlyb rd.factory.Earlyb rd ndexConf gUt l;

/**
 * T  class removes older flush vers on seg nts.
 * Cons der ng that   almost never  ncrease status flush vers ons, old statuses are not cleaned up
 * automat cally.
 */
publ c f nal class Seg ntVulture {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(Seg ntVulture.class);
  @V s bleForTest ng // Not f nal for test ng.
  protected stat c  nt num ndexFlushVers onsToKeep =
      Earlyb rdConf g.get nt("number_of_flush_vers ons_to_keep", 2);

  pr vate Seg ntVulture() {
    // t  never gets called
  }

  /**
   * Delete old bu ld generat ons, keep currentGenerat on.
   */
  @V s bleForTest ng
  stat c vo d removeOldBu ldGenerat ons(Str ng rootD rPath, Str ng currentGenerat on) {
    F le rootD r = new F le(rootD rPath);

     f (!rootD r.ex sts() || !rootD r. sD rectory()) {
      LOG.error("Root d rectory  s  nval d: " + rootD rPath);
      return;
    }

    F le[] bu ldGenerat ons = rootD r.l stF les();

    for (F le generat on : bu ldGenerat ons) {
       f (generat on.getNa ().equals(currentGenerat on)) {
        LOG. nfo("Sk pp ng current generat on: " + generat on.getAbsoluteF le());
        cont nue;
      }

      try {
        F leUt ls.deleteD rectory(generat on);
        LOG. nfo("Deleted old bu ld generat on: " + generat on.getAbsolutePath());
      } catch ( OExcept on e) {
        LOG.error("Fa led to delete old bu ld generat on at: " + generat on.getAbsolutePath(), e);
      }
    }
    LOG. nfo("Successfully deleted all old generat ons");
  }

  /**
   * Delete all t  t  sl ce data outs de t  serv ng range.
   */
  @V s bleForTest ng
  stat c vo d removeArch veT  sl ceOuts deServ ngRange(Part  onConf g part  onConf g,
      Arch veT  Sl cer t  Sl cer, Seg ntSyncConf g seg ntSyncConf g) {
    try {
      long serv ngStartT  sl ce d = Long.MAX_VALUE;
      long serv ngEndT  sl ce d = 0;
       nt part  on D = part  onConf g.get ndex ngHashPart  on D();
      L st<Arch veT  Sl ce> t  Sl ceL st = t  Sl cer.getT  Sl ces nT erRange();
      for (Arch veT  Sl ce t  Sl ce : t  Sl ceL st) {
         f (t  Sl ce.getM nStatus D(part  on D) < serv ngStartT  sl ce d) {
          serv ngStartT  sl ce d = t  Sl ce.getM nStatus D(part  on D);
        }
         f (t  Sl ce.getMaxStatus D(part  on D) > serv ngEndT  sl ce d) {
          serv ngEndT  sl ce d = t  Sl ce.getMaxStatus D(part  on D);
        }
      }
      LOG. nfo("Got t  serv ng range: [" + serv ngStartT  sl ce d + ", "
          + serv ngEndT  sl ce d + "], " + "[" + part  onConf g.getT erStartDate() + ", "
          + part  onConf g.getT erEndDate() + ") for t er: " + part  onConf g.getT erNa ());

      // T  t er conf gurat on does not have val d serv ng range: do not do anyth ng.
       f (serv ngEndT  sl ce d <= serv ngStartT  sl ce d) {
        LOG.error(" nval d serv ng range [" + part  onConf g.getT erStartDate() + ", "
            + part  onConf g.getT erEndDate() + "] for t er: " + part  onConf g.getT erNa ());
        return;
      }

       nt numDeleted = 0;
      F le[] seg nts = getSeg ntsOnRootD r(seg ntSyncConf g);
      for (F le seg nt : seg nts) {
        Str ng seg ntNa  = Seg nt nfo.getSeg ntNa FromFlus dD r(seg nt.getNa ());
         f (seg ntNa  == null) {
          LOG.error(" nval d d rectory for seg nts: " + seg nt.getAbsolutePath());
          cont nue;
        }
        long t  sl ce d = Seg nt.getT  Sl ce dFromNa (seg ntNa );
         f (t  sl ce d < 0) {
          LOG.error("Unknown d r/f le found: " + seg nt.getAbsolutePath());
          cont nue;
        }

         f (t  sl ce d < serv ngStartT  sl ce d || t  sl ce d > serv ngEndT  sl ce d) {
          LOG. nfo(seg nt.getAbsolutePath() + " w ll be deleted for outs de serv ng Range["
              + part  onConf g.getT erStartDate() + ", " + part  onConf g.getT erEndDate() + ")");
           f (deleteSeg nt(seg nt)) {
            numDeleted++;
          }
        }
      }
      LOG. nfo("Deleted " + numDeleted + " seg nts out of " + seg nts.length + " seg nts");
    } catch ( OExcept on e) {
      LOG.error("Can not t  sl ce based on t  docu nt data: ", e);
      throw new Runt  Except on(e);
    }
  }

  /**
   * Deleted seg nts from ot r part  ons. W n boxes are moved bet en
   * part  ons, seg nts from ot r part  ons may stay,   w ll have to
   * delete t m.
   */
  @V s bleForTest ng
  stat c vo d remove ndexesFromOt rPart  ons( nt  Part  on,  nt numPart  ons,
        Seg ntSyncConf g seg ntSyncConf g) {
    F le[] seg nts = getSeg ntsOnRootD r(seg ntSyncConf g);
     nt numDeleted = 0;
    for (F le seg nt : seg nts) {
       nt seg ntNumPart  ons = Seg nt.numPart  onsFromNa (seg nt.getNa ());
       nt seg ntPart  on = Seg nt.getPart  onFromNa (seg nt.getNa ());

       f (seg ntNumPart  ons < 0 || seg ntPart  on < 0) { // Not a seg nt f le,  gnor ng
        LOG. nfo("Unknown d r/f le found: " + seg nt.getAbsolutePath());
        cont nue;
      }

       f (seg ntNumPart  ons != numPart  ons || seg ntPart  on !=  Part  on) {
         f (deleteSeg nt(seg nt)) {
          numDeleted++;
        }
      }
    }
    LOG. nfo("Deleted " + numDeleted + " seg nts out of " + seg nts.length + " seg nts");
  }

  /**
   * Delete flus d seg nts of older flush vers ons.
   */
  @V s bleForTest ng
  stat c vo d removeOldFlushVers on ndexes( nt currentFlushVers on,
                                           Seg ntSyncConf g seg ntSyncConf g) {
    SortedSet< nteger>  ndexFlushVers ons =
        l stFlushVers ons(seg ntSyncConf g, currentFlushVers on);

     f ( ndexFlushVers ons == null
        ||  ndexFlushVers ons.s ze() <= num ndexFlushVers onsToKeep) {
      return;
    }

    Set<Str ng> suff xesToKeep = Sets.newHashSetW hExpectedS ze(num ndexFlushVers onsToKeep);
     nt flushVers onsToKeep = num ndexFlushVers onsToKeep;
    wh le (flushVers onsToKeep > 0 && ! ndexFlushVers ons. sEmpty()) {
       nteger oldestFlushVers on =  ndexFlushVers ons.last();
      Str ng flushF leExtens on = FlushVers on.getVers onF leExtens on(oldestFlushVers on);
       f (flushF leExtens on != null) {
        suff xesToKeep.add(flushF leExtens on);
        flushVers onsToKeep--;
      } else {
        LOG.warn("Found unknown flush vers ons: " + oldestFlushVers on
            + " Seg nts w h t  flush vers on w ll be deleted to recover d sk space.");
      }
       ndexFlushVers ons.remove(oldestFlushVers on);
    }

    Str ng seg ntSyncRootD r = seg ntSyncConf g.getLocalSeg ntSyncRootD r();
    F le d r = new F le(seg ntSyncRootD r);
    F le[] seg nts = d r.l stF les();

    for (F le seg nt : seg nts) {
      boolean keepSeg nt = false;
      for (Str ng suff x : suff xesToKeep) {
         f (seg nt.getNa ().endsW h(suff x)) {
          keepSeg nt = true;
          break;
        }
      }
       f (!keepSeg nt) {
        try {
          F leUt ls.deleteD rectory(seg nt);
          LOG. nfo("Deleted old flus d seg nt: " + seg nt.getAbsolutePath());
        } catch ( OExcept on e) {
          LOG.error("Fa led to delete old flus d seg nt.", e);
        }
      }
    }
  }

  pr vate stat c F le[] getSeg ntsOnRootD r(Seg ntSyncConf g seg ntSyncConf g) {
    Str ng seg ntSyncRootD r = seg ntSyncConf g.getLocalSeg ntSyncRootD r();
    F le d r = new F le(seg ntSyncRootD r);
    F le[] seg nts = d r.l stF les();
     f (seg nts == null) {
      return new F le[0];
    } else {
      return seg nts;
    }
  }

  pr vate stat c boolean deleteSeg nt(F le seg nt) {
    try {
      F leUt ls.deleteD rectory(seg nt);
      LOG. nfo("Deleted seg nt from ot r part  on: " + seg nt.getAbsolutePath());
      return true;
    } catch ( OExcept on e) {
      LOG.error("Fa led to delete seg nt from ot r part  on.", e);
      return false;
    }
  }

  // Returns FlushVers ons found on d sk.
  // Current FlushVers on  s always added  nto t  l st, even  f seg nts are not found on d sk,
  // because t y may not have appeared yet.
  @Nonnull
  @V s bleForTest ng
  stat c SortedSet< nteger> l stFlushVers ons(Seg ntSyncConf g sync,  nt currentFlushVers on) {
    TreeSet< nteger> flushVers ons = Sets.newTreeSet();

    // Always add current flush vers on.
    //    s poss ble that on startup w n t   s run, t  current flush vers on
    // seg nts have not appeared yet.
    flushVers ons.add(currentFlushVers on);

    Str ng seg ntSyncRootD r = sync.getLocalSeg ntSyncRootD r();
    F le d r = new F le(seg ntSyncRootD r);
     f (!d r.ex sts()) {
      LOG. nfo("seg ntSyncRootD r [" + seg ntSyncRootD r
          + "] does not ex st");
      return flushVers ons;
    }
     f (!d r. sD rectory()) {
      LOG.error("seg ntSyncRootD r [" + seg ntSyncRootD r
          + "] does not po nt to a d rectory");
      return flushVers ons;
    }
     f (!d r.canRead()) {
      LOG.error("No perm ss on to read from seg ntSyncRootD r ["
          + seg ntSyncRootD r + "]");
      return flushVers ons;
    }
     f (!d r.canWr e()) {
      LOG.error("No perm ss on to wr e to seg ntSyncRootD r ["
          + seg ntSyncRootD r + "]");
      return flushVers ons;
    }

    F le[] seg nts = d r.l stF les();
    for (F le seg nt : seg nts) {
      Str ng na  = seg nt.getNa ();
       f (!na .conta ns(FlushVers on.DEL M TER)) {
        // T   s a not a seg nt w h a FlushVers on, sk p.
        LOG. nfo("Found seg nt d rectory w hout a flush vers on: " + na );
        cont nue;
      }
      Str ng[] na Spl s = na .spl (FlushVers on.DEL M TER);
       f (na Spl s.length != 2) {
        LOG.warn("Found seg nt w h bad na : " + seg nt.getAbsolutePath());
        cont nue;
      }

      // Second half conta ns flush vers on
      try {
         nt flushVers on =  nteger.parse nt(na Spl s[1]);
        flushVers ons.add(flushVers on);
      } catch (NumberFormatExcept on e) {
        LOG.warn("Bad flush vers on number  n seg nt na : " + seg nt.getAbsolutePath());
      }
    }
    return flushVers ons;
  }

  /**
   * Removes old seg nts  n t  current bu ld gen.
   */
  @V s bleForTest ng
  stat c vo d removeOldSeg nts(Seg ntSyncConf g sync) {
     f (!sync.getScrubGen(). sPresent()) {
      return;
    }

    F le currentScrubGenSeg ntD r = new F le(sync.getLocalSeg ntSyncRootD r());

    // T  unscrubbed seg nt root d rectory, used for rebu lds and for seg nts created before
    //    ntroduced scrub gens. T  getLocalSeg ntSyncRootD r should be so th ng l ke:
    // $unscrubbedSeg ntD r/scrubbed/$scrub_gen/,
    // get unscrubbedSeg ntD r from str ng na   re  n case scrubbed d r does not ex st yet
    F le unscrubbedSeg ntD r = new F le(sync.getLocalSeg ntSyncRootD r().spl ("scrubbed")[0]);
     f (!unscrubbedSeg ntD r.ex sts()) {
      // For a new host that swapped  n,   m ght not have flus d_seg nt d r yet.
      // return d rectly  n that case.
      LOG. nfo(unscrubbedSeg ntD r.getAbsoluteF le() + "does not ex st, noth ng to remove.");
      return;
    }
    Precond  ons.c ckArgu nt(unscrubbedSeg ntD r.ex sts());
    for (F le f le : unscrubbedSeg ntD r.l stF les()) {
       f (f le.getNa ().matc s("scrubbed")) {
        cont nue;
      }
      try {
        LOG. nfo("Delet ng old unscrubbed seg nt: " + f le.getAbsolutePath());
        F leUt ls.deleteD rectory(f le);
      } catch ( OExcept on e) {
        LOG.error("Fa led to delete d rectory: " + f le.getPath(), e);
      }
    }

    // Delete all seg nts from prev ous scrub generat ons.
    F le allScrubbedSeg ntsD r = currentScrubGenSeg ntD r.getParentF le();
     f (allScrubbedSeg ntsD r.ex sts()) {
      for (F le f le : allScrubbedSeg ntsD r.l stF les()) {
         f (f le.getPath().equals(currentScrubGenSeg ntD r.getPath())) {
          cont nue;
        }
        try {
          LOG. nfo("Delet ng old scrubbed seg nt: " + f le.getAbsolutePath());
          F leUt ls.deleteD rectory(f le);
        } catch ( OExcept on e) {
          LOG.error("Fa led to delete d rectory: " + f le.getPath(), e);
        }
      }
    }
  }

  /**
   * Removes t  data for all unused seg nts from t  local d sk. T   ncludes:
   *  - data for old seg nts
   *  - data for seg nts belong ng to anot r part  on
   *  - data for seg nts belong ng to a d fferent flush vers on.
   */
  publ c stat c vo d removeUnusedSeg nts(
      Part  onManager part  onManager,
      Part  onConf g part  onConf g,
       nt sc maMajorVers on,
      Seg ntSyncConf g seg ntSyncConf g) {

     f (Earlyb rd ndexConf gUt l. sArch veSearch()) {
      removeOldBu ldGenerat ons(
          Earlyb rdConf g.getStr ng("root_d r"),
          Earlyb rdConf g.getStr ng("offl ne_seg nt_bu ld_gen")
      );
      removeOldSeg nts(seg ntSyncConf g);

      Precond  ons.c ckState(part  onManager  nstanceof Arch veSearchPart  onManager);
      removeArch veT  sl ceOuts deServ ngRange(
          part  onConf g,
          ((Arch veSearchPart  onManager) part  onManager).getT  Sl cer(), seg ntSyncConf g);
    }

    // Remove seg nts from ot r part  ons
    remove ndexesFromOt rPart  ons(
        part  onConf g.get ndex ngHashPart  on D(),
        part  onConf g.getNumPart  ons(), seg ntSyncConf g);

    // Remove old flus d seg nts
    removeOldFlushVers on ndexes(sc maMajorVers on, seg ntSyncConf g);
  }
}
