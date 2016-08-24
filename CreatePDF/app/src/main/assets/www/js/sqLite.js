/*
 * Wrapper(Middleware) for websql DB transactions.
 * Works well with Cordova sqlite plugin 'https://github.com/brodysoft/Cordova-SQLitePlugin'
 * ----------------------------------------------------------------------------------------
 */
var db;
var dbName = "bajaj_ticl.db";
// var lastIndentNumber = '';
// var indentValues = '';
var DBHandler = {

    /*
     * ------------------------ Initiate Database ------------------------
     */
    initDatabase: function() {
        //
        // if(window.sqlitePlugin){
        //console.log('Db creating');
        console.log('initDatabase function');
        db = window.sqlitePlugin.openDatabase({
            name: dbName,
            createFromLocation: 1
        }); //, successCb(callback), errorCb);
        //console.log('Db created');

        console.log('db');
        db.transaction(function(tx) {
            console.log('transaction');
            // db.transaction(function(tx) {
            //     tx.executeSql("DROP TABLE dealer", [], function(tx, result) {

            //     }, onError);
            // });
            // db.transaction(function(tx) {
            //     tx.executeSql("DROP TABLE trip", [], function(tx, result) {

            //     }, onError);
            // });

            // db.transaction(function(tx) {
            //     tx.executeSql("DROP TABLE invoice", [], function(tx, result) {

            //     }, onError);
            // });
            // db.transaction(function(tx) {
            //     tx.executeSql("DROP TABLE grn_trip", [], function(tx, result) {

            //     }, onError);
            // });

            // db.transaction(function(tx) {
            //     tx.executeSql("DROP TABLE grn_invoice", [], function(tx, result) {

            //     }, onError);
            // });
            // db.transaction(function(tx) {
            //     tx.executeSql("DROP TABLE grn_detail", [], function(tx, result) {

            //     }, onError);
            // });
            // db.transaction(function(tx) {
            //     tx.executeSql("DROP TABLE grn_chassis", [], function(tx, result) {

            //     }, onError);
            // });
            // db.transaction(function(tx) {
            //     tx.executeSql("DROP TABLE grn_chassisDetails", [], function(tx, result) {

            //     }, onError);
            // });
            // db.transaction(function(tx) {
            //     tx.executeSql("DROP TABLE driver", [], function(tx, result) {

            //     }, onError);
            // });
            tx.executeSql('CREATE TABLE IF NOT EXISTS dealer ("dealer_name" VARCHAR, "dealer_code" VARCHAR, "pdi_manager" VARCHAR)');    
            tx.executeSql('CREATE TABLE IF NOT EXISTS driver ("driver_name" VARCHAR, "driver_image" VARCHAR, "driver_signature" VARCHAR, "pdiManager_signature" VARCHAR)');
            console.log('dealer table');
            tx.executeSql('CREATE TABLE IF NOT EXISTS trip ("tripno" VARCHAR PRIMARY KEY, "created_date" DATETIME, "modified_date"  DATETIME, "total_distance" INTEGER, "truckno" VARCHAR)');
            tx.executeSql('CREATE TABLE IF NOT EXISTS invoice ("invoiceno" VARCHAR PRIMARY KEY, "plant" VARCHAR, "created_date" DATETIME, "modified_date"  DATETIME, "billqty" DOUBLE, "tripno" VARCHAR)');
            tx.executeSql('CREATE TABLE IF NOT EXISTS grn_trip ("tripno"  VARCHAR PRIMARY KEY, "created_date" DATETIME, "modified_date"  DATETIME, "truckno" VARCHAR)');
            tx.executeSql('CREATE TABLE IF NOT EXISTS grn_invoice ("invoiceno" VARCHAR PRIMARY KEY, "plant" VARCHAR, "created_date" DATETIME, "modified_date"  DATETIME, "billqty" DOUBLE, "tripno"  VARCHAR)');
            tx.executeSql('CREATE TABLE IF NOT EXISTS grn_detail ("grn_number" VARCHAR, "reporting_date" DATETIME, "reporting_time" DATETIME, "uploading_date" DATETIME, "uploading_time" DATETIME, "delay_type" VARCHAR, "reason_of_delay" VARCHAR, "flag"  INTEGER)');
            tx.executeSql('CREATE TABLE IF NOT EXISTS grn_chassis ("chassisno" VARCHAR , "damage_location" VARCHAR, "damage_detail" VARCHAR, "damage_type" VARCHAR, "damage_cause" VARCHAR, "attachment" VARCHAR, "shortage_brand_variant" VARCHAR, "shortage_part" VARCHAR, "shortage_city" VARCHAR, "invoiceno" VARCHAR)');    
            tx.executeSql('CREATE TABLE IF NOT EXISTS grn_chassisDetails ("chassisno" VARCHAR, "type" VARCHAR, "damage_location" VARCHAR, "damage_detail" VARCHAR, "damage_type" VARCHAR, "damage_cause" VARCHAR, "attachment" VARCHAR, "shortage_brand_variant" VARCHAR, "shortage_part" VARCHAR, "quantity" VARCHAR,"flag" INTEGER)');    
        });


    },

    clearData: function(){
        db.transaction(function(tx) {
             db.transaction(function(tx) {
                tx.executeSql("DROP TABLE trip", [], function(tx, result) {

                }, onError);
            });

            db.transaction(function(tx) {
                tx.executeSql("DROP TABLE invoice", [], function(tx, result) {

                }, onError);
            });
            db.transaction(function(tx) {
                tx.executeSql("DROP TABLE grn_trip", [], function(tx, result) {

                }, onError);
            });

            db.transaction(function(tx) {
                tx.executeSql("DROP TABLE grn_invoice", [], function(tx, result) {

                }, onError);
            });
            db.transaction(function(tx) {
                tx.executeSql("DROP TABLE grn_detail", [], function(tx, result) {

                }, onError);
            });
            db.transaction(function(tx) {
                tx.executeSql("DROP TABLE grn_chassis", [], function(tx, result) {

                }, onError);
            });
            db.transaction(function(tx) {
                tx.executeSql("DROP TABLE grn_chassisDetails", [], function(tx, result) {

                }, onError);
            });


        });
    },

    isExists: function(table, callback) {
        db.transaction(function(tx) {
            tx.executeSql(
                "SELECT count(*) as count FROM sqlite_master WHERE type='table' AND name= '" + table + "'", [],
                function(tx, result) {
                    callback(result);
                }, onError);
        });
    },


    // ---------------------------------------------------------------
    // Creates table with the name and columns passed
    // Params: @table => name of the table
    // @columns => colums to be created as query formatted string
    // eg., "id integer primary key, student_id integer, data text"
    // @callback => function to be called upon query execution
    // ----------------------------------------------------------------
    createTable: function(table, columns, callback) {
        db.transaction(function(tx) {
            tx.executeSql("CREATE TABLE " + table + " (" + columns + ")", [],
                function(tx, result) {
                    //  console.log("=====created table :" + table);
                    callback(result);
                }, onError);
        });
    },

    // ---------------------------------------------------------------
    // Add new record with passed values
    // Params: @table => name of the table
    // @columns => Array of colums to be inserted as query formatted string
    // eg., ["student_id", "sem_id"];
    // @values => Array of values to be inserted as query formatted string
    // eg., ["1", "2"];
    // @callback => function to be called upon query execution
    // ----------------------------------------------------------------
    newRecord: function(table, columns, values, callback) {
        //console.log(columns + ":" + values)
        var _columns = columns.join(",");
        db.transaction(function(tx) {
            tx.executeSql("INSERT INTO " + table + " (" + _columns + ") VALUES (" + getValuePlaceHolder(_columns) + ")",
                values,
                function(tx, result) {
                    // console.log("insertID", result.insertId,
                    //      "rows affected", result.rowsAffected);
                    callback(result);
                }, onError);
        });
    },

    // ---------------------------------------------------------------
    // Selects records in the table based on condition and columns passed
    // and returns via callback
    // Params: @table => name of the table
    // @columns => colums to be fetched as query formatted string
    // eg., "id, student_id, data" or "*"
    // @condition => condition to be checked as query formatted string
    // eg., "student_id = 2 AND id = 1"
    // @callback => function to be called upon query execution
    // ----------------------------------------------------------------
    getRecord: function(table, columns, condition, callback) {
        // console.log("SELECT " + columns + " FROM " + table + " WHERE "
        //      + condition);
        db.transaction(function(tx) {
            tx.executeSql("SELECT " + columns + " FROM " + table + " WHERE " + condition, [], function(tx, result) {
                callback(result);
            }, onError);
        });
    },

    // ---------------------------------------------------------------
    // Selects all records in the table and returns via callback
    // Params: @table => name of the table
    // @callback => function to be called upon query execution
    // ----------------------------------------------------------------
    getAllRecordsofTrip: function(table, callback){
        db.transaction(function(tx) {
            tx.executeSql("SELECT * FROM " + table + " ORDER BY modified_date DESC LIMIT 1", [], function(tx, result) {
                console.log('getAllRecords result', result);
                callback(result);
            });
        });
    },

    getAllRecordsofInvoice: function(table, tripno, callback){
        console.log('getAllRecordsofInvoice tripno',tripno);
        db.transaction(function(tx) {
            tx.executeSql("SELECT * FROM " + table + " WHERE tripno='"+ tripno + "'", [], function(tx, result) {
                console.log('getAllRecords result', result);
                callback(result);
            });
        });
    },
    
    getAllRecords: function(table, callback) {
        db.transaction(function(tx) {
            tx.executeSql("SELECT * FROM " + table, [], function(tx, result) {
                console.log('getAllRecords result', result);
                callback(result);
            });
        });
    },

    searchRecordsOfTrip: function(table, condition, callback){
        db.transaction(function(tx) {
            tx.executeSql("SELECT * FROM " + table + condition, [], function(tx, result) {
                console.log('getAllRecords result', result);
                callback(result);
            });
        });
    },

    getAllRecords2:function(table, callback) {
        db.transaction(function(tx) {
            tx.executeSql("SELECT * FROM " + table, [], function(tx, result2) {
                console.log('getAllRecords2 result', result2);
                callback(result2);
            });
        });
    },
    
    getgrnDetail: function(table, callback){
        db.transaction(function(tx) {
            tx.executeSql("SELECT * FROM " + table + " ORDER BY flag DESC LIMIT 1", [], function(tx, result) {
                callback(result);
            });
        });

    },

    getAllRecordsofChassis: function(table, flag, callback){

        db.transaction(function(tx) {
            tx.executeSql("SELECT * FROM " + table + " WHERE flag='"+ flag + "'", [], function(tx, result) {
                // console.log('getAllRecords result', result);
                callback(result);
            });
        });
    },

    getChassisRecords: function(table, condition, callback){
        
        db.transaction(function(tx) {
            tx.executeSql("SELECT DISTINCT chassisno FROM " + table + " WHERE " + condition, [], function(tx, result) {
                // tx.executeSql("SELECT * FROM " + table, [], function(tx, result) {
                console.log('getChassis Records result', result);
                callback(result);
            });
        });
    },

    getDriverDetail: function(table, callback){
        // db.transaction(function(tx) {
            db.transaction(function(tx) {
                tx.executeSql("SELECT * FROM " + table, [], function(tx, result) {
                    callback(result);
                });
            });
        // });

    },

    chassisDetails: function(table, condition, callback){
        
        db.transaction(function(tx) {
            tx.executeSql("SELECT DISTINCT chassisno FROM " + table + " WHERE " + condition, [], function(tx, result) {
                // tx.executeSql("SELECT * FROM " + table, [], function(tx, result) {
                // console.log('getChassis Records result', result);
                callback(result);
            });
        });
    },
    
    getAllRecordsofDriver: function(table, callback){
        db.transaction(function(tx) {

            db.transaction(function(tx) {
                tx.executeSql("SELECT * FROM " + table + " ORDER BY flag DESC LIMIT 1", [], function(tx, result) {
                    console.log('getAllRecords result', result);
                    callback(result);
                });
            });
        });
    },
    saveDriverDetail: function(table, values, callback){
        var obj={
            driver_name: values.driver_name,
            driver_image: values.driver_image,
            driver_signature: values.driver_signature,
            pdiManager_signature: values.pdiManager_signature
        };
        console.log('obj',obj);
        var objValues = getDriverValues(obj);
        var driverColumns = "('driver_name', 'driver_image', 'driver_signature', 'pdiManager_signature')";
        
        db.transaction(function(tx) {
            tx.executeSql("INSERT OR REPLACE INTO driver "+ driverColumns + " VALUES " + objValues,
                objValues,
                function(tx, result) {
                    console.log("insertID", result.insertId,"rows affected", result.rowsAffected);
                    callback(result);
                }, onError);
        });
    },


    saveRecordofDealer: function(values, callback){
         console.log("values", values);
        //Date time fields in db
        var objValues = "";

        for (var i = 0; i < values.length; i++) {
            var obj = values[i];
            //Date time fields

            var cRow = getDealerValues(obj);
            if (i != values.length - 1)
                objValues = objValues + cRow + ", ";
            else
                objValues = objValues + cRow;
                console.log('objValues',objValues);

        }

        var columns = "('dealer_name', 'dealer_code', 'pdi_manager')";
       
        db.transaction(function(tx) {
            tx.executeSql("INSERT OR REPLACE INTO dealer " + columns + " VALUES " + objValues, objValues,
                function(tx, result) {
                    console.log("result",result);
                    console.log("insertID", result.insertId, "rows affected", result.rowsAffected);
                    if (callback != null)
                        callback(result);
                }, onError);
        });

    },

    // saveRecordsofGrn_trip_invoice: function(values, callback){
    //     console.log("values", values);
    //     console.log("tripno", values[0]);
    //     console.log("created_date", values[1]);
    //     var obj1={
    //         tripno: values[0],
    //         created_date: values[1],
    //         modified_date: null,
    //         truckno: values[2]
    //     };
    //     var obj2={
    //         invoiceno: values[3],
    //         plant: values[4],
    //         created_date: null,
    //         modified_date: null,
    //         billqty: values[5],
    //         tripno: values[0]
    //     };
    //     var objValues1 = getTripValues(obj1);
    //     var objValues2 = getInvoiceValues(obj2);
    //     var tripColumns = "('tripno', 'created_date', 'modified_date', 'truckno')";
    //     var invoiceColumns = "('invoiceno', 'plant', 'created_date', 'modified_date', 'billqty', 'tripno')";
    //     db.transaction(function(tx) {
    //         tx.executeSql("INSERT OR REPLACE INTO grn_trip " + tripColumns + " VALUES " + objValues1,
    //             objValues1,
    //             function(tx, result) {
    //                 console.log("insertID", result.insertId,
    //                     "rows affected", result.rowsAffected);
    //             }, onError);
    //     });

    //     db.transaction(function(tx) {
    //         tx.executeSql("INSERT OR REPLACE INTO grn_invoice " + invoiceColumns + " VALUES " + objValues2,
    //             objValues2,
    //             function(tx, result) {
    //                 console.log("insertID", result.insertId,
    //                     "rows affected", result.rowsAffected);
    //                 if (callback != null)
    //                     callback(result);
    //             }, onError);
    //     });

    // },

     saveRecordsofGrn_trip_invoice: function(values, callback){
        var valuesLen=values.length;
        var invoiceLen=valuesLen-3;
        var invoiceArray=[];
        console.log("values", values);
        console.log("tripno", values[0]);
        console.log("created_date", values[1]);
        var l=0;
        var obj1={};

        obj1.tripno = '0000'+values[l];
        l=l+1;
        obj1.created_date = values[l];

        l=l+1;
        obj1.truckno = values[l];


        l=l+1;
        obj1.modified_date = values[l];
        console.log('tripValues',obj1);

        for(var i=0; i< (invoiceLen/3)-1; i++){
            //var invoiceObj={};
            var obj={};
            l=l+1;
            obj.invoiceno = values[l];
            l=l+1;
            obj.plant = values[l];
            obj.created_date = null;
            obj.modified_date = null;
            l=l+1;
            obj.billqty = values[l];
            obj.tripno = '0000'+values[0];
            console.log('l',l);
            invoiceArray.push(obj);
        }

        console.log('invoiceArray',invoiceArray);

        var invoiceList = _.uniq(invoiceArray, function(item, key, invoiceno) { 
            return item.invoiceno;
        });
        console.log('invoiceList',invoiceList);
        var len=invoiceList.length;
        var arr=[];
        for(var i=0; i<len; i++){
            var invoice=invoiceList[i].invoiceno;
            localStorage.setItem('invoice', invoice);
            arr.push(invoice);
            if(i===len-1){
                 localStorage.setItem('invoiceArr', arr);
            }
        }
        // var obj2={
        //     invoiceno: values[3],
        //     plant: values[4],
        //     created_date: null,
        //     modified_date: null,
        //     billqty: values[5],
        //     tripno: values[0]
        // };
        var objValues1 = getTripValues(obj1);

         var objValues = "";
        for (var j = 0; j < invoiceList.length; j++) {
            var obj = invoiceList[j];
            //Date time fields

            var cRow = getInvoiceValues(obj);
            if (j != invoiceList.length - 1)
                objValues = objValues + cRow + ", ";
            else
                objValues = objValues + cRow;
                console.log('objValues',objValues);

        }
        var tripColumns = "('tripno', 'created_date', 'modified_date', 'truckno')";
        var invoiceColumns = "('invoiceno', 'plant', 'created_date', 'modified_date', 'billqty', 'tripno')";
        db.transaction(function(tx) {
            tx.executeSql("INSERT OR REPLACE INTO grn_trip " + tripColumns + " VALUES " + objValues1,
                objValues1,
                function(tx, result) {
                    console.log("insertID", result.insertId,
                        "rows affected", result.rowsAffected);
                }, onError);
        });

        db.transaction(function(tx) {
            tx.executeSql("INSERT OR REPLACE INTO grn_invoice " + invoiceColumns + " VALUES " + objValues,
                objValues,
                function(tx, result) {
                    console.log("insertID", result.insertId,
                        "rows affected", result.rowsAffected);
                    if (callback != null)
                        callback(result);
                }, onError);
        });

    },

    deleteGrn_trip_invoice: function(trip_number, callback){

        db.transaction(function(tx) {
            tx.executeSql("DELETE FROM grn_trip WHERE tripno="+"'"+trip_number+"'", [], 
                function(tx, result) {
                    console.log('deleting grn_trip data');
                   // callback(result); 
                }, onError);
        });

        db.transaction(function(tx) {
            tx.executeSql("DELETE FROM grn_invoice WHERE tripno="+"'"+trip_number+"'", [],
                function(tx, result) {
                    console.log('deleting grn_invoice data');
                    callback(result);
                }, onError);
        });

    },

    saveRecordsofGrn_trip: function(values, callback){
        
        var obj={
            tripno: values[0],
            created_date: values[1],
            modified_date: null,
            truckno: values[2]
        };
       
        var objValues = getTripValues(obj);
        var tripColumns = "('tripno', 'created_date', 'modified_date', 'truckno')";
        
        db.transaction(function(tx) {
            tx.executeSql("INSERT OR REPLACE INTO grn_trip " + tripColumns + " VALUES " + objValues,
                objValues,
                function(tx, result) {
                    console.log("insertID", result.insertId,
                        "rows affected", result.rowsAffected);
                }, onError);
        });
    },

    saveAllRecords: function(values, callback) {
        console.log("values", values);
        //Date time fields in db
        var objValues = "";


        for (var i = 0; i < values.length; i++) {
            var obj = values[i];
            //Date time fields

            var cRow = getTripValues1(obj);
            if (i != values.length - 1)
                objValues = objValues + cRow + ", ";
            else
                objValues = objValues + cRow;
                console.log('objValues',objValues);

        }

        var columns = "('tripno', 'created_date', 'modified_date', 'total_distance', 'truckno')";
       
        db.transaction(function(tx) {
            tx.executeSql("INSERT OR REPLACE INTO trip " + columns + " VALUES " + objValues, objValues,
                function(tx, result) {
                    console.log("result",result);
                    console.log("insertID", result.insertId, "rows affected", result.rowsAffected);
                    if (callback != null)
                        callback(result);
                }, onError);
        });

    },

    saveAllRecordsofInvoice:function(values, callback) {

        console.log("values", values);
        //Date time fields in db
        var objValues = "";
        for (var i = 0; i < values.length; i++) {
            var obj = values[i];
            //Date time fields

            var cRow = getInvoiceValues(obj);
            if (i != values.length - 1)
                objValues = objValues + cRow + ", ";
            else
                objValues = objValues + cRow;
                console.log('objValues',objValues);

        }

        var columns = "('invoiceno', 'plant', 'created_date', 'modified_date', 'billqty', 'tripno')";
       
        db.transaction(function(tx) {
            tx.executeSql("INSERT OR REPLACE INTO invoice " + columns + " VALUES " + objValues, objValues,
                function(tx, result) {
                    console.log("result",result);
                    console.log("insertID", result.insertId, "rows affected", result.rowsAffected);
                    if (callback != null)
                        callback(result);
                }, onError);
        });

    },

    saveRecordsofChassis: function(values, callback){
        console.log("values", values);
        console.log("values", values);
        //Date time fields in db
        var objValues = "";


        for (var i = 0; i < values.length; i++) {
            var obj = values[i];
            //Date time fields

            var cRow = getChassisValues(obj);
            if (i != values.length - 1)
                objValues = objValues + cRow + ", ";
            else
                objValues = objValues + cRow;
                console.log('objValues',objValues);

        }

        var columns = "('chassisno', 'damage_location', 'damage_detail', 'damage_type', 'damage_cause', 'attachment', 'shortage_brand_variant', 'shortage_part','shortage_city', 'invoiceno')";
       
        db.transaction(function(tx) {
            tx.executeSql("INSERT OR REPLACE INTO grn_chassis " + columns + " VALUES " + objValues, objValues,
                function(tx, result) {
                    console.log("result",result);
                    console.log("insertID", result.insertId, "rows affected", result.rowsAffected);
                    if (callback != null)
                        callback(result);
                }, onError);
        });
    },

    saveRecordsofgrn_chassisDetails: function(values, callback){
        console.log("values of saveRecordsofgrn_chassisDetails", values);
        var objValues = "";


        for (var i = 0; i < values.length; i++) {
            var obj = values[i];
            //Date time fields

            var cRow = getGrn_ChassisValues(obj);
            if (i != values.length - 1)
                objValues = objValues + cRow + ", ";
            else
                objValues = objValues + cRow;
                console.log('objValues',objValues);

        }

        var columns = "('chassisno', 'type', 'damage_location', 'damage_detail', 'damage_type', 'damage_cause', 'attachment', 'shortage_brand_variant', 'shortage_part', 'quantity', 'flag')";
       
        db.transaction(function(tx) {
            tx.executeSql("INSERT OR REPLACE INTO grn_chassisDetails " + columns + " VALUES " + objValues, objValues,
                function(tx, result) {
                    console.log("result",result);
                    console.log("insertID", result.insertId, "rows affected", result.rowsAffected);
                    if (callback != null)
                        callback(result);
                }, onError);
        });

    },

    saveGrn_detail:function(values, callback) {
        console.log("values of saveGrn_detail", values);
        var objValues = "";

        var objValues = getGrnValues(values);
    
        var columns = "('grn_number', 'reporting_date', 'reporting_time', 'uploading_date', 'uploading_time', 'delay_type', 'reason_of_delay', 'flag')";
       
        db.transaction(function(tx) {
            tx.executeSql("INSERT OR REPLACE INTO grn_detail " + columns + " VALUES " + objValues, objValues,
                function(tx, result) {
                    console.log("result",result);
                    console.log("insertID", result.insertId, "rows affected", result.rowsAffected);
                    if (callback != null)
                        callback(result);
                }, onError);
        });

    },

    // gets number of rows in particular table
    getTableCount: function(table, callback) {
        var isCallback = false;
        var count = new Array();

        for (var counter = 0; counter < table.length; counter++) {
            function execute(value) {
                //console.log('counter' + counter+': '+table[counter]);

                db.transaction(function(tx) {
                    tx.executeSql(
                        "SELECT COUNT(*) AS CNT FROM " + table[value] + " WHERE active=1", [],
                        function(tx, result) {
                            // console.log(result.rows.item(0).CNT);
                            count.push(result.rows.item(0).CNT);
                            if (count.length == table.length && isCallback == false) {
                                isCallback = true;
                                // console.log('total count:'+count);
                                callback(count);
                            }
                        });
                });

            }
            execute(counter);

        }
        // }, 5000);
    },

    searchInvoice: function(values, callback){
        var invoiceno = values.invoiceno;
        var fromDate = values.fromDate;
        var toDate = values.toDate;
        console.log('values',values);
        db.transaction(function(tx) {
            tx.executeSql("SELECT invoiceno FROM invoice WHERE invoiceno LIKE "+"'"+invoiceno+"%"+"'" +"AND created_date >= "+"'"+fromDate+"'"+"AND created_date <= "+"'"+toDate+"'",
                values,
                function(tx, result) {
                    console.log('result',result);
                    callback(result);
                }, onError);
        });

    },
    // ---------------------------------------------------------------
    // Update record with passed condition and values
    // Params: @table => name of the table
    // @condition => condition to be checked as query formatted string
    // eg., "student_id = 2 AND id = 1"
    // @columns => Array of colums to be updated as query formatted string
    // eg., ["student_id", "sem_id"];
    // @values => Array of values to be updated as query formatted string
    // eg., ["1", "2"];
    // @callback => function to be called upon query execution
    // ----------------------------------------------------------------
    updateRecord: function(table, condition, columns, values, callback) {
        // console.log(condition);
        // console.log(values);

        db.transaction(function(tx) {
            tx.executeSql("UPDATE " + table + " SET " + getUpdateColumns(columns) + " WHERE " + condition,
                values,
                function(tx, result) {
                    callback(result);
                }, onError);
        });

    },

    // ---------------------------------------------------------------
    // Delete record with passed condition
    // Params: @table => name of the table
    // @condition => condition to be checked as query formatted string
    // eg., "student_id = 2 AND id = 1"
    // @callback => function to be called upon query execution
    // ----------------------------------------------------------------
    deleteRecord: function(table, condition, callback) {
        db.transaction(function(tx) {
            tx.executeSql("DELETE FROM " + table + " WHERE " + condition, [],
                function(tx, result) {
                    callback(result)
                }, onError);
        });
    },

    excuteSQL: function(sql) {
        db.transaction(function(tx) {
            tx.executeSql(sql, [], function(tx, result) {
                callback(result)
            }, onError);
        });
    },
    
    getRowsToUpdate: function(table, callback) {
        db.transaction(function(tx) {
            tx.executeSql("SELECT * FROM " + table + " where isRowUpdated=1", [], function(tx, result) {
                callback(result)
            }, onError);
        });

    },

    setUpdateRowsToDefault: function(table, whereClause) {
        db.transaction(function(tx) {
            tx.executeSql("UPDATE " + table + " SET isRowUpdated=0 WHERE " + whereClause, [], function(tx, result) {
                callback(result)
            }, onError);
        });
    },
     resetTables : function() {
        db.transaction(function(tx) {
            tx.executeSql("DROP TABLE trip", [], function(tx, result) {

            }, onError);
        });

        db.transaction(function(tx) {
            tx.executeSql("DROP TABLE invoice", [], function(tx, result) {

            }, onError);
        });
    }
    
};


