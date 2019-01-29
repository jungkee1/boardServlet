<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h2>게시판 글 등록</h2>
<form action=write.vo method="post" name="frm" enctype="multipart/form-data">
<table border =1px width="500">
	<tr>
		<td>글쓴이</td>
		<td><input type="text" name="writer"> <a href="list">글목록</a></td>
	</tr>
	<tr>
		<td>비밀번호</td>
		<td><input type="password" name="password"></td>
	</tr>
	<tr>
		<td>제목</td>
		<td><input type="text" name="title"></td>
	</tr>
	<tr>
		<td>내용</td>
		<td><textarea rows="30" cols="50" name="content"></textarea></td>
	</tr>
	<tr>
		<td>파일첨부</td>
		<td><input type="file" name="file"></td>
	</tr>
</table>
<input type="submit" name="submit"> <input type="reset" name="reset">
</form>

</body>
</html>