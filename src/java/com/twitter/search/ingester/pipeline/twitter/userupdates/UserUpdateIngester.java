package com.tw ter.search. ngester.p pel ne.tw ter.userupdates;

 mport java.ut l.AbstractMap;
 mport java.ut l.Collect on;
 mport java.ut l.Collect ons;
 mport java.ut l.EnumSet;
 mport java.ut l.L st;
 mport java.ut l.Map;
 mport java.ut l.Objects;
 mport java.ut l.Set;
 mport java.ut l.funct on.Funct on;
 mport java.ut l.stream.Collectors;

 mport com.google.common.collect. mmutableMap;
 mport com.google.common.collect.Sets;

 mport org.apac .commons.text.CaseUt ls;
 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.common.collect ons.Pa r;
 mport com.tw ter.dec der.Dec der;
 mport com.tw ter.f nagle.ut l.DefaultT  r;
 mport com.tw ter.g zmoduck.thr ftjava.L fecycleChangeReason;
 mport com.tw ter.g zmoduck.thr ftjava.LookupContext;
 mport com.tw ter.g zmoduck.thr ftjava.QueryF elds;
 mport com.tw ter.g zmoduck.thr ftjava.Safety;
 mport com.tw ter.g zmoduck.thr ftjava.UpdateD ff em;
 mport com.tw ter.g zmoduck.thr ftjava.User;
 mport com.tw ter.g zmoduck.thr ftjava.UserMod f cat on;
 mport com.tw ter.g zmoduck.thr ftjava.UserServ ce;
 mport com.tw ter.g zmoduck.thr ftjava.UserType;
 mport com.tw ter.search.common. ndex ng.thr ftjava.Ant soc alUserUpdate;
 mport com.tw ter.search.common. ndex ng.thr ftjava.UserUpdateType;
 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.common. tr cs.SearchLongGauge;
 mport com.tw ter.ut l.Durat on;
 mport com.tw ter.ut l.Future;
 mport com.tw ter.ut l.T  outExcept on;

/**
 * T  class  ngests {@l nk UserMod f cat on} events and transforms t m  nto a poss bly empty l st
 * of {@l nk Ant soc alUserUpdate}s to be  ndexed by Earlyb rds.
 */
