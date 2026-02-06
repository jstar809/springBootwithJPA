
async function upLoadToServer(formObj){
	console.log(formObj);
	const resp = await axios({
	    url: "/upload",
	    method: "post",
	    data: formObj,
	    headers: {
	        "Content-Type": "multipart/form-data"
	    }
	});
	 console.log(resp);
	 
	 return resp.data;
}

async function removeFileToServr(uuid, fileNmae){
	 const resp = await axios({
		url: `/remove/${uuid}_${fileNmae}`,
	    method: "delete",
	});
	
	return resp.data;
}