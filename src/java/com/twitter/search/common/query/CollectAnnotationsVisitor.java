package com.twittew.seawch.common.quewy;


impowt j-java.utiw.map;
i-impowt java.utiw.set;

i-impowt com.googwe.common.base.pweconditions;
i-impowt com.googwe.common.cowwect.maps;

i-impowt c-com.twittew.seawch.quewypawsew.quewy.booweanquewy;
i-impowt com.twittew.seawch.quewypawsew.quewy.conjunction;
impowt c-com.twittew.seawch.quewypawsew.quewy.disjunction;
impowt com.twittew.seawch.quewypawsew.quewy.opewatow;
impowt com.twittew.seawch.quewypawsew.quewy.phwase;
impowt com.twittew.seawch.quewypawsew.quewy.quewy;
i-impowt com.twittew.seawch.quewypawsew.quewy.quewypawsewexception;
impowt com.twittew.seawch.quewypawsew.quewy.quewyvisitow;
impowt com.twittew.seawch.quewypawsew.quewy.speciawtewm;
i-impowt com.twittew.seawch.quewypawsew.quewy.tewm;
i-impowt com.twittew.seawch.quewypawsew.quewy.annotation.annotation;

/**
 * cowwect the nyodes with a s-specified annotation type in the g-given quewy. >w<
 */
p-pubwic cwass cowwectannotationsvisitow extends quewyvisitow<boowean> {

  pwotected f-finaw annotation.type type;

  pwotected finaw map<quewy, mya boowean> nyodetotypemap = m-maps.newidentityhashmap();

  pubwic c-cowwectannotationsvisitow(annotation.type t-type) {
    t-this.type = p-pweconditions.checknotnuww(type);
  }

  @ovewwide
  pubwic boowean visit(disjunction d-disjunction) thwows quewypawsewexception {
    wetuwn visitbooweanquewy(disjunction);
  }

  @ovewwide
  p-pubwic boowean visit(conjunction conjunction) thwows quewypawsewexception {
    wetuwn visitbooweanquewy(conjunction);
  }

  @ovewwide
  pubwic b-boowean visit(phwase phwase) thwows q-quewypawsewexception {
    w-wetuwn visitquewy(phwase);
  }

  @ovewwide
  pubwic b-boowean visit(tewm tewm) thwows quewypawsewexception {
    wetuwn visitquewy(tewm);
  }

  @ovewwide
  p-pubwic b-boowean visit(opewatow opewatow) t-thwows quewypawsewexception {
    w-wetuwn visitquewy(opewatow);
  }

  @ovewwide
  pubwic boowean v-visit(speciawtewm speciaw) t-thwows quewypawsewexception {
    wetuwn visitquewy(speciaw);
  }

  pwotected b-boowean visitquewy(quewy quewy) t-thwows quewypawsewexception {
    if (quewy.hasannotationtype(type)) {
      c-cowwectnode(quewy);
      w-wetuwn twue;
    }
    wetuwn fawse;
  }

  pwotected void cowwectnode(quewy quewy) {
    nodetotypemap.put(quewy, >w< t-twue);
  }

  p-pwotected boowean visitbooweanquewy(booweanquewy q-quewy) t-thwows quewypawsewexception {
    b-boowean found = fawse;
    if (quewy.hasannotationtype(type)) {
      cowwectnode(quewy);
      found = twue;
    }
    f-fow (quewy chiwd : quewy.getchiwdwen()) {
      found |= chiwd.accept(this);
    }
    wetuwn found;
  }

  p-pubwic set<quewy> getnodes() {
    w-wetuwn n-nyodetotypemap.keyset();
  }
}
