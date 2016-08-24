	$(document).on('click', '.saveSync', function(){
		console.log('saveSync clicking');
		var chassisList=new Array();
	    var  serilizedChassisList = JSON.stringify(chassisList);
	    localStorage.setItem('chassisList', serilizedChassisList);

		var grnObject={};
		// var grnArray= new Array();
		var driverObj={};
		var driverImage = document.getElementById('driverImage');
		var imageData=driverImage.src;
		var driverName=$('#driverName').val();
		var driverSignature=$('#signatureofDriver').val();
		var managerSignature=$('#managerSignature').val();
		driverObj.driver_name=driverName;
		driverObj.driver_image=imageData;
		driverObj.driver_signature='';
		driverObj.pdiManager_signature='';
		console.log('driverObj',driverObj);
		DBHandler.saveDriverDetail('driver', driverObj, saveDriverDetailCallback);

		function saveDriverDetailCallback(result){
			console.log('saveDriverDetailCallback');
			DBHandler.getgrnDetail('grn_detail', grnDetailCallBack);
			
		}
		function grnDetailCallBack(result){
			console.log('grnDetailCallBack');
			var grnArr=new Array();
			var grnObj={};
			grnObj.grn_number=result.rows.item(0).grn_number;
			grnObj.reporting_date=result.rows.item(0).reporting_date;
			grnObj.reporting_time=result.rows.item(0).grn_number;
			grnObj.uploading_date=result.rows.item(0).uploading_date;
			grnObj.delay_type=result.rows.item(0).delay_type;
			grnObj.reason_of_delay=result.rows.item(0).reason_of_delay;
			grnObj.image=result.rows.item(0).image;
			grnArr.push(grnObj);
			grnObject.grn=grnArr;
			console.log('grnDetailCallBack grnObject',grnObject);
			var serilizedgrnObject = JSON.stringify(grnObject);
			console.log('serilizedgrnObject grnObject',serilizedgrnObject);
			DBHandler.getDriverDetail('driver', getDriverDetailCallback);
		}
		function getDriverDetailCallback(result){
			console.log('getDriverDetailCallback');
			console.log('length',result.rows.length);
			var driverArr=new Array();
			var driverObject={};
			console.log('result rows',result.rows.item(0));
			console.log('result rows1',result.rows.item(1));
			driverObject.driver_name=result.rows.item(0).driver_name;
            driverObject.driver_image=result.rows.item(0).driver_image;
            driverObject.driver_signature=result.rows.item(0).driver_signature;
            driverObject.pdiManager_signature=result.rows.item(0).pdiManager_signature;
            driverArr.push(driverObject);
            grnObject.driver=driverArr;
            console.log('getDriverDetailCallback grnObject',grnObject);
			DBHandler.getAllRecords('dealer', dealerDetailCallback);

		}

		function dealerDetailCallback(result){
			console.log('dealerDetailCallback');
			var dealerArr=new Array();
			var dealerObject={};
			dealerObject.dealer_name=result.rows.item(0).dealer_name;
			dealerObject.dealer_code=result.rows.item(0).dealer_code;
			dealerObject.pdi_manager=result.rows.item(0).pdi_manager;
			dealerArr.push(dealerObject);
			grnObject.dealer=dealerArr;
			console.log('dealerDetailCallback grnObject',grnObject);
			DBHandler.getAllRecordsofTrip('grn_trip', tripCallback);
		}

		function tripCallback(result){
			console.log('tripCallback');
			var tripArr=new Array();
			var tripObject={};
			var invoiceArr=new Array();
			var tripno=result.rows.item(0).tripno;
			tripObject.tripno=tripno;
			tripObject.created_date=result.rows.item(0).created_date;
			tripObject.tripno=result.rows.item(0).tripno;
			tripObject.modified_date=result.rows.item(0).modified_date;
			tripObject.truckno=result.rows.item(0).truckno;
			console.log('tripCallback grnObject',grnObject);

			DBHandler.getAllRecordsofInvoice('grn_invoice', tripno, invoiceCallback);
			function invoiceCallback(result){
				console.log('invoiceCallback');
				var len=result.rows.length;
				for(var i=0; i<len; i++){
					var invoiceObj={};
					invoiceObj.invoiceno=result.rows.item(i).invoiceno;
					invoiceObj.plant=result.rows.item(i).plant;
					invoiceObj.created_date=result.rows.item(i).created_date;
					invoiceObj.modified_date=result.rows.item(i).modified_date;
					invoiceObj.billqty=result.rows.item(i).billqty;
					invoiceObj.tripno=result.rows.item(i).tripno;
					invoiceArr.push(invoiceObj);
					
				}

				tripObject.invoice=invoiceArr;
				tripArr.push(tripObject);
				grnObject.trip=tripArr;
				var flag=localStorage.getItem('flag');
				console.log('invoiceCallback grnObject',grnObject);
				DBHandler.getAllRecordsofChassis('grn_chassisDetails', flag, chassisCallback); 
			}

		}

		function chassisCallback(result){


			console.log('chassisCallback');
			var chassisArr=new Array();
			
			var len=result.rows.length;
			for(var i=0; i<len; i++){
				var chassisList = localStorage.getItem('chassisList');
                var chassisListDetail = $.parseJSON(chassisList);
                console.log('chassisListDetailLength',chassisListDetail.length);
                var chassisno1=result.rows.item(i).chassisno;
                var chArr=new Array();
                chArr.push(chassisno1);
                var intersectionArr=_.intersection(chassisListDetail, chArr);
                chassisListDetail.push(chassisno1);
                var serilizedChassisListDetail = JSON.stringify(chassisListDetail);
			    localStorage.setItem('chassisList', serilizedChassisListDetail);
                if(intersectionArr.length==0){
                	var chassisObject={};
					var damageArr=new Array();
					var shortageArr=new Array();
					// var chassisno1=result.rows.item(i).chassisno;
					var type=result.rows.item(i).type;
					chassisObject.chassisno=chassisno1;
					if(type=="damage"){
					var damageObject={};
					damageObject.damage_location=result.rows.item(i).damage_location;
					damageObject.damage_detail=result.rows.item(i).damage_detail;
					damageObject.damage_type=result.rows.item(i).damage_type;
					damageObject.damage_cause=result.rows.item(i).damage_cause;
					damageObject.attachment=result.rows.item(i).attachment;
					damageArr.push(damageObject);

					}else if(type=="shortage"){
						var shortageObject={};
						shortageObject.shortage_brand_variant=result.rows.item(i).shortage_brand_variant;
						shortageObject.shortage_part=result.rows.item(i).shortage_part;
						shortageObject.quantity=result.rows.item(i).quantity;
						shortageObject.attachment=result.rows.item(i).attachment;
						shortageArr.push(shortageObject);
					}

					var chassisno2;
					for(var j=i+1; j<len; j++){
						chassisno2=result.rows.item(j).chassisno;
						if(chassisno1 == chassisno2){
							var type=result.rows.item(i).type;
							if(type=="damage"){
								var damageObject={};
								damageObject.damage_location=result.rows.item(i).damage_location;
								damageObject.damage_detail=result.rows.item(i).damage_detail;
								damageObject.damage_type=result.rows.item(i).damage_type;
								damageObject.damage_cause=result.rows.item(i).damage_cause;
								damageObject.attachment=result.rows.item(i).attachment;
								damageArr.push(damageObject);

							}else if(type=="shortage"){
								var shortageObject={};
								shortageObject.shortage_brand_variant=result.rows.item(i).shortage_brand_variant;
								shortageObject.shortage_part=result.rows.item(i).shortage_part;
								shortageObject.quantity=result.rows.item(i).quantity;
								shortageObject.attachment=result.rows.item(i).attachment;
								shortageArr.push(shortageObject);
							}

						}

					}
					chassisObject.damage=damageArr;
					chassisObject.shortage=shortageArr;
					chassisArr.push(chassisObject);

                }
				
			}
			grnObject.chassis=chassisArr;
			var serilizedgrnObject = JSON.stringify(grnObject);
			console.log('grnObject',grnObject);
			console.log('serilizedgrnObject',serilizedgrnObject);

			// setTimeout(function() {
			// 	DBHandler.getDriverDetail('driver', getDriverDetailCallback2);
			// }, 4000);
		}

		// function getDriverDetailCallback2(result){
		// 	var driverObject={};
		// 	console.log('result rows',result.rows.item(0));
		// 	console.log('result rows1',result.rows.item(1));
		// 	driverObject.driver_name=result.rows.item(0).driver_name;
  //           driverObject.driver_image=result.rows.item(0).driver_image;
  //           driverObject.driver_signature=result.rows.item(0).driver_signature;
  //           driverObject.pdiManager_signature=result.rows.item(0).pdiManager_signature;
  //           console.log('driverObject',driverObject);
  //           console.log('serilizeddriverObject',JSON.stringify(driverObject));
		// }

	});
	
