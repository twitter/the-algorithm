package com.twittew.pwoduct_mixew.cowe.moduwe

impowt c-com.twittew.finatwa.thwift.exceptions.exceptionmappew
i-impowt c-com.twittew.inject.wogging
i-impowt c-com.twittew.utiw.futuwe
i-impowt j-javax.inject.singweton
i-impowt scawa.utiw.contwow.nonfataw

/**
 * simiwaw to [[com.twittew.finatwa.thwift.intewnaw.exceptions.thwowabweexceptionmappew]]
 *
 * but this one awso wogs the exceptions. 😳
 */
@singweton
c-cwass woggingthwowabweexceptionmappew extends exceptionmappew[thwowabwe, XD n-nyothing] with wogging {

  ovewwide d-def handweexception(thwowabwe: thwowabwe): futuwe[nothing] = {
    ewwow("unhandwed e-exception", :3 thwowabwe)

    t-thwowabwe m-match {
      case nyonfataw(e) => futuwe.exception(e)
    }
  }
}