// ========================
// Helper functions
// ========================

// ---------------------------------------------------------------
// Get place holder as query formatted string for the columns passed
// Params: @columns => Array of colums to be updated as query formatted string
// eg., ["student_id", "sem_id"];
// ----------------------------------------------------------------

function getValuePlaceHolder(columns) {
    var valuePlaceHolder = [];
    columns.split(",").forEach(function() {
        valuePlaceHolder.push("?");
    });
    return (valuePlaceHolder.length > 1) ? valuePlaceHolder.join(",") : valuePlaceHolder[0];
}
 
 function getTripValues1(obj) {
    var tripno, created_date, modified_date, total_distance, truckno;
    //checks for integer
    tripno = obj.tripno;
    console.log('tripno',tripno);
    //checks for datetime
    created_date = obj.created_date;
    modified_date = obj.modified_date;
    total_distance = obj.total_distance;
    //checks for varchar
    truckno = obj.truckno;


    if (tripno == null || tripno == 'null' || tripno == '')
        tripno = null;
    else
        tripno = "'" + tripno + "'";
    
    if (created_date == null || created_date == 'null' || created_date == '')
        created_date = null;
    else
        created_date = "'" + created_date + "'";

    if (modified_date == null || modified_date == 'null' || modified_date == '')
        modified_date = null;
    else
        modified_date = "'" + modified_date + "'";

    if (total_distance == null || total_distance == 'null' || total_distance == '')
        truckno = null;
    
    if (truckno == null || truckno == 'null' || truckno == '')
        truckno = null;
    else
        truckno = "'" + truckno + "'";

    var currentRow = "(" + tripno + ", " + created_date + ", " + modified_date + ", "+ total_distance + ", " + truckno + ")";
    console.log(currentRow);

    return currentRow;
}

