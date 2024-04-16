package com.tw ter.search.common.ut l.ml.pred ct on_eng ne;

/**
 * A bu lder  nterface to bu ld a L ght  ghtL nearModel.
 */
publ c  nterface ModelBu lder {
  /**
   * parses a l ne of t  model f le and updates t  bu ld state
   */
  ModelBu lder parseL ne(Str ng l ne);

  /**
   * bu lds t  model
   */
  L ght  ghtL nearModel bu ld();
}
