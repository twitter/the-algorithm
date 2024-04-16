package com.tw ter.search.earlyb rd.except on;

/**
 * General Earlyb rd except on class to use  nstead of t  Java except on class.
 */
publ c class Earlyb rdExcept on extends Except on {
  publ c Earlyb rdExcept on(Throwable cause) {
    super(cause);
  }

  publ c Earlyb rdExcept on(Str ng  ssage) {
    super( ssage);
  }

  publ c Earlyb rdExcept on(Str ng  ssage, Throwable cause) {
    super( ssage, cause);
  }
}
