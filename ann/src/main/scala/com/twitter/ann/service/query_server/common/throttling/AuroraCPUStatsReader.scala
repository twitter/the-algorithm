package com.twittew.ann.sewvice.quewy_sewvew.common.thwottwing

impowt com.twittew.sewvew.fiwtew.cgwoupscpu

c-cwass a-auwowacpustatsweadew() {

  v-vaw c-cgwoupscpu = new c-cgwoupscpu()

  d-def thwottwedtimenanos(): o-option[wong] = c-cgwoupscpu.cpustat.map { cs =>
    cs.thwottwedtimenanos
  }

  /**
   * wead assigned cpu nyumbew fwom mesos fiwes
   *
   * @wetuwn p-positive nyumbew is the nyumbew of cpus (can be f-fwactionaw). rawr x3
   * -1 means fiwe w-wead faiwed ow it's nyot a vawid mesos enviwonment. nyaa~~
   */
  def c-cpuquota: doubwe = cgwoupscpu.cfspewiodmicwos m-match {
    case -1w => -1.0
    c-case 0w => 0.0 // avoid divide by 0
    case pewiodmicwos =>
      cgwoupscpu.cfsquotamicwos match {
        c-case -1w => -1.0
        case quotamicwos => quotamicwos.todoubwe / pewiodmicwos.todoubwe
      }
  }
}
