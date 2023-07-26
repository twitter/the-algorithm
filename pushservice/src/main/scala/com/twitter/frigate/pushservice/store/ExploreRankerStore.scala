package com.twittew.fwigate.pushsewvice.stowe

impowt c-com.twittew.expwowe_wankew.thwiftscawa.expwowewankew
i-impowt c-com.twittew.expwowe_wankew.thwiftscawa.expwowewankewwesponse
i-impowt c-com.twittew.expwowe_wankew.thwiftscawa.expwowewankewwequest
i-impowt com.twittew.stowehaus.weadabwestowe
i-impowt c-com.twittew.utiw.futuwe

/** a stowe fow video tweet wecommendations fwom expwowe
 *
 * @pawam expwowewankewsewvice
 */
c-case cwass expwowewankewstowe(expwowewankewsewvice: expwowewankew.methodpewendpoint)
    extends weadabwestowe[expwowewankewwequest, 😳 e-expwowewankewwesponse] {

  /** method to get video w-wecommendations
   *
   * @pawam wequest expwowe wankew wequest object
   * @wetuwn
   */
  o-ovewwide def get(
    wequest: expwowewankewwequest
  ): f-futuwe[option[expwowewankewwesponse]] = {
    e-expwowewankewsewvice.getwankedwesuwts(wequest).map { wesponse =>
      some(wesponse)
    }
  }
}
