$(function() {
    document.addEventListener("deviceready", onDeviceReady, true);
});

function onDeviceReady() {
    document.addEventListener("backbutton", function(e){ 
        navigator.app.exitApp();
    });
    $(".bajaj-footer").css("display", "block");
    DBHandler.initDatabase();
}



function loginCheck() {


    // var producturl="http://api.afterbuy.co/afterbuy/v1/consumers/product-details/?access_token=<>";

    // if (!isNetworkAvailable()) {
    //     $("#error-msg-login-window").html("Network Unavailable. Please connect to internet and retry");
    //     $("#error-msg-login-window").css("display", "block");
    //     $('#error-msg').css('visibility', 'visible');
    //     return false;
    // } else {

        var un = $('#Email').val();
        var pw = $('#Passwd').val();
        var formData = {
            // "email": un,
            "username": un,
            "password": pw
        };
        serilizedData = JSON.stringify(formData);
        console.log('formData',formData);
        console.log('serilizedData',serilizedData);
            //$(".errorDivClass").html("Error Message");
        var err = validateLogin(un, pw);
        if (err != '') {
            $("#error-msg-login-window").html(err);
            $("#error-msg-login-window").css("display", "block");
            return false;
        } else {
            // sessionStorage.setItem('username',un);
            // window.location = "home.html";
            showLoading();
            $.ajax({
                //
                // type: 'POST',
                //  url: 'http://qa.bajaj.gladminds.co/v1/gm-users/login/',
                // data: serilizedData,
                // dataType: 'json',
                // success: function(log_data, status) {
                //     console.log('log_data',log_data);
                //     console.log('status',status);
                //     alert(log_data.status);
                //     if (log_data.status == 1) {

                //         localStorage.setItem('access_token', log_data.access_token)
                //             // sessionStorage.setItem('user_group',log_data.user_group);
                //         localStorage.setItem('user_id', un);
                //         // alert(log_data.login_id+","+log_data.user_group+","+log_data.rep_manager_id);
                //         // localStorage.setItem("isRegistered", true);

                //         fetchDatabase(log_data.access_token);

                //     } else if (log_data.status == 0) {
                //         //alert(status);
                //         $("#error-msg-login-window").html(log_data.message);
                //         localStorage.setItem("isRegistered", false);
                //         $("#error-msg-login-window").css("display", "block");
                //     }
                //     hideLoading();

                // },
                //
                 type: 'GET',
                 url: 'js/api/user.json',
                // data: serilizedData,
                dataType: 'json',
                success: function(log_data, status) {
                    console.log('log_data',log_data);
                    console.log('status',status);
                    // alert(log_data.status);
                    // if (log_data.email == "ticl_dealer@bajajauto.co.in" && log_data.password== "dealer123") {
                    if (log_data.email == formData.username && log_data.password== formData.password) {
                        localStorage.setItem('email', log_data.email)
                            // sessionStorage.setItem('user_group',log_data.user_group);
                        // localStorage.setItem('user_id', un);
                        // alert(log_data.login_id+","+log_data.user_group+","+log_data.rep_manager_id);
                        // localStorage.setItem("isRegistered", true);

                        fetchDatabase(log_data.email);
                    } else{
                        //alert(status);
                        $("#error-msg-login-window").html('Invalid Credentials');
                        localStorage.setItem("isRegistered", false);
                        $("#error-msg-login-window").css("display", "block");
                    }
                    hideLoading();

                },
                error: function(e) {
                    console.log('e',e);
                    $("#error-msg-login-window").html('You are not registered. Please contact administrator');
                    $('#error-msg').css('visibility', 'visible');
                    $("#Passwd,#Email").val('');
                    $("#Email").focus();


                    return false;
                }
            });
        }
        return false;
    // }
}

// function fetchDatabase(access_token) {
//     //http://qa.bajaj.gladminds.co/v1/container-lrs/?access_token=<access_token>
//     console.log("Access Token::"+access_token);
//     var serilizedData = JSON.stringify({
//         "access_token": access_token
//     });
//     console.log(access_token);
//     $.ajax({
//         type: 'GET',
//         url: 'js/api/trip.json',
//         //data: serilizedData,
//         dataType: 'json',
//         success: function(log_data, status) {
//             console.log('GET log_data',log_data);
//             console.log('GET status',status);
//             console.log('log_data.objects',status);
//             // alert('First Sync Date is ' + printDate());
//             DBHandler.saveAllRecords(log_data.objects, dataSaved);
//             // localStorage.setItem('sync_date', printDate());

