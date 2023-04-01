namespace :Tag do
  desc "Reset common taggings - slow"
  task(reset_common: :environment) do
    Work.find_each do |w|
      print "." if w.id.modulo(100) == 0; STDOUT.flush
      #w.update_common_tags
    end
    puts "Common tags reset."
  end

  desc "Reset tag count"
  task(reset_count: :environment) do
    Tag.find_each do |t|
      t.taggings_count
    end
    puts "Tag count reset."
  end

  desc "Reset taggings count for obviously wrong taggings_count"
  task(fix_taggings_count: :environment) do
    tag_scope = Tag.where("taggings_count_cache < 0")
    tag_count = tag_scope.count
    tag_scope.each_with_index do |tag, index|
      puts "#{index} / #{tag_count}"
      tag.taggings_count
    end
    puts "Taggings count for less-than-zero counts has been reset."
  end

  desc "Update relationship has_characters"
  task(update_has_characters: :environment) do
    Relationship.find_each do |relationship|
      relationship.update_attribute(:has_characters, true) unless relationship.characters.blank?
    end
  end

  desc "Delete unused tags"
  task(delete_unused: :environment) do
    deleted_names = []
    Tag.where(canonical: false, merger_id: nil, taggings_count_cache: 0).each do |t|
      if t.taggings.count.zero? && t.child_taggings.count.zero? && t.set_taggings.count.zero?
        deleted_names << t.name
        t.destroy
      end
    end
    unless deleted_names.blank?
      puts "The following #{deleted_names.length} unused tags were deleted:"
      puts deleted_names.join(", ")
    end
  end

  desc "Convert non-canonical warnings into freeforms"
  task(convert_non_canonical_warnings_to_freeforms: :environment) do
    default_warning = ArchiveWarning.find_by(name: ArchiveConfig.WARNING_DEFAULT_TAG_NAME)
    warnings = ArchiveWarning.where(canonical: false)
    puts "Total non-canonical warnings: #{warnings.count}"

    warnings.find_each do |warning|
      puts("#{warning.name} (#{warning.works.count})") && STDOUT.flush

      warning.works.find_each do |work|
        # If the only warning is non-canonical, add a default warning
        # so the work won't be left with no warnings.
        work.archive_warnings << default_warning if work.archive_warnings.count <= 1
      end

      warning.update_attribute(:type, "Freeform")
    end
  end

  desc "Delete unused admin post tags"
  task(delete_unused_admin_post_tags: :environment) do
    AdminPostTag.joins("LEFT JOIN `admin_post_taggings` ON admin_post_taggings.admin_post_tag_id = admin_post_tags.id").where("admin_post_taggings.id IS NULL").destroy_all
  end

  desc "Clean up orphaned taggings"
  task(clean_up_taggings: :environment) do
    Tagging.find_each { |t| t.destroy if t.taggable.nil? }
    CommonTagging.find_each { |t| t.destroy if t.common_tag.nil? }
  end

  desc "Reset filter taggings"
  task(reset_filters: :environment) do
    puts "Adding jobs for work filter updates to the reindex_world queue:"

    Work.update_filters(async_update: true,
                        job_queue: :reindex_world,
                        reindex_queue: :world) do
      print(".") && STDOUT.flush
    end

    print("\n") && STDOUT.flush

    puts "Adding jobs for external work filter updates to the reindex_world queue:"
    ExternalWork.update_filters(async_update: true,
                                job_queue: :reindex_world,
                                reindex_queue: :world) do
      print(".") && STDOUT.flush
    end

    print("\n") && STDOUT.flush

    puts "All jobs enqueued! Once all jobs have finished running, call rake search:run_world_index_queue."
  end

  desc "Reset inherited meta taggings"
  task(reset_meta_tags: :environment) do
    InheritedMetaTagUpdater.update_all { print(".") && STDOUT.flush }
    print("\n") && STDOUT.flush
  end

  desc "Reset filter counts"
  task(reset_filter_counts: :environment) do
    FilterCount.set_all
  end

  desc "Reset filter counts from date"
  task(unsuspend_filter_counts: :environment) do
    admin_settings = AdminSetting.current
    if admin_settings && admin_settings.suspend_filter_counts_at
      FilterTagging.update_filter_counts_since(admin_settings.suspend_filter_counts_at)
    end
  end

  desc "Clean up invalid CommonTaggings"
  task(destroy_invalid_common_taggings: :environment) do
    count = 0

    CommonTagging.destroy_invalid do |ct, valid|
      unless valid
        puts "Deleting invalid CommonTagging: " \
             "#{ct.filterable.try(:name)} > #{ct.common_tag.try(:name)}"
        puts ct.errors.full_messages
      end

      if ((count += 1) % 1000).zero?
        puts "Processed #{count} CommonTaggings."
      end
    end

    puts "Processed #{count} CommonTaggings."
  end

  desc "Clean up invalid MetaTaggings"
  task(destroy_invalid_meta_taggings: :environment) do
    count = 0

    MetaTagging.destroy_invalid do |mt, valid|
      unless valid
        puts "Deleting invalid MetaTagging: " \
             "#{mt.meta_tag.try(:name)} > #{mt.sub_tag.try(:name)}"
        puts mt.errors.full_messages
      end

      if ((count += 1) % 1000).zero?
        puts "Processed #{count} MetaTaggings."
      end
    end

    puts "Processed #{count} MetaTaggings."
  end
end
