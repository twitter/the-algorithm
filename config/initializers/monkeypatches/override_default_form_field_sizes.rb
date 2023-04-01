# Rails default form helpers text_field etc use default sizes. Rails default form helpers text_field_tag etc do NOT. WHY.
# Here we trash all the default options.
#
# We also add the css class "text" to all text input fields because we need a class on them to be
# able to target them in ie6, alas.

# InstanceTag no longer exists in Rails 4
# module ActionView::Helpers
#   class InstanceTag
#     remove_const :DEFAULT_TEXT_AREA_OPTIONS
#     DEFAULT_TEXT_AREA_OPTIONS = { }
#
#     remove_const :DEFAULT_FIELD_OPTIONS
#     DEFAULT_FIELD_OPTIONS = { "class" => "text" }
#   end
# end


module ActionView::Helpers::FormTagHelper
  alias_method :text_field_tag_original, :text_field_tag
  def text_field_tag(name, value = nil, options = {})
    text_field_tag_original(name, value, {"class" => "text"}.merge(options))
  end
end
