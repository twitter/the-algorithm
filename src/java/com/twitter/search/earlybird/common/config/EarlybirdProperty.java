package com.tw ter.search.earlyb rd.common.conf g;

 mport java.lang.reflect.Mod f er;
 mport java.ut l.Arrays;
 mport java.ut l.L st;
 mport java.ut l.funct on.B Funct on;
 mport java.ut l.funct on.Funct on;
 mport java.ut l.stream.Collectors;

 mport com.google.common.collect. mmutableL st;

 mport com.tw ter.app.Flag;
 mport com.tw ter.app.Flaggable;
 mport com.tw ter.app.Flags;
 mport com.tw ter.f nagle.mtls.aut nt cat on.Serv ce dent f er;

/**
 * Stateless class that represents an Earlyb rd property that can be spec f ed by a command l ne
 * flag.
 * <p>
 * T   s a regular Java class  nstead of enum to have a gener c type.
 *
 * @param <T>
 */
publ c f nal class Earlyb rdProperty<T> {

  pr vate stat c f nal class PropertyType<T> {

    pr vate stat c f nal PropertyType<Boolean> BOOLEAN = new PropertyType<>(
        Flaggable.ofJavaBoolean(), Earlyb rdConf g::getBool, Earlyb rdConf g::getBool);

    pr vate stat c f nal PropertyType< nteger>  NT = new PropertyType<>(
        Flaggable.ofJava nteger(), Earlyb rdConf g::get nt, Earlyb rdConf g::get nt);

    pr vate stat c f nal PropertyType<Str ng> STR NG = new PropertyType<>(
        Flaggable.ofStr ng(), Earlyb rdConf g::getStr ng, Earlyb rdConf g::getStr ng);

    pr vate f nal Flaggable<T> flaggable;
    pr vate f nal Funct on<Str ng, T> getter;
    pr vate f nal B Funct on<Str ng, T, T> getterW hDefault;

    pr vate PropertyType(Flaggable<T> flaggable, Funct on<Str ng, T> getter,
                         B Funct on<Str ng, T, T> getterW hDefault) {
      t .flaggable = flaggable;
      t .getter = getter;
      t .getterW hDefault = getterW hDefault;
    }
  }

  publ c stat c f nal Earlyb rdProperty<Str ng> PENGU N_VERS ON =
      new Earlyb rdProperty<>(
          "pengu n_vers on",
          "T  pengu n vers on to  ndex.",
          PropertyType.STR NG,
          false);

  publ c stat c f nal Earlyb rdProperty< nteger> THR FT_PORT = new Earlyb rdProperty<>(
      "thr ft_port",
      "overr de thr ft port from conf g f le",
      PropertyType. NT,
      false);

  publ c stat c f nal Earlyb rdProperty< nteger> WARMUP_THR FT_PORT = new Earlyb rdProperty<>(
      "warmup_thr ft_port",
      "overr de warmup thr ft port from conf g f le",
      PropertyType. NT,
      false);

  publ c stat c f nal Earlyb rdProperty< nteger> SEARCHER_THREADS = new Earlyb rdProperty<>(
      "searc r_threads",
      "overr de number of searc r threads from conf g f le",
      PropertyType. NT,
      false);

  publ c stat c f nal Earlyb rdProperty<Str ng> EARLYB RD_T ER = new Earlyb rdProperty<>(
      "earlyb rd_t er",
      "t  earlyb rd t er (e.g. t er1), used on Aurora",
      PropertyType.STR NG,
      true);

  publ c stat c f nal Earlyb rdProperty< nteger> REPL CA_ D = new Earlyb rdProperty<>(
      "repl ca_ d",
      "t   D  n a part  on, used on Aurora",
      PropertyType. NT,
      true);

  publ c stat c f nal Earlyb rdProperty< nteger> PART T ON_ D = new Earlyb rdProperty<>(
      "part  on_ d",
      "part  on  D, used on Aurora",
      PropertyType. NT,
      true);

  publ c stat c f nal Earlyb rdProperty< nteger> NUM_PART T ONS = new Earlyb rdProperty<>(
      "num_part  ons",
      "number of part  ons, used on Aurora",
      PropertyType. NT,
      true);

  publ c stat c f nal Earlyb rdProperty< nteger> NUM_ NSTANCES = new Earlyb rdProperty<>(
      "num_ nstances",
      "number of  nstances  n t  job, used on Aurora",
      PropertyType. NT,
      true);

  publ c stat c f nal Earlyb rdProperty< nteger> SERV NG_T MESL CES = new Earlyb rdProperty<>(
      "serv ng_t  sl ces",
      "number of t   sl ces to serve, used on Aurora",
      PropertyType. NT,
      true);

  publ c stat c f nal Earlyb rdProperty<Str ng> ROLE = new Earlyb rdProperty<>(
      "role",
      "Role  n t  serv ce path of Earlyb rd",
      PropertyType.STR NG,
      true,
      true);

  publ c stat c f nal Earlyb rdProperty<Str ng> EARLYB RD_NAME = new Earlyb rdProperty<>(
      "earlyb rd_na ",
      "Na   n t  serv ce path of Earlyb rd w hout hash part  on suff x",
      PropertyType.STR NG,
      true,
      true);

  publ c stat c f nal Earlyb rdProperty<Str ng> ENV = new Earlyb rdProperty<>(
      "env",
      "Env ron nt  n t  serv ce path of Earlyb rd",
      PropertyType.STR NG,
      true,
      true);

  publ c stat c f nal Earlyb rdProperty<Str ng> ZONE = new Earlyb rdProperty<>(
      "zone",
      "Zone (data center)  n t  serv ce path of Earlyb rd",
      PropertyType.STR NG,
      true,
      true);

  publ c stat c f nal Earlyb rdProperty<Str ng> DL_UR  = new Earlyb rdProperty<>(
      "dl_ur ",
      "D str butedLog UR  for default DL reader",
      PropertyType.STR NG,
      false);

  publ c stat c f nal Earlyb rdProperty<Str ng> USER_UPDATES_DL_UR  = new Earlyb rdProperty<>(
      "user_updates_dl_ur ",
      "D str butedLog UR  for user updates DL reader",
      PropertyType.STR NG,
      false);

  publ c stat c f nal Earlyb rdProperty<Str ng> ANT SOC AL_USERUPDATES_DL_STREAM =
      new Earlyb rdProperty<>(
          "ant soc al_userupdates_dl_stream",
          "DL stream na  for ant soc al user updates w hout DL vers on suff x",
          PropertyType.STR NG,
          false);

  publ c stat c f nal Earlyb rdProperty<Str ng> ZK_APP_ROOT = new Earlyb rdProperty<>(
      "zk_app_root",
      "SZooKeeper base root path for t  appl cat on",
      PropertyType.STR NG,
      true);

  publ c stat c f nal Earlyb rdProperty<Boolean> SEGMENT_LOAD_FROM_HDFS_ENABLED =
      new Earlyb rdProperty<>(
          "seg nt_load_from_hdfs_enabled",
          "W t r to load seg nt data from HDFS",
          PropertyType.BOOLEAN,
          false);

  publ c stat c f nal Earlyb rdProperty<Boolean> SEGMENT_FLUSH_TO_HDFS_ENABLED =
      new Earlyb rdProperty<>(
          "seg nt_flush_to_hdfs_enabled",
          "W t r to flush seg nt data to HDFS",
          PropertyType.BOOLEAN,
          false);

  publ c stat c f nal Earlyb rdProperty<Str ng> HDFS_SEGMENT_SYNC_D R = new Earlyb rdProperty<>(
      "hdfs_seg nt_sync_d r",
      "HDFS d rectory to sync seg nt data",
      PropertyType.STR NG,
      false);

  publ c stat c f nal Earlyb rdProperty<Str ng> HDFS_SEGMENT_UPLOAD_D R = new Earlyb rdProperty<>(
      "hdfs_seg nt_upload_d r",
      "HDFS d rectory to upload seg nt data",
      PropertyType.STR NG,
      false);

  publ c stat c f nal Earlyb rdProperty<Boolean> ARCH VE_DA LY_STATUS_BATCH_FLUSH NG_ENABLED =
      new Earlyb rdProperty<>(
          "arch ve_da ly_status_batch_flush ng_enabled",
          "W t r to enable arch ve da ly status batch flush ng",
          PropertyType.BOOLEAN,
          false);

  publ c stat c f nal Earlyb rdProperty<Str ng> HDFS_ NDEX_SYNC_D R = new Earlyb rdProperty<>(
      "hdfs_ ndex_sync_d r",
      "HDFS d rectory to sync  ndex data",
      PropertyType.STR NG,
      true);

  publ c stat c f nal Earlyb rdProperty<Boolean> READ_ NDEX_FROM_PROD_LOCAT ON =
      new Earlyb rdProperty<>(
      "read_ ndex_from_prod_locat on",
      "Read  ndex from prod to speed up startup on stag ng / loadtest",
      PropertyType.BOOLEAN,
      false);

  publ c stat c f nal Earlyb rdProperty<Boolean> USE_DEC DER_OVERLAY = new Earlyb rdProperty<>(
      "use_dec der_overlay",
      "W t r to use dec der overlay",
      PropertyType.BOOLEAN,
      false);

  publ c stat c f nal Earlyb rdProperty<Str ng> DEC DER_OVERLAY_CONF G = new Earlyb rdProperty<>(
      "dec der_overlay_conf g",
      "Path to dec der overlay conf g",
      PropertyType.STR NG,
      false);

  publ c stat c f nal Earlyb rdProperty< nteger> MAX_CONCURRENT_SEGMENT_ NDEXERS =
      new Earlyb rdProperty<>(
        "max_concurrent_seg nt_ ndexers",
        "Max mum number of seg nts  ndexed concurrently",
        PropertyType. NT,
        false);

  publ c stat c f nal Earlyb rdProperty<Boolean> TF_MODELS_ENABLED =
      new Earlyb rdProperty<>(
        "tf_models_enabled",
        "W t r tensorflow models should be loaded",
        PropertyType.BOOLEAN,
        false);

  publ c stat c f nal Earlyb rdProperty<Str ng> TF_MODELS_CONF G_PATH =
      new Earlyb rdProperty<>(
        "tf_models_conf g_path",
        "T  conf gurat on path of t  yaml f le conta n ng t  l st of tensorflow models to load.",
        PropertyType.STR NG,
        false);

  publ c stat c f nal Earlyb rdProperty< nteger> TF_ NTER_OP_THREADS =
      new Earlyb rdProperty<>(
        "tf_ nter_op_threads",
        "How many tensorflow  nter op threads to use. See TF docu ntat on for more  nformat on.",
        PropertyType. NT,
        false);

  publ c stat c f nal Earlyb rdProperty< nteger> TF_ NTRA_OP_THREADS =
      new Earlyb rdProperty<>(
        "tf_ ntra_op_threads",
        "How many tensorflow  ntra op threads to use. See TF docu ntat on for more  nformat on.",
        PropertyType. NT,
        false);

  publ c stat c f nal Earlyb rdProperty< nteger> MAX_ALLOWED_REPL CAS_NOT_ N_SERVER_SET =
      new Earlyb rdProperty<>(
          "max_allo d_repl cas_not_ n_server_set",
          "How many repl cas are allo d to be m ss ng from t  Earlyb rd server set.",
          PropertyType. NT,
          false);

  publ c stat c f nal Earlyb rdProperty<Boolean> CHECK_NUM_REPL CAS_ N_SERVER_SET =
      new Earlyb rdProperty<>(
          "c ck_num_repl cas_ n_server_set",
          "W t r Coord natedEarlyb rdAct ons should c ck t  number of al ve repl cas",
          PropertyType.BOOLEAN,
          false);

  publ c stat c f nal Earlyb rdProperty< nteger> MAX_QUEUE_S ZE =
      new Earlyb rdProperty<>(
          "max_queue_s ze",
          "Max mum s ze of searc r worker executor queue.  f <= 0 queue  s unbounded.",
          PropertyType. NT,
          false);

  publ c stat c f nal Earlyb rdProperty<Str ng> KAFKA_ENV =
      new Earlyb rdProperty<>(
          "kafka_env",
          "T  env ron nt to use for kafka top cs.",
          PropertyType.STR NG,
          false);
  publ c stat c f nal Earlyb rdProperty<Str ng> KAFKA_PATH =
      new Earlyb rdProperty<>(
          "kafka_path",
          "W ly path to t  Search kafka cluster.",
          PropertyType.STR NG,
          false);
  publ c stat c f nal Earlyb rdProperty<Str ng> TWEET_EVENTS_KAFKA_PATH =
      new Earlyb rdProperty<>(
          "t et_events_kafka_path",
          "W ly path to t  t et-events kafka cluster.",
          PropertyType.STR NG,
          false);
  publ c stat c f nal Earlyb rdProperty<Str ng> USER_UPDATES_KAFKA_TOP C =
      new Earlyb rdProperty<>(
          "user_updates_top c",
          "Na  of t  Kafka top c that conta n user updates.",
          PropertyType.STR NG,
          false);
  publ c stat c f nal Earlyb rdProperty<Str ng> USER_SCRUB_GEO_KAFKA_TOP C =
      new Earlyb rdProperty<>(
          "user_scrub_geo_top c",
          "Na  of t  Kafka top c that conta n UserScrubGeoEvents.",
          PropertyType.STR NG,
          false);
  publ c stat c f nal Earlyb rdProperty<Str ng> EARLYB RD_SCRUB_GEN =
      new Earlyb rdProperty<>(
          "earlyb rd_scrub_gen",
          "SCRUB_GEN TO DEPLOY",
          PropertyType.STR NG,
          false);
  publ c stat c f nal Earlyb rdProperty<Boolean> CONSUME_GEO_SCRUB_EVENTS =
      new Earlyb rdProperty<>(
        "consu _geo_scrub_events",
        "W t r to consu  user scrub geo events or not",
        PropertyType.BOOLEAN,
        false);

  pr vate stat c f nal L st<Earlyb rdProperty<?>> ALL_PROPERT ES =
      Arrays.stream(Earlyb rdProperty.class.getDeclaredF elds())
          .f lter(f eld ->
              (f eld.getMod f ers() & Mod f er.STAT C) > 0
                && f eld.getType() == Earlyb rdProperty.class)
          .map(f eld -> {
            try {
              return (Earlyb rdProperty<?>) f eld.get(Earlyb rdProperty.class);
            } catch (Except on e) {
              throw new Runt  Except on(e);
            }
          })
          .collect(Collectors.collect ngAndT n(Collectors.toL st(),  mmutableL st::copyOf));

  publ c stat c Serv ce dent f er getServ ce dent f er() {
    return new Serv ce dent f er(
        ROLE.get(),
        EARLYB RD_NAME.get(),
        ENV.get(),
        ZONE.get());
  }

  pr vate f nal Str ng na ;
  pr vate f nal Str ng  lp;
  pr vate f nal PropertyType<T> type;
  pr vate f nal boolean requ redOnAurora;
  pr vate f nal boolean requ redOnDed cated;

  pr vate Earlyb rdProperty(Str ng na , Str ng  lp, PropertyType<T> type,
                            boolean requ redOnAurora) {
    t (na ,  lp, type, requ redOnAurora, false);
  }

  pr vate Earlyb rdProperty(Str ng na , Str ng  lp, PropertyType<T> type,
                            boolean requ redOnAurora, boolean requ redOnDed cated) {
    t .na  = na ;
    t . lp =  lp;
    t .type = type;
    t .requ redOnAurora = requ redOnAurora;
    t .requ redOnDed cated = requ redOnDed cated;
  }

  publ c Str ng na () {
    return na ;
  }

  publ c boolean  sRequ redOnAurora() {
    return requ redOnAurora;
  }

  publ c boolean  sRequ redOnDed cated() {
    return requ redOnDed cated;
  }

  publ c Flag<T> createFlag(Flags flags) {
    return flags.createMandatory(na ,  lp, null, type.flaggable);
  }

  publ c T get() {
    return type.getter.apply(na );
  }

  publ c T get(T devaultValue) {
    return type.getterW hDefault.apply(na , devaultValue);
  }

  publ c stat c Earlyb rdProperty[] values() {
    return ALL_PROPERT ES.toArray(new Earlyb rdProperty[0]);
  }
}
