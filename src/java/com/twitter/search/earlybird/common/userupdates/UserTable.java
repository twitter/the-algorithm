package com.tw ter.search.earlyb rd.common.userupdates;

 mport java.ut l. erator;
 mport java.ut l.concurrent.atom c.Atom cReference;
 mport java.ut l.funct on.Pred cate;

 mport com.google.common.annotat ons.V s bleForTest ng;
 mport com.google.common.base.Precond  ons;

 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.search.common. tr cs.SearchLongGauge;
 mport com.tw ter.search.common. tr cs.SearchRateCounter;
 mport com.tw ter.search.common.ut l.hash.GeneralLongHashFunct on;

/**
 * Table conta n ng  tadata about users, l ke NSFW or Ant soc al status.
 * Used for result f lter ng.
 */
publ c class UserTable {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(UserTable.class);

  @V s bleForTest ng // Not f nal for test ng.
  protected stat c long userUpdateTableMaxCapac y = 1L << 30;

  pr vate stat c f nal  nt DEFAULT_ N T AL_CAPAC TY = 1024;
  pr vate stat c f nal  nt BYTE_W DTH = 8;

  pr vate stat c f nal Str ng USER_TABLE_CAPAC TY = "user_table_capac y";
  pr vate stat c f nal Str ng USER_TABLE_S ZE = "user_table_s ze";
  pr vate stat c f nal Str ng
      USER_NUM_USERS_W TH_NO_B TS_SET = "user_table_users_w h_no_b s_set";
  pr vate stat c f nal Str ng USER_TABLE_ANT SOC AL_USERS = "user_table_ant soc al_users";
  pr vate stat c f nal Str ng USER_TABLE_OFFENS VE_USERS = "user_table_offens ve_users";
  pr vate stat c f nal Str ng USER_TABLE_NSFW_USERS = "user_table_nsfw_users";
  pr vate stat c f nal Str ng USER_TABLE_ S_PROTECTED_USERS = "user_table_ s_protected_users";

  /**
   * number of users f ltered
   */
  pr vate stat c f nal SearchRateCounter USER_TABLE_USERS_F LTERED_COUNTER =
      new SearchRateCounter("user_table_users_f ltered");

  pr vate SearchLongGauge userTableCapac y;
  pr vate SearchLongGauge userTableS ze;
  pr vate SearchLongGauge userTableNumUsersW hNoB sSet;
  pr vate SearchLongGauge userTableAnt soc alUsers;
  pr vate SearchLongGauge userTableOffens veUsers;
  pr vate SearchLongGauge userTableNsfwUsers;
  pr vate SearchLongGauge userTable sProtectedUsers;

  pr vate f nal Pred cate<Long> user dF lter;
  pr vate long lastRecordT  stamp;

  pr vate stat c f nal class HashTable {
    pr vate  nt numUsers nTable;
    pr vate  nt numUsersW hNoB sSet;
    // s ze 8 array conta ns t  number of users who have t  b  set at t   ndex (0-7) pos  on
    // e.g. setB Counts[0] stores t  number of users who have t  0 b  set  n t  r bytes
    pr vate long[] setB Counts;

    pr vate f nal long[] hash;
    pr vate f nal byte[] b s;

    pr vate f nal  nt hashMask;

    HashTable( nt s ze) {
      t .hash = new long[s ze];
      t .b s = new byte[s ze];
      t .hashMask = s ze - 1;
      t .numUsers nTable = 0;
      t .setB Counts = new long[BYTE_W DTH];
    }

    protected  nt hashS ze() {
      return hash.length;
    }

    //  f   want to decrease t  number of users  n t  table,   can delete as many users
    // as t  table returns, by call ng f lterTableAndCountVal d ems.
    publ c vo d setCountOfNumUsersW hNoB sSet() {
       nt count = 0;
      for ( nt   = 0;   < hash.length;  ++) {
         f ((hash[ ] > 0) && (b s[ ] == 0)) {
          count++;
        }
      }

      numUsersW hNoB sSet = count;
    }

    publ c vo d setSetB Counts() {
      long[] counts = new long[BYTE_W DTH];
      for ( nt   = 0;   < hash.length;  ++) {
         f (hash[ ] > 0) {
           nt tempB s = b s[ ] & 0xff;
           nt curB Pos = 0;
          wh le (tempB s != 0) {
             f ((tempB s & 1) != 0) {
              counts[curB Pos]++;
            }
            tempB s = tempB s >>> 1;
            curB Pos++;
          }
        }
      }
      setB Counts = counts;
    }
  }

  publ c stat c f nal  nt ANT SOC AL_B T = 1;
  publ c stat c f nal  nt OFFENS VE_B T = 1 << 1;
  publ c stat c f nal  nt NSFW_B T = 1 << 2;
  publ c stat c f nal  nt  S_PROTECTED_B T = 1 << 3;

  publ c long getLastRecordT  stamp() {
    return t .lastRecordT  stamp;
  }

  publ c vo d setLastRecordT  stamp(long lastRecordT  stamp) {
    t .lastRecordT  stamp = lastRecordT  stamp;
  }

  publ c vo d setOffens ve(long user D, boolean offens ve) {
    set(user D, OFFENS VE_B T, offens ve);
  }

  publ c vo d setAnt soc al(long user D, boolean ant soc al) {
    set(user D, ANT SOC AL_B T, ant soc al);
  }

  publ c vo d setNSFW(long user D, boolean nsfw) {
    set(user D, NSFW_B T, nsfw);
  }

  publ c vo d set sProtected(long user D, boolean  sProtected) {
    set(user D,  S_PROTECTED_B T,  sProtected);
  }

  /**
   * Adds t  g ven user update to t  table.
   */
  publ c boolean  ndexUserUpdate(UserUpdatesC cker c cker, UserUpdate userUpdate) {
     f (c cker.sk pUserUpdate(userUpdate)) {
      return false;
    }

    sw ch (userUpdate.updateType) {
      case ANT SOC AL:
        setAnt soc al(userUpdate.tw terUser D, userUpdate.updateValue != 0);
        break;
      case NSFW:
        setNSFW(userUpdate.tw terUser D, userUpdate.updateValue != 0);
        break;
      case OFFENS VE:
        setOffens ve(userUpdate.tw terUser D, userUpdate.updateValue != 0);
        break;
      case PROTECTED:
        set sProtected(userUpdate.tw terUser D, userUpdate.updateValue != 0);
        break;
      default:
        return false;
    }

    return true;
  }

  pr vate f nal Atom cReference<HashTable> hashTable = new Atom cReference<>();

  pr vate  nt hashCode(long user D) {
    return ( nt) GeneralLongHashFunct on.hash(user D);
  }

  /**
   * Returns an  erator for user  Ds that have at least one of t  b s set.
   */
  publ c  erator<Long> getFlaggedUser d erator() {
    HashTable table = hashTable.get();

    f nal long[] currUser dTable = table.hash;
    f nal byte[] currB sTable = table.b s;
    return new  erator<Long>() {
      pr vate  nt  ndex = f ndNext(0);

      pr vate  nt f ndNext( nt  ndex) {
         nt start ng ndex =  ndex;
        wh le (start ng ndex < currUser dTable.length) {
           f (currUser dTable[start ng ndex] != 0 && currB sTable[start ng ndex] != 0) {
            break;
          }
          ++start ng ndex;
        }
        return start ng ndex;
      }

      @Overr de
      publ c boolean hasNext() {
        return  ndex < currUser dTable.length;
      }

      @Overr de
      publ c Long next() {
        Long r = currUser dTable[ ndex];
         ndex = f ndNext( ndex + 1);
        return r;
      }

      @Overr de
      publ c vo d remove() {
        throw new UnsupportedOperat onExcept on();
      }
    };
  }

  /**
   * Constructs an UserUpdatesTable w h an g ven HashTable  nstance.
   * Use <code>use dF lter</code> as a Pred cate that returns true for t  ele nts
   * needed to be kept  n t  table.
   * Use shouldRehash to force a rehas ng on t  g ven HashTable.
   */
  pr vate UserTable(HashTable hashTable, Pred cate<Long> user dF lter,
                    boolean shouldRehash) {

    Precond  ons.c ckNotNull(user dF lter);

    t .hashTable.set(hashTable);
    t .user dF lter = user dF lter;

    exportUserUpdatesTableStats();

    LOG. nfo("User table num users: {}. Users w h no b s set: {}. "
            + "Ant soc al users: {}. Offens ve users: {}. Nsfw users: {}.  sProtected users: {}.",
        t .getNumUsers nTable(),
        t .getNumUsersW hNoB sSet(),
        t .getSetB Count(ANT SOC AL_B T),
        t .getSetB Count(OFFENS VE_B T),
        t .getSetB Count(NSFW_B T),
        t .getSetB Count( S_PROTECTED_B T));

     f (shouldRehash) {
       nt f lteredTableS ze = f lterTableAndCountVal d ems();
      // Hav ng exactly 100% usage can  mpact lookup. Ma nta n t  table at under 50% usage.
       nt newTableCapac y = computeDes redHashTableCapac y(f lteredTableS ze * 2);

      rehash(newTableCapac y);

      LOG. nfo("User table num users after rehash: {}. Users w h no b s set: {}. "
              + "Ant soc al users: {}. Offens ve users: {}. Nsfw users: {}.  sProtected users: {}.",
          t .getNumUsers nTable(),
          t .getNumUsersW hNoB sSet(),
          t .getSetB Count(ANT SOC AL_B T),
          t .getSetB Count(OFFENS VE_B T),
          t .getSetB Count(NSFW_B T),
          t .getSetB Count( S_PROTECTED_B T));
    }
  }

  pr vate UserTable( nt  n  alS ze, Pred cate<Long> user dF lter) {
    t (new HashTable(computeDes redHashTableCapac y( n  alS ze)), user dF lter, false);
  }

  @V s bleForTest ng
  publ c UserTable( nt  n  alS ze) {
    t ( n  alS ze, user d -> true);
  }

  publ c stat c UserTable
    newTableW hDefaultCapac yAndPred cate(Pred cate<Long> user dF lter) {

    return new UserTable(DEFAULT_ N T AL_CAPAC TY, user dF lter);
  }

  publ c stat c UserTable newTableNonF lteredW hDefaultCapac y() {
    return newTableW hDefaultCapac yAndPred cate(user d -> true);
  }

  pr vate vo d exportUserUpdatesTableStats() {
    userTableS ze = SearchLongGauge.export(USER_TABLE_S ZE);
    userTableCapac y = SearchLongGauge.export(USER_TABLE_CAPAC TY);
    userTableNumUsersW hNoB sSet = SearchLongGauge.export(
        USER_NUM_USERS_W TH_NO_B TS_SET
    );
    userTableAnt soc alUsers = SearchLongGauge.export(USER_TABLE_ANT SOC AL_USERS);
    userTableOffens veUsers = SearchLongGauge.export(USER_TABLE_OFFENS VE_USERS);
    userTableNsfwUsers = SearchLongGauge.export(USER_TABLE_NSFW_USERS);
    userTable sProtectedUsers = SearchLongGauge.export(USER_TABLE_ S_PROTECTED_USERS);

    LOG. nfo(
        "Export ng stats for user table. Start ng w h numUsers nTable={}, usersW hZeroB s={}, "
            + "ant soc alUsers={}, offens veUsers={}, nsfwUsers={},  sProtectedUsers={}.",
        getNumUsers nTable(),
        getNumUsersW hNoB sSet(),
        getSetB Count(ANT SOC AL_B T),
        getSetB Count(OFFENS VE_B T),
        getSetB Count(NSFW_B T),
        getSetB Count( S_PROTECTED_B T));
    updateStats();
  }

  pr vate vo d updateStats() {
    HashTable table = t .hashTable.get();
    userTableS ze.set(table.numUsers nTable);
    userTableNumUsersW hNoB sSet.set(table.numUsersW hNoB sSet);
    userTableCapac y.set(table.hashS ze());
    userTableAnt soc alUsers.set(getSetB Count(ANT SOC AL_B T));
    userTableOffens veUsers.set(getSetB Count(OFFENS VE_B T));
    userTableNsfwUsers.set(getSetB Count(NSFW_B T));
    userTable sProtectedUsers.set(getSetB Count( S_PROTECTED_B T));
  }

  /**
   * Computes t  s ze of t  hashtable as t  f rst po r of two greater than or equal to  n  alS ze
   */
  pr vate stat c  nt computeDes redHashTableCapac y( nt  n  alS ze) {
    long po rOfTwoS ze = 2;
    wh le ( n  alS ze > po rOfTwoS ze) {
      po rOfTwoS ze *= 2;
    }
     f (po rOfTwoS ze >  nteger.MAX_VALUE) {
      LOG.error("Error: po rOfTwoS ze overflo d  nteger.MAX_VALUE!  n  al s ze: " +  n  alS ze);
      po rOfTwoS ze = 1 << 30;  // max po r of 2
    }

    return ( nt) po rOfTwoS ze;
  }

  publ c  nt getNumUsers nTable() {
    return hashTable.get().numUsers nTable;
  }

  /**
   * Get t  number of users who have t  b  set at t  `userStateB ` pos  on
   */
  publ c long getSetB Count( nt userStateB ) {
     nt b  = userStateB ;
     nt b Pos  on = 0;
    wh le (b  != 0 && (b  & 1) == 0) {
      b  = b  >>> 1;
      b Pos  on++;
    }
    return hashTable.get().setB Counts[b Pos  on];
  }

  publ c Pred cate<Long> getUser dF lter() {
    return user dF lter::test;
  }

  /**
   * Updates a user flag  n t  table.
   */
  publ c f nal vo d set(long user D,  nt b , boolean value) {
    //  f user D  s f ltered return  m d ately
     f (!shouldKeepUser(user D)) {
      USER_TABLE_USERS_F LTERED_COUNTER. ncre nt();
      return;
    }

    HashTable table = t .hashTable.get();

     nt hashPos = f ndHashPos  on(table, user D);
    long  em = table.hash[hashPos];
    byte b s = 0;
     nt b sD ff = 0;

     f ( em != 0) {
      byte b sOr g nally = b s = table.b s[hashPos];
       f (value) {
        b s |= b ;
      } else {
        // AND' ng w h t   nverse map clears t  des red b , but
        // doesn't change any of t  ot r b s
        b s &= ~b ;
      }

      // F nd t  changed b s after t  above operat on,    s poss ble that no b   s changed  f
      // t   nput 'b '  s already set/unset  n t  table.
      // S nce b w se operators cannot be d rectly appl ed on Byte, Byte  s promoted  nto  nt to
      // apply t  operators. W n that happens,  f t  most s gn f cant b  of t  Byte  s set,
      // t  promoted  nt has all s gn f cant b s set to 1. 0xff b mask  s appl ed  re to make
      // sure only t  last 8 b s are cons dered.
      b sD ff = (b sOr g nally & 0xff) ^ (b s & 0xff);

       f (b sOr g nally > 0 && b s == 0) {
        table.numUsersW hNoB sSet++;
      } else  f (b sOr g nally == 0 && b s > 0) {
        table.numUsersW hNoB sSet--;
      }
    } else {
       f (!value) {
        // no need to add t  user, s nce all b s would be false anyway
        return;
      }

      // New user str ng.
       f (table.numUsers nTable + 1 >= (table.hashS ze() >> 1)
          && table.hashS ze() != userUpdateTableMaxCapac y) {
         f (2L * (long) table.hashS ze() < userUpdateTableMaxCapac y) {
          rehash(2 * table.hashS ze());
          table = t .hashTable.get();
        } else {
           f (table.hashS ze() < ( nt) userUpdateTableMaxCapac y) {
            rehash(( nt) userUpdateTableMaxCapac y);
            table = t .hashTable.get();
            LOG.warn("User update table s ze reac d  nteger.MAX_VALUE, performance w ll degrade.");
          }
        }

        // Must repeat t  operat on w h t  res zed hashTable.
        hashPos = f ndHashPos  on(table, user D);
      }

       em = user D;
      b s |= b ;
      b sD ff = b  & 0xff;

      table.numUsers nTable++;
    }

    table.hash[hashPos] =  em;
    table.b s[hashPos] = b s;

    // update setB Counts for t  changed b s after apply ng t   nput 'b '
     nt curB sD ffPos = 0;
    wh le (b sD ff != 0) {
       f ((b sD ff & 1) != 0) {
         f (value) {
          table.setB Counts[curB sD ffPos]++;
        } else {
          table.setB Counts[curB sD ffPos]--;
        }
      }
      b sD ff = b sD ff >>> 1;
      curB sD ffPos++;
    }

    updateStats();
  }

  publ c f nal boolean  sSet(long user D,  nt b s) {
    HashTable table = hashTable.get();
     nt hashPos = f ndHashPos  on(table, user D);
    return table.hash[hashPos] != 0 && (table.b s[hashPos] & b s) != 0;
  }

  /**
   * Returns true w n user dF lter cond  on  s be ng  t.
   *  f f lter  s not present returns true
   */
  pr vate boolean shouldKeepUser(long user D) {
    return user dF lter.test(user D);
  }

  pr vate  nt f ndHashPos  on(f nal HashTable table, f nal long user D) {
     nt code = hashCode(user D);
     nt hashPos = code & table.hashMask;

    // Locate user  n hash
    long  em = table.hash[hashPos];

     f ( em != 0 &&  em != user D) {
      // Confl ct: keep search ng d fferent locat ons  n
      // t  hash table.
      f nal  nt  nc = ((code >> 8) + code) | 1;
      do {
        code +=  nc;
        hashPos = code & table.hashMask;
         em = table.hash[hashPos];
      } wh le ( em != 0 &&  em != user D);
    }

    return hashPos;
  }

  /**
   * Appl es t  f lter ng pred cate and returns t  s ze of t  f ltered table.
   */
  pr vate synchron zed  nt f lterTableAndCountVal d ems() {
    f nal HashTable oldTable = t .hashTable.get();
     nt newS ze = 0;

     nt clearNo emSet = 0;
     nt clearNoB sSet = 0;
     nt clearDontKeepUser = 0;

    for ( nt   = 0;   < oldTable.hashS ze();  ++) {
      f nal long  em = oldTable.hash[ ]; // t   s t  user D
      f nal byte b s = oldTable.b s[ ];

      boolean clearSlot = false;
       f ( em == 0) {
        clearSlot = true;
        clearNo emSet++;
      } else  f (b s == 0) {
        clearSlot = true;
        clearNoB sSet++;
      } else  f (!shouldKeepUser( em)) {
        clearSlot = true;
        clearDontKeepUser++;
      }

       f (clearSlot) {
        oldTable.hash[ ] = 0;
        oldTable.b s[ ] = 0;
      } else {
        newS ze += 1;
      }
    }

    oldTable.setCountOfNumUsersW hNoB sSet();
    oldTable.setSetB Counts();

    LOG. nfo("Done f lter ng table: clearNo emSet={}, clearNoB sSet={}, clearDontKeepUser={}",
        clearNo emSet, clearNoB sSet, clearDontKeepUser);

    return newS ze;
  }

  /**
   * Called w n hash  s too small (> 50% occup ed)
   */
  pr vate vo d rehash(f nal  nt newS ze) {
    f nal HashTable oldTable = t .hashTable.get();
    f nal HashTable newTable = new HashTable(newS ze);

    f nal  nt newMask = newTable.hashMask;
    f nal long[] newHash = newTable.hash;
    f nal byte[] newB s = newTable.b s;

    for ( nt   = 0;   < oldTable.hashS ze();  ++) {
      f nal long  em = oldTable.hash[ ];
      f nal byte b s = oldTable.b s[ ];
       f ( em != 0 && b s != 0) {
         nt code = hashCode( em);

         nt hashPos = code & newMask;
        assert hashPos >= 0;
         f (newHash[hashPos] != 0) {
          f nal  nt  nc = ((code >> 8) + code) | 1;
          do {
            code +=  nc;
            hashPos = code & newMask;
          } wh le (newHash[hashPos] != 0);
        }
        newHash[hashPos] =  em;
        newB s[hashPos] = b s;
        newTable.numUsers nTable++;
      }
    }

    newTable.setCountOfNumUsersW hNoB sSet();
    newTable.setSetB Counts();
    t .hashTable.set(newTable);

    updateStats();
  }

  publ c vo d setTable(UserTable newTable) {
    hashTable.set(newTable.hashTable.get());
    updateStats();
  }

  @V s bleForTest ng
  protected  nt getHashTableCapac y() {
    return hashTable.get().hashS ze();
  }

  @V s bleForTest ng
  protected  nt getNumUsersW hNoB sSet() {
    return hashTable.get().numUsersW hNoB sSet;
  }
}
