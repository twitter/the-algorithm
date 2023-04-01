# frozen_string_literal: true
module ExportsHelper

  def send_csv_data(content_array, filename)
    send_data(export_csv(content_array), filename: filename, type: :csv)
  end

  # Tab-separated CSV with utf-16le encoding (unicode) and byte order
  # mark. This seems to be the only variant Excel can get
  # automatically into proper table format. OpenOffice handles it
  # well, too.
  def export_csv(content_array)
    csv_data = content_array.map { |x| x.to_csv(col_sep: "\t", encoding: "utf-8") }.join
    byte_order_mark = "\uFEFF"
    (byte_order_mark + csv_data).encode("utf-16le", "utf-8", invalid: :replace, undef: :replace, replace: "")
  end
end
