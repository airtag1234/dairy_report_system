<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="../layout/app.jsp">
    <c:param name="content">
        <h2>フォローリスト</h2>
        <ul>
            <c:forEach var="follow" items="${follows}">
                <li>
                        <c:out value="${employee.id}" />
                </li>
            </c:forEach>
        </ul>

        <a href="<c:url value='/employees/show?id=${employee.id}' />">詳細ページへ戻る</a>

    </c:param>
</c:import>