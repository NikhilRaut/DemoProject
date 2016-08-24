$(function() {
    document.addEventListener("deviceready", onDeviceReady, true);
    // showLoading();
});


function onDeviceReady() {
	console.log('inside create_grn');
	$('.sandbox-container').datepicker({
	   	format: "dd-mm-yyyy",
	   	autoclose: true,
	   	todayHighlight: true
    });
}
// $(document).ready(function(){

        /**---------- Start of datepicker js----------**/
    // $('.sandbox-container').datepicker({
	   // 	format: "dd-mm-yyyy",
	   // 	autoclose: true,
	   // 	todayHighlight: true
    // });
     /**---------- End of date picker js----------**/
   

// })

$(document).on('click', '.createGrnButton', function(){
	var rel = $(this).attr('data-rel');
	
	var getCheckedInvoices = $(this).parents(".demo"+rel).find(".select_for_grn");

	console.log(getCheckedInvoices)
	// if($(getCheckedInvoices).checked) {
	// 	alert("checked")
	// }
})

$(".createGrnButton").click(function(){
	window.location = "grn_details.html"
})

function logout_user() {
	window.location = "index.html";
}