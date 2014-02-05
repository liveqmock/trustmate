<%@ page language="java" contentType="text/html; charset=utf-8" import = "java.io.*"%><%
    
    
    String fileName = (String)request.getAttribute("fileName");
    String filePath = (String)request.getAttribute("filePath");

    if (fileName == null) fileName = "";
    if (filePath == null) filePath = "";

    try {
        if (filePath.equals("") || fileName.equals("")) {
            out.println("<script language='javascript'>");
            out.println("alert('File Name or Path not specified.');");
            out.println("</script>");
            return;
        }
        
    } catch (Exception e) {
        out.println("<script language='javascript'>");
        out.println("alert('Unexpected Error.');");
        out.println("</script>");
        return;
    }
    
    String agent=request.getHeader("USER-AGENT");
	 if (agent.indexOf("MSIE 6.0") > -1 || agent.indexOf("MSIE 5.5") > -1) {
	  response.setHeader("Content-type", "application/octet-stream");
	  response.setHeader("Content-Disposition", "attachment; filename="+fileName+".xls");
	  response.setHeader("Content-Transfer-ENCODING", "binary");
	  response.setHeader("Pragma", "no-cache");
	  response.setHeader("Cache-Control", "private");
	  response.setHeader("Expires", "0");
	 } else {
	  response.setHeader("Content-type", "file/unknown");
	  response.setHeader("Content-Disposition", "attachment; filename="+fileName+".xls");
	  response.setHeader("Content-Description", "Servlet Generated Data");
	  response.setHeader("Pragma", "no-cache");
	  response.setHeader("Cache-Control", "private");
	  response.setHeader("Expires", "0");
	 }
    

    try {
        File file = new File(filePath+fileName); 
        byte b[] = new byte[(int)file.length()];

        response.setHeader("Content-Disposition","attachment;filename=" + fileName);

        if (file.isFile()) {
                 BufferedInputStream fin = new BufferedInputStream(new FileInputStream(file) );
                 BufferedOutputStream outs = new BufferedOutputStream(response.getOutputStream());
                 int read = 0;
                 while ((read = fin.read(b)) != -1) {
                            outs.write(b,0,read);
                 }
                 outs.close();
                 fin.close();
        } else {
            response.setHeader("Content-Disposition","");
            out.println("<script language=javascript>");
            out.println("alert('File does not exist!');");
            out.println("history.back();");
            out.println("</script>");
        }
    } catch (IOException e) {
        response.setHeader("Content-Disposition","");
        out.println("<script language=javascript>");
        out.println("alert('Does not file !');");
        out.println("history.back();");
        out.println("</script>");
    }
%>