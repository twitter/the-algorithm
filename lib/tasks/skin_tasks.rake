namespace :skins do

  def ask(message)
    print message
    STDIN.gets.chomp.strip
  end

  def replace_or_new(skin_content)
    skin = Skin.new
    if skin_content.match(/REPLACE:\s*(\d+)/)
      id = $1.to_i
      skin = Skin.where(:id => id).first
      unless skin
        puts "Couldn't find skin with id #{id} to replace"
        return nil
      end
    end
    skin
  end

  def set_parents(skin, parent_names)
    # clear existing ones
    SkinParent.where(:child_skin_id => skin.id).delete_all

    parent_position = 1
    parents = parent_names.split(/,\s?/).map {|pn| pn.strip}
    parents.each do |parent_name|
      if parent_name.match(/^(\d+)$/)
        parent_skin = Skin.where("title LIKE 'Archive 2.0: (#{parent_name})%'").first
      elsif parent_name.blank?
        puts "Empty parent name for #{skin.title}"
        next
      else
        parent_skin = Skin.where(:title => parent_name).first
      end
      unless parent_skin
        puts "Couldn't find parent #{parent_name} to add, skipping"
        next
      end
      if (parent_skin.role == "site" || parent_skin.role == "override") && skin.role != "override"
        skin.role = "override"
        skin.save or puts "Problem updating skin #{skin.title} to be replacement skin: #{skin.errors.full_messages.join(', ')}"
        next
      end
      p = skin.skin_parents.build(:parent_skin => parent_skin, :position => parent_position)
      if p.save
        parent_position += 1
      else
        puts "Skipping skin parent #{parent_name}: #{p.errors.full_messages.join(', ')}"
      end
    end
  end

  def get_user_skins
    dir = Skin.site_skins_dir + 'user_skins_to_load'
    default_preview_filename = "#{dir}/previews/default_preview.png"
    user_skin_files = Dir.entries(dir).select {|f| f.match(/css$/)}
    skins = []
    user_skin_files.each do |skin_file|
      skins << File.read("#{dir}/#{skin_file}").split(/\/\*\s*END SKIN\s*\*\//)
    end
    skins.flatten!
  end

  desc "Purge user skins parents"
  task(:purge_user_skins_parents => :environment) do
    get_user_skins.each do |skin_content|
      skin = replace_or_new(skin_content)
      if skin.new_record? && skin_content.match(/SKIN:\s*(.*)\s*\*\//)
        skin = Skin.find_by_title($1.strip)
      end
      skin.skin_parents.delete_all
    end
  end

  desc "Load user skins"
  task(:load_user_skins => :environment) do
    replace = ask("Replace existing skins with same titles? (y/n) ") == "y"
    Rake::Task['skins:purge_user_skins_parents'].invoke if replace

    author = User.find_by_login("lim")
    dir = Skin.site_skins_dir + 'user_skins_to_load'

    skins = get_user_skins
    skins.each do |skin_content|
      next if skin_content.blank?

      # Determine if we're replacing or creating new
      next unless (skin = replace_or_new(skin_content))

      # set the title and preview
      if skin_content.match(/SKIN:\s*(.*)\s*\*\//)
        title = $1.strip
        if (oldskin = Skin.find_by_title(title)) && oldskin.id != skin.id
          if replace
            skin = oldskin
          else
            puts "Existing skin with title #{title} - did you mean to replace? Skipping."
            next
          end
        end
        skin.title = title
        preview_filename = "#{dir}/previews/#{title.gsub(/[^\w\s]+/, '')}.png"
        unless File.exists?(preview_filename)
          puts "No preview filename #{preview_filename} found for #{title}"
          preview_filename = "#{dir}/previews/default_preview.png"
        end
        File.open(preview_filename, 'rb') {|preview_file| skin.icon = preview_file}
      else
        puts "No skin title found for skin #{skin_content}"
        next
      end

      # set the css and make public
      skin.css = skin_content
      skin.public = true
      skin.official = true
      skin.author = author unless skin.author

      if skin_content.match(/DESCRIPTION:\s*(.*?)\*\//m)
        skin.description = "<pre>#{$1}</pre>"
      end
      if skin_content.match(/PARENT_ONLY/)
        skin.unusable = true
      end

      # make sure we have valid skin now
      if skin.save
        puts "Saved skin #{skin.title}"
      else
        puts "Problem with skin #{skin.title}: #{skin.errors.full_messages.join(', ')}"
        next
      end

      # recache any cached skins
      if skin.cached?
        skin.cache!
      end

      # set parents
      if skin_content.match(/PARENTS:\s*(.*)\s*\*\//)
        parent_string = $1
        set_parents(skin, parent_string)
      end
    end

  end

  desc "Load site skins"
  task(:load_site_skins => :environment) do
    settings = AdminSetting.first
    if settings.default_skin_id.nil?
      settings.default_skin_id = Skin.default.id
      settings.save(validate: false)
    end
    Skin.load_site_css
    Skin.set_default_to_current_version
  end

  desc "Cache all site skins in the skin chooser"
  task(cache_chooser_skins: :environment) do
    # The default skin can be changed to something other than Skin.default via
    # admin settings, so we want to cache that skin, not Skin.default.
    skins = Skin.where(id: AdminSetting.default_skin_id).or(Skin.in_chooser)
    successes = []
    failures = []

    skins.each do |skin|
      if skin.cache!
        successes << skin.title
      else
        failures << skin.title
      end
    end
    puts
    puts("Cached #{successes.join(',')}") if successes.any?
    puts("Couldn't cache #{failures.join(',')}") if failures.any?
    STDOUT.flush
  end

  desc "Remove all existing skins from preferences"
  task(:disable_all => :environment) do
    default_id = AdminSetting.default_skin_id
    Preference.update_all(:skin_id => default_id)
  end

  desc "Unapprove all existing official skins"
  task(:unapprove_all => :environment) do
    default_id = AdminSetting.default_skin_id
    Skin.where("id != ?", default_id).update_all(:official => false)
  end

end
