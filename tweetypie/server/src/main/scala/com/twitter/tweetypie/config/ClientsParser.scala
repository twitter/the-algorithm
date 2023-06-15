package com.twitter.tweetypie.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.twitter.finagle.mtls.authentication.ServiceIdentifier
import com.twitter.util.Try

case object EmptyConfigException extends Exception

case class ServiceIdentifierPattern(
  role: Option[String],
  service: Option[String],
  environment: Option[String],
) {
  // Service identifier matches if the fields of service identifier
  // match all the defined fields of pattern.
  def matches(id: ServiceIdentifier): Boolean =
    Seq(
      role.map(_ == id.role),
      service.map(_ == id.service),
      environment.map(_ == id.environment),
    )
      .flatten
      .forall(identity)

  // True if this is the kind of pattern that only specifies environment.
  // This should be used in rare cases, for example letting all devel clients
  // use permitted methods - like get_tweet_fields.
  def onlyEnv: Boolean =
    role.isEmpty && service.isEmpty && environment.isDefined
}

case class Client(
  clientId: String,
  serviceIdentifiers: Seq[ServiceIdentifierPattern],
  tpsLimit: Option[Int],
  environments: Seq[String],
  loadShedEnvs: Seq[String],
  permittedMethods: Set[String],
  accessAllMethods: Boolean,
  bypassVisibilityFiltering: Boolean,
  enforceRateLimit: Boolean) {

  // Client matches a service identifier if any of its patterns
  // match.
  def matches(id: ServiceIdentifier): Boolean =
    serviceIdentifiers.exists(_.matches(id))
}

object ClientsParser {

  // Case classes for parsing yaml - should match the structure of clients.yml
  private case class YamlServiceIdentifier(
    role: Option[String],
    service: Option[String],
    environment: Option[String],
  )
  private case class YamlClient(
    client_id: String,
    service_identifiers: Option[Seq[YamlServiceIdentifier]],
    service_name: String,
    tps_quota: String,
    contact_email: String,
    environments: Seq[String],
    load_shed_envs: Option[
      Seq[String]
    ], // list of environments we can rejects requests from if load shedding
    comment: Option[String],
    permitted_methods: Option[Seq[String]],
    access_all_methods: Boolean,
    bypass_visibility_filtering: Boolean,
    bypass_visibility_filtering_reason: Option[String],
    rate_limit: Boolean) {
    def toClient: Client = {

      // we provision tps_quota for both DCs during white-listing, to account for full fail-over.
      val tpsLimit: Option[Int] = Try(tps_quota.replaceAll("[^0-9]", "").toInt * 1000).toOption

      Client(
        clientId = client_id,
        serviceIdentifiers = service_identifiers.getOrElse(Nil).flatMap { id =>
          if (id.role.isDefined || id.service.isDefined || id.environment.isDefined) {
            Seq(ServiceIdentifierPattern(
              role = id.role,
              service = id.service,
              environment = id.environment,
            ))
          } else {
            Seq()
          }
        },
        tpsLimit = tpsLimit,
        environments = environments,
        loadShedEnvs = load_shed_envs.getOrElse(Nil),
        permittedMethods = permitted_methods.getOrElse(Nil).toSet,
        accessAllMethods = access_all_methods,
        bypassVisibilityFiltering = bypass_visibility_filtering,
        enforceRateLimit = rate_limit
      )
    }
  }

  private val mapper: ObjectMapper = new ObjectMapper(new YAMLFactory())
  mapper.registerModule(DefaultScalaModule)

  private val yamlClientTypeFactory =
    mapper
      .getTypeFactory()
      .constructCollectionLikeType(
        classOf[Seq[YamlClient]],
        classOf[YamlClient]
      )

  def apply(yamlString: String): Seq[Client] = {
    val parsed =
      mapper
        .readValue[Seq[YamlClient]](yamlString, yamlClientTypeFactory)
        .map(_.toClient)

    if (parsed.isEmpty)
      throw EmptyConfigException
    else
      parsed
  }
}
