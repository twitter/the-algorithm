package com.tw ter.search.common.sc ma.earlyb rd;

 mport javax.annotat on.Nullable;

 mport com.tw ter.search.common.conf g.Conf g;

publ c enum FlushVers on {
  /* =======================================================
   * Vers ons
   * ======================================================= */
  VERS ON_0(" n  al vers on of part  on flush ng."),
  VERS ON_1("Added t  stamps and correspond ng mapper to Seg ntData."),
  VERS ON_2("Add column str de f elds."),
  VERS ON_3("Change facet f eld conf gurat on."),
  VERS ON_4("Add per term offens ve counters to parallel post ng arrays."),
  VERS ON_5("Add nat ve photo facet."),
  VERS ON_6("Add UserFeature column str de f eld"),
  VERS ON_7(" ndex seg nt opt m zat ons; new facet data structures."),
  VERS ON_8("Store statuses  n  mory  n Earlyb rd."),
  VERS ON_9(" ndex from_user_ ds  nto a searchable f eld."),
  VERS ON_10("Change from_user_ d d ct onary from fst to mphf"),
  VERS ON_11("Wr e  mage and v deo facet  n separate lucene f eld."),
  VERS ON_12("Add ret eted status  D to t  sparse CSF."),
  VERS ON_13("Add  sOffens ve f eld for profan y f lter."),
  VERS ON_14("F x features column str de f eld corrupt on."),
  VERS ON_15("Upgrade Lucene vers on, wh ch has a d fferent FST ser al zat on format."),
  VERS ON_16("Remove maxDoc  n favor of lastDoc D"),
  VERS ON_17("Added part  on and t  sl ce  dent f ers to Seg ntData."),
  VERS ON_18("Per-term payloads"),
  VERS ON_19("Mult ple per-doc payload f elds"),
  VERS ON_20("Un fy and f x hash codes"),
  VERS ON_21("Super a so  new flex ble realt   post ng l st format."),
  VERS ON_22("Added new geo  mple ntat on."),
  VERS ON_23("Upgrade to Lucene 4.0.0 F nal"),
  VERS ON_24("Added t et top c  ds."),
  VERS ON_25("Turn on sk p l st for  nt on facet."),
  VERS ON_26("Added new EncodedT etFeaturesColumnStr deF eld."),
  VERS ON_27("Top c  ds facet f eld."),
  VERS ON_28("From-user d scover stor es sk pl st f eld."),
  VERS ON_29("Move token zed screen na  to t  new userna  f eld"),
  VERS ON_30("Enable HF term pa rs  ndex."),
  VERS ON_31("Remove reverse doc  ds."),
  VERS ON_32("Sw ch shared status  d CSF to non-sparse long CSF  ndex."),
  VERS ON_33("New sk p l sts for opt m zed h gh df post ng l sts."),
  VERS ON_34("Store t et s gnature  n Earlyb rdEncodedFeatures."),
  VERS ON_35("Don't store shared status  d csf  n arch ve  ndexes."),
  VERS ON_36("Don't store norms."),
  VERS ON_37("64 b  user  ds."),
  VERS ON_38(" ndex l nks  n arch ve."),
  VERS ON_39("F x p c.tw ter.com  mage l nk handl ng not sett ng t   nternal f eld correctly."),
  VERS ON_40("F x all arch ve t ets be ng marked as repl es."),
  VERS ON_41("Avo d flush ng event_ ds f eld; event clusters are appl ed as updates."),
  VERS ON_42("No pos  on f elds refactor ng; made a few f elds to not use pos  on."),
  VERS ON_43(" ndex pr vate geo coord nates"),
  VERS ON_44("Mater al ze last doc  d  n H ghDFCompressedPost ngl sts", true),
  VERS ON_45("Remov ng from_user_ d facets support", true),
  VERS ON_46("Guard aga nst badly out of order t ets  n t  search arch ve.", true),
  VERS ON_47("Added card t le and descr pt on f elds.", true),
  VERS ON_48("Added card type CSF.", true),
  VERS ON_49("Lucene 4.4 upgrade", true),
  VERS ON_50("Put  m-arch ve back on non-lucene opt m zed  ndexes", true),
  VERS ON_51("Force  ndex rebu ld to f x blank text f eld. See SEARCH-2505.", true),
  VERS ON_52("Refactor ng of docValues/CSF.", true),
  VERS ON_53("Remove Seg ntData.Conf gurat on", true),
  VERS ON_54("F x bad  nd ces caused by SEARCH-2723.", true),
  VERS ON_55("F xed non-determ n st c facet ds across restarts. SEARCH-2815.", true),
  VERS ON_56("Flush Facet DMap.", true),
  VERS ON_57("Remove LatLonMapper and use standard DocValues  nstead.", true),
  VERS ON_58("Longterm Attr bute Opt m zat on.", true),
  VERS ON_59("Rena d arch ve seg nt na s. Current seg nt  s no longer mutable.", true),
  // Flush vers on 60 and 59 have t  sa  format.
  // Flush vers on  s  ncreased to tr gger a rebu ld, because   not ced  ncomplete seg nts.
  // More deta ls can be found on SEARCH-3664
  VERS ON_60("Flush vers on change to tr gger seg nt rebu ld.", true),
  VERS ON_61("Add ng back from_user_ d", true),
  VERS ON_62("Add ret et facet.", true),
  VERS ON_63("Sw ch to new  ndex AP   n com.tw ter.search.core.earlyb rd.", true),
  VERS ON_64("Sort  rge arch ve day and part-* data. SEARCH-4692.", true),
  VERS ON_65("F x  D_F ELD and CREATED_AT_F ELD sort order. SEARCH-4004 SEARCH-912 ", true),
  VERS ON_66("Rebu ld data for 1/5/2015. Data on HDFS f xed as part of SEARCH-5347.", true),
  VERS ON_67("Upgrade to Lucene 4.10.3.", true),
  VERS ON_68("Sw ch ng to Pengu n v4", true),
  VERS ON_69("F x 16% arch ve seg nts: SEARCH-6073", true),
  VERS ON_70("Sw ch ng to Pengu n v4 for full arch ve cluster. SEARCH-5302", true),
  VERS ON_71("Sw ch ng to Pengu n v4 for ssd arch ve cluster.", true),
  VERS ON_72("Added Esc rb rd annotat ons for full arch ve.", true),
  VERS ON_73("Lucene 5.2.1 upgrade.", true, 0),
  VERS ON_74("Hanndle geo scurbbed data and arch ve geo  ndex accuracy", true, 0),
  VERS ON_75("Delete from_user_ d_stor es from  nd ces", true, 0),
  VERS ON_76("Allow mult ple  ndex extens ons.", true, 0),
  VERS ON_77("Removed Earlyb rdCodec", true, 0),
  // m nor vers on 2: added embedded t et features
  // m nor vers on 3: change embedded t et features to  NC_ONLY
  VERS ON_78("Added 80 bytes of extended features", true, 3),
  // m nor vers on 1: SEARCH-8564 - Reference T et Author  D, us ng
  //                  EXTENDED_TEST_FEATURE_UNUSED_B TS_2 and EXTENDED_TEST_FEATURE_UNUSED_B TS_3
  VERS ON_79("Rena d UNUSED_B T to HAS_V S BLE_L NK", true, 1),
  // m nor vers on 2: SEARCH-8564 / http://go/rb/770373
  //                  Made REFERENCE_AUTHOR_ D_LEAST_S GN F CANT_ NT and
  //                  REFERENCE_AUTHOR_ D_MOST_S GN F CANT_ NT  mmutable f eld
  VERS ON_80("Facet for l nks: SEARCH-8331", true, 2),
  // m nor vers on 1: added v deo v ew count
  VERS ON_81("Add ng LowDF post ng l st w h packed  nts", true, 1),
  VERS ON_82("Enabl ng H ghDF post ng l st w h packed  nts", true, 0),
  // m nor vers on 1: SEARCH-9379 - Added b set for nullcast t ets
  // m nor vers on 2: SEARCH-8765 - Added v s ble token rat o
  VERS ON_83("Add b s  n encoded features for  d a type flags. SEARCH-9131", true, 2),
  VERS ON_84("Enable arch ve rebu ld for __has_l nks f eld. SEARCH-9635", true, 0),
  // m nor vers on 1: SEARCHQUAL-8130, add engage nt v2
  VERS ON_85("New arch ve bu ld gen for m ss ng geo data. SEARCH-9894", true, 1),
  VERS ON_86("Added new f elds to t   ndex", true, 0),
  // Dur ng t  rebu ld both t  statuses and t  engage nt counts  re regenerated.
  // m nor vers on 1: added quote_count
  VERS ON_87("Per od c arch ve full rebu ld. SEARCH-9423", true, 1),
  // m nor vers on 1: make new token zed user na /handle f elds textSearchable
  //                  (see go/rb/847134/)
  // m nor vers on 2: added has_quote
  VERS ON_88("F x ng m ss ng day  n t  full arch ve  ndex. SEARCH-11233", true, 2),
  VERS ON_89(" ndex and store conversat on  ds.", true, 0),
  VERS ON_90("F x ng  ncons stent days  n t  full arch ve  ndex. SEARCH-11744", true, 0),
  VERS ON_91("Mak ng  n_reply_to_user_ d f eld use MPH. SEARCH-10836", true, 0),
  VERS ON_92("Allow searc s by any f eld. SEARCH-11251", true, 0),
  // Dur ng t  rebu ld   regenerated engage nt counts and  rged t  annotat ons  n t 
  // aggregate job.
  VERS ON_93("Per od c arch ve full rebu ld. SEARCH-11076", true, 0),
  // m nor vers on 1: add Thr ftCSFV ewSett ngs.outputCSFType
  VERS ON_94(" ndex ng a bunch of geo f elds. SEARCH-10283", true, 1),
  VERS ON_95("Remov ng top c  D f elds. SEARCH-8616", true, 0),
    // m nor vers on 1: add Thr ftCSFV ewSett ngs.normal zat onType
  VERS ON_96("Enabl ng conversat on  D for all clusters. SEARCH-11989", true, 1),
  // m nor vers on 1: set several feature conf gurat on to be correct double type
  // m nor vers on 2: set so  more feature conf gurat on to be correct double type
  // m nor vers on 3: add safety labels SEARCHQUAL-9561
  // m nor vers on 4: add   ghted engage nt counts SEARCHQUAL-9574
  // m nor vers on 5: add Dopam ne non personal zed score SEARCHQUAL-9743
  VERS ON_97("Chang ng CSF type to BOOLEAN for so  has_* flags.", true, 5),
  VERS ON_98("Per od c arch ve full rebu ld. PCM-56871.", true, 1),
  VERS ON_99("Remov ng na d_ent  es f eld. SEARCH-13708", true, 0),
  // m nor vers on 1: add per scope features (SEARCHQUAL-10008)
  // m nor vers on 2: add raw_earlyb rd_score to T etExternalFeatures (SEARCHQUAL-10347)
  VERS ON_100("Upgrade Pengu n Vers on from V4 to V6. SEARCH-12991", true, 2),
  // m nor vers on 1: adjust for normal zer type for so  engage nt counters (SEARCHQUAL-9537)
  // m nor vers on 2: add decay ng engage nt counts and last engaged t  stamps (SEARCHQUAL-10532)
  VERS ON_101("Add emoj  to t   ndex. SEARCH-12991", true, 2),
  VERS ON_102("Per od c full arch ve rebu ld. PCM-67851", true, 0),
  VERS ON_103("Add l ked_by_user_ d f eld. SEARCH-15341", true, 0),
  // m nor vers on 1: remove last engaged t  stamp w h 3-h   ncre nt (SEARCHQUAL-10903)
  // m nor vers on 2: add fake engage nt counts (SEARCHQUAL-10795)
  // m nor vers on 3: add last engaged t  stamp w h 1-h   ncre nt (SEARCHQUAL-10942)
  VERS ON_104("Revert ng to t  20170109_pc100_par30 bu ld gen. SEARCH-15731", true, 3),
  VERS ON_105("Add 3 new f elds to arch ve  ndex for engage nt features. SEARCH-16102", true, 0),
  // T   s t  last rebu ld based on /tables/statuses. Start ng 9/14 t  bu ld-gen  s po red
  // by T etS ce. Dur ng t  rebu ld both statuses and engage nt counts  re rebu lt.
  VERS ON_106("Per od c arch ve full rebu ld. PCM-74652", true, 0),
  VERS ON_107("Remov ng card f elds from full arch ve  ndex.", true, 0),
  VERS ON_108("Remov ng t  tms_ d f eld from all sc mas.", true, 0),
  VERS ON_109("Remov ng LAT_LON_F ELD from all sc mas.", true, 0),
  VERS ON_110("Add ng t  card f elds back to t  full arch ve  ndex.", true, 1),
  // m nor vers on 1: Add composer s ce csf f eld (SEARCH-22494)
  VERS ON_111("Add ng composer_s ce to  ndex. SEARCH-20377.", true, 1),
  VERS ON_112("Part al rebu ld to f x SEARCH-22529.", true, 0),
  VERS ON_113("Full arch ve bu ld gen 20180312_pc100_par30.", true, 0),
  VERS ON_114("F x for SEARCH-23761.", true, 0),
  VERS ON_115("Add f elds for quoted t ets. SEARCH-23919", true, 0),
  // m nor vers on 1: Add 4 b  hashtag count,  nt on count and stock count (SEARCH-24336)
  VERS ON_116("Bump flush vers on for scrubb ng p pel ne. SEARCH-24225", true, 1),
  VERS ON_117("Add ret eted_by_user_ d and repl ed_to_by_user_ d f elds. SEARCH-24463", true, 0),
  // m nor vers on 1: Removed dopam ne_non_personal zed_score (SEARCHQUAL-10321)
  VERS ON_118("Add ng t  reply and ret et s ce t et  Ds: SEARCH-23702, SEARCH-24502", true, 1),
  // m nor vers on 1: add bl nk engage nt counts (SEARCHQUAL-15176)
  VERS ON_119("Remove publ c  nferred locat on: SEARCH-24235", true, 1),
  VERS ON_120("Flush extens ons before f elds w n flush ng seg nts.", true, 0),
  VERS ON_121("Flush t  start ngDoc dForSearch f eld. SEARCH-25464.", true, 0),
  VERS ON_122("Do not flush t  start ngDoc dForSearch f eld.", true, 0),
  VERS ON_123("Renam ng t  largestDoc D flus d property to f rstAddedDoc D.", true, 0),
  VERS ON_124("Use t  sk p l st post ng l st for all f elds.", true, 0),
  VERS ON_125("Use hashmap for t et  D lookup.", true, 0),
  VERS ON_126("Use t  sk p l st post ng l st for all f elds.", true, 0),
  VERS ON_127("Flush ng t  m n and max doc  Ds  n each seg nt.", true, 0),
  VERS ON_128("Add card_lang to  ndex. SEARCH-26539", true, 0),
  VERS ON_129("Move t  t et  D mapper to t  seg nt data.", true, 0),
  VERS ON_130("Move t  t   mapper to t  seg nt data.", true, 0),
  VERS ON_131("Change t  facets classes to work w h any doc  Ds.", true, 0),
  VERS ON_132("Make t  CSF classes work w h any doc  Ds.", true, 0),
  VERS ON_133("Remov ng smallestDoc D property.", true, 0),
  VERS ON_134("Opt m ze DeletedDocs before flush ng.", true, 0),
  VERS ON_135("Add payloads to sk pl sts.", true, 0),
  VERS ON_136("Add na  to  nt pools.", true, 0),
  VERS ON_137("Add unsorted stream offset.", true, 0),
  VERS ON_138("Sw ch to t  OutOfOrderRealt  T et DMapper.", true, 0),
  VERS ON_139("Remove realt   post ng l sts.", true, 0),
  VERS ON_140("Add na d_ent y f eld. SEARCH-27547", true, 0),
  VERS ON_141("Flush t  out of order updates count.", true, 0),
  VERS ON_142("Add na d_ent y facet support. SEARCH-28054", true, 0),
  VERS ON_143(" ndex updates before opt m z ng seg nt.", true, 0),
  VERS ON_144("Refactor TermsArray.", true, 0),
  VERS ON_145("Remove SmallestDoc D.", true, 0),
  VERS ON_146("Add ent y_ d facet support. SEARCH-28071", true, 0),
  VERS ON_147("Enable updat ng facets", true, 0),
  VERS ON_148("Rena  t  counter for feature updates to part al updates", true, 0),
  VERS ON_149("Stop flush ng offsets for sorted updates DL streams.", true, 0),
  VERS ON_150("Update t  na  of t  property for t  updates DL stream offset.", true, 0),
  VERS ON_151("Upgrade Lucene vers on to 5.5.5.", true, 0),
  VERS ON_152("Upgrade Lucene vers on to 6.0.0.", true, 0),
  VERS ON_153("Upgrade Lucene vers on to 6.6.6.", true, 0),
  VERS ON_154("Store t  t  sl ce  D on Earlyb rd ndexSeg ntData.", true, 0),
  VERS ON_155("Do not flush  ndex extens ons.", true, 0),
  VERS ON_156("Deprecate Thr ft ndexedF eldSett ngs.defaultF eldBoost.", true, 0),
  VERS ON_157("Load CREATED_AT_CSF_F ELD  nto RAM  n arch ve.", true, 0),
  VERS ON_158("Added d rected at user  D f eld and CSF.", true, 0),
  VERS ON_159("Chang ng deleted docs ser al zat on format.", true, 0),
  VERS ON_160("Add f elds for  alth model scores. SEARCH-31907, HML-2099", true, 0),
  VERS ON_161("Sw ch to t  'search' Kafka cluster.", true, 0),
  VERS ON_162("Update Lucene vers on to 7.0.0.", true, 0),
  VERS ON_163("Update Lucene vers on to 7.7.2.", true, 0),
  // m nor vers on 1: add  S_TREND NG_NOW_FLAG
  VERS ON_164("Collect per-term stats  n t  realt   seg nts.", true, 1),
  VERS ON_165("Update Lucene vers on to 8.5.2.", true, 0),
  VERS ON_166("Ser al ze maxPos  on f eld for  nvertedRealt   ndex", true, 0),
  VERS ON_167("Add f eld for pSpam T etScore. HML-2557", true, 0),
  VERS ON_168("Add f eld for pReportedT etScore. HML-2644", true, 0),
  VERS ON_169("Add f eld for spam T etContentScore. PFM-70", true, 0),
  VERS ON_170("Add reference author  d CSF. SEARCH-34715", true, 0),
  VERS ON_171("Add space_ d f eld. SEARCH-36156", true, 0),
  VERS ON_172("Add facet support for space_ d. SEARCH-36388", true, 0),
  VERS ON_173("Add space adm n and t le f elds. SEARCH-36986", true, 0),
  VERS ON_174("Sw ch ng to Pengu n v7 for realt  -exp0 cluster. SEARCH-36068", true, 0),
  VERS ON_175("Add ng exclus ve conversat on author  d CSF", true, 0),
  VERS ON_176("Add ng card UR  CSF", true, 0),
  // m nor vers on 1: add FROM_BLUE_VER F ED_ACCOUNT_FLAG
  // m nor vers on 2: Add ng new cluster REALT ME_CG. SEARCH-45692
  VERS ON_177("Add ng URL Descr pt on and T le f elds. SEARCH-41641", true, 2),

  /**
   * T  sem  colon  s on a separate l ne to avo d pollut ng g  bla   tory.
   * Put a comma after t  new enum f eld   add ng.
   */;

  // T  current vers on.
  publ c stat c f nal FlushVers on CURRENT_FLUSH_VERS ON =
      FlushVers on.values()[FlushVers on.values().length - 1];

  publ c stat c f nal Str ng DEL M TER = "_v_";

  /* =======================================================
   *  lper  thods
   * ======================================================= */
  pr vate f nal Str ng descr pt on;
  pr vate f nal boolean  sOff c al;
  pr vate f nal  nt m norVers on;

  /**
   * A flush vers on  s not off c al unless expl c ly stated to be off c al.
   * An unoff c al flush vers on  s never uploaded to HDFS.
   */
  pr vate FlushVers on(Str ng descr pt on) {
    t (descr pt on, false, 0);
  }

  pr vate FlushVers on(Str ng descr pt on, boolean  sOff c al) {
    t (descr pt on,  sOff c al, 0);
  }

  pr vate FlushVers on(Str ng descr pt on, boolean  sOff c al,  nt m norVers on) {
    t .descr pt on = descr pt on;
    t . sOff c al =  sOff c al;
    t .m norVers on = m norVers on;
  }

  /**
   * Returns f le extens on w h vers on number.
   */
  publ c Str ng getVers onF leExtens on() {
     f (t  == VERS ON_0) {
      return "";
    } else {
      return DEL M TER + ord nal();
    }
  }

  /**
   * Returns f le extens on g ven flush vers on number.
   *  f t  flush vers on  s unknown (e.g. h g r than current flush vers on or lo r than 0), null
   *  s returned.
   */
  @Nullable
  publ c stat c Str ng getVers onF leExtens on( nt flushVers on) {
     f (flushVers on > CURRENT_FLUSH_VERS ON.ord nal() || flushVers on < 0) {
      return null;
    } else {
      return FlushVers on.values()[flushVers on].getVers onF leExtens on();
    }
  }

  /**
   * Returns a str ng descr b ng t  current sc ma vers on.
   * @deprecated Please use {@l nk com.tw ter.search.common.sc ma.base.Sc ma#getVers onDescr pt on()}
   */
  @Deprecated
  publ c Str ng getDescr pt on() {
    return descr pt on;
  }

  /**
   * Returns t  sc ma's major vers on.
   * @deprecated Please use {@l nk com.tw ter.search.common.sc ma.base.Sc ma#getMajorVers onNumber()}.
   */
  @Deprecated
  publ c  nt getVers onNumber() {
    return t .ord nal();
  }

  publ c boolean onOrAfter(FlushVers on ot r) {
    return compareTo(ot r) >= 0;
  }

  /**
   * Returns w t r t  sc ma vers on  s off c al. Only off c al seg nts are uploaded to HDFS.
   * @deprecated Please use {@l nk com.tw ter.search.common.sc ma.base.Sc ma# sVers onOff c al()}.
   */
  @Deprecated
  publ c boolean  sOff c al() {
    //   want t  load ng/flush ng tests to pass locally even  f t  vers on  s not  ant
    // to be an off c al vers on.
    return  sOff c al || Conf g.env ron nt sTest();
  }

  /**
   * As of now, t   s hardcoded to 0.   w ll start us ng t  soon.
   * @deprecated Please consult sc ma for m nor vers on. T  should only be used to bu ld sc ma.
   */
  @Deprecated
  publ c  nt getM norVers on() {
    return m norVers on;
  }
}
