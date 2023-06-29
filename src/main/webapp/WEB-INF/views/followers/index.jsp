<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="../layout/app.jsp">
    <c:param name="content">
        <h2>${employee.name}のフォロワーリスト</h2>
        <table id="follower_list">
            <tbody>
                <tr>
                    <th class="follower_code">社員番号</th>
                    <th class="follower_name">氏名</th>
                    <th class="follower_action">操作</th>
                </tr>
                <c:forEach var="follower" items="${followers}" varStatus="status">
                    <tr class="row${status.count % 2}">
                        <td class="follower_code"><c:out value="${follower.followEmployee.code}" /></td>
                        <td class="follower_name"><c:out value="${follower.followEmployee.name}" /></td>
                        <td class="follower_action"><a href="<c:url value='/employees/show?id=${follower.followEmployee.id}' />">詳細を表示</a></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <div id="pagination">
            （全 ${followers_count}名）<br />
            <c:forEach var="i" begin="1" end="${((followers_count - 1) / 15) + 1}" step="1">
                <c:choose>
                    <c:when test="${i == page}">
                        <c:out value="${i}" />&nbsp;
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value='/followers/index?page=${i}' />"><c:out value="${i}" /></a>&nbsp;
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>


        <p><a href="<c:url value='/employees/show?id=${employee.id}' />">${employee.name}さんの詳細ページへ戻る</a></p>

    </c:param>
</c:import>