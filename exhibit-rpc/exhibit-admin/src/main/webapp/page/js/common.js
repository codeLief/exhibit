String.prototype.format = function(args) {
    var result = this;
    if (arguments.length > 0) {
    	
    	//我是{name}" {"name":"111"}"
        if (arguments.length == 1 && typeof (args) == "object") {
        	
        	var filtReg = "[ \| [A-Za-z_$]+[A-Za-z_$\\d]+]?";
        	
            for (var key in args) {
            	
            	var value = args[key] == undefined? "" : args[key];
            	
            	var reg = new RegExp("({" + key + filtReg + "})", "g");
            	
            	try {
					//包含filter
					if (reg.test(result)) {
						var filtF = result.split(reg)[1].replace(/\s+/g, "")
								.split("{" + key + "|")[1].split("}")[0];
						value = window[filtF](value);
					} else {
						reg = new RegExp("({" + key + "})", "g");
						if (!reg.test(result)) {
							reg = new RegExp("(" + key + ")", "g");
							if (reg.test(result)) {
								throw "syntax error";
							}
						}
					}
				} catch (e) {
					throw key + " syntax error";
				}
				result = result.replace(reg, value);
            }
        }//"我是{0},性别{1}" "ankang, nan"
        else {
            for (var i = 0; i < arguments.length; i++) {
            	
            	var value = arguments[i] == undefined? "" : arguments[i]; 
            	
　　　　　　　　　　　var reg= new RegExp("({)" + i + "(})", "g");

                result = result.replace(reg, value);
            }
        }
    }
    return result;
}

function http(url, data){
	
	var result = null;
	
	$.ajax({
		type : 'GET',
		url : url,
		dataType : "json",
		data:"body=" + JSON.stringify(data),
		async:false,
		success : function(data) {

			if (data.code == 200) {

				result = data.data;
			}
		},
	});
	
	return result;
}


function pageProvider(data){
	
	return http('/exhibit-admin/show/pageProvider', data);
}

function pageConsumer(data){
	
	return http('/exhibit-admin/show/pageConsumer', data);
}

function listEnv(){
	
	return http('/exhibit-admin/show/listEnv', null);
}

function listProviderDetail(data){
	
	return http('/exhibit-admin/show/listProviderDetail', data);
}

function removeProviderDetail(data){
	
	return http('/exhibit-admin/show/removeProviderDetail', data);
}

function removeConsumer(data){
	
	return http('/exhibit-admin/show/removeConsumer', data);
}
