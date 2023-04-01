module TagTypeHelper

  # Determines the appropriate CSS class given the tag class name e.g. "Archive"
  #
  # Examples
  #
  #   tag_type_css_class("ArchiveWarning")
  #   # => "warning"
  def tag_type_css_class(tag_type)
    tag_type = tag_type.singularize.classify
    case tag_type
    when "AdditionalTag"
      "freeform"
    when "ArchiveWarning"
      "warning"
    else
      tag_type.downcase
    end
  end

  # Determines the Tag Type labels e.g "Warnings", "Categories", "Fandoms"
  #
  # Examples
  #
  #   tag_type_label_name("archive_warning")
  #   # => "Warnings"
  #
  # Returns String label in singular form
  def tag_type_label_name(tag_type)
    label = case tag_type.singularize.underscore
            when "archive_warning"
              ArchiveWarning.label_name
            when "freeform"
              Freeform.label_name
            when "tag"
              "Bookmarker's Tag"
            else
              tag_type.humanize.titleize
            end
    label.singularize
  end
end
