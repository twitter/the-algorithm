package com.twitter.home_mitwitterer.product.list_recommended_users.gate

import com.twitter.home_mitwitterer.model.request.HasListId
import com.twitter.product_mitwitterer.core.functional_component.gate.Gate
import com.twitter.product_mitwitterer.core.model.common.identifier.GateIdentifier
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.socialgraph.{thriftscala => sg}
import com.twitter.stitch.Stitch
import com.twitter.stitch.socialgraph.SocialGraph

import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
case class ViewerIsListOwnerGate @Inject() (socialGraph: SocialGraph)
    etwittertends Gate[PipelineQuery with HasListId] {

  override val identifier: GateIdentifier = GateIdentifier("ViewerIsListOwner")

  private val relationship = sg.Relationship(relationshipType = sg.RelationshipType.ListOwning)

  override def shouldContinue(query: PipelineQuery with HasListId): Stitch[Boolean] = {
    val request = sg.EtwitteristsRequest(
      source = query.getRequiredUserId,
      target = query.listId,
      relationships = Seq(relationship))
    socialGraph.etwitterists(request).map(_.etwitterists)
  }
}
