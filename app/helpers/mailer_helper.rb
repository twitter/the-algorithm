module MailerHelper

  def style_bold(text)
    ("<b style=\"color:#990000\">" + "#{text}".html_safe + "</b>").html_safe
  end

  def style_link(body, url, html_options = {})
    html_options[:style] = "color:#990000"
    link_to(body.html_safe, url, html_options)
  end

  # For work, chapter, and series links
  def style_creation_link(title, url, html_options = {})
    html_options[:style] = "color:#990000"
    ("<i><b>" + link_to(title.html_safe, url, html_options) + "</b></i>").html_safe
  end

  # For work, chapter, and series titles
  def style_creation_title(title)
    ("<i><b style=\"color:#990000\">" + title.html_safe + "</b></i>").html_safe
  end

  def style_footer_link(body, url, html_options = {})
    html_options[:style] = "color:#FFFFFF"
    link_to(body.html_safe, url, html_options)
  end

  def style_email(email, name = nil, html_options = {})
    html_options[:style] = "color:#990000"
    mail_to(email, name.nil? ? nil : name.html_safe, html_options)
  end

  def style_pseud_link(pseud)
    style_link("<img src=\"" + root_url + "favicon.ico\" style=\"border:none;display:inline-block;font-weight:bold;height:16px;padding-right:3px;vertical-align:-3px;width:16px;\">" +
      pseud.byline, user_pseud_url(pseud.user, pseud))
  end

  def text_pseud(pseud)
    pseud.byline + " (#{user_pseud_url(pseud.user, pseud)})"
  end

  def style_quote(text)
    ("<blockquote style=\"background:#eee;margin:1em;padding:8px;\">" + text + "</blockquote>").html_safe
  end

  def support_link(text)
    style_link(text, root_url + "support")
  end

  def abuse_link(text)
    style_link(text, root_url + "abuse_reports/new")
  end

  def tos_link(text)
    style_link(text, tos_url)
  end

  def opendoors_link(text)
    style_link(text, "http://opendoors.transformativeworks.org/contact-open-doors/")
  end

  def styled_divider
    ("<div style=\"line-height:0.5em;\">" +
      "<br>" +
      "<hr style=\"color:transparent;background-color: transparent;border-bottom: 1px solid #DDDDDD;\">" +
    "</div><br>").html_safe
  end

  def text_divider
    "--------------------"
  end

  # strip opening paragraph tags, and line breaks or close-pargraphs at the end of the string
  # all other close-paragraphs become double line breaks
  # line break tags become single line breaks
  # bold text is wrapped in *
  # italic text is wrapped in /
  # underlined text is wrapped in _
  # all other html tags are stripped
  def to_plain_text(html)
    strip_tags(
      html.gsub(/<p>|<\/p>\z|<br( ?\/)?>\z/, "")
        .gsub(/<\/p>/, "\n\n")
        .gsub(/<br( ?\/)?>/, "\n")
        .gsub(/<\/?(b|em|strong)>/, "*")
        .gsub(/<\/?(i|cite)>/, "/")
        .gsub(/<\/?u>/, "_")
    )
  end

  # Reformat a string as HTML with <br> tags instead of newlines, but with all
  # other HTML escaped.
  # This is used for collection.assignment_notification, which already strips
  # HTML tags (when saving the collection settings, the params are sanitized),
  # but that still leaves other HTML entities.
  def escape_html_and_create_linebreaks(html)
    # Escape each line with h(), then join with <br>s and mark as html_safe to
    # ensure that the <br>s aren't escaped.
    html.split("\n").map { |line_of_text| h(line_of_text) }.join('<br>').html_safe
  end

  # The title used in creatorship_notification and creatorship_request
  # emails.
  def creation_title(creation)
    if creation.is_a?(Chapter)
      t("mailer.general.creation.title_with_chapter_number",
         position: creation.position, title: creation.work.title)
    else
      creation.title
    end
  end

  # e.g., Title (x words), where Title is a link
  def creation_link_with_word_count(creation, creation_url)
    title = if creation.is_a?(Chapter)
              creation.full_chapter_title.html_safe
            else
              creation.title.html_safe
            end
    t("mailer.general.creation.link_with_word_count",
      creation_link: style_creation_link(title, creation_url),
      word_count: creation_word_count(creation)).html_safe
  end

  # e.g., "Title" (x words), where Title is not a link
  def creation_title_with_word_count(creation)
    title = if creation.is_a?(Chapter)
              creation.full_chapter_title.html_safe
            else
              creation.title.html_safe
            end
    t("mailer.general.creation.title_with_word_count",
      creation_title: title, word_count: creation_word_count(creation))
  end

  # The bylines used in subscription emails to prevent exposing the name(s) of
  # anonymous creator(s).
  def creator_links(work)
    if work.anonymous?
      "Anonymous"
    else
      work.pseuds.map { |p| style_pseud_link(p) }.to_sentence.html_safe
    end
  end

  def creator_text(work)
    if work.anonymous?
      "Anonymous"
    else
      work.pseuds.map { |p| text_pseud(p) }.to_sentence.html_safe
    end
  end

  def work_metadata_label(text)
    text.html_safe + t("mailer.general.metadata_label_indicator")
  end

  # Spacing is dealt with in locale files, e.g. " : " for French.
  def work_tag_metadata(tags)
    return if tags.empty?

    "#{work_tag_metadata_label(tags)}#{work_tag_metadata_list(tags)}"
  end

  # TODO: We're using this for labels in set_password_notification, too. Let's
  # take the "work" out of the name.
  def style_work_metadata_label(text)
    style_bold(work_metadata_label(text))
  end

  # Spacing is dealt with in locale files, e.g. " : " for French.
  def style_work_tag_metadata(tags)
    return if tags.empty?

    label = style_bold(work_tag_metadata_label(tags))
    "#{label}#{style_work_tag_metadata_list(tags)}".html_safe
  end

  private

  # e.g., 1 word or 50 words
  def creation_word_count(creation)
    t("mailer.general.creation.word_count", count: creation.word_count)
  end

  def work_tag_metadata_label(tags)
    return if tags.empty?

    type = tags.first.type
    t("activerecord.models.#{type.underscore}", count: tags.count) + t("mailer.general.metadata_label_indicator")
  end

  # We don't use .to_sentence because these aren't links and we risk making any
  # connector word (e.g., "and") look like part of the final tag.
  def work_tag_metadata_list(tags)
    return if tags.empty?

    tags.pluck(:name).join(t("support.array.words_connector"))
  end

  def style_work_tag_metadata_list(tags)
    return if tags.empty?

    type = tags.first.type
    # Fandom tags are linked and to_sentence'd.
    if type == "Fandom"
      tags.map { |f| style_link(f.name, fandom_url(f)) }.to_sentence.html_safe
    else
      work_tag_metadata_list(tags)
    end
  end
end # end of MailerHelper
