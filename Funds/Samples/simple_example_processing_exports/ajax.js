


function ajaxWithJSON(){
	let request = new XMLHttpRequest();
	request.open("POST", "ajaxWithJSON.php");
	request.send();
	request.onreadystatechange = function(){
		if(this.readyState == 4 && this.status == 200){
			let data = this.responseText.split("\n\n\n\n");
			let payload = new Array();
			for(let i = 0; i < data.length-1; i++){
				payload[i] = JSON.parse(data[i]);
			}
			let target = 0;
			let sentence = "the account is named " + payload[target].name + " and contains the transaction " + payload[target].transactions[0].description + " which was debited for " + payload[target].transactions[0].debit;
			document.getElementById('JSONoutput').innerHTML = sentence;
		}
	}
}


function ajaxWithXML(){
	let request = new XMLHttpRequest();
	request.open("POST", "ajaxWithXML.php", true);
	request.send();
	request.onreadystatechange = function(){
		if(this.readyState == 4 && this.status == 200){
			let data = this.responseXML;
			let payload = data.getElementsByTagName('Account');
			let target = 0;
			let sentence = "the account is named " + payload[target].children[0].innerHTML + " and contains the transaction " + payload[target].children[7].children[0].children[2].innerHTML + " which was debited for " + payload[target].children[7].children[0].children[5].innerHTML;
			document.getElementById('XMLoutput').innerHTML = sentence;
		}
	}
}









