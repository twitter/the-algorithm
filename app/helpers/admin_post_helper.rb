module AdminPostHelper
  def sorted_translations(admin_post)
    admin_post.translations.sort_by do |translation|
      language = translation.language
      language.sortable_name.blank? ? language.short : language.sortable_name
    end
  end
end
