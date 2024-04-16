package com.tw ter.search.common.query;

 mport com.tw ter.search.queryparser.query.annotat on.Annotat on;


/**
 * A v s or that collects t  nodes that have :v annotat on
 */
publ c class CollectVar antV s or extends CollectAnnotat onsV s or {
  publ c CollectVar antV s or() {
    super(Annotat on.Type.VAR ANT);
  }
}
