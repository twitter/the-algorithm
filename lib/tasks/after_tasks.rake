namespace :After do
  # Keep only the most recent tasks, i.e., about two years' worth.
  # If you need older tasks, check GitHub.

  desc "Update the mapping for the work index"
  task(update_work_mapping: :environment) do
    WorkIndexer.create_mapping
  end

  desc "Fix tags with extra spaces"
  task(fix_tags_with_extra_spaces: :environment) do
    total_tags = Tag.count
    total_batches = (total_tags + 999) / 1000
    puts "Inspecting #{total_tags} tags in #{total_batches} batches"

    report_string = ["Tag ID", "Old tag name", "New tag name"].to_csv
    Tag.find_in_batches.with_index do |batch, index|
      batch_number = index + 1
      progress_msg = "Batch #{batch_number} of #{total_batches} complete"

      batch.each do |tag|
        next unless tag.name != tag.name.squish

        old_tag_name = tag.name
        new_tag_name = old_tag_name.gsub(/[[:space:]]/, "_")

        new_tag_name << "_" while Tag.find_by(name: new_tag_name)
        tag.update_attribute(:name, new_tag_name)

        report_row = [tag.id, old_tag_name, new_tag_name].to_csv
        report_string += report_row
      end

      puts(progress_msg) && STDOUT.flush
    end
    puts(report_string) && STDOUT.flush
  end

  desc "Fix works imported with a noncanonical Teen & Up Audiences rating tag"
  task(fix_teen_and_up_imported_rating: :environment) do
    borked_rating_tag = Rating.find_by!(name: "Teen & Up Audiences")
    canonical_rating_tag = Rating.find_by!(name: ArchiveConfig.RATING_TEEN_TAG_NAME)

    work_ids = []
    invalid_work_ids = []
    borked_rating_tag.works.find_each do |work|
      work.ratings << canonical_rating_tag
      work.ratings = work.ratings - [borked_rating_tag]
      if work.save
        work_ids << work.id
      else
        invalid_work_ids << work.id
      end
      print(".") && STDOUT.flush
    end

    unless work_ids.empty?
      puts "Converted '#{borked_rating_tag.name}' rating tag on #{work_ids.size} works:"
      puts work_ids.join(", ")
      STDOUT.flush
    end

    unless invalid_work_ids.empty?
      puts "The following #{invalid_work_ids.size} works failed validations and could not be saved:"
      puts invalid_work_ids.join(", ")
      STDOUT.flush
    end
  end

  desc "Clean up noncanonical rating tags"
  task(clean_up_noncanonical_ratings: :environment) do
    canonical_not_rated_tag = Rating.find_by!(name: ArchiveConfig.RATING_DEFAULT_TAG_NAME)
    noncanonical_ratings = Rating.where(canonical: false)
    puts "There are #{noncanonical_ratings.size} noncanonical rating tags."

    next if noncanonical_ratings.empty?

    puts "The following noncanonical Ratings will be changed into Additional Tags:"
    puts noncanonical_ratings.map(&:name).join("\n")

    work_ids = []
    invalid_work_ids = []
    noncanonical_ratings.find_each do |tag|
      works_using_tag = tag.works
      tag.update_attribute(:type, "Freeform")

      works_using_tag.find_each do |work|
        next unless work.ratings.empty?

        work.ratings = [canonical_not_rated_tag]
        if work.save
          work_ids << work.id
        else
          invalid_work_ids << work.id
        end
        print(".") && STDOUT.flush
      end
    end

    unless work_ids.empty?
      puts "The following #{work_ids.size} works were left without a rating and successfully received the Not Rated rating:"
      puts work_ids.join(", ")
      STDOUT.flush
    end

    unless invalid_work_ids.empty?
      puts "The following #{invalid_work_ids.size} works failed validations and could not be saved:"
      puts invalid_work_ids.join(", ")
      STDOUT.flush
    end
  end

  desc "Clean up noncanonical category tags"
  task(clean_up_noncanonical_categories: :environment) do
    Category.where(canonical: false).find_each do |tag|
      tag.update_attribute(:type, "Freeform")
      puts "Noncanonical Category tag '#{tag.name}' was changed into an Additional Tag."
    end
    STDOUT.flush
  end

  desc "Add default rating to works missing a rating"
  task(add_default_rating_to_works: :environment) do
    work_count = Work.count
    total_batches = (work_count + 999) / 1000
    puts("Checking #{work_count} works in #{total_batches} batches") && STDOUT.flush
    updated_works = []

    Work.find_in_batches.with_index do |batch, index|
      batch_number = index + 1

      batch.each do |work|
        next unless work.ratings.empty?

        work.ratings << Rating.find_by!(name: ArchiveConfig.RATING_DEFAULT_TAG_NAME)
        work.save
        updated_works << work.id
      end
      puts("Batch #{batch_number} of #{total_batches} complete") && STDOUT.flush
    end
    puts("Added default rating to works: #{updated_works}") && STDOUT.flush
  end

  desc "Fix pseuds with invalid icon data"
  task(fix_invalid_pseud_icon_data: :environment) do
    # From validates_attachment_content_type in pseuds model.
    valid_types = %w[image/gif image/jpeg image/png]

    # If you change either of these, update lookup_invalid_pseuds.rb in
    # otwcode/otw-scripts to ensure the proper users are notified.
    pseuds_with_invalid_icons = Pseud.where("icon_file_name IS NOT NULL AND icon_content_type NOT IN (?)", valid_types)
    pseuds_with_invalid_text = Pseud.where("CHAR_LENGTH(icon_alt_text) > ? OR CHAR_LENGTH(icon_comment_text) > ?", ArchiveConfig.ICON_ALT_MAX, ArchiveConfig.ICON_COMMENT_MAX)

    invalid_pseuds = [pseuds_with_invalid_icons, pseuds_with_invalid_text].flatten.uniq
    invalid_pseuds_count = invalid_pseuds.count

    skipped_pseud_ids = []

    # Update the pseuds.
    puts("Updating #{invalid_pseuds_count} pseuds") && STDOUT.flush

    invalid_pseuds.each do |pseud|
      # Change icon content type to jpeg if it's jpg.
      pseud.icon_content_type = "image/jpeg" if pseud.icon_content_type == "image/jpg"
      # Delete the icon if it's not a valid type.
      pseud.icon = nil unless (valid_types + ["image/jpg"]).include?(pseud.icon_content_type)
      # Delete the icon alt text if it's too long.
      pseud.icon_alt_text = "" if pseud.icon_alt_text.length > ArchiveConfig.ICON_ALT_MAX
      # Delete the icon comment if it's too long.
      pseud.icon_comment_text = "" if pseud.icon_comment_text.length > ArchiveConfig.ICON_COMMENT_MAX
      skipped_pseud_ids << pseud.id unless pseud.save
      print(".") && STDOUT.flush
    end
    if skipped_pseud_ids.any?
      puts
      puts("Couldn't update #{skipped_pseud_ids.size} pseud(s): #{skipped_pseud_ids.join(',')}") && STDOUT.flush
    end
  end

  desc "Backfill renamed_at for existing users"
  task(add_renamed_at_from_log: :environment) do
    total_users = User.all.size
    total_batches = (total_users + 999) / 1000
    puts "Updating #{total_users} users in #{total_batches} batches"

    User.find_in_batches.with_index do |batch, index|
      batch.each do |user|
        renamed_at_from_log = user.log_items.where(action: ArchiveConfig.ACTION_RENAME).last&.created_at
        next unless renamed_at_from_log

        user.update_column(:renamed_at, renamed_at_from_log)
      end

      batch_number = index + 1
      progress_msg = "Batch #{batch_number} of #{total_batches} complete"
      puts(progress_msg) && STDOUT.flush
    end
    puts && STDOUT.flush
  end

  desc "Fix threads for comments from 2009"
  task(fix_2009_comment_threads: :environment) do
    def fix_comment(comment)
      comment.with_lock do
        if comment.reply_comment?
          comment.update_column(:thread, comment.commentable.thread)
        else
          comment.update_column(:thread, comment.id)
        end
        comment.comments.each { |reply| fix_comment(reply) }
      end
    end

    incorrect = Comment.top_level.where("thread != id")
    total = incorrect.count

    puts "Updating #{total} thread(s)"

    incorrect.find_each.with_index do |comment, index|
      fix_comment(comment)

      puts "Fixed thread #{index + 1} out of #{total}" if index % 100 == 99
    end
  end

  desc "Convert remaining chapter kudos into work kudos"
  task(clean_up_chapter_kudos: :environment) do
    kudos = Kudo.where(commentable_type: "Chapter")
    kudos_count = kudos.count

    puts("Updating #{kudos_count} chapter kudos") && STDOUT.flush

    indestructible_kudo_ids = []
    unupdatable_kudo_ids = []

    kudos.find_each do |kudo|
      if kudo.commentable.nil? || kudo.commentable.work.nil?
        indestructible_kudo_ids << kudo.id unless kudo.destroy
        print(".") && STDOUT.flush
        next
      end

      kudo.commentable = kudo.commentable.work
      unless kudo.save
        if kudo.errors.keys == [:ip_address] || kudo.errors.keys == [:user_id]
          # If it's a uniqueness problem, orphan the kudo and re-save.
          kudo.ip_address = nil
          kudo.user_id = nil
          unupdatable_kudo_ids << kudo.id unless kudo.save
        else
          # In other cases, let's be cautious and only log.
          unupdatable_kudo_ids << kudo.id
        end
      end
      print(".") && STDOUT.flush
    end

    puts
    puts("Couldn't destroy #{indestructible_kudo_ids.size} kudo(s): #{indestructible_kudo_ids.join(',')}") if indestructible_kudo_ids.any?
    puts("Couldn't update #{unupdatable_kudo_ids.size} kudo(s): #{unupdatable_kudo_ids.join(',')}") if unupdatable_kudo_ids.any?
    STDOUT.flush
  end

  desc "Remove translation_admin role"
  task(remove_translation_admin_role: :environment) do
    r = Role.find_by(name: "translation_admin")
    r&.destroy
  end

  # This is the end that you have to put new tasks above.
end
