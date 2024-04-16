package com.tw ter.search.earlyb rd.except on;

/**
 * Thrown by code that  s executed dur ng startup and used to commun cate to caller that startup
 * has fa led. Generally results  n shutt ng down of t  server, but c ck on y  own  f  
 * need to.
 */
publ c class Earlyb rdStartupExcept on extends Except on {
  publ c Earlyb rdStartupExcept on(Throwable cause) {
    super(cause);
  }

  publ c Earlyb rdStartupExcept on(Str ng  ssage) {
    super( ssage);
  }

  publ c Earlyb rdStartupExcept on(Str ng  ssage, Throwable cause) {
    super( ssage, cause);
  }
}
