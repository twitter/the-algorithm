package com.tw ter.ho _m xer.federated

 mport com.tw ter.g zmoduck.{thr ftscala => gd}
 mport com.tw ter.ho _m xer.marshaller.request.Ho M xerRequestUnmarshaller
 mport com.tw ter.ho _m xer.model.request.Ho M xerRequest
 mport com.tw ter.ho _m xer.{thr ftscala => hm}
 mport com.tw ter.product_m xer.core.funct onal_component.conf gap .ParamsBu lder
 mport com.tw ter.product_m xer.core.p pel ne.product.ProductP pel neRequest
 mport com.tw ter.product_m xer.core.p pel ne.product.ProductP pel neResult
 mport com.tw ter.product_m xer.core.product.reg stry.ProductP pel neReg stry
 mport com.tw ter.product_m xer.core.{thr ftscala => pm}
 mport com.tw ter.st ch.Arrow
 mport com.tw ter.st ch.St ch
 mport com.tw ter.strato.callcontext.CallContext
 mport com.tw ter.strato.catalog.Op tadata
 mport com.tw ter.strato.conf g._
 mport com.tw ter.strato.data._
 mport com.tw ter.strato.fed.StratoFed
 mport com.tw ter.strato.generated.cl ent.auth_context.Aud  pCl entColumn
 mport com.tw ter.strato.generated.cl ent.g zmoduck.Compos eOnUserCl entColumn
 mport com.tw ter.strato.graphql.t  l nes.{thr ftscala => gql}
 mport com.tw ter.strato.thr ft.ScroogeConv
 mport com.tw ter.t  l nes.render.{thr ftscala => tr}
 mport com.tw ter.ut l.Try
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Ho M xerColumn @ nject() (
  ho M xerRequestUnmarshaller: Ho M xerRequestUnmarshaller,
  compos eOnUserCl entColumn: Compos eOnUserCl entColumn,
  aud  pCl entColumn: Aud  pCl entColumn,
  paramsBu lder: ParamsBu lder,
  productP pel neReg stry: ProductP pel neReg stry)
    extends StratoFed.Column(Ho M xerColumn.Path)
    w h StratoFed.Fetch.Arrow {

  overr de val contact nfo: Contact nfo = Contact nfo(
    contactEma l = "",
    ldapGroup = "",
    slackRoom d = ""
  )

  overr de val  tadata: Op tadata =
    Op tadata(
      l fecycle = So (L fecycle.Product on),
      descr pt on =
        So (Descr pt on.Pla nText("Federated Strato column for T  l nes served v a Ho  M xer"))
    )

  pr vate val bouncerAccess: Seq[Pol cy] = Seq(BouncerAccess())
  pr vate val f natraTestServ ce dent f ers: Seq[Pol cy] = Seq(
    Serv ce dent f erPattern(
      role = "",
      serv ce = "",
      env = "",
      zone = Seq(""))
  )

  overr de val pol cy: Pol cy = AnyOf(bouncerAccess ++ f natraTestServ ce dent f ers)

  overr de type Key = gql.T  l neKey
  overr de type V ew = gql.Ho T  l neV ew
  overr de type Value = tr.T  l ne

  overr de val keyConv: Conv[Key] = ScroogeConv.fromStruct[gql.T  l neKey]
  overr de val v ewConv: Conv[V ew] = ScroogeConv.fromStruct[gql.Ho T  l neV ew]
  overr de val valueConv: Conv[Value] = ScroogeConv.fromStruct[tr.T  l ne]

  pr vate def createHo M xerRequestArrow(
    compos eOnUserCl entColumn: Compos eOnUserCl entColumn,
    aud  pCl entColumn: Aud  pCl entColumn
  ): Arrow[(Key, V ew), hm.Ho M xerRequest] = {

    val populateUserRolesAnd p: Arrow[(Key, V ew), (Opt on[Set[Str ng]], Opt on[Str ng])] = {
      val g zmoduckV ew: (gd.LookupContext, Set[gd.QueryF elds]) =
        (gd.LookupContext(), Set(gd.QueryF elds.Roles))

      val populateUserRoles = Arrow
        .flatMap[(Key, V ew), Opt on[Set[Str ng]]] { _ =>
          St ch.collect {
            CallContext.tw terUser d.map { user d =>
              compos eOnUserCl entColumn.fetc r
                .callStack(Ho M xerColumn.FetchCallstack)
                .fetch(user d, g zmoduckV ew).map(_.v)
                .map {
                  _.flatMap(_.roles.map(_.roles.toSet)).getOrElse(Set.empty)
                }
            }
          }
        }

      val populate pAddress = Arrow
        .flatMap[(Key, V ew), Opt on[Str ng]](_ =>
          aud  pCl entColumn.fetc r
            .callStack(Ho M xerColumn.FetchCallstack)
            .fetch((), ()).map(_.v))

      Arrow.jo n(
        populateUserRoles,
        populate pAddress
      )
    }

    Arrow.z pW hArg(populateUserRolesAnd p).map {
      case ((key, v ew), (roles,  pAddress)) =>
        val dev ceContextOpt = So (
          hm.Dev ceContext(
             sPoll ng = CallContext. sPoll ng,
            requestContext = v ew.requestContext,
            latestControlAva lable = v ew.latestControlAva lable,
            autoplayEnabled = v ew.autoplayEnabled
          ))
        val seenT et ds = v ew.seenT et ds.f lter(_.nonEmpty)

        val (product, productContext) = key match {
          case gql.T  l neKey.Ho T  l ne(_) | gql.T  l neKey.Ho T  l neV2(_) =>
            (
              hm.Product.For ,
              hm.ProductContext.For (
                hm.For (
                  dev ceContextOpt,
                  seenT et ds,
                  v ew.dspCl entContext,
                  v ew.pushToHo T et d
                )
              ))
          case gql.T  l neKey.Ho LatestT  l ne(_) | gql.T  l neKey.Ho LatestT  l neV2(_) =>
            (
              hm.Product.Follow ng,
              hm.ProductContext.Follow ng(
                hm.Follow ng(dev ceContextOpt, seenT et ds, v ew.dspCl entContext)))
          case gql.T  l neKey.CreatorSubscr pt onsT  l ne(_) =>
            (
              hm.Product.Subscr bed,
              hm.ProductContext.Subscr bed(hm.Subscr bed(dev ceContextOpt, seenT et ds)))
          case _ => throw new UnsupportedOperat onExcept on(s"Unknown product: $key")
        }

        val cl entContext = pm.Cl entContext(
          user d = CallContext.tw terUser d,
          guest d = CallContext.guest d,
          guest dAds = CallContext.guest dAds,
          guest dMarket ng = CallContext.guest dMarket ng,
          app d = CallContext.cl entAppl cat on d,
           pAddress =  pAddress,
          userAgent = CallContext.userAgent,
          countryCode = CallContext.requestCountryCode,
          languageCode = CallContext.requestLanguageCode,
           sTwoff ce = CallContext. s nternalOrTwoff ce,
          userRoles = roles,
          dev ce d = CallContext.dev ce d,
          mob leDev ce d = CallContext.mob leDev ce d,
          mob leDev ceAd d = CallContext.ad d,
          l m AdTrack ng = CallContext.l m AdTrack ng
        )

        hm.Ho M xerRequest(
          cl entContext = cl entContext,
          product = product,
          productContext = So (productContext),
          maxResults = Try(v ew.count.get.to nt).toOpt on.orElse(Ho M xerColumn.MaxCount),
          cursor = v ew.cursor.f lter(_.nonEmpty)
        )
    }
  }

  overr de val fetch: Arrow[(Key, V ew), Result[Value]] = {
    val transformThr ft ntoP pel neRequest: Arrow[
      (Key, V ew),
      ProductP pel neRequest[Ho M xerRequest]
    ] = {
      Arrow
        . dent y[(Key, V ew)]
        .andT n {
          createHo M xerRequestArrow(compos eOnUserCl entColumn, aud  pCl entColumn)
        }
        .map {
          case thr ftRequest =>
            val request = ho M xerRequestUnmarshaller(thr ftRequest)
            val params = paramsBu lder.bu ld(
              cl entContext = request.cl entContext,
              product = request.product,
              featureOverr des =
                request.debugParams.flatMap(_.featureOverr des).getOrElse(Map.empty),
            )
            ProductP pel neRequest(request, params)
        }
    }

    val underly ngProduct: Arrow[
      ProductP pel neRequest[Ho M xerRequest],
      ProductP pel neResult[tr.T  l neResponse]
    ] = Arrow
      . dent y[ProductP pel neRequest[Ho M xerRequest]]
      .map { p pel neRequest =>
        val p pel neArrow = productP pel neReg stry
          .getProductP pel ne[Ho M xerRequest, tr.T  l neResponse](
            p pel neRequest.request.product)
          .arrow
        (p pel neArrow, p pel neRequest)
      }.applyArrow

    transformThr ft ntoP pel neRequest.andT n(underly ngProduct).map {
      _.result match {
        case So (result) => found(result.t  l ne)
        case _ => m ss ng
      }
    }
  }
}

object Ho M xerColumn {
  val Path = "ho -m xer/ho M xer.T  l ne"
  pr vate val FetchCallstack = s"$Path:fetch"
  pr vate val MaxCount: Opt on[ nt] = So (100)
}
