namespace :resanitize do
  desc "Re-run the sanitizer on all fields."
  task(all: :environment) do
    [
      AbuseReport,
      AdminActivity,
      AdminBanner,
      AdminPost,
      AdminSetting,
      Bookmark,
      Chapter,
      Collection,
      CollectionProfile,
      Comment,
      ExternalWork,
      Feedback,
      GiftExchange,
      KnownIssue,
      OwnedTagSet,
      Profile,
      Prompt,
      PromptMeme,
      Pseud,
      Question::Translation,
      Series,
      Skin,
      Work,
      WranglingGuideline
    ].each do |klass|
      next unless klass.exists?

      puts "Enqueueing all #{klass} objects for resanitization."

      klass.find_in_batches.with_index do |batch, index|
        puts "Enqueuing batch #{index + 1} of #{klass} objects."
        ResanitizeBatchJob.perform_later(klass.to_s, batch.map(&:id))
      end
    end
  end
end
