package com.twitter.product_mixer.component_library.decorator.urt.builder.richtext.twitter_text

trait TwitterTextRendererProcessor {
  def process(twitterTextRichTextBuilder: TwitterTextRenderer): TwitterTextRenderer
}
