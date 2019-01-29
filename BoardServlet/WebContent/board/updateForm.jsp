<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script>
function cel(){ //취소하기
	if(confirm("취소 하시겠습니까?")){
		history.go(-1);
	}
}
</script>
</head>
<body>
<h2>게시판 글 수정하기</h2>
<form action="update" method="post" name="frm" enctype="multipart/form-data">
<input type="hidden" name="board_num" value="${vo.board_num}">
<table border =1px width="500">
	<tr>
		<td>글쓴이</td>
		<td><input type="text" name="writer" value="${vo.board_name }" disabled> <a href="list">글목록</a></td>
	</tr>
	<tr>
		<td>비밀번호</td>
		<td>
			<input type="hidden" name="oldPassword" value="${vo.board_pass}">
			<input type="password" name="newPassword" placeholder="비밀번호 일치해야 수정가능"></td>
	</tr>
	<tr>
		<td>제목</td>
		<td><input type="text" name="title" value="${vo.board_subject}"></td>
	</tr>
	<tr>
		<td>내용</td>
		<td><textarea rows="30" cols="50" name="content" >${vo.board_content }</textarea></td>
	</tr>
	<tr>
		<td>파일첨부</td>
		<td>기존 파일:${vo.board_file }<input type="file" name="file">←수정할 파일</td>
	</tr>
</table>
<input type="submit" name="submit" value="수정하기"> <input type="button" name="reset" value="취소" onclick="cel()">
</form>

</body>
</html>