function getDriverValues(obj){
    var driver_name, driver_image, driver_signature, pdiManager_signature;
    driver_name=obj.driver_name;
    driver_image=obj.driver_image;
    driver_signature=obj.driver_signature;
    pdiManager_signature=obj.pdiManager_signature;
    if (driver_name == null || driver_name == 'null' || driver_name == '')
        driver_name = null;
    else
        driver_name = "'" + driver_name + "'";

    if (driver_image == null || driver_image == 'null' || driver_image == '')
        driver_image = null;
    else
        driver_image = "'" + driver_image + "'";

    if (driver_signature == null || driver_signature == 'null' || driver_signature == '')
        driver_signature = null;
    else
        driver_signature = "'" + driver_signature + "'";

    if (pdiManager_signature == null || pdiManager_signature == 'null' || pdiManager_signature == '')
        pdiManager_signature = null;
    else
        pdiManager_signature = "'" + pdiManager_signature + "'";

    var currentRow = "(" + driver_name + ", " + driver_image + ", " + driver_signature + ", " + pdiManager_signature + ")";
    console.log(currentRow);

    return currentRow;
}

function getTripValues(obj) {
    var tripno, created_date, modified_date, truckno;
    //checks for integer
    tripno = obj.tripno;
    console.log('tripno',tripno);
    //checks for datetime
    created_date = obj.created_date;
    modified_date = obj.modified_date;
    //checks for varchar
    truckno = obj.truckno;
  
    

    if (tripno == null || tripno == 'null' || tripno == '')
        tripno = null;
    else
        tripno = "'" + tripno + "'";
    
    if (created_date == null || created_date == 'null' || created_date == '')
        created_date = null;
    else
        created_date = "'" + created_date + "'";

    if (modified_date == null || modified_date == 'null' || modified_date == '')
        modified_date = null;
    else
        modified_date = "'" + modified_date + "'";

    
    if (truckno == null || truckno == 'null' || truckno == '')
        truckno = null;
    else
        truckno = "'" + truckno + "'";

    var currentRow = "(" + tripno + ", " + created_date + ", " + modified_date + ", " + truckno + ")";
    console.log(currentRow);

    return currentRow;
}

