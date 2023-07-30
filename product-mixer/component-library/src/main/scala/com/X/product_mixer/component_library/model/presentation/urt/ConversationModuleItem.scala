package com.X.product_mixer.component_library.model.presentation.urt

import com.X.product_mixer.core.model.common.presentation.urt.BaseUrtItemPresentation
import com.X.product_mixer.core.model.common.presentation.urt.IsDispensable
import com.X.product_mixer.core.model.common.presentation.urt.WithItemTreeDisplay

trait ConversationModuleItem
    extends BaseUrtItemPresentation
    with IsDispensable
    with WithItemTreeDisplay