publ c class UserUpdate ngester {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(UserUpdate ngester.class);
  pr vate stat c f nal Durat on RESULT_T MEOUT = Durat on.fromSeconds(3);

  pr vate stat c f nal L st<Ant soc alUserUpdate> NO_UPDATE = Collect ons.emptyL st();

  // Map from UserUpdateType to a set of Safety f elds to exam ne.
  pr vate stat c f nal Map<UserUpdateType, Set<Safety._F elds>> SAFETY_F ELDS_MAP =
       mmutableMap.of(
          UserUpdateType.ANT SOC AL,
          Sets. mmutableEnumSet(
              Safety._F elds.SUSPENDED, Safety._F elds.DEACT VATED, Safety._F elds.OFFBOARDED),
          UserUpdateType.NSFW,
          Sets. mmutableEnumSet(Safety._F elds.NSFW_USER, Safety._F elds.NSFW_ADM N),
          UserUpdateType.PROTECTED, Sets. mmutableEnumSet(Safety._F elds. S_PROTECTED));

  pr vate stat c f nal Funct on<Safety._F elds, Str ng> F ELD_TO_F ELD_NAME_FUNCT ON =
      f eld -> "safety." + CaseUt ls.toCa lCase(f eld.na (), false, '_');

  pr vate stat c f nal Map<Str ng, UserUpdateType> F ELD_NAME_TO_TYPE_MAP =
      SAFETY_F ELDS_MAP.entrySet().stream()
          .flatMap(
              entry -> entry.getValue().stream()
                  .map(f eld -> new AbstractMap.S mpleEntry<>(
                      F ELD_TO_F ELD_NAME_FUNCT ON.apply(f eld),
                      entry.getKey())))
          .collect(Collectors.toMap(
              AbstractMap.S mpleEntry::getKey,
              AbstractMap.S mpleEntry::getValue));

  pr vate stat c f nal Map<Str ng, Safety._F elds> F ELD_NAME_TO_F ELD_MAP =
      SAFETY_F ELDS_MAP.values().stream()
          .flatMap(Collect on::stream)
          .collect(Collectors.toMap(
              F ELD_TO_F ELD_NAME_FUNCT ON,
              Funct on. dent y()));

  pr vate stat c f nal LookupContext LOOKUP_CONTEXT = new LookupContext()
      .set nclude_deact vated(true)
      .set nclude_erased(true)
      .set nclude_suspended(true)
      .set nclude_offboarded(true)
      .set nclude_protected(true);

  pr vate f nal UserServ ce.Serv ceToCl ent userServ ce;
  pr vate f nal Dec der dec der;

  pr vate f nal SearchLongGauge userMod f cat onLatency;
  pr vate f nal SearchCounter unsuccessfulUserMod f cat onCount;
  pr vate f nal SearchCounter by nact veAccountDeact vat onUserMod f cat onCount;
  pr vate f nal SearchCounter  rrelevantUserMod f cat onCount;
  pr vate f nal SearchCounter notNormalUserCount;
  pr vate f nal SearchCounter m ss ngSafetyCount;
  pr vate f nal SearchCounter userServ ceRequests;
  pr vate f nal SearchCounter userServ ceSuccesses;
  pr vate f nal SearchCounter userServ ceNoResults;
  pr vate f nal SearchCounter userServ ceFa lures;
  pr vate f nal SearchCounter userServ ceT  outs;
  pr vate f nal Map<Pa r<UserUpdateType, Boolean>, SearchCounter> counterMap;

  publ c UserUpdate ngester(
      Str ng statPref x,
      UserServ ce.Serv ceToCl ent userServ ce,
      Dec der dec der
  ) {
    t .userServ ce = userServ ce;
    t .dec der = dec der;

    userMod f cat onLatency =
        SearchLongGauge.export(statPref x + "_user_mod f cat on_latency_ms");
    unsuccessfulUserMod f cat onCount =
        SearchCounter.export(statPref x + "_unsuccessful_user_mod f cat on_count");
    by nact veAccountDeact vat onUserMod f cat onCount =
        SearchCounter.export(statPref x
                + "_by_ nact ve_account_deact vat on_user_mod f cat on_count");
     rrelevantUserMod f cat onCount =
        SearchCounter.export(statPref x + "_ rrelevant_user_mod f cat on_count");
    notNormalUserCount =
        SearchCounter.export(statPref x + "_not_normal_user_count");
    m ss ngSafetyCount =
        SearchCounter.export(statPref x + "_m ss ng_safety_count");
    userServ ceRequests =
        SearchCounter.export(statPref x + "_user_serv ce_requests");
    userServ ceSuccesses =
        SearchCounter.export(statPref x + "_user_serv ce_successes");
    userServ ceNoResults =
        SearchCounter.export(statPref x + "_user_serv ce_no_results");
    userServ ceFa lures =
        SearchCounter.export(statPref x + "_user_serv ce_fa lures");
    userServ ceT  outs =
        SearchCounter.export(statPref x + "_user_serv ce_t  outs");
    counterMap =  mmutableMap.<Pa r<UserUpdateType, Boolean>, SearchCounter>bu lder()
        .put(Pa r.of(UserUpdateType.ANT SOC AL, true),
            SearchCounter.export(statPref x + "_ant soc al_set_count"))
        .put(Pa r.of(UserUpdateType.ANT SOC AL, false),
            SearchCounter.export(statPref x + "_ant soc al_unset_count"))
        .put(Pa r.of(UserUpdateType.NSFW, true),
            SearchCounter.export(statPref x + "_nsfw_set_count"))
        .put(Pa r.of(UserUpdateType.NSFW, false),
            SearchCounter.export(statPref x + "_nsfw_unset_count"))
        .put(Pa r.of(UserUpdateType.PROTECTED, true),
            SearchCounter.export(statPref x + "_protected_set_count"))
        .put(Pa r.of(UserUpdateType.PROTECTED, false),
            SearchCounter.export(statPref x + "_protected_unset_count"))
        .bu ld();
  }

  /**
   * Convert a UserMod f cat on event  nto a (poss bly empty) l st of ant soc al updates for
   * Earlyb rd.
   */
  publ c Future<L st<Ant soc alUserUpdate>> transform(UserMod f cat on userMod f cat on) {
    userMod f cat onLatency.set(System.currentT  M ll s() - userMod f cat on.getUpdated_at_msec());

     f (!userMod f cat on. sSuccess()) {
      unsuccessfulUserMod f cat onCount. ncre nt();
      return Future.value(NO_UPDATE);
    }

    // To avo d UserTable gets overflo d,   exclude traff c from By nact veAccountDeact vat on
     f (userMod f cat on.getUser_aud _data() != null
        && userMod f cat on.getUser_aud _data().getReason() != null
        && userMod f cat on.getUser_aud _data().getReason()
            == L fecycleChangeReason.BY_ NACT VE_ACCOUNT_DEACT VAT ON) {
      by nact veAccountDeact vat onUserMod f cat onCount. ncre nt();
      return Future.value(NO_UPDATE);
    }

    long user d = userMod f cat on.getUser_ d();
    Set<UserUpdateType> userUpdateTypes = getUserUpdateTypes(userMod f cat on);
     f (userUpdateTypes. sEmpty()) {
       rrelevantUserMod f cat onCount. ncre nt();
      return Future.value(NO_UPDATE);
    }

    Future<User> userFuture = userMod f cat on. sSetCreate()
        ? Future.value(userMod f cat on.getCreate())
        : getUser(user d);

    return userFuture
        .map(user -> {
           f (user == null) {
            return NO_UPDATE;
          } else  f (user.getUser_type() != UserType.NORMAL) {
            LOG. nfo("User w h  d={}  s not a normal user.", user d);
            notNormalUserCount. ncre nt();
            return NO_UPDATE;
          } else  f (!user. sSetSafety()) {
            LOG. nfo("Safety for User w h  d={}  s m ss ng.", user d);
            m ss ngSafetyCount. ncre nt();
            return NO_UPDATE;
          }

           f (userMod f cat on. sSetUpdate()) {
            // Apply relevant updates from UserMod f cat on as User returned from G zmoduck may not
            // have reflected t m yet.
            applyUpdates(user, userMod f cat on);
          }

          return userUpdateTypes.stream()
              .map(userUpdateType ->
                  convertToAnt Soc alUserUpdate(
                      user, userUpdateType, userMod f cat on.getUpdated_at_msec()))
              .peek(update ->
                  counterMap.get(Pa r.of(update.getType(), update. sValue())). ncre nt())
              .collect(Collectors.toL st());
        })
        .onFa lure(com.tw ter.ut l.Funct on.cons(except on -> {
           f (except on  nstanceof UserNotFoundExcept on) {
            userServ ceNoResults. ncre nt();
          } else  f (except on  nstanceof T  outExcept on) {
            userServ ceT  outs. ncre nt();
            LOG.error("UserServ ce.get t  d out for user  d=" + user d, except on);
          } else {
            userServ ceFa lures. ncre nt();
            LOG.error("UserServ ce.get fa led for user  d=" + user d, except on);
          }
        }));
  }

  pr vate stat c Set<UserUpdateType> getUserUpdateTypes(UserMod f cat on userMod f cat on) {
    Set<UserUpdateType> types = EnumSet.noneOf(UserUpdateType.class);

     f (userMod f cat on. sSetUpdate()) {
      userMod f cat on.getUpdate().stream()
          .map(UpdateD ff em::getF eld_na )
          .map(F ELD_NAME_TO_TYPE_MAP::get)
          .f lter(Objects::nonNull)
          .collect(Collectors.toCollect on(() -> types));
    } else  f (userMod f cat on. sSetCreate() && userMod f cat on.getCreate(). sSetSafety()) {
      Safety safety = userMod f cat on.getCreate().getSafety();
       f (safety. sSuspended()) {
        types.add(UserUpdateType.ANT SOC AL);
      }
       f (safety. sNsfw_adm n() || safety. sNsfw_user()) {
        types.add(UserUpdateType.NSFW);
      }
       f (safety. s s_protected()) {
        types.add(UserUpdateType.PROTECTED);
      }
    }

    return types;
  }

  pr vate Future<User> getUser(long user d) {
    userServ ceRequests. ncre nt();
    return userServ ce.get(
        LOOKUP_CONTEXT,
        Collect ons.s ngletonL st(user d),
        Collect ons.s ngleton(QueryF elds.SAFETY))
        .w h n(DefaultT  r.get nstance(), RESULT_T MEOUT)
        .flatMap(userResults -> {
           f (userResults.s ze() != 1 || !userResults.get(0). sSetUser()) {
            return Future.except on(new UserNotFoundExcept on(user d));
          }

          userServ ceSuccesses. ncre nt();
          return Future.value(userResults.get(0).getUser());
        });
  }

  pr vate stat c vo d applyUpdates(User user, UserMod f cat on userMod f cat on) {
    userMod f cat on.getUpdate().stream()
        .f lter(update -> F ELD_NAME_TO_F ELD_MAP.conta nsKey(update.getF eld_na ()))
        .f lter(UpdateD ff em:: sSetAfter)
        .forEach(update ->
            user.getSafety().setF eldValue(
                F ELD_NAME_TO_F ELD_MAP.get(update.getF eld_na ()),
                Boolean.valueOf(update.getAfter()))
        );
  }

  pr vate Ant soc alUserUpdate convertToAnt Soc alUserUpdate(
      User user,
      UserUpdateType userUpdateType,
      long updatedAt) {
    boolean value = SAFETY_F ELDS_MAP.get(userUpdateType).stream()
        .anyMatch(safetyF eld -> (boolean) user.getSafety().getF eldValue(safetyF eld));
    return new Ant soc alUserUpdate(user.get d(), userUpdateType, value, updatedAt);
  }

  class UserNotFoundExcept on extends Except on {
    UserNotFoundExcept on(long user d) {
      super("User " + user d + " not found.");
    }
  }
}