//         },
//         error: function(e) {
//             console.log(e);
//             return false;
//         }
//     });
// }
function fetchDatabase(email) {
    //http://qa.bajaj.gladminds.co/v1/container-lrs/?access_token=<access_token>
    console.log("email::"+email);
    // var serilizedData = JSON.stringify({
    //     "access_token": access_token
    // });
    // console.log(access_token);
    $.ajax({
        type: 'GET',
        url: 'js/api/dealer.json',
        //data: serilizedData,
        dataType: 'json',
        success: function(log_data, status) {
            console.log('GET log_data',log_data);
            console.log('GET status',status);
            console.log('log_data.objects',status);
            // alert('First Sync Date is ' + printDate());
            DBHandler.saveRecordofDealer(log_data.objects, saveRecordofDealerCallback);
            // localStorage.setItem('sync_date', printDate());

        },
        error: function(e) {
            console.log(e);
            return false;
        }
    });

    // $.ajax({
    //     type: 'GET',
    //     url: 'js/api/trip.json',
    //     //data: serilizedData,
    //     dataType: 'json',
    //     success: function(log_data, status) {
    //         console.log('GET log_data',log_data);
    //         console.log('GET status',status);
    //         console.log('log_data.objects',status);
    //         // alert('First Sync Date is ' + printDate());
    //         DBHandler.saveAllRecords(log_data.objects, dataSaved);
    //         // localStorage.setItem('sync_date', printDate());

    //     },
    //     error: function(e) {
    //         console.log(e);
    //         return false;
    //     }
    // });
}

function saveRecordofDealerCallback(result){
    // alert('inside saveRecordofDealerCallback');
    console.log('saveRecordofDealerCallback');
     $.ajax({
        type: 'GET',
        url: 'js/api/trip.json',
        //data: serilizedData,
        dataType: 'json',
        success: function(log_data, status) {
            console.log('GET log_data',log_data);
            console.log('GET status',status);
            console.log('log_data.objects',status);
            // alert('First Sync Date is ' + printDate());
            DBHandler.saveAllRecords(log_data.objects, dataSaved);
            // localStorage.setItem('sync_date', printDate());

        },
        error: function(e) {
            console.log(e);
            return false;
        }
    });

}
// function dataSaved(data) {
//     console.log('data');
//     hideLoading();
//     alert('data is saved');
//     window.location = "home.html";
//    // window.location = "create_grn.html";

// }
function dataSaved(data) {
    // alert('inside dataSaved');
    console.log('trip data');
    // alert('data is saved');
     $.ajax({
        type: 'GET',
        url: 'js/api/invoice.json',
        //data: serilizedData,
        dataType: 'json',
        success: function(log_data, status) {
            console.log('GET log_data',log_data);
            console.log('GET status',status);
            console.log('log_data.objects',status);
            // alert('First Sync Date is ' + printDate());
            DBHandler.saveAllRecordsofInvoice(log_data.objects, dataSaved2);
            // localStorage.setItem('sync_date', printDate());

        },
        error: function(e) {
            console.log(e);
            return false;
        }
    });
}
function dataSave(data){
    console.log('invoice data');
    $('#some').attr('id');
}
function dataSaved2(data) {
    console.log('invoice data');
    // hideLoading();
    // alert('invoice data is saved');
    // window.location = "create_grn.html";
    $.ajax({
        type: 'GET',
        url: 'js/api/chassis.json',
        dataType: 'json',
        success: function(log_data, status) {
            console.log('GET log_data',log_data);
            console.log('GET status',status);
            console.log('log_data.objects',status);
            // alert('saveAllRecordsofInvoiceCallback');
            DBHandler.saveRecordsofChassis(log_data.objects, saveRecordsofChassisCallback);

        },
        error: function(e) {
            console.log(e);
            return false;
        }
    });
}

function saveRecordsofChassisCallback(result) {
    // alert('saveRecordsofChassisCallback');
    console.log('invoice data');
    hideLoading();
    // alert('invoice data is saved');
    // window.location = "create_grn.html";
    // DBHandler.saveAllRecordsofInvoice(log_data.objects, dataSaved2);
    window.location = "create_grn.html";
}



