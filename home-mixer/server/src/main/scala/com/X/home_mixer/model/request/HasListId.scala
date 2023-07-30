package com.X.home_mixer.model.request

/**
 * [[HasListId]] enables shared components to access the list id shared by all list timeline products.
 */
trait HasListId {
  def listId: Long
}