function getInvoiceValues(obj){
     var invoiceno, plant, created_date, modified_date, billqty, tripno;
        console.log('getInvoiceValues',obj);
        invoiceno = obj.invoiceno;
        plant = obj.plant;
        created_date = obj.created_date;
        modified_date = obj.modified_date;
        billqty = obj.billqty;
        tripno = obj.tripno;
        if (invoiceno == null || invoiceno == 'null' || invoiceno == '')
            invoiceno = null;
        else
            invoiceno = "'" + invoiceno + "'";

        if (plant == null || plant == 'null' || plant == '')
            plant = null;
        else
            plant = "'" + plant + "'";

        if (created_date == null || created_date == 'null' || created_date == '')
            created_date = null;
        else
            created_date = "'" + created_date + "'";

        if (modified_date == null || modified_date == 'null' || modified_date == '')
            modified_date = null;
        else
            modified_date = "'" + modified_date + "'";

        
        if (billqty == null || billqty == 'null' || billqty == '')
            billqty = null;

        if (tripno == null || tripno == 'null' || tripno == '')
            tripno = null;
        else
            tripno = "'" + tripno + "'";
        var currentRow = "(" + invoiceno + ", " + plant + ", " + created_date + ", " + modified_date + ", " + billqty + ", " + tripno +")";
        console.log(currentRow);

        return currentRow;
}

