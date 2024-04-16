package com.tw ter.product_m xer.core.product.gu ce.scope;

 mport stat c java.lang.annotat on.Ele ntType.METHOD;
 mport stat c java.lang.annotat on.Ele ntType.TYPE;
 mport java.lang.annotat on.Retent on;
 mport stat c java.lang.annotat on.Retent onPol cy.RUNT ME;
 mport java.lang.annotat on.Target;

 mport com.google. nject.ScopeAnnotat on;

@Target({ TYPE, METHOD })
@Retent on(RUNT ME)
@ScopeAnnotat on
publ c @ nterface ProductScoped {}
