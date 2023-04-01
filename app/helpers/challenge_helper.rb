module ChallengeHelper
  def prompt_tags(prompt)
    details = content_tag(:h6, ts("Tags"), class: "landmark heading")
    TagSet::TAG_TYPES.each do |type|
      if prompt && prompt.tag_set && !prompt.tag_set.with_type(type).empty?
        details += content_tag(:ul, tag_link_list(prompt.tag_set.with_type(type), link_to_works=true), class: "#{type} type tags commas")
      end
    end
    details
  end      
  
  # generate the display value for the claim
  def claim_title(claim)
    claim.title.html_safe + link_to(ts(" (Details)"), collection_prompt_path(claim.collection, claim.request_prompt), target: "_blank", class: "toggle")
  end

  # count the number of tag sets used in a challenge
  def tag_set_count(collection)
    if challenge_type_present?(collection)
      tag_sets = determine_tag_sets(collection.challenge)

      # use `blank?` instead of `empty?` since there is the possibility that
      #   `tag_sets` will be nil, and nil does not respond to `blank?`
      tag_sets.size unless tag_sets.blank?
    end
  end

  private

  # Private: Determines whether a collection has a challenge type
  #
  # Returns a boolean
  def challenge_type_present?(collection)
    collection && collection.challenge_type.present?
  end

  # Private: Determines the collection of owned_tag_sets based on a given
  #           challenge's class
  #
  # Returns an ActiveRecord Collection object or nil
  def determine_tag_sets(challenge)
    if challenge.class.name == 'GiftExchange'
      challenge.offer_restriction.owned_tag_sets
    elsif challenge.class.name == 'PromptMeme'
      challenge.request_restriction.owned_tag_sets
    end
  end
end
