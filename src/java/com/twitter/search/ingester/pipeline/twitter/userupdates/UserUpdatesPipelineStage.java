package com.tw ter.search. ngester.p pel ne.tw ter.userupdates;

 mport java.ut l.funct on.Suppl er;

 mport org.apac .commons.p pel ne.P pel ne;
 mport org.apac .commons.p pel ne.StageDr ver;
 mport org.apac .commons.p pel ne.StageExcept on;

 mport com.tw ter.search. ngester.p pel ne.tw ter.Tw terBaseStage;
 mport com.tw ter.search. ngester.p pel ne.ut l.P pel neUt l;

/**
 * T  stage  s a sh m for t  UserUpdatesP pel ne.
 *
 * Eventually t  UserUpdatesP pel ne w ll be called d rectly from a Tw terServer, but t  ex sts
 * as a br dge wh le   m grate.
 */
publ c class UserUpdatesP pel neStage extends Tw terBaseStage {
  // T   s 'prod', 'stag ng', or 'stag ng1'.
  pr vate Str ng env ron nt;
  pr vate UserUpdatesP pel ne userUpdatesP pel ne;

  @Overr de
  protected vo d do nnerPreprocess() throws StageExcept on {
    StageDr ver dr ver = ((P pel ne) stageContext).getStageDr ver(t );
    Suppl er<Boolean> booleanSuppl er = () -> dr ver.getState() == StageDr ver.State.RUNN NG;
    try {
      userUpdatesP pel ne = UserUpdatesP pel ne.bu ldP pel ne(
          env ron nt,
          w reModule,
          getStageNa Pref x(),
          booleanSuppl er,
          clock);

    } catch (Except on e) {
      throw new StageExcept on(t , e);
    }
    P pel neUt l.feedStartObjectToStage(t );
  }

  @Overr de
  publ c vo d  nnerProcess(Object obj) throws StageExcept on {
    userUpdatesP pel ne.run();
  }

  @SuppressWarn ngs("unused")  // populated from p pel ne conf g
  publ c vo d setEnv ron nt(Str ng env ron nt) {
    t .env ron nt = env ron nt;
  }

}
