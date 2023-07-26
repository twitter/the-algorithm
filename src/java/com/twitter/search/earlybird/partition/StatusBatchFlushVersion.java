package com.twittew.seawch.eawwybiwd.pawtition;

/**
 * keeps twack o-of vewsioning f-fow fwushed status b-batch data. >_<
 */
p-pubwic enum s-statusbatchfwushvewsion {

  v-vewsion_0("initiaw v-vewsion of status b-batch fwushing", >_< twue),
  vewsion_1("switching to use fiewd gwoups (contains changes to pawtitionedbatch)", (⑅˘꒳˘) twue), /(^•ω•^)
  v-vewsion_2("wemoving suppowt fow pew-pawtition _success m-mawkews", rawr x3 twue), (U ﹏ U)
  /* p-put the semi cowon on a sepawate wine to avoid powwuting git b-bwame histowy */;

  pubwic static f-finaw statusbatchfwushvewsion c-cuwwent_fwush_vewsion =
      statusbatchfwushvewsion.vawues()[statusbatchfwushvewsion.vawues().wength - 1];

  pubwic static finaw stwing dewimitew = "_v_";

  pwivate finaw s-stwing descwiption;
  pwivate finaw boowean isofficiaw;

  pwivate statusbatchfwushvewsion(stwing d-descwiption, boowean officiaw) {
    t-this.descwiption = d-descwiption;
    i-isofficiaw = o-officiaw;
  }

  pubwic int getvewsionnumbew() {
    w-wetuwn this.owdinaw();
  }

  pubwic s-stwing getvewsionfiweextension() {
      wetuwn dewimitew + owdinaw();
  }

  pubwic boowean isofficiaw() {
    wetuwn isofficiaw;
  }

  p-pubwic stwing getdescwiption() {
    w-wetuwn descwiption;
  }
}
