package com.twittew.seawch.common.wewevance.featuwes;

/**
 * defines w-wewevance wewated c-constants t-that awe used at b-both ingestion t-time and
 * eawwybiwd s-scowing time. 🥺
 */
p-pubwic f-finaw cwass wewevancesignawconstants {
  // usew weputation
  pubwic static finaw byte unset_weputation_sentinew = b-byte.min_vawue;
  pubwic static finaw byte max_weputation = 100;
  p-pubwic static finaw byte min_weputation = 0;
  // b-bewow ovewaww cdf of ~10%, mya defauwt vawue fow nyew usews, 🥺
  // g-given as a goodwiww vawue i-in case it is unset
  p-pubwic static finaw byte goodwiww_weputation = 17;

  // text scowe
  pubwic static finaw byte unset_text_scowe_sentinew = b-byte.min_vawue;
  // woughwy at ovewaww cdf of ~10%, >_< given as a goodwiww vawue i-in case it is unset
  pubwic static f-finaw byte goodwiww_text_scowe = 19;

  p-pwivate w-wewevancesignawconstants() {
  }

  // c-check whethew the specified usew wep v-vawue is vawid
  pubwic static boowean isvawidusewweputation(int u-usewwep) {
    wetuwn usewwep != unset_weputation_sentinew
           && usewwep >= min_weputation
           && usewwep < max_weputation;
  }
}
