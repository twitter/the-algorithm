module AdvancedSearchHelper

  def filter_boolean_value(filter_action, tag_type, tag_id)
    if filter_action == "include"
      @search.send("#{tag_type}_ids").present? &&
        @search.send("#{tag_type}_ids").include?(tag_id)
    elsif tag_type == "tag" && @search.respond_to?(:excluded_bookmark_tag_ids)
      # Bookmarker's tag exclude checkboxes on bookmark filters
      @search.excluded_bookmark_tag_ids.present? && @search.excluded_bookmark_tag_ids.include?(tag_id)
    else
      # Work tag exclude checkboxes on bookmark filters,
      # or exclude checkboxes on work filters
      @search.excluded_tag_ids.present? && @search.excluded_tag_ids.include?(tag_id)
    end
  end
end
