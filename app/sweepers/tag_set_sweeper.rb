class TagSetSweeper < ActionController::Caching::Sweeper
  observe TagSet, TagSetAssociation, OwnedTagSet

  def after_create(record)
    expire_cache_for(record)
  end

  def after_update(record)
    expire_cache_for(record)
  end

  def after_destroy(record)
    expire_cache_for(record)
  end

  private

  def get_tagset_from_record(record)
    case record.class.to_s
    when "TagSetAssociation"
      record.owned_tag_set ? record.owned_tag_set.tag_set : nil
    when "OwnedTagSet"
      record.tag_set
    when "TagSet"
      record
    else
      nil
    end
  end

  def expire_cache_for(record)
    tag_set = get_tagset_from_record(record)
    unless tag_set.nil?
      # expire the tag_set show page and fragments
      ActionController::Base.new.expire_fragment("tag_set_show_#{tag_set.id}")
      TagSet::TAG_TYPES.each {|type| ActionController::Base.new.expire_fragment("tag_set_show_#{tag_set.id}_#{type}")}
    end
  end

end
