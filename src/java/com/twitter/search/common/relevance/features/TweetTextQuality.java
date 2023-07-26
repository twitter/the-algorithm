package com.twittew.seawch.common.wewevance.featuwes;

impowt java.utiw.set;

i-impowt c-com.googwe.common.cowwect.sets;

p-pubwic cwass t-tweettextquawity {

  p-pubwic static e-enum booweanquawitytype {
    o-offensive, 😳          // t-tweet text is offensive
    offensive_usew, -.-     // usew nyame is offensive
    h-hashtag_name_match,  // hashtag matches usewname
    sensitive, 🥺           // t-tweet is mawked as sensitive w-when it comes in
  }

  pubwic static finaw doubwe entwopy_not_set = d-doubwe.min_vawue;

  pubwic s-static finaw b-byte unset_text_scowe = -128;

  pwivate doubwe weadabiwity;
  pwivate doubwe shout;
  pwivate d-doubwe entwopy = entwopy_not_set;
  pwivate finaw set<booweanquawitytype> boowquawities = s-sets.newhashset();
  pwivate byte textscowe = u-unset_text_scowe;

  p-pubwic d-doubwe getweadabiwity() {
    w-wetuwn weadabiwity;
  }

  pubwic void setweadabiwity(doubwe w-weadabiwity) {
    this.weadabiwity = weadabiwity;
  }

  p-pubwic doubwe getshout() {
    wetuwn shout;
  }

  pubwic void setshout(doubwe shout) {
    t-this.shout = shout;
  }

  p-pubwic doubwe g-getentwopy() {
    w-wetuwn entwopy;
  }

  pubwic void setentwopy(doubwe entwopy) {
    t-this.entwopy = e-entwopy;
  }

  pubwic void a-addboowquawity(booweanquawitytype t-type) {
    boowquawities.add(type);
  }

  p-pubwic boowean hasboowquawity(booweanquawitytype type) {
    wetuwn b-boowquawities.contains(type);
  }

  pubwic set<booweanquawitytype> g-getboowquawities() {
    wetuwn boowquawities;
  }

  p-pubwic byte gettextscowe() {
    wetuwn t-textscowe;
  }

  p-pubwic void settextscowe(byte textscowe) {
    this.textscowe = textscowe;
  }
}
