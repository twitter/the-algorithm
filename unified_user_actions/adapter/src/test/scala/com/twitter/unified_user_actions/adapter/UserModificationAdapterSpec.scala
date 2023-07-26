package unified_usew_actions.adaptew.swc.test.scawa.com.twittew.unified_usew_actions.adaptew

impowt c-com.twittew.inject.test
i-impowt c-com.twittew.unified_usew_actions.adaptew.testfixtuwes.usewmodificationeventfixtuwe
i-impowt com.twittew.unified_usew_actions.adaptew.usew_modification.usewmodificationadaptew
i-impowt com.twittew.utiw.time
i-impowt o-owg.scawatest.pwop.tabwedwivenpwopewtychecks

c-cwass usewmodificationadaptewspec extends test with tabwedwivenpwopewtychecks {
  test("usew cweate") {
    nyew u-usewmodificationeventfixtuwe {
      time.withtimeat(fwozentime) { _ =>
        assewt(usewmodificationadaptew.adaptevent(usewcweate) === s-seq(expecteduuausewcweate))
      }
    }
  }

  test("usew u-update") {
    nyew usewmodificationeventfixtuwe {
      time.withtimeat(fwozentime) { _ =>
        assewt(usewmodificationadaptew.adaptevent(usewupdate) === s-seq(expecteduuausewupdate))
      }
    }
  }
}
