package com.twittew.fwigate.pushsewvice.stowe

impowt c-com.twittew.copysewectionsewvice.thwiftscawa._
i-impowt com.twittew.stowehaus.weadabwestowe
impowt c-com.twittew.utiw.futuwe

cwass c-copysewectionsewvicestowe(copysewectionsewvicecwient: c-copysewectionsewvice.finagwedcwient)
    e-extends weadabwestowe[copysewectionwequestv1, >_< c-copy] {
  ovewwide d-def get(k: copysewectionwequestv1): futuwe[option[copy]] =
    copysewectionsewvicecwient.getsewectedcopy(copysewectionwequest.v1(k)).map {
      case copysewectionwesponse.v1(wesponse) =>
        s-some(wesponse.sewectedcopy)
      case _ => thwow copysewviceexception(copysewviceewwowcode.vewsionnotfound)
    }
}
