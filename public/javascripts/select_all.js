// To be included only where needed:
// tag_wranglings/index, tags/wrangle, admin/spam
// TODO: Refactor to be less repetitive

$j(document).ready(function(){
  $j("#wrangle_all_select").click(function() {
    $j("#wrangulator").find(":checkbox[name='selected_tags[]']").each(function(index, ticky) {
        $j(ticky).prop("checked", true);
      });
  });
  $j("#wrangle_all_deselect").click(function() {
    $j("#wrangulator").find(":checkbox[name='selected_tags[]']").each(function(index, ticky) {
        $j(ticky).prop("checked", false);
      });
  });
  $j("#canonize_all_select").click(function() {
    $j("#wrangulator").find(":checkbox[name='canonicals[]']").each(function(index, ticky) {
        $j(ticky).prop("checked", true);
      });
  });
  $j("#canonize_all_deselect").click(function() {
    $j("#wrangulator").find(":checkbox[name='canonicals[]']").each(function(index, ticky) {
        $j(ticky).prop("checked", false);
      });
  });
  $j("#spam_all_select").click(function() {
    $j("#spam_works").find(":checkbox[name='spam[]']").each(function(index, ticky) {
        $j(ticky).prop("checked", true);
      });
  });
  $j("#spam_all_deselect").click(function() {
    $j("#spam_works").find(":checkbox[name='spam[]']").each(function(index, ticky) {
        $j(ticky).prop("checked", false);
      });
  });
  $j("#ham_all_select").click(function() {
    $j("#spam_works").find(":checkbox[name='ham[]']").each(function(index, ticky) {
        $j(ticky).prop("checked", true);
      });
  });
  $j("#ham_all_deselect").click(function() {
    $j("#spam_works").find(":checkbox[name='ham[]']").each(function(index, ticky) {
        $j(ticky).prop("checked", false);
      });
  });
})