function getGrnValues(obj){
       var grn_number, reporting_date, reporting_time, uploading_date, uploading_time, delay_type, reason_of_delay, flag;
        grn_number = obj.grn_number;
        reporting_date = obj.reporting_date;
        reporting_time = obj.reporting_time;
        uploading_date = obj.uploading_date;
        uploading_time = obj.uploading_time;
        delay_type = obj.delay_type;
        reason_of_delay = obj.reason_of_delay;
        flag = obj.flag;
        if (grn_number == null || grn_number == 'null' || grn_number == '')
            grn_number = null;
        else
            grn_number = "'" + grn_number + "'";

        if (reporting_date == null || reporting_date == 'null' || reporting_date == '')
            reporting_date = null;
        else
            reporting_date = "'" + reporting_date + "'";

        if (reporting_time == null || reporting_time == 'null' || reporting_time == '')
            reporting_time = null;
        else
            reporting_time = "'" + reporting_time + "'";

        if (uploading_date == null || uploading_date == 'null' || uploading_date == '')
            uploading_date = null;
        else
            uploading_date = "'" + uploading_date + "'";

        if (uploading_time == null || uploading_time == 'null' || uploading_time == '')
            uploading_time = null;
        else
            uploading_time = "'" + uploading_time + "'";
        
        if (delay_type == null || delay_type == 'null' || delay_type == '')
            delay_type = null;
        else
            delay_type = "'" + delay_type + "'";

        if (reason_of_delay == null || reason_of_delay == 'null' || reason_of_delay == '')
            reason_of_delay = null;
        else
            reason_of_delay = "'" + reason_of_delay + "'";

        if (flag == null || flag == 'null' || flag == '')
            flag = null;
        var currentRow = "(" + grn_number +", "+ reporting_date + ", " + reporting_time + ", " + uploading_date + ", " + uploading_time + ", " + delay_type + ", " + reason_of_delay + ", " + flag +")";
        console.log('currentRow',currentRow);

        return currentRow;
}

