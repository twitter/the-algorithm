package com.tw ter.product_m xer.component_l brary.exper  nts. tr cs

 mport com.tw ter.product_m xer.component_l brary.exper  nts. tr cs.PlaceholderConf g.PlaceholdersMap
 mport java. o.F le
 mport java. o.Pr ntWr er
 mport scala.collect on. mmutable.L stSet
 mport scala. o.S ce
 mport scopt.Opt onParser

pr vate case class  tr cTemplateCL Conf g(
  // default values requ red for Opt onParser
  templateF leNa : Str ng = null,
  outputF leNa : Str ng = null,
   tr cGroupNa : Str ng = null,
   tr cGroupDesc: Str ng = null,
   tr cGroup d: Opt on[Long] = None,
  absolutePath: Opt on[Str ng] = None)

tra   tr cTemplateCL Runner {
  def templateD r: Str ng
  def placeholders: PlaceholdersMap
  pr vate val ProgramNa  = " tr c Template CL "
  pr vate val Vers onNumber = "1.0"

  pr vate def mkPath(f leNa : Str ng, absolutePath: Opt on[Str ng]): Str ng = {
    val relat veD r = s"$templateD r/$f leNa "
    absolutePath match {
      case So (path) => s"$path/$relat veD r"
      case _ => relat veD r
    }
  }

  def ma n(args: Array[Str ng]): Un  = {
    val parser = new Opt onParser[ tr cTemplateCL Conf g](ProgramNa ) {
       ad(ProgramNa , Vers onNumber)
      // opt on  nvoked by -o or --output
      opt[Str ng]('o', "output")
        .requ red()
        .valueNa ("<f le>")
        .act on((value, conf g) => conf g.copy(outputF leNa  = value))
        .text("output CSV f le w h  nterpolated l nes")
      // opt on  nvoked by -t or --template
      opt[Str ng]('t', "template")
        .requ red()
        .valueNa ("<f le>")
        .act on((value, conf g) => conf g.copy(templateF leNa  = value))
        .text(
          s" nput template f le (see README.md for template format). Path  s relat ve to $templateD r.")
      // opt on  nvoked by -n or --na 
      opt[Str ng]('n', "na ")
        .requ red()
        .valueNa ("<groupNa >")
        .act on((value, conf g) => conf g.copy( tr cGroupNa  = value))
        .text(" tr c group na ")
      // opt on  nvoked by -d or --descr pt on
      opt[Str ng]('d', "descr pt on")
        .requ red()
        .valueNa ("<groupDescr pt on>")
        .act on((value, conf g) => conf g.copy( tr cGroupDesc = value))
        .text(" tr c group descr pt on")
      // opt on  nvoked by -- d
      opt[Long](" d")
        .opt onal()
        .valueNa ("<group d>")
        .act on((value, conf g) => conf g.copy( tr cGroup d = So (value)))
        .text(" tr c group  D ( tr c MUST be created  n go/ddg)")
      // opt on  nvoked by -p or --path
      opt[Str ng]('p', "path")
        .opt onal()
        .valueNa ("<d rectory>")
        .act on((value, conf g) => conf g.copy(absolutePath = So (value)))
        .text(s"absolute path po nt ng to t  $templateD r. Requ red by bazel")
    }

    parser.parse(args,  tr cTemplateCL Conf g()) match {
      case So (conf g) =>
        val templateL nes =
          S ce.fromF le(mkPath(conf g.templateF leNa , conf g.absolutePath)).getL nes.toL st
        val  nterpolatedL nes = templateL nes
          .f lter(!_.startsW h("#")).flatMap( tr cTemplates. nterpolate(_, placeholders))
        val wr er = new Pr ntWr er(new F le(mkPath(conf g.outputF leNa , conf g.absolutePath)))
        val  tr cs =  nterpolatedL nes.map( tr c.fromL ne)
        pr ntln(s"${ tr cs.s ze}  tr c def n  ons found  n template f le.")
        val dup tr cs =  tr cs.groupBy( dent y).collect {
          case (dup, lst)  f lst.lengthCompare(1) > 0 => dup
        }
        pr ntln(s"\nWARN NG: ${dup tr cs.s ze} Dupl cate  tr c def n  on(s)\n$dup tr cs\n")
        val  tr cGroup =  tr cGroup(
          conf g. tr cGroup d,
          conf g. tr cGroupNa ,
          conf g. tr cGroupDesc,
           tr cs.to[L stSet])
        pr ntln(s"${ tr cGroup.un que tr cNa s.s ze} un que DDG  tr cs w h " +
          s"${ tr cGroup. tr cs.s ze}  tr c def n  ons  n '${ tr cGroup.na }'  tr c group.")
        wr er.wr e( tr cGroup.toCsv)
        wr er.close()
      case _ =>
      // argu nts are bad, error  ssage w ll have been d splayed
    }
  }
}
