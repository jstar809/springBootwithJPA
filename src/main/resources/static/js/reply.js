async function getList({bno , page , size , goLast}){
	const resp = await axios.get(`/replies/list/${bno}`,{params : {page , size}});
	
	if(goLast){
		const total = resp.data.total;
		const lastPage =parseInt(Math.ceil(total/size));
		
		return getList({bno , page:lastPage , size , goLast:false})
				
	}
	
	return resp.data;	
}

async function addReply (replyObj){
	const resp = await axios.post('/replies' ,replyObj);
	
	return resp.data;
}

async function getReply(rno) {
	const resp = await axios.get(`/replies/${rno}`);
	
	return resp.data;
}

async function modify(replyObj){
	const resp = await axios.put(`/replies/${replyObj.rno}` , replyObj);
	
	return resp.data;
}

async function remove(rno) {
	const resp = await axios.delete(`/replies/${rno}`);
	
	return resp.data;
	
	
}