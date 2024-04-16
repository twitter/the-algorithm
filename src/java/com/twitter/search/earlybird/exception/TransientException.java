package com.tw ter.search.earlyb rd.except on;

publ c class Trans entExcept on extends Except on {
  publ c Trans entExcept on(Throwable t) {
    super(t);
  }

  publ c Trans entExcept on(Str ng  ssage, Throwable cause) {
    super( ssage, cause);
  }

  publ c Trans entExcept on(Str ng  ssage) {
    super( ssage);
  }
}
