package com.tw ter.servo.cac 

/**
 * Converts all keys to a str ng v a .toStr ng
 */
class ToStr ngKeyTransfor r[K] extends KeyTransfor r[K] {
  overr de def apply(key: K) = key.toStr ng
}

/**
 * Pref xes all keys w h a str ng
 */
class Pref xKeyTransfor r[K](
  pref x: Str ng,
  del m er: Str ng = constants.Colon,
  underly ng: KeyTransfor r[K] = new ToStr ngKeyTransfor r[K]: ToStr ngKeyTransfor r[K])
    extends KeyTransfor r[K] {
  pr vate[t ] val fullPref x = pref x + del m er

  overr de def apply(key: K) = fullPref x + underly ng(key)
}
