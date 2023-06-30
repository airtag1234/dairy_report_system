<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="constants.ForwardConst" %>
<%@ page import="constants.AttributeConst" %>

<c:set var="actRep" value="${ForwardConst.ACT_REP.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commEdt" value="${ForwardConst.CMD_EDIT.getValue()}" />
<c:set var="commDoFav" value="${ForwardConst.CMD_DO_FAVORITE.getValue()}" />
<c:set var="commDelFav" value="${ForwardConst.CMD_DELETE_FAVORITE.getValue()}" />
<c:set var="actFol" value="${ForwardConst.ACT_FOL.getValue()}" />
<c:set var="commCrt" value="${ForwardConst.CMD_CREATE.getValue()}" />
<c:set var="commDest" value="${ForwardConst.CMD_DESTROY.getValue()}" />


<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
    <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}" />
            </div>
        </c:if>

        <h2>日報 詳細ページ</h2>

        <table>
            <tbody>
                <tr>
                    <th>氏名</th>
                    <td><c:out value="${report.employee.name}" /></td>
                </tr>
                <tr>
                    <th>日付</th>
                    <fmt:parseDate value="${report.reportDate}" pattern="yyyy-MM-dd" var="reportDay" type="date" />
                    <td><fmt:formatDate value='${reportDay}' pattern='yyyy-MM-dd' /></td>
                </tr>
                <tr>
                    <th>内容</th>
                    <td><pre><c:out value="${report.content}" /></pre></td>
                </tr>
                <tr>
                    <th>登録日時</th>
                    <fmt:parseDate value="${report.createdAt}" pattern="yyyy-MM-dd'T'HH:mm:ss" var="createDay" type="date" />
                    <td><fmt:formatDate value="${createDay}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                </tr>
                <tr>
                    <th>更新日時</th>
                    <fmt:parseDate value="${report.updatedAt}" pattern="yyyy-MM-dd'T'HH:mm:ss" var="updateDay" type="date" />
                    <td><fmt:formatDate value="${updateDay}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                </tr>

                <tr>
                    <c:if test="${favoriteCount > 0}">
                        <th>「いいね」の数</th>
                        <td>${favoriteCount}件</td>
                    </c:if>
                </tr>
            </tbody>
        </table>
         <c:choose>
        <c:when test="${sessionScope.login_employee.id == report.employee.id}">
            <p>
                <a href="<c:url value='?action=${actRep}&command=${commEdt}&id=${report.id}' />">この日報を編集する</a>
            </p>
        </c:when>
        <c:otherwise>
            <c:if test="${isFollow == false}">
                <form method="POST" action="<c:url value='?action=${actFol}&command=${commCrt}&id=${report.employee.id}' />">
                <br />
                <input type="submit" value="この作成者をフォローする">
                </form>
            </c:if>
            <c:if test="${isFollow == true}">
                 <form method="POST" action="<c:url value='?action=${actFol}&command=${commDest}&id=${report.employee.id}' />">
                <br />
                <input type="submit" value="この作成者をフォローを外す">
                </form>
            </c:if>
        </c:otherwise>
        </c:choose>

         <br>
        <c:choose>
            <c:when test="${myFavoriteCount != 1}">
                <form method="POST" action="<c:url value='?action=${actRep}&command=${commDoFav}&id=${report.id}' />">
                    <input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}" />
                    <button type="submit">いいね</button>
                </form>
            </c:when>
            <c:otherwise>
                <form method="POST" action="<c:url value='?action=${actRep}&command=${commDelFav}&id=${report.id}' />">
                    <input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}" />
                    <button type="submit">いいねを取り消す</button>
                </form>
            </c:otherwise>
        </c:choose>

        <br><br>
        <p>
            <a href="<c:url value='?action=${actRep}&command=${commIdx}' />">一覧に戻る</a>
        </p>
    </c:param>
</c:import>