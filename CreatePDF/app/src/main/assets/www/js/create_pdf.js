
// $(document).on('click', '#makePdf', function(){ 
 $(document).on('click', '.printButton', function(){ 
    var grnFlag=localStorage.getItem('grnFlag');
    var base64Data;
    console.log("generating pdf...");
    function encodeImage(src, callback) {
        var canvas = document.createElement('canvas'),
        ctx = canvas.getContext('2d'),
        img = new Image();
        
        img.onload = function(){
            canvas.width  = img.width;
            canvas.height = img.height;
            ctx.drawImage(img, 0, 0, img.width, img.height);
            callback(canvas.toDataURL());
        }
        img.src = src;
    }

    encodeImage('img/logo.png', imageCallback);

    function imageCallback(encodedImage) { 
       // document.getElementById('data').value = encodedImage;
       base64Data=encodedImage;
       console.log('base64Data', base64Data);

       //
       var doc = new jsPDF('p', 'pt', 'a4');
        // doc.addPage();
        doc.setFont("courier");
        doc.setFontType("normal");
        doc.addImage(base64Data, 'PNG', 10, 5, 140, 140);
        doc.text(200, 40, 'GOODS RECEIPT NOTE');
        doc.addImage(base64Data, 'PNG', 440, 5, 140, 140);
        // var columns = ["ID", "Name", "Country"];
        // var rows = [
        //     [1, v, "Tanzania"],
        //     [2, "Nelson", "Kazakhstan"]
        // ];

       
        // doc.autoTable(columns, rows,{
        //     margin: {top: 100},
        //     // beforePageContent: function(data) {
        //     //      doc.text("Header", 20, 110);
        //     // }
        // });
        
        // var v=$('#grn1234').text();
        // var column = ["GRN NUMBER", v];
        // var row = ["", ""];
        // doc.autoTable(column, row,{
        //     margin: {top: 80, left: 5},
            // beforePageContent: function(data) {
            //      doc.text("Header", 20, 110);
            // }
        // });
        

        //grnNumber
        var getColumns_grnNumber = function () {
            return [
                {title: "GRN HEADING", dataKey: "grnHeading"},
                {title: "GRN VALUE", dataKey: "grnNumber"},
            ];
        };

        var getData_grnNumber =function() {
            var data = [];
                data.push({
                    grnHeading: "GRN Number",
                    grnNumber: $('#grn1234').text()
                });
            return data;
        }
        // function getData_grnNumber() {
        //     var data = [];
        //         data.push({
        //             grnHeading: "GRN NUMBER",
        //             grnNumber: $('#grn1234').text()
        //         });
        //     return data;
        // }
        
        doc.autoTable(getColumns_grnNumber(), getData_grnNumber(), {
                margin: {top: 160, left: 10},
                // padding:{top:2, bottom: 2, left: 2, right:2},
                // borderWidth: 0,
                // borderHorizontalWidth: 1,
                // borderColor: '#636363',
                theme: 'grid',
                // borderWidth: 20,
                // borderColor: 0xdddddd,
                tableWidth: 'wrap',
                bodyStyles: {rowHeight: 24, rowWidth:140, valign: 'middle'},
                drawHeaderRow: function() {
                    // Don't draw header row
                    return false;
                },
                columnStyles: {
                    grnHeading: {columnWidth: 110, fillColor: [255, 255, 255], fontSize: 16, fontStyle: 'bold'},
                    grnNumber: {columnWidth: 170, fillColor: [255, 255, 255], fontSize: 16, fontStyle: 'bold'}
                }
        });

        //dealer Name
        var getColumns_dealerName = function () {
            return [
                {title: "dealer Name Heading", dataKey: "dealerNameHeading"},
                {title: "DealerName", dataKey: "dealerName"},
            ];
        };
        console.log('dealerName', $('#dealerName1234').text());
        var getData_dealerName =function() {
            var data2 = [];
                data2.push({
                    dealerNameHeading: "Dealer Name",
                    dealerName: $('#dealerName1234').text()
                });
            return data2;
        }
        
        doc.autoTable(getColumns_dealerName(), getData_dealerName(), {
                margin: {top: 200, left: 10},
                // padding:{top:2, bottom: 2, left: 2, right:2},
                // borderWidth: 0,
                // borderHorizontalWidth: 1,
                // borderColor: '#636363',
                theme: 'grid',
                // borderWidth: 20,
                // borderColor: 0xdddddd,
                tableWidth: 'wrap',
                bodyStyles: {rowHeight: 24, rowWidth:140, valign: 'middle'},
                drawHeaderRow: function() {
                    // Don't draw header row
                    return false;
                },
                columnStyles: {
                    dealerNameHeading: {columnWidth: 110, fillColor: [255, 255, 255], fontSize: 16, fontStyle: 'bold'},
                    dealerName: {columnWidth: 170, fillColor: [255, 255, 255], fontSize: 16, fontStyle: 'bold'}
                }
        });

        //Dealer Code
        var getColumns_dealerCode = function () {
            return [
                {title: "Dealer Code Heading", dataKey: "dealerCodeHeading"},
                {title: "DealerCode", dataKey: "dealerCode"},
            ];
        };
        // console.log('dealerName', $('#dealerName1234').text());
        var getData_dealerCode =function() {
            var data = [];
                data.push({
                    dealerCodeHeading: "Dealer Code",
                    dealerCode: $('#dealerCode1234').text()
                });
            return data;
        }
        
        doc.autoTable(getColumns_dealerCode(), getData_dealerCode(), {
                margin: {top: 225, left: 10},
                // padding:{top:2, bottom: 2, left: 2, right:2},
                // borderWidth: 0,
                // borderHorizontalWidth: 1,
                // borderColor: '#636363',
                theme: 'grid',
                // borderWidth: 20,
                // borderColor: 0xdddddd,
                tableWidth: 'wrap',
                bodyStyles: {rowHeight: 24, rowWidth:140, valign: 'middle'},
                drawHeaderRow: function() {
                    // Don't draw header row
                    return false;
                },
                columnStyles: {
                    dealerCodeHeading: {columnWidth: 110, fillColor: [255, 255, 255], fontSize: 16, fontStyle: 'bold'},
                    dealerCode: {columnWidth: 170, fillColor: [255, 255, 255], fontSize: 16, fontStyle: 'bold'}
                }
        });
        //Region
        var getColumns_region = function () {
            return [
                {title: "Region Heading", dataKey: "regionHeading"},
                {title: "Region", dataKey: "region"},
            ];
        };
       
        var getData_region =function() {
            var data = [];
                data.push({
                    regionHeading: "Region",
                    region: 'RJ'
                });
            return data;
        }
        
        doc.autoTable(getColumns_region(), getData_region(), {
                margin: {top: 250, left: 10},
                // padding:{top:2, bottom: 2, left: 2, right:2},
                // borderWidth: 0,
                // borderHorizontalWidth: 1,
                // borderColor: '#636363',
                theme: 'grid',
                // borderWidth: 20,
                // borderColor: 0xdddddd,
                tableWidth: 'wrap',
                bodyStyles: {rowHeight: 24, rowWidth:140, valign: 'middle'},
                drawHeaderRow: function() {
                    // Don't draw header row
                    return false;
                },
                columnStyles: {
                    regionHeading: {columnWidth: 110, fillColor: [255, 255, 255], fontSize: 16, fontStyle: 'bold'},
                    region: {columnWidth: 170, fillColor: [255, 255, 255], fontSize: 16, fontStyle: 'bold'}
                }
        });
        //City
        var getColumns_city = function () {
            return [
                {title: "City Heading", dataKey: "cityHeading"},
                {title: "city", dataKey: "city"}
            ];
        };
        
        var getData_city =function() {
            var data = [];
                data.push({
                    cityHeading: "City",
                    city: 'SRI GANGANAGAR'
                });
            return data;
        }
        
        doc.autoTable(getColumns_city(), getData_city(), {
                margin: {top: 275, left: 10},
                // padding:{top:2, bottom: 2, left: 2, right:2},
                // borderWidth: 0,
                // borderHorizontalWidth: 1,
                // borderColor: '#636363',
                theme: 'grid',
                // borderWidth: 20,
                // borderColor: 0xdddddd,
                tableWidth: 'wrap',
                bodyStyles: {rowHeight: 24, rowWidth:140, valign: 'middle'},
                drawHeaderRow: function() {
                    // Don't draw header row
                    return false;
                },
                columnStyles: {
                    cityHeading: {columnWidth: 110, fillColor: [255, 255, 255], fontSize: 16, fontStyle: 'bold'},
                    city: {columnWidth: 170, fillColor: [255, 255, 255], fontSize: 16, fontStyle: 'bold'}
                }
        });

         //Transporter Name
        var getColumns_transporterName = function () {
            return [
                {title: "Transporter Name Heading", dataKey: "transporterNameHeading"},
                {title: "TransporterName", dataKey: "transporterName"}
            ];
        };
        var getData_transporterName =function() {
            var data = [];
                data.push({
                    transporterNameHeading: "Transporter Name",
                    transporterName: 'XPRESS LOGISTICS'
                });
            return data;
        }
        
        doc.autoTable(getColumns_transporterName(),getData_transporterName(), {
                margin: {top: 200, left: 300},
                // padding:{top:2, bottom: 2, left: 2, right:2},
                // borderWidth: 0,
                // borderHorizontalWidth: 1,
                // borderColor: '#636363',
                theme: 'grid',
                // borderWidth: 20,
                // borderColor: 0xdddddd,
                styles: {overflow: 'linebreak'},
                tableWidth: 'wrap',
                tableHeight: 'wrap',
                bodyStyles: {valign: 'middle'},
                drawHeaderRow: function() {
                    // Don't draw header row
                    return false;
                },
                columnStyles: {
                    transporterNameHeading: {columnWidth: 110, fillColor: [255, 255, 255], fontSize: 16, fontStyle: 'bold'},
                    transporterName: {columnWidth: 170, fillColor: [255, 255, 255], fontSize: 16, fontStyle: 'bold'}
                }
        });




         //Transporter Code
        var getColumns_transporterCode = function () {
            return [
                {title: "Transporter Code Heading", dataKey: "transporterCodeHeading"},
                {title: "TransporterCode", dataKey: "transporterCode"},
            ];
        };
        // console.log('dealerName', $('#dealerName1234').text());
        var getData_transporterCode =function() {
            var data = [];
                data.push({
                    transporterCodeHeading: "Transporter Code",
                    transporterCode: '30643'
                });
            return data;
        }
        
        doc.autoTable(getColumns_transporterCode(), getData_transporterCode(), {
                margin: {top: 232, left: 300},
                // padding:{top:2, bottom: 2, left: 2, right:2},
                // borderWidth: 0,
                // borderHorizontalWidth: 1,
                // borderColor: '#636363',
                theme: 'grid',
                // borderWidth: 20,
                // borderColor: 0xdddddd,
                styles: {overflow: 'linebreak'},
                tableWidth: 'wrap',
                tableHeight: 'wrap',
                bodyStyles: {valign: 'middle'},
                drawHeaderRow: function() {
                    // Don't draw header row
                    return false;
                },
                columnStyles: {
                    transporterCodeHeading: {columnWidth: 110, fillColor: [255, 255, 255], fontSize: 16, fontStyle: 'bold'},
                    transporterCode: {columnWidth: 170, fillColor: [255, 255, 255], fontSize: 16, fontStyle: 'bold'}
                }
        });

    
        //Truck Number
        var getColumns_truckNumber = function () {
            return [
                {title: "Truck Number Heading", dataKey: "truckNumberHeading"},
                {title: "TruckNumber", dataKey: "truckNumber"},
            ];
        };
        var getData_truckNumber =function() {
            var data = [];
                data.push({
                    truckNumberHeading: "Truck Number",
                    truckNumber: localStorage.getItem('truckno')
                });
            return data;
        }
        
        doc.autoTable(getColumns_truckNumber(), getData_truckNumber(), {
                margin: {top: 264, left: 300},
                // padding:{top:2, bottom: 2, left: 2, right:2},
                // borderWidth: 0,
                // borderHorizontalWidth: 1,
                // borderColor: '#636363',
                styles: {overflow: 'linebreak'},
                theme: 'grid',
                // borderWidth: 20,
                // borderColor: 0xdddddd,
                tableWidth: 'wrap',
                tableHeight: 'wrap',
                bodyStyles: {valign: 'middle'},
                drawHeaderRow: function() {
                    // Don't draw header row
                    return false;
                },
                columnStyles: {
                    truckNumberHeading: {columnWidth: 110, fillColor: [255, 255, 255], fontSize: 16, fontStyle: 'bold'},
                    truckNumber: {columnWidth: 170, fillColor: [255, 255, 255], fontSize: 16, fontStyle: 'bold'}
                }
        });

        doc.line(10, 310, 568, 310);
        doc.setLineWidth(1.5);
        //Invoice Details
         var getColumns_invoiceDetails = function () {
            return [
                {title: "invoice Details Heading", dataKey: "invoiceDetailsHeading"}
            ];
        };
        var getData_invoiceDetails =function() {
            var data = [];
                data.push({
                    invoiceDetailsHeading: "Invoice Details",
                });
            return data;
        }
        
        doc.autoTable(getColumns_invoiceDetails(), getData_invoiceDetails(), {
                margin: {top: 320, left: 10},
                // padding:{top:2, bottom: 2, left: 2, right:2},
                // borderWidth: 0,
                // borderHorizontalWidth: 1,
                // borderColor: '#636363',
                styles: {overflow: 'linebreak'},
                theme: 'grid',
                // borderWidth: 20,
                // borderColor: 0xdddddd,
                tableWidth: 'wrap',
                tableHeight: 'wrap',
                bodyStyles: {valign: 'middle'},
                drawHeaderRow: function() {
                    // Don't draw header row
                    return false;
                },
                columnStyles: {
                    invoiceDetailsHeading: {columnWidth: 160, fillColor: [255, 255, 255], fontSize: 16, fontStyle: 'bold'},
                }
        });
        //Invoice Details
        var getColumns_invoiceDetails2 = function () {
            return [
                {title: "S No", dataKey: "sNo"},
                {title: "Invoice No", dataKey: "invoiceNo"},
                {title: "Invoice Date", dataKey: "invoiceDate"},
                {title: "Plant", dataKey: "plant"},
                {title: "Trip No.", dataKey: "tripNo"},
                {title: "Billed Qty", dataKey: "billedQty"}
            ];
        };
        var count;
        var getData_invoiceDetails2 =function() {
            count=$('#invoiceDetail #tripTable tbody').children().size();
            console.log('count',count);
            var data = [];
            data.push({
                sNo: 'S.NO.',
                invoiceNo: 'Invoice NO.',
                invoiceDate: 'Invoice Date',
                plant: 'Plant',
                tripNo: 'Trip No.',
                billedQty: 'Billed Qtys'
            });
            for(var i=0; i<count; i++){
                
                var value = $('#invoiceDetail #tripTable tbody tr').eq(i).find("td").eq(1).html();    
                console.log('value',value);
                data.push({
                    sNo: $('#invoiceDetail #tripTable tbody tr').eq(i).find("td").eq(0).html(),
                    invoiceNo: $('#invoiceDetail #tripTable tbody tr').eq(i).find("td").eq(1).html(),
                    invoiceDate: $('#invoiceDetail #tripTable tbody tr').eq(i).find("td").eq(2).html(),
                    plant: $('#invoiceDetail #tripTable tbody tr').eq(i).find("td").eq(3).html(),
                    tripNo: $('#invoiceDetail #tripTable tbody tr').eq(i).find("td").eq(4).html(),
                    billedQty: $('#invoiceDetail #tripTable tbody tr').eq(i).find("td").eq(5).html()
                });
            }
            
            return data;
        }
        
        doc.autoTable(getColumns_invoiceDetails2(), getData_invoiceDetails2(), {
                margin: {top: 360, left: 10},
                // padding:{top:2, bottom: 2, left: 2, right:2},
                // borderWidth: 0,
                // borderHorizontalWidth: 1,
                // borderColor: '#636363',
                styles: {overflow: 'linebreak'},
                theme: 'grid',
                // borderWidth: 20,
                // borderColor: 0xdddddd,
                tableWidth: 'wrap',
                tableHeight: 'wrap',
                bodyStyles: {valign: 'middle'},
                drawHeaderRow: function() {
                    // Don't draw header row
                    return false;
                },
                columnStyles: {
                    sNo: {columnWidth: 90, fillColor: [255, 255, 255], fontSize: 14, fontStyle: 'bold'},
                    invoiceNo: {columnWidth: 90, fillColor: [255, 255, 255], fontSize: 14, fontStyle: 'bold'},
                    invoiceDate: {columnWidth: 90, fillColor: [255, 255, 255], fontSize: 14, fontStyle: 'bold'},
                    plant: {columnWidth: 90, fillColor: [255, 255, 255], fontSize: 14, fontStyle: 'bold'},
                    tripNo: {columnWidth: 90, fillColor: [255, 255, 255], fontSize: 14, fontStyle: 'bold'},
                    billedQty: {columnWidth: 90, fillColor: [255, 255, 255], fontSize: 14, fontStyle: 'bold'}
                }
        });
        
        //Damage Details Heading
         var getColumns_damageDetails = function () {
            return [
                {title: "damage Details Heading", dataKey: "damageDetailsHeading"}
            ];
        };
        var getData_damageDetails =function() {
            var data = [];
                data.push({
                    damageDetailsHeading: "Damage Details",
                });
            return data;
        }
        var v=(count)*25;
        console.log('v',v);
        doc.autoTable(getColumns_damageDetails(), getData_damageDetails(), {
                margin: {top: 360+v+28, left: 10},
                // padding:{top:2, bottom: 2, left: 2, right:2},
                // borderWidth: 0,
                // borderHorizontalWidth: 1,
                // borderColor: '#636363',
                styles: {overflow: 'linebreak'},
                theme: 'grid',
                // borderWidth: 20,
                // borderColor: 0xdddddd,
                tableWidth: 'wrap',
                tableHeight: 'wrap',
                bodyStyles: {valign: 'middle'},
                drawHeaderRow: function() {
                    // Don't draw header row
                    return false;
                },
                columnStyles: {
                    damageDetailsHeading: {columnWidth: 160, fillColor: [255, 255, 255], fontSize: 16, fontStyle: 'bold'},
                }
        });
       

        //Damage Details
         var getColumns_damageDetails2 = function () {
            return [
                {title: "S No", dataKey: "sNo"},
                {title: "Chassis No", dataKey: "chassisNo"},
                {title: "Damaged Part", dataKey: "damagedPart"},
                {title: "Type of Damage", dataKey: "typeofDamage"},
                {title: "Brand Name", dataKey: "brandName"},
                {title: "Probable Cause", dataKey: "probableCause"},
                {title: "Damage Loc", dataKey: "damageLoc"},
                {title: "Image", dataKey: "image"}
            ];
        };
        var count2;
        var getData_damageDetails2 =function() {
            count2=$('#damageDetail table tbody').children().size();
            console.log('count2',count2);
            var data = [];
            data.push({
                sNo: 'S.No',
                chassisNo: 'Chassis No.',
                damagedPart: 'Damaged Part',
                typeofDamage: 'Type of Damage',
                brandName: 'Brand Name',
                probableCause: 'Probable Cause',
                damageLoc: 'Damage Loc.',
                image: 'Image'
            });
            if(count2>0){
                for(var i=0; i<count2; i++){
                
                    data.push({
                        sNo: $('#damageDetail table tbody tr').eq(i).find("td").eq(0).html(),
                        chassisNo: $('#damageDetail table tbody tr').eq(i).find("td").eq(1).html(),
                        damagedPart: $('#damageDetail table tbody tr').eq(i).find("td").eq(2).html(),
                        typeofDamage: $('#damageDetail table tbody tr').eq(i).find("td").eq(3).html(),
                        // brandName: $('#damageDetail table tbody tr').eq(i).find("td").eq(4).html(),
                        brandName: '',
                        probableCause: $('#damageDetail table tbody tr').eq(i).find("td").eq(4).html(),
                        damageLoc: $('#damageDetail table tbody tr').eq(i).find("td").eq(5).html(),
                        image: ''
                    });
                }
            }
            
            console.log('data',data);
            return data;

        }
        doc.autoTable(getColumns_damageDetails2(), getData_damageDetails2(), {
                margin: {top: 360+v+28+40, left: 10},
                // padding:{top:2, bottom: 2, left: 2, right:2},
                // borderWidth: 0,
                // borderHorizontalWidth: 1,
                // borderColor: '#636363',
                styles: {overflow: 'linebreak'},
                theme: 'grid',
                // borderWidth: 20,
                // borderColor: 0xdddddd,
                tableWidth: 'wrap',
                tableHeight: 'wrap',
                bodyStyles: {valign: 'middle'},
                drawHeaderRow: function() {
                    // Don't draw header row
                    return false;
                },
                columnStyles: {
                    sNo: {columnWidth: 68, fillColor: [255, 255, 255], fontSize: 14, fontStyle: 'bold'},
                    chassisNo: {columnWidth: 68, fillColor: [255, 255, 255], fontSize: 14, fontStyle: 'bold'},
                    damagedPart: {columnWidth: 68, fillColor: [255, 255, 255], fontSize: 14, fontStyle: 'bold'},
                    typeofDamage: {columnWidth: 68, fillColor: [255, 255, 255], fontSize: 14, fontStyle: 'bold'},
                    brandName: {columnWidth: 68, fillColor: [255, 255, 255], fontSize: 14, fontStyle: 'bold'},
                    probableCause: {columnWidth: 68, fillColor: [255, 255, 255], fontSize: 14, fontStyle: 'bold'},
                    damageLoc: {columnWidth: 68, fillColor: [255, 255, 255], fontSize: 14, fontStyle: 'bold'},
                    image: {columnWidth: 68, fillColor: [255, 255, 255], fontSize: 14, fontStyle: 'bold'}
                   
                }
        });
        var v2=360+v+28+40+32;
        if(count2==0){
            doc.rect(10, v2, 544, 35, 'S');
            doc.text('No Damage Found', 10+250, v2+20);
        }
            
        var v3=360+v+28+40+40+count2*65;
        if(count2>0){
            doc.text(10, v3, 'Upper deck --driver side');
            doc.text(300, v3, 'Lower deck--driver side');
            var row= localStorage.getItem('truckLayoutRow');
            var column= localStorage.getItem('truckLayoutColumn');
            var arr;
            var getColumns_upperLowerDeckTruckLayout = function () {
                arr=new Array();

                for(var i=0; i<=column; i++){
                    var obj={};
                    obj.title='C'+i;
                    obj.dataKey='C'+i;
                    arr.push(obj);
                }
                console.log('arr',arr);
            return arr;
            };
            // var count;
            var a1;
            var a2;
            var a3;
            var getData_upperDeckTruckLayout =function() {
                var damageLoc=$('#damageDetail table tbody tr').eq(0).find("td").eq(5).html();
                // count=$('#invoiceDetail #tripTable tbody').children().size();
                // console.log('count',count);
                var len= arr.length;
                var data = new Array();
                var obj2={};
                for(var j=0; j<len; j++){
                    if(j==0){
                        obj2[arr[j].dataKey]= '';
                    }else{
                         obj2[arr[j].dataKey]= arr[j].dataKey;
                    }
                
                }
                data.push(obj2);
                a1=damageLoc.charAt(0);
                a2=damageLoc.charAt(2);
                a3=damageLoc.charAt(4);
                for(var l=1; l<=row; l++){
                    var obj3={};
                    var keys = Object.keys(obj2);
                    console.log('length',keys.length);
                    for (var h = 0; h < keys.length; h++) {
                        var val = obj2[keys[h]];
                        console.log('val',val);
                        var v4='C0';
                        if(val==''){
                            obj3[v4]='R'+l;
                        }else if(l==a2 && 'C'+a3==val && a1=='U'){
                            obj3[val]='a';
                        }else{
                            obj3[val]='';
                        }

                    }
                    console.log('obj3',obj3);
                    data.push(obj3);
               }
             
                return data;
            }
            

            // if(a1 == 'U'){
                    doc.autoTable(getColumns_upperLowerDeckTruckLayout(), getData_upperDeckTruckLayout(), {
                    margin: {top: v3+22, left: 10},
                    // padding:{top:2, bottom: 2, left: 2, right:2},
                    // borderWidth: 0,
                    // borderHorizontalWidth: 1,
                    // borderColor: '#636363',
                    styles: {overflow: 'linebreak'},
                    theme: 'grid',
                    // borderWidth: 20,
                    // borderColor: 0xdddddd,
                    tableWidth: 'wrap',
                    tableHeight: 'wrap',
                    bodyStyles: {valign: 'middle'},
                    drawHeaderRow: function() {
                        // Don't draw header row
                        return false;
                    },

                });
            // }
            //lowerDeckTruckLayout


            var getData_lowerDeckTruckLayout =function() {
                var damageLoc=$('#damageDetail table tbody tr').eq(0).find("td").eq(5).html();
                // count=$('#invoiceDetail #tripTable tbody').children().size();
                // console.log('count',count);
                var len= arr.length;
                var data = new Array();
                var obj2={};
                for(var j=0; j<len; j++){
                    if(j==0){
                        obj2[arr[j].dataKey]= '';
                    }else{
                         obj2[arr[j].dataKey]= arr[j].dataKey;
                    }
                
                }
                data.push(obj2);
                a1=damageLoc.charAt(0);
                a2=damageLoc.charAt(2);
                a3=damageLoc.charAt(4);
                for(var l=1; l<=row; l++){
                    var obj3={};
                    // var len2=_.size(obj2);
                    // for(var h=0; h<len2; h++){

                    // }
                    var keys = Object.keys(obj2);
                    console.log('length',keys.length);
                    for (var h = 0; h < keys.length; h++) {
                        var val = obj2[keys[h]];
                        console.log('val',val);
                        var v4='C0';
                        if(val==''){
                            obj3[v4]='R'+l;
                        }else if(l==a2 && 'C'+a3==val && a1=='L'){
                            obj3[val]='X';
                        }else{
                            obj3[val]='';
                        }

                    }
                    console.log('obj3',obj3);
                    data.push(obj3);
                   
               }
               
                 
                return data;
            }

             // if(a1 == 'L'){
                    doc.autoTable(getColumns_upperLowerDeckTruckLayout(), getData_lowerDeckTruckLayout(), {
                    margin: {top: v3+22, left: 300},
                    // padding:{top:2, bottom: 2, left: 2, right:2},
                    // borderWidth: 0,
                    // borderHorizontalWidth: 1,
                    // borderColor: '#636363',
                    styles: {overflow: 'linebreak'},
                    theme: 'grid',
                    // borderWidth: 20,
                    // borderColor: 0xdddddd,
                    tableWidth: 'wrap',
                    tableHeight: 'wrap',
                    bodyStyles: {valign: 'middle'},
                    drawHeaderRow: function() {
                        // Don't draw header row
                        return false;
                    },
                    // columnStyles: {
                    //     sNo: {columnWidth: 90, fillColor: [255, 255, 255], fontSize: 14, fontStyle: 'bold'},
                    //     invoiceNo: {columnWidth: 90, fillColor: [255, 255, 255], fontSize: 14, fontStyle: 'bold'},
                    //     invoiceDate: {columnWidth: 90, fillColor: [255, 255, 255], fontSize: 14, fontStyle: 'bold'},
                    //     plant: {columnWidth: 90, fillColor: [255, 255, 255], fontSize: 14, fontStyle: 'bold'},
                    //     tripNo: {columnWidth: 90, fillColor: [255, 255, 255], fontSize: 14, fontStyle: 'bold'},
                    //     billedQty: {columnWidth: 90, fillColor: [255, 255, 255], fontSize: 14, fontStyle: 'bold'}
                    // }
                });
            // }

        }


            doc.addPage();
            //Shrtage Details
            doc.rect(10, 25, 200, 55, 'S');
            doc.text('Shortage Details', 35, 55);

            var getColumns_shortageDetails = function () {
                return [
                    {title: "Sr No", dataKey: "sNo"},
                    {title: "Brand Variant", dataKey: "brandVariant"},
                    {title: "Shortage Part", dataKey: "shortagePart"},
                    {title: "Shortage QTY", dataKey: "shortageQty"}
                ];
            };
            var count4;
            var getData_shortageDetails =function() {
                count4=$('#shortageDetail table tbody').children().size();
                console.log('count4',count4);
                var data = [];
                data.push({
                    sNo: 'Sr.No.',
                    brandVariant: 'Brand Variant',
                    shortagePart: 'Shortage Part',
                    shortageQty: 'Shortage QTY'
                });
                if(count4>0){
                    for(var i=0; i<count4; i++){
                    
                        data.push({
                            sNo: $('#shortageDetail table tbody tr').eq(i).find("td").eq(0).html(),
                            brandVariant: $('#shortageDetail table tbody tr').eq(i).find("td").eq(1).html(),
                            shortagePart: '',
                            shortageQty: $('#shortageDetail table tbody tr').eq(i).find("td").eq(2).html()
                        });
                    }
                }
                
                // console.log('data',data);
                return data;

            }


            doc.autoTable(getColumns_shortageDetails(), getData_shortageDetails(), {
                    margin: {top: 100, left: 10},
                    // padding:{top:2, bottom: 2, left: 2, right:2},
                    // borderWidth: 0,
                    // borderHorizontalWidth: 1,
                    // borderColor: '#636363',
                    styles: {overflow: 'linebreak'},
                    theme: 'grid',
                    // borderWidth: 20,
                    // borderColor: 0xdddddd,
                    tableWidth: 'wrap',
                    tableHeight: 'wrap',
                    bodyStyles: {valign: 'middle'},
                    drawHeaderRow: function() {
                        // Don't draw header row
                        return false;
                    },
                    columnStyles: {
                        sNo: {columnWidth: 140, fillColor: [255, 255, 255], fontSize: 14, fontStyle: 'bold'},
                        brandVariant: {columnWidth: 140, fillColor: [255, 255, 255], fontSize: 14, fontStyle: 'bold'},
                        shortagePart: {columnWidth: 140, fillColor: [255, 255, 255], fontSize: 14, fontStyle: 'bold'},
                        shortageQty: {columnWidth: 140, fillColor: [255, 255, 255], fontSize: 14, fontStyle: 'bold'}
                       
                    }
            });
            if(count4==0){
                doc.rect(10, 100+21, 560, 40, 'S');
                doc.text('No Shortage Found', 200, 100+35);
                doc.line(10, 100+22+50, 568, 100+22+50);
            }
            var v4=100+60+count4*28;
            if(count4>0){
                doc.line(10, v4, 568, v4);
            }
           

            //delay Details
            // var t1=v4;
            doc.rect(10, v4+20, 200, 55, 'S');
            doc.text('Delay Details', 40, v4+20+25);
            //Date
            doc.rect(100+10, v4+20+50+55, 100, 30, 'S');
            doc.text('Date', 110, v4+20+55+50+22);
            //Time
            doc.rect(100+110, v4+20+50+55, 100, 30, 'S');
            doc.text('Time', 100+120+10, v4+20+50+55+22);

             //Reporting at Dealer
            var getColumns_reportingAtDealer = function () {
                return [
                    {title: "Reporting at Dealer", dataKey: "reportingAtDealer"},
                    {title: "Date", dataKey: "date"},
                    {title: "Time", dataKey: "time"}
                ];
            };

            var getData_reportingAtDealer =function() {
                var data = [];
                    data.push({
                        reportingAtDealer: "Reporting at Dealer",
                        date: '10-08-2015',
                        time: '01:15PM'
                    });
                return data;
            }
            
            doc.autoTable(getColumns_reportingAtDealer(), getData_reportingAtDealer(), {
                    margin: {top: v4+20+50+55+30, left: 10},
                    // padding:{top:2, bottom: 2, left: 2, right:2},
                    // borderWidth: 0,
                    // borderHorizontalWidth: 1,
                    // borderColor: '#636363',
                    theme: 'grid',
                    styles: {overflow: 'linebreak'},
                    // borderWidth: 20,
                    // borderColor: 0xdddddd,
                    tableWidth: 'wrap',
                    tableHeight: 'wrap',
                    bodyStyles: {valign: 'middle'},
                    drawHeaderRow: function() {
                        // Don't draw header row
                        return false;
                    },
                    columnStyles: {
                        reportingAtDealer: {columnWidth: 100, fillColor: [255, 255, 255], fontSize: 16, fontStyle: 'bold'},
                        date: {columnWidth: 100, fillColor: [255, 255, 255], fontSize: 16, fontStyle: 'bold'},
                        time: {columnWidth: 100, fillColor: [255, 255, 255], fontSize: 16, fontStyle: 'bold'}
                        
                    }
            });
            //Unloading at dealer
            var getColumns_unloadingAtDealer = function () {
                return [
                    {title: "Unloading at dealer", dataKey: "unloadingAtDealer"},
                    {title: "Date", dataKey: "date"},
                    {title: "Time", dataKey: "time"}
                ];
            };

            var getData_unloadingAtDealer =function() {
                var data = [];
                    data.push({
                        unloadingAtDealer: "Unloading at dealer",
                        date: '10-08-2015',
                        time: '03:15PM'
                    });
                return data;
            }
            
            doc.autoTable(getColumns_unloadingAtDealer(), getData_unloadingAtDealer(), {
                    margin: {top: v4+20+50+55+30+32, left: 10},
                    // padding:{top:2, bottom: 2, left: 2, right:2},
                    // borderWidth: 0,
                    // borderHorizontalWidth: 1,
                    // borderColor: '#636363',
                    theme: 'grid',
                    styles: {overflow: 'linebreak'},
                    // borderWidth: 20,
                    // borderColor: 0xdddddd,
                    tableWidth: 'wrap',
                    tableHeight: 'wrap',
                    bodyStyles: {valign: 'middle'},
                    drawHeaderRow: function() {
                        // Don't draw header row
                        return false;
                    },
                    columnStyles: {
                        unloadingAtDealer: {columnWidth: 100, fillColor: [255, 255, 255], fontSize: 16, fontStyle: 'bold'},
                        date: {columnWidth: 100, fillColor: [255, 255, 255], fontSize: 16, fontStyle: 'bold'},
                        time: {columnWidth: 100, fillColor: [255, 255, 255], fontSize: 16, fontStyle: 'bold'}
                        
                    }
            });
            //Reason for the Delay
            var getColumns_reasonFortheDelay = function () {
                return [
                    {title: "Reason for the Delay", dataKey: "reasonFortheDelay"},
                    {title: "Reason", dataKey: "reason"}
                   
                ];
            };

            var getData_reasonFortheDelay =function() {
                var data = [];
                    data.push({
                        reasonFortheDelay: "Reason for the Delay",
                        reason: 'NO DELAY'
                    });
                return data;
            }
            
            doc.autoTable( getColumns_reasonFortheDelay(), getData_reasonFortheDelay(), {
                    margin: {top: v4+20+50+55+30+32+32, left: 10},
                    // padding:{top:2, bottom: 2, left: 2, right:2},
                    // borderWidth: 0,
                    // borderHorizontalWidth: 1,
                    // borderColor: '#636363',
                    theme: 'grid',
                    styles: {overflow: 'linebreak'},
                    // borderWidth: 20,
                    // borderColor: 0xdddddd,
                    tableWidth: 'wrap',
                    tableHeight: 'wrap',
                    bodyStyles: {valign: 'middle'},
                    drawHeaderRow: function() {
                        // Don't draw header row
                        return false;
                    },
                    columnStyles: {
                        reasonFortheDelay: {columnWidth: 100, fillColor: [255, 255, 255], fontSize: 16, fontStyle: 'bold'},
                        reason: {columnWidth: 200, fillColor: [255, 255, 255], fontSize: 16, fontStyle: 'bold'}
                        
                    }
            });
            
            //Expected Date of Delivery
            var getColumns_expectedDateofDelivery = function () {
                return [
                    {title: "Expected Date of Delivery", dataKey: "expectedDateofDelivery"},
                    {title: "Reason", dataKey: "reason"}
                   
                ];
            };

            var getData_expectedDateofDelivery =function() {
                var data = [];
                    data.push({
                        expectedDateofDelivery: "Expected Date of Delivery",
                        reason: '08-08-2015'
                    });
                return data;
            }
            
            doc.autoTable( getColumns_expectedDateofDelivery(), getData_expectedDateofDelivery(), {
                    margin: {top: v4+20+50+55, left: 120+200},
                    // padding:{top:2, bottom: 2, left: 2, right:2},
                    // borderWidth: 0,
                    // borderHorizontalWidth: 1,
                    // borderColor: '#636363',
                    theme: 'grid',
                    styles: {overflow: 'linebreak'},
                    // borderWidth: 20,
                    // borderColor: 0xdddddd,
                    tableWidth: 'wrap',
                    tableHeight: 'wrap',
                    bodyStyles: {valign: 'middle'},
                    drawHeaderRow: function() {
                        // Don't draw header row
                        return false;
                    },
                    columnStyles: {
                        expectedDateofDelivery: {columnWidth: 120, fillColor: [255, 255, 255], fontSize: 16, fontStyle: 'bold'},
                        reason: {columnWidth: 120, fillColor: [255, 255, 255], fontSize: 16, fontStyle: 'bold'}
                        
                    }
            });
            //Delay in Days
            var getColumns_delayInDays = function () {
                return [
                    {title: "Delay in Days", dataKey: "delayInDays"},
                    {title: "Reason", dataKey: "reason"}
                   
                ];
            };

            var getData_delayInDays =function() {
                var data = [];
                    data.push({
                        delayInDays: "Delay in Days",
                        reason: ''
                    });
                return data;
            }
            
            doc.autoTable( getColumns_delayInDays(), getData_delayInDays(), {
                    margin: {top: v4+20+50+55+32, left: 120+200},
                    // padding:{top:2, bottom: 2, left: 2, right:2},
                    // borderWidth: 0,
                    // borderHorizontalWidth: 1,
                    // borderColor: '#636363',
                    theme: 'grid',
                    styles: {overflow: 'linebreak'},
                    // borderWidth: 20,
                    // borderColor: 0xdddddd,
                    tableWidth: 'wrap',
                    tableHeight: 'wrap',
                    bodyStyles: {valign: 'middle'},
                    drawHeaderRow: function() {
                        // Don't draw header row
                        return false;
                    },
                    columnStyles: {
                        delayInDays: {columnWidth: 120, fillColor: [255, 255, 255], fontSize: 16, fontStyle: 'bold'},
                        reason: {columnWidth: 120, fillColor: [255, 255, 255], fontSize: 16, fontStyle: 'bold'}
                        
                    }
            });
            //Reporting at Dealer
            // doc.rect(10, v4+20+50+55+30, 100, 55, 'S');
            // doc.text('Reporting at Dealer', 11, v4+20+50+55+30);

            // //Date of Reporting at Dealer
            // doc.rect(10, v4+20, 200, 55, 'S');
            // doc.text('10-08-2015', 40, v4+20+25);
            // //Time of Reporting at Dealer
            // doc.rect(10, v4+20, 200, 55, 'S');
            // doc.text('01:15PM', 40, v4+20+25);

            // //Unloading at dealer
            // doc.rect(10, v4+20, 200, 55, 'S');
            // doc.text('Unloading at dealer', 40, v4+20+25);
            // //Date of Unloading at dealer
            // doc.rect(10, v4+20, 200, 55, 'S');
            // doc.text('10-08-2015', 40, v4+20+25);
            // //Time of Unloading at dealer
            // doc.rect(10, v4+20, 200, 55, 'S');
            // doc.text('03:15PM', 40, v4+20+25);

            // //Reason for the Delay
            // doc.rect(10, v4+20, 200, 55, 'S');
            // doc.text('Reason for the Delay', 40, v4+20+25);
            // //Reason for the Delay Detail
            // doc.rect(10, v4+20, 200, 55, 'S');
            // doc.text('NO DELAY', 40, v4+20+25);


            // //Expected Date of Delivery
            // doc.rect(10, v4+20, 200, 55, 'S');
            // doc.text('Expected Date of Delivery', 40, v4+20+25);
            // //Expected Date of Delivery Detail
            // doc.rect(10, v4+20, 200, 55, 'S');
            // doc.text('08-08-2015', 40, v4+20+25);

            // //Delay in Days 
            // doc.rect(10, v4+20, 200, 55, 'S');
            // doc.text('Delay in Days', 40, v4+20+25);
            // ///Delay in Days Detail
            // doc.rect(10, v4+20, 200, 55, 'S');
            // doc.text('0', 40, v4+20+25);


            var grn=$('#grn1234').text();
            var driverName=$('#driverName').val();
            doc.line(10, 700, 568, 700);
            doc.text('Sign of Transporter', 10, 700+20);
            doc.text('Name', 10, 700+40);
            doc.text('Driver Name '+driverName, 10, 700+60);
            doc.text('GRN NO. '+grn, 10, 700+80);
            doc.text('Sign of Dealer', 400, 700+20);
            doc.text('Stamp', 400, 700+40);
        try
        {
            var blob = new Blob([ab], {type: "application/pdf"});
            console.debug("case 1");
            return blob;
        }
        catch (e)
        {
            window.BlobBuilder = window.BlobBuilder || window.WebKitBlobBuilder || window.MozBlobBuilder || window.MSBlobBuilder;
            if (e.name == 'TypeError' && window.BlobBuilder)
            {
                var bb = new BlobBuilder();
                bb.append(ab);
                console.debug("case 2");
                return bb.getBlob("application/pdf");

            }
            else if (e.name == "InvalidStateError")
            {
                  // InvalidStateError (tested on FF13 WinXP)
                console.debug("case 3");
                return new Blob([ab], {type: "application/pdf"});

            }
            else
            {
                // We're screwed, blob constructor unsupported entirely
                console.debug("Error");
            }
        }
        //
        // var pdfOutput = doc.output();
        var pdfOutput = doc.output("blob");
        // var pdfOutput = doc.output(“blob”);
        // console.log( pdfOutput );
     
        //NEXT SAVE IT TO THE DEVICE'S LOCAL FILE SYSTEM
        console.log("file system...");
        window.requestFileSystem(LocalFileSystem.PERSISTENT, 0, function(fileSystem) {
     
        console.log(fileSystem.name);
        console.log(fileSystem.root.name);
        console.log(fileSystem.root.fullPath);
     
            fileSystem.root.getFile("test.pdf", {create: true}, function(entry) {
                // var fileDetail = entry;
                var fileDetail = JSON.stringify(entry);
                // alert(fileDetail);
                var location=JSON.parse(fileDetail).nativeURL;
                alert('The pdf file is stored in this location '+location);
                console.log('fileEntry',fileDetail);
                console.log('The pdf file is stored in this location',location);
                console.log('type',typeof(fileDetail));

                entry.createWriter(function(writer) {
                   writer.onwrite = function(evt) {
                    // alert('write success');
                   console.log("write success");
                };
         
                console.log("writing to file");
                   writer.write( pdfOutput );
                }, function(error) {
                 console.log(error);
              });
         
            }, function(error){
               console.log(error);
            });
        },

        function(event){
           console.log( evt.target.error.code );
        });
       //
    }
    
});
