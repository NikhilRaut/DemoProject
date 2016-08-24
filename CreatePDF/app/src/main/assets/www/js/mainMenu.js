$(function() {
    
    document.addEventListener("deviceready", onDeviceReady, true);
    // showLoading();
});


function onDeviceReady() {
 
// $(document).ready(function(){
     // DBHandler.initDatabase();
     // console.log('Inside MainMenu.js');
     // alert('Inside MainMenu.js');
    // $.ajax({
    //     type: 'GET',
    //     url: 'js/api/mainMenu.json?get_param=value',
    //     data: { get_param: 'value' }, 
    //     dataType: 'json',
    //     success: function(mainMenu_data, status){
    //         var box_id = [{"id" : "1"},{"id" : "2"},{"id" : "3"},{"id" : "4"}]

    //         var brand_logo_icon = localStorage.setItem("brandLogo", mainMenu_data.brand_logo)

    //         var header_logo = '<img id="bajaj-logo" class="img-responsive header-brand-logo" src='+localStorage.getItem("brandLogo")+' alt="Bajaj">';
    //         $(".header_logo_menu").append(header_logo)

    //         var permission = localStorage.getItem("permission");

    //         for (var i=0;i<mainMenu_data.legends.length;i++) {
    //             var option_name = mainMenu_data.legends[i].option_name;
    //             var className = mainMenu_data.legends[i].className;
    //             var url = mainMenu_data.legends[i].img_path;

    //             var main_html = '<div class="col-lg-3 col-sm-6 col-xs-12 options-block links_blk_'+box_id[i].id+'"><div class="clearfix menuTable-wrapper"><div class="portlet '+className+' "><div class="portlet-heading"><div class="portlet-title"><h4>'+option_name+'</h4></div><div class="clearfix"></div></div><div id="orangePortlet" class="panel-collapse collapse in"><div class="portlet-body"><ul class="menu-options-list">';

    //             for(var j=0;j<mainMenu_data.legends[i].url.length;j++) {

    //                 var service_name = mainMenu_data.legends[i].url[j].name;
    //                 var service_url = mainMenu_data.legends[i].url[j].href;

    //                 if(service_url != "#") {
    //                     main_html +='<li style="display:inline-flex"><i class="fa fa-arrow-right mainMenu-arrow"></i><a href="'+service_url+'">'+service_name+'</a><img class="new_link_symbol img-responsive" src="img/star-new.png"/></li>';    
    //                 } else {
    //                     main_html +='<li><i class="fa fa-arrow-right mainMenu-arrow"></i><a href="'+service_url+'">'+service_name+'</a></li>';
    //                 }

                    
    //             }

    //             main_html+='</ul></div><div class="category-image" style="background:url('+url+')no-repeat;"></div></div></div></div></div>';
                

    //             $("#mainMenu-area").append(main_html);  

    //             if(permission == 2) {
    //                 $(".links_blk_1").css("display", "none");
    //                 $(".links_blk_3").css("display", "none");
    //                 $(".links_blk_4").css("display", "none");
    //             }

    //         }
    //     },
    //     error : function(e) {
    //         console.log(e)
    //     }
    // });
// });


}