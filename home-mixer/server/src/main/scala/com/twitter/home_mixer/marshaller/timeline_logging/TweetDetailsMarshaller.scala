package com.tw ter.ho _m xer.marshaller.t  l ne_logg ng

 mport com.tw ter.ho _m xer.model.Ho Features.Author dFeature
 mport com.tw ter.ho _m xer.model.Ho Features.S ceT et dFeature
 mport com.tw ter.ho _m xer.model.Ho Features.S ceUser dFeature
 mport com.tw ter.ho _m xer.model.Ho Features.SuggestTypeFeature
 mport com.tw ter.product_m xer.component_l brary.model.presentat on.urt.Urt emPresentat on
 mport com.tw ter.product_m xer.component_l brary.model.presentat on.urt.UrtModulePresentat on
 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata.GeneralContextTypeMarshaller
 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateW hDeta ls
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.t et.T et em
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Conversat onGeneralContextType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.GeneralContext
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Top cContext
 mport com.tw ter.t  l nes.serv ce.{thr ftscala => tst}
 mport com.tw ter.t  l nes.t  l ne_logg ng.{thr ftscala => thr ftlog}

object T etDeta lsMarshaller {

  pr vate val generalContextTypeMarshaller = new GeneralContextTypeMarshaller()

  def apply(entry: T et em, cand date: Cand dateW hDeta ls): thr ftlog.T etDeta ls = {
    val soc alContext = cand date.presentat on.flatMap {
      case _ @Urt emPresentat on(t  l ne em: T et em, _) => t  l ne em.soc alContext
      case _ @UrtModulePresentat on(t  l neModule) =>
        t  l neModule. ems. ad. em match {
          case t  l ne em: T et em => t  l ne em.soc alContext
          case _ => So (Conversat onGeneralContextType)
        }
    }

    val soc alContextType = soc alContext match {
      case So (GeneralContext(contextType, _, _, _, _)) =>
        So (generalContextTypeMarshaller(contextType).value.toShort)
      case So (Top cContext(_, _)) => So (tst.ContextType.Top c.value.toShort)
      case _ => None
    }

    thr ftlog.T etDeta ls(
      s ceT et d = cand date.features.getOrElse(S ceT et dFeature, None),
      soc alContextType = soc alContextType,
      suggestType = cand date.features.getOrElse(SuggestTypeFeature, None).map(_.na ),
      author d = cand date.features.getOrElse(Author dFeature, None),
      s ceAuthor d = cand date.features.getOrElse(S ceUser dFeature, None)
    )
  }
}
