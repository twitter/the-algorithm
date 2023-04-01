# These methods return information about the given TagSetNomination or the
# tags in a a TagSet
module TagSetsHelper

  def nomination_notes(limit)
    message = ""
    if limit[:fandom] > 0
      if limit[:character] > 0
        if limit[:relationship] > 0
          message = ts("You can nominate up to %{f} fandoms and up to %{c} characters and %{r} relationships for each one.",
            f: limit[:fandom], c: limit[:character], r: limit[:relationship])
        else
          message = ts("You can nominate up to %{f} fandoms and up to %{c} characters for each one.", f: limit[:fandom], c: limit[:character])
        end
      elsif limit[:relationship] > 0
        message = ts("You can nominate up to %{f} fandoms and up to %{r} relationships for each one.", f: limit[:fandom], r: limit[:relationship])
      else
        message = ts("You can nominate up to %{f} fandoms.", f: limit[:fandom])
      end
    else
      if limit[:character] > 0
        if limit[:relationship] > 0
          message = ts("You can nominate up to %{c} characters and %{r} relationships.", c: limit[:character], r: limit[:relationship])
        else
          message = ts("You can nominate up to %{c} characters.", c: limit[:character])
        end
      elsif limit[:relationship] > 0
        message = ts("You can nominate up to %{r} relationships.", r: limit[:relationship])
      end
    end

    if limit[:freeform] > 0
      if message.blank?
        message = ts("You can nominate up to %{ff} additional tags.", ff: limit[:freeform])
      else
        message += ts(" You can also nominate up to %{ff} additional tags.", ff: limit[:freeform])
      end
    end

    message
  end

  def nomination_status(nomination=nil)
    symbol = "?!"
    status = "unreviewed"
    tooltip = ts('This nomination has not been reviewed yet.')
    if nomination
      if nomination.approved
        symbol = "&#10004;"
        status = "approved"
        tooltip = ts('This nomination has been approved!')
      elsif nomination.rejected
        symbol = "&#10006;"
        status = "rejected"
        tooltip = ts('This nomination was rejected (but another version may have been approved instead).')
      elsif @tag_set.nominated
        symbol = "?!"
        status = "unreviewed"
        tooltip = ts('This nomination has not been reviewed yet and can still be changed.')
      end
    end

    return content_tag(:span, content_tag(:span, "#{symbol}".html_safe), class: "#{status} symbol", data: {tooltip: "#{tooltip}"})
  end

  def nomination_tag_information(nominated_tag)
    tag_object = nominated_tag.type.gsub(/Nomination/, '').constantize.find_by_name(nominated_tag.tagname)
    status = "nonexistent"
    tooltip = ts("This tag has never been used before. Check the spelling!")
    title = ts("nonexistent tag")
    span_content = nominated_tag.tagname
    synonym_for = ""
    case
    when nominated_tag.canonical
      if nominated_tag.parented
        status = "canonical"
        tooltip = ts("This is a canonical tag.")
        title = ts("canonical tag")
        span_content = link_to_tag_works(tag_object)
      else
        status = "unparented"
        tooltip = ts("This is a canonical tag but not associated with the specified fandom.")
        title = ts("canonical tag without parent")
        span_content = link_to_tag_works(tag_object)
      end
    when nominated_tag.synonym
      status = "synonym"
      tooltip = ts("This is a synonym of a canonical tag.")
      title = ts("tag synonym")
      synonym_for = content_tag(:span, " (#{link_to_tag_works(tag_object.merger, class: "canonical")})".html_safe)
    when nominated_tag.exists
      status = "unwrangled"
      tooltip = ts("This is not a canonical tag.")
      title = ts("non-canonical tag")
    end

    return content_tag(:span, "#{span_content}".html_safe, class: "#{status} nomination", title: "#{title}", data: {tooltip: "#{tooltip}"}) + synonym_for

  end

  def tag_relation_to_list(tag_relation)
    tag_relation.by_name_without_articles.pluck(:name).map {|tagname| content_tag(:li, tagname)}.join("\n").html_safe
  end

end
