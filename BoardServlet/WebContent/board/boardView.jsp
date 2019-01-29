<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<script>

function upD(){ //수정하기
	location.href="update?num=${vo.board_num}";
} 

function del(){ //삭제하기 (비밀번호 유효 검사 여기서 하면 웹피이지 소스에서 보이니깐 DB에서 처리 하도록)
		location.href="delete?num=${vo.board_num}";
}

function answer(){ //답글달기 
	//frm.action="answer"; // form에 name줬으면 frm. id줬으면 #frm.
	//frm.submit() 	이렇게 코드 짜면 세개 버튼 다 submit으로 만든 다음 각각의 action으로 post,get 제출 가능
	location.href="answerBridge?num=${vo.board_num}&ref=${vo.board_re_ref}&lev=${vo.board_re_lev}&step=${vo.board_re_step}";
}

function comment(){
	$.ajax({
		url:"comment",
		type:"post",
		data:{"msg" : $("#msg").val(),"bNum" :$("#bbNum").val(),"cName" : $("#cName").val()},
		success:function(data){ 
			
			data=$.parseJSON(data);
			alert(data);
			var htmlStr="";
			htmlStr += "<table>";
			for(var i=0; i<data.length;i++){
				htmlStr += "<tr>";
				htmlStr += "<td>" + data[i].cMsg+"</td>";
				htmlStr += "</tr>";
			}
			htmlStr += "</table>";
			$("#commentResult").html(htmlStr);
		},
		error:function(e){
			alert("오류메시지: " +e);
		}
	});
}
</script>
<body>

<form name="frm">
<table border=1px>
	<tr>
		<td>번호</td>
		<td>이름</td>
		<td>제목</td>
		<td>내용</td>
		<td>업로드파일></td>
		<td>조회수</td>
		<td>작성일</td>
	</tr>
	<tr>
		<td>${vo.board_num }</td>
		<td>${vo.board_name }</td>
		<td>${vo.board_subject }</td>
		<td>${vo.board_content }</td>
		<td>${vo.board_file }</td>
		<td>${vo.board_readcount }</td>
		<td>${vo.board_date }</td>
		<input type="hidden" value="${vo.board_num }" id="bbNum">
		<input type="hidden" value="${vo.board_re_ref }">
		<input type="hidden" value="${vo.board_re_lev }">
		<input type="hidden" value="${vo.board_re_step }">
	</tr>
	<tr>
		<td colspan=7 align="right">
			<input type="button" value="답글" onclick="answer()">
			<input type="button" value="수정" onclick="upD()">
			<input type="button" value="삭제" onclick="del()">
			
		</td>
	</tr>
</table>
<input type="text" size="150" name="msg" id="msg"> <input type="text" id="cName"> <input type="button" value="댓글" onclick="comment()">
</form>

<div id="commentResult"></div>


</body>
</html>



