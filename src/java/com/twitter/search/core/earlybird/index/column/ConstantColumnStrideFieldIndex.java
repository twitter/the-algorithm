package com.tw ter.search.core.earlyb rd. ndex.column;

/**
 * A ColumnStr deF eld ndex  mple ntat on that always returns t  sa  value.
 */
publ c class ConstantColumnStr deF eld ndex extends ColumnStr deF eld ndex {
  pr vate f nal long defaultValue;

  publ c ConstantColumnStr deF eld ndex(Str ng na , long defaultValue) {
    super(na );
    t .defaultValue = defaultValue;
  }

  @Overr de
  publ c long get( nt doc D) {
    return defaultValue;
  }
}
