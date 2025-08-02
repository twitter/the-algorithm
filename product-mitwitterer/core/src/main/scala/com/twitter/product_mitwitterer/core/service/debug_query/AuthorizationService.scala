package com.twitter.product_mitwitterer.core.service.debug_query

import com.twitter.finagle.mtls.authentication.ServiceIdentifier
import com.twitter.inject.annotations.Flag
import com.twitter.product_mitwitterer.core.functional_component.common.access_policy.AccessPolicy
import com.twitter.product_mitwitterer.core.functional_component.common.access_policy.AccessPolicyEvaluator
import com.twitter.product_mitwitterer.core.model.common.identifier.ComponentIdentifierStack
import com.twitter.product_mitwitterer.core.module.product_mitwitterer_flags.ProductMitwittererFlagModule.ServiceLocal
import com.twitter.product_mitwitterer.core.pipeline.pipeline_failure.Authentication
import com.twitter.product_mitwitterer.core.pipeline.pipeline_failure.BadRequest
import com.twitter.product_mitwitterer.core.pipeline.pipeline_failure.PipelineFailure
import com.twitter.turntable.{thriftscala => t}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

/**
 * Basic class that provides a verification method for checking if a call to our debugging
 * features is allowed/authorized to make said call.
 * @param isServiceLocal Whether the service is being run locally.
 */
@Singleton
class AuthorizationService @Inject() (@Flag(ServiceLocal) isServiceLocal: Boolean) {
  import AuthorizationService._

  /**
   * Check whether a call to a given product is authorized. Throws an [[UnauthorizedServiceCallEtwitterception]]
   * if not.
   * @param requestingServiceIdentifier The Service Identifier of the calling service
   * @param productAccessPolicies The access policies of the product being called.
   * @param requestContetwittert The request contetwittert of the caller.
   */
  def verifyRequestAuthorization(
    componentIdentifierStack: ComponentIdentifierStack,
    requestingServiceIdentifier: ServiceIdentifier,
    productAccessPolicies: Set[AccessPolicy],
    requestContetwittert: t.TurntableRequestContetwittert
  ): Unit = {
    val isServiceCallAuthorized =
      requestingServiceIdentifier.role == AllowedServiceIdentifierRole && requestingServiceIdentifier.service == AllowedServiceIdentifierName
    val userLdapGroups = requestContetwittert.ldapGroups.map(_.toSet)

    val accessPolicyAllowed = AccessPolicyEvaluator.evaluate(
      productAccessPolicies = productAccessPolicies,
      userLdapGroups = userLdapGroups.getOrElse(Set.empty)
    )

    if (!isServiceLocal && !isServiceCallAuthorized) {
      throw new UnauthorizedServiceCallEtwitterception(
        requestingServiceIdentifier,
        componentIdentifierStack)
    }

    if (!isServiceLocal && !accessPolicyAllowed) {
      throw new InsufficientAccessEtwitterception(
        userLdapGroups,
        productAccessPolicies,
        componentIdentifierStack)
    }
  }
}

object AuthorizationService {
  final val AllowedServiceIdentifierRole = "turntable"
  final val AllowedServiceIdentifierName = "turntable"
}

class UnauthorizedServiceCallEtwitterception(
  serviceIdentifier: ServiceIdentifier,
  componentIdentifierStack: ComponentIdentifierStack)
    etwittertends PipelineFailure(
      BadRequest,
      s"Unetwitterpected Service tried to call Turntable Debug endpoint: ${ServiceIdentifier.asString(serviceIdentifier)}",
      componentStack = Some(componentIdentifierStack))

class InsufficientAccessEtwitterception(
  ldapGroups: Option[Set[String]],
  desiredAccessPolicies: Set[AccessPolicy],
  componentIdentifierStack: ComponentIdentifierStack)
    etwittertends PipelineFailure(
      Authentication,
      s"Request did not satisfy access policies: $desiredAccessPolicies with ldapGroups = $ldapGroups",
      componentStack = Some(componentIdentifierStack))
