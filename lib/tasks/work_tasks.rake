namespace :work do
  desc "Purge drafts created more than a month ago"
  task(:purge_old_drafts => :environment) do
    count = 0
    Work.unposted.where('works.created_at < ?', 1.month.ago).find_each do |work|
      begin
        work.destroy!
        count += 1
      rescue StandardError => e
        puts "The following error occurred while trying to destroy draft #{work.id}:"
        puts "#{e.class}: #{e.message}"
        puts e.backtrace
      end
    end
    puts "Unposted works (#{count}) created more than one month ago have been purged"
  end

  desc "create missing hit counters"
  task(:missing_stat_counters => :environment) do
    Work.find_each do |work|
      counter = work.stat_counter
      unless counter
        counter = StatCounter.create(:work => work, :hit_count => 1)
      end
    end
  end

  # Usage: rake work:reset_word_counts[en]
  desc "Reset word counts for works in the specified language"
  task(:reset_word_counts, [:lang] => :environment) do |_t, args|
    language = Language.find_by(short: args.lang)
    raise "Invalid language: '#{args.lang}'" if language.nil?

    works = Work.where(language: language)
    print "Resetting word count for #{works.count} '#{language.short}' works: "

    works.find_in_batches do |batch|
      batch.each do |work|
        work.chapters.each do |chapter|
          chapter.content_will_change!
          chapter.save
        end
        work.save
      end
      print(".") && STDOUT.flush
    end
    puts && STDOUT.flush
  end
end
