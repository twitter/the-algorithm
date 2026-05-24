package com.twitter.product_mixer.component_library.selector.sorter

sealed trait SortOrder
case object Ascending extends SortOrder
case object Descending extends SortOrder
