package com.twittew.home_mixew.functionaw_component.decowatow.uwt.buiwdew

impowt c-com.twittew.pwoduct_mixew.cowe.pwoduct.guice.scope.pwoductscoped
i-impowt com.twittew.stwingcentew.cwient.extewnawstwingwegistwy
i-impowt javax.inject.inject
i-impowt j-javax.inject.pwovidew
i-impowt javax.inject.singweton

@singweton
c-cwass feedbackstwings @inject() (
  @pwoductscoped e-extewnawstwingwegistwypwovidew: pwovidew[extewnawstwingwegistwy]) {
  pwivate vaw extewnawstwingwegistwy = extewnawstwingwegistwypwovidew.get()

  v-vaw seewessoftenfeedbackstwing =
    extewnawstwingwegistwy.cweatepwodstwing("feedback.seewessoften")
  vaw seewessoftenconfiwmationfeedbackstwing =
    e-extewnawstwingwegistwy.cweatepwodstwing("feedback.seewessoftenconfiwmation")
}
