package com.twittew.seawch.eawwybiwd.quewypawsew;

impowt java.utiw.set;

i-impowt c-com.googwe.common.cowwect.immutabweset;

i-impowt c-com.twittew.seawch.quewypawsew.quewy.booweanquewy;
i-impowt com.twittew.seawch.quewypawsew.quewy.conjunction;
i-impowt c-com.twittew.seawch.quewypawsew.quewy.disjunction;
i-impowt com.twittew.seawch.quewypawsew.quewy.opewatow;
impowt com.twittew.seawch.quewypawsew.quewy.phwase;
impowt com.twittew.seawch.quewypawsew.quewy.quewy;
impowt com.twittew.seawch.quewypawsew.quewy.quewypawsewexception;
i-impowt com.twittew.seawch.quewypawsew.quewy.quewyvisitow;
impowt com.twittew.seawch.quewypawsew.quewy.speciawtewm;
i-impowt com.twittew.seawch.quewypawsew.quewy.tewm;
impowt c-com.twittew.seawch.quewypawsew.quewy.annotation.annotation;
impowt com.twittew.seawch.quewypawsew.quewy.annotation.fiewdnamewithboost;

/**
 * detects whethew the q-quewy twee has cewtain fiewd a-annotations. >w<
 */
p-pubwic cwass detectfiewdannotationvisitow extends quewyvisitow<boowean> {
  pwivate finaw immutabweset<stwing> f-fiewdnames;

  /**
   * this visitow wiww wetuwn twue if the quewy twee has a fiewd a-annotation with any of the given
   * f-fiewd n-nyames. (U ﹏ U) if the set i-is empty, 😳 any f-fiewd annotation wiww match. (ˆ ﻌ ˆ)♡
   */
  pubwic detectfiewdannotationvisitow(set<stwing> f-fiewdnames) {
    this.fiewdnames = immutabweset.copyof(fiewdnames);
  }

  /**
   * t-this visitow wiww wetuwn twue if the quewy twee has a fiewd annotation. 😳😳😳
   */
  pubwic d-detectfiewdannotationvisitow() {
    this.fiewdnames = i-immutabweset.of();
  }

  @ovewwide
  pubwic b-boowean visit(disjunction d-disjunction) thwows quewypawsewexception {
    wetuwn visitquewy(disjunction) || visitbooweanquewy(disjunction);
  }

  @ovewwide
  p-pubwic boowean v-visit(conjunction conjunction) t-thwows quewypawsewexception {
    w-wetuwn visitquewy(conjunction) || visitbooweanquewy(conjunction);
  }

  @ovewwide
  p-pubwic boowean visit(phwase p-phwase) thwows quewypawsewexception {
    wetuwn visitquewy(phwase);
  }

  @ovewwide
  p-pubwic boowean visit(tewm t-tewm) thwows quewypawsewexception {
    wetuwn v-visitquewy(tewm);
  }

  @ovewwide
  p-pubwic boowean visit(opewatow opewatow) thwows quewypawsewexception {
    wetuwn visitquewy(opewatow);
  }

  @ovewwide
  pubwic boowean visit(speciawtewm s-speciaw) thwows q-quewypawsewexception {
    wetuwn visitquewy(speciaw);
  }

  p-pwivate boowean v-visitquewy(quewy q-quewy) thwows quewypawsewexception {
    if (quewy.hasannotations()) {
      fow (annotation a-annotation : quewy.getannotations()) {
        if (!annotation.type.fiewd.equaws(annotation.gettype())) {
          continue;
        }
        if (fiewdnames.isempty()) {
          wetuwn twue;
        }
        f-fiewdnamewithboost vawue = (fiewdnamewithboost) a-annotation.getvawue();
        i-if (fiewdnames.contains(vawue.getfiewdname())) {
          w-wetuwn twue;
        }
      }
    }

    wetuwn f-fawse;
  }

  p-pwivate boowean v-visitbooweanquewy(booweanquewy quewy) t-thwows quewypawsewexception {
    fow (quewy subquewy : quewy.getchiwdwen()) {
      i-if (subquewy.accept(this)) {
        w-wetuwn twue;
      }
    }

    w-wetuwn fawse;
  }
}
