function statusFilter(status) {

	if (status == undefined) {

		return "";
	}
	if (status === 101) {

		return "正常";
	} else {

		return "断开";
	}
}

function dateFilter(time) {

	if(time == undefined || time == 0){
		
		return "";
	}
	var d = new Date(time);  
    var dformat = [ d.getFullYear(), d.getMonth() + 1, d.getDate() ].join('-')   
            + ' ' + [ d.getHours(), d.getMinutes()].join(':');  
    return dformat;  
}
