package com.tw ter.search.earlyb rd.common.userupdates;

 mport java.ut l.Date;
 mport java.ut l.concurrent.T  Un ;

 mport com.tw ter.common.ut l.Clock;
 mport com.tw ter.dec der.Dec der;
 mport com.tw ter.search.common. ndex ng.thr ftjava.UserUpdateType;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdCluster;
 mport com.tw ter.search.earlyb rd.common.conf g.Earlyb rdConf g;

/**
 * Conta ns log c for dec d ng w t r to apply a certa n user update to t  {@l nk UserTable}.
 */
publ c class UserUpdatesC cker {
  pr vate f nal Date ant soc alStartDate;
  pr vate f nal Dec der dec der;
  pr vate f nal boolean  sFullArch veCluster;

  publ c UserUpdatesC cker(Clock clock, Dec der dec der, Earlyb rdCluster cluster) {
    // How many days of ant soc al users to keep. A value of -1  ans keep ng all user updates.
    long ant soc alRecordDays =
        Earlyb rdConf g.getLong("keep_recent_ant soc al_user_updates_days", 30);
    t .ant soc alStartDate = ant soc alRecordDays > 0
        ? new Date(clock.nowM ll s() - T  Un .DAYS.toM ll s(ant soc alRecordDays)) : null;
    t .dec der = dec der;
    t . sFullArch veCluster = cluster == Earlyb rdCluster.FULL_ARCH VE;
  }

  /**
   * Dec des w t r to sk p t  g ven User nfoUpdate.
   */
  publ c boolean sk pUserUpdate(UserUpdate userUpdate) {
     f (userUpdate == null) { // always sk p null updates
      return true;
    }

    UserUpdateType type = userUpdate.updateType;

     f (type == UserUpdateType.PROTECTED && sk pProtectedUserUpdate()) {
      return true;
    }

     f (type == UserUpdateType.ANT SOC AL && sk pAnt soc alUserUpdate(userUpdate)) {
      return true;
    }

    // NSFW users can cont nue to t et even after t y are marked as NSFW. That  ans
    // that t  snapshot needs to have all NSFW users from t  beg nn ng of t  .  nce, no NSFW
    // users updates c ck  re.

    // pass all c cks, do not sk p t  user update
    return false;
  }

  // Ant soc al/suspended users can't t et after t y are suspended. Thus  f    ndex stores
  // t ets from t  last 10 days, and t y  re suspended 60 days ago,   don't need t m s nce
  // t re w ll be no t ets from t m.   can save space by not stor ng  nfo about those users.

  // (For arch ve, at rebu ld t     f lter out all suspended users t ets, so for a user that
  // was suspended before a rebu ld, no need to use space to store that t  user  s suspended)
  pr vate boolean sk pAnt soc alUserUpdate(UserUpdate userUpdate) {
    return ant soc alStartDate != null && userUpdate.getUpdatedAt().before(ant soc alStartDate);
  }

  // sk p protected user updates for realt   and protected clusters
  pr vate boolean sk pProtectedUserUpdate() {
    return ! sFullArch veCluster;
  }
}
