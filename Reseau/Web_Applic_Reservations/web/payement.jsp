<%--
    Document   : payement
    Created on : 18-oct.-2012, 15:55:40
    Author     : Greenlamp
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@page errorPage="error.jsp"%>
<jsp:useBean id="beanUser" scope="session" class="Bean.BeanUser"/>
<% if(beanUser == null || beanUser.getNom() == null){ %>
<% RequestDispatcher rd; %>
<% ServletContext sc = getServletContext(); %>
<% rd = sc.getRequestDispatcher("/Servlet_Controle?action=error&message=\"Impossible d'accéder à cette page sans être authentifié au préalable.\"");%>
<% rd.forward(request, response); %>
<% } %>
<jsp:useBean id="beanCaddy" scope="session" class="Bean.BeanCaddy"/>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" media="all" type="text/css" href="css/style.css" />
        <title>Payement <%=beanUser.getNom()%></title>
    </head>
    <body>
        <div class="site">
            <div class="header">
                <span>Site de réservation de chambre</span>
            </div>
            <div class="clean"></div>
            <div class="corp">
                <div class="menu_v">
                    <div class="menu_v_top">Menu</div>
                    <div class="menu_v_ctn">
                        <ul>
                            <li><a href="?action=accueil">Accueil</a></li>
                            <li><a href="?action=magasin">Magasin</a></li>
                            <li><a href="?action=caddy">Caddy</a></li>
                        </ul>
                    </div>
                </div>
                <div class="corp_ctn">
                    <h1>Payement</h1>
                    <div class="paragraphe">
                        <form action="Servlet_Controle" method="post">
                            <table align="center">
                                <tr>
                                    <td>
                                        Numéro de carte de crédit:
                                    </td>
                                    <td>
                                        <input type="text" name="numCarte"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        Numéro de chance:
                                    </td>
                                    <td>
                                        <%=getServletContext().getAttribute("numero")%>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        Total à payer:
                                    </td>
                                    <td>
                                        <%int total = beanCaddy.getPrixTotal();%>
                                        <%=total%>
                                        <input type="hidden" name="total" value="<%=total%>"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <input type="hidden" name="action" value="payer"/>
                                        <input type="submit" value="Payer !"/>
                                    </td>
                                </tr>
                            </table>
                        </form>
                    </div>
                </div>
            </div>
            <div class="clean"></div>
            <div class="foot">
                <span>
                    Codé par Gabriel Knuts 2302
                </span>
            </div>
        </div>

    </body>
</html>
