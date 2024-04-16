package com.tw ter.search.earlyb rd;

 mport com.tw ter.f nagle.thr ft.Thr ftCl entRequest;
 mport com.tw ter.search.common.dark.DarkProxy;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdServ ce;
 mport com.tw ter.ut l.Durat on;

/**
 * Manages a f nagle server underneath, wh ch can be recreated.
 *
 * T  class  s not thread-safe.    s up to t  concrete  mple ntat ons and t  r callers to
 * correctly synchron ze calls to t se  thods (for example, to make sure that t re  s no race
 * cond  on  f startProduct onF nagleServer() and stopProduct onF nagleServer() are called
 * concurrently from two d fferent threads).
 */
publ c  nterface Earlyb rdF nagleServerManager {
  /**
   * Determ nes  f t  warm up f nagle server  s currently runn ng
   */
  boolean  sWarmUpServerRunn ng();

  /**
   * Starts up t  warm up f nagle server on t  g ven port.
   */
  vo d startWarmUpF nagleServer(
      Earlyb rdServ ce.Serv ce face serv ce face,
      Str ng serv ceNa ,
       nt port);

  /**
   * Stops t  warm up f nagle server, after wa  ng for at most t  g ven amount of t  .
   */
  vo d stopWarmUpF nagleServer(Durat on serverCloseWa T  ) throws  nterruptedExcept on;

  /**
   * Determ nes  f t  product on f nagle server  s currently runn ng.
   */
  boolean  sProduct onServerRunn ng();

  /**
   * Starts up t  product on f nagle server on t  g ven port.
   */
  vo d startProduct onF nagleServer(
      DarkProxy<Thr ftCl entRequest, byte[]> darkProxy,
      Earlyb rdServ ce.Serv ce face serv ce face,
      Str ng serv ceNa ,
       nt port);

  /**
   * Stops t  product on f nagle server after wa  ng for at most t  g ven amount of t  .
   */
  vo d stopProduct onF nagleServer(Durat on serverCloseWa T  ) throws  nterruptedExcept on;
}
