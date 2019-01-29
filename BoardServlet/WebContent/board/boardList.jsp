<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div align="center" id="pageResult"><!--페이징 뿌리는 영역 열림  -->

<c:if test = "${count gt 0 }"><!--if 안에 게시글 수 및 게시글  -->
<div align ="right" > 총 게시물 수:${count }</div><br> 

<!-- 전체보기 -->
<table border=1px align="center" width="700px" >
	<tr>
		<td>글번호</td>
		<td>글제목</td>
		<td>글쓴이</td>
		<td>날짜</td>
	</tr>
	
<c:forEach var ="vo" items="${list }" varStatus="status">
	
	<tr>
		<td>${number-status.index }</td> <!--페이징 이후의 게시글 번호를 매김 db와는 연관x  -->
		<c:if test="${vo.board_re_lev  > 0}">
			<td><img src="./image/level.gif" width="${vo.board_re_lev*8}" height ="16">
				<img src="./image/re.gif"><!--답글이면 이미지 넣고  -->
				<a href="view?num=${vo.board_num }">${vo.board_subject }</a>
			</td> 
		</c:if>
		<c:if test="${vo.board_re_lev <=0 }">
			<td><a href="view?num=${vo.board_num }">${vo.board_subject }</a></td> <!--그냥 글이면 이미지 없이  -->
		</c:if>
		<td>${vo.board_name }</td>
		<td>${vo.board_date }</td>
		<input type="hidden" value="${vo.board_re_ref }">
		<input type="hidden" value="${vo.board_re_lev }">
		<input type="hidden" value="${vo.board_re_step }">
		<input type="hidden" value="${vo.board_readcount }">
	</tr>

</c:forEach>
</table>
</c:if>
<!--  -->



<c:if test="${startPage>blockPage }"><!-- 이전 페이지 표시  -->
<a href="javascript:getData(${startPage-blockPage })">[이전]</a>
</c:if>

<!--페이지들 출력  -->
<c:forEach begin="${startPage }" end="${endPage }" var ="i">
	<c:if test="${i==currentPage }">
		${i }
	</c:if>
	<c:if test="${i!=currentPage }">
		<a href="javascript:getData(${i })">[${i }]</a>
	</c:if>
</c:forEach>

<!--다음 페이지 표시  -->
<c:if test="${endPage<totPage }">
	<a href="javascript:getData(${endPage+1 })">[다음]</a>
</c:if>
</div><!--페이징 뿌리는 영역 닫힘  -->



<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<script> //페이지 뿌리는 메소드(영역(pageResult) 을 달리하면 다른 곳에도 뿌릴 수 있다)
function getData(pageNum){ //위의  var =i / ${i} 인자를 pageNum으로 받았음
	$("#pageResult").load("list",{"pageNum" : pageNum},function(data){
		$("#pageResult").html(data);
	})
	
}

</script>
