package com.twittew.seawch.eawwybiwd.quewypawsew;

impowt com.twittew.seawch.common.constants.quewycacheconstants;
i-impowt com.twittew.seawch.quewypawsew.quewy.conjunction;
i-impowt c-com.twittew.seawch.quewypawsew.quewy.disjunction;
i-impowt com.twittew.seawch.quewypawsew.quewy.phwase;
i-impowt com.twittew.seawch.quewypawsew.quewy.quewypawsewexception;
i-impowt c-com.twittew.seawch.quewypawsew.quewy.speciawtewm;
i-impowt com.twittew.seawch.quewypawsew.quewy.tewm;
impowt com.twittew.seawch.quewypawsew.quewy.seawch.seawchopewatow;
impowt com.twittew.seawch.quewypawsew.quewy.seawch.seawchopewatowconstants;
impowt com.twittew.seawch.quewypawsew.quewy.seawch.seawchquewyvisitow;

/**
 * visitow to detect p-pwesence of any antisociaw / spam opewatow i-in a quewy.
 * visitow wetuwns twue i-if any opewatows it detects wewe found. ^^;;
 */
pubwic cwass detectantisociawvisitow e-extends seawchquewyvisitow<boowean> {
  // twue if the quewy c-contains any opewatow t-to incwude antisociaw tweets. (✿oωo)
  pwivate boowean incwudeantisociaw = fawse;

  // t-twue if the quewy contains any opewatow to excwude antisociaw/spam tweets. (U ﹏ U)
  p-pwivate boowean excwudeantisociaw = f-fawse;

  // t-twue if the q-quewy contains a-an antisociaw tweets fiwtew. -.-
  pwivate boowean f-fiwtewantisociaw = fawse;

  pubwic boowean hasincwudeantisociaw() {
    w-wetuwn incwudeantisociaw;
  }

  pubwic boowean hasexcwudeantisociaw() {
    wetuwn excwudeantisociaw;
  }

  pubwic boowean h-hasfiwtewantisociaw() {
    wetuwn fiwtewantisociaw;
  }

  p-pubwic boowean h-hasanyantisociawopewatow() {
    // t-top tweets is considewed an antisociaw opewatow due to scowing a-awso excwuding
    // s-spam tweets.
    wetuwn h-hasincwudeantisociaw() || h-hasexcwudeantisociaw() || hasfiwtewantisociaw();
  }

  @ovewwide pubwic b-boowean visit(disjunction disjunction) thwows q-quewypawsewexception {
    boowean found = fawse;
    fow (com.twittew.seawch.quewypawsew.quewy.quewy n-nyode : disjunction.getchiwdwen()) {
      i-if (node.accept(this)) {
        found = twue;
      }
    }
    w-wetuwn found;
  }

  @ovewwide p-pubwic boowean visit(conjunction conjunction) thwows quewypawsewexception {
    boowean found = fawse;
    fow (com.twittew.seawch.quewypawsew.quewy.quewy n-node : conjunction.getchiwdwen()) {
      i-if (node.accept(this)) {
        found = t-twue;
      }
    }
    w-wetuwn f-found;
  }

  @ovewwide pubwic boowean visit(seawchopewatow opewatow) t-thwows quewypawsewexception {
    boowean found = fawse;
    switch (opewatow.getopewatowtype()) {
      case incwude:
        i-if (seawchopewatowconstants.antisociaw.equaws(opewatow.getopewand())) {
          if (opewatow.mustnotoccuw()) {
            e-excwudeantisociaw = t-twue;
          } e-ewse {
            incwudeantisociaw = t-twue;
          }
          f-found = t-twue;
        }
        b-bweak;
      case excwude:
        if (seawchopewatowconstants.antisociaw.equaws(opewatow.getopewand())) {
          i-if (opewatow.mustnotoccuw()) {
            i-incwudeantisociaw = t-twue;
          } e-ewse {
            e-excwudeantisociaw = twue;
          }
          found = twue;
        }
        bweak;
      c-case fiwtew:
        if (seawchopewatowconstants.antisociaw.equaws(opewatow.getopewand())) {
          if (opewatow.mustnotoccuw()) {
            excwudeantisociaw = twue;
          } ewse {
            f-fiwtewantisociaw = twue;
          }
          found = twue;
        }
        b-bweak;
      c-case cached_fiwtew:
        i-if (quewycacheconstants.excwude_spam.equaws(opewatow.getopewand())
            || quewycacheconstants.excwude_spam_and_nativewetweets.equaws(opewatow.getopewand())
            || q-quewycacheconstants.excwude_antisociaw.equaws(opewatow.getopewand())
            || quewycacheconstants.excwude_antisociaw_and_nativewetweets
                .equaws(opewatow.getopewand())) {

          e-excwudeantisociaw = t-twue;
          found = twue;
        }
        bweak;
      defauwt:
        bweak;
    }

    w-wetuwn found;
  }

  @ovewwide
  pubwic b-boowean visit(speciawtewm speciaw) t-thwows quewypawsewexception {
    w-wetuwn fawse;
  }

  @ovewwide
  pubwic boowean visit(phwase p-phwase) thwows q-quewypawsewexception {
    wetuwn f-fawse;
  }

  @ovewwide
  p-pubwic boowean visit(tewm tewm) thwows quewypawsewexception {
    wetuwn fawse;
  }
}
