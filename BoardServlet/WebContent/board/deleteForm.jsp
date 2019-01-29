<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script>
function del(){ //삭제하기
	frm.submit()
}

function cel(){ //취소하기
	if(confirm("취소 하시겠습니까?")){
		location.href="list";
	}
}


</script>
</head>
<body>
<form action="delete" method="post" id=frm>
	<table align="center" border=1px >
		<tr>
			<td>삭제하기</td>
		</tr>
		<tr>
			<td>
			<input type="hidden" value="${num}" name="num">
			<input type="text" placeholder="비밀번호 입력" name="password">&nbsp; 
			<input type="button" value="삭제" onclick="del()">&nbsp;
			<input type="button" value="취소" onclick="cel()">&nbsp;
			</td>
		</tr>
	</table>
</form>

</body>
</html>