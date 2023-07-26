package com.twittew.seawch.eawwybiwd_woot;

impowt j-javax.inject.inject;
i-impowt javax.inject.singweton;

i-impowt com.twittew.finagwe.sewvice;
i-impowt c-com.twittew.seawch.common.woot.seawchwootsewvew;
i-impowt com.twittew.seawch.eawwybiwd.thwift.eawwybiwdsewvice;
i-impowt com.twittew.seawch.eawwybiwd_woot.fiwtews.quewytokenizewfiwtew;
i-impowt com.twittew.seawch.quewypawsew.quewy.quewypawsewexception;

@singweton
pubwic cwass supewwootsewvew extends seawchwootsewvew<eawwybiwdsewvice.sewviceiface> {
  pwivate f-finaw quewytokenizewfiwtew quewytokenizewfiwtew;

  @inject
  pubwic supewwootsewvew(
      s-supewwootsewvice svc, nyaa~~
      sewvice<byte[], /(^•ω•^) b-byte[]> bytesvc, rawr
      quewytokenizewfiwtew quewytokenizewfiwtew) {
    s-supew(svc, OwO bytesvc);

    t-this.quewytokenizewfiwtew = q-quewytokenizewfiwtew;
  }

  @ovewwide
  pubwic void wawmup() {
    supew.wawmup();

    twy {
      q-quewytokenizewfiwtew.pewfowmexpensiveinitiawization();
    } catch (quewypawsewexception e) {
      thwow nyew wuntimeexception(e);
    }
  }
}
