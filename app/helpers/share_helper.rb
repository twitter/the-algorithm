# Helper for work and bookmark social media sharing code
module ShareHelper
  # Get work title, word count, and creators and add app short name,
  # but do not add formatting so it can be link text for Tumblr sharing.
  def get_tumblr_embed_link_title(work)
    title = work.title + " (#{work.word_count} #{ts('words')})"
    pseud = text_byline(work)
    "#{title} #{ts("by")} #{pseud} #{ts("[#{ArchiveConfig.APP_SHORT_NAME}]")}"
  end

  def get_tweet_text(work)
    if work.unrevealed?
      ts("Mystery Work")
    else
      names = text_byline(work)
      fandoms = short_fandom_string(work)
      "#{work.title} by #{names} - #{fandoms}".truncate(95)
    end
  end

  def get_tweet_text_for_bookmark(bookmark)
    return unless bookmark.bookmarkable.is_a?(Work)

    names = bookmark.bookmarkable.creators.to_sentence
    fandoms = short_fandom_string(bookmark.bookmarkable)
    "Bookmark of #{bookmark.bookmarkable.title} by #{names} - #{fandoms}".truncate(83)
  end

  private

  def short_fandom_string(work)
    work.fandoms.size > 2 ? ts("Multifandom") : work.fandom_string
  end

  # Being able to add line breaks in the sharing templates makes the code
  # easier to read and edit, but we don't want them in the sharing code itself
  def remove_newlines(html)
    html.delete("\n")
  end
end
