module SkinsHelper
  def skin_author_link(skin)
    if skin.author.is_a? User
      link_to(skin.byline, skin.author)
    else
      skin.byline
    end
  end

  # we only actually display an image if there's a file
  def skin_preview_display(skin)
    if skin && skin.icon_file_name
      link_to image_tag(skin.icon.url(:standard), alt: skin.icon_alt_text, class: "icon", skip_pipeline: true), skin.icon.url(:original)
    end
  end

  def skin_tag
    skin = nil

    if params[:site_skin]
      skin ||= Skin.approved_or_owned_by.usable.find_by(id: params[:site_skin])
    end

    if (logged_in? || logged_in_as_admin?) && session[:site_skin]
      skin ||= Skin.approved_or_owned_by.usable.find_by(id: session[:site_skin])
    end

    skin_id = if skin.nil?
                current_user&.preference&.skin_id || AdminSetting.default_skin_id
              else
                skin.id
              end

    return "" if skin_id.nil?

    roles = if logged_in_as_admin?
              Skin::DEFAULT_ROLES_TO_INCLUDE + ["admin"]
            else
              Skin::DEFAULT_ROLES_TO_INCLUDE
            end

    # We include the version information for both the skin_id and the
    # AdminSetting.default_skin_id because the default skin is used in skins of
    # type "user", so we need to regenerate the cache block when it's modified.
    #
    # We also include the default_skin_id in the version number so that we
    # regenerate the cache block when an admin updates the current default
    # skin.
    Rails.cache.fetch(
      [:v1, :site_skin, skin_id, logged_in_as_admin?],
      version: [skin_cache_version(skin_id),
                AdminSetting.default_skin_id,
                skin_cache_version(AdminSetting.default_skin_id)]
    ) do
      skin ||= Skin.find(skin_id)
      skin.get_style(roles)
    end
  end

  def show_advanced_skin?(skin)
    !skin.new_record? && 
      (skin.role != Skin::DEFAULT_ROLE ||
        (skin.media.present? && skin.media != Skin::DEFAULT_MEDIA) ||
        skin.ie_condition.present? ||
        skin.unusable? ||
        !skin.skin_parents.empty?)
  end

  def my_site_skins_link(link_text = nil)
    link_text ||= "My Site Skins"
    span_if_current ts(link_text), user_skins_path(current_user, skin_type: "Site"), current_page?(user_skins_path(current_user)) && (params[:skin_type].blank? || params[:skin_type] == "Site")
  end
  
  def my_work_skins_link(link_text = nil)
    link_text ||= "My Work Skins"
    span_if_current ts(link_text), user_skins_path(current_user, skin_type: "WorkSkin")
  end

  def public_site_skins_link(link_text = nil)
    link_text ||= "Public Site Skins"
    span_if_current ts(link_text), skins_path(skin_type: "Site"), current_page?(skins_path) && (params[:skin_type].blank? || params[:skin_type] == "Site")
  end
  
  def public_work_skins_link(link_text = nil)
    link_text ||= "Public Work Skins"
    span_if_current ts(link_text), skins_path(skin_type: "WorkSkin")
  end
end