function getDealerValues(obj){
    var dealer_name, dealer_code, pdi_manager;
    dealer_name = obj.dealer_name;
    dealer_code = obj.dealer_code;
    pdi_manager = obj.pdi_manager;
    
    if (dealer_name == null || dealer_name == 'null' || dealer_name == '')
        dealer_name = null;
    else
        dealer_name = "'" + dealer_name + "'";

    if (dealer_code == null || dealer_code == 'null' || dealer_code == '')
        dealer_code = null;
    else
        dealer_code = "'" + dealer_code + "'";

    if (pdi_manager == null || pdi_manager == 'null' || pdi_manager == '')
        pdi_manager = null;
    else
        pdi_manager = "'" + pdi_manager + "'";

    var currentRow = "(" + dealer_name +", "+ dealer_code + ", " + pdi_manager + ")";
    console.log('currentRow',currentRow);

    return currentRow;

}

function getChassisValues(obj){
    var chassisno, damage_location, damage_detail, damage_type, damage_cause, attachment;
    var shortage_brand_variant, shortage_part, shortage_city, invoiceno;
    chassisno = obj.chassisno;
    damage_location = obj.damage_location;
    damage_detail = obj.damage_detail;
    damage_type = obj.damage_type;
    damage_cause = obj.damage_cause;
    attachment = obj.attachment;
    shortage_brand_variant = obj.shortage_brand_variant;
    shortage_part = obj.shortage_part;
    shortage_city = obj.shortage_city;
    invoiceno = obj.invoiceno;
    
    if (chassisno == null || chassisno == 'null' || chassisno == '')
        chassisno = null;
    else
        chassisno = "'" + chassisno + "'";

    if (damage_location == null || damage_location == 'null' || damage_location == '')
        damage_location = null;
    else
        damage_location = "'" + damage_location + "'";

    if (damage_detail == null || damage_detail == 'null' || damage_detail == '')
        damage_detail = null;
    else
        damage_detail = "'" + damage_detail + "'";

    if (damage_type == null || damage_type == 'null' || damage_type == '')
        damage_type = null;
    else
        damage_type = "'" + damage_type + "'";

    if (damage_cause == null || damage_cause == 'null' || damage_cause == '')
        damage_cause = null;
    else
        damage_cause = "'" + damage_cause + "'";

    if (attachment == null || attachment == 'null' || attachment == '')
        attachment = null;
    else
        attachment = "'" + attachment + "'";
    if (shortage_brand_variant == null || shortage_brand_variant == 'null' || shortage_brand_variant == '')
        shortage_brand_variant = null;
    else
        shortage_brand_variant = "'" + shortage_brand_variant + "'";

    if (shortage_part == null || shortage_part == 'null' || shortage_part == '')
        shortage_part = null;
    else
        shortage_part = "'" + shortage_part + "'";

    if (shortage_city == null || shortage_city == 'null' || shortage_city == '')
        shortage_city = null;
    else
        shortage_city = "'" + shortage_city + "'";

   if (invoiceno == null || invoiceno == 'null' || invoiceno == '')
        invoiceno = null;
   else
        invoiceno = "'" + invoiceno + "'";
    
    var currentRow = "(" + chassisno +", "+ damage_location + ", " + damage_detail + ", "+ damage_type + ", " + damage_cause + ", "+ attachment + ", " + shortage_brand_variant +", " + shortage_part + ", "+ shortage_city + ", " + invoiceno +")";
    console.log('currentRow',currentRow);

    return currentRow;
}

