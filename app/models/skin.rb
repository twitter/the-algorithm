require 'fileutils'

class Skin < ApplicationRecord
  include HtmlCleaner
  include CssCleaner
  include SkinCacheHelper
  include SkinWizard

  TYPE_OPTIONS = [
                   [ts("Site Skin"), "Skin"],
                   [ts("Work Skin"), "WorkSkin"],
                 ]

  # any media types that are not a single alphanumeric word have to be specially
  # handled in get_media_for_filename/parse_media_from_filename
  MEDIA = %w(all screen handheld speech print braille embossed projection tty tv) + [
    "only screen and (max-width: 42em)",
    "only screen and (max-width: 62em)",
    "(prefers-color-scheme: dark)",
    "(prefers-color-scheme: light)"
  ]
  IE_CONDITIONS = %w(IE IE5 IE6 IE7 IE8 IE9 IE8_or_lower)
  ROLES = %w(user override)
  ROLE_NAMES = {"user" => "add on to archive skin", "override" => "replace archive skin entirely"}
  # We don't show some roles to users
  ALL_ROLES = ROLES + %w(admin translator site)
  DEFAULT_ROLE = "user"
  DEFAULT_ROLES_TO_INCLUDE = %w(user override site)
  DEFAULT_MEDIA = ["all"]

  SKIN_PATH = 'stylesheets/skins/'
  SITE_SKIN_PATH = 'stylesheets/site/'

  belongs_to :author, class_name: 'User'
  has_many :preferences

  serialize :media, Array

  # a skin can be both parent and child
  has_many :skin_parents, foreign_key: 'child_skin_id',
                          class_name: 'SkinParent',
                          dependent: :destroy, inverse_of: :child_skin
  has_many :parent_skins, -> { order("skin_parents.position ASC") }, through: :skin_parents, inverse_of: :child_skins

  has_many :skin_children, foreign_key: 'parent_skin_id',
                                  class_name: 'SkinParent', dependent: :destroy, inverse_of: :parent_skin
  has_many :child_skins, through: :skin_children, inverse_of: :parent_skins

  accepts_nested_attributes_for :skin_parents, allow_destroy: true, reject_if: proc { |attrs| attrs[:position].blank? || (attrs[:parent_skin_title].blank? && attrs[:parent_skin_id].blank?) }

  has_attached_file :icon,
                    styles: { standard: "100x100>" },
                    url: "/system/:class/:attachment/:id/:style/:basename.:extension",
                    path: %w(staging production).include?(Rails.env) ? ":class/:attachment/:id/:style.:extension" : ":rails_root/public:url",
                    storage: %w(staging production).include?(Rails.env) ? :s3 : :filesystem,
                    s3_protocol: "https",
                    s3_credentials: "#{Rails.root}/config/s3.yml",
                    bucket: %w(staging production).include?(Rails.env) ? YAML.load_file("#{Rails.root}/config/s3.yml")['bucket'] : "",
                    default_url: "/images/skins/iconsets/default/icon_skins.png"

  after_save :skin_invalidate_cache
  def skin_invalidate_cache
    skin_chooser_expire_cache
    skin_cache_version_update(id)

    # Work skins can't have children, but site skins (which have type nil)
    # might have children that need expiration:
    return unless type.nil?

    SkinParent.get_all_child_ids(id).each do |child_id|
      skin_cache_version_update(child_id)
    end
  end

  validates_attachment_content_type :icon, content_type: /image\/\S+/, allow_nil: true
  validates_attachment_size :icon, less_than: 500.kilobytes, allow_nil: true
  validates_length_of :icon_alt_text, allow_blank: true, maximum: ArchiveConfig.ICON_ALT_MAX,
    too_long: ts("must be less than %{max} characters long.", max: ArchiveConfig.ICON_ALT_MAX)

  validates_length_of :description, allow_blank: true, maximum: ArchiveConfig.SUMMARY_MAX,
    too_long: ts("must be less than %{max} characters long.", max: ArchiveConfig.SUMMARY_MAX)

  validates_length_of :css, allow_blank: true, maximum: ArchiveConfig.CONTENT_MAX,
    too_long: ts("must be less than %{max} characters long.", max: ArchiveConfig.CONTENT_MAX)

  before_validation :clean_media
  def clean_media
    # handle bizarro cucumber-only error that prevents media from deserializing correctly when attachments are made
    if media && media.is_a?(Array) && !media.empty?
      new_media = media.flatten.compact.collect {|m| m.gsub(/\["(\w+)"\]/, '\1')}
      self.media = new_media
    end
  end

  validate :valid_media
  def valid_media
    if media && media.is_a?(Array) && media.any? {|m| !MEDIA.include?(m)}
      errors.add(:base, ts("We don't currently support the media type %{media}, sorry! If we should, please let Support know.", media: media.join(', ')))
    end
  end

  validates :ie_condition, inclusion: {in: IE_CONDITIONS, allow_nil: true, allow_blank: true}
  validates :role, inclusion: {in: ALL_ROLES, allow_blank: true, allow_nil: true }

  validate :valid_public_preview
  def valid_public_preview
    return true if (self.official? || !self.public? || self.icon_file_name)
    errors.add(:base, ts("You need to upload a screencap if you want to share your skin."))
  end

  validates_presence_of :title
  validates :title, uniqueness: { message: ts("must be unique"), case_sensitive: true }

  validates_numericality_of :margin, :base_em, allow_nil: true
  validate :valid_font
  def valid_font
    return if self.font.blank?
    self.font.split(',').each do |subfont|
      if sanitize_css_font(subfont).blank?
        errors.add(:font, "cannot use #{subfont}.")
      end
    end
  end

  validate :valid_colors
  def valid_colors

    if !self.background_color.blank? && sanitize_css_value(self.background_color).blank?
      errors.add(:background_color, "uses a color that is not allowed.")
    end

    if !self.foreground_color.blank? && sanitize_css_value(self.foreground_color).blank?
      errors.add(:foreground_color, "uses a color that is not allowed.")
    end
  end

  validate :clean_css
  def clean_css
    return if self.css.blank?
    self.css = clean_css_code(self.css)
  end

  scope :public_skins, -> { where(public: true) }
  scope :approved_skins, -> { where(official: true, public: true) }
  scope :unapproved_skins, -> { where(public: true, official: false, rejected: false) }
  scope :rejected_skins, -> { where(public: true, official: false, rejected: true) }
  scope :site_skins, -> { where(type: nil) }
  scope :wizard_site_skins, -> { where("type IS NULL AND (
      margin IS NOT NULL OR
      background_color IS NOT NULL OR
      foreground_color IS NOT NULL OR
      font IS NOT NULL OR
      base_em IS NOT NULL OR
      paragraph_margin IS NOT NULL OR
      headercolor IS NOT NULL OR
      accent_color IS NOT NULL
    )
  ") }

  def self.cached
    where(cached: true)
  end

  def self.in_chooser
    where(in_chooser: true)
  end

  def self.featured
    where(featured: true)
  end

  def self.approved_or_owned_by(user = User.current_user)
    if user.nil?
      approved_skins
    else
      approved_or_owned_by_any([user])
    end
  end

  def self.approved_or_owned_by_any(users)
    where("(public = 1 AND official = 1) OR author_id in (?)", users.map(&:id))
  end

  def self.usable
    where(unusable: false)
  end

  def self.sort_by_recent
    order("updated_at DESC")
  end

  def self.sort_by_recent_featured
    order("featured DESC, updated_at DESC")
  end

  def approved_or_owned_by?(user)
    self.public? && self.official? || author_id == user.id
  end

  def remove_me_from_preferences
    Preference.where(skin_id: self.id).update_all(skin_id: AdminSetting.default_skin_id)
  end

  def editable?
    if self.filename.present?
      return false
    elsif self.official && self.public
      return true if User.current_user.is_a? Admin
    elsif self.author == User.current_user
      return true
    else
      return false
    end
  end

  def byline
    if self.author.is_a? User
      author.login
    else
      ArchiveConfig.APP_SHORT_NAME
    end
  end

  def wizard_settings?
    margin.present? || font.present? || background_color.present? || foreground_color.present? || base_em.present? || paragraph_margin.present? || headercolor.present? || accent_color.present?
  end

  # create the minimal number of files we can, containing all the css for this entire skin
  def cache!
    self.clear_cache!
    self.public = true
    self.official = true
    save!
    css_to_cache = ""
    last_role = ""
    file_count = 1
    skin_dir = Skin.skins_dir + skin_dirname
    FileUtils.mkdir_p skin_dir
    (get_all_parents + [self]).each do |next_skin|
      if next_skin.get_sheet_role != last_role
        # save to file
        if css_to_cache.present?
          cache_filename = skin_dir + "#{file_count}_#{last_role}.css"
          file_count+=1
          File.open(cache_filename, 'w') {|f| f.write(css_to_cache)}
          css_to_cache = ""
        end
        last_role = next_skin.get_sheet_role
      end
      css_to_cache += next_skin.get_css
    end
    # TODO this repetition is all wrong but my brain is fried
    if css_to_cache.present?
      cache_filename = skin_dir + "#{file_count}_#{last_role}.css"
      File.open(cache_filename, 'w') {|f| f.write(css_to_cache)}
      css_to_cache = ""
    end
    self.cached = true
    save!
  end

  def clear_cache!
    skin_dir = Skin.skins_dir + skin_dirname
    FileUtils.rm_rf skin_dir # clear out old if exists
    self.cached = false
    save!
  end

  def recache_children!
    child_ids = SkinParent.get_all_child_ids(id)
    Skin.where(cached: true, id: child_ids).find_each(&:cache!)
  end

  def get_sheet_role
    "#{get_role}_#{get_media_for_filename}_#{ie_condition}"
  end

  # have to handle any media types that aren't a single alphanumeric word here
  def get_media_for_filename
    ((media.nil? || media.empty?) ? DEFAULT_MEDIA : media).map {|m|
      case
      when m.match(/max-width: 42em/)
        "narrow"
      when m.match(/max-width: 62em/)
        "midsize"
      when m.match(/prefers-color-scheme: dark/)
        "dark"
      when m.match(/prefers-color-scheme: light/)
        "light"
      else
        m
      end
    }.join(".")
  end

  def parse_media_from_filename(media_string)
    media_string.gsub(/narrow/, "only screen and (max-width: 42em)")
      .gsub(/midsize/, "only screen and (max-width: 62em)")
      .gsub(/dark/, "(prefers-color-scheme: dark)")
      .gsub(/light/, "(prefers-color-scheme: light)")
      .gsub(".", ", ")
  end

  def parse_sheet_role(role_string)
    (sheet_role, sheet_media, sheet_ie_condition) = role_string.split('_')
    sheet_media = parse_media_from_filename(sheet_media)
    [sheet_role, sheet_media, sheet_ie_condition]
  end

  def get_css
    if filename
      File.read(Rails.public_path.join(filename))
    else
      css
    end
  end

  def get_media(separator=", ")
    ((media.nil? || media.empty?) ? DEFAULT_MEDIA : media).join(separator)
  end

  def get_role
    self.role || DEFAULT_ROLE
  end

  def get_all_parents
    all_parents = []
    parent_skins.each do |parent|
      all_parents += parent.get_all_parents
      all_parents << parent
    end
    all_parents
  end

  # This is the main function that actually returns code to be embedded in a page
  def get_style(roles_to_include = DEFAULT_ROLES_TO_INCLUDE)
    style = ""

    if self.get_role != "override" && self.get_role != "site" &&
       self.id != AdminSetting.default_skin_id &&
       AdminSetting.default_skin.is_a?(Skin)
      style += AdminSetting.default_skin.get_style(roles_to_include)
    end

    style += self.get_style_block(roles_to_include)
    style.html_safe
  end

  def get_ie_comment(style, ie_condition = self.ie_condition)
    if ie_condition.present?
      ie_comment= "<!--[if "
      ie_comment += "lte " if ie_condition.match(/or_lower/)
      ie_comment += "gte " if ie_condition.match(/or_higher/)
      ie_comment += "IE"
      ie_comment += " #{$1}" if ie_condition.match(/IE(\d)/)
      ie_comment += "]>" + style + "<![endif]-->"
    else
      style
    end
  end

  # This builds the stylesheet, so the order is important
  def get_wizard_settings
    style = ""

    style += font_size_styles(base_em) if base_em.present?

    style += font_styles(font) if font.present?

    style += background_color_styles(background_color) if background_color.present?

    style += paragraph_margin_styles(paragraph_margin) if paragraph_margin.present?

    style += foreground_color_styles(foreground_color) if foreground_color.present?

    style += header_styles(headercolor) if headercolor.present?

    style += accent_color_styles(accent_color) if accent_color.present?

    style += work_margin_styles(margin) if margin.present?

    style
  end

  def get_style_block(roles_to_include)
    block = ""
    if self.cached?
      # cached skin in a directory
      block = get_cached_style(roles_to_include)
    else
      # recursively get parents
      parent_skins.each do |parent|
        block += parent.get_style_block(roles_to_include) + "\n"
      end

      # finally get this skin
      if roles_to_include.include?(get_role)
        if self.filename.present?
          block += get_ie_comment(stylesheet_link(self.filename, get_media))
        else
          if (wizard_block = get_wizard_settings).present?
            block += '<style type="text/css" media="' + get_media + '">' + wizard_block + '</style>'
          end
          if self.css.present?
            block += get_ie_comment('<style type="text/css" media="' + get_media + '">' + self.css + '</style>')
          end
        end
      end
    end
    return block
  end

  def get_cached_style(roles_to_include)
    block = ""
    self_skin_dir = Skin.skins_dir + self.skin_dirname
    Skin.skin_dir_entries(self_skin_dir, /^\d+_(.*)\.css$/).each do |sub_file|
      if sub_file.match(/^\d+_(.*)\.css$/)
        (sheet_role, sheet_media, sheet_ie_condition) = parse_sheet_role($1)
        if roles_to_include.include?(sheet_role)
          block += get_ie_comment(stylesheet_link(SKIN_PATH + self.skin_dirname + sub_file, sheet_media), sheet_ie_condition) + "\n"
        end
      end
    end
    block
  end

  def stylesheet_link(file, media)
    # we want one and only one / in the url path
    '<link rel="stylesheet" type="text/css" media="' + media + '" href="/' + file.gsub(/^\/*/,"") + '" />'
  end

  def self.naturalized(string)
    string.scan(/[^\d]+|[\d]+/).collect { |f| f.match(/\d+(\.\d+)?/) ? f.to_f : f }
  end

  def self.load_site_css
    Skin.skin_dir_entries(Skin.site_skins_dir, /^\d+\.\d+$/).each do |version|
      version_dir = "#{Skin.site_skins_dir + version}/"
      if File.directory?(version_dir)
        # let's load up the file
        skins = []
        Skin.skin_dir_entries(version_dir, /^(\d+)-(.*)\.css/).each do |skin_file|
          filename = SITE_SKIN_PATH + version + '/' + skin_file
          skin_file.match(/^(\d+)-(.*)\.css/)
          position = $1.to_i
          title = $2
          title.gsub!(/(\-|\_)/, ' ')
          description = "Version #{version} of the #{title} component (#{position}) of the default archive site design."
          firstline = File.open(version_dir + skin_file, &:readline)
          skin_role = "site"
          if firstline.match(/ROLE: (\w+)/)
            skin_role = $1
          end
          skin_media = ["screen"]
          if firstline.match(/MEDIA: (.*?) ENDMEDIA/)
            skin_media = $1.split(/,\s?/)
          elsif firstline.match(/MEDIA: (\w+)/)
            skin_media = [$1]
          end
          skin_ie = ""
          if firstline.match(/IE_CONDITION: (\w+)/)
            skin_ie = $1
          end

          full_title = "Archive #{version}: (#{position}) #{title}"
          skin = Skin.find_by(title: full_title)
          if skin.nil?
            skin = Skin.new
          end

          # update the attributes
          skin.title ||= full_title
          skin.filename = filename
          skin.description = description
          skin.public = true
          skin.media = skin_media
          skin.role = skin_role
          skin.ie_condition = skin_ie
          skin.unusable = true
          skin.official = true
          File.open(version_dir + 'preview.png', 'rb') {|preview_file| skin.icon = preview_file}
          skin.save!
          skins << skin
        end

        # set up the parent relationship of all the skins in this version
        top_skin = Skin.find_by(title: "Archive #{version}")
        if top_skin
          top_skin.clear_cache! if top_skin.cached?
          top_skin.skin_parents.delete_all
        else
          top_skin = Skin.new(title: "Archive #{version}", css: "", description: "Version #{version} of the default Archive style.",
                              public: true, role: "site", media: ["screen"])
        end
        File.open(version_dir + 'preview.png', 'rb') {|preview_file| top_skin.icon = preview_file}
        top_skin.official = true
        top_skin.save!
        skins.each_with_index do |skin, index|
          skin_parent = top_skin.skin_parents.build(child_skin: top_skin, parent_skin: skin, position: index+1)
          skin_parent.save!
        end
        if %w(staging production).include? Rails.env
          top_skin.cache!
        end
      end
    end
  end

  # get the directory name for the skin file
  def skin_dirname
    "skin_#{self.id}_#{self.title.gsub(/[^\w]/, '_')}/".downcase
  end

  def self.skins_dir
    Rails.public_path.join(SKIN_PATH).to_s
  end

  def self.skin_dir_entries(dir, regex)
    Dir.entries(dir).select {|f| f.match(regex)}.sort_by {|f| Skin.naturalized(f.to_s)}
  end

  def self.site_skins_dir
    Rails.public_path.join(SITE_SKIN_PATH).to_s
  end

  # Get the most recent version and find the topmost skin
  def self.get_current_version
    Skin.skin_dir_entries(Skin.site_skins_dir, /^\d+\.\d+$/).last
  end

  def self.get_current_site_skin
    current_version = Skin.get_current_version
    if current_version
      Skin.find_by(title: "Archive #{Skin.get_current_version}", official: true)
    else
      nil
    end
  end

  def self.default
    Skin.find_by(title: "Default", official: true) || Skin.create_default
  end

  def self.create_default
    transaction do
      skin = Skin.find_or_initialize_by(title: "Default")

      skin.official = true
      skin.public = true
      skin.role = "site"
      skin.css = ""
      skin.set_thumbnail_from_current_version

      skin.save!
      skin
    end
  end

  def self.set_default_to_current_version
    transaction do
      default_skin = default

      default_skin.set_thumbnail_from_current_version

      parent_skin = get_current_site_skin
      if parent_skin && default_skin.parent_skins != [parent_skin]
        default_skin.skin_parents.destroy_all
        default_skin.skin_parents.build(parent_skin: parent_skin, position: 1)
      end

      default_skin.save!
    end
  end

  def set_thumbnail_from_current_version
    current_version = self.class.get_current_version

    icon_path = if current_version
                  self.class.site_skins_dir + current_version + "/preview.png"
                else
                  self.class.site_skins_dir + "preview.png"
                end

    File.open(icon_path) do |icon_file|
      self.icon = icon_file
    end
  end
end
