class WorkLink < ApplicationRecord
  belongs_to :work

  def self.create_or_increment(work_id, full_url, count_to_add=1)
    url = full_url.gsub(/\?(.*)$/, '') if full_url # chop any arguments to collapse urls
    return if work_id.blank? || url.blank? || url == "-" || url.match(/#{ArchiveConfig.APP_HOST}/) # skip internal references
    link = WorkLink.find_or_create_by(work_id: work_id, url: url)
    link.count ||= 0
    link.count += count_to_add
    begin
      link.save
    rescue
      # don't die if there was a race condition and the db didn't let us insert a duplicate record
    end
  end

end