function getGrn_ChassisValues(obj){
    var chassisno, type, damage_location, damage_detail, damage_type, damage_cause, attachment, flag;
    var shortage_brand_variant, shortage_part, quantity;
    chassisno = obj.chassisno;
    type=obj.type;
    damage_location = obj.damage_location;
    damage_detail = obj.damage_detail;
    damage_type = obj.damage_type;
    damage_cause = obj.damage_cause;
    attachment = obj.attachment;
    shortage_brand_variant = obj.shortage_brand_variant;
    shortage_part = obj.shortage_part;
    quantity = obj.quantity;
    flag = obj.flag;
    
    if (chassisno == null || chassisno == 'null' || chassisno == '')
        chassisno = null;
    else
        chassisno = "'" + chassisno + "'";

    if (type == null || type == 'null' || type == '')
        type = null;
    else
        type = "'" + type + "'";

    if (damage_location == null || damage_location == 'null' || damage_location == '')
        damage_location = null;
    else
        damage_location = "'" + damage_location + "'";

    if (damage_detail == null || damage_detail == 'null' || damage_detail == '')
        damage_detail = null;
    else
        damage_detail = "'" + damage_detail + "'";

    if (damage_type == null || damage_type == 'null' || damage_type == '')
        damage_type = null;
    else
        damage_type = "'" + damage_type + "'";

    if (damage_cause == null || damage_cause == 'null' || damage_cause == '')
        damage_cause = null;
    else
        damage_cause = "'" + damage_cause + "'";

    if (attachment == null || attachment == 'null' || attachment == '')
        attachment = null;
    else
        attachment = "'" + attachment + "'";
    if (shortage_brand_variant == null || shortage_brand_variant == 'null' || shortage_brand_variant == '')
        shortage_brand_variant = null;
    else
        shortage_brand_variant = "'" + shortage_brand_variant + "'";

    if (shortage_part == null || shortage_part == 'null' || shortage_part == '')
        shortage_part = null;
    else
        shortage_part = "'" + shortage_part + "'";

    if (quantity == null || quantity == 'null' || quantity == '')
        quantity = null;
    else
        quantity = "'" + quantity + "'";

    if (flag == null || flag == 'null' || flag == '')
        flag = null;

    
    var currentRow = "(" + chassisno + ", " + type + ", " + damage_location + ", " + damage_detail + ", "+ damage_type + ", " + damage_cause + ", "+ attachment + ", " + shortage_brand_variant +", " + shortage_part + ", "+ quantity + ", "+ flag + ")";
    console.log('currentRow',currentRow);

    return currentRow;
}


function onError(tx, error) {
    console.log(error.message);
}

function onError1(tx, error) {
    console.log(error.message);
}

function successCb(callback) {
    //console.log('Success::' + db);
    if (callback != null)
        callback();
}

function errorCb() {
    console.log('Error');

}


// ---------------------------------------------------------------
// Get columns to be updated as query formatted string from the columns passed
// Params: @columns => Array of colums to be updated as query formatted string
// eg., ["student_id", "sem_id"];
// ----------------------------------------------------------------
function getUpdateColumns(columns) {

    var result = [];
    columns.forEach(function(value) {
        result.push(value + " = ? ");
    });

    return result.join(",");
}