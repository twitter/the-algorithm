class ScheduledReindexJob
  MAIN_CLASSES = %w(Pseud Tag Work Bookmark Series ExternalWork).freeze

  def self.perform(reindex_type)
    classes = case reindex_type
              when 'main', 'background'
                MAIN_CLASSES
              when 'stats'
                %w(StatCounter)
              end
    classes.each{ |klass| run_queue(klass, reindex_type) }
  end

  def self.run_queue(klass, reindex_type)
    IndexQueue.from_class_and_label(klass, reindex_type).run
  end

end

