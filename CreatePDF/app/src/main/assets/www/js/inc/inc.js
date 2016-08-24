// if(localStorage.getItem("user_id")===null)
//   relogin();
localhost = "../js/api/";

 apiURL = '//demos-dynamo.elasticbeanstalk.com/apis/';
//apiURL = '../pne/apis/';

// apiURL = '//localhost/solidusPnE/websrc/apis/';
//apiURL = '//localhost/www/solidusPnE/websrc-15-10-2015/websrc/apis/';
var months = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'June', 'July', 'Aug', 'Sept', 'Oct', 'Nov', 'Dec'];


function checkAuthetication() {
    access_token = localStorage.getItem("access_token");
    if (access_token == null) {
        relogin();
    }
}

function getParameterByName(url) {
    url = url.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
    var regex = new RegExp("[\\?&]" + url + "=([^&#]*)"),
    results = regex.exec(location.search);
    return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
}

function showLoading(){
    $("#loading").show();
}

function hideLoading(){
    $("#loading").hide();
}

function formatDate(date) {
    var xDate = new Date(date);

    var dd = xDate.getDate();
    var mm = xDate.getMonth() + 1; 
    var yyyy = xDate.getFullYear(); 

    return (yyyy+ '-' +mm+ '-' +dd);
}

function relogin() {
	localStorage.clear();
	window.location.href = "index.html";
}

function logout_user() {
    $.ajax({
              url: apiURL+ 'index.php?action=logout',
              success: function(data){
                $("#logout-window").modal('hide');
                hideLoading();
                localStorage.clear();
                window.location.href = "index.html";
              },error:function(){
                    hideLoading();
                    return false;
              }
          });
}

function numberWithCommas(x) {
    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}




