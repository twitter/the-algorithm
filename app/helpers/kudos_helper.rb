module KudosHelper
  # Returns a comma-separated list of kudos. Restricts the list to the first
  # ArchiveConfig.MAX_KUDOS_TO_SHOW entries, with a link to view more.
  #
  # When showing_more is true, returns a list with a connector at the front,
  # so that it can be appended to an existing list to make a longer list.
  # Otherwise, returns a normal-looking list.
  def kudos_user_links(commentable, kudos, showing_more: true)
    kudos = kudos.order(id: :desc)

    total_count = kudos.count
    collapsed_count = total_count - ArchiveConfig.MAX_KUDOS_TO_SHOW
    kudos_to_display = kudos.limit(ArchiveConfig.MAX_KUDOS_TO_SHOW).to_a

    kudos_links = kudos_to_display.map do |kudo|
      link_to kudo.user.login, kudo.user
    end

    # Make sure to duplicate the hash returned by I18n.translate, because
    # otherwise I18n will start returning our modified version:
    connectors = t("support.array").dup

    if showing_more
      # Make a connector appear at the front:
      kudos_links.unshift("")

      # Even if it looks like there are only two items, we're actually just
      # showing the last part of a longer list, so we should always use the
      # last_word_connector instead of the two_words_connector:
      connectors[:two_words_connector] = connectors[:last_word_connector]
    end

    if collapsed_count.positive?
      # Add the link to show more at the end of the list:
      kudos_links << link_to(
        t("kudos.user_links.more_link", count: collapsed_count),
        work_kudos_path(commentable, before: kudos_to_display.last.id),
        id: "kudos_more_link", remote: true
      )

      # Regardless of whether we're showing 2 or 3+, we need to wrap the last
      # connector in a span with the id "kudos_more_connector" so that we can
      # remove/alter it later:
      %i[two_words_connector last_word_connector].each do |connector_type|
        connectors[connector_type] = tag.span(connectors[connector_type],
                                              id: "kudos_more_connector")
      end
    end

    kudos_links.to_sentence(connectors).html_safe
  end
end
