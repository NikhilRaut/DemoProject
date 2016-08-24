var version_num="1.0.4";
$(document).ready(function() {
    // create_footer();
});

function showLoading() {
    $("#loading").show();
}

function hideLoading() {
    $("#loading").hide();
}
function showSyncing() {
    $("#syncing").show();
}

function hideSyncing() {
    $("#syncing").hide();
}

function validateLogin(username,password)
{
    var errorMsg='';
    if(username=='' && password=='')
        errorMsg="Please enter credentials"
    else if(username=='')
        // errorMsg="Please enter username"
        errorMsg="Invalid credentials"
    else if(password=='')
        // errorMsg="Please enter password"
        errorMsg="Invalid credentials"
    
    return errorMsg;
}


function getParameterByName(url) {
    url = url.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
    var regex = new RegExp("[\\?&]" + url + "=([^&#]*)"),
        results = regex.exec(location.search);
    return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
}

$(document).ajaxStop(function() {
    $("#loading").hide();
});

// function fetchData() {

//    // alert(dateStr);
//  //   console.log("In fetch data Access Token : " + accessToken);
//     $.ajax({
//         /*url : 'http://qa.bajaj.gladminds.co/v1/container-lrs/?access_token='+accessToken+'&modified_date__gte='+dateStr,*/
//         url: 'http://qa.bajaj.gladminds.co/v1/container-lrs/?access_token=' + localStorage.getItem('access_token') + '&modified_date__gte=' + localStorage.getItem('sync_date'),
//         type: 'GET',
//         dataType: 'json',
//         success: function(result, status) {

//             //alert("Length of result is : " + result.objects.length );
//             if(result.objects.length>0)
//             {
//               DBHandler.saveAllRecords(result.objects, null);
//             }
//             //
//         },
//         error: function(e) {
//             if (e.status == 401) {
//                 alert("Something is wrong. Please try again");
//                 return;
//             }
//         }
//     });
// }

function create_footer(version) {

    var footer = '<span>All rights reserved by Bajaj Auto. Ltd.</span><br>\
      <div style="margin:2px 0"><span class="framed circle" style="line-height:20px;margin:10px 20px 10px 0;"><img style="width:16px;height:16px" src="img/phone.png" alt="phone icon" align="top"> <a href="tel:917847011011">+917847011011</a></span> <span class="framed circle"><img style="width:16px;height:16px" src="img/mail.svg" alt="Mail icon" align="top"> <a href="mailto:hello@gladminds.co">hello@gladminds.co</a></span></div>\
      <div style="margin-bottom:10px;"><span>Powered by <a href="//www.gladminds.co/#products" target="_blank">GladMinds Connect Platform</a></span>, \
      <span>'+version_num+'</span>';

    $(".bajaj-footer").append(footer);
}


// Called when background mode has been activated

function setupAutoSync() {
    //alert('Auto sync revoked');

    // // Enable background mode
    // cordova.plugins.backgroundMode.enable();
    // cordova.plugins.backgroundMode.configure({
    //     silent: true
    // })
    // cordova.plugins.backgroundMode.onactivate = function() {
    //     alert('activating backgroundMode');

    //     cordova.plugins.backgroundMode.configure({
    //         silent: true
    //     })
    //     console.log('starting timer');

    //     setTimeout(function() {
    //         // Modify the currently displayed notification
    //         console.log('In setTimeout');

    //         var networkState = navigator.network.connection.type;
    //         var isConnected = false;

    //         var states = {};
    //         if (states[networkState] == states[Connection.NONE])
    //             isConnected = false;
    //         else if (states[networkState] == states[Connection.UNKNOWN])
    //             isConnected = false;
    //         else
    //             isConnected = true;

    //         if (isConnected) {
    //             console.log('Background fetching');
    //             fetchData();
    //         }
    //         // cordova.plugins.backgroundMode.configure({
    //         //     text: 'Running in background for more than 5s now.'
    //         // });
    //     }, 5000);
    // }

    // cordova.plugins.backgroundMode.ondeactivate = function() {
    //     cordova.plugins.backgroundMode.enable();
    //     var networkState = navigator.network.connection.type;

    //     var isConnected = false;

    //     var states = {};
    //     states[Connection.UNKNOWN] = 'Unknown connection';
    //     states[Connection.ETHERNET] = 'Ethernet connection';
    //     states[Connection.WIFI] = 'WiFi connection';
    //     states[Connection.CELL_2G] = 'Cell 2G connection';
    //     states[Connection.CELL_3G] = 'Cell 3G connection';
    //     states[Connection.CELL_4G] = 'Cell 4G connection';
    //     states[Connection.NONE] = 'No network connection';
    //     if (states[networkState] == states[Connection.NONE])
    //         isConnected = false;
    //     else if (states[networkState] == states[Connection.UNKNOWN])
    //         isConnected = false;
    //     else
    //         isConnected = true;

    //     if (isConnected) {
    //         fetchData();
    //     }
    //     cordova.plugins.backgroundMode.configure({
    //         silent: true
    //     })

    // };
}


function logoutUser() {
    // http://qa.bajaj.gladminds.co/v1/gm-users/logout/?access_token=
    var logoutUrl = qaLogoutURL + "?access_token=" + localStorage.getItem('access_token');
    showLoading();
    $.ajax({
        url: logoutUrl,
        success: function(data) {
            $("#logout-window").modal('hide');
            hideLoading();
            // bootbox.alert(data.message,function(){
            localStorage.clear();
            DBHandler.resetTables();

            window.location = "index.html";
            // });        
        },
        error: function() {
            alert("logout failure");
            hideLoading();
            return false;
        }
    });
}


function isNetworkAvailable()
{
      var networkState = navigator.network.connection.type;

        var isConnected = false;

        var states = {};
        states[Connection.UNKNOWN] = 'Unknown connection';
        states[Connection.ETHERNET] = 'Ethernet connection';
        states[Connection.WIFI] = 'WiFi connection';
        states[Connection.CELL_2G] = 'Cell 2G connection';
        states[Connection.CELL_3G] = 'Cell 3G connection';
        states[Connection.CELL_4G] = 'Cell 4G connection';
        states[Connection.NONE] = 'No network connection';
        if (states[networkState] == states[Connection.NONE])
            isConnected = false;
        else if (states[networkState] == states[Connection.UNKNOWN])
            isConnected = false;
        else
            isConnected = true;

return isConnected;
}

function printDate() {
    var temp = new Date();
    dateStr = padStr(temp.getFullYear()) + "-" +
        padStr(1 + temp.getMonth()) + "-" +
        padStr(temp.getDate());

    timeStr = padStr(temp.getHours()) + ":" +
        padStr(temp.getMinutes()) + ":" +
        padStr(temp.getSeconds());

    return dateStr;
}

function padStr(i) {
    return (i < 10) ? "0" + i : "" + i;
}


function relogin() {
    localStorage.clear();
    window.location.href = "/";
}