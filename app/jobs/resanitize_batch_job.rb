# A job for resanitizing all fields for a class, and saving the results to the
# database. Takes the name of a class and an array of IDs.
class ResanitizeBatchJob < ApplicationJob
  include HtmlCleaner

  retry_on StandardError, attempts: 5, wait: 10.minutes

  queue_as :resanitize

  def perform(klass_name, ids)
    klass = klass_name.constantize

    # Go through all of the fields allowing HTML and look for ones that this
    # klass has, then calculate a list of pairs like:
    #   [["summary", "summary_sanitizer_version"],
    #    ["notes", "notes_sanitizer_version"]]
    fields_to_sanitize = []

    ArchiveConfig.FIELDS_ALLOWING_HTML.each do |field|
      next unless klass.has_attribute?(field) &&
                  klass.has_attribute?("#{field}_sanitizer_version")

      fields_to_sanitize << [field, "#{field}_sanitizer_version"]
    end

    # Go through each record in the batch, and check the sanitizer version of
    # all the fields that need processing. If any field has a sanitizer version
    # less than the archive's SANITIZER_VERSION, run the sanitizer on the field
    # and re-save it to the database.
    klass.where(id: ids).each do |record|
      record.with_lock do
        fields_to_sanitize.each do |field, sanitizer_version|
          next if record[sanitizer_version] &&
                  record[sanitizer_version] >= ArchiveConfig.SANITIZER_VERSION

          record[field] = sanitize_value(field, record[field])
          record[sanitizer_version] = ArchiveConfig.SANITIZER_VERSION
        end

        record.save(validate: false)
      end
    end
  end
end
