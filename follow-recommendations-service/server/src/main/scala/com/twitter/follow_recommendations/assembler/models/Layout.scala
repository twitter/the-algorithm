package com.twitter.follow_recommendations.assembler.models

sealed trait Layout

case class UserListLayout(
  header: Option[HeaderConfig],
  userListOptions: UserListOptions,
  socialProofs: Option[Seq[SocialProof]],
  footer: Option[FooterConfig])
    extends Layout

case class CarouselLayout(
  header: Option[HeaderConfig],
  carouselOptions: CarouselOptions,
  socialProofs: Option[Seq[SocialProof]])
    extends Layout
