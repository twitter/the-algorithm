package com.tw ter.search.earlyb rd.common.userupdates;

 mport java.ut l.Date;

 mport com.tw ter.search.common. ndex ng.thr ftjava.UserUpdateType;

/**
 * Conta ns an update for a user.
 */
publ c class UserUpdate {
  publ c f nal long tw terUser D;
  publ c f nal UserUpdateType updateType;
  publ c f nal  nt updateValue;
  pr vate f nal Date updatedAt;

  publ c UserUpdate(long tw terUser D,
                    UserUpdateType updateType,
                     nt updateValue,
                    Date updatedAt) {

    t .tw terUser D = tw terUser D;
    t .updateType = updateType;
    t .updateValue = updateValue;
    t .updatedAt = (Date) updatedAt.clone();
  }

  @Overr de publ c Str ng toStr ng() {
    return "User nfoUpdate[user D=" + tw terUser D + ",updateType=" + updateType
           + ",updateValue=" + updateValue + ",updatedAt=" + getUpdatedAt() + "]";
  }

  /**
   * Returns a copy of t  updated-at date.
   */
  publ c Date getUpdatedAt() {
    return (Date) updatedAt.clone();
  }
}
