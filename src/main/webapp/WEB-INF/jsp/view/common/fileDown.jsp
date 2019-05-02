<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="java.io.*"%>
<%@ page import="java.text.*" %>
<%@ page import="java.lang.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.net.*" %>
<%	
	String filePath = "";
	if(request.getAttribute("filePath")!=null && !"".equals(request.getAttribute("filePath").toString())){
		filePath = request.getAttribute("filePath").toString();
	}
	String fileName = "";
	if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
		fileName = request.getParameter("fileName");
	}
	
	String orgfilename = fileName;
	  
	InputStream in = null;
	OutputStream os = null;
	File file = null;
	boolean skip = false;
	String client = "";
	String savePath = "";
	savePath = filePath;
	%><%=savePath%><%
	try{
	
	    try{
	        file = new File(savePath, fileName);
	        in = new FileInputStream(file);
	    }catch(FileNotFoundException fe){
	    	out.println(fe);
	        skip = true;
	    }
		
	    client = request.getHeader("User-Agent");
	
	
	    response.reset() ;
	    response.setContentType("application/octet-stream");
	    response.setHeader("Content-Description", "JSP Generated Data");
	
	    if(!skip){   
	        // IE
	        if(client.indexOf("MSIE") != -1){
	            response.setHeader ("Content-Disposition", "attachment; filename="+new String(orgfilename.getBytes("KSC5601"),"ISO8859_1"));
	
	        }else{
	
	            orgfilename = new String(orgfilename.getBytes("utf-8"),"iso-8859-1");
	
	            response.setHeader("Content-Disposition", "attachment; filename=\"" + orgfilename + "\"");
	            response.setHeader("Content-Type", "application/octet-stream; charset=utf-8");
	        }  
	         
	        response.setHeader ("Content-Length", ""+file.length() );
	
	
			out.clear();
			out = pageContext.pushBody();
	
	        os = response.getOutputStream();
	        byte b[] = new byte[(int)file.length()];
	        int leng = 0;
	         
	        while( (leng = in.read(b)) > 0 ){
	            os.write(b,0,leng);
	        }
	    }else{
	        response.setContentType("text/html;charset=UTF-8");
	        out.println("파일을 찾을수 없습니다.");
	    }
	}catch(Exception e){
	  e.printStackTrace();
	}finally{
		if(in!=null){
			try{
				 in.close();
			}catch(Exception ex){}
		}
		if(os!=null){
			try{
				 os.close();
				 
			}catch(Exception ex){}
		}
	}
	
%>